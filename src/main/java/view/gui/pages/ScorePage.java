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
    private JPanel buttonPanel;
    private ScoreDao scoreDao;
    private final Color bgColor = Color.decode("#E7E5FF");
    private final Color btnColor = Color.decode("#FFFFFF");

    public ScorePage(HomePage homePage, GUIView view) {
        this.homePage = homePage;
        this.view = view;
        scoreDao = ScoreDao.getInstance();

        mainPanel = new JPanel();
        buttonPanel = new JPanel();
        setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgColor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setTitle("SCORES");
        getContentPane().add(mainPanel);
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
        addHomeButton();
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
        reset.setPreferredSize(new Dimension(130, 35));

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
        backToHome.setPreferredSize(new Dimension(130, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(backToHome, gbc);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 500, 0));
    }

    private void visualizeScores() {
        JPanel scorePanel = new JPanel(new GridBagLayout());
        scorePanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel scores = new JLabel("<html>" + "<br>" + "<br>" + "<div style='text-align: " + "center;'>" +
                "<h1><u>Best Scores by Levels</u></h1><br>" +
                "EASY: " + scoresReadable(scoreDao.getEasyTime(), scoreDao.getEasyClicks()) + "<br>" +
                "MEDIUM: " + scoresReadable(scoreDao.getMediumTime(), scoreDao.getMediumClicks()) + "<br>" +
                "HARD: " + scoresReadable(scoreDao.getHardTime(), scoreDao.getHardClicks()) + "<br>" +
                "EXPERT: " + scoresReadable(scoreDao.getExpertTime(), scoreDao.getExpertClicks()) + "<br>" +
                "CUSTOM: " + scoresReadable(scoreDao.getCustomTime(), scoreDao.getCustomClicks()) +
                "</div> </html>");

        scores.setFont(new Font(scores.getFont().getName(), Font.PLAIN, 18));
        scorePanel.add(scores, gbc);
        scorePanel.setBorder(new EmptyBorder(180, 0, 50, 0));
        mainPanel.add(scorePanel);
    }

    private String scoresReadable(int timeScore, int clickScore) {
        return "Time - " + view.timeMessage(timeScore) + ", Clicks - " + clickScore + "<br>";
    }
}