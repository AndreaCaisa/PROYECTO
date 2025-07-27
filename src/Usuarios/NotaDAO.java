package Usuarios;

import ConexionSQL.ConexionBD;
import Datos.Nota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    // Crear una nueva nota en bd
    public boolean crearNota(Nota nota) {
        // insertar informacion en la tabla
        String sql = "INSERT INTO notas (idInscripcion, descripcion, calificacion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            //asignacion de valores
            stmt.setInt(1, nota.getIdInscripcion());
            stmt.setString(2, nota.getDescripcion());
            stmt.setDouble(3, nota.getCalificacion());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al crear nota: " + e.getMessage());
            return false;
        }
    }

    // Leer todas las notas por Inscripcion y crea la lista para guardar las notas
    public List<Nota> leerNotasPorInscripcion(int idInscripcion) {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT * FROM notas WHERE idInscripcion = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idInscripcion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //aqui se llena la informacion por cada fila con datos
                Nota nota = new Nota();
                nota.setIdNota(rs.getInt("idNota"));
                nota.setIdInscripcion(rs.getInt("idInscripcion"));
                nota.setDescripcion(rs.getString("descripcion"));
                nota.setCalificacion(rs.getDouble("calificacion"));
                lista.add(nota);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer notas: " + e.getMessage());
        }

        return lista;
    }

    // Actualizar una nota
    public boolean actualizarNota(Nota nota) {
        String sql = "UPDATE notas SET descripcion = ?, calificacion = ? WHERE idNota = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nota.getDescripcion());
            stmt.setDouble(2, nota.getCalificacion());
            stmt.setInt(3, nota.getIdNota());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar nota: " + e.getMessage());
            return false;
        }
    }

    // Eliminar una nota por ID
    public boolean eliminarNota(int idNota) {
        String sql = "DELETE FROM notas WHERE idNota = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNota);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar nota: " + e.getMessage());
            return false;
        }
    }
    public boolean inscribirEstudiante(int idEstudiante, int idMateria) {
        String sql = "INSERT INTO inscripciones (idEstudiante, idMateria) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEstudiante);
            pstmt.setInt(2, idMateria);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al inscribir estudiante: " + e.getMessage());
            return false;
        }
    }
    // buscar ID de inscripción según estudiante y materia
    public int obtenerIdInscripcion(int idEstudiante, int idMateria) {
        String sql = "SELECT idInscripcion FROM inscripciones WHERE idEstudiante = ? AND idMateria = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
         // asigna los ids
            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("idInscripcion");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener ID de inscripción: " + e.getMessage());
        }
     //Si no se encontró la inscripción o hubo un error, devolver -1.
        return -1; // No encontrado
    }
}