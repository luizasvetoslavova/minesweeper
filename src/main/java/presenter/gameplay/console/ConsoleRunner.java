package presenter.gameplay.console;

import model.mines.Initializer;
import presenter.gameplay.CellOpener;
import view.console.ConsoleView;

import java.util.Scanner;

public class ConsoleRunner {
    public static void main(String[] args) {
        new ConsoleGameplay(new ConsoleView(new Scanner(System.in)), Initializer.getInstance(), new CellOpener())
                .start();
    }
}