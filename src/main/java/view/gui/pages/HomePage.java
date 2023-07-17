package view.gui.pages;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {
    private final JPanel mainPanel;

    private JButton easy;
    private JButton medium;
    private JButton hard;
    private JButton expert;
    private JButton custom;

    private String rules;

    public HomePage() {
        mainPanel = new JPanel();
        setLayout(new BorderLayout());
        setVisible(true);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    }

    public void initHome() {
        showRules();
        addButtons();
        add(mainPanel);
    }

    private void showRules() {
        JLabel rules = new JLabel("<html>" + "<br>" + "<br>" + "<br>" + "<br>" + "<br>" + "<br>" +
                "<div style='text-align: " + "center;'>" +
                this.rules + "<br>" + "<br>" + "Pick your level:" + "</div> </html>");

        rules.setFont(new Font(rules.getFont().getName(), Font.PLAIN, 17));
        JPanel rulesPanel = new JPanel();
        rulesPanel.add(rules);
        mainPanel.add(rulesPanel);
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        easy = new JButton("EASY");
        medium = new JButton("MEDIUM");
        hard = new JButton("HARD");
        expert = new JButton("EXPERT");
        custom = new JButton("CUSTOM");

        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.add(expert);
        buttonPanel.add(custom);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 200, 100));
        mainPanel.add(buttonPanel);
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

    public void setRules(String rules) {
        this.rules = rules;
    }
}