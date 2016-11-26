package yporque.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by francisco on 11/22/16.
 */
@Entity
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="movimiento_id", unique = true, nullable = false)
    private long id;
    private long cuentaId;
    private String codigo;
    private String descripcion;
    private double importe;
    private Instant fecha;

    public static Movimiento generarEntrega(Instant fecha, String descripcion, long cuentaId, double monto){
        return new Movimiento(fecha, cuentaId, "ENTREGA", descripcion, monto);
    }

    public Movimiento(Instant fecha, long cuentaId, String codigo, String descripcion, double importe) {
        this.cuentaId = cuentaId;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.importe = importe;
        this.fecha = fecha;
    }

    public Movimiento() {
    }

    public long getId() {
        return id;
    }

    public long getCuentaId() {
        return cuentaId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public void setCuentaId(long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Instant getFecha() {
        return fecha;
    }
}
