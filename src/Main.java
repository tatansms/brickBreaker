import controlador.ControladorPelota;
import modelo.*;
import vista.PanelPelota;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame marco = new JFrame("Brick Breaker");
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setSize(800, 600);

        // Layout para gestionar las pantallas
        CardLayout cardLayout = new CardLayout();
        JPanel contenedorPaneles = new JPanel(cardLayout);



        // Pantalla de inicio
        JPanel pantallaInicio = new FondoPanel("C:/Users/Sebastian/IdeaProjects/brickBreaker/src/resources/imagenes/fondo/minecraft-gif-icegif-6.gif");

        pantallaInicio.setLayout(new BorderLayout());


        // Panel superior con el botón y la selección de nivel
        JPanel panelSuperior = new JPanel(new BorderLayout());

        JButton botonIniciar = new JButton("Iniciar Juego");
        panelSuperior.add(botonIniciar, BorderLayout.SOUTH);

        // *** Selección de nivel en la misma pantalla ***
        JLabel etiquetaSeleccionNivel = new JLabel("Selecciona un nivel:");
        String[] niveles = {"Nivel 1", "Nivel 2", "Nivel 3"};
        JComboBox<String> comboBoxNiveles = new JComboBox<>(niveles);
        panelSuperior.add(etiquetaSeleccionNivel, BorderLayout.NORTH);
        panelSuperior.add(comboBoxNiveles, BorderLayout.CENTER);

        pantallaInicio.add(panelSuperior, BorderLayout.SOUTH);

        // Crear el juego
        int ancho = 800;
        int alto = 600;
        int vidas = 3;

        // Añadir el menú de inicio al contenedor de paneles
        contenedorPaneles.add(pantallaInicio, "Inicio");

        // Pantalla de victoria
        JPanel pantallaVictoria = new JPanel();
        pantallaVictoria.setLayout(new BorderLayout());
        JLabel textoVictoria = new JLabel("¡Victoria!", JLabel.CENTER);
        JButton botonReiniciar = new JButton("Reiniciar");
        pantallaVictoria.add(textoVictoria, BorderLayout.CENTER);
        pantallaVictoria.add(botonReiniciar, BorderLayout.CENTER);

        // Pantalla de derrota
        JPanel pantallaDerrota = new JPanel();
        pantallaDerrota.setLayout(new BorderLayout());
        JLabel textoDerrota = new JLabel("GAME OVER", JLabel.CENTER);
        pantallaDerrota.add(textoDerrota, BorderLayout.CENTER);

        // Añadir los paneles al contenedor
        marco.add(contenedorPaneles);
        contenedorPaneles.add(pantallaVictoria, "Victoria");
        contenedorPaneles.add(pantallaDerrota, "Derrota");

        marco.setVisible(true);

        // Acción al hacer clic en "Iniciar Juego"
        botonIniciar.addActionListener(e -> {
            String nivelSeleccionado = (String) comboBoxNiveles.getSelectedItem();
            int indiceNivel = 1; // Valor predeterminado por defecto
            if (nivelSeleccionado != null) {
                switch (nivelSeleccionado) {
                    case "Nivel 1":
                        indiceNivel = 1;
                        break;
                    case "Nivel 2":
                        indiceNivel = 2;
                        break;
                    case "Nivel 3":
                        indiceNivel = 3;
                        break;
                }
            }

            // Crear el nivel basado en la selección del usuario
            Nivel nivel = new Nivel(indiceNivel, 0, vidas, 6);

            // Barra
            Barra barra = new Barra(350, alto - 70, 100, 10, ancho);

            // Ajustar la posición inicial de la pelota en relación con la barra
            int radioPelota = 10;
            int posicionXPelota = barra.getX() + (barra.getAncho() / 2) - radioPelota;  // Centrar la pelota en la barra
            int posicionYPelota = barra.getY() - radioPelota - 5;  // Colocar la pelota justo encima de la barra

            Pelota pelota = new Pelota(posicionXPelota, posicionYPelota, radioPelota, 5, 5, ancho, alto);
            List<Bloque> bloques = nivel.getBloques();
            Juego juego = new Juego(vidas, bloques);

            // Crear el panel del juego
            PanelPelota panelPelota = new PanelPelota(pelota, barra, bloques, nivel);
            contenedorPaneles.add(panelPelota, "Juego"); // Añadir panel de juego después de la selección de nivel

            ControladorPelota controlador = new ControladorPelota(pelota, barra, panelPelota, juego, cardLayout, contenedorPaneles, nivel);

            // Iniciar el juego
            controlador.iniciar();
            cardLayout.show(contenedorPaneles, "Juego"); // Cambiar a la pantalla de juego
        });
        //        // Acción al hacer clic en "Reiniciar"
//        botonReiniciar.addActionListener(e -> {
//            reiniciarJuego(pelota, barra, bloques, panelPelota, panelPelota, nivel);
//            cardLayout.show(contenedorPaneles, "Juego");
//        });

//
    }

    public static class FondoPanel extends JPanel {
        private Image imagen;

        public FondoPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                // Dibuja la imagen ajustada al tamaño del panel
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }



}



