package com.example.sampleiotclient.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sampleiotclient.R;
import com.example.sampleiotclient.common.Api;
import com.example.sampleiotclient.common.Config;
import com.example.sampleiotclient.pojo.auth.User;
import com.example.sampleiotclient.pojo.index.PrivacyChoiceResponse;
import com.example.sampleiotclient.pojo.index.PrivacyPolicyReportResponse;
import com.example.sampleiotclient.pojo.privacy.PrivacyChoice;
import com.example.sampleiotclient.pojo.privacy.PrivacyContent;
import com.example.sampleiotclient.pojo.privacy.PrivacyPolicy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyAdapter extends RecyclerView.Adapter<PrivacyAdapter.MyViewHolder> {

    private final PrivacyPolicyReportResponse privacyPolicyReportResponse;
    private final LayoutInflater inflater;
    private Context context;
    private AlertDialog dialog;


    public PrivacyAdapter(Context context, PrivacyPolicyReportResponse privacyPolicyReportResponse) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.privacyPolicyReportResponse = privacyPolicyReportResponse;
        dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.privacy_loading_message)
                .setTitle(R.string.privacy_loading_title)
                .create();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.recycler_view_privacy, parent, false);
        return new MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder mholder, int position) {
        PrivacyPolicy privacyPolicy = privacyPolicyReportResponse.getPolicies().get(position);
        mholder.position = position;
        mholder.privacyPolicy = privacyPolicy;
        mholder.textPrivacyIndex.setText("隱私政策" + (position + 1));
        mholder.textPrivacyDescription.setText(privacyPolicy.getDescription());
        mholder.switchPrivacyChoice.setChecked(privacyPolicyReportResponse.getChoices().get(position));
    }

    @Override
    public int getItemCount() {
        return privacyPolicyReportResponse.getPolicies().size();
    }

    public void setPrivacyChoice(PrivacyContent privacyContent, @NonNull boolean isAccepted) {
        dialog.show();
        Api.getApi("http://192.168.2.114:8080").setPrivacyChoice(new PrivacyChoice()
                .setPrivacyContent(
                        privacyContent
                                .setUser(new User().setAccount("user")))
                .setAccepted(isAccepted))
                .enqueue(new Callback<PrivacyChoiceResponse>() {
                    @Override
                    public void onResponse(Call<PrivacyChoiceResponse> call, Response<PrivacyChoiceResponse> response) {
                        Log.i("setPrivacyChoice - onResponse()", "success");
                        new Handler().postDelayed(() -> dialog.hide(), 500);
                    }

                    @Override
                    public void onFailure(Call<PrivacyChoiceResponse> call, Throwable t) {
                        Log.e("setPrivacyChoice - onFailure()", t.getMessage(), t);
                        new Handler().postDelayed(() -> dialog.hide(), 500);
                    }
                });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public PrivacyPolicy privacyPolicy;
        public TextView textPrivacyIndex;
        public TextView textPrivacyDescription;
        public Switch switchPrivacyChoice;
        public PrivacyAdapter mAdapter;

        public MyViewHolder(View itemView, PrivacyAdapter adapter) {
            super(itemView);
            this.textPrivacyIndex = itemView.findViewById(R.id.text_privacy_index);
            this.textPrivacyDescription = itemView.findViewById(R.id.text_privacy_description);
            this.switchPrivacyChoice = itemView.findViewById(R.id.switch_privacy_choice);
            this.mAdapter = adapter;

            this.switchPrivacyChoice.setOnClickListener(v ->
                    setPrivacyChoice(
                            new PrivacyContent()
                                    .setPolicy(privacyPolicy)
                                    .setDevice(privacyPolicyReportResponse.getDevice()),
                            ((Switch) v).isChecked()
                    )
            );
        }

    }

}
