package view.gui.pages;

import model.levels.*;
import model.mines.Matrix;
import view.gui.TableButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TablePage extends JFrame {
    private final List<TableButton> tableButtons;
    private final JPanel mainPanel;
    private final Matrix matrix;
    private final HomePage homePage;
    private final Color tableBtnColor;
    private final Color btnColor;
    private final Font buttonFont;

    private JButton reset;
    private JButton nextLevel;
    private JButton previousLevel;
    private Color bgColor;
    private int buttonSize;

    public TablePage(Matrix matrix, HomePage homePage, String heading) {
        this.matrix = matrix;
        this.homePage = homePage;
        tableButtons = new ArrayList<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgColor);
        setBgColor();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(heading);
        setVisible(true);

        tableBtnColor = Color.decode("#FFFFFF");
        btnColor = Color.decode("#F3F3F3");
        buttonFont = new Font("Georgia", Font.PLAIN, 14);
    }

    public void draw() {
        addLabel();
        drawTable();
        addButtons();
        add(mainPanel);
    }

    private void setBgColor() {
        if (matrix instanceof Easy) {
            bgColor = Color.decode("#E6FFCD");
        } else if (matrix instanceof Medium) {
            bgColor = Color.decode("#FCFFDA");
        } else if (matrix instanceof Hard) {
            bgColor = Color.decode("#FFDFDF");
        } else if (matrix instanceof Expert) {
            bgColor = Color.decode("#FFDFF4");
        } else if (matrix instanceof Custom) {
            bgColor = Color.decode("#DFFFFB");
        }
    }

    private void addLabel() {
        Font timesNewRomanFont = new Font("Georgia", Font.PLAIN, 20);

        JLabel levelCaption = new JLabel("<html>" + "<br><br><br>" +
                matrix.getClass().getSimpleName().toUpperCase(Locale.ROOT) + "</html>");
        levelCaption.setFont(timesNewRomanFont);

        JPanel captionPanel = new JPanel();
        captionPanel.add(levelCaption);
        captionPanel.setBackground(bgColor);
        mainPanel.add(captionPanel);
    }

    private void drawTable() {
        setTableButtonsSize();
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new GridBagLayout());
        mainContentPanel.setBackground(bgColor);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(matrix.getCells().length, matrix.getCells()[0].length));
        tablePanel.setBackground(bgColor);

        for (int line = 0; line < matrix.getCells().length; line++) {
            for (int col = 0; col < matrix.getCells()[line].length; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(buttonSize, buttonSize));
                button.setBackground(tableBtnColor);
                tablePanel.add(button);
                tableButtons.add(new TableButton(button, matrix.getCells()[line][col]));
            }
        }

        tablePanel.setBorder(BorderFactory.createEmptyBorder(25, 200, 40, 200));
        mainContentPanel.add(tablePanel);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
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

        backToHome.setBackground(btnColor);
        backToHome.setPreferredSize(new Dimension(150, 35));
        backToHome.setFont(buttonFont);

        reset.setBackground(btnColor);
        reset.setPreferredSize(new Dimension(90, 35));
        reset.setFont(buttonFont);

        nextLevel.setBackground(btnColor);
        nextLevel.setPreferredSize(new Dimension(140, 35));
        nextLevel.setFont(buttonFont);

        previousLevel.setBackground(btnColor);
        previousLevel.setPreferredSize(new Dimension(170, 35));
        previousLevel.setFont(buttonFont);

        buttonPanel.setBackground(bgColor);
        buttonPanel.add(previousLevel);
        buttonPanel.add(reset);
        buttonPanel.add(backToHome);
        buttonPanel.add(nextLevel);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 70, 200));
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
            return 17;
        } else if (rows <= 43 && cols <= 43) {
            return 12;
        }
        return 11;
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