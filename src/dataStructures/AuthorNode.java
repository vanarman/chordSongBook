package dataStructures;

/**
 * Author data structure
 *
 * @author Dmytro Sytnik (VanArman)
 * @version 21 February, 2018
 */
public class AuthorNode {
    private int id;
    private String authorName;

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
     * @param authorName String new author name
     */
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    /**
     * Author name as string
     * @return String author name
     */
    @Override
    public String toString() {
        return authorName;
    }
}