package presenter.gameplay;

import model.levels.Hard;
import model.mines.Initializer;
import model.mines.Matrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import presenter.gameplay.console.ConsoleGameplay;
import view.ConsoleView;

import java.util.Scanner;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsoleGameplayTest {
    private Gameplay gameplay;
    private Matrix matrix;

    @BeforeAll
    private void setUp() {
        gameplay = new ConsoleGameplay(new ConsoleView(new Scanner(System.in)), Initializer.getInstance(),
                new NeighborOpener());
        matrix = new Hard();
    }
}