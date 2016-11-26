package yporque.utils;

import org.springframework.stereotype.Component;
import yporque.model.Movimiento;
import yporque.request.VentaRequest;

import java.time.Instant;
import java.util.function.BiFunction;

/**
 * Created by francisco on 11/22/16.
 */
@Component("movimientoFunction")
public class MovimientoFunction implements BiFunction<Instant, VentaRequest, Movimiento> {

    @Override
    public Movimiento apply(Instant instant, VentaRequest ventaRequest) {

        Movimiento movimiento = new Movimiento(
                    instant,
                    0,
                    ventaRequest.getArticulo().getCodigo(),
                    ventaRequest.getArticulo().getDescripcion(),
                    ventaRequest.getArticulo().getPrecio()*(1-ventaRequest.getDescuento()/100)*-1
        );
        return movimiento;
    }
}
