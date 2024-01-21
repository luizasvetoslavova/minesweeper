package view.gui;

import model.mines.CellStatus;
import presenter.gameplay.GameTimer;
import presenter.gameplay.gui.GUIGameplay;
import view.gui.pages.TablePage;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUIView {
    private static final String FLAG_IMAGE = "/images/flag.png";
    private static final String BOMB_IMAGE = "/images/bomb.png";
    private static final String ZERO_IMAGE = "/images/none.png";
    private static final String ONE_IMAGE = "/images/one.png";
    private static final String TWO_IMAGE = "/images/two.png";
    private static final String THREE_IMAGE = "/images/three.png";
    private static final String FOUR_IMAGE = "/images/four.png";
    private static final String FIVE_IMAGE = "/images/five.png";
    private static final String SIX_IMAGE = "/images/six.png";
    private static final String SEVEN_IMAGE = "/images/seven.png";
    private static final String EIGHT_IMAGE = "/images/eight.png";

    private TablePage tablePage;
    private GUIGameplay guiGameplay;

    public GUIView() {
    }

    public GUIView(GUIGameplay guiGameplay) {
        this.guiGameplay = guiGameplay;
    }

    public void showMessage(String message) {
        UIManager.put("OptionPane.background", Color.decode("#F9E3FF"));
        UIManager.put("Panel.background", Color.decode("#F9E3FF"));
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);

        message = message.replace("\n", "<br>");
        JLabel messageLabel = new JLabel("<html><font face='Georgia' size='4'>" + message + "</font></html>");
        Font messageFont = new Font("Georgia", Font.PLAIN, 14);
        messageLabel.setFont(messageFont);

        JOptionPane optionPane = new JOptionPane(messageLabel, JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        optionPane.setFont(messageFont);
        JDialog dialog = optionPane.createDialog("MESSAGE");
        dialog.setVisible(true);
    }


    public void showAllBombs() {
        tablePage.getButtons()
                .stream()
                .filter(tableButton -> tableButton.getCell().isBomb())
                .forEach(tableButton -> setButtonImage(tableButton, BOMB_IMAGE));
    }

    public void showAllOpened() {
        tablePage.getButtons()
                .stream()
                .filter(tableButton -> tableButton.getCell().getCellStatus() == CellStatus.OPENED
                        && tableButton.getButton().getIcon() == null)
                .forEach(tableButton ->
                        setButtonImage(tableButton, setOpenDigit(tableButton.getCell().getDigit())));
    }


    public void setButtonImage(TableButton tableButton, String resource) {
        tableButton.getButton().setIcon(
                new ImageIcon(
                        new ImageIcon(Objects.requireNonNull(getClass().getResource(resource))).
                                getImage().getScaledInstance(tableButton.getButton().getWidth(),
                                        tableButton.getButton().getHeight(), Image.SCALE_SMOOTH)));
    }

    public String timeMessage(GameTimer gameTimer) {
        String hour = " hours ";
        String minute = " minutes ";
        String second = " seconds";

        if (gameTimer.getHours() == 1) hour = " hour ";
        if (gameTimer.getSecondsConverted() == 1) second = " second";
        if (gameTimer.getMinutesConverted() == 1) minute = " minute ";

        if (gameTimer.getHours() > 0) return gameTimer.getHours() + hour + gameTimer.getMinutesConverted() +
                minute + gameTimer.getSecondsConverted() + second;
        if (gameTimer.getMinutesConverted() > 0) return gameTimer.getMinutesConverted() + minute +
                gameTimer.getSecondsConverted() + second;
        return gameTimer.getSecondsConverted() + second;
    }

    public String timeMessage(int seconds) {
        GameTimer timer = new GameTimer();

        String hour = " hours ";
        String minute = " minutes ";
        String second = " seconds";

        int hours = timer.getHours(seconds);
        int minutes = timer.getMinutes(seconds, hours);
        int secondsConverted = timer.getSeconds(seconds, minutes);

        if (hours == 1) hour = " hour ";
        if (secondsConverted == 1) second = " second";
        if (minutes == 1) minute = " minute ";

        if (hours > 0) return hours + hour + minutes + minute + secondsConverted + second;
        if (minutes > 0) return minutes + minute + secondsConverted + second;
        return secondsConverted + second;
    }

    public void showInvalidSizeMessage() {
        showMessage("Invalid size! \n" +
                "Min size: 3x3 \n" +
                "Max size: 50x50");
    }

    public void showMessageOnLoss() {
        showMessage("BOOM! You stepped on a mine.\n");
    }

    public void showNewScoreMessage() {
        showMessage("NEW SCORE!\n" +
                getScoreInfo() +
                "Old time score for level: " + timeMessage(guiGameplay.getScoreSaver().getOldTimeScore()) + "\n" +
                "Old click score for level: " + guiGameplay.getScoreSaver().getOldClickScore());
    }

    public void showWinMessage() {
        showMessage("CONGRATULATIONS! You won.\n" +
                getScoreInfo() +
                "Best time score for level: " + timeMessage(guiGameplay.getScoreSaver().getTimeScore()) + "\n" +
                "Best click score for level: " + guiGameplay.getScoreSaver().getClickScore());
    }

    private String getScoreInfo() {
        return "Time: " + guiGameplay.getTime() + "\n" + "Clicks: " + guiGameplay.getClickCount() + "\n";
    }

    private String setOpenDigit(int digit) {
        return switch (digit) {
            case 0 -> ZERO_IMAGE;
            case 1 -> ONE_IMAGE;
            case 2 -> TWO_IMAGE;
            case 3 -> THREE_IMAGE;
            case 4 -> FOUR_IMAGE;
            case 5 -> FIVE_IMAGE;
            case 6 -> SIX_IMAGE;
            case 7 -> SEVEN_IMAGE;
            case 8 -> EIGHT_IMAGE;
            default -> BOMB_IMAGE;
        };
    }

    public String getFLAG_IMAGE() {
        return FLAG_IMAGE;
    }

    public void setTablePage(TablePage tablePage) {
        this.tablePage = tablePage;
    }
}