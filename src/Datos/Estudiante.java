package Datos;

public class Estudiante {
    //Variables privadas
    private int idEstudiante;
    private String cedula;
    private String nombres;
    private String apellidos;

    //CONTRUCTOR VACIO PARA CREAR SIN DATOS INICIALES
    public Estudiante() {
    }

    // CONTRUCTOR CON PARAMETROS
    public Estudiante(int idEstudiante, String cedula, String nombres, String apellidos) {
        this.idEstudiante = idEstudiante;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    // Métodos Getters y Setters para acceder y modificar las variables
    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}

