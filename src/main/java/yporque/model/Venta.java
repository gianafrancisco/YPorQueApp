package yporque.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 13/12/2015.
 */
@Entity
@Table(name="venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "venta_id")
    private Long ventaId;
    private String descripcion;
    private Double montoTotal;
    private Instant fecha;
    private TipoDePago tipoPago;

    public Venta() {
    }

    public Venta(String descripcion, Instant fecha, TipoDePago tipoPago, Double montoTotal) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipoPago = tipoPago;
        this.montoTotal = montoTotal;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Instant getFecha() {
        return fecha;
    }

    public TipoDePago getTipoPago() {
        return tipoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
