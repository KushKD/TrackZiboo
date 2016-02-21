package magnetickush.trackziboo;

/**
 * Created by kuush on 2/21/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kuush on 12/2/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    boolean bool = false;
    static final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private  ArrayList<HashMap<String,String>> list_Attendance_Details = new ArrayList<HashMap<String,String>>();

    //Database Column HashMap UserDetails
    public static final String KEY_ID_DB = "id";
    public static final String FINGER_ONE_DB = "FingerOne";
    public static final String FINGER_TWO_DB = "FingerTwo";
    public static final String AADHAAR_DB = "Aadhaar";
    public static final String NAME_DB = "Name";
    public static final String CAREOFF_DB = "CareOFf";
    public static final String DOB_DB = "DOB";

    //Database Columns HASHMAP Attendance Details
    public static final String ATTENDANCE_ID_DB = "id";
    public static final String DATE_DB = "Date";
    public static final String LATITUDE_DB = "Latitude";
    public static final String LONGITUDE_DB = "Longitude";
    public static final String TIME_DB = "Time";
    public static final String Flag_Sync_DB = "Flag_Sync";



    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TrackZiboo";

    // Contacts table name
    private static final String TABLE_ZIBO = "UserDetails";
    private static final String TABLE_ZIBOLOCATION = "ZibooPosition";



    //User Details Columns
    private static final String KEY_ID = "id";
    private static final String USERNAME = "Username";
    private static final String IMEI = "IMEI";
    private static final String START = "start";

    //Attendance Details Columns
    private static final String ID = "id";
    private static final String DATE = "Date";
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";
    private static final String TIME = "Time";
    private static final String FLAGSYNC = "Flag_Sync";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERDATA_TABLE = "CREATE TABLE " + TABLE_ZIBO + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                +USERNAME+" TEXT,"
                +IMEI+" TEXT,"
                + START + " TEXT" + ")";

        //Create Second Table
        String CREATE_ZIBOLOCATION_TABLE = "CREATE TABLE " + TABLE_ZIBOLOCATION + "("
                + ID + " INTEGER PRIMARY KEY,"
                +DATE+" TEXT,"
                +LATITUDE+" TEXT,"
                +LONGITUDE+" TEXT,"
                +TIME+" TEXT,"
                + FLAGSYNC + " TEXT" + ")";


        db.execSQL(CREATE_USERDATA_TABLE);
        db.execSQL(CREATE_ZIBOLOCATION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZIBO);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    Boolean addContact(POJO_ZIBOO_IS_ZIBO Details ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, Details.getUsername()); // Fingure String
        values.put(IMEI, Details.getIMEI());
        values.put(START, Details.getStart());
        // Inserting Row
        db.insert(TABLE_ZIBO, null, values);
        db.close(); // Closing database connection

        try{
            exportDatabse(DATABASE_NAME);
        }catch (Exception e){
            Log.d("Got Error ..",e.getLocalizedMessage());
        }
        return true;
    }


    /**
     * Saving the Attendance
     * @return
     */
    Boolean addAttendance(POJO_ZIBO_LOCATION LocationZibo ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATE, LocationZibo.getDate());
        values.put(LATITUDE, LocationZibo.getLatitude());
        values.put(LONGITUDE, LocationZibo.getLongitude());
        values.put(TIME,LocationZibo.getTime());
        values.put(FLAGSYNC, LocationZibo.getFlagSync());


        // Inserting Row
        db.insert(TABLE_ZIBOLOCATION, null, values);
        db.close(); // Closing database connection

        try{
            exportDatabse(DATABASE_NAME);
        }catch (Exception e){
            Log.d("Got Error ..",e.getLocalizedMessage());
        }
        return true;
    }


    // Getting the Complete Database in a List
  /*  public ArrayList<HashMap<String, String>> GetAllData(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERDETAILS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        while(cursor.moveToNext()){
            HashMap<String,String> temp = new HashMap<String, String>();
            Log.d(KEY_ID_DB, cursor.getString(0));
            temp.put(KEY_ID_DB, cursor.getString(0));
            temp.put(FINGER_ONE_DB, cursor.getString(1));
            temp.put(FINGER_TWO_DB,cursor.getString(2));
            temp.put(AADHAAR_DB,cursor.getString(3));
            temp.put(NAME_DB,cursor.getString(4));
            temp.put(CAREOFF_DB,cursor.getString(5));
            temp.put(DOB_DB,cursor.getString(6));
            list.add(temp);
        }
        return list;

    }*/

    // // Getting the Complete Database in a List Attendance
    public ArrayList<HashMap<String, String>> GetAllData_AttendanceStatus(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ZIBOLOCATION +" ORDER BY "+ DATE_DB +" DESC" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        while(cursor.moveToNext()){
            HashMap<String,String> Attendance_Details = new HashMap<String, String>();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            Attendance_Details.put(ATTENDANCE_ID_DB, cursor.getString(0));
            Attendance_Details.put(DATE_DB, cursor.getString(1));
            Attendance_Details.put(LATITUDE_DB,cursor.getString(2));
            Attendance_Details.put(LONGITUDE_DB,cursor.getString(3));
            Attendance_Details.put(TIME_DB,cursor.getString(4));
            Attendance_Details.put(Flag_Sync_DB,cursor.getString(5));
            list_Attendance_Details.add(Attendance_Details);
        }
        return list_Attendance_Details;

    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ZIBOLOCATION ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    // Updating single contact
  /*  public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }*/

    // Deleting single contact
  /*  public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }*/

    // Adding new contact
   /* void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }*/

    // Getting single contact
   /* Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REPORTING, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }*/

    // Getting All Contacts
  /*  public List<FingurePoJo> getAllContacts() {
        List<FingurePoJo> FingureList = new ArrayList<FingurePoJo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORTING;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FingurePoJo FP = new FingurePoJo();
               // contact.setID(Integer.parseInt(cursor.getString(0)));
               // contact.setName(cursor.getString(1));
               // contact.setPhoneNumber(cursor.getString(2));
                FP.setFingure_DB(cursor.getString(1));
                Log.d(cursor.getString(1) , "Nothing");
                FingureList.add(FP);
            } while (cursor.moveToNext());
        }
        // return contact list
        return FingureList;
    }*/


    public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+DatabaseHandler.class.getPackage().getName()+"//databases//"+databaseName+"";
                String backupDBPath = "Zibo.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                else{
                    Log.d("Error","No Idea");
                }
            }else{
                Log.d("Error", "No Idea 2");
            }
        } catch (Exception e) {

        }
    }
}