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

    /**
     * Get authors list
     *
     * @param authorId int author id
     * @return ArrayList authors
     */
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

    /**
     * Get song list
     *
     * @param authorId int author id
     * @return ArrayList songs
     */
    public ArrayList<SongNode> getSongData(int authorId) {
        if(authorId < 0){
            System.out.println("authorID is: "+authorId+" for getSongData is not valid.");
            return null;
        }

        ArrayList<SongNode> songScope = new ArrayList<SongNode>();

        String sql = "SELECT S.id, S.sName, S.authorId, A.aName FROM author A, songs S ";
        sql += "WHERE S.authorId = A.id AND S.authorId = "+ authorId;

        try {
            Statement statement = c.createStatement();
            assert statement != null;
            ResultSet result = statement.executeQuery(sql);
            while (result != null && result.next()) {
                songScope.add(new SongNode(result.getInt("id"), result.getString("sName"), result.getInt("authorId")));
            }



            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve getSongData operation where authorId = "+ authorId);
        }
        return songScope;
    }

    /**
     * Get song lyric
     *
     * @param songId int song id
     * @return ArrayList lyrics
     */
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
                lyricScope.add(new LyricNode(result.getInt("songId"), result.getString("sName"), result.getString("songText"), result.getInt("authorId")));
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve getLyricData operation where songId = "+ songId);
        }
        return lyricScope;
    }

    /**
     * Remove song and song lyric
     *
     * @param songId int song id
     */
    public void removeSong(int songId) {

        String sql0 = "DELETE FROM lyric WHERE songId = "+ songId;
        String sql1 = "DELETE FROM songs WHERE id = "+ songId;

        try (
            Connection conn = c;
            PreparedStatement pstmt0 = conn.prepareStatement(sql0);
            PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            // Execute remove statement
            pstmt0.executeUpdate();
            pstmt1.executeUpdate();

            pstmt0.close();
            pstmt1.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve removeSong operation where songId = "+ songId);
        }
    }

    /**
     * Remove author and transfer all songs to (unknown) author
     * @param authorId int author id
     */
    public void removeAuthor(int authorId){
        String sql = "DELETE FROM author WHERE id = "+ authorId;

        try {
            Connection conn = c;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Execute remove statement
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve removeAuthor operation where authorId = "+ authorId);
        }
    }

    /**
     * Update author name in db
     *
     * @param newName  String new author name
     * @param authorId int author id
     */
    public int updateAuthor(String newName, int authorId){
        int generKey = authorId;
        String sql = null;
        if(authorId >= 0) {
            sql = "UPDATE author SET aName = ?  WHERE id = ?;";
        } else {
            sql = "INSERT INTO author VALUES (null, ?)";
        }
        try {
            PreparedStatement pstmtUpdate = c.prepareStatement(sql);
            if(authorId >= 0) {
                pstmtUpdate.setString(1, newName);
                pstmtUpdate.setInt(2, authorId);
            } else {
                pstmtUpdate.setString(1, newName);
            }
            pstmtUpdate.executeUpdate();
            ResultSet rs = pstmtUpdate.getGeneratedKeys();
            if(rs != null && rs.next()){
                generKey = rs.getInt(1);
            }


            pstmtUpdate.close();

        } catch (SQLException e) {
            System.out.println("Cannot resolve updateAuthor operation where authorId = "+ authorId);
        }
        return generKey;
    }

    /**
     * Update lyric in db
     *
     * @param newLyric  String new Lyric name
     * @param songId int song id
     */
    public void updateLyric(String newLyric, int songId){
        String sql = "UPDATE lyric SET songText = ?  WHERE songId = ?";

        try {
            Connection conn = c;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newLyric);
            pstmt.setInt(2, songId);
            // Execute update statement
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve updateAuthor operation where authorId = "+ songId);
        }
    }

    public int addNewSong(String songName, String songLyric, int authorId){
        String sql = "INSERT INTO songs (sName, authorId) VALUES (?, ?)";
        int generKey = 0;

        try {
            Connection conn = c;
            PreparedStatement pstmtSong = conn.prepareStatement(sql);
            pstmtSong.setString(1, songName);
            pstmtSong.setInt(2, authorId);
            // Execute update statement
            pstmtSong.executeUpdate();
            
            ResultSet rs = pstmtSong.getGeneratedKeys();
            if(rs != null && rs.next()){
                generKey = rs.getInt(1);
            }
            
            sql = "INSERT INTO lyric (songText, songId) VALUES (?, ?)";
            pstmtSong = conn.prepareStatement(sql);
            pstmtSong.setString(1, songLyric);
            pstmtSong.setInt(2, generKey);
            pstmtSong.executeUpdate();
            pstmtSong.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve addNewSong operation where authorId = "+ authorId);
        }

        return generKey;
    }

    public void updateSong(String songName, int songId) {
        String sql = "UPDATE songs SET sName = ?  WHERE id = ?";

        try {
            Connection conn = c;
            PreparedStatement pstmtSongUp = conn.prepareStatement(sql);
            pstmtSongUp.setString(1, songName);
            pstmtSongUp.setInt(2, songId);
            // Execute update statement
            pstmtSongUp.executeUpdate();
            pstmtSongUp.close();
        } catch (SQLException e) {
            System.out.println("Cannot resolve updateSong operation where authorId = "+ songId);
        }
    }
}
