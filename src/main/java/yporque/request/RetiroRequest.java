package yporque.request;

public class RetiroRequest {

    private Double monto;
    private String descripcion;
    private String username;

    public RetiroRequest(Double monto, String descripcion, String username) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.username = username;
    }

    public RetiroRequest() {
        this.descripcion = "";
        this.monto = 0.0;
        this.username = "";
    }

    public Double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUsername() {
        return username;
    }
}
