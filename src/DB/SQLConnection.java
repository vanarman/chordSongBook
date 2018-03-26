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
class SQLConnection {
    private Connection c;

    /**
     * Default constructor.
     */
    public SQLConnection(){
        // System Path to the songDBs
//        String url = getClass().getResource("songBook.sqlite").getFile();
//        System.out.println(url);


            SQLiteConfig config = new SQLiteConfig(); //I add this configuration
            config.enforceForeignKeys(true);
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            // create a connection to the database
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            new LocalLogger().logInfo("Class.forName: "+e.toString());
        }
        try {
            c = DriverManager.getConnection("jdbc:sqlite:songBook.sqlite", config.toProperties());
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot establish connection to the DB."+e.getSQLState());
            e.printStackTrace();
        }
        try {
            c.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        } catch (SQLException e) {
//            new LocalLogger().logInfo("Cannot establish connection to the DB in path: "+url);
//            c = null;
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

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
