package view.gui;

import model.levels.*;
import model.mines.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TablePage extends JFrame {
    private final List<TableButton> tableButtons;

    private final Matrix matrix;
    private final JPanel mainPanel;
    private final HomePage homePage;

    private JButton reset;
    private JButton nextLevel;
    private JButton previousLevel;

    private int buttonSize;

    public TablePage(Matrix matrix, HomePage homePage, String heading) {
        this.matrix = matrix;
        this.homePage = homePage;
        tableButtons = new ArrayList<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setTitle(heading);
        setVisible(true);
        setResizable(false);
    }

    public void draw() {
        addLabel();
        drawTable();
        addButtons();
        add(mainPanel);
    }

    private void addLabel() {
        JLabel levelCaption = new JLabel("<html>" + "<br>" +
                matrix.getClass().getSimpleName().toUpperCase(Locale.ROOT) + "<html>");
        JPanel rulesPanel = new JPanel();
        rulesPanel.add(levelCaption);
        mainPanel.add(rulesPanel);
    }

    private void drawTable() {
        setTableButtonsSize();
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(matrix.getCells().length, matrix.getCells()[0].length));

        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(buttonSize, buttonSize));
                tablePanel.add(button);
                tableButtons.add(new TableButton(button, matrix.getCells()[line][col]));
            }
        }

        contentPanel.add(tablePanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void addButtons() {
        previousLevel = new JButton("PREVIOUS LEVEL");
        previousLevel.setVisible(false);

        JPanel buttonPanel = new JPanel();
        reset = new JButton("RESET");

        JButton backToHome = new JButton("BACK TO HOME");
        backToHome.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                homePage.setVisible(true);
            }
        });

        nextLevel = new JButton("NEXT LEVEL");
        nextLevel.setVisible(false);

        buttonPanel.add(previousLevel);
        buttonPanel.add(reset);
        buttonPanel.add(backToHome);
        buttonPanel.add(nextLevel);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(29, 200, 40, 200));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setTableButtonsSize() {
        if (matrix instanceof Easy) buttonSize = 35;
        else if (matrix instanceof Medium) buttonSize = 23;
        else if (matrix instanceof Hard) buttonSize = 20;
        else if (matrix instanceof Expert) buttonSize = 18;
        else if (matrix instanceof Custom) buttonSize = setCustomSize(matrix);
    }

    private int setCustomSize(Matrix custom) {
        int rows = custom.getCells().length;
        int cols = custom.getCells()[0].length;

        if (rows <= 10 && cols <= 10) {
            return 35;
        } else if (rows <= 15 && cols <= 15) {
            return 30;
        } else if (rows <= 20 && cols <= 20) {
            return 25;
        } else if (rows <= 32 && cols <= 32) {
            return 20;
        } else if (rows <= 43 && cols <= 43) {
            return 15;
        }
        return 13;
    }

    public List<TableButton> getButtons() {
        return tableButtons;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public JButton getReset() {
        return reset;
    }

    public JButton getNextLevel() {
        return nextLevel;
    }

    public JButton getPreviousLevel() {
        return previousLevel;
    }
}