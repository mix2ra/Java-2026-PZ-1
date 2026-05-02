import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Головний клас програми
public class DistanceCalculatorGUI extends JFrame {

    // Поля для введення координат
    private JTextField lat1Field, lon1Field, lat2Field, lon2Field;

    // Поле для виводу результату
    private JTextField resultField;

    // Радіус Землі в метрах
    private static final double R = 6371e3;

    public DistanceCalculatorGUI() {

        // Налаштування вікна
        setTitle("Обчислення відстані між точками");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основна панель
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Створення компонентів
        panel.add(new JLabel("Широта точки 1 (lat1):"));
        lat1Field = new JTextField();
        panel.add(lat1Field);

        panel.add(new JLabel("Довгота точки 1 (lon1):"));
        lon1Field = new JTextField();
        panel.add(lon1Field);

        panel.add(new JLabel("Широта точки 2 (lat2):"));
        lat2Field = new JTextField();
        panel.add(lat2Field);

        panel.add(new JLabel("Довгота точки 2 (lon2):"));
        lon2Field = new JTextField();
        panel.add(lon2Field);

        panel.add(new JLabel("Відстань D (м):"));
        resultField = new JTextField();
        resultField.setEditable(false);
        panel.add(resultField);

        // Кнопки
        JButton solveButton = new JButton("Solve");
        JButton clearButton = new JButton("Clear");

        panel.add(solveButton);
        panel.add(clearButton);

        // Додавання панелі до вікна
        add(panel);

        // Обробник кнопки Solve
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDistance();
            }
        });

        // Обробник кнопки Clear
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    // Метод обчислення відстані
    private void calculateDistance() {
        try {
            // Зчитування координат
            double lat1 = Double.parseDouble(lat1Field.getText());
            double lon1 = Double.parseDouble(lon1Field.getText());
            double lat2 = Double.parseDouble(lat2Field.getText());
            double lon2 = Double.parseDouble(lon2Field.getText());

            // Переведення в радіани
            double phi1 = Math.toRadians(lat1);
            double phi2 = Math.toRadians(lat2);
            double deltaPhi = Math.toRadians(lat2 - lat1);
            double deltaLambda = Math.toRadians(lon2 - lon1);

            // Формула гаверсинуса
            double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2)
                    + Math.cos(phi1) * Math.cos(phi2)
                    * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = R * c;

            // Вивід результату
            resultField.setText(String.format("%.2f", distance));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Введіть коректні числові значення!",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Очищення полів
    private void clearFields() {
        lat1Field.setText("");
        lon1Field.setText("");
        lat2Field.setText("");
        lon2Field.setText("");
        resultField.setText("");
    }

    // Точка входу в програму
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DistanceCalculatorGUI().setVisible(true);
        });
    }
}