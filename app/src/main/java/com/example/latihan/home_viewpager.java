package com.example.latihan;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;


public class home_viewpager extends AppCompatActivity {
    private boolean isReciverReigtered = false;
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
            if(!isNetworkAvailable(context)){
                //Toast.makeText(context, "ON",Toast.LENGTH_SHORT).show();
                Notification(context, "Wifi Connection On");
            }
            else if(isNetworkAvailable(context)){
                //Toast.makeText(context, "OFF",Toast.LENGTH_SHORT).show();
                Notification(context, "Wifi Connection OFF");
            }
        }
        public void Notification(Context context, String msg){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setTicker(msg)
                    .setContentTitle("Latihan notification wifi")
                    .setContentText(msg)
                    .setAutoCancel(true);
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationmanager.notify(0, builder.build());
        }

        private boolean isNetworkAvailable(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        }
    };
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
}


