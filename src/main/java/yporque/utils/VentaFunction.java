package yporque.utils;

import org.springframework.stereotype.Component;
import yporque.model.*;

import java.time.Instant;
import java.util.function.BiFunction;
import java.util.function.Function;

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
                        ventaRequest.getCantidad(),
                        articulo.getFactor1(),
                        articulo.getFactor2(),
                        articulo.getPrecioLista(),
                        articulo.getPrecio()*ventaRequest.getCantidad(),
                        tipoDePago,
                        vendedor.getUsername()
                );
    }
}
