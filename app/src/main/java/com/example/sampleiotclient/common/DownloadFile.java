package com.example.sampleiotclient.common;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.sampleiotclient.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class DownloadFile {

    private static final String TAG = "Download File";
    public static final String CHANNELID = "118";
    public static final CharSequence CHANNELCHAR = "Download Report File";
    private NotificationManager notificationManager;

    private Context context;
    private String downloadUrl;
    private String downloadFileName;
    private AlertDialog dialog;


    public DownloadFile(Context context, String downloadUrl) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        this.dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.privacy_loading_message)
                .setTitle(R.string.detail_report_download)
                .create();
        isStoragePermissionGranted();
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        @Override
        protected void onPostExecute(Void result) {
            try {
                dialog.hide();
                if (outputFile != null) {
                    notification();
                    Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    dialogDelay();
                    Toast.makeText(context, "Downloaded Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                dialogDelay();
                Toast.makeText(context, "Downloaded Failed with Exception", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                downloadFileName = java.net.URLDecoder.decode(
                        connection.getHeaderField("Content-Disposition")
                                .substring(connection.getHeaderField("Content-Disposition").indexOf("=")+1)
                                .replace("UTF-8''","")
                                .trim(),
                        String.valueOf(StandardCharsets.UTF_8));

                // check SDCard
                if (!new CheckForSDCard().isSDCardPresent()) {
                    Toast.makeText(context, "There is no SD Card.", Toast.LENGTH_SHORT).show();
                }

                // check file
                outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), downloadFileName);
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    outputFile.setWritable(true);
                    Log.e(TAG, "File Created");
                }
                // output handle
                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                OutputStream output = new FileOutputStream(outputFile.getPath());
                byte[] data = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                fos.flush();
                fos.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }
            return null;
        }
    }

    class CheckForSDCard {
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                requestPermissions((Activity)context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void dialogDelay() {
        new Handler().postDelayed(() ->{}, 3000);
    }

    private void notification(){
            int NOTIFY_ID = 0;
            NotificationCompat.Builder builder;
            if (notificationManager == null) {
                notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(CHANNELID);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(CHANNELID, CHANNELCHAR, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(context, CHANNELID);
                builder.setContentTitle("下載成功")
                        .setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setContentText(downloadFileName)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setTicker("下載成功")
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            }
            else {
                builder = new NotificationCompat.Builder(context, CHANNELID);
                builder.setContentTitle("下載成功")
                        .setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setContentText(downloadFileName)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setTicker("下載成功")
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);
            }
            Notification notification = builder.build();
            notificationManager.notify(NOTIFY_ID, notification);
    }

}