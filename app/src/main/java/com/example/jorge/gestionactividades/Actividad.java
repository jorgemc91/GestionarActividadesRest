package com.example.jorge.gestionactividades;

import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;

/**
 * Created by Jorge on 15/02/2015.
 */
public class Actividad<ArrayList> implements Serializable, Parcelable{
    private String id, idProfesor, tipo, fechaI, fechaF, lugarI, lugarF, descripcion, alumno;

    public Actividad(String id, String idProfesor, String tipo, String fechaI, String fechaF, String lugarI, String lugarF, String descripcion, String alumno) {
        this.id = id;
        this.idProfesor = idProfesor;
        this.tipo = tipo;
        this.fechaI = fechaI;
        this.fechaF = fechaF;
        this.lugarI = lugarI;
        this.lugarF = lugarF;
        this.descripcion = descripcion;
        this.alumno = alumno;
    }

    public Actividad(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaI() {
        return fechaI;
    }

    public void setFechaI(String fechaI) {
        this.fechaI = fechaI;
    }

    public String getFechaF() {
        return fechaF;
    }

    public void setFechaF(String fechaF) {
        this.fechaF = fechaF;
    }

    public String getLugarI() {
        return lugarI;
    }

    public void setLugarI(String lugarI) {
        this.lugarI = lugarI;
    }

    public String getLugarF() {
        return lugarF;
    }

    public void setLugarF(String lugarF) {
        this.lugarF = lugarF;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id='" + id + '\'' +
                ", idProfesor='" + idProfesor + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaI='" + fechaI + '\'' +
                ", fechaF='" + fechaF + '\'' +
                ", lugarI='" + lugarI + '\'' +
                ", lugarF='" + lugarF + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.idProfesor);
        parcel.writeString(this.tipo);
        parcel.writeString(this.fechaI);
        parcel.writeString(this.fechaF);
        parcel.writeString(this.lugarI);
        parcel.writeString(this.lugarF);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.alumno);
    }

    public static final Parcelable.Creator<Actividad> CREATOR = new Parcelable.Creator<Actividad>(){

        @Override
        public Actividad createFromParcel(Parcel parcel) {
            String id = parcel.readString();
            String idProfesor = parcel.readString();
            String tipo = parcel.readString();
            String fechaI = parcel.readString();
            String fechaF = parcel.readString();
            String lugarI = parcel.readString();
            String lugarF = parcel.readString();
            String descripcion = parcel.readString();
            String alumno = parcel.readString();
            return new Actividad(id, idProfesor, tipo, fechaI, fechaF, lugarI, lugarF, descripcion, alumno);
        }

        @Override
        public Actividad[] newArray(int i) {
            return new Actividad[0];
        }
    };
}
