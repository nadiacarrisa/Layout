package com.example.latihan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;


public class home_viewpager extends AppCompatActivity {
    private boolean isReciverReigtered = false;
    private static final String TAG = "MainActivity";
    public static final long INTERVAL=3000;//variable to execute services every 10 second
    private Handler mHandler=new Handler(); // run on another Thread to avoid crash
    private Timer mTimer=null; // timer handling
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_viewpager);
        ViewPager vpgr = findViewById(R.id.view_pager);
        vpgr.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tl = findViewById(R.id.tab_layout);
        tl.setupWithViewPager(vpgr);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String strEmail = bundle.getString("email","");
        Toast.makeText(getApplicationContext(), "Selamat datang "+strEmail,Toast.LENGTH_SHORT
        ).show();
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();

            if(!isNetworkAvailable(context)){
                //Toast.makeText(context, "ON",Toast.LENGTH_SHORT).show();
                Notification("Wifi Connection On");
            }
            else if(isNetworkAvailable(context)){
                //Toast.makeText(context, "OFF",Toast.LENGTH_SHORT).show();
                Notification("Wifi Connection OFF");
            }

        } };
        private void Notification(String message){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel =
                        new NotificationChannel("Notifications", "Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Notifications")
                    .setContentTitle("Wifi Notofication")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .setContentText(message);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(999,builder.build());

        }

        private boolean isNetworkAvailable(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        }

    protected void onResume(){
        super.onResume();
        if(!isReciverReigtered){
            isReciverReigtered = true;
            registerReceiver(receiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        }
    }
    protected void onPause(){
        super.onPause();
        if(isReciverReigtered){

        }
    }
    public void scheduleJob(View v){
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
//                .setRequiresCharging(true)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
        if(mTimer!=null)
            mTimer.cancel();
        else
            mTimer=new Timer(); // recreate new timer
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(),0,INTERVAL);
    }
    public void cancelJob(View v){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG , "Job canceled");
        mTimer.cancel();
    }
    private class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast at every 3 second
                    Toast.makeText(getApplicationContext(), "3detik", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


