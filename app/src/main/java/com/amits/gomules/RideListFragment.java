package com.amits.gomules;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amits.gomules.DataBase.SQLiteDataBaseHelper;
import com.amits.gomules.Entity.RideListEntity;
import com.amits.gomules.Utils.RideListCustomAdapter;

import java.util.ArrayList;

public class RideListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SQLiteDataBaseHelper sqLiteDataBaseHelper;
    TextView tvMsg;

    private static ArrayList<RideListEntity> rideListData;

    public RideListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvMsg = view.findViewById(R.id.tvMsg);

        recyclerView.setHasFixedSize(true);

        //now initialize the layout that we want to use Linear layout
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        sqLiteDataBaseHelper = new SQLiteDataBaseHelper(this.getContext());

        rideListData = sqLiteDataBaseHelper.getRideList();


        if (rideListData.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            tvMsg.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvMsg.setVisibility(View.INVISIBLE);
        }


        adapter = new RideListCustomAdapter(rideListData);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
