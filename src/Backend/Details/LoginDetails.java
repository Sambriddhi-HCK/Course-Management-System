package Backend.Details;

import Database.DBConnection;

/*
 * This class is used to store the Credentials for logging in
 *
 * This method contains the getter and setter for retrieving Credentials Information
 *
 */
public class LoginDetails {
    private final String studentTable = "StudentCreds";
    private final String tutorTable = "tutorcreds";
    private final String adminTable = "Admin";

    private String tableName;

    private int tutId;
    private String password;


    public int gettutId() {
        return tutId;
    }

    public void setUid(int uid) {
        this.tutId = uid;
    }

}