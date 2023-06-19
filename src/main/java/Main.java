import Gameplay.ConsoleGameplay;
import Mines.Initializer;
import View.ConsoleView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new ConsoleGameplay(new ConsoleView(new Scanner(System.in)), Initializer.getInstance()).start();
    }
}