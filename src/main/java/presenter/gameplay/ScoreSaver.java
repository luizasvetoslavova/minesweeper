package presenter.gameplay;

import presenter.gameplay.gui.GUIGameplay;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreSaver {
    private final GUIGameplay gameplay;
    private final String leastClicksPath = "src/main/java/model/scores/clicks";
    private final String leastTimePath = "src/main/java/model/scores/time";

    public ScoreSaver(GUIGameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void saveScores() {
        saveTime();
        saveClicks();
    }

    private void saveTime() {
        if (!isFileEmpty(leastTimePath)) deleteFileContent(leastTimePath);

        try (FileWriter fileWriter = new FileWriter(leastTimePath, true)) {
            fileWriter.write(gameplay.getTime());
        } catch (IOException e) {
            System.err.println("Error writing to the text file: " + e.getMessage());
        }
    }

    private void saveClicks() {
        if (!isFileEmpty(leastClicksPath)) deleteFileContent(leastClicksPath);

        try (FileWriter fileWriter = new FileWriter(leastClicksPath, true)) {
            fileWriter.write(String.valueOf(gameplay.getOpenedCount()));
        } catch (IOException e) {
            System.err.println("Error writing to the text file: " + e.getMessage());
        }
    }

    private boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() == 0;
    }

    private void deleteFileContent(String filePath) {
        try {
            new FileWriter(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}