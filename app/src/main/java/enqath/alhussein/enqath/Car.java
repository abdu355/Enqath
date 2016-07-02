package enqath.alhussein.enqath;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mohammad on 2016-06-21.
 */
public class Car extends Activity implements View.OnClickListener {
    Button btnMinor, btnSerious,btnMed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_crash_level);
        btnMinor=(Button)findViewById(R.id.btnMinor);
        btnMed=(Button)findViewById(R.id.btnMed);
        btnSerious=(Button)findViewById(R.id.btnSerious);

        btnSerious.setOnClickListener(this);
        btnMed.setOnClickListener(this);
        btnSerious.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnMinor:
                break;
            case R.id.btnMed:
                break;
            case R.id.btnSerious:
                break;




        }
    }
}
