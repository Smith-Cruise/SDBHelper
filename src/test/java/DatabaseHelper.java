import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Smith on 2017/3/28.
 */
public class DatabaseHelper {
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        DRIVER = "com.mysql.cj.jdbc.Driver";
        URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
        USERNAME = "root";
        PASSWORD = "";

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("can't find jdbc driver");
        }
    }

    static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
