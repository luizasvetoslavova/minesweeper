package view.gui;

import javax.swing.*;
import java.awt.*;

public class HomePage {
    private final JFrame frame;
    private final JPanel mainPanel;

    private JButton easy;
    private JButton medium;
    private JButton hard;
    private JButton expert;

    public HomePage() {
        frame = new JFrame();
        mainPanel = new JPanel();
        frame.setTitle("MINESWEEPER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    }

    public void initHome() {
        showRules();
        addButtons();
        frame.add(mainPanel);
    }

    private void showRules() {
        JLabel rules = new JLabel("<html>" + "<br>" + "<br>" + "<br>" + "<div style='text-align: " +
                "center;'>" +
                "Welcome to Minesweeper!" + "<br>" + "<br>" + "Rules:" + "<br>" +
                "1. The number shown on an unlocked cell is the number of model.mines adjacent to it." + "<br>" +
                "2. You have to flag all the model.mines and not unlock on a single one, or else you lose and " +
                "the game ends." +
                "<br>" + "You can start by clicking at any random cell." + "<br>" + "Signs:" + "<br>" +
                "◽ - Empty cell. There are no bombs near it." + "<br>" + "⬛ - Bomb." + "<br>" +
                "⬜ - Unopened cell." + "<br>" + "⛳ - Flag." + "<br>" + "<br>" + "Pick your level:" + "</div> </html>");

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
        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.add(expert);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 200, 100));
        mainPanel.add(buttonPanel);
    }

    public JButton getEasy() {
        return easy;
    }

    public JButton getMedium() {
        return medium;
    }

    public JButton getHard() {
        return hard;
    }

    public JButton getExpert() {
        return expert;
    }

    public JFrame getFrame() {
        return frame;
    }
}