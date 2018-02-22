package dataStructures;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 29 December, 2017
 */
public class SongNode{
    private int sid;
    private String songName;

    public SongNode(int sid, String songName) {
       // super(aid, authorName);
        this.sid = sid;
        this.songName = songName;
    }

    public int getSid() {
        return sid;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public String toString() {
        return songName;
    }
}
