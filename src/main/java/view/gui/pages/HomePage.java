package view.gui.pages;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private static final Color bgColor = Color.decode("#FFF5C5");
    private static final String btnFontName = "Georgia";
    private static final String textFontName = //"Times New Roman";
     "Baskerville";

    private final JPanel mainPanel;
    private final JPanel rulesPanel;
    private final JPanel buttonPanel;
    private final JPanel scoreBtnPanel;
    private final JButton easy;
    private final JButton medium;
    private final JButton hard;
    private final JButton expert;
    private final JButton custom;
    private final JButton scores;
    private String rules;

    public HomePage() {
        mainPanel = new JPanel();
        rulesPanel = new JPanel();
        setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        setTitle("MINESWEEPER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        easy = new JButton("EASY");
        medium = new JButton("MEDIUM");
        hard = new JButton("HARD");
        expert = new JButton("EXPERT");
        custom = new JButton("CUSTOM");
        scores = new JButton("SCORES");
        buttonPanel = new JPanel();
        scoreBtnPanel = new JPanel();

        setDesign();
        setVisible(true);
    }

    public void initHome() {
        showRules();
        addButtons();
        add(mainPanel);
    }

    public String getHomeRuleSet() {
        return "<html>" + "<br>" + "<br>" + "<div style='text-align: " + "center;'>" +
                "<br>" +
                "<h3 style=\"font-family: '" + textFontName + "', serif; font-size: 28px;\">" +
                "<br>Welcome to Minesweeper!" +
                "</h3>" +
                "<br><br>" +
                "<p style=\"font-family: '" + textFontName + "', serif; font-size: 16px;\">" +
                "The number shown on an opened cell is the number of mines adjacent to it. " +
                "<br>If a cell is empty, it means that there are no mines near it.<br>" +
                "You need to flag all the mines and not open any. If you do open a mine, you die. <br><br>" +
                "All digit squares need to be opened in order for you to win.<br>" +
                "You can start by clicking at any random cell.<br><br>" +
                "Regular click to open a cell. Right click to flag. <br> Be fast for a good score!" +
                "<br><br>" +
                "<br><br>START:" +
                "</p>" +
                "</div> </html>";
    }

    private void setDesign() {
        setBackground();
        designButtons();
    }

    private void setBackground() {
        mainPanel.setBackground(bgColor);
        buttonPanel.setBackground(bgColor);
        rulesPanel.setBackground(bgColor);
        scoreBtnPanel.setBackground(bgColor);
    }

    private void designButtons() {
        Font buttonFont = new Font(btnFontName, Font.PLAIN, 17);
        Color buttonBgColor = Color.WHITE;

        easy.setBackground(buttonBgColor);
        easy.setFont(buttonFont);
        easy.setPreferredSize(new Dimension(106, 42));

        medium.setBackground(buttonBgColor);
        medium.setFont(buttonFont);
        medium.setPreferredSize(new Dimension(112, 42));

        hard.setBackground(buttonBgColor);
        hard.setFont(buttonFont);
        hard.setPreferredSize(new Dimension(106, 42));

        expert.setBackground(buttonBgColor);
        expert.setFont(buttonFont);
        expert.setPreferredSize(new Dimension(106, 42));

        custom.setBackground(buttonBgColor);
        custom.setFont(buttonFont);
        custom.setPreferredSize(new Dimension(106, 42));

        scores.setBackground(buttonBgColor);
        scores.setFont(buttonFont);
        scores.setPreferredSize(new Dimension(106, 42));
    }

    private void showRules() {
        JLabel rules = new JLabel(this.rules);
        rules.setFont(new Font(rules.getFont().getName(), Font.PLAIN, 17));
        rulesPanel.add(rules);
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, -35, 200));
        mainPanel.add(rulesPanel);
    }

    private void addButtons() {
        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.add(expert);
        buttonPanel.add(custom);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(7, 200, -40, 200));
        scoreBtnPanel.add(scores);
        scoreBtnPanel.setBorder(BorderFactory.createEmptyBorder(15, 200, 40, 200));
        alignButtonsInMainPanel(buttonPanel, scoreBtnPanel);
    }

    private void alignButtonsInMainPanel(JPanel buttonPanel, JPanel scoreBtnPanel) {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(-5));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(-5));
        scoreBtnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scoreBtnPanel);
        mainPanel.add(Box.createVerticalGlue());
    }

    public JButton getEasyBtn() {
        return easy;
    }

    public JButton getMediumBtn() {
        return medium;
    }

    public JButton getHardBtn() {
        return hard;
    }

    public JButton getExpertBtn() {
        return expert;
    }

    public JButton getCustomBtn() {
        return custom;
    }

    public JButton getScoreBtn() {
        return scores;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}