package enqath.alhussein.enqath;

/**
 * Created by Abdulwahab on 7/3/2016.
 */
public class User {

    public String username;
    public String email;
    public String phone;
    public String dob;
    public String pass;

    public User() {

    }

    public User(String username, String email,String pass,String phone,String dob) {
        this.username = username;
        this.email = email;
        this.phone=phone;
        this.dob=dob;
        this.pass=pass;
    }
}