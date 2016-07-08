package enqath.alhussein.enqath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;

public class Preferences extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    TableRow R1,R2,R3,R4,R5;
    Spinner spinner;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        save=(Button)findViewById(R.id.btnSave);
        save.setOnClickListener(this);


        R1=(TableRow)findViewById(R.id.R1);
        R2=(TableRow)findViewById(R.id.R2);
        R3=(TableRow)findViewById(R.id.R3);
        R4=(TableRow)findViewById(R.id.R4);
        R5=(TableRow)findViewById(R.id.R5);
        spinner=(Spinner)findViewById(R.id.spinner);
        R1.setVisibility(View.VISIBLE);
        R2.setVisibility(View.GONE);
        R3.setVisibility(View.GONE);
        R4.setVisibility(View.GONE);
        R5.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.Contacts,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(spinner.getSelectedItemPosition()==0){
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.GONE);
            R3.setVisibility(View.GONE);
            R4.setVisibility(View.GONE);
            R5.setVisibility(View.GONE);

        }
        if(spinner.getSelectedItemPosition()==1){
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.GONE);
            R4.setVisibility(View.GONE);
            R5.setVisibility(View.GONE);

        }
        if(spinner.getSelectedItemPosition()==2){
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.VISIBLE);
            R4.setVisibility(View.GONE);
            R5.setVisibility(View.GONE);

        }
        if(spinner.getSelectedItemPosition()==3){
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.VISIBLE);
            R4.setVisibility(View.VISIBLE);
            R5.setVisibility(View.GONE);

        }
        if(spinner.getSelectedItemPosition()==4){
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.VISIBLE);
            R4.setVisibility(View.VISIBLE);
            R5.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        R1.setVisibility(View.VISIBLE);
        R2.setVisibility(View.GONE);
        R3.setVisibility(View.GONE);
        R4.setVisibility(View.GONE);
        R5.setVisibility(View.GONE);



    }

    @Override
    public void onClick(View view) {

    }
}
