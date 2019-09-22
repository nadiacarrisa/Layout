package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Button btn = (Button)findViewById(R.id.btnSubmit);
    private boolean isReciverReigtered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signup(View view){
        Intent intent = new Intent(MainActivity.this, Main_Activity_Utama.class);
        startActivity(intent);
    }

    public void login(View view){
        final EditText emailValidate = (EditText)findViewById(R.id.txtEmail);

        final TextView textView = (TextView)findViewById(R.id.text);

        String email = emailValidate.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // onClick of button perform this simplest code.
        if (email.matches(emailPattern))
        {
            Intent i = new Intent(MainActivity.this, home_viewpager.class);
            Bundle b = new Bundle();
            b.putString("email", email);
            i.putExtras(b);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }

    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!isNetworkAvailable(context)){
                Toast.makeText(context, "ON",Toast.LENGTH_SHORT).show();
                Notification(context, "Wifi Connection On");
            }
            else if(isNetworkAvailable(context)){
                Toast.makeText(context, "OFF",Toast.LENGTH_SHORT).show();
                Notification(context, "Wifi Connection OFF");
            }
        }
        public void Notification(Context context, String msg){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher_simbol)
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

//    txt.setOnClickListener(new View.OnClickListener(){
//       @Override
//       public void onClick(View v){
//
//        }
//    });

//    txt.setOnClickListener (new View.OnClickListener(){
//        @Override
//                public void onClick(View v){
//            Intent i = new Intent (MainActivity.this, Activity_Kedua.class);
//            startActivity(i);
//        }
//    });
}
