package yporque.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yporque.model.Caja;
import yporque.model.Retiro;
import yporque.model.TipoDePago;
import yporque.model.Venta;
import yporque.repository.CajaRepository;
import yporque.repository.RetiroRepository;
import yporque.repository.VentaRepository;

import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by francisco on 30/12/15.
 */
@Component("cierreFunction")
public class CierreFunction implements BiFunction<Instant, String, Caja> {

    private VentaRepository ventaRepository;
    private RetiroRepository retiroRepository;
    private CajaRepository cajaRepository;


    @Autowired
    public CierreFunction(VentaRepository ventaRepository, RetiroRepository retiroRepository, CajaRepository cajaRepository) {
        this.ventaRepository = ventaRepository;
        this.retiroRepository = retiroRepository;
        this.cajaRepository = cajaRepository;
    }

    @Override
    public Caja apply(Instant cierre, String username) {

        List<Caja> cajaList = cajaRepository.findByCierre(Instant.EPOCH);
        if(!cajaList.isEmpty()) {
            Caja abierta = cajaList.get(0);
            abierta.setCierreUsername(username);
            abierta.setCierre(cierre);

            List<Retiro> retiroList = retiroRepository.findByFechaBetween(abierta.getApertura(), abierta.getCierre());
            List<Venta> ventaList = ventaRepository.findByFechaBetween(abierta.getApertura(), abierta.getCierre());

            Double efectivo = 0.0;
            Double tarjeta = 0.0;
            Double retiros = 0.0;

            retiros = retiroList.stream().mapToDouble(Retiro::getMonto).sum();
            tarjeta = ventaList.stream().filter(venta -> venta.getTipoPago() == TipoDePago.TARJETA).mapToDouble(Venta::getPrecio).sum();
            efectivo = ventaList.stream().filter(venta -> venta.getTipoPago() == TipoDePago.EFECTIVO).mapToDouble(Venta::getPrecio).sum();

            abierta.setEfectivo(efectivo);
            abierta.setTarjeta(tarjeta);
            abierta.setTotalVentaDia(efectivo + tarjeta - retiros);
            //TODO: check offset transporte and cambio about $400
            abierta.setEfectivoDiaSiguiente(efectivo - retiros);
            return abierta;
        }
        return null;
    }
}
