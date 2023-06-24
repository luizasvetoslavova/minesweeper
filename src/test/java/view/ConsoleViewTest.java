package view;

import model.levels.Hard;
import model.mines.Matrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Scanner;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsoleViewTest {
    private ConsoleView view;
    private Matrix matrix;

    @BeforeAll
    private void setUp() {
        view = new ConsoleView(new Scanner(System.in));
        matrix = new Hard();
        view.setMatrix(matrix);
    }

    @Test
    void testShow_WhenStringGiven_ThenPrintInConsole() {

    }

    @Test
    void testUserInput_WhenStringGiven_ThenReturnInputToString() {

    }

    @Test
    void testShowFront_WhenGameStepIsTaken_ThenShowOpenedCellsAndColumnIndexes() {

    }

    @Test
    void testGetLineAndCol_WhenGivenRightParameters_ThenReturnArray() {

    }

    @Test
    void testGetLineAndCol_WhenGivenWrongParameters_ThenRecurseUntilGivenRightParameters() {

    }
}