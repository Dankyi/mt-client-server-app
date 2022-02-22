import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
        public Connection connection;

    public Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://selene.hud.ac.uk:3306/";
        String dbName = "u1669769";
        String username = "u1669769";
        String password = "EB01feb21eb";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url+dbName, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        return connection;
    }
    
}