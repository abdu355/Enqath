package enqath.alhussein.enqath;

/**
 * Created by Abdulwahab on 7/3/2016.
 */


public class UserProfile {




    public String fname,lname;
    public String phone;
    public String dob;
    public String nID;
    public String nat;
  //  -----

    public UserProfile() {

    }

    public UserProfile(String fname, String lname, String phone, String dob, String nID, String nat) {
        this.fname = fname;
        this.lname = lname;
        this.phone=phone;
        this.dob=dob;
        this.nID=nID;
        this.nat=nat;
    }


    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getnID() {
        return nID;
    }

    public void setnID(String nID) {
        this.nID = nID;
    }


}
