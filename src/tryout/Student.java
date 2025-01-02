package tryout;

import java.sql.*;

public class Student {
    private static Statement statement;
    private static final String URL = Constants.DB_URL;
    private static final String USER = Constants.DB_USER;
    private static final String PASSWORD = Constants.DB_PASSWORD;

    public static void initializeDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            statement = connection.createStatement();
            String checkTableSQL = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'student'";
            try (ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
                if (!resultSet.next() || resultSet.getInt(1) == 0) {
                    String createTableSQL = "CREATE TABLE IF NOT EXISTS Student (" +
                            "roll SERIAL PRIMARY KEY, " +
                            "name VARCHAR(50) NOT NULL, " +
                            "dept VARCHAR(50) NOT NULL, " +
                            "cgpa FLOAT NOT NULL)";
                    statement.executeUpdate(createTableSQL);
                }
            }
        }
    }

    public static void fillDummy() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String insertDataSQL = "INSERT INTO student (name, dept, cgpa) VALUES " +
                    "('Souvik', 'CS', 3.0), " +
                    "('Sanjoy', 'CS', 4.0), " +
                    "('Asif', 'CS', 3.8)";
            statement.executeUpdate(insertDataSQL);
        }
    }

    public static void main(String[] args) throws SQLException {
        initializeDatabase();
        String checkDataSQL = "SELECT COUNT(*) FROM student";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkDataSQL)) {
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                fillDummy();
            }
        }
       
        String sql = "SELECT name FROM " + Constants.TABLE_NAME; // Use double quotes to preserve case
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        }

        //call create to add one more entry
        CRUD.createStudent("AsifProPlus", "Universe", 4.1);
    }
}
