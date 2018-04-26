package com.amits.gomules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amits.gomules.DataBase.SQLiteDataBaseHelper;
import com.amits.gomules.Entity.UserEntity;
import com.amits.gomules.Utils.SharedPrefUtil;
import com.amits.gomules.Utils.UserTable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    UserEntity userEntity;
    SQLiteDataBaseHelper sqLiteDataBaseHelper;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        userEntity = new UserEntity();
        sqLiteDataBaseHelper = new SQLiteDataBaseHelper(this);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        sharedPref = getApplicationContext().getSharedPreferences(SharedPrefUtil.Name, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {

        int viewID = v.getId();

        if (viewID == R.id.btnLogin) {

            String email=etEmail.getText().toString();
            String password=etPassword.getText().toString();

            if(email.length() == 0 ) {
                etEmail.setError("Email is required!");
            return;
            }
            if(password.length() == 0 ) {
                etPassword.setError("Password is required!");
            return;
            }

            userEntity.setEmail(email);

            userEntity.setPassword(password);

            UserEntity userDetails = sqLiteDataBaseHelper.login(userEntity);
            if (userDetails.isUserExists()) {
                editor = sharedPref.edit();
                editor.putString(SharedPrefUtil.FirstName, userDetails.getFirstName());
                editor.putString(SharedPrefUtil.LastName, userDetails.getLastName());
                editor.putInt(SharedPrefUtil.ID, userDetails.getID());
                editor.putString(SharedPrefUtil.Email, userDetails.getEmail());

                editor.commit(); // commit changes

                Intent intentNav = new Intent(this, NavigationActivity.class);
                startActivity(intentNav);
            } else {
                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
            }
        } else if (viewID == R.id.btnRegister) {
            Intent intentRegister = new Intent(this, RegistrationActivity.class);
            startActivity(intentRegister);
        }
    }
}
