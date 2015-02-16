package com.example.jorge.gestionactividades;

/**
 * Created by Jorge on 15/02/2015.
 */
public class Profesor<ArrayList> {
    private String id, nombre;

    public Profesor(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Profesor(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
