package yporque.utils;

import org.springframework.stereotype.Component;
import yporque.model.*;
import yporque.request.VentaRequest;

import java.time.Instant;
import java.util.function.BiFunction;

/**
 * Created by francisco on 24/12/15.
 */
@Component("ventaFunction")
public class VentaFunction implements BiFunction<Instant, VentaRequest, Venta> {
    @Override
    public Venta apply(Instant instant, VentaRequest ventaRequest) {
        Articulo articulo = ventaRequest.getArticulo();
        Vendedor vendedor = ventaRequest.getVendedor();
        TipoDePago tipoDePago = (ventaRequest.getFormaPago().equals("Efectivo")?TipoDePago.EFECTIVO:TipoDePago.TARJETA);
        return new Venta(instant,
                        articulo.getCodigo(),
                        articulo.getDescripcion(),
                        1,
                        articulo.getFactor1(),
                        articulo.getFactor2(),
                        articulo.getPrecioLista(),
                        articulo.getPrecio(),
                        tipoDePago,
                        vendedor.getUsername(),
                        ventaRequest.getNroCupon()
        );
    }
}
