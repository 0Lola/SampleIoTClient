package com.example.sampleiotclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleiotclient.PrivacyActivity;
import com.example.sampleiotclient.R;
import com.example.sampleiotclient.pojo.device.Device;

import java.util.LinkedList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {

    private final LinkedList<Device> mList;
    private final LayoutInflater inflater;
    private Context context;

    public DeviceAdapter(Context context, LinkedList<Device> mList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.recycler_view_device, parent, false);
        return new MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder mholder, int position) {
        Device device = mList.get(position);
        mholder.position = position;
        mholder.device = device;
        mholder.textDeviceName.setText(device.getName());
        mholder.textDeviceUDN.setText(device.getUdn());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public Device device;
        public TextView textDeviceName;
        public TextView textDeviceUDN;
        public DeviceAdapter mAdapter;

        public MyViewHolder(View itemView, DeviceAdapter adapter) {
            super(itemView);
            this.textDeviceName = itemView.findViewById(R.id.text_device_name);
            this.textDeviceUDN = itemView.findViewById(R.id.text_device_udn);
            this.mAdapter = adapter;

            itemView.setOnClickListener(v -> context.startActivity(
                    new Intent(context, PrivacyActivity.class)
                            .putExtra("udn", textDeviceUDN.getText().toString())));
        }


    }

}
