package com.example.student.vezbetreci;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by student on 3.4.2017.
 */

public class Nova extends View {
    Paint zelena = new Paint();
    Paint zuta = new Paint();
    Paint crvena = new Paint();
    Paint plava = new Paint();

    public Nova(Context context) {
        super(context);
        zelena.setColor(Color.GREEN);
        zuta.setColor(Color.YELLOW);
        crvena.setColor(Color.RED);
        plava.setColor(Color.rgb(103, 213, 255));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), plava);   //nebo
        canvas.drawRect(0, getWidth(), 1200, getBottom(), zelena);  //trava
        canvas.drawCircle(0, 0, 300, zuta); //sunce
        canvas.drawRect(600, 800, 900, 1080, zuta); //kuca
        canvas.drawRect(670, 970, 750, 1080, crvena);   //vrata
        //canvas.drawRect(700, 1000, 720, 1020, crvena);   //prozor zavrsi
    }

}
