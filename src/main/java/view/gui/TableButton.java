package view.gui;

import model.mines.Cell;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableButton {
    private final JButton button;
    private final Cell cell;
    private int timesClicked;

    public TableButton(JButton button, Cell cell) {
        this.button = button;
        this.cell = cell;
        timesClicked = 0;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) timesClicked++;
            }
        });
    }

    public JButton getButton() {
        return button;
    }

    public Cell getCell() {
        return cell;
    }

    public int getTimesClicked() {
        return timesClicked;
    }
}