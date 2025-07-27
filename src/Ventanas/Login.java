package Ventanas;

import Usuarios.UsuarioDAO;
import javax.swing.*;

public class Login extends JFrame {

    // Varicbles privadas
    private JPanel mainPanel;
    private JTextField txtUsuarios;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    //CONSTRUCTOR

    public Login() {
        // Configuración de la ventana
        setTitle("Inicio de Sesión");
        setContentPane(mainPanel); // ¡Esta línea conecta el diseño con el código!
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setVisible(true);

        // Lógica del botón
        btnLogin.addActionListener(e -> realizarLogin());
    }

    /**
     * Lógica que se ejecuta al hacer clic en el botón.
     */
    private void realizarLogin() {
        String usuario = txtUsuarios.getText();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese usuario y contraseña.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        String rol = dao.validarUsuario(usuario, password);

        if (rol != null) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido!");
            if (rol.equals("Administrador")) {
                new Administrador(); // Abre la ventana del Admin
            } else if (rol.equals("Profesor")) {
                new FormularioNota(); // Abre la ventana del Profesor
            }
            this.dispose(); // Cierra la ventana de login
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }

    //para ejecutar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}