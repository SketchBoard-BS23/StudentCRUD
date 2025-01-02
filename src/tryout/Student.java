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
                            "Roll SERIAL PRIMARY KEY, " +
                            "Name VARCHAR(50) NOT NULL, " +
                            "Dept VARCHAR(50) NOT NULL, " +
                            "CGPA FLOAT NOT NULL)";
                    statement.executeUpdate(createTableSQL);
                }
            }
        }
    }

    public static void fillDummy() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String insertDataSQL = "INSERT INTO Student (Name, Dept, CGPA) VALUES " +
                    "('Souvik', 'CS', 3.0), " +
                    "('Sanjoy', 'CS', 4.0), " +
                    "('Asif', 'CS', 3.8)";
            statement.executeUpdate(insertDataSQL);
        }
    }

    public static void main(String[] args) throws SQLException {
        initializeDatabase();
        fillDummy();
        String sql = "SELECT Name FROM Student";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("Name"));
            }
        }
    }
}
