package dataStructures;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 29 December, 2017
 */
public class LyricNode extends SongNode{
    private String lyric;

    public LyricNode(int songId, String songName, String songText, int authorId) {
        super(songId, songName, authorId);
        this.lyric = songText;
    }

    public String getLyric() {
        return lyric;
    }

    @Override
    public String toString() {
        return lyric;
    }
}
