
package yporque.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by fgiana on 04/11/2015.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String description;
    private Integer stock;
    private Double price;

    public Product(){};

    public Product(String code, String description, Integer stock, Double price) {
        this.code=code;
        this.description=description;
        this.stock = stock;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%d, code='%s', description='%s', stock='%d', price='%d']", id,
                code, description, stock, price);
    }
}
