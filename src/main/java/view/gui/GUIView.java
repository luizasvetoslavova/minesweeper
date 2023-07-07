package view.gui;

import model.mines.CellStatus;

import javax.swing.*;
import java.awt.*;

public class GUIView {
    private final String FLAG_IMAGE = "src/main/resources/flag.png";
    private final String BOMB_IMAGE = "src/main/resources/bomb.png";
    private final String ZERO_IMAGE = "src/main/resources/zero.png";
    private final String ONE_IMAGE = "src/main/resources/one.png";
    private final String TWO_IMAGE = "src/main/resources/two.png";
    private final String THREE_IMAGE = "src/main/resources/three.png";
    private final String FOUR_IMAGE = "src/main/resources/four.png";
    private final String FIVE_IMAGE = "src/main/resources/five.png";
    private final String SIX_IMAGE = "src/main/resources/six.png";
    private final String SEVEN_IMAGE = "src/main/resources/seven.png";
    private final String EIGHT_IMAGE = "src/main/resources/eight.png";

    private TablePage tablePage;

    public void showAllBombs() {
        tablePage.getButtons().forEach(tableButton -> {
            if (tableButton.getCell().isBomb()) {
                setButtonImage(tableButton, BOMB_IMAGE);
            }
        });
    }

    public void showAllOpened() {
        tablePage.getButtons().forEach(tableButton -> {
            if (tableButton.getCell().getCellStatus().equals(CellStatus.OPENED)) {
                setButtonImage(tableButton, setOpenDigit(tableButton.getCell().getDigit()));
            }
        });
    }

    public void setButtonImage(TableButton tableButton, String resource) {
        Image image = new ImageIcon(resource).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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