package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Coba_Fragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba__fragment);
        prepareFragment();
    }
    private void prepareFragment(){
        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_placeholder,new fragment_coba()).commit();
    }
}
