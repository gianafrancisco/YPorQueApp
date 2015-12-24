package yporque.model;

import javax.persistence.*;

/**
 * Created by francisco on 12/12/2015.
 */
@Entity
@Table(name="vendedor")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vendedor_id", nullable = false)
    private Long vendedorId;

    private String username;
    private String password;
    private String nombre;
    private String apellido;

    public Vendedor() {
        this.username = "";
        this.nombre = "";
        this.apellido = "";
    }

    public Vendedor(String username, String password, String nombre, String apellido) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[ id=").append(vendedorId)
                .append(", nombre=").append(nombre).append(", apellido=").append(apellido)
                .append(", username=").append(username).append(", password=").append(password).append(" ]").toString();
    }
}
