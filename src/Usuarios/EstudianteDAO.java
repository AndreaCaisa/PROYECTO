package Usuarios;

import ConexionSQL.ConexionBD;  //se crea conexion con la bd
import Datos.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// el dao es por dato access object para conectar con la bd
public class EstudianteDAO {


    public boolean crear(Estudiante estudiante) {
        // instrucción SQL con marcadores de posición (?)
        String sql = "INSERT INTO estudiantes (cedula, nombres, apellidos) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();//se obtiene la conexion a la bd
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  //se prepara la instruccion
          //se reemplaza los ?? con datos del estudiante
            pstmt.setString(1, estudiante.getCedula());
            pstmt.setString(2, estudiante.getNombres());
            pstmt.setString(3, estudiante.getApellidos());

            pstmt.executeUpdate();// se guarda en bd
            return true;

        } catch (SQLException e) {
            System.out.println("Error al crear estudiante: " + e.getMessage());
            return false;
        }
    }
    // creo una lista para guardar los estudiantes
    public List<Estudiante> leerTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes ORDER BY apellidos, nombres";
     //resultados con bd
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                estudiantes.add(new Estudiante(
                        rs.getInt("idEstudiante"),
                        rs.getString("cedula"),
                        rs.getString("nombres"),
                        rs.getString("apellidos")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }
//para actualizar al estudiante
    public boolean actualizar(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET cedula = ?, nombres = ?, apellidos = ? WHERE idEstudiante = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, estudiante.getCedula());
            pstmt.setString(2, estudiante.getNombres());
            pstmt.setString(3, estudiante.getApellidos());
            pstmt.setInt(4, estudiante.getIdEstudiante());//esto se une al where
           // se guarda actualizacion
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

   //para eliminar estudiante
    public boolean eliminar(int id) {
        String sql = "DELETE FROM estudiantes WHERE idEstudiante = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
           //guarda la eliminacion
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
}
