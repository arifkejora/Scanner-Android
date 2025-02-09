package com.trisnasejati.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {
    TextView Username;
    String nama;
    Button btnTambah, btnLihatBarang;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(DashboardActivity.this);

        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        //etUsername = findViewById(R.id.etMainUsername);
        Username = findViewById(R.id.tvUsername);

        //username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        nama = sessionManager.getUserDetail().get(SessionManager.NAMA);

        //etUsername.setText(username);
        Username.setText(nama);

        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLihatBarang = findViewById(R.id.btnLihatBarang);
        btnLihatBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LihatBarangActivity.class);
                startActivity(intent);
            }
        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }


}