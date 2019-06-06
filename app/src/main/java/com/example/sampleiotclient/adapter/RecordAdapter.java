package com.example.sampleiotclient.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleiotclient.R;
import com.example.sampleiotclient.pojo.index.PrivacyChoiceResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    private final LinkedList<PrivacyChoiceResponse> mList;
    private final LayoutInflater inflater;
    private Context context;

    public RecordAdapter(Context context, LinkedList<PrivacyChoiceResponse> mList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.recycler_view_record, parent, false);
        return new MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder mholder, int position) {
        PrivacyChoiceResponse response = mList.get(position);
        mholder.position = position;
        mholder.response = response;
        mholder.textDeviceName.setText(response.getPrivacyChoice().getPrivacyContent().getDevice().getName());
        mholder.textPrivacyIndex.setText("隱私政策 " + response.getPrivacyChoice().getPrivacyContent().getPolicy().getId());
        mholder.textPrivacyDescription.setText(response.getPrivacyChoice().getPrivacyContent().getPolicy().getDescription());
        mholder.textPrivacyContent.setText(response.getPrivacyChoice().isAccepted() ? "同意" : "拒絕");
        mholder.textPrivacyContent.setBackground(response.getPrivacyChoice().isAccepted() ?
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.green_radius_background, null) :
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.red_radius_background, null));
        mholder.textPrivacyTime.setText(convertDateTime(response.getLocalDateTime()));
    }

    private String convertDateTime(String dateTime) {
        SimpleDateFormat preConvert = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat postConvert = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String convertDateTime = "";
        try {
            convertDateTime = postConvert.format(preConvert.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertDateTime;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public PrivacyChoiceResponse response;
        public TextView textDeviceName;
        public TextView textPrivacyIndex;
        public TextView textPrivacyDescription;
        public TextView textPrivacyContent;
        public TextView textPrivacyTime;
        public RecordAdapter mAdapter;

        public MyViewHolder(View itemView, RecordAdapter adapter) {
            super(itemView);
            this.textDeviceName = itemView.findViewById(R.id.text_device_name);
            this.textPrivacyIndex = itemView.findViewById(R.id.text_privacy_index);
            this.textPrivacyDescription = itemView.findViewById(R.id.text_privacy_description);
            this.textPrivacyContent = itemView.findViewById(R.id.text_privacy_content);
            this.textPrivacyTime = itemView.findViewById(R.id.text_privacy_time);
            this.mAdapter = adapter;
        }


    }

}
