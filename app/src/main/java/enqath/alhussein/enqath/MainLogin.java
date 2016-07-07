package enqath.alhussein.enqath;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainLogin extends AppCompatActivity {

    private Button skipbtn,email_sign_in_button,email_sign_up_button;
    private SignInButton gsign_in_button;
    private EditText useremail,pass;
    private FirebaseAuth mAuth;
    private String errorMsg;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "Firebase Authentication";
    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        errorMsg= getString(R.string.errorMsg);//using string.xml value so we can translate it later on :D

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        try {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //firebase init ------------------------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(MainLogin.this, Main.class));
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        // ...




        //firebase init ------------------------------------------------------------------------------

        skipbtn=(Button)findViewById(R.id.skiplogin);
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Main.class));
            }
        });

        useremail=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        email_sign_in_button=(Button)findViewById(R.id.email_sign_in_button);
        email_sign_up_button=(Button)findViewById(R.id.email_sign_up_button);
        gsign_in_button=(SignInButton)findViewById(R.id.gsign_in_button);


        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(useremail.getText().length()<=0)
                {
                    useremail.setError(errorMsg);
                }
                if (pass.getText().length()<=0)
                {
                    pass.setError(errorMsg);
                }
                else
                {
                    //Authenticate
                    userSingin(useremail.getText().toString(),pass.getText().toString());
                }
            }
        });
        email_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),QuickRegistration.class));
            }
        });
        gsign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Currently not available",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void userSingin(String email, String password)
    {
        progress = ProgressDialog.show(MainLogin.this, "Logging In",
            "Almost Done...", true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the firebaseUser. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in firebaseUser can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(MainLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                        // ...
                    }
                });
        if (errorMsg==null)
            errorMsg="Field Required";
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }


}
