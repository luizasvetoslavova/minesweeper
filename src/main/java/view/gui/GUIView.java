package view.gui;

import javax.swing.*;
import java.awt.*;

public class GUIView {
    private final JFrame frame;
    private final JPanel panel;

    public GUIView() {
        this.frame = new JFrame();
        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        frame.setVisible(true);
    }

    public void initJFrame() {
        frame.setTitle("MINESWEEPER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1150, 700);
        frame.setLocation(200, 80);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    public void showRules() {
        JLabel rules = new JLabel("<html>" + "<br>" + "<div style='text-align: center;'>" +
                "Welcome to Minesweeper!" + "<br>" + "<br>" + "Rules:" + "<br>" +
                "~ The number shown on an unlocked cell is the number of model.mines adjacent to it." + "<br>" +
                "~ You have to flag all the model.mines and not unlock on a single one, or else you lose and the game ends." +
                "<br>" + "You can start by clicking at any random cell." + "<br>" + "Signs:" + "<br>" +
                "◽ - Empty cell. There are no bombs near it." + "<br>" + "⬛ - Bomb." + "<br>" +
                "⬜ - Unopened cell." + "<br>" + "⛳ - Flag." + "<br>" + "<br>" + "Pick your level:" +
                "<br>" + "<br>" + "</div> </html>");

        rules.setFont(new Font(rules.getFont().getName(), Font.PLAIN, 17));
        panel.add(rules);
        frame.add(panel);
    }

    public void addButtons() {
        JButton easyButton = new JButton("EASY");
        JButton mediumButton = new JButton("MEDIUM");
        JButton hardButton = new JButton("HARD");
        JButton expertButton = new JButton("EXPERT");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(10));

        panel.add(easyButton);
        panel.add(mediumButton);
        panel.add(hardButton);
        panel.add(expertButton);
        frame.add(panel);
    }
}
