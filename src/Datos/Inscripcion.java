package Datos;

public class Inscripcion {

    //Variables privadas
    private int idInscripcion;
    private int idEstudiante;
    private int idMateria;

    // Constructor vacío
    public Inscripcion() {
    }

    // Constructor con parámetros
    public Inscripcion(int idInscripcion, int idEstudiante, int idMateria) {
        this.idInscripcion = idInscripcion;
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }
}