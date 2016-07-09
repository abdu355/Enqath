package enqath.alhussein.enqath;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mohammad on 2016-06-21.
 */
public class Fire extends Activity implements View.OnClickListener{
  Button btnMinor,btnMed,btnSerious;
    final String number = "tel:000";
    final String txtMinor="Fire incident occurred ";

    Uri callui = Uri.parse(number);
    Intent callIntent = new Intent(Intent.ACTION_CALL, callui);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fire_level);
        btnMinor=(Button)findViewById(R.id.btnMinorF);
        btnMed=(Button)findViewById(R.id.btnMedF);
        btnSerious=(Button)findViewById(R.id.btnSeriousF);

        btnMinor.setOnClickListener(this);
        btnMed.setOnClickListener(this);
        btnSerious.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnMinorF:
                  startActivity(callIntent);
                break;
            case R.id.btnMedF:
                break;
            case R.id.btnSeriousF:
                break;




        }
    }
    }

