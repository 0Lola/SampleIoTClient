package com.example.sampleiotclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sampleiotclient.adapter.DeviceAdapter;
import com.example.sampleiotclient.common.Api;
import com.example.sampleiotclient.pojo.device.Device;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private LinkedList<Device> devices = new LinkedList<>();
    private RecyclerView deviceRecyclerView;
    private DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }


    private void init(){
        readDevices();
    }

    private void setAdapter(){
        deviceRecyclerView = findViewById(R.id.device_recycler_view);
        deviceAdapter = new DeviceAdapter(this,devices);
        deviceRecyclerView.setAdapter(deviceAdapter);
        deviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void readDevices() {
        Api.getApi("http://192.168.2.114:8081").readDevices().enqueue(
                new Callback<List<Device>>() {
                    @Override
                    public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                        Log.i("readDevices - onResponse()", "success");
                        devices = response.body() == null ?
                                null : response.body().stream().collect(Collectors.toCollection(LinkedList::new));
                        setAdapter();
                    }

                    @Override
                    public void onFailure(Call<List<Device>> call, Throwable t) {
                        Log.e("readDevices - onFailure()", t.getMessage(), t);
                        devices = new LinkedList<>();
                        setAdapter();
                    }
                }
        );
    }

}
