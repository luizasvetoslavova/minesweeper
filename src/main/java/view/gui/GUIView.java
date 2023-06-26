package view.gui;

import javax.swing.*;
import java.awt.*;

public class GUIView {
    private final JFrame frame;
    private JPanel mainPanel;

    public GUIView() {
        this.frame = new JFrame();
    }

    public void initJFrame() {
        frame.setTitle("MINESWEEPER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1150, 700);
        frame.setLocation(200, 80);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);
    }

    public void showRules() {
        JLabel rules = new JLabel("<html>" + "<br>" + "<br>" + "<br>" + "<br>" + "<div style='text-align: " +
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

    public void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("EASY"));
        buttonPanel.add(new JButton("MEDIUM"));
        buttonPanel.add(new JButton("HARD"));
        buttonPanel.add(new JButton("EXPERT"));

        int topPadding = 50;
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, 100, 200, 100));

        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
    }
}