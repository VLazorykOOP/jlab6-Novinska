import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*Промоделювати обертання супутника навколо планети по еліптичній орбіті.
Коли ховається за планетою - супутнику не видно.*/

public class Task1 extends JFrame {
    private int centerX = 300; // координати центра планети
    private int centerY = 300;
    private int planetRadius = 50; // радіус планети
    private int satelliteRadius = 20; // радіус супутника
    private double angle = 0; // кут обертання супутника

    public Task1() {
        setTitle("Супутник на еліптичній орбіті");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillOval(centerX - planetRadius, centerY - planetRadius, 2 * planetRadius, 2 * planetRadius); // планета

                //координати супутника 
                int satelliteX = (int) (centerX + 1.5 * planetRadius * Math.cos(angle)); // по X
                int satelliteY = (int) (centerY + 0.5 * planetRadius * Math.sin(angle) + planetRadius * Math.cos(angle)); // по Y
                g.setColor(Color.BLACK);
                if (angle <= Math.PI*4/3) { // якщо кут менше або дорівнює π, малюємо супутник
                    g.fillOval(satelliteX - satelliteRadius, satelliteY - satelliteRadius, 2 * satelliteRadius, 2 * satelliteRadius); // супутник
                }
            }
        };

        add(panel);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += 0.02; // зміна кута обертання
                if (angle > 2 * Math.PI) {
                    angle = 0; // якщо кут більше 2π, обнуляємо його
                }
                panel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Task1().setVisible(true);
        });
    }
}
