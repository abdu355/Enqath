package enqath.alhussein.enqath;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registeration extends Activity implements View.OnClickListener {
    private EditText txtFname, txtLname, txtPass, txtCpass, txtNationality, txtDob, txtPhone, txtUname;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registation);
        txtFname=(EditText)findViewById(R.id.txtFname);
        txtLname=(EditText)findViewById(R.id.txtLname);
        //txtUname=(EditText)findViewById(R.id.txtUname);
        txtPass=(EditText)findViewById(R.id.txtPass);
        txtCpass=(EditText)findViewById(R.id.txtCpass);
        txtNationality=(EditText)findViewById(R.id.txtNationality);
        txtDob=(EditText)findViewById(R.id.txtDOB);
        txtPhone=(EditText)findViewById(R.id.txtPhone);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);


    }
    public void insert(int phone, String name,String name2, String user,String pass,String Dateob, String National){
        SQLiteDatabase db;
        db = openOrCreateDatabase( "Database.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);


        try {
            final String CREATE_TABLE_CONTAIN = "CREATE TABLE IF NOT EXISTS tbl_Contain ("
                    + "phone INTEGER primary key,"
                    + "Fname TEXT,"
                    + "Lname TEXT,"
                    + "Uname TEXT,"
                    + "Pass TEXT,"
                    + "DOB Text,"
                    +"Nationality TEXT);";
            db.execSQL(CREATE_TABLE_CONTAIN);
            Toast.makeText(Registeration.this, "table created ", Toast.LENGTH_LONG).show();
            String sql =
                    "INSERT INTO Database.db (phone, Fname, Lname, uname,Pass,DOB, Nationality)"
                            +"VALUES("+phone+","+"name"+","+name2+","+user+","+pass+","+Dateob+","+National+")" ;

            db.execSQL(sql);
        }
        catch (Exception e) {
            Toast.makeText(Registeration.this, "ERROR "+e.toString(), Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSubmit:
                String str=txtPhone.getText().toString();
                String str1=txtDob.getText().toString();
                if(str==null|| str1=="")
                {
                    txtPhone.setError("please don't leave me behind");

                }
                else{
                insert(Integer.parseInt(txtPhone.getText().toString()),txtFname.getText().toString(),
                        txtLname.getText().toString(),txtUname.getText().toString(),txtPass.getText().toString(),txtDob.getText().toString(),
                        txtNationality.getText().toString());

                 }
        }


    }
}
