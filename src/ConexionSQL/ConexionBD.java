package ConexionSQL;

import java.sql.Connection;
import java.sql.DriverManager; //conecta con drive
import java.sql.SQLException;   // maneja errores

public class ConexionBD {
    //url para la conexion de bd
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_de_notas_escolares?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";//superusuario por defecto
    private static final String PASSWORD = ""; // En XAMPP por defecto

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // establece conexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se encontró el driver de MySQL. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: No se pudo conectar a la Base de Datos. " + e.getMessage());
        }
        return connection;
    }
}