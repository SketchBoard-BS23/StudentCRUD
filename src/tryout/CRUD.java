package tryout;

import java.sql.*;

public class CRUD {
    public static void createStudent(String Name, String Dept, Double CGPA) throws SQLException {

        // postgresql to create the student
        String sql = "INSERT INTO " + Constants.TABLE_NAME + " (name, dept, cgpa) VALUES('" + Name + "', '" + Dept + "', " + CGPA + ")";
        //check if there is Student.Connection
        Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(sql);
        System.out.println("*****Create Student Performed*****");
        System.out.println(rowsAffected + " rows affected");
        //now check the last entry
        sql = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY roll DESC LIMIT 1";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        System.out.println("The entry is:");
        System.out.println(rs.getInt("roll") + " " + rs.getString("name") + " " + rs.getString("dept") + " " + rs.getDouble("cgpa"));
    }
}
