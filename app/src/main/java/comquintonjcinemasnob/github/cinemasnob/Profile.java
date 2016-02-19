package comquintonjcinemasnob.github.cinemasnob;

/**
 * Represents a single Profile
 */
public class Profile {
    String userName;
    String interests;
    String major;

    /**
     * Instantiates the Profile object
     * @param n userName
     * @param m major
     * @param i interests
     */
    public Profile(String n, String m, String i) {
        userName = n;
        major = m;
        interests = i;
    }

    /**
     * Getter method for major
     * @return User's major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Getter method for interests
     * @return User's interests
     */
    public String getInterests() {
        return interests;
    }
}
