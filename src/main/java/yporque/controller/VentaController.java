package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.Articulo;
import yporque.model.Venta;
import yporque.model.VentaRequest;
import yporque.repository.ArticuloRepository;
import yporque.repository.VentaRepository;
import yporque.utils.VentaFunction;

import java.time.Instant;
import java.util.List;

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

    @RequestMapping("/venta/confirmar")
    public Double confirmar(@RequestBody List<VentaRequest> ventas){

        Instant fecha = Instant.now();

        ventas.stream().forEach(ventaRequest -> {
            Articulo art = articuloRepository.findOne(ventaRequest.getArticulo().getArticuloId());
            art.setCantidadStock(art.getCantidadStock()-ventaRequest.getCantidad());
            articuloRepository.saveAndFlush(art);
            ventaRepository.save(ventaFunction.apply(fecha,ventaRequest));
        });

        return ventas.stream().mapToDouble(ventaRequest -> ventaRequest.getCantidad()*ventaRequest.getArticulo().getPrecio()).sum();

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

}

