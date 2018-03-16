

/**
 * Author data structure
 *
 * @author Dmytro Sytnik (VanArman)
 * @version 21 February, 2018
 */
public class AuthorNode {
    private int id;
    private String authorName;
    private static final DBExtractionModification dbExMod = new DBExtractionModification();

    /**
     * Default constructor
     *
     * @param id int author identification
     * @param authorName String author name
     */
    public AuthorNode(int id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public AuthorNode(String authorName){
        int id = dbExMod.addAuthor(authorName);
        if(id > 0) {
            this.id = id;
            this.authorName = authorName;
        } else {
            new Exception("Cannot create authorNode");
        }
    }

    /**
     * Gets author identification
     *
     * @return int author identification
     */
    public int getId() {
        return id;
    }

    /**
     * Get author name
     *
     * @return String author name
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Set new name for the author
     *
     * @param newName String new author name
     */
    public void setAuthorName(String newName) {
        this.authorName = newName;
        dbExMod.updateAuthorName(this.id, newName);
    }

    /**
     * Author name as string
     * @return String author name
     */
    @Override
    public String toString() {
        return authorName;
    }
}