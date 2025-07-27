package Ventanas;

import Datos.Estudiante;
import Usuarios.EstudianteDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class Estudiantes extends JFrame {

    // variables privadas
    private JLabel lblTitulo, lblCedula, lblNombres, lblApellidos;
    private JTextField txtCedula, txtNombres, txtApellidos;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaEstudiantes;
    private JScrollPane scrollPaneTabla;
    private DefaultTableModel modeloTabla;

    //CONSTRUCTOR
    public Estudiantes() {
        initComponents();
        configurarTabla();
        cargarEstudiantes();
        configurarListeners();
    }

    private void initComponents() {
        setTitle("Gestión de Estudiantes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        lblTitulo = new JLabel("GESTIÓN DE ESTUDIANTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        lblCedula = new JLabel("Cédula:");
        txtCedula = new JTextField();
        lblNombres = new JLabel("Nombres:");
        txtNombres = new JTextField();
        lblApellidos = new JLabel("Apellidos:");
        txtApellidos = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        tablaEstudiantes = new JTable();
        scrollPaneTabla = new JScrollPane(tablaEstudiantes);

        lblTitulo.setBounds(250, 20, 300, 30);
        lblCedula.setBounds(50, 80, 100, 25);
        txtCedula.setBounds(150, 80, 200, 25);
        lblNombres.setBounds(50, 120, 100, 25);
        txtNombres.setBounds(150, 120, 200, 25);
        lblApellidos.setBounds(50, 160, 100, 25);
        txtApellidos.setBounds(150, 160, 200, 25);
        btnGuardar.setBounds(50, 220, 100, 30);
        btnActualizar.setBounds(160, 220, 120, 30);
        btnEliminar.setBounds(290, 220, 100, 30);
        btnLimpiar.setBounds(400, 220, 100, 30);
        scrollPaneTabla.setBounds(50, 280, 700, 250);

        add(lblTitulo); add(lblCedula); add(txtCedula);
        add(lblNombres); add(txtNombres); add(lblApellidos);
        add(txtApellidos); add(btnGuardar); add(btnActualizar);
        add(btnEliminar); add(btnLimpiar); add(scrollPaneTabla);
    }

    private void configurarTabla() {
        String[] titulos = {"ID", "Cédula", "Nombres", "Apellidos"};
        modeloTabla = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaEstudiantes.setModel(modeloTabla);
    }

    private void cargarEstudiantes() {
        modeloTabla.setRowCount(0);
        EstudianteDAO dao = new EstudianteDAO();
        List<Estudiante> estudiantes = dao.leerTodos();
        for (Estudiante est : estudiantes) {
            modeloTabla.addRow(new Object[]{est.getIdEstudiante(), est.getCedula(), est.getNombres(), est.getApellidos()});
        }
    }

    private void configurarListeners() {
        btnGuardar.addActionListener(e -> guardarEstudiante());
        btnActualizar.addActionListener(e -> actualizarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        tablaEstudiantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarEstudiante();
            }
        });
    }

    //  MÉTODOS DE LÓGICA

    private void guardarEstudiante() {
        String cedula = txtCedula.getText();
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        if (cedula.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Estudiante est = new Estudiante(0, cedula, nombres, apellidos);
        EstudianteDAO dao = new EstudianteDAO();
        if (dao.crear(est)) {
            JOptionPane.showMessageDialog(this, "Estudiante guardado con éxito.");
            limpiarCampos();
            cargarEstudiantes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String cedula = txtCedula.getText();
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        Estudiante est = new Estudiante(idEstudiante, cedula, nombres, apellidos);
        EstudianteDAO dao = new EstudianteDAO();
        if (dao.actualizar(est)) {
            JOptionPane.showMessageDialog(this, "Estudiante actualizado con éxito.");
            limpiarCampos();
            cargarEstudiantes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar a este estudiante?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            EstudianteDAO dao = new EstudianteDAO();
            if (dao.eliminar(idEstudiante)) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado con éxito.");
                limpiarCampos();
                cargarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        tablaEstudiantes.clearSelection();
    }

    private void seleccionarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada != -1) {
            String cedula = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
            String nombres = modeloTabla.getValueAt(filaSeleccionada, 2).toString();
            String apellidos = modeloTabla.getValueAt(filaSeleccionada, 3).toString();
            txtCedula.setText(cedula);
            txtNombres.setText(nombres);
            txtApellidos.setText(apellidos);
        }
    }

    // para ejecutar
    public static void main(String args[]) {
        // Esto asegura que la interfaz se cree en el hilo correcto
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Estudiantes().setVisible(true);
            }
        });
    }
}