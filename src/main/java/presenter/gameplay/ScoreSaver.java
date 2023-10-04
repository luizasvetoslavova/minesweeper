package presenter.gameplay;

import model.ScoreDao;
import model.levels.Easy;
import model.levels.Expert;
import model.levels.Hard;
import model.levels.Medium;
import presenter.gameplay.gui.GUIGameplay;

public class ScoreSaver {
    private ScoreDao scoreDao;
    private final GUIGameplay gameplay;
    private int timeScore;
    private int clickScore;
    private boolean isNewScore;
    private int oldTimeScore;
    private int oldClickScore;

    public ScoreSaver(GUIGameplay gameplay) {
        this.gameplay = gameplay;
        scoreDao = ScoreDao.getInstance();
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
            oldClickScore = scoreDao.getEasyClicks();
            oldTimeScore = scoreDao.getEasyTime();
        } else if (gameplay.getCurrentMatrix() instanceof Medium) {
            oldClickScore = scoreDao.getMediumClicks();
            oldTimeScore = scoreDao.getMediumTime();
        } else if (gameplay.getCurrentMatrix() instanceof Hard) {
            oldClickScore = scoreDao.getHardClicks();
            oldTimeScore = scoreDao.getHardTime();
        } else if (gameplay.getCurrentMatrix() instanceof Expert) {
            oldClickScore = scoreDao.getExpertClicks();
            oldTimeScore = scoreDao.getExpertTime();
        }
    }

    private void updateScores() {
        if (gameplay.getCurrentMatrix() instanceof Easy) {
            scoreDao.setEasyClicks(clickScore);
            scoreDao.setEasyTime(timeScore);
        } else if (gameplay.getCurrentMatrix() instanceof Medium) {
            scoreDao.setMediumClicks(clickScore);
            scoreDao.setMediumTime(timeScore);
        } else if (gameplay.getCurrentMatrix() instanceof Hard) {
            scoreDao.setHardClicks(clickScore);
            scoreDao.setHardTime(timeScore);
        } else if (gameplay.getCurrentMatrix() instanceof Expert) {
            scoreDao.setExpertClicks(clickScore);
            scoreDao.setExpertTime(timeScore);
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