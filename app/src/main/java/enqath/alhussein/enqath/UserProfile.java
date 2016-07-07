package enqath.alhussein.enqath;

/**
 * Created by Abdulwahab on 7/3/2016.
 */


public class UserProfile {


    public String username;


    public String email;
    public String phone;
    public String dob;
  //  -----
    public String blood;
    public String allergies;
    public String currentCondition;
    public String nID;
    public String extraInfo;
    public String medications;


    public UserProfile() {

    }

    public UserProfile(String username, String email, String phone, String dob) {
        this.username = username;
        this.email = email;
        this.phone=phone;
        this.dob=dob;
    }

    public UserProfile(String medications, String extraInfo, String currentCondition, String allergies, String blood) {
        this.medications = medications;
        this.extraInfo = extraInfo;
        this.currentCondition = currentCondition;
        this.allergies = allergies;
        this.blood = blood;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getnID() {
        return nID;
    }

    public void setnID(String nID) {
        this.nID = nID;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }
}
