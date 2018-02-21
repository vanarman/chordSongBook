package dataStructures;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 21 February, 2018
 */
public class AuthorNode {
    private int id;
    private String authorName;

    public AuthorNode(int aid, String authorName) {
        this.id = aid;
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    @Override
    public String toString() {
        return authorName;
    }
}
