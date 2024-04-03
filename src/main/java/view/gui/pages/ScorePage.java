package view.gui.pages;

import model.ScoreDao;
import view.gui.GUIView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScorePage extends JFrame {
    private static final Color bgColor = Color.decode("#E7E5FF");
    private static final Color btnColor = Color.decode("#FFFFFF");
    private static final Font buttonFont = new Font("Georgia", Font.PLAIN, 17);
    private static final String textFont = "Times New Roman";

    private final JPanel mainPanel;
    private final HomePage homePage;
    private final GUIView view;
    private final JPanel buttonPanel;
    private final ScoreDao scoreDao;

    public ScorePage(HomePage homePage, GUIView view) {
        this.homePage = homePage;
        this.view = view;
        scoreDao = ScoreDao.getInstance();

        mainPanel = new JPanel();
        buttonPanel = new JPanel();
        setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setTitle("SCORES");
        getContentPane().add(mainPanel);
        mainPanel.setBackground(bgColor);
        setupScoreButton();
    }

    private void setupScoreButton() {
        homePage.getScoreBtn().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupPage();
                setVisible(true);
                homePage.setVisible(false);
            }
        });
    }

    private void setupPage() {
        mainPanel.removeAll();
        buttonPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();

        addHomeButton();
        visualizeScores();
        addResetButton();
        mainPanel.add(buttonPanel);
    }

    private void addResetButton() {
        JButton reset = new JButton("RESET SCORES");
        reset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreDao.resetDb();
                view.showMessage("Scores reset successfully!");
                setupPage();
            }
        });
        reset.setBackground(btnColor);
        reset.setPreferredSize(new Dimension(160, 42));
        reset.setFont(buttonFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(reset, gbc);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 500, 0));
        buttonPanel.setBackground(bgColor);
    }

    private void addHomeButton() {
        JButton backArrow = new JButton("<--");
        backArrow.setContentAreaFilled(false);
        backArrow.setBorderPainted(false);

        backArrow.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                homePage.setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backArrow);
        buttonPanel.setBackground(bgColor);
        mainPanel.add(buttonPanel);
    }

    private void visualizeScores() {
        JPanel scorePanel = new JPanel(new GridBagLayout());
        scorePanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel scores = new JLabel("<html>" +
                "<br><br>" +
                "<div style='text-align: " + "center;'>" +
                "<p style=\"font-family: '" + textFont + "', serif; font-size: 27px;\"><br>" +
                "Best Scores by Levels" +
                "</p><br>" +
                "<table border=\"\" cellpadding=\"25\" style=\"font-family: '" + textFont + "', serif;\"" +
                "\\\"background-color: #FFFFFF;\\\">\n" +
                "    <tr>\n" +
                "        <th></th>\n" +
                "        <th>Easy</th>\n" +
                "        <th>Medium</th>\n" +
                "        <th>Hard</th>\n" +
                "        <th>Expert</th>\n" +
                "        <th>Custom</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>Time</th>\n" +
                "        <td>" + view.timeMessage(scoreDao.getEasyTime()) + "</td>\n" +
                "        <td>" + view.timeMessage(scoreDao.getMediumTime()) + "</td>\n" +
                "        <td>" + view.timeMessage(scoreDao.getHardTime()) + "</td>\n" +
                "        <td>" + view.timeMessage(scoreDao.getExpertTime()) + "</td>\n" +
                "        <td>" + view.timeMessage(scoreDao.getCustomTime()) + "</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>" + "Clicks" + "</th>\n" +
                "        <td>" + scoreDao.getEasyClicks() + "</td>\n" +
                "        <td>" + scoreDao.getMediumClicks() + "</td>\n" +
                "        <td>" + scoreDao.getHardClicks() + "</td>\n" +
                "        <td>" + scoreDao.getExpertClicks() + "</td>\n" +
                "        <td>" + scoreDao.getCustomClicks() + "</td>\n" +
                "    </tr>\n" +
                "</table>" +
                "</html>");

        scores.setFont(new Font(scores.getFont().getName(), Font.PLAIN, 28));
        scorePanel.add(scores, gbc);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(80, 100, 50, 100));
        mainPanel.add(scorePanel);
    }
}