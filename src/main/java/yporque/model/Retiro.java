package yporque.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by francisco on 30/12/15.
 */
@Entity
@Table(name = "retiro")
public class Retiro {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="retiro_id")
    private Long retiroId;
    private Double monto;
    private String descripcion;
    private Instant fecha;
    private String username;

    public Retiro() {
        this.monto = 0.0;
        this.descripcion = "";
        this.fecha = Instant.EPOCH;
        this.username = "";
    }

    public Retiro(Double monto, String descripcion, Instant fecha, String username) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.username = username;
    }

    public Double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Instant getFecha() {
        return fecha;
    }

    public String getUsername() {
        return username;
    }
}
