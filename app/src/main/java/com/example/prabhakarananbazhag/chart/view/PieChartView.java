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

import com.example.prabhakarananbazhag.chart.model.PieChartData;

import java.util.concurrent.TimeUnit;

public class PieChartView extends View{
    Paint paint,paint1;
    PieChartData pieChartData;
    private ValueAnimator mTimerAnimator;
    int no_of_iteration=0;

    public PieChartView(Context context){
        super(context);
    }
    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint1=new Paint();
        paint.setColor(Color.BLACK);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float start=0f;
        if(pieChartData!=null){
            float[] sweep=scale();
            int z=canvas.getWidth()/20;

            int w=canvas.getWidth()/30;
            int x=canvas.getWidth()-z;
            int y= canvas.getHeight()-z;
            RectF rectF = new RectF(z,z,x,y);
            RectF rectF1=new RectF(w,w,x+w,y+w);
            for(int i=0;i<pieChartData.getTeam().size();++i){
                paint.setColor(Color.parseColor(String.valueOf(pieChartData.getTeam().get(i).getColour())));
                Path p2=new Path();
                canvas.drawArc(rectF, start, sweep[i], true, paint);
                p2.addArc(rectF,start,sweep[i]);
                Path p = new Path();
                p.addArc(rectF1, start, sweep[i]);
                PathMeasure pathMeasure = new PathMeasure(p2, false);
                float pathLength = pathMeasure.getLength();
                paint.setColor(Color.BLACK);
                paint.setTextSize(getWidth() / 25);
                paint.setTextAlign(Paint.Align.CENTER);
                String name = String.valueOf(pieChartData.getTeam().get(i).getName());
                if (paint.measureText(name) > pathLength) {
                    while (paint.measureText(name) < pathLength) {
                        name = name.substring(0, name.length() - 1);
                    }
                    canvas.drawTextOnPath(name.substring(0, name.length() - 3).concat("..."), p, 0, 0, paint);
                } else {
                    canvas.drawTextOnPath(String.valueOf(pieChartData.getTeam().get(i).getName()), p, 0, 0, paint);
                }
                start += sweep[i];
            }

        }else{
            return;
        }
    }
    private float[] scale() {
        float[] sweep=new float[pieChartData.getTeam().size()];
        float total = getTotal();
        for(int i=0;i<pieChartData.getTeam().size();i++){
            sweep[i] = (Float.parseFloat(String.valueOf(pieChartData.getTeam().get(i).getMatches())) /total) * 360;
        }
        return sweep;
    }
    private float getTotal(){
        float total = 0;
        for(int i=0;i<pieChartData.getTeam().size();i++) {
            float val= Float.parseFloat(String.valueOf(pieChartData.getTeam().get(i).getMatches()));
            total += val;
        }
        return total;
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
    public void setdata(PieChartData chartdata){
        pieChartData=chartdata;
        postInvalidate();
    }
}
