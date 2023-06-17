package View;

import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show(String string) {
        System.out.print(string);
    }

    public String userInput() {
        return scanner.nextLine();
    }
}