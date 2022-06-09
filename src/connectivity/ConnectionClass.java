package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionClass {
public Connection connection;


    public Connection getConnection() throws ClassNotFoundException, SQLException {

        String dbName="bankdatabase";
        String userName="root";
        String password="toor";

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);


        return connection;
    }
}
