package com.example.mazda.requestpermission.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mazda.requestpermission.Constants;
import com.example.mazda.requestpermission.RequestBodyHelper;
import com.example.mazda.requestpermission.MethodCollection;
import com.example.mazda.requestpermission.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout firstName, lastName, email, phoneNumber, password;
    TextInputEditText inputFirstName, inputLastName, inputEmail, inputPhoneNumber, inputPassword;
    RadioButton radioButtonPria, radioButtonWanita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    @SuppressLint("ResourceType")
    private void init() {
        inputFirstName = findViewById(R.id.input_firstname);
        inputLastName = findViewById(R.id.input_lastname);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputPhoneNumber = findViewById(R.id.input_phone_number);
        radioButtonPria = findViewById(R.id.rb_pria);
        radioButtonWanita = findViewById(R.id.rb_wanita);
        radioButtonPria.setId(1);
        radioButtonWanita.setId(2);
        radioButtonPria.setChecked(true);

    }

    public void doRegister(View view) throws JSONException, NoSuchAlgorithmException {
        RadioGroup radioGroup = findViewById(R.id.radio_grup);
        int gender = radioGroup.getCheckedRadioButtonId();
        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputLastName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String phoneNumber = inputPhoneNumber.getText().toString().trim();
        String password = MethodCollection.convertToMD5(inputPassword.getText().toString().trim());
        RequestBodyHelper.registerUser(firstName, lastName, email, password, phoneNumber, gender, Constants.REGISTER_REQUEST, this);
    }
}
