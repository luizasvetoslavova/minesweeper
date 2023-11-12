package view.gui.pages;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private final JPanel mainPanel;
    private JPanel rulesPanel;
    private JPanel buttonPanel;
    private JPanel scoreBtnPanel;
    private JButton easy;
    private JButton medium;
    private JButton hard;
    private JButton expert;
    private JButton custom;
    private JButton scores;
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

    private void setDesign() {
        setBackground();
        designButtons();
    }

    private void setBackground() {
        Color bgColor = Color.decode("#FFF5C5");
        mainPanel.setBackground(bgColor);
        buttonPanel.setBackground(bgColor);
        rulesPanel.setBackground(bgColor);
        scoreBtnPanel.setBackground(bgColor);
    }

    private void designButtons() {
        Font buttonFont = new Font("Georgia", Font.PLAIN, 14);
        easy.setFont(buttonFont);
        medium.setFont(buttonFont);
        hard.setFont(buttonFont);
        expert.setFont(buttonFont);
        custom.setFont(buttonFont);
        scores.setFont(buttonFont);

        easy.setBackground(Color.WHITE);
        easy.setPreferredSize(new Dimension(90, 35));

        medium.setBackground(Color.WHITE);
        medium.setPreferredSize(new Dimension(100, 35));

        hard.setBackground(Color.WHITE);
        hard.setPreferredSize(new Dimension(90, 35));

        expert.setBackground(Color.WHITE);
        expert.setPreferredSize(new Dimension(100, 35));

        custom.setBackground(Color.WHITE);
        custom.setPreferredSize(new Dimension(100, 35));

        scores.setBackground(Color.WHITE);
        scores.setPreferredSize(new Dimension(90, 35));
    }

    private void showRules() {
        JLabel rules = new JLabel("<html>" + "<br>" + "<br>" + "<br>" + "<div style='text-align: " + "center;'>" +
                this.rules + "</div> </html>");

        rules.setFont(new Font(rules.getFont().getName(), Font.PLAIN, 17));
        rulesPanel.add(rules);
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 5, 200));
        mainPanel.add(rulesPanel);
    }

    private void addButtons() {
        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.add(expert);
        buttonPanel.add(custom);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 10, 200));
        scoreBtnPanel.add(scores);
        scoreBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 40, 200));
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