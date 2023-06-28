package view.gui;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Matrix;

import javax.swing.*;
import java.awt.*;

public class TablePage {
    private final Matrix matrix;
    private final JPanel mainPanel;
    private final JFrame frame;
    private int buttonSize;

    public TablePage(Matrix matrix) {
        this.matrix = matrix;
        frame = new JFrame();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void draw() {
        drawTable();
    }

    private void drawTable() {
        int numRows = matrix.getCells().length;
        int numCols = matrix.getCells()[0].length;

        setButtonSize();
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(numRows, numCols));

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(buttonSize, buttonSize));
                tablePanel.add(button);
            }
        }

        contentPanel.add(tablePanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
    }

    private void setButtonSize() {
        if (matrix instanceof Easy) {
            buttonSize = 35;
        } else if (matrix instanceof Medium) {
            buttonSize = 23;
        } else if (matrix instanceof Hard) {
            buttonSize = 20;
        } else if (matrix instanceof Expert) {
            buttonSize = 18;
        }
    }
}