package DB;

import dataStructures.AuthorNode;
import dataStructures.LyricNode;
import dataStructures.SongNode;

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

        if(id > -1){
            sql += "WHERE id = "+ id;
        }

        try {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Cannot execute getAuthorData query!!!\nSQL QUERY: "+ sql);
            System.out.println(e.getMessage());
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

    public ArrayList<SongNode> getSongData(int authorId) {
        if(authorId < 0){
            System.out.println("authorID is: "+authorId+" for getSongData is not valid.");
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        ArrayList<SongNode> songScope = new ArrayList<SongNode>();

        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            System.out.println("Cannot create statement for getSongData!!!");
        }

        String sql = "SELECT S.id, S.sName, S.authorId, A.aName FROM author A, songs S ";
        sql += "WHERE S.authorId = A.id AND S.authorId = "+ authorId;

        try {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Cannot execute getSongData query!!!\nSQL QUERY: "+ sql);
        }

        try {
            while (result != null && result.next()){
                songScope.add(new SongNode(result.getInt("id"), result.getString("sName")));
            }
        } catch (SQLException e) {
            System.out.println("Cannot retrieve data from getSongData result set.");
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot close statement in getSongData.");
        }
        return songScope;
    }

    public ArrayList<LyricNode> getLyricData(int songId) {
        if(songId < 0){
            System.out.println("songId for getLyricData is not valid. SongId: "+ songId);
        }

        Statement statement = null;
        ResultSet result = null;
        ArrayList<LyricNode> lyricScope = new ArrayList<LyricNode>();

        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            System.out.println("Cannot create statement for getLyricData!!!");
        }

        String sql = "SELECT L.id, L.songText, L.songId, S.sName, S.authorId, A.aName FROM lyric L, author A, songs S ";
        sql += "WHERE S.authorId = A.id AND L.songId = S.id AND L.songId = "+ songId;

        try {
            assert statement != null;
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Cannot execute getLyricData query!!!\nSQL QUERY: "+ sql);
        }

        try {
            while (result != null && result.next()){
                //int songId,                           String songName,                    int authorId,                       String authorName,                      String songText
                lyricScope.add(new LyricNode(result.getInt("songId"), result.getString("sName"), result.getString("songText")));
            }
        } catch (SQLException e) {
            System.out.println("Cannot retrieve data from getLyricData result set.");
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot close statement in getLyricData.");
        }
        return lyricScope;
    }
}
