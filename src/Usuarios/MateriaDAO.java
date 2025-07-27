package Usuarios;

import ConexionSQL.ConexionBD;
import Datos.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    public boolean crear(Materia materia) {
        String sql = "INSERT INTO materias (nombreMateria) VALUES (?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, materia.getNombreMateria());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al crear materia: " + e.getMessage());
            return false;
        }
    }

    //Lee todas las materias desde la base de datos.

    public List<Materia> leerTodos() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materias ORDER BY nombreMateria";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materias.add(new Materia(
                        rs.getInt("idMateria"),
                        rs.getString("nombreMateria")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer materias: " + e.getMessage());
        }
        return materias;
    }

     //Actualiza una materia (sin descripción).

    public boolean actualizar(Materia materia) {
        String sql = "UPDATE materias SET nombreMateria = ? WHERE idMateria = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, materia.getNombreMateria());
            pstmt.setInt(2, materia.getIdMateria());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar materia: " + e.getMessage());
            return false;
        }
    }

    //Elimina una materia por ID.

    public boolean eliminar(int id) {
        String sql = "DELETE FROM materias WHERE idMateria = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar materia: " + e.getMessage());
            return false;
        }
    }
}