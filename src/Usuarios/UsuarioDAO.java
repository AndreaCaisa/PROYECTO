package Usuarios;

import ConexionSQL.ConexionBD;
import Datos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement; //para ejecutar consultas con sql
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    //busca que coincida el usuario y contrasena
    public String validarUsuario(String user, String pass) {
        String sql = "SELECT rol FROM usuarios WHERE nombreUsuario = ? AND password = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             //asigna valores
            pstmt.setString(1, user);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();

            // Si encuentra una fila, devuelve el rol de esa fila.
            if (rs.next()) {
                return rs.getString("rol");
            }

        } catch (SQLException e) {
            // Imprime el error
            System.out.println("Error al validar usuario: " + e.getMessage());
        }
        return null;
    }

    //Crea un nuevo usuario.

    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombreUsuario, password, rol) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
              //asignar valores
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    //Obtiene la lista de todos los usuarios.

    public List<Usuario> leerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Crea un objeto Usuario por cada fila y lo añade a la lista
                usuarios.add(new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombreUsuario"),
                        rs.getString("password"),
                        rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    //Actualiza un usuario existente.

    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombreUsuario = ?, password = ?, rol = ? WHERE idUsuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol());
            pstmt.setInt(4, usuario.getIdUsuario());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    //Elimina un usuario por su ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
