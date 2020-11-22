package com.example.retrofitexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView goRegister;
    TextInputEditText email, password;
    Button signButton;
    TextInputLayout emailerror, passerror;
    LoginInterface loginService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            movetoActivity();
        }

        goRegister = (TextView) findViewById(R.id.goRegisterID);
        email = (TextInputEditText) findViewById(R.id.logEmailID);
        password = (TextInputEditText) findViewById(R.id.logPasswordID);
        signButton = (Button) findViewById(R.id.signInID);
        emailerror = (TextInputLayout) findViewById(R.id.emailError);
        passerror = (TextInputLayout) findViewById(R.id.passError);
        loginService = ApiUtils.loginInterface();
        progressDialog = new ProgressDialog(this);

        goRegister.setOnClickListener(this);
        signButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.goRegisterID) {
            startActivity(new Intent(LoginActivity.this, Registration.class));
        } else if (v.getId() == R.id.signInID) {
           String mail = email.getText().toString().trim();
           String pass = password.getText().toString().trim();
           validate(mail, pass);


        }
    }

    public void validate(String mail, String pass) {
        emailerror.setErrorEnabled(false);
        passerror.setErrorEnabled(false);
        if (!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass)){

            if(pass.length()<6){
                passerror.setError("Too short");
            }else{
                sendPost(mail, pass);
                progressDialog.setMessage("Signing In");
                progressDialog.show();
            }

        }else{
            if(TextUtils.isEmpty(mail)){
                emailerror.setError("Empty");
            }else if(TextUtils.isEmpty(pass)){
                passerror.setError("Empty");
            }
        }
    }

    public void sendPost(String mail, String pass) {
        Call<FetchData> call = loginService.savePost(mail, pass);
        call.enqueue(new Callback<FetchData>() {
            @Override
            public void onResponse(Call<FetchData> call, Response<FetchData> response) {
                FetchData ob=response.body();
                String userid=ob.getUserid();
                Log.d("UserID:: ", userid);
                int user_session_id = Integer.parseInt(userid);
                progressDialog.dismiss();
                if(!(userid.equals("-1"))) {
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(user_session_id);
                    movetoActivity();
                }else{
                    Toast toast = Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<FetchData> call, Throwable t) {
                Log.d("Error:: ", t.getMessage());
                Toast toast = Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

    }

    private void movetoActivity() {

        Intent intent = new Intent(LoginActivity.this, viewPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}