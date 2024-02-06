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
    private static final Color tableBtnColor = Color.decode("#FFFFFF");
    private static final Color btnColor = Color.decode("#F3F3F3");
    private static final String btnFontName = "Georgia";
    private static final String easyBgColor = "#E6FFCD";
    private static final String mediumBgColor = "#FCFFDA";
    private static final String hardBgColor = "#FFDFDF";
    private static final String expertBgColor = "#FFDFF4";
    private static final String customBgColor = "#DFFFFB";

    private final List<TableButton> tableButtons;
    private final JPanel mainPanel;
    private final Matrix matrix;
    private final HomePage homePage;
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
    }

    public void draw() {
        addLabel();
        drawTable();
        addButtons();
        add(mainPanel);
    }

    private void setBgColor() {
        if (matrix instanceof Easy) {
            bgColor = Color.decode(easyBgColor);
        } else if (matrix instanceof Medium) {
            bgColor = Color.decode(mediumBgColor);
        } else if (matrix instanceof Hard) {
            bgColor = Color.decode(hardBgColor);
        } else if (matrix instanceof Expert) {
            bgColor = Color.decode(expertBgColor);
        } else if (matrix instanceof Custom) {
            bgColor = Color.decode(customBgColor);
        }
    }

    private void addLabel() {
        String upperSpace;
        if (matrix instanceof Expert || matrix instanceof Custom) upperSpace = "<br><br>";
        else upperSpace = "<br><br><br>";

        Font captionFont = new Font(btnFontName, Font.PLAIN, 24);
        JLabel levelCaption = new JLabel("<html>" + upperSpace +
                matrix.getClass().getSimpleName().toUpperCase(Locale.ROOT) + "</html>");
        levelCaption.setFont(captionFont);

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

        designButtons(backToHome, buttonPanel);
    }

    private void designButtons(JButton backToHome, JPanel buttonPanel) {
        Font btnFont = new Font(btnFontName, Font.PLAIN, 17);
        backToHome.setBackground(btnColor);
        backToHome.setPreferredSize(new Dimension(165, 42));
        backToHome.setFont(btnFont);

        reset.setBackground(btnColor);
        reset.setPreferredSize(new Dimension(90, 42));
        reset.setFont(btnFont);

        nextLevel.setBackground(btnColor);
        nextLevel.setPreferredSize(new Dimension(140, 42));
        nextLevel.setFont(btnFont);

        previousLevel.setBackground(btnColor);
        previousLevel.setPreferredSize(new Dimension(180, 42));
        previousLevel.setFont(btnFont);

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
        if (matrix instanceof Easy) buttonSize = 40;
        else if (matrix instanceof Medium) buttonSize = 29;
        else if (matrix instanceof Hard) buttonSize = 28;
        else if (matrix instanceof Expert) buttonSize = 23;
        else if (matrix instanceof Custom) buttonSize = setCustomSize(matrix);
    }

    private int setCustomSize(Matrix custom) {
        int rows = custom.getCells().length;
        int cols = custom.getCells()[0].length;

        if (rows <= 10 && cols <= 10) {
            return 38;
        } else if (rows <= 15 && cols <= 15) {
            return 33;
        } else if (rows <= 20 && cols <= 20) {
            return 26;
        } else if (rows <= 25 && cols <= 25) {
            return 22;
        } else if (rows <= 32 && cols <= 32) {
            return 19;
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