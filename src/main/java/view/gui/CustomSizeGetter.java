package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomSizeGetter extends JDialog {
    private final JPanel mainPanel;
    private final GUIView view;

    private int lines;
    private int cols;

    public CustomSizeGetter() {
        super(new JFrame(), "CUSTOM - ENTER LINES AND COLUMNS", true);
        mainPanel = new JPanel();
        view = new GUIView();
        setSize(180, 135);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    }

    public void draw() {
        JPanel fieldPanel = new JPanel();
        JTextField linesField = addDigitField(fieldPanel, "Lines");
        JTextField colsField = addDigitField(fieldPanel, "Columns");

        JButton submitButton = new JButton("OK");
        setupButton(submitButton, linesField, colsField, fieldPanel);

        mainPanel.add(fieldPanel);
        add(mainPanel);
        setVisible(true);
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

    public boolean isSizeInvalid() {
        return lines < 3 || cols < 3 || lines > 50 || cols > 50;
    }

    private JTextField addDigitField(JPanel fieldPanel, String requirement) {
        JTextField digitField = new JTextField();
        digitField.setPreferredSize(new Dimension(70, 25));
        fieldPanel.add(new JLabel(requirement + ":"));
        fieldPanel.add(digitField);
        return digitField;
    }

    public int getLines() {
        return lines;
    }

    public int getCols() {
        return cols;
    }
}