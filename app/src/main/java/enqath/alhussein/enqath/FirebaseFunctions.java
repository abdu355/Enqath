package enqath.alhussein.enqath;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Abdulwahab on 7/7/2016.
 *
 * This class will be used to store all firebase methods to be accessed from all Activities in the app
 */
public class FirebaseFunctions {
    UserProfile dbuser;
    public void pushProfileData(UserProfile userProfile, String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("profiles").child(userid).setValue(userProfile);

    }
    public UserProfile getProfielData(String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        dbuser = new UserProfile();

        myRef.child("profiles").child(userid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get userProfile value
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                        dbuser.setEmail(userProfile.getEmail());
                        dbuser.setDob(userProfile.getDob());
                        dbuser.setUsername(userProfile.getUsername());
                        dbuser.setPhone(userProfile.getPhone());

                        Log.d("DBUSER",""+dbuser.getEmail()+" "+dbuser.getDob());
                        Log.d("DBUSER",""+dbuser.getUsername()+" "+dbuser.getPhone());
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("getProfielDataFunction", "getUser:onCancelled", databaseError.toException());
                    }
                });

        return dbuser;
    }

    public void pushMedID(String userid, String ... params) //Push all available medical and critical information
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("medications",params[0]);
        myMap.put("extrainfo",params[1]);
        myMap.put("currentCondition",params[2]);
        myMap.put("allergies",params[3]);
        myMap.put("bloodType",params[4]);
        //put all fields required
        // ...
        // ...
        myRef.child("profiles").child(userid).updateChildren(myMap);
    }

}
