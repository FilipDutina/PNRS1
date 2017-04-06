package com.example.student.vezbetreci;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by student on 6.4.2017.
 */

public class Krug extends View {

    Paint zelena = new Paint();
    Paint zuta = new Paint();
    Paint crvena = new Paint();
    Paint plava = new Paint();
    Context context = getContext();
    int x;
    int y;
    int flag1;

    //**************************************

    public Krug(Context context) {
        super(context);
        zelena.setColor(Color.GREEN);
        zuta.setColor(Color.YELLOW);
        crvena.setColor(Color.RED);
        plava.setColor(Color.rgb(103, 213, 255));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //if(flag1 == 1)
            canvas.drawCircle(getWidth()/2, getHeight()/2, 100, plava);
        //else
            //canvas.drawCircle(getWidth()/2, getHeight()/2, 100, zelena);

    }

    public boolean onTouchEvent(MotionEvent e)
    {
        x = (int) e.getX();
        y = (int) e.getY();

        if((x > getWidth()/2 - 100) && (x < getWidth()/2 + 100) && (y > getHeight()/2 - 100) && (y < getHeight()/2 + 100))
        {
            //Canvas crtanje;
            Toast.makeText(context, "Krug prichkinut", Toast.LENGTH_SHORT).show();
            //flag1 = 1;
        }
        //else
            //flag1 = 0;
        return true;
    }
}
