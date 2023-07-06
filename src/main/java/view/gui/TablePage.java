package view.gui;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TablePage {
    private final List<TableButton> tableButtons;

    private final Matrix matrix;
    private final JPanel mainPanel;
    private final HomePage homePage;
    private final JFrame frame;

    private int buttonSize;

    public TablePage(Matrix matrix, HomePage homePage, String heading) {
        this.matrix = matrix;
        this.homePage = homePage;
        tableButtons = new ArrayList<>();
        frame = new JFrame(heading);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void draw() {
        drawTable();
        addButtons();
        frame.add(mainPanel);
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
        JPanel buttonPanel = new JPanel();

        JButton reset = new JButton("RESET");
        reset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                draw();
            }
        });

        JButton backToHome = new JButton("BACK TO HOME");
        backToHome.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                homePage.getFrame().setVisible(true);
            }
        });

        buttonPanel.add(reset);
        buttonPanel.add(backToHome);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(29, 200, 40, 200));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setTableButtonsSize() {
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

    public List<TableButton> getButtons() {
        return tableButtons;
    }

    public Matrix getMatrix() {
        return matrix;
    }
}