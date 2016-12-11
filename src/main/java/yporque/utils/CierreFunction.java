package yporque.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yporque.model.*;
import yporque.repository.CajaRepository;
import yporque.repository.ResumenRepository;
import yporque.repository.RetiroRepository;

import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;
import org.apache.log4j.Logger;

/**
 * Created by francisco on 30/12/15.
 */
@Component("cierreFunction")
public class CierreFunction implements BiFunction<Instant, String, Caja> {

    final static Logger logger = Logger.getLogger(CierreFunction.class);

    private RetiroRepository retiroRepository;
    private CajaRepository cajaRepository;
    private ResumenRepository resumenRepository;


    @Autowired
    public CierreFunction(RetiroRepository retiroRepository, CajaRepository cajaRepository, ResumenRepository resumenRepository) {
        this.retiroRepository = retiroRepository;
        this.cajaRepository = cajaRepository;
        this.resumenRepository = resumenRepository;
    }

    @Override
    public Caja apply(Instant cierre, String username) {

        List<Caja> cajaList = cajaRepository.findByCierre(Instant.EPOCH);

        if(cajaList.isEmpty()){
            cajaList = cajaRepository.findAll();
        }

        if(!cajaList.isEmpty()) {
            Caja abierta = cajaList.get(0);
            abierta.setCierreUsername(username);
            abierta.setCierre(cierre);

            List<Retiro> retiroList = retiroRepository.findByFechaBetween(abierta.getApertura(), abierta.getCierre());
            List<Resumen> resumenList = resumenRepository.findByFechaBetween(abierta.getApertura(), abierta.getCierre());

            Double efectivo = 0.0;
            Double tarjeta = 0.0;
            Double retiros = 0.0;

            retiros = retiroList.stream().mapToDouble(Retiro::getMonto).sum();
            efectivo = resumenList.stream().mapToDouble(Resumen::getEfectivo).sum();
            tarjeta = resumenList.stream().mapToDouble(Resumen::getTarjeta).sum();

            abierta.setEfectivo(efectivo);
            abierta.setTarjeta(tarjeta);
            abierta.setTotalVentaDia(efectivo + tarjeta);
            abierta.setEfectivoDiaSiguiente(efectivo - retiros);
            return abierta;
        }else{
            logger.info("Caja open not found.");
            return null;
        }
    }
}
