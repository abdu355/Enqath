package enqath.alhussein.enqath;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Abdulwahab on 7/7/2016.
 *
 * This class will be used to store all firebase methods to be accessed from all Activities in the app
 */
public class FirebaseFunctions {
    User dbuser;
    public void pushProfileData(User user, String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("users").child(userid).setValue(user);
    }
    public User getProfielData(String userid)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        dbuser = new User();

        myRef.child("users").child(userid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        dbuser.setEmail(user.getEmail());
                        dbuser.setDob(user.getDob());
                        dbuser.setUsername(user.getUsername());
                        dbuser.setPhone(user.getPhone());

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
}
