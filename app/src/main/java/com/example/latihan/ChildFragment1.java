package com.example.latihan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class ChildFragment1 extends Fragment {
    private static final String TAG = "MainActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_child_fragment1, container, false);
        Button buttonInFragment1 = rootView.findViewById(R.id.btnfragment);
        buttonInFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), Coba_Fragment.class);
                startActivity(intent1);
            }
        });
        Button buttonInFragment2 = rootView.findViewById(R.id.logout);
        buttonInFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
        return rootView;
    }
}
