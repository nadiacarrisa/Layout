package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Button btn = (Button)findViewById(R.id.btnSubmit);
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
            Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
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
