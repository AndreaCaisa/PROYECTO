package Ventanas;

import Datos.Materia;
import Usuarios.MateriaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class FormularioMateria extends JFrame {
    private JPanel panelMateria;
    private JTextField txtNombreMateria;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnBorrar;
    private JButton btnLimpiar;
    private JTable tablaMaterias;

    private DefaultTableModel modeloTabla;
    private int idMateriaSeleccionada = -1;

    public FormularioMateria() {
        setTitle("Gestión de Materias");
        setContentPane(panelMateria);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        configurarTabla();
        cargarMaterias();

        // Acciones de los botones
        btnGuardar.addActionListener(e -> guardarMateria());
        btnActualizar.addActionListener(e -> actualizarMateria());
        btnBorrar.addActionListener(e -> borrarMateria());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Al hacer clic en una fila, cargar datos en el formulario
        tablaMaterias.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaMaterias.getSelectedRow();
                if (fila >= 0) {
                    idMateriaSeleccionada = (int) modeloTabla.getValueAt(fila, 0);
                    String nombre = (String) modeloTabla.getValueAt(fila, 1);
                    txtNombreMateria.setText(nombre);
                }
            }
        });
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no se puedan editar directamente
            }
        };
        tablaMaterias.setModel(modeloTabla);
    }

    private void cargarMaterias() {
        modeloTabla.setRowCount(0); // Limpia la tabla
        MateriaDAO dao = new MateriaDAO();
        List<Materia> materias = dao.leerTodos();

        for (Materia mat : materias) {
            modeloTabla.addRow(new Object[]{mat.getIdMateria(), mat.getNombreMateria()});
        }
    }

    private void guardarMateria() {
        String nombre = txtNombreMateria.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la materia no puede estar vacío.");
            return;
        }

        Materia nueva = new Materia(nombre);
        MateriaDAO dao = new MateriaDAO();

        if (dao.crear(nueva)) {
            JOptionPane.showMessageDialog(this, "Materia registrada exitosamente.");
            cargarMaterias();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la materia.");
        }
    }

    private void actualizarMateria() {
        if (idMateriaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una materia de la tabla para actualizar.");
            return;
        }

        String nombre = txtNombreMateria.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return;
        }

        Materia actualizada = new Materia(idMateriaSeleccionada, nombre);
        MateriaDAO dao = new MateriaDAO();

        if (dao.actualizar(actualizada)) {
            JOptionPane.showMessageDialog(this, "Materia actualizada.");
            cargarMaterias();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la materia.");
        }
    }

    private void borrarMateria() {
        if (idMateriaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una materia de la tabla para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta materia?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            MateriaDAO dao = new MateriaDAO();
            if (dao.eliminar(idMateriaSeleccionada)) {
                JOptionPane.showMessageDialog(this, "Materia eliminada.");
                cargarMaterias();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la materia.");
            }
        }
    }

    private void limpiarCampos() {
        txtNombreMateria.setText("");
        idMateriaSeleccionada = -1;
        tablaMaterias.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormularioMateria::new);
    }
}