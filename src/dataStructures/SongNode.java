

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 29 December, 2017
 */
public class SongNode{
    private int sid;
    private String songName;
    private int authorId;
    private static final DBExtractionModification dbExMod = new DBExtractionModification();

    public SongNode(int sid, String songName, int authorId) {
        this.sid = sid;
        this.songName = songName;
        this.authorId = authorId;
    }

    public SongNode(String songName, String songLyric, int authorId){
        int newId = dbExMod.addNewSong(songName, songLyric, authorId);
        if(newId > 0){
            this.sid = newId;
            this.songName = songName;
            this.authorId = authorId;
        }
    }

    public int getSid() {
        return sid;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String newName) {
        this.songName = newName;
        dbExMod.updateSongName(sid, newName);
    }

    @Override
    public String toString() {
        return songName;
    }
}
