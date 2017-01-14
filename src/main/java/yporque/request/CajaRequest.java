package yporque.request;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by francisco on 30/12/15.
 */
public class CajaRequest {

    private String username;
    private Instant fecha;
    private Double efectivoDisponible;

    public CajaRequest(String username, Instant fecha, Double efectivoDisponible) {
        this.username = username;
        this.fecha = fecha;
        this.efectivoDisponible = efectivoDisponible;
    }

    public CajaRequest() {
        this.username = "";
        this.fecha = LocalDateTime.now().toInstant(ZoneOffset.ofHours(-3));
        this.efectivoDisponible = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Double getEfectivoDisponible() {
        return efectivoDisponible;
    }
}
