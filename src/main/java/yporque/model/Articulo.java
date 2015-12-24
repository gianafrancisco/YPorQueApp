package yporque.model;

import javax.persistence.*;

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
    private String descripcion;
    private Integer cantidad;
    private Integer cantidadStock;
    private String codigo;


    public Articulo() {
        this.precio = 0.0;
        this.precioLista = 0.0;
        this.factor1 = 1.0;
        this.factor2 = 1.0;
        this.descripcion = "";
        this.codigo = "";
        this.cantidad = 0;
        this.cantidadStock = 0;
    }

    public Articulo(String codigo, String descripcion, Double precioLista, Double factor1, Double factor2, Integer cantidad, Integer cantidadStock) {
        this.codigo = codigo;
        this.precioLista = precioLista;
        this.factor1 = factor1;
        this.factor2 = factor2;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.cantidad = cantidad;
        actualizarPrecio();
    }

    public Long getArticuloId() {
        return articuloId;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private void actualizarPrecio(){
        precio = Math.ceil(precioLista*factor1*factor2*10)/10;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getCodigo() {
        return codigo;
    }

}

