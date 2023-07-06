package presenter.gameplay.gui;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import model.mines.Cell;
import model.mines.CellStatus;
import model.mines.Initializer;
import model.mines.Matrix;
import presenter.gameplay.Gameplay;
import view.gui.HomePage;
import view.gui.TableButton;
import view.gui.TablePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIGameplay implements Gameplay{
    private final String FLAG_IMAGE = "src/main/resources/flag.png";
    private final String BOMB_IMAGE = "src/main/resources/bomb.png";
    private final String ONE_IMAGE = "src/main/resources/one.png";
    private final String TWO_IMAGE = "src/main/resources/two.png";
    private final String THREE_IMAGE = "src/main/resources/three.png";
    private final String FOUR_IMAGE = "src/main/resources/four.png";
    private final String FIVE_IMAGE = "src/main/resources/five.png";
    private final String SIX_IMAGE = "src/main/resources/six.png";
    private final String SEVEN_IMAGE = "src/main/resources/seven.png";
    private final String EIGHT_IMAGE = "src/main/resources/eight.png";

    private int openedCount;

    private final HomePage homePage;
    private TablePage currentTablePage;
    private Matrix currentMatrix;

    public GUIGameplay(HomePage homePage) {
        this.homePage = homePage;
        openedCount = 0;
    }

    @Override
    public void rules() {
        homePage.setRules("Welcome to Minesweeper!" + "<br>" + "<br>" + "Rules:" + "<br>" +
                "1. The number shown on an opened cell is the number of mines (bombs) adjacent to it." + "<br>" +
                "2. You have to flag all the mines and not unlock on a single one, or else you lose and " +
                "the game ends." + "<br>" + "You can start by clicking at any random cell." + "<br>" + "<br>" +
                "Signs:" + "<br>" + "◽ - Empty cell. There are no bombs near it." + "<br>" + "⬛ - Bomb." + "<br>" +
                "⬜ - Unopened cell." + "<br>" + "⛳ - Flag." + "<br>" + "<br>" + "Left click to open a cell. " +
                "Right click to flag.");
    }

    @Override
    public void start() {
        rules();
        homePage.initHome();
        levelChoice();

    }

    @Override
    public void levelChoice() {
        setupHomeButton(homePage.getEasy(), new Easy(), "EASY");
        setupHomeButton(homePage.getMedium(), new Medium(), "MEDIUM");
        setupHomeButton(homePage.getHard(), new Hard(), "HARD");
        setupHomeButton(homePage.getExpert(), new Expert(), "EXPERT");
    }

    @Override
    public void openCell() {
        openedCount++;

        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Thread openCellThread = new Thread(() -> {
                        if (openedCount == 1) {
                            Initializer.getInstance().initOnFirstClick(tableButton.getCell(), openedCount);
                        }
                        tableButton.getCell().setCellStatus(CellStatus.OPENED);
                        setButtonImage(tableButton, setOpenDigit(tableButton.getCell().getDigit()));
                    });
                    openCellThread.start();
                }
            });
        });
    }

    private String setOpenDigit(int digit) {
        return switch (digit) {
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

    @Override
    public void putFlag() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (!isEven(tableButton.getTimesClicked()) &&
                                !tableButton.getCell().getCellStatus().equals(CellStatus.OPENED)) {
                            tableButton.getCell().setCellStatus(CellStatus.FLAGGED);
                            setButtonImage(tableButton, FLAG_IMAGE);
                        }
                    }
                }
            });
        });
    }

    @Override
    public void removeFlag() {
        currentTablePage.getButtons().forEach(tableButton -> {
            tableButton.getButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (isEven(tableButton.getTimesClicked())
                                && tableButton.getCell().getCellStatus().equals(CellStatus.FLAGGED)) {
                            tableButton.getCell().setCellStatus(CellStatus.UNOPENED);
                            tableButton.getButton().setIcon(null);
                        }
                    }
                }
            });
        });
    }

    @Override
    public void win() {

    }

    @Override
    public void lose(Cell cell) {

    }

    @Override
    public void reset() {

    }

    private void hideHome() {
        homePage.getFrame().setVisible(false);
    }

    private void setupHomeButton(JButton button, Matrix matrix, String heading) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideHome();
                currentTablePage = new TablePage(matrix, homePage, heading);
                currentMatrix = currentTablePage.getMatrix();

                Initializer.getInstance().setMatrix(currentMatrix);

                currentTablePage.draw();
                buttonOperations();
            }
        });
    }

    private void buttonOperations() {
        openCell();
        putFlag();
        removeFlag();
    }

    private void setButtonImage(TableButton tableButton, String resource) {
        Image image = new ImageIcon(resource).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        tableButton.getButton().setIcon(scaledIcon);
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }
}