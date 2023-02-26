import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utils {
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "tedi123456789T!");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);
    }
}
