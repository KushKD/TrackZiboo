package magnetickush.trackziboo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserInformation extends Activity {

    EditText name;
    TextView showcount;
    Button button,getcount,b1,b2;
    Button refresh;
    ListView LV;

    String Name, IMEI , DaywithDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        name= (EditText)findViewById(R.id.name);
        button = (Button)findViewById(R.id.button);
        getcount = (Button)findViewById(R.id.getcount);
        showcount = (TextView)findViewById(R.id.showcount);
        refresh = (Button)findViewById(R.id.refresh);
        LV = (ListView)findViewById(R.id.showList);

        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GetList
                showListNew SLN = new showListNew();
                SLN.execute();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start The Service
                startService(new Intent(getBaseContext(), ServiceLocation.class));
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stop The Service
                stopService(new Intent(getBaseContext(), ServiceLocation.class));
            }
        });


        getcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCount GC = new getCount();
                GC.execute();
            }
        });


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

   public  class getCount extends AsyncTask<String ,String,Integer >{

        @Override
        protected Integer doInBackground(String... params) {
            DatabaseHandler DH = new DatabaseHandler(UserInformation.this);
            int count = DH.getContactsCount();

            return count;
        }

        @Override
        protected void onPostExecute(Integer  s) {
            showcount.setText(Integer.toString(s));
            super.onPostExecute(s);
        }
    }

    public class showListNew extends AsyncTask<String,String,String>{

        ArrayList<HashMap<String,String>> listDB_AttendanceDetails = null;
        BinderData bindingData ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            DatabaseHandler DH = new DatabaseHandler(UserInformation.this);
            listDB_AttendanceDetails = new ArrayList<HashMap<String,String>>();
            listDB_AttendanceDetails = DH.GetAllData_AttendanceStatus();
            String Message = null;


            if(listDB_AttendanceDetails.size()!=0){




               bindingData = new BinderData(UserInformation.this,listDB_AttendanceDetails);
                Message = "List is not empty" + Integer.toString( listDB_AttendanceDetails.size());




            }else{
                Message = "List is  empty";
            }


            return Message;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LV.setAdapter(bindingData);
            // Toast.makeText(UserInformation.this,s,Toast.LENGTH_LONG).show();
        }
    }

}
