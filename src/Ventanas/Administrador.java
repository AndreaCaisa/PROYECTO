package Ventanas;

import javax.swing.*;
import java.awt.*;

public class Administrador extends JFrame {

    //variables privadas
    private JButton btnEstudiante;
    private JButton btnMateria;
    private JButton btnInscripcion;
    private JButton btnUsuarios;
    private JButton btnCerrarSesion;
    private JButton btnNota;

    //CONSTRUCTOR
    public Administrador() {
        // Llama a los métodos para construir y configurar la ventana
        initComponents();
        configurarListeners();
    }

    //Crea y posiciona todos los componentes visuales de la ventana.

    private void initComponents() {
        //  ventana principal
        setTitle("Panel de Administración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra toda la app al cerrar esta ventana
        setSize(400, 500); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new GridLayout(7, 1, 10, 10)); // organizar los botones verticalmente


        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Creación de los componentes ---
        JLabel lblTitulo = new JLabel("Panel de Control", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        btnEstudiante = new JButton("Estudiantes");
        btnMateria = new JButton("Materias");
        btnNota = new JButton("Notas");
        btnInscripcion = new JButton("Inscripciones de alumno a materia");
        btnUsuarios = new JButton("Gestion Usuarios");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Estilo para los botones
        Font botonFont = new Font("Arial", Font.PLAIN, 16);
        btnEstudiante.setFont(botonFont);
        btnMateria.setFont(botonFont);
        btnNota.setFont(botonFont);
        btnInscripcion.setFont(botonFont);
        btnUsuarios.setFont(botonFont);
        btnCerrarSesion.setFont(botonFont);
        btnCerrarSesion.setBackground(new Color(220, 53, 69)); // Color rojo
        btnCerrarSesion.setForeground(Color.WHITE);

        // --- Añadir componentes a la ventana ---
        add(lblTitulo);
        add(btnEstudiante);
        add(btnMateria);
        add(btnNota);
        add(btnInscripcion);
        add(btnUsuarios);
        add(btnCerrarSesion);

        // Hace visible la ventana al final
        setVisible(true);
    }

    // si da click aparece esta ventana
    private void configurarListeners() {
        btnEstudiante.addActionListener(e -> new Estudiantes().setVisible(true));
        btnMateria.addActionListener(e -> new FormularioMateria().setVisible(true));
        btnNota.addActionListener(e -> new FormularioNota().setVisible(true));
        btnInscripcion.addActionListener(e -> new Inscripcion().setVisible(true));
        btnUsuarios.addActionListener(e -> new Usuarios().setVisible(true));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

    }

    //Cierra la sesión actual y vuelve a la ventana de Login.
    private void cerrarSesion() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres cerrar sesión?",
                "Confirmar Cierre",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            new Login(); // Crea y muestra la nueva ventana de Login
            this.dispose();    // Cierra la ventana actual de Administrador
        }
    }
    //para ejeccutar
    public static void main(String[] args) {
        // Esto asegura que la ventana se cree en el hilo correcto de Swing
        SwingUtilities.invokeLater(() -> new Administrador());
    }
}