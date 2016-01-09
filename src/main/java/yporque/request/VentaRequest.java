package yporque.request;

import yporque.model.Articulo;
import yporque.model.Vendedor;

/**
 * Created by francisco on 23/12/15.
 */
public class VentaRequest {
    private final Articulo articulo;
    private final Integer cantidad;
    private final Vendedor vendedor;
    private final String formaPago;
    private final String nroCupon;

    public VentaRequest() {
        this.formaPago = "Efectivo";
        this.cantidad = 0;
        this.articulo = null;
        this.vendedor = null;
        this.nroCupon = "";
    }

    public VentaRequest(Articulo articulo, Integer cantidad, Vendedor vendedor, String formaPago, String nroCupon) {
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.vendedor = vendedor;
        this.formaPago = formaPago;
        this.nroCupon = nroCupon;
    }

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

    public String getNroCupon() {
        return nroCupon;
    }
}
