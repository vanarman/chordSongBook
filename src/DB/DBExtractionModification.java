package DB;

import dataStructures.AuthorNode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Perform connection to the DB and allows extract and modify data in local DB
 *
 * @author Dmytro Sytnik (VanArman)
 * @version 21 February, 2018
 */
public class DBExtractionModification {
    private Connection c;

    /**
     * Default constructor that creates connection to the DB
     */
    public DBExtractionModification() {
        this.c = new SQLConnection().getConnection();
    }

    public ArrayList<AuthorNode> getAuthorsList(int id){
        Statement statement = null;
        ResultSet result = null;
        ArrayList<AuthorNode> authorScope = new ArrayList<AuthorNode>();

        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            System.out.println("Cannot create statement for getAuthorData!!!");
        }

        String sql = "SELECT * FROM author ";

        if(id > 0){
            sql += "WHERE id = "+ id;
        }

        try {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Cannot execute getAuthorData query!!!\nSQL QUERY: "+ sql);
        }

        try {
            while (result != null && result.next()){
                authorScope.add(new AuthorNode(result.getInt("id"), result.getString("aName")));
            }
        } catch (SQLException e) {
            System.out.println("Cannot retrieve data from getAuthorData result set.");
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot close statement in getAuthorData.");
        }
        return authorScope;
    }
}
