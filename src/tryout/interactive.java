package tryout;

import java.sql.*;
import java.util.Scanner;

public class interactive {
    static Scanner sc = new Scanner(System.in);
    static Integer showMenu(){
        System.out.println("\n\n\n** Main Menu **");
        System.out.println("1. Show all students");
        System.out.println("2. Create a new student");
        System.out.println("3. Update a student");
        System.out.println("4. Delete a student");
        System.out.println("5. Exit");
        System.out.println("Enter your choice (1-5): ");
        // Read the number

        int choice = sc.nextInt();
        //clean console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        return choice;
    }
    public static void main(String[] args) throws SQLException {
        while(true){
            int choice = showMenu();
            while(choice < 1 || choice > 5){
                System.out.println("Invalid choice, please try again\n\n");
                choice = showMenu();
            }
            if(choice == 5) break;
            if(choice == 1){
                Connection connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM " + Constants.TABLE_NAME);
                //print table header wth sufficient spacing using %s
                System.out.printf("%-10s %-20s %-15s %-10s%n", "Roll", "Name", "Department", "CGPA");
                while (rs.next()) {
                    System.out.printf("%-10d %-20s %-15s %-10.2f%n", rs.getInt("roll"), rs.getString("name"), rs.getString("dept"), rs.getDouble("cgpa"));
                }
            }
            if(choice == 2){
                System.out.println("Please enter Name, Department and CGPA separated by space (ex. Sanjay CS 3.8):");
                CRUD.createStudent(sc.next(), sc.next(), sc.nextDouble());
            }
            if(choice == 3){
                System.out.println("Please enter Roll, new Name, new Department and new CGPA separated by space (ex. 17 Sanjay CS 3.8):");
                CRUD.updateStudent(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble());
            }
            if(choice == 4){
                System.out.println("Enter the roll of the student to be deleted");
                int roll = sc.nextInt();
                CRUD.deleteStudent(roll);
            }
        }
        sc.close();
    }
}
