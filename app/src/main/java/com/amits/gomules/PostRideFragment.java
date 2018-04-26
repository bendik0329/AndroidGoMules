package com.amits.gomules;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.amits.gomules.DataBase.SQLiteDataBaseHelper;
import com.amits.gomules.Entity.RideEntity;
import com.amits.gomules.Utils.SharedPrefUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class PostRideFragment extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPref;
    EditText etTitle;
    EditText etDesc;
    EditText etSeat;
    EditText etMobileNo;
    EditText etDate;
    EditText etCost;
    EditText etSource;
    EditText etDestination;
    Button btnSave;
    RideEntity rideEntity;
    SQLiteDataBaseHelper sqLiteDataBaseHelper;

    String dateFormat = "MM/dd/yyyy";

    Calendar clDate;

    DatePickerDialog.OnDateSetListener datePicker;
    SimpleDateFormat sdf;


    public PostRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_ride, container, false);

        sdf = new SimpleDateFormat(dateFormat, Locale.US);

        etTitle = view.findViewById(R.id.etTitle);
        etDesc = view.findViewById(R.id.etDesc);
        etSeat = view.findViewById(R.id.etSeat);
        etMobileNo = view.findViewById(R.id.etNumber);
        etDate = view.findViewById(R.id.etDate);
        etCost = view.findViewById(R.id.etCost);
        etSource = view.findViewById(R.id.etSource);
        etDestination = view.findViewById(R.id.etDestination);
        btnSave = view.findViewById(R.id.btnPostRide);
        clDate = Calendar.getInstance();

        sharedPref = getActivity().getSharedPreferences(SharedPrefUtil.Name, getActivity().MODE_PRIVATE);

        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        etDate.setText(dateString);

        // set calendar date and update editDate
        datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                clDate.set(Calendar.YEAR, year);
                clDate.set(Calendar.MONTH, monthOfYear);
                clDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAvailableDate();
            }

        };



        // onclick - popup datepicker
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, datePicker, clDate
                        .get(Calendar.YEAR), clDate.get(Calendar.MONTH),
                        clDate.get(Calendar.DAY_OF_MONTH)) {


                }.show();
            }
        });


        btnSave.setOnClickListener(this);

        rideEntity = new RideEntity();

        sqLiteDataBaseHelper = new SQLiteDataBaseHelper(this.getContext());

        return view;
    }

    private void updateAvailableDate() {
        etDate.setText(sdf.format(clDate.getTime()));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnPostRide) {

            String title= etTitle.getText().toString();
            String desc=etDesc.getText().toString();
            String seat=etSeat.getText().toString();
            String mobile=etMobileNo.getText().toString();
            String date=etDate.getText().toString();
            String cost=etCost.getText().toString();
            String source=etSource.getText().toString();
            String destination =etDestination.getText().toString();

            if(title.length() == 0 ) {
                etTitle.setError("Title is required!");
                return;
            }
            if(desc.length() == 0 ) {
                etDesc.setError("Description is required!");
                return;
            }
            if(seat.length() == 0) {
                etSeat.setError("Seat is required!");
                return;
            }
            if(cost.length() == 0) {
                etCost.setError("Cost is required!");
                return;
            }
            if(mobile.length() == 0) {
                etMobileNo.setError("Mobile No is required!");
                return;
            }
            if(date.length() == 0) {
                etDate.setError("Date is required!");
                return;
            }
            if(source.length() == 0) {
                etSource.setError("Source is required!");
                return;
            }
            if(destination.length() == 0) {
                etDestination.setError("Destination is required!");
                return;
            }

            LocalDate datetime = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            String newDate= datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            rideEntity.setTitle(title);
            rideEntity.setDescription(desc);
            rideEntity.setNoOfSeat(Integer.parseInt(seat));
            rideEntity.setContactMobile(mobile);
            rideEntity.setAvailableDate(newDate);
            rideEntity.setCost(Float.parseFloat(cost));
            rideEntity.setSource(source);
            rideEntity.setDestination(destination);
            rideEntity.setUserID(sharedPref.getInt(SharedPrefUtil.ID,0));

            /*rideEntity.setUserID(Integer.parseInt());*/
            long rideID = sqLiteDataBaseHelper.addRide(rideEntity);
            if (rideID > 0) {
                Toast.makeText(this.getContext(), R.string.ride_added, Toast.LENGTH_SHORT).show();

                etTitle.setText("");
                etDesc.setText("");
                etSeat.setText("");
                etMobileNo.setText("");
                long currentdate = System.currentTimeMillis();
                String dateString = sdf.format(currentdate);
                etDate.setText(dateString);
                etCost.setText("");
                etSource.setText("");
                etDestination.setText("");
            } else {
                Toast.makeText(this.getContext(), R.string.error_ride_add, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
