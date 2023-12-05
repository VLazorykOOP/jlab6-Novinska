/*Програма повинна включати обробку виключних ситуацій. Обробити 3
типи виключень: 2 стандартних і 1 власне. Зі стандартних виключень можна
взяти виключення пов’язане з відкриттям файлу і невірним форматом вхідних
даних. Для власного виключення потрібно розробити клас наслідуваний від
класу ArithmeticException. В програмі передбачити генерацію власного
виключення при певних вхідних даних (придумати самостійно).
Задана дійсна матриця A(n,n), n <= 20. Розробити програму, яка будує вектор X(n) за правилом:
X(i)(i=1,2,...,n) дорівнює півсумі модулів максимального і мінімального елементів i-го рядка. */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Task2 extends JFrame {

    private JTextField sizeField;
    private JTextArea resultArea;

    public Task2() {
        setTitle("Matrix App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        JLabel sizeLabel = new JLabel("Size of matrix (n <= 20): ");
        sizeField = new JTextField();
        JButton loadMatrixButton = new JButton("Load Matrix");

        inputPanel.add(sizeLabel);
        inputPanel.add(sizeField);
        inputPanel.add(loadMatrixButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        loadMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(sizeField.getText());
                    if (n > 20) {
                        throw new CustomException("Custom exception: n > 20.");
                    }

                    double[][] A = readMatrixFromUser(n);
                    double[] X = calculateVectorX(A, n);

                    displayResult(X);
                } catch (NumberFormatException ex) {
                    showMessage("Invalid input format. Please enter a valid integer.");
                } catch (IOException ex) {
                    showMessage("Error reading file: " + ex.getMessage());
                } catch (CustomException ex) {
                    showMessage(ex.getMessage());
                }
            }
        });

        add(mainPanel);
    }

    private double[][] readMatrixFromUser(int n) throws IOException {
        double[][] A = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String inputValue = JOptionPane.showInputDialog("Enter A[" + i + "][" + j + "]:");
                A[i][j] = Double.parseDouble(inputValue);
            }
        }

        return A;
    }

    private double[] calculateVectorX(double[][] A, int n) {
        double[] X = new double[n];

        for (int i = 0; i < n; i++) {
            double max = A[i][0];
            double min = A[i][0];

            for (int j = 0; j < n; j++) {
                if (A[i][j] > max) {
                    max = A[i][j];
                }
                if (A[i][j] < min) {
                    min = A[i][j];
                }
            }

            X[i] = (Math.abs(max) + Math.abs(min)) / 2.0;
        }

        return X;
    }

    private void displayResult(double[] X) {
        StringBuilder result = new StringBuilder("Array X:\n");
        for (int i = 0; i < X.length; i++) {
            result.append("X[").append(i).append("] = ").append(X[i]).append("\n");
        }
        resultArea.setText(result.toString());
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Task2().setVisible(true);
            }
        });
    }

    static class CustomException extends ArithmeticException {
        public CustomException(String message) {
            super(message);
        }
    }
}
