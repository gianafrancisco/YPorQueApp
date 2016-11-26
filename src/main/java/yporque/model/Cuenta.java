package yporque.model;

import javax.persistence.*;

/**
 * Created by francisco on 19/11/2016.
 */
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cuenta_id", unique = true, nullable = false)
    private long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private long dni;
    @Transient
    private double saldo;

    public Cuenta() {
        this.saldo = 0.0;
        this.apellido = "";
        this.nombre = "";
        this.dni = 0L;
        this.email = "";
        this.telefono = "";
    }

    public Cuenta(String nombre, String apellido, String telefono, String email, long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.dni = dni;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public long getDni() {
        return dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
}
