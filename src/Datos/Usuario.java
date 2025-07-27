package Datos;

public class Usuario {

    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private String rol;

    // Constructor vacío
    public Usuario() {
    }

    //CONTRUCTOR VACIO PARA CREAR SIN DATOS INICIALES
    public Usuario(int idUsuario, String nombreUsuario, String password, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
