package yporque.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by francisco on 30/12/15.
 */
@Entity
@Table(name = "caja")
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="caja_id")
    private Long cajaId;
    private Instant apertura;
    private String aperturaUsername;
    private Instant cierre;
    private String cierreUsername;
    private Double efectivo;
    private Double tarjeta;
    private Double totalVentaDia;
    private Double efectivoDiaSiguiente;

    public Caja() {
        this.apertura = Instant.now();
        this.cierre = Instant.EPOCH;
        this.efectivo = 0.0;
        this.tarjeta = 0.0;
        this.totalVentaDia = 0.0;
        this.efectivoDiaSiguiente = 0.0;
        this.aperturaUsername = "";
        this.cierreUsername = "";
    }

    public Caja(Instant apertura, String aperturaUsername) {
        this.apertura = apertura;
        this.aperturaUsername = aperturaUsername;
        this.cierreUsername = "";
        this.cierre = Instant.EPOCH;
        this.efectivo = 0.0;
        this.tarjeta = 0.0;
        this.totalVentaDia = 0.0;
        this.efectivoDiaSiguiente = 0.0;
    }

    public Instant getCierre() {
        return cierre;
    }

    public void setCierre(Instant cierre) {
        this.cierre = cierre;
    }

    public Double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Double efectivo) {
        this.efectivo = efectivo;
    }

    public Double getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Double tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Double getTotalVentaDia() {
        return totalVentaDia;
    }

    public void setTotalVentaDia(Double totalVentaDia) {
        this.totalVentaDia = totalVentaDia;
    }

    public Double getEfectivoDiaSiguiente() {
        return efectivoDiaSiguiente;
    }

    public void setEfectivoDiaSiguiente(Double efectivoDiaSiguiente) {
        this.efectivoDiaSiguiente = efectivoDiaSiguiente;
    }

    public Instant getApertura() {
        return apertura;
    }

    public String getAperturaUsername() {
        return aperturaUsername;
    }

    public String getCierreUsername() {
        return cierreUsername;
    }

    public void setCierreUsername(String cierreUsername) {
        this.cierreUsername = cierreUsername;
    }

    public Long getCajaId() {
        return cajaId;
    }

}
