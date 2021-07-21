package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Myinfo extends AppCompatActivity {

    private TextView info_id,info_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        Intent intent3 = getIntent();
        String userID = intent3.getStringExtra("userID");
        String userPassword = intent3.getStringExtra("userPassword");

        info_id=findViewById(R.id.info_id);
        info_pw=findViewById(R.id.info_pw);

        info_id.setText(userID);
        info_pw.setText(userPassword);
    }
}