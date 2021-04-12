package com.ias.apirest.springbootbackend.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
//Table, se utiliza para indicar si el nombre de la clase es diferente al de la base de datos
@Table(name = "clientes")
public class Cliente implements Serializable {

    //Id, indica el id de la columna, llave primaría
    //GeneratedValue, en este caso para indicar que es autoincremental con identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String apellido;
    private String email;
    //Column, se utiliza para cambiar el nombre de la propiedad e indicar los diferentes atributos que puede tener la columna
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;


    //Se crean las propiedades para setear los atributos
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private static final long serialVersionUID = 1264084683678153673L;
}
