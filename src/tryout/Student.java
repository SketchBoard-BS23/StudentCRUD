package tryout;

import java.sql.*;
import tryout.Constants;

public class Student {
    public static void main(String[] args) throws SQLException {
        String sql = "SELECT name FROM Student";
        // Add from constants
        String url = Constants.DB_URL;
        String user = Constants.DB_USER;
        String password = Constants.DB_PASSWORD;
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        System.out.println(rs.getString("name"));
    }
}
