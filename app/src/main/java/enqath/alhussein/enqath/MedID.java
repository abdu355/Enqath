package enqath.alhussein.enqath;

/**
 * Created by Abdulwahab on 7/8/2016.
 */
public class MedID {
    public String blood;
    public String allergies;
    public String currentCondition;
    public String extraInfo;
    public String medications;

    public MedID() {

    }

    public MedID(String blood, String allergies, String currentCondition, String extraInfo, String medications) {
        this.blood = blood;
        this.allergies = allergies;
        this.currentCondition = currentCondition;
        this.extraInfo = extraInfo;
        this.medications = medications;
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
