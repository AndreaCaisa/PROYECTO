package Datos;

public class Materia {
    //Variables privadas
    private int idMateria;
    private String nombreMateria;

    // Constructor vacío
    public Materia() {
    }

    // Constructor con parametros
    public Materia(int idMateria, String nombreMateria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
    }

    // Constructor para crear nueva materia
    public Materia(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}