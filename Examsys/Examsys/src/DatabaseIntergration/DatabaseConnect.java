package DatabaseIntergration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Connect to DB
public class DatabaseConnect {

    Connection conn;

    public Connection getConnection() {
        try {
            //Check driver and try connection if successfull return connection
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolexamdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error in conncetion: " + ex);
            return (Connection) ex;
            //return null;
        }
    }

}
