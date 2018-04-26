package com.amits.gomules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amits.gomules.DataBase.SQLiteDataBaseHelper;
import com.amits.gomules.Entity.UserEntity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnRegister;

    UserEntity userEntity;

    SQLiteDataBaseHelper sqLiteDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFirstName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);

        btnRegister.setOnClickListener(this);

        userEntity=new UserEntity();

        sqLiteDataBaseHelper= new SQLiteDataBaseHelper(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnRegister)
        {

            String firstname=etFirstName.getText().toString();
            String lastName=etLastName.getText().toString();
            String email=etEmail.getText().toString();
            String password=etPassword.getText().toString();
            String confirmpassword=etConfirmPassword.getText().toString();

            if(firstname.length() == 0 ) {
                etFirstName.setError("First Name is required!");
                return;
            }
            if(lastName.length() == 0 ) {
                etLastName.setError("Last Name is required!");
                return;
            }
            if(email.length() == 0 || (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                etEmail.setError("A valid email is required!");
                return;
            }
            if(password.length() == 0 ) {
                etPassword.setError("Password is required!");
                return;
            }
            if(confirmpassword.length() == 0 ) {
                etConfirmPassword.setError("Confirm Password is required!");
                return;
            }

            if(!confirmpassword.equals(password)) {
                etConfirmPassword.setError("Confirm Password must match the Password!");
                return;
            }

            userEntity.setFirstName(firstname);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);
            userEntity.setPassword(password);

            long userID = sqLiteDataBaseHelper.addUser(userEntity);
            if(userID>0)
            {
                Toast.makeText(this,R.string.registration_success, Toast.LENGTH_SHORT).show();

                //Should I redirect to the Login page or the dashboard?
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
            }
            else
            {
                Toast.makeText(this,R.string.registration_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
