package presenter.gameplay;

import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import presenter.gameplay.gui.GUIGameplay;

import java.io.*;

public class ScoreSaver {
    private final GUIGameplay gameplay;
    private String clickScorePath;
    private String timeScorePath;
    private boolean isNewScore;
    private int oldTimeScore;
    private int oldClickScore;

    public ScoreSaver(GUIGameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void saveScores() {
        setFilePaths();
        isNewScore = false;

        if (!isFileEmpty(timeScorePath)) {
            if (isCurrentTimeLess(gameplay.getGameTimer().getSecondsTotal())) {
                oldTimeScore = Integer.parseInt(getContent(timeScorePath));
                saveTime();
                isNewScore = true;
            }
        } else {
            oldTimeScore = 0;
            saveTime();
            isNewScore = true;
        }

        if (!isFileEmpty(clickScorePath)) {
            if (areCurrentClicksLess(gameplay.getClickCount())) {
                oldClickScore = Integer.parseInt(getContent(clickScorePath));
                saveClicks();
                isNewScore = true;
            }
        } else {
            oldClickScore = 0;
            saveClicks();
            isNewScore = true;
        }
    }

    public String getTimeScore() {
        setFilePaths();
        return getContent(timeScorePath);
    }

    public String getClickScore() {
        setFilePaths();
        return getContent(clickScorePath);
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

    private void setFilePaths() {
        if (gameplay.getCurrentMatrix() instanceof Easy) {
            clickScorePath = "src/main/resources/scores/easy/clicks";
            timeScorePath = "src/main/resources/scores/easy/time";
        } else if (gameplay.getCurrentMatrix() instanceof Medium) {
            clickScorePath = "src/main/resources/scores/medium/clicks";
            timeScorePath = "src/main/resources/scores/medium/time";
        } else if (gameplay.getCurrentMatrix() instanceof Hard) {
            clickScorePath = "src/main/resources/scores/hard/clicks";
            timeScorePath = "src/main/resources/scores/hard/time";
        } else if (gameplay.getCurrentMatrix() instanceof Expert) {
            clickScorePath = "src/main/resources/scores/expert/clicks";
            timeScorePath = "src/main/resources/scores/expert/time";
        }
    }

    private void saveTime() {
        if (!isFileEmpty(timeScorePath)) deleteContent(timeScorePath);

        try (FileWriter fileWriter = new FileWriter(timeScorePath, true)) {
            fileWriter.write(String.valueOf(gameplay.getGameTimer().getSecondsTotal()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveClicks() {
        if (!isFileEmpty(clickScorePath)) deleteContent(clickScorePath);

        try (FileWriter fileWriter = new FileWriter(clickScorePath, true)) {
            fileWriter.write(String.valueOf(gameplay.getClickCount()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCurrentTimeLess(int seconds) {
        return Integer.parseInt(getContent(timeScorePath)) > seconds;
    }

    private boolean areCurrentClicksLess(int clicks) {
        return Integer.parseInt(getContent(clickScorePath)) > clicks;
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

    public boolean isNewScore() {
        return isNewScore;
    }

    public int getOldClickScore() {
        return oldClickScore;
    }

    public int getOldTimeScore() {
        return oldTimeScore;
    }
}