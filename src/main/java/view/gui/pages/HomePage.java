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
        setVisible(true);
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
        getRootPane().setBackground(bgColor);


    }

    //TODO place text inside the buttons
    //rounded white buttons
//    private void designButtons() {
//        int arc = 35;
//
//        BorderFactory.createEmptyBorder(5, 20, 5, 20);
//        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(
//                new LineBorder(Color.decode("#FFF5C5"), 0),
//                new Border() {
//                    @Override
//                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//                        g.setColor(Color.WHITE);
//                        g.fillRoundRect(x, y, width - 1, height - 1, arc, arc);
//                    }
//
//                    @Override
//                    public Insets getBorderInsets(Component c) {
//                        return new Insets(arc, arc, arc, arc);
//                    }
//
//                    @Override
//                    public boolean isBorderOpaque() {
//                        return true;
//                    }
//                });
//
//        easy.setBorder(compoundBorder);
//        easy.setBackground(Color.decode("#FFF5C5"));
//        easy.setPreferredSize(new Dimension(90, 35));
//    }

    private void designButtons() {
        easy.setBackground(Color.WHITE);
        easy.setPreferredSize(new Dimension(90, 35));

        medium.setBackground(Color.WHITE);
        medium.setPreferredSize(new Dimension(90, 35));

        hard.setBackground(Color.WHITE);
        hard.setPreferredSize(new Dimension(90, 35));

        expert.setBackground(Color.WHITE);
        expert.setPreferredSize(new Dimension(90, 35));

        custom.setBackground(Color.WHITE);
        custom.setPreferredSize(new Dimension(90, 35));

        scores.setBackground(Color.WHITE);
        scores.setPreferredSize(new Dimension(90, 35));
    }

    private void showRules() {
        JLabel rules = new JLabel("<html>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<div style='text-align: " + "center;'>" +
                this.rules + "<br>" + "<br>" + "Pick your level:" + "</div> </html>");

        rules.setFont(new Font(rules.getFont().getName(), Font.PLAIN, 17));
        rulesPanel.add(rules);
        mainPanel.add(rulesPanel);
    }

    private void addButtons() {
        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.add(expert);
        buttonPanel.add(custom);
        scoreBtnPanel.add(scores);
        alignButtonsInMainPanel(buttonPanel, scoreBtnPanel);
    }

    private void alignButtonsInMainPanel(JPanel buttonPanel, JPanel scoreBtnPanel) {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(0));
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