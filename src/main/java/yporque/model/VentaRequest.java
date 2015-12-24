package yporque.model;

/**
 * Created by francisco on 23/12/15.
 */
public class VentaRequest {
    private Articulo articulo;
    private Integer cantidad;
    private Vendedor vendedor;
    private String formaPago;

    public VentaRequest() {
        this.formaPago = "Efectivo";
        this.cantidad = 0;
    }

    public VentaRequest(Articulo articulo, Integer cantidad, Vendedor vendedor, String formaPago) {
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.vendedor = vendedor;
        this.formaPago = formaPago;
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
}
