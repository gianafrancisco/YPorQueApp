package yporque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yporque.model.*;
import yporque.repository.ArticuloRepository;
import yporque.repository.RetiroRepository;
import yporque.repository.VentaRepository;
import yporque.utils.VentaFunction;

import java.time.*;
import java.util.List;
import java.util.function.Function;

/**
 * Created by francisco on 23/12/15.
 */
@RestController
@Component("retiroController")
public class RetiroController {

    @Autowired
    private RetiroRepository retiroRepository;

    public static Function<RetiroRequest, Retiro> convertRetiro(){
        Function<RetiroRequest, Retiro> function = retiroRequest1 -> {
            return new Retiro(retiroRequest1.getMonto(), retiroRequest1.getDescripcion(), Instant.now(), retiroRequest1.getUsername());
        };
        return function;
    }

    @RequestMapping("/retiro/agregar")
    public Retiro agregar(@RequestBody RetiroRequest retiroRequest){

        Retiro retiro = convertRetiro().apply(retiroRequest);
        retiroRepository.saveAndFlush(retiro);
        return retiro;
    }


    @RequestMapping("/retiro/today")
    public Page<Retiro> obtenerListado(Pageable pageRequest) {

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime stopTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        return retiroRepository.findByFechaBetween(startTime.toInstant(ZoneOffset.UTC), stopTime.toInstant(ZoneOffset.UTC), pageRequest);
    }


}

