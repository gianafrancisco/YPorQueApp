package yporque.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 21/11/15.
 */
@Entity
@Table(name="articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "articulo_id", nullable = false)
    private Long articuloId;
    private Double precio;
    private Double precioLista;
    private Double factor1;
    private Double factor2;
    private Double porcetaje;
    private String descripcion;

    public Articulo() {
    }

    public Articulo(Double precio, Double precioLista, Double factor1, Double factor2, Double porcetaje, String descripcion) {
        this.precio = precio;
        this.precioLista = precioLista;
        this.factor1 = factor1;
        this.factor2 = factor2;
        this.porcetaje = porcetaje;
        this.descripcion = descripcion;
        actualizarPrecio();
    }

    public Long getArticuloId() {
        return articuloId;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(Double precioLista) {
        this.precioLista = precioLista;
        actualizarPrecio();
    }

    public Double getFactor1() {
        return factor1;
    }

    public void setFactor1(Double factor1) {
        this.factor1 = factor1;
        actualizarPrecio();
    }

    public Double getFactor2() {
        return factor2;
    }

    public void setFactor2(Double factor2) {
        this.factor2 = factor2;
        actualizarPrecio();
    }

    public Double getPorcetaje() {
        return porcetaje;
    }

    public void setPorcetaje(Double porcetaje) {
        this.porcetaje = porcetaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private void actualizarPrecio(){
        precio = precioLista*factor1*factor2;
    }

}

