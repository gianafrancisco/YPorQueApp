package yporque.model;

/**
 * Created by francisco on 11/26/16.
 */
public class Entrega {
    private String descripcion;
    private double monto;
    private String username;

    public Entrega() {
    }

    public Entrega(String descripcion, double monto, String username) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.username = username;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public String getUsername() {
        return username;
    }
}
