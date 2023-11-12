package view.gui.pages;

import view.gui.GUIView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomSizeGetter extends JDialog {
    private static final Color btnColor = Color.decode("#F3F3F3");
    private static final Color bgColor = Color.decode("#FFFFFF");
    private static final Color textFieldColor = Color.decode("#E3FFF0");
    private static final String fontName = "Times New Roman";

    private final JPanel mainPanel;
    private final GUIView view;
    private int lines;
    private int cols;

    public CustomSizeGetter() {
        super(new JFrame(), "DIMENSIONS", true);
        mainPanel = new JPanel();
        view = new GUIView();
        setSize(180, 135);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        setBackground(bgColor);
        UIManager.put("OptionPane.messageFont", new Font(fontName, Font.PLAIN, 18));
        UIManager.put("OptionPane.buttonFont", new Font(fontName, Font.BOLD, 14));
        UIManager.put("TextField.font", new Font(fontName, Font.PLAIN, 14));
    }

    public void draw() {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setBackground(bgColor);
        JTextField linesField = addDigitField(fieldPanel, "Lines:");
        JTextField colsField = addDigitField(fieldPanel, "Columns:");
        linesField.setFont(new Font(fontName, Font.PLAIN, 16));
        colsField.setFont(new Font(fontName, Font.PLAIN, 16));

        JButton submitButton = new JButton("OK");
        submitButton.setBackground(btnColor);
        submitButton.setFont(new Font(fontName, Font.BOLD, 14));
        setupButton(submitButton, linesField, colsField, fieldPanel);

        mainPanel.add(fieldPanel);
        add(mainPanel);
        setVisible(true);
    }

    public boolean isSizeInvalid() {
        return lines < 3 || cols < 3 || lines > 50 || cols > 50;
    }

    private void setupButton(JButton submitButton, JTextField linesField, JTextField colsField, JPanel panel) {
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    lines = Integer.parseInt(linesField.getText().trim());
                    cols = Integer.parseInt(colsField.getText().trim());
                    if (isSizeInvalid()) {
                        view.showInvalidSizeMessage();
                        return;
                    }
                    setVisible(false);
                } catch (NumberFormatException ex) {
                    view.showInvalidSizeMessage();
                }
            }
        });
        panel.add(submitButton);
    }

    private JTextField addDigitField(JPanel fieldPanel, String requirement) {
        JTextField digitField = new JTextField();
        digitField.setPreferredSize(new Dimension(70, 25));
        digitField.setBackground(textFieldColor);
        fieldPanel.add(new JLabel(requirement));
        fieldPanel.add(digitField);

        Font fieldFont = new Font(fontName, Font.PLAIN, 14);
        fieldPanel.getFontMetrics(fieldFont);
        digitField.setFont(fieldFont);
        return digitField;
    }

    public int getLines() {
        return lines;
    }

    public int getCols() {
        return cols;
    }
}