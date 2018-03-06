package dataStructures;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 29 December, 2017
 */
public class SongNode{
    private int sid;
    private String songName;
    private int authorId;

    public SongNode(int sid, String songName, int authorId) {
       // super(aid, authorName);
        this.sid = sid;
        this.songName = songName;
        this.authorId = authorId;
    }

    public int getSid() {
        return sid;
    }

    public String getSongName() {
        return songName;
    }

    public int getAuthorId(){ return authorId; }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setAuthorId(int newAuthorId) { this.authorId = newAuthorId; }

    @Override
    public String toString() {
        return songName;
    }
}
