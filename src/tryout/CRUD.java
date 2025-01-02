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
  
    public static void updateStudent(int roll, String Name, String Dept, Double CGPA) throws SQLException {
        // SQL to update the student
        String sql = "UPDATE " + Constants.TABLE_NAME + " SET name = '" + Name + "', dept = '" + Dept + "', cgpa = " + CGPA + " WHERE roll = " + roll;
        // Check if there is a Student.Connection
        Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(sql);
        System.out.println("*****Update Student Performed*****");
        System.out.println(rowsAffected + " rows affected");
        System.out.println("The updated entry is:");
        sql = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE roll = " + roll;
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()) {
            System.out.println(rs.getInt("roll") + " " + rs.getString("name") + " " + rs.getString("dept") + " " + rs.getDouble("cgpa"));
        } else {
            System.out.println("No entry with roll " + roll + " found");
        }
    }
}
