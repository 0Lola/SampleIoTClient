package com.example.sampleiotclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.sampleiotclient.adapter.RecordAdapter;
import com.example.sampleiotclient.common.Api;
import com.example.sampleiotclient.pojo.index.PrivacyChoiceResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordActivity extends AppCompatActivity {

    private LinkedList<PrivacyChoiceResponse> privacyChoiceResponses = new LinkedList<>();
    private RecyclerView recordRecyclerView;
    private RecordAdapter recordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
    }

    private void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        readRecords();
    }

    private void setAdapter(){
        recordRecyclerView = findViewById(R.id.record_recycler_view);
        recordAdapter = new RecordAdapter(this,privacyChoiceResponses);
        recordRecyclerView.setAdapter(recordAdapter);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void readRecords() {
        Api.getApi("http://192.168.2.114:8080").readPrivacyChoiceRecordsByUser("user").enqueue(new Callback<List<PrivacyChoiceResponse>>() {
            @Override
            public void onResponse(Call<List<PrivacyChoiceResponse>> call, @NonNull Response<List<PrivacyChoiceResponse>> response) {
                Log.i("fetchRecord - onResponse()", "success");
                privacyChoiceResponses = response.body() == null ?
                        null : response.body().stream().collect(Collectors.toCollection(LinkedList::new));
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<PrivacyChoiceResponse>> call, Throwable t) {
                Log.e("fetchRecord - onFailure()", t.getMessage(), t);
                privacyChoiceResponses = new LinkedList<>();
                setAdapter();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
