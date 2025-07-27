package Ventanas;

import Datos.Nota;
import Usuarios.EstudianteDAO;
import Usuarios.MateriaDAO;
import Usuarios.NotaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormularioNota extends JFrame {

    private JComboBox<String> comboMateria;
    private JComboBox<String> comboEstudiante;
    private JTable tablaNotas;
    private JTextField txtDescripcion;
    private JTextField txtCalificacion;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    // Mapas para relacionar nombre con id
    private Map<String, Integer> mapaMaterias = new HashMap<>();
    private Map<String, Integer> mapaEstudiantes = new HashMap<>();

    private DefaultTableModel modeloTabla;

    public FormularioNota() {
        setTitle("Registro de Notas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panelPrincipal);

        // --- PANEL SUPERIOR: título y combos ---
        JPanel panelSuperior = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Registro de Notas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(lblTitulo, BorderLayout.PAGE_START);

        // Panel combos: etiquetas + combos
        JPanel panelCombos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCombos.add(new JLabel("Seleccione Materia:"));
        comboMateria = new JComboBox<>();
        panelCombos.add(comboMateria);

        panelCombos.add(new JLabel("Seleccione Estudiante:"));
        comboEstudiante = new JComboBox<>();
        panelCombos.add(comboEstudiante);

        panelSuperior.add(panelCombos, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.PAGE_START);

        // --- PANEL CENTRAL: tabla notas ---
        modeloTabla = new DefaultTableModel(new String[]{"ID Nota", "Descripción", "Calificación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla solo lectura
            }
        };
        tablaNotas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaNotas);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // --- PANEL INFERIOR: campos y botones ---
        JPanel panelInferior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInferior.add(new JLabel("Descripción:"), gbc);

        txtDescripcion = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panelInferior.add(txtDescripcion, gbc);

        // Calificación
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelInferior.add(new JLabel("Calificación:"), gbc);

        txtCalificacion = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panelInferior.add(txtCalificacion, gbc);

        // Botones: Guardar, Actualizar, Eliminar
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        panelInferior.add(panelBotones, gbc);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Cargar datos en combos y tabla
        cargarMaterias();
        cargarEstudiantes();

        // Listeners
        comboMateria.addActionListener(e -> cargarNotas());
        comboEstudiante.addActionListener(e -> cargarNotas());

        btnGuardar.addActionListener(e -> guardarNota());
        btnActualizar.addActionListener(e -> actualizarNota());
        btnEliminar.addActionListener(e -> eliminarNota());

        tablaNotas.getSelectionModel().addListSelectionListener(e -> seleccionarNota());

        setVisible(true);
    }

    private void cargarMaterias() {
        mapaMaterias.clear();
        MateriaDAO dao = new MateriaDAO();
        List<Datos.Materia> materias = dao.leerTodos();

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement("Seleccione una materia...");
        for (Datos.Materia m : materias) {
            modelo.addElement(m.getNombreMateria());
            mapaMaterias.put(m.getNombreMateria(), m.getIdMateria());
        }
        comboMateria.setModel(modelo);
    }

    private void cargarEstudiantes() {
        mapaEstudiantes.clear();
        EstudianteDAO dao = new EstudianteDAO();
        List<Datos.Estudiante> estudiantes = dao.leerTodos();

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement("Seleccione un estudiante...");
        for (Datos.Estudiante est : estudiantes) {
            String nombreCompleto = est.getNombres() + " " + est.getApellidos();
            modelo.addElement(nombreCompleto);
            mapaEstudiantes.put(nombreCompleto, est.getIdEstudiante());
        }
        comboEstudiante.setModel(modelo);
    }

    private void cargarNotas() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);

        String matSeleccionada = (String) comboMateria.getSelectedItem();
        String estSeleccionado = (String) comboEstudiante.getSelectedItem();

        if (matSeleccionada == null || estSeleccionado == null ||
                matSeleccionada.equals("Seleccione una materia...") ||
                estSeleccionado.equals("Seleccione un estudiante...")) {
            return; // No hacer nada si no están seleccionados
        }

        int idMateria = mapaMaterias.get(matSeleccionada);
        int idEstudiante = mapaEstudiantes.get(estSeleccionado);

        NotaDAO notaDAO = new NotaDAO();
        int idInscripcion = notaDAO.obtenerIdInscripcion(idEstudiante, idMateria);
        if (idInscripcion == -1) {
            // No hay inscripción, no mostrar notas
            return;
        }

        List<Nota> notas = notaDAO.leerNotasPorInscripcion(idInscripcion);
        for (Nota nota : notas) {
            modeloTabla.addRow(new Object[]{
                    nota.getIdNota(),
                    nota.getDescripcion(),
                    nota.getCalificacion()
            });
        }
    }

    private void guardarNota() {
        String descripcion = txtDescripcion.getText().trim();
        String califStr = txtCalificacion.getText().trim();

        if (descripcion.isEmpty() || califStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double calificacion;
        try {
            calificacion = Double.parseDouble(califStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una calificación válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String matSeleccionada = (String) comboMateria.getSelectedItem();
        String estSeleccionado = (String) comboEstudiante.getSelectedItem();

        if (matSeleccionada == null || estSeleccionado == null ||
                matSeleccionada.equals("Seleccione una materia...") ||
                estSeleccionado.equals("Seleccione un estudiante...")) {
            JOptionPane.showMessageDialog(this, "Seleccione materia y estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idMateria = mapaMaterias.get(matSeleccionada);
        int idEstudiante = mapaEstudiantes.get(estSeleccionado);

        NotaDAO notaDAO = new NotaDAO();
        int idInscripcion = notaDAO.obtenerIdInscripcion(idEstudiante, idMateria);
        if (idInscripcion == -1) {
            JOptionPane.showMessageDialog(this, "El estudiante no está inscrito en la materia.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Nota nota = new Nota(0, idInscripcion, descripcion, calificacion, null);

        if (notaDAO.crearNota(nota)) {
            JOptionPane.showMessageDialog(this, "Nota guardada correctamente.");
            cargarNotas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarNota() {
        int fila = tablaNotas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una nota para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String descripcion = txtDescripcion.getText().trim();
        String califStr = txtCalificacion.getText().trim();

        if (descripcion.isEmpty() || califStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double calificacion;
        try {
            calificacion = Double.parseDouble(califStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una calificación válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idNota = (int) modeloTabla.getValueAt(fila, 0);

        NotaDAO notaDAO = new NotaDAO();
        Nota nota = new Nota(idNota, 0, descripcion, calificacion, null);

        if (notaDAO.actualizarNota(nota)) {
            JOptionPane.showMessageDialog(this, "Nota actualizada correctamente.");
            cargarNotas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarNota() {
        int fila = tablaNotas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una nota para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la nota?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int idNota = (int) modeloTabla.getValueAt(fila, 0);

        NotaDAO notaDAO = new NotaDAO();
        if (notaDAO.eliminarNota(idNota)) {
            JOptionPane.showMessageDialog(this, "Nota eliminada correctamente.");
            cargarNotas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarNota() {
        int fila = tablaNotas.getSelectedRow();
        if (fila == -1) return;

        String descripcion = (String) modeloTabla.getValueAt(fila, 1);
        Object califObj = modeloTabla.getValueAt(fila, 2);
        String calificacion = califObj != null ? califObj.toString() : "";

        txtDescripcion.setText(descripcion);
        txtCalificacion.setText(calificacion);
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtCalificacion.setText("");
        tablaNotas.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormularioNota::new);
    }
}