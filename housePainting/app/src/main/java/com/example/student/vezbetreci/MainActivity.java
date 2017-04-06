package com.example.student.vezbetreci;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;


public class MainActivity extends AppCompatActivity {

    Nova crtaj;
    Krug krug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        krug = new Krug(this);
        krug.setBackgroundColor(Color.WHITE);
        setContentView(krug);
    }
}
