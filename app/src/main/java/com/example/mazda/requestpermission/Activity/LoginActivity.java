package com.example.mazda.requestpermission.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mazda.requestpermission.Constants;
import com.example.mazda.requestpermission.RequestBodyHelper;
import com.example.mazda.requestpermission.MethodCollection;
import com.example.mazda.requestpermission.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout inputEmail, inputPassword;
    TextInputEditText editTextEmail, editTextPassword;
    public static CoordinatorLayout snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        inputEmail = findViewById(R.id.layout_input_email);
        inputPassword = findViewById(R.id.layout_input_password);
        editTextEmail = findViewById(R.id.input_email);
        editTextPassword = findViewById(R.id.input_password);

        snackbar = findViewById(R.id.snackbar);
    }

    public void doLogin(View view) throws JSONException, NoSuchAlgorithmException {
        if (editTextEmail.getText() == null) {
            inputEmail.setError("Harap isi email");
            if (editTextPassword.getText() == null) {
                inputPassword.setError("Harap isi password");
            }
            if (editTextEmail.getText() == null && editTextPassword.getText() == null) {
                inputEmail.setError("Email tidak boleh kosong");
                inputPassword.setError("Password tidak boleh kosong");
            }
        } else {
            String email = editTextEmail.getText().toString().trim();
            String password = MethodCollection.convertToMD5(editTextPassword.getText().toString());
            RequestBodyHelper.loginUser(LoginActivity.this, email, password, Constants.LOGIN_USER_REQUEST);
        }
    }

    public void moveToRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void showDialogReset(View view) {
        final View forgotPassword = LayoutInflater.from(this).inflate(R.layout.forgot_password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(forgotPassword);
        builder.setTitle("Reset password");
        builder.setCancelable(false);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextInputEditText emailReset = forgotPassword.findViewById(R.id.email_reset);
                String email = emailReset.getText().toString().trim();
                try {
                    RequestBodyHelper.resetPassword(email, Constants.RESET_PASSWORD_REQUEST, LoginActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog resetPassword = builder.create();
        resetPassword.show();
    }
}
