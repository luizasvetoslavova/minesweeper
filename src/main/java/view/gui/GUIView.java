package view.gui;

import model.mines.CellStatus;
import presenter.gameplay.GameTimer;
import view.gui.pages.TablePage;

import javax.swing.*;
import java.awt.*;

public class GUIView {
    private final String FLAG_IMAGE = "/images/flag.png";
    private final String BOMB_IMAGE = "/images/bomb.png";
    private final String ZERO_IMAGE = "/images/none.png";
    private final String ONE_IMAGE = "/images/one.png";
    private final String TWO_IMAGE = "/images/two.png";
    private final String THREE_IMAGE = "/images/three.png";
    private final String FOUR_IMAGE = "/images/four.png";
    private final String FIVE_IMAGE = "/images/five.png";
    private final String SIX_IMAGE = "/images/six.png";
    private final String SEVEN_IMAGE = "/images/seven.png";
    private final String EIGHT_IMAGE = "/images/eight.png";

    private TablePage tablePage;

    public void showAllBombs() {
        tablePage.getButtons()
                .stream()
                .filter(tableButton -> tableButton.getCell().isBomb())
                .forEach(tableButton -> setButtonImage(tableButton, BOMB_IMAGE));
    }

    public void showAllOpened() {
        tablePage.getButtons()
                .stream()
                .filter(tableButton -> tableButton.getCell().getCellStatus() == CellStatus.OPENED)
                .forEach(tableButton ->
                        setButtonImage(tableButton, setOpenDigit(tableButton.getCell().getDigit())));
    }

    public void setButtonImage(TableButton tableButton, String resource) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resource));
        Image image = icon.getImage().getScaledInstance(tableButton.getButton().getWidth(),
                tableButton.getButton().getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        tableButton.getButton().setIcon(scaledIcon);
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
        JOptionPane.showMessageDialog(null, "Invalid size! \n" +
                "Min size: 3x3 \n" +
                "Max size: 50x50");
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