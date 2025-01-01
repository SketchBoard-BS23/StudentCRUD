package tryout;

import java.sql.*;

public class Student {
    public static void main(String[] args) throws SQLException {
        String sql = "SELECT name FROM Student";
        String url = "jdbc:postgresql://localhost:5432/student_crud";
        String user = "postgres";
        String password = "0000";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        System.out.println(rs.getString("name"));
    }
}
