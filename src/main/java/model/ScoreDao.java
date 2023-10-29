package model;

import java.sql.*;

public class ScoreDao {
    private final String JDBC_URL = "jdbc:sqlite:mydatabase.db";
    private static ScoreDao instance = null;

    public static ScoreDao getInstance() {
        if (instance == null) return new ScoreDao();
        return instance;
    }

    private ScoreDao() {
        connectToDb();
        createDb();
        setDefaultValues();
    }

    public void resetDb() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String updateSQL = "UPDATE scores SET clicks = 0, time = 0";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectToDb() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDb() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS scores (" +
                    "level TEXT, " +
                    "clicks INTEGER, " +
                    "time INTEGER)";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultValues() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String insertSQL = "INSERT INTO scores (level, clicks, time) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, "easy");
                preparedStatement.setInt(2, 0);
                preparedStatement.setInt(3, 0);
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "medium");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "hard");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "expert");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "custom");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getValue(String level, boolean isTime) {
        String scoreType;
        if (isTime) scoreType = "time";
        else scoreType = "clicks";

        int score = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String selectSQL = "SELECT " + scoreType + " FROM scores WHERE level = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, level);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        score = resultSet.getInt(scoreType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    private void replaceValue(String level, boolean isTime, int value) {
        String scoreType;
        if (isTime) scoreType = "time";
        else scoreType = "clicks";

        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String updateSQL = "UPDATE scores SET " + scoreType + " = ? WHERE level = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
                preparedStatement.setInt(1, value);
                preparedStatement.setString(2, level);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getEasyTime() {
        return getValue("easy", true);
    }

    public int getEasyClicks() {
        return getValue("easy", false);
    }

    public int getMediumTime() {
        return getValue("medium", true);
    }

    public int getMediumClicks() {
        return getValue("medium", false);
    }

    public int getHardTime() {
        return getValue("hard", true);
    }

    public int getHardClicks() {
        return getValue("hard", false);
    }

    public int getExpertTime() {
        return getValue("expert", true);
    }

    public int getExpertClicks() {
        return getValue("expert", false);
    }

    public int getCustomTime() {
        return getValue("custom", true);
    }

    public int getCustomClicks() {
        return getValue("custom", false);
    }

    public void setEasyTime(int value) {
        replaceValue("easy", true, value);
    }

    public void setEasyClicks(int value) {
        replaceValue("easy", false, value);
    }

    public void setMediumTime(int value) {
        replaceValue("medium", true, value);
    }

    public void setMediumClicks(int value) {
        replaceValue("medium", false, value);
    }

    public void setHardTime(int value) {
        replaceValue("hard", true, value);
    }

    public void setHardClicks(int value) {
        replaceValue("hard", false, value);
    }

    public void setExpertTime(int value) {
        replaceValue("expert", true, value);
    }

    public void setExpertClicks(int value) {
        replaceValue("expert", false, value);
    }

    public void setCustomTime(int value) {
        replaceValue("custom", true, value);
    }

    public void setCustomClicks(int value) {
        replaceValue("custom", false, value);
    }
}