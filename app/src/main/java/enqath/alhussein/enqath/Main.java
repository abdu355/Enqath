package enqath.alhussein.enqath;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import java.util.List;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView useremail_nav,username_nav;
    private ImageView userdp_nav;
    String userdisplayname;
    FirebaseUser user;

    private Button btnEnqath;
    FrameLayout layout;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 124;

    final String number = "tel:000";
    android.app.FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        useremail_nav = (TextView) navHeaderView.findViewById(R.id.useremail_nav);
        username_nav=(TextView)navHeaderView.findViewById(R.id.username_nav);
        //--------------------------

        btnEnqath = (Button) findViewById(R.id.btnEnqath);
        layout = (FrameLayout)findViewById(R.id.container);
        userdp_nav=(ImageView)findViewById(R.id.userdp_nav);

        btnEnqath.setOnClickListener(this);

        Firebase.setAndroidContext(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Toast.makeText(Main.this,"WELCOME " + user.getDisplayName(),Toast.LENGTH_LONG).show();
            //get user profile and display in nav drawer
            useremail_nav.setText(user.getEmail());
            username_nav.setText(user.getDisplayName());
            //set DP image here

            if(username_nav.getText().length()<=0)
            {
                showdialog();
            }
        } else {
            // No user is signed in
            Toast.makeText(Main.this,"GUEST MODE",Toast.LENGTH_LONG).show();
        }

    }
    public void DynamicPermission() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasReadContactPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
                if (hasReadContactPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            101);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, Settings.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainLogin.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            //fragmentManager.beginTransaction().replace(R.id.container, new Home()).commit();
            Fragment f = fragmentManager.findFragmentById(R.id.container);
            if(f!=null)
            {
                try {
                    fragmentManager.beginTransaction().remove(getFragmentManager().findFragmentById(R.id.container)).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (id == R.id.nav_secpage) {
            fragmentManager.beginTransaction().replace(R.id.container, new SecondFrag()).commit();
        } else if (id == R.id.nav_thirdpage) {
            fragmentManager.beginTransaction().replace(R.id.container, new ThirdFrag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnqath:

                Toast.makeText(this,"Calling...",Toast.LENGTH_SHORT).show();

                Uri callui = Uri.parse(number);
                Intent callIntent = new Intent(Intent.ACTION_CALL, callui);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

                break;
            case R.id.btnFire:
                //    startActivity(callIntent);
                // startActivity(new Intent(this,Fire.class));

                break;
            case R.id.btnCar:
                //    startActivity(callIntent);

                break;
            case R.id.btnTheft:
                //     startActivity(callIntent);

                break;
            case R.id.btnDrawn:
                //     startActivity(callIntent);

                break;


        }
    }

    private void showdialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(getApplicationContext());
        alert.setMessage("Consider updating your profile");
        alert.setTitle("Profile Setup");

        alert.setView(edittext);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                 userdisplayname = edittext.getText().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userdisplayname)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Firebase Profile", "User profile updated.");
                                }
                            }
                        });
            }
        });

        alert.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

}
