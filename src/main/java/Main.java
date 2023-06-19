import presenter.gameplay.ConsoleGameplay;
import model.mines.Initializer;
import view.ConsoleView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new ConsoleGameplay(new ConsoleView(new Scanner(System.in)), Initializer.getInstance()).start();
    }
}