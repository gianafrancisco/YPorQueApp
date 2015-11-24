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
    private int articuloId;
    private float precio;
    private float precio_lista;
    private float porcetaje;
    private String descripcion;

    @OneToMany(mappedBy="articulo",targetEntity=Item.class,
            fetch=FetchType.EAGER)
    private List<Item> items;

    public Articulo(float precio, float precio_lista, float porcetaje, String descripcion) {
        this.precio = precio;
        this.precio_lista = precio_lista;
        this.porcetaje = porcetaje;
        this.descripcion = descripcion;
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecio_lista() {
        return precio_lista;
    }

    public void setPrecio_lista(float precio_lista) {
        this.precio_lista = precio_lista;
    }

    public float getPorcetaje() {
        return porcetaje;
    }

    public void setPorcetaje(float porcetaje) {
        this.porcetaje = porcetaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
