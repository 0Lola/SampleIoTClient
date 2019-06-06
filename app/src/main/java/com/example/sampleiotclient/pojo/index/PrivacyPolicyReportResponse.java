package com.example.sampleiotclient.pojo.index;

import android.provider.DocumentsContract;

import com.example.sampleiotclient.pojo.device.Device;
import com.example.sampleiotclient.pojo.privacy.PrivacyPolicy;
import com.example.sampleiotclient.pojo.privacy.PrivacyPolicyReport;

import java.util.HashMap;
import java.util.List;

public class PrivacyPolicyReportResponse extends PrivacyPolicyReport {

    private HashMap<Integer, Boolean> choices ;

    public PrivacyPolicyReportResponse(){
        super();
    }

    public PrivacyPolicyReportResponse(String id, String version, String description, Device device, List<PrivacyPolicy> policies, DocumentsContract.Document document, HashMap<Integer, Boolean> choices) {
        super();
        this.choices = choices;
    }

    public HashMap<Integer, Boolean> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<Integer, Boolean> choices) {
        this.choices = choices;
    }
}
