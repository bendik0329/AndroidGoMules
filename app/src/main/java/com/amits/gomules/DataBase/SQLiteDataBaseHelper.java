package com.amits.gomules.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amits.gomules.Entity.RideEntity;
import com.amits.gomules.Entity.RideListEntity;
import com.amits.gomules.Entity.UserEntity;
import com.amits.gomules.Utils.RideTable;
import com.amits.gomules.Utils.UserTable;

import java.util.ArrayList;
import java.util.Calendar;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "goMulesRidesDB";

    SQLiteDatabase db;

    private static final int DATA_BASE_VERSION = 10;

    Cursor cursor;


    /*    Query to create USER table*/
    public static final String Create_User_Table = "CREATE TABLE " + UserTable.TableName + "("
            + UserTable.ID + " INTEGER PRIMARY KEY, "
            + UserTable.FirstName + " TEXT NOT NULL, "
            + UserTable.LastName + " TEXT NOT NULL, " + UserTable.Email + " TEXT NOT NULL, "
            + UserTable.Password + " TEXT NOT NULL, " + UserTable.Active + " BOOLEAN, " + UserTable.CreatedOn + " TEXT );";

    /*    Query to create Ride table*/
    public static final String Create_Ride_Table = "CREATE TABLE " + RideTable.TableName + "("

            + RideTable.ID + " INTEGER PRIMARY KEY, " + RideTable.UserID + " INTEGER , " + RideTable.Title + " TEXT, "
            + RideTable.Description + " TEXT, " +
            RideTable.Cost + " FLOAT, " + RideTable.ContactMobile + " TEXT, "
            + RideTable.CreatedOn + " TEXT, "
            + RideTable.AvailableDate+" TEXT, "

            + RideTable.Source + " TEXT, "
            + RideTable.Destination + " TEXT, "
            + RideTable.NoOfSeat + " INTEGER, "
            + " FOREIGN KEY (" + RideTable.ID + ") REFERENCES " + RideTable.TableName + " (" + RideTable.ID + "));";

    //constructor
    public SQLiteDataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    /*    Function to get the date*/
    private String getDate() {
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
        // Drop older table if existed
       db.execSQL("DROP TABLE IF EXISTS " + UserTable.TableName);
       db.execSQL("DROP TABLE IF EXISTS " + RideTable.TableName);
        // Create tables again
        onCreate(db);

    }


    //function to insert ride details
    public long addRide(RideEntity rideEntity) {
        ContentValues cv = new ContentValues();
        cv.put(RideTable.Title, rideEntity.getTitle());
        cv.put(RideTable.Description,rideEntity.getDescription());
        cv.put(RideTable.NoOfSeat,rideEntity.getNoOfSeat());
        cv.put(RideTable.ContactMobile, rideEntity.getContactMobile());
        cv.put(RideTable.AvailableDate, rideEntity.getAvailableDate());
        cv.put(RideTable.Cost,rideEntity.getCost());
        cv.put(RideTable.Source,rideEntity.getSource());
        cv.put(RideTable.Destination,rideEntity.getDestination());
        cv.put(RideTable.CreatedOn, getDate());
        cv.put(RideTable.UserID,rideEntity.getUserID() );
        db = this.getWritableDatabase();
        long ride_id = db.insert(RideTable.TableName, null, cv);
        db.close();
        return ride_id;

    }

    //insert user
    public long addUser(UserEntity userEntity) {


       if(this.checkIfEmailExists(userEntity.getEmail()))
       {
           return 0;
       }

        ContentValues cv = new ContentValues();
        System.out.println();
        cv.put(UserTable.FirstName, userEntity.getFirstName());
        cv.put(UserTable.LastName, userEntity.getLastName());
        cv.put(UserTable.Email, userEntity.getEmail());
        cv.put(UserTable.Password, userEntity.getPassword());
        cv.put(UserTable.Active, userEntity.isActive());
        cv.put(UserTable.CreatedOn, getDate());
        //insert row
        db = this.getWritableDatabase();
        long user_id = db.insert(UserTable.TableName, null, cv);
        System.out.println("Userid " + user_id);
        db.close();
        return user_id;
    }


    public boolean checkIfEmailExists(String email) {
        db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + UserTable.TableName + " WHERE "+UserTable.Email+ "= ?";
        cursor = getReadableDatabase().rawQuery(sqlQuery, new String[]{email});
        return (cursor.getCount() > 0)?true:false;
    }

    public UserEntity login(UserEntity userEntity) {
        db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + UserTable.TableName + " WHERE "+UserTable.Email+ "= ? AND "+UserTable.Password+"=?";
        cursor = getReadableDatabase().rawQuery(sqlQuery, new String[]{userEntity.getEmail(),userEntity.getPassword()});
        if (cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    userEntity.setFirstName(cursor.getString(cursor.getColumnIndex(UserTable.FirstName)));
                    userEntity.setLastName(cursor.getString(cursor.getColumnIndex(UserTable.LastName)));
                    userEntity.setUserExists(true);
                    userEntity.setID( Integer.parseInt(cursor.getString(cursor.getColumnIndex(UserTable.ID))));
                } while (cursor.moveToNext());
            }
        return userEntity;
        }
        else
        {
            userEntity.setUserExists(false);
            return userEntity;
        }
    }

    public ArrayList<RideListEntity> getRideList() {
        db = this.getWritableDatabase();
        String UT=UserTable.TableName+".";
        String RT=RideTable.TableName+".";
        String sqlQuery = "SELECT "+UT+UserTable.FirstName+","
        +UT+UserTable.LastName+" ,"+UT+UserTable.Email+","
        +RT+RideTable.Title+","+ RT+RideTable.Description+","+RT+RideTable.NoOfSeat+","
        +RT+RideTable.Source+","+ RT+RideTable.Destination+","
        +RT+RideTable.ContactMobile+","+RT+RideTable.AvailableDate+","+RT+RideTable.Cost+" "
        +"FROM "+ UserTable.TableName + " INNER JOIN  "+RideTable.TableName +" ON "
        +UT+UserTable.ID+" = "+RT+RideTable.UserID + " WHERE "+RT+RideTable.AvailableDate+ ">= date('now')";

        cursor = getReadableDatabase().rawQuery(sqlQuery,null);
            ArrayList<RideListEntity> ridelist=new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    RideListEntity entity=new RideListEntity();
                    entity.setFirstName(cursor.getString(cursor.getColumnIndex(UserTable.FirstName)));
                    entity.setLastName(cursor.getString(cursor.getColumnIndex(UserTable.LastName)));
                    entity.setEmail(cursor.getString(cursor.getColumnIndex(UserTable.Email)));
                    entity.setContactMobile(cursor.getString(cursor.getColumnIndex(RideTable.ContactMobile)));
                    entity.setTitle(cursor.getString(cursor.getColumnIndex(RideTable.Title)));
                    entity.setDescription(cursor.getString(cursor.getColumnIndex(RideTable.Description)));
                    entity.setSource(cursor.getString(cursor.getColumnIndex(RideTable.Source)));
                    entity.setDestination(cursor.getString(cursor.getColumnIndex(RideTable.Destination)));
                    entity.setNoOfSeat(Integer.parseInt(cursor.getString(cursor.getColumnIndex(RideTable.NoOfSeat))));
                    entity.setAvailableDate(cursor.getString(cursor.getColumnIndex(RideTable.AvailableDate)));
                    entity.setCost(Float.parseFloat(cursor.getString(cursor.getColumnIndex(RideTable.Cost))));
                    ridelist.add(entity);
                } while (cursor.moveToNext());
            }
            return ridelist;
    }

}
