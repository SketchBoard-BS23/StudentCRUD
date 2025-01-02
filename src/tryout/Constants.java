package tryout;

public final class Constants {
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Define string constants
    public static final String DB_NAME = "student_crud";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "0000";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    public static final String TABLE_NAME = "student";

}
