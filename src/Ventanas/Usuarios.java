package Ventanas;

import Datos.Usuario;
import Usuarios.UsuarioDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Formulario para la gestión (CRUD) de Usuarios.
 */
public class Usuarios extends JFrame {

    // --- 1. DECLARACIONES ---
    private JPanel mainPanel;
    private JTextField txtNombreUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRol;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnBorrar;
    private JButton btnLimpiar;
    private JTable tablaUsuarios;

    private DefaultTableModel modeloTabla;

    //CONSTRUCTOR de la ventana.
    public Usuarios() {
        setTitle("Gestión de Usuarios");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        configurarTabla();
        cargarRoles();
        cargarUsuarios();
        configurarListeners();
    }

    private void configurarTabla() {
        String[] titulos = {"ID", "Nombre de Usuario", "Rol"}; // No mostramos la contraseña en la tabla por seguridad
        modeloTabla = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaUsuarios.setModel(modeloTabla);
    }

    //Llenado del ComboBox con los roles disponibles.
    private void cargarRoles() {
        comboRol.addItem("Administrador");
        comboRol.addItem("Profesor");
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.leerTodos();
        for (Usuario usr : usuarios) {
            modeloTabla.addRow(new Object[]{usr.getIdUsuario(), usr.getNombreUsuario(), usr.getRol()});
        }
    }

    private void configurarListeners() {
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnBorrar.addActionListener(e -> eliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaUsuarios.addMouseListener(new MouseAdapter() {
            @Override //
            public void mouseClicked(MouseEvent e) {
                seleccionarUsuario();
            }
        });
    }

    private void limpiarCampos() {
        txtNombreUsuario.setText("");
        txtPassword.setText("");
        comboRol.setSelectedIndex(0);
        tablaUsuarios.clearSelection();
    }

    private void seleccionarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            txtNombreUsuario.setText(modeloTabla.getValueAt(fila, 1).toString());
            comboRol.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
            // Dejamos el campo de contraseña en blanco por seguridad.
            // Si se quiere cambiar, se debe escribir una nueva.
            txtPassword.setText("");
        }
    }

    private void guardarUsuario() {
        String nombreUsuario = txtNombreUsuario.getText();
        String password = new String(txtPassword.getPassword());
        String rol = (String) comboRol.getSelectedItem();

        if (nombreUsuario.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario y la contraseña son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usr = new Usuario(0, nombreUsuario, password, rol);
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.crear(usr)) {
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente.");
            cargarUsuarios();

            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario. Es posible que el nombre ya exista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para actualizar.", "Sin Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
        String nombreUsuario = txtNombreUsuario.getText();
        String password = new String(txtPassword.getPassword());
        String rol = (String) comboRol.getSelectedItem();

        // Si la contraseña está en blanco, significa que el admin no quiere cambiarla.
        // En una aplicación real, aquí iría una lógica más compleja para no sobreescribir con un campo vacío.
        // Para este proyecto, asumiremos que si se actualiza, la contraseña también debe escribirse.
        if (password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Para actualizar un usuario, debe proporcionar una contraseña.", "Contraseña Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usr = new Usuario(idUsuario, nombreUsuario, password, rol);
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.actualizar(usr)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            cargarUsuarios();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para eliminar.", "Sin Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este usuario?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.eliminar(idUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                cargarUsuarios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método Main para ejecutar esta ventana de forma independiente.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Usuarios());
    }
}
