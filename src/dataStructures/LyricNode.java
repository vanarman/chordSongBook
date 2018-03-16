/**
 * @author Dmytro Sytnik (VanArman)
 * @version 29 December, 2017
 */
public class LyricNode extends SongNode {
    private String lyric;
    private static final DBExtractionModification dbExMod = new DBExtractionModification();

    public LyricNode(int songId, String songName, String songText, int authorId) {
        super(songId, songName, authorId);
        this.lyric = songText;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String newLyric) {
        this.lyric = newLyric;
        dbExMod.updateSongLyric(this.getSid(), newLyric);
    }

    @Override
    public String toString() {
        return lyric;
    }
}
