package view.gui.pages;

import model.ScoreDao;
import view.gui.GUIView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScorePage extends JFrame {
    private final JPanel mainPanel;
    private final HomePage homePage;
    private final GUIView view;
    private final JPanel buttonPanel;
    private final ScoreDao scoreDao;
    private final Color bgColor;
    private final Color btnColor;
    private final Font buttonFont;

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

        bgColor = Color.decode("#E7E5FF");
        btnColor = Color.decode("#FFFFFF");
        buttonFont = new Font("Georgia", Font.PLAIN, 14);
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

        visualizeScores();
        addResetButton();
        addHomeButton();
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
        reset.setPreferredSize(new Dimension(150, 35));
        reset.setFont(buttonFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(reset, gbc);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 500, 0));
        buttonPanel.setBackground(bgColor);
    }

    private void addHomeButton() {
        JButton backToHome = new JButton("BACK TO HOME");
        backToHome.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                homePage.setVisible(true);
            }
        });
        backToHome.setBackground(btnColor);
        backToHome.setPreferredSize(new Dimension(150, 35));
        backToHome.setFont(buttonFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(backToHome, gbc);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 300, 0));
    }

    private void visualizeScores() {
        JPanel scorePanel = new JPanel(new GridBagLayout());
        scorePanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel scores = new JLabel("<html>" +
                "<br><br><br><br>" +
                "<div style='text-align: " + "center;'>" +
                "<p style=\"font-family: 'Times New Roman', serif; font-size: 27px;\"><br>" +
                "Best Scores by Levels:" +
                "</p><br><br>" +
                "<table border=\"\" cellpadding=\"8\" style=\"font-family: 'Times New Roman', serif;\"" +
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

        scores.setFont(new Font(scores.getFont().getName(), Font.PLAIN, 23));
        scorePanel.add(scores, gbc);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(80, 100, 50, 100));
        mainPanel.add(scorePanel);
    }
}