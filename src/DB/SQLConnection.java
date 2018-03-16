package DB;

import lib.LocalLogger;
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
        // System Path to the songDBs
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"DB"+System.getProperty("file.separator")+"songBook.sqlite";

        try {
            SQLiteConfig config = new SQLiteConfig(); //I add this configuration
            config.enforceForeignKeys(true);
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+filePath, config.toProperties());
            c.setAutoCommit(true);
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot establish connection to the DB in path: "+filePath);
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
