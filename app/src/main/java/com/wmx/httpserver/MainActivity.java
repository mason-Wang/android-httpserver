package com.wmx.httpserver;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private HttpServer mHttpd;
    private TextView mUploadInfo, mDownloadInfo;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.server_info);
        StringBuilder serverInfo = new StringBuilder().append("HTTP Sever Address: http://").
                append(getWifiIpAddress()).append(":8080\n").
                append("Using \"curl -F file=@myfile").append(" http://").
                append(getWifiIpAddress()).append(":8080\" ").append(" to upload file");
        tv.setText(serverInfo);

        mUploadInfo = (TextView) findViewById(R.id.upload_info);
        mDownloadInfo = (TextView) findViewById(R.id.download_info);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mHttpd = new HttpServer(8080);
        mHttpd.setOnStatusUpdateListener(new HttpServer.OnStatusUpdateListener() {
            @Override
            public void onUploadingProgressUpdate(final int progress) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(progress);
                    }
                });
            }

            @Override
            public void onUploadingFile(final File file, final boolean done) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (done) {
                            mUploadInfo.setText("Upload file " + file.getName() + " done!");
                        } else {
                            mProgressBar.setProgress(0);
                            mUploadInfo.setText("Uploading file " + file.getName() + "...");
                        }
                    }
                });
            }

            @Override
            public void onDownloadingFile(final File file, final boolean done) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (done) {
                            mDownloadInfo.setText("Download file " + file.getName() + " done!") ;
                        } else {
                            mDownloadInfo.setText("Downloading file " + file.getName() + " ...");
                        }
                    }
                });
            }
        });

        try {
            mHttpd.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mHttpd.stop();
        super.onDestroy();
    }

    public String getWifiIpAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return android.text.format.Formatter.formatIpAddress(info.getIpAddress());
    }
}
