package tryout;

import java.sql.*;

public class Student {
    private static Statement statement;

    private static boolean databaseExists(Statement statement, String dbName) throws SQLException {
        String query = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
        try (var rs = statement.executeQuery(query)) {
            return rs.next();
        }
    }

    public static void initializeDatabase() throws SQLException {

        try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            if (!databaseExists(statement, Constants.DB_NAME)) {
                statement.executeUpdate("CREATE DATABASE " + Constants.DB_NAME);
                System.out.println("Database created.");
            } else {
                System.out.println("Database already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD)) {
            statement = connection.createStatement();
            String checkTableSQL = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = " + "'" + Constants.TABLE_NAME + "'";
            try (ResultSet resultSet = statement.executeQuery(checkTableSQL)) {
                if (!resultSet.next() || resultSet.getInt(1) == 0) {
                    System.out.println( "Table does not exist. Creating table...");
                    String createTableSQL = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + " (" +
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
        try (Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
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
        String checkDataSQL = "SELECT COUNT(*) FROM " + Constants.TABLE_NAME;
        try (Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkDataSQL)) {
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                fillDummy();
            }
        }
       
//        String sql = "SELECT name FROM " + Constants.TABLE_NAME; // Use double quotes to preserve case
//        try (Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
//             Statement statement = connection.createStatement();
//             ResultSet rs = statement.executeQuery(sql)) {
//            while (rs.next()) {
//                System.out.println(rs.getString("name"));
//            }
//        }

        //call create to add one more entry
//        CRUD.createStudent("AsifProPlus", "Universe", 4.1);
//        CRUD.updateStudent(17,"AsifProMaxPlus", "Multiverse", 10.0);
        interactive.main(args);
    }
}
