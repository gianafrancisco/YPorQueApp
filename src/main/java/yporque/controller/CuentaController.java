package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.*;
import yporque.repository.CuentaRepository;
import yporque.repository.MovimientoRepository;
import yporque.repository.VentaRepository;

import java.net.URI;
import java.time.Instant;

/**
 * Created by francisco on 18/12/15.
 */
@RestController
@Component("cuentaController")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET)
    public ResponseEntity<Page<Cuenta>> get(Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.findAll(pageRequest));
        responseEntity.getBody().getContent().stream().forEach(
                cuenta -> {
                    Double saldo = movimientoRepository.saldo(cuenta.getId());
                    cuenta.setSaldo(saldo == null ? 0.0: saldo);
                }
        );
        return responseEntity;
    }


    @RequestMapping(value = "/cuentas/{cuentaId}/movimientos", method = RequestMethod.GET)
    public ResponseEntity<Page<Movimiento>> get(@PathVariable Long cuentaId, Pageable pageRequest){
        return ResponseEntity.ok(movimientoRepository.findByCuentaId(cuentaId, pageRequest));
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET, params = {"search"})
    public ResponseEntity<Page<Cuenta>> get(String search, Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.search(search, pageRequest));
        responseEntity.getBody().getContent().stream().forEach(
                cuenta -> {
                    Double saldo = movimientoRepository.saldo(cuenta.getId());
                    cuenta.setSaldo(saldo == null ? 0.0: saldo);
                }
        );
        return responseEntity;
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET, params = {"dni"})
    public ResponseEntity<Page<Cuenta>> getDni(String dni, Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.findByDni(Long.valueOf(dni), pageRequest));
        responseEntity.getBody().getContent().stream().forEach(
                cuenta -> {
                    Double saldo = movimientoRepository.saldo(cuenta.getId());
                    cuenta.setSaldo(saldo == null ? 0.0: saldo);
                }
        );
        return responseEntity;
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.POST)
    public ResponseEntity<Cuenta> post(@RequestBody Cuenta cuenta){
        Cuenta c = cuentaRepository.saveAndFlush(cuenta);
        return ResponseEntity.ok(c);
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.PUT)
    public ResponseEntity<Cuenta> put(@RequestBody Cuenta cuenta){
        Cuenta c = cuentaRepository.saveAndFlush(cuenta);
        return ResponseEntity.ok(c);
    }

    @RequestMapping(value = "/cuentas/{cuentaId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long cuentaId){
        cuentaRepository.delete(cuentaId);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/cuentas/movimientos/{movimientoId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long movimientoId){
        movimientoRepository.delete(movimientoId);
        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
        return responseEntity;
    }

    @RequestMapping(value = "/cuentas/{cuentaId}/movimientos", method = RequestMethod.POST)
    public ResponseEntity<Movimiento> postMovimeinto(@PathVariable Long cuentaId,@RequestBody Entrega entrega){
        Instant fecha = Instant.now();
        Movimiento movimiento = Movimiento.generarEntrega(fecha, entrega.getDescripcion(), cuentaId, entrega.getMonto());
        movimiento = movimientoRepository.saveAndFlush(movimiento);
        Cuenta cuenta = cuentaRepository.findOne(cuentaId);
        // TODO: Add vendedor to venta and movimiento
        Venta venta = new Venta(fecha, "ENTREGA", "Cuenta Corriente " + cuenta.getDni(), 1, 1.0, 1.0,
                                movimiento.getImporte(), movimiento.getImporte(), TipoDePago.C_CORRIENTE,"", "");
        ventaRepository.saveAndFlush(venta);
        ResponseEntity<Movimiento> responseEntity = ResponseEntity.created(URI.create("/cuenta/" + cuentaId + "/movimientos/" + movimiento.getId())).body(movimiento);
        return responseEntity;
    }
}
