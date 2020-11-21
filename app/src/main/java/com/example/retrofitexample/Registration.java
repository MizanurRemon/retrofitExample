package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    ImageView backButton;
    Button signUp;
    EditText nameText, mailText, phoneText, passwordText;
    TextInputEditText repassText;
    String name, mail, phone, password, repassword;
    TextInputLayout passerror, repasserror;
    ApiInterface apiservice;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        backButton = (ImageView) findViewById(R.id.backButtonID);
        signUp = (Button) findViewById(R.id.signUpID);
        nameText = (EditText) findViewById(R.id.nameID);
        mailText = (EditText) findViewById(R.id.emailID);
        phoneText = (EditText) findViewById(R.id.contactID);
        passwordText = (EditText) findViewById(R.id.passwordID);
        repassText = (TextInputEditText) findViewById(R.id.repasswordID);

        passerror = (TextInputLayout) findViewById(R.id.passError);
        repasserror = (TextInputLayout) findViewById(R.id.repassErrorID);
        progressDialog = new ProgressDialog(this);

        apiservice = ApiUtils.getAPIService();

        signUp.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signUpID) {

            name = nameText.getText().toString().trim();
            mail = mailText.getText().toString().trim();
            phone = phoneText.getText().toString().trim();
            password = passwordText.getText().toString().trim();
            repassword = repassText.getText().toString().trim();

            validate(name, mail, phone, password, repassword);

        }else if(v.getId()== R.id.backButtonID){
            startActivity(new Intent(Registration.this, LoginActivity.class));
        }
    }

    private void validate(String name, String mail, String phone, String password, String repassword) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
            if (password.length() < 6) {
                passerror.setError(getResources().getString(R.string.password_too_short));
            } else {
                passerror.setErrorEnabled(false);
                if (password.equals(repassword)) {
                    repasserror.setErrorEnabled(false);
                    sendpost(name, mail, phone, password);
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
                } else {
                    repasserror.setError(getResources().getString(R.string.not_match));
                }
            }
        } else {
            Toast toast = Toast.makeText(Registration.this, "Empty field found", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void sendpost(String name, String mail, String phone, String password) {

        Call<String> call = apiservice.savePost(name, mail, phone, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(Registration.this, "Registration Successfull", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                startActivity(new Intent(Registration.this, viewPage.class));
                finish();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("FUCK", t.getMessage());
                Toast toast = Toast.makeText(Registration.this, "Error", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
}