package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.*;
import yporque.repository.*;
import yporque.request.ConfirmarVentaRequest;
import yporque.request.DevolucionRequest;
import yporque.request.VentaRequest;
import yporque.utils.MovimientoFunction;
import yporque.utils.VentaFunction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by francisco on 23/12/15.
 */
@RestController
@Component("ventaController")
public class VentaController {

    public static final String END_TIME = "2100-05-28T00:00:00Z";
    public static final String START_TIME = "1984-05-28T00:00:00Z";
    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaFunction ventaFunction;

    @Autowired
    private MovimientoFunction movimientoFunction;

    @Autowired
    private ResumenRepository resumenRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    private Cuenta cuenta;

    @RequestMapping("/venta/confirmar")
    public Map<String, String> confirmar(@RequestBody ConfirmarVentaRequest params) {

        if(VentaFunction.getTipoDePago(params.getFormaPago()).equals(TipoDePago.C_CORRIENTE)){
            Long dni = Long.valueOf(params.getDni());
            if(cuentaRepository.findByDni(dni) == null){
                cuenta = new Cuenta("", "", "", "", dni);
                cuenta = cuentaRepository.saveAndFlush(cuenta);
            }
        }

        List<VentaRequest> ventas = params.getArticulos();
        List<DevolucionRequest> devoluciones = params.getDevoluciones();

        String vendedor = "";
        if(ventas.size() > 0){
           vendedor = ventas.get(0).getVendedor().getUsername();
        }

        Instant fecha = LocalDateTime.now().toInstant(ZoneOffset.ofHours(-3));

        if(VentaFunction.getTipoDePago(params.getFormaPago()).equals(TipoDePago.C_CORRIENTE)){
            cuenta = cuentaRepository.findByDni(Long.valueOf(params.getDni()));
        }

        ventas.stream().forEach(ventaRequest -> {
            for(int i = 0; i<ventaRequest.getCantidad(); i++) {
                Articulo art = articuloRepository.findOne(ventaRequest.getArticulo().getArticuloId());
                art.setCantidadStock(art.getCantidadStock() - 1);
                articuloRepository.saveAndFlush(art);
                Venta venta = ventaFunction.apply(fecha, ventaRequest);
                ventaRepository.saveAndFlush(venta);
                if(VentaFunction.getTipoDePago(params.getFormaPago()).equals(TipoDePago.C_CORRIENTE)){
                    Movimiento movimiento = movimientoFunction.apply(fecha, ventaRequest);
                    movimiento.setCuentaId(cuenta.getId());
                    movimientoRepository.saveAndFlush(movimiento);
                }
            }
        });

        if(VentaFunction.getTipoDePago(params.getFormaPago()).equals(TipoDePago.C_CORRIENTE)){
            if(params.getEntregaInicial() > 0){
                Movimiento entrega = Movimiento.generarEntrega(fecha, "Entrega Inicial", cuenta.getId(), params.getEntregaInicial());
                movimientoRepository.saveAndFlush(entrega);
                // TODO: Add vendedor to venta and movimiento
                Venta venta = new Venta(fecha, "ENTREGA INICIAL", "Cuenta Corriente " + cuenta.getDni(), 1, 1.0, 1.0,
                        entrega.getImporte(), entrega.getImporte(), TipoDePago.EFECTIVO, vendedor, "");
                ventaRepository.saveAndFlush(venta);
                Resumen resumen = new Resumen(fecha, TipoDePago.EFECTIVO, params.getEntregaInicial(), 0.0);
                resumenRepository.saveAndFlush(resumen);
            }
        }

        devoluciones.stream().forEach(devolucionRequest -> {
            Venta articuloDevuelto = devolucionRequest.getVenta();
            articuloDevuelto.setDevuelto(true);
            ventaRepository.saveAndFlush(articuloDevuelto);
            TipoDePago tipoDePago = VentaFunction.getTipoDePago(devolucionRequest.getFormaPago());
            List<Articulo> list = articuloRepository.findByCodigo(articuloDevuelto.getCodigo());
                if(!list.isEmpty()) {
                    Articulo art = list.get(0);
                    art.setCantidadStock(art.getCantidadStock() + 1);
                    articuloRepository.saveAndFlush(art);
                    Venta devolucion = new Venta(fecha,
                            articuloDevuelto.getCodigo(),
                            articuloDevuelto.getDescripcion(),
                            devolucionRequest.getCantidad(),
                            articuloDevuelto.getFactor1(),
                            articuloDevuelto.getFactor2(),
                            articuloDevuelto.getPrecioLista(),
                            -1 * articuloDevuelto.getPrecio(),
                            tipoDePago,
                            devolucionRequest.getVendedor().getUsername(),
                            devolucionRequest.getNroCupon()
                    );

                    ventaRepository.saveAndFlush(devolucion);
                }
            }
        );

        if(!VentaFunction.getTipoDePago(params.getFormaPago()).equals(TipoDePago.C_CORRIENTE)) {
            Resumen resumen = new Resumen(fecha, VentaFunction.getTipoDePago(params.getFormaPago()), params.getEfectivo(), params.getTarjeta());
            resumenRepository.saveAndFlush(resumen);
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("codigoDevolucion",String.format("%x", fecha.getEpochSecond()));
        return map;

    }

    @RequestMapping("/ventas")
    public Page<Venta> obtenerListado(@RequestParam(required = false) Instant startTime, @RequestParam(required = false) Instant endTime, Pageable pageRequest) {

        Instant end = Instant.parse(END_TIME);
        Instant start = Instant.parse(START_TIME);
        if(endTime != null){
            end = endTime;
        }
        if(startTime != null){
            start = startTime;
        }

        return ventaRepository.findByFechaBetween(start, end, pageRequest);
    }

    @RequestMapping("/venta/search")
    public Page<Venta> filtrar(@RequestParam(value = "") String search,@RequestParam(required = false) Instant startTime, @RequestParam(required = false) Instant endTime, Pageable pageRequest){

        Instant end = Instant.parse(END_TIME);
        Instant start = Instant.parse(START_TIME);
        if(endTime != null){
            end = endTime;
        }
        if(startTime != null){
            start = startTime;
        }

        return ventaRepository.filtrar(start, end, search, pageRequest);
    }


    @RequestMapping("/venta/devolucion")
    public List<Venta> filtrarPorCodigoDevolucion(@RequestParam(required = true) String codigoDevolucion){
        return ventaRepository.findByCodigoDevolucion(codigoDevolucion);
    }


}

