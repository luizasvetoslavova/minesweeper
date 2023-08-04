package model;

public class ScoreDao {
    private static int EASY_TIME = 0;
    private static int EASY_CLICKS = 0;
    private static int MEDIUM_TIME = 0;
    private static int MEDIUM_CLICKS = 0;
    private static int HARD_TIME = 0;
    private static int HARD_CLICKS = 0;
    private static int EXPERT_TIME = 0;
    private static int EXPERT_CLICKS = 0;

    public ScoreDao() {
        //TODO
        //-> create database
        //-> save default scores
        //-> for each 'set' method update database
        //-> for each 'get' method extract from database
        //-> add methods for extraction, writing and deletion
    }

    public static int getEasyTime() {
        return EASY_TIME;
    }

    public static int getEasyClicks() {
        return EASY_CLICKS;
    }

    public static int getMediumTime() {
        return MEDIUM_TIME;
    }

    public static int getMediumClicks() {
        return MEDIUM_CLICKS;
    }

    public static int getHardTime() {
        return HARD_TIME;
    }

    public static int getHardClicks() {
        return HARD_CLICKS;
    }

    public static int getExpertTime() {
        return EXPERT_TIME;
    }

    public static int getExpertClicks() {
        return EXPERT_CLICKS;
    }

    public static void setEasyTime(int easyTime) {
        EASY_TIME = easyTime;
    }

    public static void setEasyClicks(int easyClicks) {
        EASY_CLICKS = easyClicks;
    }

    public static void setMediumTime(int mediumTime) {
        MEDIUM_TIME = mediumTime;
    }

    public static void setMediumClicks(int mediumClicks) {
        MEDIUM_CLICKS = mediumClicks;
    }

    public static void setHardTime(int hardTime) {
        HARD_TIME = hardTime;
    }

    public static void setHardClicks(int hardClicks) {
        HARD_CLICKS = hardClicks;
    }

    public static void setExpertTime(int expertTime) {
        EXPERT_TIME = expertTime;
    }

    public static void setExpertClicks(int expertClicks) {
        EXPERT_CLICKS = expertClicks;
    }
}