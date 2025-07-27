package Ventanas;

import Datos.Estudiante;
import Datos.Materia;
import Usuarios.EstudianteDAO;
import Usuarios.MateriaDAO;
import Usuarios.NotaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inscripcion extends JFrame {

    private JComboBox<String> comboEstudiantes;
    private JComboBox<String> comboMaterias;
    private JButton btnInscribir;

    private Map<String, Integer> mapaEstudiantes = new HashMap<>();
    private Map<String, Integer> mapaMaterias = new HashMap<>();

    public Inscripcion() {
        setTitle("Inscribir Estudiante en Materia");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel para los combos
        JPanel panelCampos = new JPanel(new GridLayout(4, 1, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelCampos.add(new JLabel("Selecciona un estudiante:"));
        comboEstudiantes = new JComboBox<>();
        panelCampos.add(comboEstudiantes);

        panelCampos.add(new JLabel("Selecciona una materia:"));
        comboMaterias = new JComboBox<>();
        panelCampos.add(comboMaterias);

        add(panelCampos, BorderLayout.CENTER);

        // Botón en parte inferior
        btnInscribir = new JButton("Inscribir");
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnInscribir);
        add(panelBoton, BorderLayout.SOUTH);

        cargarEstudiantes();
        cargarMaterias();

        btnInscribir.addActionListener(e -> inscribirEstudiante());

        setVisible(true);
    }

    private void cargarEstudiantes() {
        EstudianteDAO dao = new EstudianteDAO();
        List<Estudiante> estudiantes = dao.leerTodos();

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement("Seleccione un estudiante...");
        mapaEstudiantes.clear();

        for (Estudiante est : estudiantes) {
            String nombreCompleto = est.getNombres() + " " + est.getApellidos();
            modelo.addElement(nombreCompleto);
            mapaEstudiantes.put(nombreCompleto, est.getIdEstudiante());
        }

        comboEstudiantes.setModel(modelo);
    }

    private void cargarMaterias() {
        MateriaDAO dao = new MateriaDAO();
        List<Materia> materias = dao.leerTodos();

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement("Seleccione una materia...");
        mapaMaterias.clear();

        for (Materia mat : materias) {
            String nombre = mat.getNombreMateria();
            modelo.addElement(nombre);
            mapaMaterias.put(nombre, mat.getIdMateria());
        }

        comboMaterias.setModel(modelo);
    }

    private void inscribirEstudiante() {
        String estudianteSeleccionado = (String) comboEstudiantes.getSelectedItem();
        String materiaSeleccionada = (String) comboMaterias.getSelectedItem();

        if ("Seleccione un estudiante...".equals(estudianteSeleccionado) || "Seleccione una materia...".equals(materiaSeleccionada)) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante y una materia.", "Selección Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEstudiante = mapaEstudiantes.get(estudianteSeleccionado);
        int idMateria = mapaMaterias.get(materiaSeleccionada);

        NotaDAO dao = new NotaDAO();
        if (dao.inscribirEstudiante(idEstudiante, idMateria)) {
            JOptionPane.showMessageDialog(this, "¡Estudiante inscrito correctamente en la materia!");
            this.dispose(); // Cierra la ventana después de inscribir
        } else {
            JOptionPane.showMessageDialog(this, "Error: El estudiante ya está inscrito en esta materia o hubo un problema.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Inscripcion::new);
    }
}