package com.devang.mathsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView welcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        welcome =findViewById(R.id.welcomeTxt);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);


    }

}