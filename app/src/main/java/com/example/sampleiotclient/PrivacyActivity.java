package com.example.sampleiotclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.sampleiotclient.adapter.PrivacyAdapter;
import com.example.sampleiotclient.common.Api;
import com.example.sampleiotclient.common.DownloadFile;
import com.example.sampleiotclient.pojo.index.PrivacyPolicyReportResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyActivity extends AppCompatActivity {

    private PrivacyPolicyReportResponse privacyPolicyReportResponse = new PrivacyPolicyReportResponse();
    private RecyclerView privacyRecyclerView;
    private PrivacyAdapter privacyAdapter;
    private Button downloadFileButton;
    private TextView reportVersionTextView;
    private TextView reportDescriptionTextView;
    private String udn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        init();
    }

    private void init() {
        udn = getIntent().getStringExtra("udn");
        reportVersionTextView = findViewById(R.id.text_report_version);
        reportDescriptionTextView = findViewById(R.id.text_report_description);
        downloadFileButton = findViewById(R.id.button_download_file);
        downloadFileButton.setOnClickListener(v ->
                new DownloadFile(this,
                        "http://192.168.2.114:8081/document/" + udn));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        readPrivacyPolicyReportByDevice(udn);
    }

    private void setAdapter() {
        privacyRecyclerView = findViewById(R.id.privacy_recycler_view);
        privacyAdapter = new PrivacyAdapter(this, privacyPolicyReportResponse);
        privacyRecyclerView.setAdapter(privacyAdapter);
        privacyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setDevice() {
        reportVersionTextView.setText("版本" + privacyPolicyReportResponse.getVersion());
        reportDescriptionTextView.setText(privacyPolicyReportResponse.getDescription());
    }

    public void readPrivacyPolicyReportByDevice(@NonNull String udn) {
        Api.getApi("http://192.168.2.114:8080").readPrivacyPolicyReportByDevice(udn, "user").enqueue(new Callback<PrivacyPolicyReportResponse>() {
            @Override
            public void onResponse(Call<PrivacyPolicyReportResponse> call, Response<PrivacyPolicyReportResponse> response) {
                Log.i("readPrivacyPolicyReportByDevice - onResponse()", "success");
                privacyPolicyReportResponse = response.body();
                setAdapter();
                setDevice();
            }

            @Override
            public void onFailure(Call<PrivacyPolicyReportResponse> call, Throwable t) {
                Log.e("readPrivacyPolicyReportByDevice - onFailure()", t.getMessage(), t);
                startActivity(new Intent(PrivacyActivity.this, HomeActivity.class));
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
