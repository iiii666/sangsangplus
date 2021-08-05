package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import android.net.Uri;
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
    private int port = 9000; //서버랑 꼭 포트 번호 같게 만들어주고
    private String SERVER_IP = "165.229.125.136";  //서버 아이피 주소 적어주세요!



    private String call_num = "01088914665";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        sub_info=findViewById(R.id.sub_info);
        sub_service= findViewById(R.id.sub_service);
        sub_ex=findViewById(R.id.sub_ex);

        sub_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SubActivity.this, Myinfo.class);
                Intent intent3 = getIntent();
                String userID= intent3.getStringExtra("userID");
                String userPassword= intent3.getStringExtra("userPassword");
                String userName = intent3.getStringExtra("userName");
                intent2.putExtra("userID",userID);
                intent2.putExtra("userPassword",userPassword);
                intent2.putExtra("userName",userName);
                startActivity(intent2);
            }
        });
        sub_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Service.class);
                startActivity(intent);
            }
        });

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
        sub_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SubActivity.this,Setting.class);
                startActivity(intent1);
            }
        });

        //살짝 바꿨는데 잘 모르겠어요...

<<<<<<< HEAD
//        if(TextView!=null)
//        {
//
//            AlertDialog.Builder ad = new AlertDialog.Builder(SubActivity.this);
//            ad.setIcon(R.mipmap.ic_launcher);
//            ad.setTitle("위험");
//            ad.setMessage("위험상황입니까?");
//
//            final EditText et = new EditText(SubActivity.this);
//            ad.setView(et);
//
//            ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    OS.println("Help me");
////                    OS.flush();
//                }
//            });
//
//            ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    OS.println("ignore");
//                }
//
//            });
//            ad.show();
//        }
=======
        if(TextView != null)
        {

            AlertDialog.Builder ad = new AlertDialog.Builder(SubActivity.this);
            ad.setIcon(R.mipmap.ic_launcher);
            ad.setTitle("위험");
            ad.setMessage("위험상황입니까?");

            final EditText et = new EditText(SubActivity.this);
            ad.setView(et);

            ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String tel = "tel:"+call_num;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(tel));

                        startActivity(intent);

                    //startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));


                }

            });

            ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ad.show();
        }
>>>>>>> develop



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
                    DIS.read(buf, 0, 1024);
                    final String redata = new String(buf, 0, 1024);
                    if (data != redata) {
                        TextView.setText(redata);

                    }
                    Log.d("data", "data : " + redata);


                    runOnUiThread(() -> {

                        if (redata.contains("help")) {

                            AlertDialog.Builder ad = new AlertDialog.Builder(SubActivity.this);
                            ad.setIcon(R.mipmap.ic_launcher);
                            ad.setTitle("위험");
                            ad.setMessage("위험상황입니까?");

                            final EditText et = new EditText(SubActivity.this);
                            ad.setView(et);

                            ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    OS.println("1");//위험상황 맞으면 1보냄
//                                    OS.close();
                                    //                    OS.flush();
                                }
                            });

                            ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    OS.println("0"); //아니면 0보냄
//                                    OS.close();
                                }

                            });
                            ad.show();
                        }
                    });
                }catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }

    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        try {
//            socket.close(); //소켓을 닫는다.
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}