package com.example.new_project_check;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();

            }
        }, 4000);
    }
}