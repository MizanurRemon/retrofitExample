package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class viewPage extends AppCompatActivity {
    RecyclerView recyclerView;
    GetDataInterface getdataservice;

    private DataAdapter dataAdapter;
    private List<FetchData> fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getdataservice = ApiUtils.getDataInterface();

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
                Toast toast = Toast.makeText(viewPage.this, t.getMessage(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.d("Error", t.getMessage());
            }
        });
    }
}