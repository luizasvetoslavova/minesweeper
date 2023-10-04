package model;

public class ScoreDao {
    private static ScoreDao instance = null;
    private static int EASY_TIME = 0;
    private static int EASY_CLICKS = 0;
    private static int MEDIUM_TIME = 0;
    private static int MEDIUM_CLICKS = 0;
    private static int HARD_TIME = 0;
    private static int HARD_CLICKS = 0;
    private static int EXPERT_TIME = 0;
    private static int EXPERT_CLICKS = 0;

    public static ScoreDao getInstance() {
        if (instance == null) return new ScoreDao();
        return instance;
    }

    private ScoreDao() {
        //TODO
        //-> create database
        //-> save default scores
        //-> for each 'set' method update database
        //-> for each 'get' method extract from database
        //-> add methods for extraction, writing and deletion
    }

    public int getEasyTime() {
        return EASY_TIME;
    }

    public int getEasyClicks() {
        return EASY_CLICKS;
    }

    public int getMediumTime() {
        return MEDIUM_TIME;
    }

    public int getMediumClicks() {
        return MEDIUM_CLICKS;
    }

    public int getHardTime() {
        return HARD_TIME;
    }

    public int getHardClicks() {
        return HARD_CLICKS;
    }

    public int getExpertTime() {
        return EXPERT_TIME;
    }

    public int getExpertClicks() {
        return EXPERT_CLICKS;
    }

    public void setEasyTime(int easyTime) {
        EASY_TIME = easyTime;
    }

    public void setEasyClicks(int easyClicks) {
        EASY_CLICKS = easyClicks;
    }

    public void setMediumTime(int mediumTime) {
        MEDIUM_TIME = mediumTime;
    }

    public void setMediumClicks(int mediumClicks) {
        MEDIUM_CLICKS = mediumClicks;
    }

    public void setHardTime(int hardTime) {
        HARD_TIME = hardTime;
    }

    public void setHardClicks(int hardClicks) {
        HARD_CLICKS = hardClicks;
    }

    public void setExpertTime(int expertTime) {
        EXPERT_TIME = expertTime;
    }

    public void setExpertClicks(int expertClicks) {
        EXPERT_CLICKS = expertClicks;
    }
}