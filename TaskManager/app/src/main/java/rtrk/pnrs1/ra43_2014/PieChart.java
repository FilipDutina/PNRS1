package rtrk.pnrs1.ra43_2014;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Filip Dutina on 03-May-17.
 */

public class PieChart extends View{

    //variables
    protected Paint myPaint;
    protected Paint myBackgroundPaint;
    protected Paint myTextPaint;

    protected RectF myRect;

    protected float myPercentageTarget = 0;
    protected float myPercentage = 0;

    protected boolean percentageSet = false;


    public PieChart(Context context)
    {
        super(context);
        init();
    }

    public PieChart(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        init();
    }

    public PieChart(Context context, AttributeSet attributeSet, int defStyle)
    {
        super(context, attributeSet, defStyle);
        init();
    }

    public Paint getPaint()
    {
        return myPaint;
    }

    public void setMyPercentageTarget(float percentage)
    {
        this.myPercentageTarget = percentage;
    }

    public void setPercentage()
    {
        this.myPercentage++;
        if(myPercentage == myPercentageTarget)
        {
            percentageSet = true;
        }
        invalidate();
    }

    public void init()
    {
        myPaint = new Paint();
        myPaint.setColor(getContext().getResources().getColor(R.color.redColor));   //default color
        myPaint.setAntiAlias(true);
        myPaint.setStyle(Paint.Style.FILL);

        myBackgroundPaint = new Paint();
        myBackgroundPaint.setColor(getContext().getResources().getColor(R.color.pieBackground));
        myBackgroundPaint.setAntiAlias(true);
        myBackgroundPaint.setStyle(Paint.Style.FILL);

        myTextPaint = new Paint();
        myTextPaint.setColor(getContext().getResources().getColor(R.color.pieText));
        myTextPaint.setTextSize(40);
        myTextPaint.setAntiAlias(true);
        myTextPaint.setStyle(Paint.Style.FILL);

        myRect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        myRect.set(0, 0, getWidth(), getHeight());
        canvas.drawArc(myRect, -90, 360, true, myBackgroundPaint);
        canvas.drawArc(myRect, -90, 3.6f*myPercentage, true, myPaint);
        canvas.drawText(String.valueOf(myPercentage) + "%", getWidth()/2 - 30, getHeight()/2 + 10, myTextPaint);
        if(!percentageSet)
        {
            setPercentage();
        }
    }
}
