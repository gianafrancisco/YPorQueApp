package yporque.model;

/**
 * Created by francisco on 23/12/15.
 */
public class VentaRequest {
    private Articulo articulo;
    private Integer cantidad;
    private Vendedor vendedor;
    private String formaPago;

    public Articulo getArticulo() {
        return articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public String getFormaPago() {
        return formaPago;
    }
}
