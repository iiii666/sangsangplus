package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private EditText et_test_id;
    private EditText et_test_pw;
    private Button btn_move_lg,btn_move_assign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this).setContentTitle("위험 상황입니까").setContentText("5분내로 응답이 없는경우 구조요청을 합니다");
        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(),R.drawable.kakaotalk_20210716_130131343);
        PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this,0,new Intent(getApplicationContext(),SubActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,mBuilder.build());
        et_test_id=findViewById(R.id.et_test_id);
        et_test_pw=findViewById(R.id.et_test_pw);
        btn_move_lg=findViewById(R.id.btn_move_lg);
        btn_move_assign=findViewById(R.id.btn_move_assign);

        btn_move_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AssignActivity.class);
                startActivity(intent);
            }
        });
        btn_move_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = et_test_id.getText().toString();
                String userPassword = et_test_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");
                                String userName =jsonObject.getString("userName");
                                Toast.makeText(getApplicationContext(), "로그인 에 성공하였습니다", Toast.LENGTH_SHORT).show();

                                Intent intent2 = new Intent(MainActivity.this,SubActivity.class);
                                Intent intent3 = new Intent(MainActivity.this,Myinfo.class);
                                Intent intent_add=getIntent();//주소 받아옴
                                intent2.putExtra("userID",userID);
                                intent2.putExtra("userPassword",userPassword);
                                intent2.putExtra("userName",userName);
                                startActivity(intent2);
                               // startActivity(intent3);

                            } else {

                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);


            }

        });

    }
}