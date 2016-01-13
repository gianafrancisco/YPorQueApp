package yporque.model;

import javax.persistence.*;

/**
 * Created by francisco on 21/11/15.
 */
@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id", unique = true, nullable = false)
    private Long itemId;
    @Column(name="articulo_id", nullable = false)
    private Long articuloId;
    private String codigo;
    private Integer cantidad;
    private Integer cantidadStock;
    @Column(name="venta_id", nullable = true)
    private Long ventaId;

    public Item() {
        this.articuloId = 0L;
        this.codigo = "";
        this.cantidad = 0;
        this.cantidadStock = 0;
        this.ventaId = 0L;
    }

    public Item(String codigo, Integer cantidad, Long articuloId) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.cantidadStock = this.cantidad;
        this.articuloId = articuloId;

    }

    public Long getArticuloId() {
        return articuloId;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


}
