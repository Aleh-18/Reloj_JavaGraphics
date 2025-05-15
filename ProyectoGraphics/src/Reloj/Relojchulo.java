/**
 * Clase Reloj_Graphics que muestra un reloj analógico con manecillas y números
 * colocados como JLabels. Utiliza Swing para la interfaz gráfica y Java2D para el
 * dibujo de manecillas y circunferencia del reloj.
 *
 * <p>El reloj se actualiza cada segundo mediante un Timer, y las manecillas de horas,
 * minutos y segundos se calculan en función de la hora actual del sistema.</p>
 *
 */
package Reloj;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Relojchulo extends JFrame {

    public JPanel panelReloj;

    /**
     * Constructor que inicializa la ventana, configura el panel de dibujo,
     * coloca los números y arranca el temporizador para actualizar cada segundo.
     */
    public Relojchulo() {
        setTitle("Reloj");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panel donde se va a pintar el reloj
        panelReloj = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarReloj((Graphics2D) g);
            }
        };

        panelReloj.setBounds(0, 0, 400, 400);
        panelReloj.setBackground(Color.WHITE);
        panelReloj.setLayout(null);
        add(panelReloj);

        colocarNumeros();

        // Timer que actualiza cada segundo
        Timer timer = new Timer(1000, e -> panelReloj.repaint());
        timer.start();

        setVisible(true);
    }

    /**
     * Dibuja el contorno del reloj, las manecillas y el punto central.
     *
     * @param g objeto Graphics2D para realizar el dibujo
     */
    public void dibujarReloj(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int centroX = panelReloj.getWidth() / 2;
        int centroY = panelReloj.getHeight() / 2;
        int radio = 150;

        // Dibujar círculo
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4)); // Stroke es para el grosor de la línea
        g.drawOval(centroX - radio, centroY - radio, radio * 2, radio * 2);
        
        // Hora actual
        LocalTime ahora = LocalTime.now();

        // Cada segundo equivale a 6 grados en el reloj (360° entre 60 segundos)
        double segundo = ahora.getSecond() * 6;

        // Cada minuto también son 6 grados (360° entre 60 minutos),
        // y se le suma el avance que genera el segundo
        double minuto = ahora.getMinute() * 6 + segundo / 60;

        // Cada hora equivale a 30 grados (360° entre 12 horas),
        // y se le suma el avance proporcional que lleva el minutero (minuto / 12)
        double hora = (ahora.getHour() % 12) * 30 + minuto / 12;

        // Agujas
        dibujarAguja(g, centroX, centroY, hora, radio * 0.5, 6, Color.BLACK);   // hora
        dibujarAguja(g, centroX, centroY, minuto, radio * 0.7, 4, Color.BLACK); // minuto
        dibujarAguja(g, centroX, centroY, segundo, radio * 0.9, 2, Color.RED);  // segundo

        // Centro del reloj
        g.setColor(Color.BLACK);
        g.fillOval(centroX - 5, centroY - 5, 10, 10);
    }

    /**
     * Dibuja una aguja de longitud y grosor especificados en el ángulo dado.
     *
     * @param g objeto Graphics2D para dibujar
     * @param centroX coordenada X del centro del reloj
     * @param centroY coordenada Y del centro del reloj
     * @param grados ángulo en grados donde apunta la aguja
     * @param largo longitud de la aguja
     * @param grosor grosor de la línea
     * @param color color de la aguja
     */
    public void dibujarAguja(Graphics2D g, int centroX, int centroY, double grados, double largo, int grosor, Color color) {
        double radianes = Math.toRadians(grados - 90); // 0° hacia arriba
        int x = (int) (centroX + largo * Math.cos(radianes));
        int y = (int) (centroY + largo * Math.sin(radianes));
        g.setColor(color);
        g.setStroke(new BasicStroke(grosor));
        g.drawLine(centroX, centroY, x, y);
    }

    /**
     * Añade los números del 1 al 12 al panel en posiciones fijas.
     */
    public void colocarNumeros() {
        // Fuente para los números
        Font fuente = new Font("Arial", Font.BOLD, 16);

        // Coordenadas de cada número (calculadas manualmente)
        agregarNumero("12", 195, 50, fuente);  // Número 12
        agregarNumero("1", 265, 70, fuente);   // Número 1
        agregarNumero("2", 310, 115, fuente);  // Número 2
        agregarNumero("3", 330, 195, fuente);  // Número 3
        agregarNumero("4", 310, 260, fuente);  // Número 4
        agregarNumero("5", 265, 305, fuente);  // Número 5
        agregarNumero("6", 200, 325, fuente);  // Número 6
        agregarNumero("7", 130, 305, fuente);  // Número 7
        agregarNumero("8", 85, 270, fuente);   // Número 8
        agregarNumero("9", 65, 195, fuente);   // Número 9
        agregarNumero("10", 85, 115, fuente);  // Número 10
        agregarNumero("11", 130, 70, fuente);  // Número 11

    }

    /**
     * Crea un JLabel con un número y lo añade al panel.
     *
     * @param texto texto a mostrar
     * @param x posición X
     * @param y posición Y
     * @param fuente fuente del texto
     */
    public void agregarNumero(String texto, int x, int y, Font fuente) {
        JLabel numero = new JLabel(texto);
        numero.setFont(fuente);
        numero.setBounds(x, y, 30, 30);
        panelReloj.add(numero);
    }

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args no usados
     */
    public static void main(String[] args) {
        new Relojchulo();
    }
}
