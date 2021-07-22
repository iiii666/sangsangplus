package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    private ImageButton sub_info;
    private ImageButton sub_service;
    private ImageButton sub_ex;





    TextView TextView;

    private Socket socket;
    private thread thread;
    private InputStream IS;
    private PrintWriter OS;
    private int port = 8888; //서버랑 꼭 포트 번호 같게 만들어주고
    private String SERVER_IP = "192.168.0.19";  //서버 아이피 주소 적어주세요!

    private ImageButton btn_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            socket = new Socket(SERVER_IP, port);
            OS = new PrintWriter(socket.getOutputStream(),true);
            Log.d("socket","socket");
        }catch(Exception e){
            e.printStackTrace();
        }
        thread = new thread();
        thread.start();
        OS.println("connect success");
        Log.d("connect success","connect success");

        TextView = (TextView)findViewById(R.id.TextView);



        //살짝 바꿨는데 잘 모르겠어요...

       /* btn_service = findViewById(R.id.btn_service);
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this , Service.class);
                startActivity(intent);
            }
        });*/
    }


    class thread extends Thread
    {
        @Override
        public void run() {
            byte[] buf = new byte[1024];
            String data = null;
            while(true)
            {
                try {
                    IS = socket.getInputStream();
                    DataInputStream DIS = new DataInputStream(IS);
                    DIS.read(buf,0,1024);
                    final String redata = new String(buf,0,1024);
                    if(data != redata)
                    {
                        TextView.setText(redata);
                    }
                    Log.d("data","data : " + redata);


                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            socket.close(); //소켓을 닫는다.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}