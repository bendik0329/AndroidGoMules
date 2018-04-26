package com.amits.gomules;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amits.gomules.Utils.SharedPrefUtil;

public class WelcomeFragment extends Fragment {

    SharedPreferences sharedPref;
    TextView tvWelcome;

    public WelcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        tvWelcome = view.findViewById(R.id.etWelcome);
        sharedPref = getActivity().getSharedPreferences(SharedPrefUtil.Name, getActivity().MODE_PRIVATE);

        if (sharedPref.contains(SharedPrefUtil.FirstName) && sharedPref.contains(SharedPrefUtil.LastName)) {
            tvWelcome.setText("Welcome, " + sharedPref.getString(SharedPrefUtil.FirstName, "") + " " + sharedPref.getString(SharedPrefUtil.LastName, ""));
        }

        // Inflate the layout for this fragment
        return view;
    }

}
