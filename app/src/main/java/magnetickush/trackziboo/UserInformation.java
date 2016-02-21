package magnetickush.trackziboo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInformation extends Activity {

    EditText name;
    Button button;

    String Name, IMEI , DaywithDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        name= (EditText)findViewById(R.id.name);
        button = (Button)findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    IMEI = telephonyManager.getDeviceId().toString().trim();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Unbale to get the IMEI Number",Toast.LENGTH_LONG).show();
                    IMEI = Integer.toString(0);
                }

                try{
                    String dateStr = "04/05/2010";

                    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateObj = curFormater.parse(dateStr);
                    SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

                     DaywithDate = postFormater.format(dateObj);

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Unable To get the Date and Time",Toast.LENGTH_LONG).show();
                    DaywithDate = "NaN";
                }

                getData();

                Intent i = new Intent(UserInformation.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void getData() {
        if(name.getText().toString().trim()!=null) {
            Name = name.getText().toString().trim();

              //SaveData
            save(Name,IMEI,DaywithDate);

        }else{
            Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_LONG).show();
        }

    }

    private void save(String name, String imei, String daywithDate) {
try {
    DatabaseHandler DH = new DatabaseHandler(this);

    POJO_ZIBOO_IS_ZIBO PZ = new POJO_ZIBOO_IS_ZIBO();
    PZ.setUsername(name);
    PZ.setIMEI(imei);
    PZ.setStart(daywithDate);
    DH.addContact(PZ);
    Toast.makeText(getApplicationContext(),"Data  Saved",Toast.LENGTH_LONG).show();
}catch( Exception e){

    Toast.makeText(getApplicationContext(),"Data was not Saved",Toast.LENGTH_LONG).show();

}
    }

}
