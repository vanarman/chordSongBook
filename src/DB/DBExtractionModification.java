package DB;

import dataStructures.AuthorNode;
import dataStructures.LyricNode;
import dataStructures.SongNode;

import java.sql.*;
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

    public ArrayList<AuthorNode> getAuthorsList(int authorId){
        Statement statement = null;
        ResultSet result = null;
        ArrayList<AuthorNode> authorScope = new ArrayList<AuthorNode>();

        try {
            statement = c.createStatement();

            String sql = "SELECT * FROM author";

            if(authorId > -1){
                sql += " WHERE id = "+ authorId;
            }

            result = statement.executeQuery(sql);

            while (result != null && result.next()){
                authorScope.add(new AuthorNode(result.getInt("id"), result.getString("aName")));
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve getAuthorsList operation where authorId = "+ authorId);
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

        String sql = "SELECT S.id, S.sName, S.authorId, A.aName FROM author A, songs S ";
        sql += "WHERE S.authorId = A.id AND S.authorId = "+ authorId;

        try {
            statement = c.createStatement();
            assert statement != null;
            result = statement.executeQuery(sql);

            while (result != null && result.next()){
                songScope.add(new SongNode(result.getInt("id"), result.getString("sName")));
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve getSongData operation where authorId = "+ authorId);
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

        String sql = "SELECT L.id, L.songText, L.songId, S.sName, S.authorId, A.aName FROM lyric L, author A, songs S ";
        sql += "WHERE S.authorId = A.id AND L.songId = S.id AND L.songId = "+ songId;

        try {
            statement = c.createStatement();
            assert statement != null;
            result = statement.executeQuery(sql);

            while (result != null && result.next()){
                //int songId,                           String songName,                    int authorId,                       String authorName,                      String songText
                lyricScope.add(new LyricNode(result.getInt("songId"), result.getString("sName"), result.getString("songText")));
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve getLyricData operation where songId = "+ songId);
        }
        return lyricScope;
    }

    public void removeSong(int songId) {

        String sql0 = "DELETE FROM lyric WHERE songId = "+ songId;
        String sql1 = "DELETE FROM songs WHERE id = "+ songId;

        try (
            Connection conn = c;
            PreparedStatement pstmt0 = conn.prepareStatement(sql0);
            PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            // Execute delete statement
            pstmt0.executeUpdate();
            pstmt1.executeUpdate();

            pstmt0.close();
            pstmt1.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve removeSong operation where songId = "+ songId);
        }
    }
}
