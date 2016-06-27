package enqath.alhussein.enqath;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.io.IOException;
import java.net.URI;


/**
 * Created by Mohammad on 2016-06-01.
 */
public class Main extends Activity implements View.OnClickListener {
    private Button btnEnqath;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 124;

    String number = "tel:000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnEnqath = (Button) findViewById(R.id.btnEnqath);
        btnEnqath.setOnClickListener(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("YOUR_APP_ID")
                .server("http://Enqath:1337/parse")

                .build()
        );
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnqath:


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_settings:
              startActivity(new Intent(this,Settings.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, About.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
