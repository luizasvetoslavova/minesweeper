package presenter.gameplay;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import presenter.gameplay.gui.GUIGameplay;

import java.io.*;

public class ScoreSaver {
    private final GUIGameplay gameplay;
    private String leastClicksPath;
    private String leastTimePath;

    public ScoreSaver(GUIGameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void saveScores() {
        setFilePaths();
        if (isFileEmpty(leastTimePath)) saveTime();
        else if (isCurrentTimeLess(gameplay.getGameTimer().getSecondsTotal())) saveTime();

        if (isFileEmpty(leastClicksPath)) saveClicks();
        else if (areCurrentClicksLess(gameplay.getClickCount())) saveClicks();
    }

    public String getTimeScore() {
        setFilePaths();
        return getContent(leastTimePath);
    }

    public String getClickScore() {
        setFilePaths();
        return getContent(leastClicksPath);
    }

    private void setFilePaths() {
        if (gameplay.getCurrentMatrix() instanceof Easy) {
            leastClicksPath = "src/main/java/model/scores/easy/clicks";
            leastTimePath = "src/main/java/model/scores/easy/time";
        } else if (gameplay.getCurrentMatrix() instanceof Medium) {
            leastClicksPath = "src/main/java/model/scores/medium/clicks";
            leastTimePath = "src/main/java/model/scores/medium/time";
        } else if (gameplay.getCurrentMatrix() instanceof Hard) {
            leastClicksPath = "src/main/java/model/scores/hard/clicks";
            leastTimePath = "src/main/java/model/scores/hard/time";
        } else if (gameplay.getCurrentMatrix() instanceof Expert) {
            leastClicksPath = "src/main/java/model/scores/expert/clicks";
            leastTimePath = "src/main/java/model/scores/expert/time";
        }
    }

    private void saveTime() {
        if (!isFileEmpty(leastTimePath)) deleteContent(leastTimePath);

        try (FileWriter fileWriter = new FileWriter(leastTimePath, true)) {
            fileWriter.write(String.valueOf(gameplay.getGameTimer().getSecondsTotal()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveClicks() {
        if (!isFileEmpty(leastClicksPath)) deleteContent(leastClicksPath);

        try (FileWriter fileWriter = new FileWriter(leastClicksPath, true)) {
            fileWriter.write(String.valueOf(gameplay.getClickCount()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCurrentTimeLess(int seconds) {
        return Integer.parseInt(getContent(leastTimePath)) > seconds;
    }

    private boolean areCurrentClicksLess(int clicks) {
        return Integer.parseInt(getContent(leastClicksPath)) > clicks;
    }

    private boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() == 0;
    }

    private void deleteContent(String filePath) {
        try {
            new FileWriter(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContent(String filePath) {
        String content = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content = line;
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}