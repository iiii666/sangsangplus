package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AssignActivity extends AppCompatActivity {

    private EditText as_id;
    private EditText as_pw;
    private EditText as_add;
    private Button as_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        as_id = findViewById(R.id.as_id);
        as_pw = findViewById(R.id.as_pw);
        as_add = findViewById(R.id.as_add);
        as_ok = findViewById(R.id.as_ok);
//회원가입 버튼 클릭시 수행
        as_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = as_id.getText().toString();
                String userPassword = as_pw.getText().toString();
                String userName = as_add.getText().toString();
                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AssignActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {

                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(AssignActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}