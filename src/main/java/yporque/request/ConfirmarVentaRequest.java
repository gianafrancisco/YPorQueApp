package yporque.request;

import java.util.Collections;
import java.util.List;

/**
 * Created by francisco on 09/01/16.
 */
public class ConfirmarVentaRequest {
    private List<VentaRequest> articulos;
    private List<DevolucionRequest> devoluciones;
    private String formaPago;
    private Double efectivo;
    private Double tarjeta;
    private String dni;
    private double entregaInicial;

    public ConfirmarVentaRequest(List<VentaRequest> articulos, List<DevolucionRequest> devoluciones, String formaPago, Double efectivo, Double tarjeta, String dni, double entregaInicial) {
        this.articulos = articulos;
        this.devoluciones = devoluciones;
        this.tarjeta = tarjeta;
        this.efectivo = efectivo;
        this.formaPago = formaPago;
        this.dni = dni;
        this.entregaInicial = entregaInicial;
    }

    public List<VentaRequest> getArticulos() {
        return articulos;
    }

    public List<DevolucionRequest> getDevoluciones() {
        return devoluciones;
    }

    public ConfirmarVentaRequest() {
        this.articulos = Collections.EMPTY_LIST;
        this.devoluciones = Collections.EMPTY_LIST;
        this.formaPago = "Efectivo";
        this.efectivo = 0.0;
        this.tarjeta = 0.0;
        this.dni = "";
    }

    public String getFormaPago() {
        return formaPago;
    }

    public Double getEfectivo() {
        return efectivo;
    }

    public Double getTarjeta() {
        return tarjeta;
    }

    public String getDni() {
        return dni;
    }

    public double getEntregaInicial() {
        return entregaInicial;
    }
}
