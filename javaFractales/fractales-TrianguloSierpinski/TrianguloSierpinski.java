import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class TrianguloSierpinski extends JPanel {

    private static final int SIZE = 600; // Tamaño de la ventana
    private static final int OFFSET = 50; // Margen para centrar el triángulo
    private int scale;

    public TrianguloSierpinski(int scale) {
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D.Double top = new Point2D.Double(SIZE / 2.0, OFFSET);
        Point2D.Double left = new Point2D.Double(OFFSET, SIZE - OFFSET);
        Point2D.Double right = new Point2D.Double(SIZE - OFFSET, SIZE - OFFSET);

        drawSierpinskiTriangle(g2d, top, left, right, scale);
    }

    private void drawSierpinskiTriangle(Graphics2D g2d, Point2D.Double top, Point2D.Double left, Point2D.Double right, int scale) {
        if (scale == 0) {
            Path2D path = new Path2D.Double();
            path.moveTo(top.getX(), top.getY());
            path.lineTo(left.getX(), left.getY());
            path.lineTo(right.getX(), right.getY());
            path.closePath();
            g2d.fill(path);
        } else {
            Point2D.Double leftMid = midpoint(top, left);
            Point2D.Double rightMid = midpoint(top, right);
            Point2D.Double bottomMid = midpoint(left, right);

            drawSierpinskiTriangle(g2d, top, leftMid, rightMid, scale - 1);
            drawSierpinskiTriangle(g2d, leftMid, left, bottomMid, scale - 1);
            drawSierpinskiTriangle(g2d, rightMid, bottomMid, right, scale - 1);
        }
    }

    private Point2D.Double midpoint(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.getX() + p2.getX()) / 2.0, (p1.getY() + p2.getY()) / 2.0);
    }

    public static void main(String[] args) {
        int scale = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el escalar (número entero positivo):"));
        if (scale <= 0) {
            System.out.println("El escalar debe ser un número entero positivo.");
            return;
        }

        JFrame frame = new JFrame("Sierpinski Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE, SIZE);
        frame.add(new TrianguloSierpinski(scale));
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
