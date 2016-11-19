package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yporque.model.Cuenta;
import yporque.repository.CuentaRepository;

/**
 * Created by francisco on 18/12/15.
 */
@RestController
@Component("cuentaController")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET)
    public ResponseEntity<Page<Cuenta>> get(Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.findAll(pageRequest));
        return responseEntity;
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET, params = {"search"})
    public ResponseEntity<Page<Cuenta>> get(String search, Pageable pageRequest){
        ResponseEntity<Page<Cuenta>> responseEntity = ResponseEntity.ok(cuentaRepository.search(search, pageRequest));
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

}
