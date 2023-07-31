package view.gui.pages;

import presenter.gameplay.ScoreSaver;
import view.gui.GUIView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScorePage extends JFrame {
    private final JPanel mainPanel;
    private final HomePage homePage;
    private final ScoreSaver scoreSaver;
    private final GUIView view;

    public ScorePage(HomePage homePage, ScoreSaver scoreSaver, GUIView view) {
        this.homePage = homePage;
        this.scoreSaver = scoreSaver;
        this.view = view;

        mainPanel = new JPanel();
        setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
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
            }
        });
    }

    private void setupPage() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();

        initScorePage();
        addHomeButton();
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
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        buttonPanel.add(backToHome, gbc);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 500, 0));
        mainPanel.add(buttonPanel);
    }

    private void initScorePage() {
        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int easyTimeScore = getAppropriateDigitValue("src/main/resources/scores/easy/time");
        int easyClickScore = getAppropriateDigitValue("src/main/resources/scores/easy/clicks");
        int mediumTimeScore = getAppropriateDigitValue("src/main/resources/scores/medium/time");
        int mediumClickScore = getAppropriateDigitValue("src/main/resources/scores/medium/time");
        int hardTimeScore = getAppropriateDigitValue("src/main/resources/scores/hard/time");
        int hardClickScore = getAppropriateDigitValue("src/main/resources/scores/hard/clicks");
        int expertTimeScore = getAppropriateDigitValue("src/main/resources/scores/expert/time");
        int expertClickScore = getAppropriateDigitValue("src/main/resources/scores/expert/clicks");

        JLabel scores = new JLabel("<html>" + "<br>" + "<br>" + "<div style='text-align: " + "center;'>" +
                "<h1><u>Best Scores by Levels</u></h1><br>" +
                "EASY: " + scoresReadable(easyTimeScore, easyClickScore) + "<br>" +
                "MEDIUM: " + scoresReadable(mediumTimeScore, mediumClickScore) + "<br>" +
                "HARD: " + scoresReadable(hardTimeScore, hardClickScore) + "<br>" +
                "EXPERT: " + scoresReadable(expertTimeScore, expertClickScore) + "</div> </html>");

        scores.setFont(new Font(scores.getFont().getName(), Font.PLAIN, 18));
        scorePanel.add(scores, gbc);
        scorePanel.setBorder(new EmptyBorder(180, 0, 50, 0));
        mainPanel.add(scorePanel);
    }

    private String scoresReadable(int timeScore, int clickScore) {
        return "Time - " + view.timeMessage(timeScore) + ", Clicks - " + clickScore + "<br>";
    }

    private int getAppropriateDigitValue(String filePath) {
        return (scoreSaver.getContent(filePath) != null) ? Integer.parseInt(scoreSaver.getContent(filePath)) : 0;
    }
}