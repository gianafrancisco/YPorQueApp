package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.Cuenta;
import yporque.model.Entrega;
import yporque.model.Movimiento;
import yporque.repository.CuentaRepository;
import yporque.repository.MovimientoRepository;

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

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET)
    public ResponseEntity<Page<Cuenta>> get(Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.findAll(pageRequest));
        return responseEntity;
    }


    @RequestMapping(value = "/cuentas/{cuentaId}/movimientos", method = RequestMethod.GET)
    public ResponseEntity<Page<Movimiento>> get(@PathVariable Long cuentaId, Pageable pageRequest){
        return ResponseEntity.ok(movimientoRepository.findByCuentaId(cuentaId, pageRequest));
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET, params = {"search"})
    public ResponseEntity<Page<Cuenta>> get(String search, Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.search(search, pageRequest));
        return responseEntity;
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET, params = {"dni"})
    public ResponseEntity<Page<Cuenta>> getDni(String dni, Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.findByDni(Long.valueOf(dni), pageRequest));
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
    public ResponseEntity<Movimiento> postMovimeinto(@PathVariable Long cuentaId, Entrega entrega){
        Movimiento movimiento = Movimiento.generarEntrega(Instant.now(), entrega.getDescripcion(), cuentaId, entrega.getMonto());
        movimiento = movimientoRepository.saveAndFlush(movimiento);
        ResponseEntity<Movimiento> responseEntity = ResponseEntity.created(URI.create("/cuenta/" + cuentaId + "/movimientos/" + movimiento.getId())).body(movimiento);
        return responseEntity;
    }
}
