package com.example.retrofitexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.retrofitexample.R.id.logoutID;

public class viewPage extends AppCompatActivity {
    RecyclerView recyclerView;
    GetDataInterface getdataservice;

    private DataAdapter dataAdapter;
    private List<FetchData> fetchData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getdataservice = ApiUtils.getDataInterface();
        progressDialog = new ProgressDialog(this);

        getdata();
    }

    private void getdata() {
        Call<List<FetchData>> call = getdataservice.getalldata();
        call.enqueue(new Callback<List<FetchData>>() {
            @Override
            public void onResponse(Call<List<FetchData>> call, Response<List<FetchData>> response) {
                fetchData = response.body();
                dataAdapter = new DataAdapter(fetchData, viewPage.this);
                recyclerView.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<FetchData>> call, Throwable t) {
                Toast toast = Toast.makeText(viewPage.this, t.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == logoutID) {
            SessionManagement sessionManagement = new SessionManagement(viewPage.this);
            sessionManagement.removeSession();
            progressDialog.setMessage("Logging Out");
            progressDialog.show();
            moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToLogin() {
        Intent intent = new Intent(viewPage.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}