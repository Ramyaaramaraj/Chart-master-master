package com.example.prabhakarananbazhag.chart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.prabhakarananbazhag.chart.model.DonutChartData;

import java.util.concurrent.TimeUnit;

public class DonutChartView extends View {
    Paint paint,textPaint,shadowPaint;
    RectF rect;
    int midx,midy,radius;
    float currentStartArcPosition=0f;
    float currentSweep;
    int no_of_iteration=0;
    float totalValues=0;
    int shadowWidth=0;
    DonutChartData data;
    private ValueAnimator mTimerAnimator;

    public DonutChartView(Context context) {
        super(context);
    }

    public DonutChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DonutChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void init(@Nullable AttributeSet attrs){
        rect=new RectF();
        paint=new Paint(paint.ANTI_ALIAS_FLAG);
        textPaint=new Paint(textPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        shadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimerAnimator=new ValueAnimator();
        shadowWidth=50;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (data == null) {
            return;
        } else {
            canvas.rotate(-90, canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.drawColor(Color.TRANSPARENT);
            midx = getWidth() / 2;
            midy = getHeight() / 2;
            if (midx < midy) radius = midx;
            else radius = midy;
            radius = radius - shadowWidth;
            paint.setStrokeWidth( data.getWidth());
            paint.setStyle(Paint.Style.STROKE);
            currentStartArcPosition=0f;
            int sweepCount=0;

            for (int i = 0; i < data.getField().size(); i++) {
                paint.setColor(Color.parseColor(String.valueOf(data.getField().get(i).getField_color())));
                currentSweep = (data.getField().get(i).getField_value() / totalValues) * 360;
                float currentArc = currentStartArcPosition;
                rect.set(midx - radius +(data.getWidth()/2f), midy - radius+(data.getWidth()/2f) , midx + radius-(data.getWidth()/2f), midy + radius-(data.getWidth()/2f));
                for (float sweep = 1; sweep <= currentSweep; sweep++) {
                    if(sweepCount>=no_of_iteration){
                        break;
                    }else{

                        canvas.drawArc(rect, currentArc, 2, false, paint);
                        currentArc++;
                    }
                    sweepCount++;
                }

                if(currentStartArcPosition+currentSweep<=no_of_iteration){

                    Path p1=new Path();
                    rect.set(midx-radius+(data.getWidth()/1.5f),
                            midy-radius+(data.getWidth()/1.5f),midx+radius-(data.getWidth()/1.5f),midy+radius-(data.getWidth()/1.5f));

                    drawArcLocal(p1,currentSweep,currentStartArcPosition,currentSweep);
                    PathMeasure pm=new PathMeasure(p1,false);

                    textPaint.setColor(Color.BLACK);
                    textSizeCalcualtor(data.getField().get(i).getField_name(),pm,data.getWidth()/4);
                    canvas.drawTextOnPath(data.getField().get(i).getField_name(),p1,(int)(pm.getLength()-textPaint.measureText(data.getField().get(i).getField_name()))/2,-data.getWidth()/5,textPaint);

                    textPaint.setColor(Color.RED);
                    textSizeCalcualtor(String.valueOf(data.getField().get(i).getField_value()),pm,data.getWidth()/4);
                    canvas.drawTextOnPath(String.valueOf(data.getField().get(i).getField_value()),p1,(int)(pm.getLength()-textPaint.measureText(String.valueOf(data.getField().get(i).getField_value())))/2,data.getWidth()/4,textPaint);
                    p1.close();
                }
                currentStartArcPosition=currentStartArcPosition+currentSweep;

            }
        }
    }

    public void textSizeCalcualtor(String field,PathMeasure pm,int size){
        textPaint.setTextSize(size);
        while(pm.getLength()<textPaint.measureText(field)){
            textPaint.setTextSize(textPaint.getTextSize()-2);
        }
    }


    public void drawArcLocal(Path path,float currentSweep1,float startAngle,float sweepAngle){
        if(currentSweep1==360){
            path.addArc(rect,startAngle,sweepAngle);
        }else{
            path.arcTo(rect,startAngle,sweepAngle);
        }
    }

    public void start(int secs) {
        mTimerAnimator.setIntValues(0,360);
        mTimerAnimator.setDuration(TimeUnit.SECONDS.toMillis(secs));
        mTimerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                no_of_iteration=(int)animation.getAnimatedValue();
                invalidate();
            }});
        mTimerAnimator.start();
    }

    public void setDonutGraphValues( DonutChartData donutGraphData){
        data=donutGraphData;
        postInvalidate();
        totalValues=0;
        for(int i=0;i<data.getField().size();i++){
            totalValues+=data.getField().get(i).getField_value();
        }
        init(null);
    }
}