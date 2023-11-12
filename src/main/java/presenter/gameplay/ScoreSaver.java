package presenter.gameplay;

import model.ScoreDao;
import model.levels.*;
import presenter.gameplay.gui.GUIGameplay;

public class ScoreSaver {
    private final ScoreDao scoreDao;
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
        updateScores();
    }

    private void updateScores() {
        timeScore = oldTimeScore;
        clickScore = oldClickScore;
        isNewScore = false;

        if (timeScore == 0 || clickScore == 0) {
            isNewScore = true;
            timeScore = gameplay.getGameTimer().getSecondsTotal();
            clickScore = gameplay.getClickCount();
        }
        if (isNewScoreHigher(gameplay.getGameTimer().getSecondsTotal(), timeScore)) {
            isNewScore = true;
            timeScore = gameplay.getGameTimer().getSecondsTotal();
        }
        if (isNewScoreHigher(gameplay.getClickCount(), clickScore)) {
            isNewScore = true;
            clickScore = gameplay.getClickCount();
        }
        updateScoresInDao();
    }

    private void updateScoresInDao() {
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
        } else if (gameplay.getCurrentMatrix() instanceof Custom) {
            scoreDao.setCustomClicks(clickScore);
            scoreDao.setCustomTime(timeScore);
        }
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
        } else if (gameplay.getCurrentMatrix() instanceof Custom) {
            oldClickScore = scoreDao.getCustomClicks();
            oldTimeScore = scoreDao.getCustomTime();
        }
    }

    private boolean isNewScoreHigher(int newScore, int oldScore) {
        return newScore < oldScore;
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