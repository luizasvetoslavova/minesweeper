package view.gui.pages;

import javax.swing.*;

public class BasePage extends JFrame {
    private final HomePage homePage;

    public BasePage() {
        setTitle("MINESWEEPER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        homePage = new HomePage();
        add(homePage);
        setVisible(true);
    }

    public HomePage getHomePage() {
        return homePage;
    }
}
