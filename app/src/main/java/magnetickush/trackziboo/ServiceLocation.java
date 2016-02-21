package magnetickush.trackziboo;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by kuush on 2/21/2016.
 */

public class ServiceLocation extends Service {


    GPSTracker gps;
    double tmplat=0;
    double tmplong=0;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
       // Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
    }



//	    @Override
//	    public void onStart(Intent intent, int startId) {
//	        // For time consuming an long tasks you can launch a new thread here...
//		        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
//
//	    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
       // Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

      time();

        return START_STICKY;
    }


    private void time() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                gps = new GPSTracker(ServiceLocation.this);
                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    System.out.println("Latitude is"+latitude);
                    System.out.println("Longitude is" +longitude);

                    String lat=String.valueOf(latitude);
                    String lon=String.valueOf(longitude);

                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    // full name form of the day
                    System.out.println(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
                     String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String strDate = sdf.format(calendar.getTime());

                    String new_date = null;
                    DateTime DXT = new DateTime();
                    try {
                        new_date = DXT.date_time_day();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String new_date_save = day +","+ new_date;




                    try {

                        //Save data to Database

                        POJO_ZIBO_LOCATION PZJ = new POJO_ZIBO_LOCATION();
                        PZJ.setDate(new_date_save);
                        PZJ.setLatitude(lat);
                        PZJ.setLongitude(lon);
                        PZJ.setTime(strDate);
                        PZJ.setFlagSync("false");

                        DatabaseHandler DH = new DatabaseHandler(ServiceLocation.this);
                        DH.addAttendance(PZJ);
                       /* Log.d("Writting File","Working");
                        long time= System.currentTimeMillis();
                        String millisec = ""+ time ;
                        String  loc =  time+ "\t" + latitude + "\t"+ "\t" + longitude + "\n";
                        FileOutputStream fos = new FileOutputStream(f,true);


                        fos.write(loc.getBytes());
                        fos.flush();
                        fos.close();

                        Log.d("File Created",loc);*/
                    } catch (Exception e) {
                    }
                    tmplat=latitude;
                    tmplong=longitude;

                }

                else{
                    gps.showSettingsAlert();
                }

                try {
                    Thread.sleep(2000);
                    time();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, 200000); // 5 sec  180000

    }

    @Override

    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }





}

