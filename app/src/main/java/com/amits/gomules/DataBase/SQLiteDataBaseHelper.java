package com.amits.gomules.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.amits.gomules.Entity.Ride;
import com.amits.gomules.Entity.User;

import java.util.Calendar;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "goMulesRidesDB";

    SQLiteDatabase db;

    private static final int DATA_BASE_VERSION = 1;

    Cursor cursor;


/*    Query to create USER table*/
    public static final String Create_User_Table = "CREATE TABLE " + User.TableName + "("
            + User.ID + " INTEGER PRIMARY KEY, "
            + User.FirstName + " TEXT NOT NULL, "
            + User.LastName + " TEXT NOT NULL, " + User.Email + " TEXT NOT NULL, "
            + User.Password + " TEXT NOT NULL, " + User.Active + " BOOLEAN, " + User.CreatedOn + " TEXT );";


/*    Query to create Ride table*/
    public static  final String Create_Ride_Table = "CREATE TABLE " + Ride.TableName + "("

            + Ride.ID + " INTEGER PRIMARY KEY, " + Ride.UserID + " INTEGER , " + Ride.Title + " TEXT, "
            + Ride.Description + " TEXT, " +
            Ride.Cost + " FLOAT, " + Ride.ContactMobile + " TEXT, "
            + Ride.CreatedOn + " TEXT, "
            + Ride.NoOfSeat + " INTEGER, "
            + " FOREIGN KEY (" + Ride.ID + ") REFERENCES " + User.TableName + " (" + User.ID + "));";

    public SQLiteDataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


/*    Function to get the date*/
    public String getDate() {
        Calendar c = Calendar.getInstance();

        String sDate = c.get(Calendar.YEAR) + "/"
                + c.get(Calendar.MONTH)
                + "/" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);
        return sDate;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_User_Table);
        db.execSQL(Create_Ride_Table);
        Log.e("db on create call", "called");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*// Drop older table if existed
                db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
				db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
				db.execSQL("DROP TABLE IF EXISTS " + PLACED_ORDER);
				// Create tables again
				onCreate(db);*/

    }

/*    // insert accomodation details
    public long addAccommodationPost(AccommodationAvailable accommodationAvailable, int userID) {
        ContentValues cv = new ContentValues();
        System.out.println("Title " + accommodationAvailable.getAcom_title());
        System.out.println("Desc " + accommodationAvailable.getAcom_desc());
        System.out.println("Cost " + accommodationAvailable.getAcom_cost());
        System.out.println("Phone " + accommodationAvailable.getAcom_contactdetail());
        System.out.println("Available Date " + accommodationAvailable.getAcom_availbleDate());
        System.out.println("Available Date " + getDate());
        System.out.println("UserID" + getDate());
        cv.put(accommodation_uid, userID);
        cv.put(accommodation_title, accommodationAvailable.getAcom_title());
        cv.put(accommodation_description, accommodationAvailable.getAcom_desc());
        // cv.put(accommodation_size, accommodationAvailable.getAcom_size());
        cv.put(accommodation_cost, accommodationAvailable.getAcom_cost());
        cv.put(accommodation_contactdetail, accommodationAvailable.getAcom_contactdetail());
        cv.put(accommodation_availbleDate, accommodationAvailable.getAcom_availbleDate());
        System.out.println(cv);
        db = this.getWritableDatabase();
        long accom_id = db.insert(AvailAccommodation_TABLE, null, cv);
        System.out.println(AvailAccommodation_TABLE);

        System.out.println("AccomendationId " + accom_id);
        db.close();
        return accom_id;
    }*/

    //insert user
   /* public long addUser(UserEntity userEntity) {
        ContentValues cv = new ContentValues();
        System.out.println(CREATE_UR_TABLE);
        System.out.println("FirstName " + userEntity.getFirstName());
        System.out.println("LastName " + userEntity.getLastName());
        System.out.println("Email " + userEntity.getEmail());
        System.out.println("Password " + userEntity.getPassword());
        System.out.println("IsActive " + userEntity.isUserActive());
        System.out.println("user_createdOn " + getDate());
        cv.put(user_firstName, userEntity.getFirstName());
        cv.put(user_lastName, userEntity.getLastName());
        cv.put(user_email, userEntity.getEmail());
        cv.put(user_pwd, userEntity.getPassword());
        cv.put(user_active, userEntity.isUserActive());
        cv.put(user_createdOn, getDate());
        //insert row
        db = this.getWritableDatabase();
        long user_id = db.insert(UserReg_TABLE, null, cv);
        System.out.println("Userid " + user_id);
        db.close();
        return user_id;
    }*/

    /*public int getHighestID() {
        final String MY_QUERY = "SELECT last_insert_rowid()";
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        cur.close();
        return ID;
    }*/

    /*public boolean validateUser(String email, String pwd) {
        db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + UserReg_TABLE + " WHERE "
                + user_email + "='" + email + "'AND " + user_pwd + "='" + pwd + "'", null);
        if (cursor.getCount() > 0)
            return true;
        return false;
    }*/


    /*public int getUserID(String user_email) {
        db = this.getWritableDatabase();
        int ID=0;
        System.out.println("SELECT user_id FROM " + UserReg_TABLE + " WHERE user_email ='" + user_email+"'");
        cursor = db.rawQuery("SELECT user_id FROM " + UserReg_TABLE + " WHERE user_email ='" + user_email+"'", null);
        if (cursor != null && cursor.moveToFirst()) {
            ID = cursor.getInt(0);
            System.out.println("ID" + ID);
        }
        return ID;
    }*/


/*    public List<AccommodationAvailable> getAllAccommodation(String user_email) {
        List<AccommodationAvailable> accommodationList = new ArrayList<AccommodationAvailable>();
        //select all query
        String sqlQuery = "SELECT  accommodation_uid,accommodation_title, accommodation_cost,accommodation_phone " +
                "FROM " + AvailAccommodation_TABLE + " JOIN " + UserReg_TABLE +
                " ON Available_Accommodation.accommodation_uid = user_registration.user_id WHERE user_email='" + user_email + "'";
        System.out.println(sqlQuery);
        cursor = getReadableDatabase().rawQuery(sqlQuery, null);

        //looping through all row and adding to list
        if (cursor.moveToFirst()) {
            System.out.println("Cursor: Inside");
            do {
                AccommodationAvailable accommodationAvailable = new AccommodationAvailable();
                accommodationAvailable.setAcom_uid(Integer.parseInt(cursor.getString(0)));
                accommodationAvailable.setAcom_title(cursor.getString(1));
                accommodationAvailable.setAcom_cost(cursor.getString(2));
                accommodationAvailable.setAcom_contactdetail(cursor.getString(3));
//                accommodationAvailable.bindImage.setImageID(R.drawable.house);
                //adding to list
                accommodationList.add(accommodationAvailable);
            } while (cursor.moveToNext());
        }
        for (AccommodationAvailable accommodationAvailable : accommodationList
                ) {
            System.out.println("Data" + accommodationAvailable.getAcom_title());

        }
        return accommodationList;
    }*/
}
