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
    private JButton submitButton;
    private int lines;
    private int cols;

    public CustomSizeGetter() {
        super(new JFrame(), "DIMENSIONS", true);
        mainPanel = new JPanel();
        view = new GUIView();
        setSize(320, 170);
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
        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        JTextField linesField = addDimensionField(gbc, fieldPanel, "Lines");
        gbc.gridy = 1;
        JTextField colsField = addDimensionField(gbc, fieldPanel, "Columns");
        addSubmitButton(fieldPanel, gbc);

        setupButton(submitButton, linesField, colsField, fieldPanel);
        mainPanel.add(fieldPanel);
        add(mainPanel);
        setVisible(true);
    }

    private JTextField addDimensionField(GridBagConstraints gbc, JPanel fieldPanel, String text) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lineRequirement = new JLabel(text + ": ");
        lineRequirement.setFont(new Font(fontName, Font.PLAIN, 18));
        gbc.insets = new Insets(7, 0, 8, 7);
        fieldPanel.add(lineRequirement, gbc);

        gbc.gridx = 1;
        JTextField dimensionField = addDigitField(fieldPanel, "");
        dimensionField.setFont(new Font(fontName, Font.PLAIN, 16));
        dimensionField.setPreferredSize(new Dimension(75, dimensionField.getPreferredSize().height));
        gbc.insets = new Insets(0, 0, 8, 7);
        fieldPanel.add(dimensionField, gbc);
        return dimensionField;
    }

    private void addSubmitButton(JPanel fieldPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 50, 10, 0);

        JButton submitButton = new JButton("OK");
        Dimension buttonSize = new Dimension(65, 30);
        submitButton.setPreferredSize(buttonSize);
        submitButton.setBackground(btnColor);
        submitButton.setFont(new Font(fontName, Font.BOLD, 14));
        fieldPanel.add(submitButton, gbc);
        this.submitButton = submitButton;
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
    }

    private JTextField addDigitField(JPanel fieldPanel, String requirement) {
        JTextField digitField = new JTextField();
        digitField.setPreferredSize(new Dimension(90, 30));
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