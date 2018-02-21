package DB;

import java.sql.Connection;

/**
 * Perform connection to the DB and allows extract and modify data in local DB
 *
 * @author Dmytro Sytnik (VanArman)
 * @version 21 February, 2018
 */
public class DBExtractionModification {
    private Connection c;

    /*
    * Default constructor
    */
    public DBExtractionModification() {
        this.c = new SQLConnection().getConnection();
    }


}
