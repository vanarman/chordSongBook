package DB;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Perform connection do the local DB
 *
 * @author Dmytro Sytnik (VanArman)
 * @version 21 February, 2018
 */
public class SQLConnection {
    private Connection c;

    /**
     * Default constructor.
     */
    public SQLConnection(){
        try {
            SQLiteConfig config = new SQLiteConfig(); //I add this configuration
            config.enforceForeignKeys(true);
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            // db parameters songDB
            String url = "jdbc:sqlite:songBook.sqlite";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url, config.toProperties());
            c.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Connection to the DB cannot be performed.");
            System.out.println(e.getMessage());
            c = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return a connection
     *
     * @return Connection
     */
    public Connection getConnection() {
        return c;
    }
}
