package presenter.gameplay;

import model.ScoreDao;
import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import presenter.gameplay.gui.GUIGameplay;

public class ScoreSaver {
    private final GUIGameplay gameplay;
    private int timeScore;
    private int clickScore;
    private boolean isNewScore;
    private int oldTimeScore;
    private int oldClickScore;

    public ScoreSaver(GUIGameplay gameplay) {
        this.gameplay = gameplay;
        new ScoreDao();
    }

    public void saveScores() {
        setOldScores();
        isNewScore = false;
        updateScores(oldTimeScore, gameplay.getGameTimer().getSecondsTotal());
        updateScores(oldClickScore, gameplay.getClickCount());
    }

    private void updateScores(int oldScore, int newScore) {
        timeScore = oldTimeScore;
        clickScore = oldClickScore;

        if (oldClickScore == 0 || oldTimeScore == 0) {
            isNewScore = true;
            timeScore = gameplay.getGameTimer().getSecondsTotal();
            clickScore = gameplay.getClickCount();
        } else if (isCurrentScoreHigher(oldScore, newScore)) {
            isNewScore = true;
            timeScore = gameplay.getGameTimer().getSecondsTotal();
            clickScore = gameplay.getClickCount();
        }
        updateScores();
    }

    private void setOldScores() {
        if (gameplay.getCurrentMatrix() instanceof Easy) {
            oldClickScore = ScoreDao.getEasyClicks();
            oldTimeScore = ScoreDao.getEasyTime();
        } else if (gameplay.getCurrentMatrix() instanceof Medium) {
            oldClickScore = ScoreDao.getMediumClicks();
            oldTimeScore = ScoreDao.getMediumTime();
        } else if (gameplay.getCurrentMatrix() instanceof Hard) {
            oldClickScore = ScoreDao.getHardClicks();
            oldTimeScore = ScoreDao.getHardTime();
        } else if (gameplay.getCurrentMatrix() instanceof Expert) {
            oldClickScore = ScoreDao.getExpertClicks();
            oldTimeScore = ScoreDao.getExpertTime();
        }
    }

    private void updateScores() {
        if (gameplay.getCurrentMatrix() instanceof Easy) {
            ScoreDao.setEasyClicks(clickScore);
            ScoreDao.setEasyTime(timeScore);
        } else if (gameplay.getCurrentMatrix() instanceof Medium) {
            ScoreDao.setMediumClicks(clickScore);
            ScoreDao.setMediumTime(timeScore);
        } else if (gameplay.getCurrentMatrix() instanceof Hard) {
            ScoreDao.setHardClicks(clickScore);
            ScoreDao.setHardTime(timeScore);
        } else if (gameplay.getCurrentMatrix() instanceof Expert) {
            ScoreDao.setExpertClicks(clickScore);
            ScoreDao.setExpertTime(timeScore);
        }
    }

    private boolean isCurrentScoreHigher(int oldScore, int newScore) {
        return oldScore > newScore;
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

    public int getTimeScore() {
        return timeScore;
    }

    public int getClickScore() {
        return clickScore;
    }
}