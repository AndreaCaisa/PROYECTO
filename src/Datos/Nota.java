package Datos;

import java.sql.Timestamp;

public class Nota {
    //Variables privadas
    private int idNota;
    private int idInscripcion; // Clave para saber a quién y a qué materia pertenece
    private String descripcion;
    private double calificacion;
    private Timestamp fechaRegistro;

    // Constructor vacío
    public Nota() {
    }

    // Constructor con parámetros
    public Nota(int idNota, int idInscripcion, String descripcion, double calificacion, Timestamp fechaRegistro) {
        this.idNota = idNota;
        this.idInscripcion = idInscripcion;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.fechaRegistro = fechaRegistro;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
}