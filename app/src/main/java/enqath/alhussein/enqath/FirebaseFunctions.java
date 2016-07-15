package enqath.alhussein.enqath;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Abdulwahab on 7/7/2016.
 *
 * This class will be used to store all firebase methods to be accessed from all Activities in the app
 */
public class FirebaseFunctions {
    UserProfile dbuser;


    //<---------------------------------------------------PUSH FUNCTIONS--------------------------------------------------->
    public void pushProfileData(UserProfile userProfile, String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("profiles").child(userid).setValue(userProfile);

    }
    public void pushMedID(MedID medID, String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("medID").child(userid).setValue(medID);
    }
    public void pushIncident(IncidentDetails incidentDetails, String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("incidentDetails").child(userid).setValue(incidentDetails);
    }

    public void pushContacts(EmergencyContacts phone, String userid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("emergencyContacts").child(userid).setValue(phone);
        //myRef.child("emergencyContacts").push();




    }

    //<---------------------------------------------------FETCH FUNCTIONS--------------------------------------------------->



    //<---------------------------------------------------UPDATE/APPEND FUNCTIONS--------------------------------------------------->
    public void updateMedID(String userid, String ... params) //Push all available medical and critical information
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        HashMap<String, Object> myMap = new HashMap<String,Object>();
        myMap.put("medications",params[0]);
        myMap.put("extrainfo",params[1]);
        myMap.put("currentCondition",params[2]);
        myMap.put("allergies",params[3]);
        myMap.put("bloodType",params[4]);
        //put all fields required
        // ...
        // ...
        myRef.child("medID").child(userid).updateChildren(myMap);
    }


}
