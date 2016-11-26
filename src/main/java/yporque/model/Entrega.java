package yporque.model;

/**
 * Created by francisco on 11/26/16.
 */
public class Entrega {
    private String descripcion;
    private double monto;


    public Entrega() {
    }

    public Entrega(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }
}
