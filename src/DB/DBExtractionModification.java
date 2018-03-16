package DB;

import dataStructures.AuthorNode;
import dataStructures.LyricNode;
import dataStructures.SongNode;
import lib.LocalLogger;

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
     * Update author table with new author name by ID.
     *
     * @param aId int author ID
     * @param aName String new author name for corresponding author ID
     */
    public void updateAuthorName(int aId, String aName){
        try {
            String sql = "UPDATE author SET aName = ?  WHERE id = ?;";
            updateNode(aId, aName, sql);
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot resolve updateAuthorName operation where authorId = "+ aId);
        }
    }

    /**
     * Update songs table with new song name by ID.
     *
     * @param sId int song ID
     * @param sName String new song name for corresponding song ID
     */
    public void updateSongName(int sId, String sName){
        try {
            String sql = "UPDATE songs SET sName = ?  WHERE id = ?;";
            updateNode(sId, sName, sql);
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot resolve updateSongName operation where songId = "+ sId);
        }
    }

    /**
     * Update lyric table with new song text by ID.
     *
     * @param sId int lyric ID
     * @param lyric String new song lyric for corresponding song ID
     */
    public void updateSongLyric(int sId, String lyric){
        try {
            String sql = "UPDATE lyric SET songText = ?  WHERE songId = ?;";
            updateNode(sId, lyric, sql);
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot resolve updateSongName operation where songId = "+ sId);
        }
    }

    /**
     * Universal update method.
     *
     * @param id int ID of the searching field
     * @param newValue String new value to be updated
     * @param sql String SQL query to execute
     * @throws SQLException exception of SQL query execution to avoid system crash
     */
    private void updateNode(int id, String newValue, String sql) throws SQLException {
        PreparedStatement psNameUpdate = null;
        try {
            psNameUpdate = c.prepareStatement(sql);
            psNameUpdate.setString(1, newValue);
            psNameUpdate.setInt(2, id);
            psNameUpdate.executeUpdate();
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot execute update query for: "+ sql);
        } finally {
            if (psNameUpdate != null) {
                psNameUpdate.close();
                c.close();
            }
        }
    }

    /**
     * Remove song and song lyric
     *
     * @param songId int song id
     */
    public void removeSong(int songId) {
        String sql0 = "DELETE FROM lyric WHERE songId = ?";
        String sql1 = "DELETE FROM songs WHERE id = ?";
        try {
            removeNode(sql0, songId);
            removeNode(sql1, songId);
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot resolve removeSong operation where songId = "+ songId);
        }
    }

    /**
     * Remove author and transfer all songs to (unknown) author
     *
     * @param authorId int author id
     */
    public void removeAuthor(int authorId){
        String sql = "DELETE FROM author WHERE id = ?";
        try {
            removeNode(sql, authorId);
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot resolve removeAuthor operation where songId = "+ authorId);
        }
    }

    /**
     * Universal remove method that removes datea from the db by given sql query
     *
     * @param sql String sql to execute
     * @param id int ID of the node to remove
     * @throws SQLException throw exception if cannot perform removing operation
     */
    private void removeNode(String sql, int id) throws SQLException {
        PreparedStatement psNameUpdate = null;
        try {
            psNameUpdate = c.prepareStatement(sql);
            psNameUpdate.setInt(1, id);
            psNameUpdate.executeUpdate();
        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot execute remove query for: "+ sql);
        } finally {
            if (psNameUpdate != null) {
                psNameUpdate.close();
                c.close();
            }
        }
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
            new LocalLogger().logInfo("Cannot resolve getAuthorsList operation where authorId = "+ authorId);
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
            //System.out.println("authorID is: "+authorId+" for getSongData is not valid.");
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
            new LocalLogger().logInfo("Cannot resolve getSongData operation where authorId = "+ authorId);
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
            new LocalLogger().logInfo("songId for getLyricData is not valid. SongId: "+ songId);
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
            new LocalLogger().logInfo("Cannot resolve getLyricData operation where songId = "+ songId);
        }
        return lyricScope;
    }

    /**
     * Add new author to the DB
     *
     * @param authorName String name of the new author
     * @return int ID of the created row in DB to create corresponding node
     */
    public int addAuthor(String authorName){
        String sql = "INSERT INTO author VALUES (null, ?)";
        int generKey = -1;
        try {
            PreparedStatement pstmtUpdate = c.prepareStatement(sql);
            pstmtUpdate.setString(1, authorName);
            pstmtUpdate.executeUpdate();
            ResultSet rs = pstmtUpdate.getGeneratedKeys();
            if(rs != null && rs.next()){
                generKey = rs.getInt(1);
            }

            pstmtUpdate.close();

        } catch (SQLException e) {
            new LocalLogger().logInfo("Cannot resolve addAuthor operation where authorName = "+ authorName);
        }
        return generKey;
    }

    /**
     * Add new song and lyric
     *
     * @param songName String name of the new song
     * @param songLyric String lyric of the new song
     * @param authorId int ID of the song author
     * @return int ID of the recently created row in DB in order to create corresponding node
     */
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
            new LocalLogger().logInfo("Cannot resolve addNewSong operation where authorId = "+ authorId);
        }

        return generKey;
    }
}
