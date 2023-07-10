package view.gui;

import model.mines.CellStatus;

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