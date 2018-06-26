package com.example.prabhakarananbazhag.chart.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.prabhakarananbazhag.chart.model.BarChartData;
import com.example.prabhakarananbazhag.chart.model.LineChartData;
import com.example.prabhakarananbazhag.chart.model.ScatterChartData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class ScatterChartView extends View {
    Paint lines=new Paint();
    Paint plot = new Paint();
    Paint scale = new Paint();
    Paint coordinate_point = new Paint();
    Paint labels = new Paint();
    Paint paint=new Paint();

    public ScatterChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ScatterChartView(Context context) {
        super(context);

    }
    ScatterChartData cvalues;
    public  void setvalues(ScatterChartData cd) {
        cvalues =cd;
        postInvalidate();
    }
    @Override
    public void onDraw(Canvas canvas) {
        if (cvalues != null) {
            //.........................Canvas Attributes..................
            int length = canvas.getHeight();
            int breadth = canvas.getWidth();
            int len_dec = length / 10;
            int bre_dec = breadth / 10;
            int dec;
            if(len_dec>bre_dec)
            {
                dec=len_dec;
            }
            else if(bre_dec>len_dec)
            {
                dec=bre_dec;
            }
            else
            {
                dec=bre_dec;
            }
            Log.i("String dec", String.valueOf(dec));
            ///    int size = getResources().getDimensionPixelSize(R.dimen.myFontSize);
            //     axis.setTextSize(size);

            //..................Colours................
            ArrayList Colours=new ArrayList();

            for( ScatterChartData.Colours c:cvalues.getColours()) {
                Colours.add(c.getColor());
            }
           /* lines.setColor(Color.parseColor((String)Colours.get(0)));
            coordinate_point.setColor(Color.parseColor((String)Colours.get(1)));
            plot.setColor(Color.parseColor((String)Colours.get(2)));
            scale.setColor(Color.parseColor((String)Colours.get(3)));
            labels.setColor(Color.parseColor((String)Colours.get(4)));*/

            lines.setColor(Color.BLACK);
            coordinate_point.setColor(Color.GRAY);
            plot.setColor(Color.MAGENTA);
            scale.setColor(Color.RED);
            labels.setColor(Color.RED);



            //..................Labels................
            ArrayList Labels=new ArrayList();
            for( BarChartData.Labels l:cvalues.getLabel()) {
                Labels.add(l.getTitle());
            }
            labels.setTextAlign(Paint.Align.CENTER);
            int size=getWidth()/30;
            int size1=dec/4;
            labels.setTextSize(size);
            labels.setTextSize(size1);
            canvas.drawText((String) Labels.get(0), breadth/2, size1, labels);
            int size2=dec/6;
            labels.setTextSize(size1);
            canvas.drawText((String) Labels.get(1), breadth/2, size1*3, labels);
            Path path = new Path();
            path.moveTo(length-(2*size),length/2);
            path.lineTo(length-50,length/2-100);
            canvas.drawPath(path, labels);
            canvas.drawTextOnPath((String) Labels.get(2),path,0,0,labels);
            //...............Rectangle Creation..............
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            int length_dec=length/20;
            int breadth_dec=breadth/20;

            canvas.drawRect(dec, dec, breadth-dec, length-dec,paint);
            //.............Xarray and Yarray Creation................
            ArrayList Xaxis=new ArrayList();
            ArrayList Yaxis=new ArrayList();
            //............Fetching Values Using For Each...................//
            for( ScatterChartData.Xplot x:cvalues.getXaxisplot())
            {
                Xaxis.add(x.getXaxis_point());
            }

            for( ScatterChartData.Yplot y:cvalues.getYaxisplot())
            {
                Yaxis.add(y.getYaxis_points());
            }
            //.............XFormat Checking.............
            String xcheck= String.valueOf(Xaxis.get(0));
            float q=paint.measureText(String.valueOf(xcheck));
            Log.i("St", String.valueOf(q));
            int check=xFormat(xcheck);
            int xc=0;
            HashMap xplot=new HashMap();
            switch (check)
            {
                case 0:
                    xplot=xString(Xaxis,canvas,length,breadth,dec);
                    xc=1;
                    break;
                default:
                    Log.i("scale","Integer");
                    xplot=xNumber(Xaxis,canvas,length,breadth,dec);
                    xc=2;
                    break;
            }
            //.............YFormat Checking.............
            String ycheck= String.valueOf(Yaxis.get(0));
            int checky=yFormat(ycheck);
            int yc=0;
            HashMap yplot=new HashMap();
            switch (checky)
            {
                case 0:
                    yplot=yString(Yaxis,canvas,length,breadth,dec);
                    yc=1;
                    break;
                default:
                    Log.i("scale","y...Integer");
                    yplot= yNumber(Yaxis,canvas,length,breadth,dec);
                    yc=2;
                    break;
            }
            //.........PLOTTING..............
            plot(Xaxis,Yaxis,xplot,yplot,canvas,xc,yc);
        } else {
            return;
        }
    }
    private void plot(ArrayList xaxis, ArrayList yaxis,  HashMap xplot, HashMap yplot, Canvas canvas, int xc, int yc) {
        int s=xaxis.size();

        if((xc==1)&&(yc==1)) { //X and Y String
            for (int j = 0; j < s; j++) {
                String val1=(String) xaxis.get(j);
                String val2=(String) yaxis.get(j);
                Object xcc =  xplot.get(val1);
                Object ycc =  yplot.get(val2);

                canvas.drawCircle((int) xcc, (int) ycc, 5, plot);
            }

        }
        if((xc==1)&&(yc==2)) {  //X String Y Float
            for (int j = 0; j < s; j++) {
                String val2=(String) yaxis.get(j);
                float tc=Float.parseFloat(val2);
                //Check wheather the number is Integer or Float..........
                if((yplot.containsKey(tc))) {
                    float new_value=  Float.parseFloat((String)yaxis.get(j));
                    String val1= (String) xaxis.get(j);
                    Object ycc =  yplot.get(new_value);
                    Object xcc =  xplot.get(val1);

                    canvas.drawCircle((int) xcc, (int) ycc, 5, plot);
                }
                else {
                    float val;
                    //...............Float Logic.....
                    float xcc =Float.parseFloat((String) yaxis.get(j));
                    //...........Integer part separation........
                    int integer_part= (int) xcc;
                    //........Decimal part separation........
                    String dot=".";
                    int count=0;
                    for(int d=0;d<val2.length();d++) {
                        if(String.valueOf(val2.charAt(d)).equals(dot)) {
                            ++count;
                            break;
                        }
                    }
                    String decimal=val2.substring(count+1);
                    float decimal_part=Float.parseFloat((decimal));
                    //...........Distance between two Elements.................
                    int distance;
                    Object temp1=yplot.get((float)integer_part);
                    Object temp2;
                    int v=s-1;
                    if(j==v) {
                        temp2=yplot.get((float)integer_part-1);
                    }
                    else {
                        temp2=yplot.get((float)integer_part+1);
                    }
                    //distance=Integer.parseInt((String) temp2)-Integer.parseInt((String) temp1);
                    distance=(int)temp2-(int)temp1;
                    //.................Internal Distance Calculation..............
                    float internal_distance=(float)distance/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance*decimal_part) ;
                    val=(int)temp1+pixel_new;
                    String val1= (String) xaxis.get(j);
                    Object xcc_f =  xplot.get(val1);

                    canvas.drawCircle((int)xcc_f,(int) val, 5, plot);
                }
            }

        }
        if((xc==2)&&(yc==1)) {   //X Number....Y String....//
            for (int j = 0; j < s; j++) {
                //int val1=Integer.parseInt ((String) xaxis.get(j));;
                String val1=(String) xaxis.get(j);
                float tc=Float.parseFloat(val1);
                //Check wheather the number is Integer or Float..........
                if((xplot.containsKey(tc))){
                    float new_value=  Float.parseFloat((String)xaxis.get(j));
                    String val2= (String) yaxis.get(j);
                    Object xcc =  xplot.get(new_value);
                    Object ycc =  yplot.get(val2);

                    canvas.drawCircle((int) xcc, (int) ycc, 5, plot);
                }
                else {
                    float val;
                    //...............Float Logic.....
                    float xcc =Float.parseFloat((String) xaxis.get(j));
                    //...........Integer part separation........
                    int integer_part= (int) xcc;
                    //........Decimal part separation........
                    String dot=".";
                    int count=0;
                    for(int d=0;d<val1.length();d++){
                        if(String.valueOf(val1.charAt(d)).equals(dot)){
                            ++count;
                            break;
                        }
                    }
                    String decimal=val1.substring(count+1);
                    float decimal_part=Float.parseFloat((decimal));
                    //...........Distance between two Elements.................
                    int distance;
                    Object temp1=xplot.get((float)integer_part);
                    Object temp2;
                    int v=s-1;
                    if(j==v) {
                        temp2=xplot.get((float)integer_part-1);
                    }
                    else {
                        temp2=xplot.get((float)integer_part+1);
                    }
                    distance=(int)temp2-(int)temp1;
                    //.................Internal Distance Calculation..............
                    float internal_distance=(float)distance/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance*decimal_part) ;
                    val=(int)temp1+pixel_new;
                    String val2= (String) yaxis.get(j);
                    Object ycc_f =  yplot.get(val2);

                    canvas.drawCircle((int)val,(int) ycc_f, 5, plot);
                }
            }

        }
        if((xc==2)&&(yc==2)) {    //X And Y Number
            Object xcc_f,ycc_f;
            for (int j = 0; j < s; j++) {
                //....................Corresponding X Range....................//
                String val2_x=(String) xaxis.get(j);
                float tc_x=Float.parseFloat(val2_x);
                //Check wheather the number is Integer or Float..........
                if((xplot.containsKey(tc_x))) {
                    Log.i("Enter xi","Enterinhg x int ");
                    float new_value_x=  Float.parseFloat((String)xaxis.get(j));
                    String val1_x= (String) xaxis.get(j);
                    float temp_val1_x=Float.parseFloat(val1_x);
                    xcc_f = (int) xplot.get(temp_val1_x);
                    Log.i("msg","1 x");
                }
                else {
                    Log.i("Enter xf","Entering x float");
                    float val_x;
                    //...............Float Logic.....
                    float xcc_x =Float.parseFloat((String) xaxis.get(j));
                    //...........Integer part separation........
                    int integer_part_x= (int) xcc_x;
                    //........Decimal part separation........
                    String dot_x=".";
                    int count_x=0;
                    for(int d=0;d<val2_x.length();d++) {
                        if(String.valueOf(val2_x.charAt(d)).equals(dot_x)) {
                            break;
                        }
                        ++count_x;
                    }
                    String decimal_x=val2_x.substring(count_x+1);
                    float decimal_part_x=Float.parseFloat((decimal_x));
                    //...........Distance between two Elements.................
                    int distance_x;
                    Object temp1=xplot.get((float)integer_part_x);
                    Log.i("S", String.valueOf(temp1));
                    Object temp2;
                    int v=s-1;
                    if(j==v) {
                        temp2=xplot.get((float)integer_part_x-1);
                    }
                    else {
                        temp2=xplot.get((float)integer_part_x+1);
                    }
                    distance_x=(int)temp2-(int)temp1;
                    //.................Internal Distance Calculation..............
                    float internal_distance_x=(float)distance_x/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance_x*decimal_part_x) ;
                    val_x=(int)temp1+pixel_new;
                    xcc_f = (int)val_x;
                    Log.i("msg","Exit X float");

                }
                //....................Corresponding Y Range....................//
                String val2_y=(String) yaxis.get(j);
                float tc_y=Float.parseFloat(val2_y);
                //Check wheather the number is Integer or Float..........
                if((yplot.containsKey(tc_y))) {
                    Log.i("Enter yi","Enterinhg y int ");
                    float new_value_y=  Float.parseFloat((String)yaxis.get(j));
                    String val1_y= (String) yaxis.get(j);
                    float temp_val1_y=Float.parseFloat(val1_y);
                    ycc_f = (int) yplot.get(temp_val1_y);
                    Log.i("y ", String.valueOf((int)ycc_f));
                    Log.i("msg","123");
                }
                else {
                    Log.i("Enter yf ","Enterinhg y float ");
                    float val_y;
                    //...............Float Logic.....
                    float xcc_y =Float.parseFloat((String) yaxis.get(j));
                    //...........Integer part separation........
                    int integer_part= (int) xcc_y;
                    //........Decimal part separation........
                    String dot_y=".";
                    int count_y=0;
                    for(int d=0;d<val2_y.length();d++) {
                        if(String.valueOf(val2_y.charAt(d)).equals(dot_y)) {
                            ++count_y;
                            break;
                        }
                    }
                    String decimal_y=val2_y.substring(count_y+1);
                    float decimal_part_y=Float.parseFloat((decimal_y));
                    //...........Distance between two Elements.................
                    int distance_y;
                    Object temp1_y=yplot.get((float)integer_part);
                    Object temp2_y;
                    int v_y=s-1;
                    if(j==v_y) {
                        temp2_y=yplot.get((float)integer_part-1);
                    }
                    else {
                        temp2_y=yplot.get((float)integer_part+1);
                    }
                    distance_y=(int)temp2_y-(int)temp1_y;
                    //.................Internal Distance Calculation..............
                    float internal_distance_y=(float)distance_y/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance_y*decimal_part_y) ;
                    val_y=(int)temp1_y+pixel_new;
                    ycc_f = (int)val_y;
                }

                canvas.drawCircle((int) xcc_f, (int)ycc_f, 5, plot);
            }
        }

    }
    private HashMap yNumber(ArrayList Yaxis, Canvas canvas, int length, int breadth,int dec) {
        //................Yarray Creation.........
        int yaxis[]=new int[Yaxis.size()];
        for(int i=0;i<Yaxis.size();i++) {
            float temp=Float.parseFloat((String) Yaxis.get(i));
            yaxis[i]= (int)(temp) ;
        }
        //.................TempArray Generation..........
        //.....................Y.................
        int temp_yaxis[] =new  int[Yaxis.size()];
        System.arraycopy(yaxis, 0,temp_yaxis, 0, Yaxis.size());
        Arrays.sort(temp_yaxis);
        int ymin = temp_yaxis[0];
        int ymax = temp_yaxis[Yaxis.size() - 1];
        //...............yscale Generation..................
        int yinc=(ymax-ymin)/Yaxis.size();
        ArrayList yscale=new ArrayList();
        int temp=ymin;
        if(yinc!=0)
        {
            for(;;) {
                if(temp<=ymax) {
                    yscale.add(temp);
                    temp=temp+yinc;
                }
                else {
                    yscale.add(temp);
                    break;
                }
            }
        }
        else
        {
            yinc=1;
            for(;;) {
                if(temp<=ymax) {
                    yscale.add(temp);
                    temp=temp+yinc;
                }
                else {
                    yscale.add(temp);
                    break;
                }
            }
        }

        Log.i("txt", String.valueOf(Yaxis));
        Log.i("yyscale", String.valueOf(yscale));
        int yscale_count=yscale.size();
        //...........Horizontal Lines.................
        int hxs = dec, hxst =breadth-dec, hys =length-dec, hyst =length-dec;
        int ysplit=((length-dec)-dec)/yscale_count;
        for (int i = 0; i <yscale_count; i++) {
            canvas.drawLine(hxs, hys, hxst, hyst,lines);
            hys = hys -ysplit;
            hyst = hyst - ysplit;
        }
        //.............ypoint fixing and Yscale Printing................
        int xstart=dec;  int ystart=length-dec-ysplit;
        HashMap ypixel = new HashMap();
        for(int i=0;i<yscale_count;i++) {
            int count;
            canvas.drawCircle(xstart, ystart, 5, coordinate_point);
            //...............Resizing the txt...............//
            scale.setTextSize(130);
            while(scale.measureText(String.valueOf(yscale.get(i)))>dec){
                scale.setTextSize(scale.getTextSize()-1);
            }
            int Resize= (int) (scale.getTextSize()/2);
            scale.setTextSize(Resize);
            canvas.drawText(String.valueOf((int) yscale.get(i)), xstart-2*(scale.getTextSize()), ystart+5,scale);
            //..................................................//
            ypixel.put(Float.parseFloat(String.valueOf(yscale.get(i))), ystart);
            int plot=(Integer) yscale.get(i);
            int temp_inc=ysplit/(yinc);
            Log.i("this", String.valueOf(temp_inc));
            Log.i("s", String.valueOf(ysplit));
            if(temp_inc!=0) {
                for (count = 1; count <= yinc; count++) {
                    ++plot;
                    ypixel.put((float) plot, ystart - (temp_inc * count));
                }
                ystart-=ysplit;
            }
            else
            {
                temp_inc=1;
                for (count = 1; count <= yinc; count++) {
                    ++plot;
                    ypixel.put((float) plot, ystart - (temp_inc * count));
                }
                ystart-=ysplit;
            }
        }
        Log.i("Xsize", String.valueOf(ypixel));
        return ypixel;
    }
    private HashMap yString(ArrayList Yaxis, Canvas canvas, int length, int breadth,int dec) {
        //...........Horizontal Lines.................
        int hxs = dec, hxst =breadth-dec, hys =length-dec, hyst =length-dec;
        int ysplit=((length-dec)-dec)/Yaxis.size();
        for (int i = 0; i <Yaxis.size(); i++) {
            canvas.drawLine(hxs, hys, hxst, hyst,lines);
            hys = hys -ysplit;
            hyst = hyst - ysplit;
        }
        //.............Ypoint fixing and Yscale Printing...............
        int xstart=dec;  int ystart=length-dec-ysplit;
        HashMap ypixel = new HashMap();
        for(int i=0;i<Yaxis.size();i++) {
            canvas.drawCircle(xstart, ystart, 5, coordinate_point);
            Log.i("Y", String.valueOf((Object) Yaxis.get(i)));
            //...............Resizing the txt...............//
            scale.setTextSize(130);
            while(scale.measureText(String.valueOf(Yaxis.get(i)))>dec){
                scale.setTextSize(scale.getTextSize()-1);
            }
            int Resize= (int) (scale.getTextSize()/2);
            scale.setTextSize(Resize);
            canvas.drawText(String.valueOf((int) Yaxis.get(i)), xstart-2*(scale.getTextSize()), ystart+5,scale);
            //............................................................//
            ypixel.put(Yaxis.get(i), ystart);
            ystart=ystart-ysplit;
        }
        return ypixel;
    }
    private HashMap xString(ArrayList Xaxis,Canvas canvas,int length,int breadth,int dec) {
        //...........Vertical Lines...............
        int vxs = dec, vxst =dec, vys = dec, vyst = length-dec;
        int xsplit=((breadth-dec)-dec)/Xaxis.size();
        for (int i = 0; i <Xaxis.size(); i++) {
            canvas.drawLine(vxs, vys, vxst, vyst, lines);
            vxs = vxs + xsplit;
            vxst = vxst + xsplit;
        }
        //.............Xpoint fixing and Xscale Printing...............
        int xstart=dec+xsplit;int ystart=length-dec;
        HashMap xpixel = new HashMap();
        for(int i=0;i<Xaxis.size();i++) {
            canvas.drawCircle(xstart, ystart, 5, coordinate_point);
            //...............Resizing the txt...............//
            scale.setTextSize(130);
            while(scale.measureText(String.valueOf(Xaxis.get(i)))>xsplit){
                scale.setTextSize(scale.getTextSize()-1);
            }
            int Resize= (int) (scale.getTextSize()/2);
            scale.setTextSize(Resize);
            canvas.drawText(String.valueOf((int) Xaxis.get(i)), xstart-(scale.getTextSize()), ystart+(dec/3),scale);
            //..............................................//
            xpixel.put( Xaxis.get(i), xstart);
            xstart+=xsplit;
        }
        return  xpixel;
    }
    private HashMap xNumber(ArrayList Xaxis,Canvas canvas,int length,int breadth,int dec) {
        //.............Xarray Creation................
        int xaxisvalues[]=new int[Xaxis.size()];
        for(int i=0;i<Xaxis.size();i++) {
            float temp=Float.parseFloat((String) Xaxis.get(i));
            xaxisvalues[i]= (int)(temp) ;
        }
        //.................TempArray Generation..........
        //..............X..............
        int temp_xaxis[] =new  int[Xaxis.size()];
        System.arraycopy(xaxisvalues, 0,temp_xaxis, 0, Xaxis.size());
        Arrays.sort(temp_xaxis);
        int xmin = temp_xaxis[0];
        int xmax = temp_xaxis[Xaxis.size() - 1];
        //................Xscale Generation..................
        int xinc= (int) ((xmax-xmin)/Xaxis.size());
        ArrayList xscale=new ArrayList();
        int temp=(int)xmin;
        if(xinc!=0)
        {
            for(;;) {
                if(temp<=xmax) {
                    xscale.add(temp);
                    temp=temp+xinc;
                }
                else {
                    xscale.add(temp);
                    break;
                }
            }
        }
        else
        {
            xinc=1;
            for(;;) {
                if(temp<=xmax) {
                    xscale.add(temp);
                    temp=temp+xinc;
                }
                else {
                    xscale.add(temp);
                    break;
                }
            }
        }

        Log.i("scale", String.valueOf(xscale));
        int xscale_count=xscale.size();
        //...........Vertical Lines...............
        int vxs = dec, vxst =dec, vys = dec, vyst = length-dec;
        int xsplit=((breadth-dec)-dec)/xscale_count;
        Log.i("scale", String.valueOf(xsplit));
        for (int i = 0; i <xscale_count; i++) {
            canvas.drawLine(vxs, vys, vxst, vyst, lines);
            vxs = vxs + xsplit;
            vxst = vxst + xsplit;
        }
        //...........xpoint fixing and Xscale Printing...................
        int xstart=dec+xsplit;int ystart=length-dec;
        HashMap xpixel = new HashMap();
        int temp_inc=(int) (xsplit)/(xinc);
        Log.i("tempinc", String.valueOf(temp_inc));
        for(int i=0;i<xscale_count;i++) {
            int count;
            canvas.drawCircle(xstart, ystart, 5, coordinate_point);

            //...............Resizing the txt...............//
            scale.setTextSize(130);
            while(scale.measureText(String.valueOf(xscale.get(i)))>xsplit){
                scale.setTextSize(scale.getTextSize()-1);
            }
            int Resize= (int) (scale.getTextSize()/2);
            scale.setTextSize(Resize);
            canvas.drawText(String.valueOf((int) xscale.get(i)), xstart-(scale.getTextSize()), ystart+(dec/3),scale);
            //.....................................................//
            xpixel.put(Float.parseFloat(String.valueOf(xscale.get(i))), xstart);
            int plot=( int) xscale.get(i);
            if(temp_inc!=0) {
                for (count = 1; count <= xinc; count++) {
                    ++plot;
                    xpixel.put((float) plot, xstart + (temp_inc * count));
                }
                xstart += xsplit;
            }
            else
            {
                temp_inc=0;
                for (count = 1; count <= xinc; count++) {
                    ++plot;
                    xpixel.put((float) plot, xstart + (temp_inc * count));
                }
                xstart += xsplit;

            }
        }
        return  xpixel;
    }
    //........................Function To check X Range Format.......................
    public int xFormat(String xval) {
        int count=0;
        for(int i = 0; i < xval.length(); i++) {
            if((Character.isDigit(xval.charAt(i)))||(Boolean)((String.valueOf(xval.charAt(i))).equals("."))) {
                ++count;
            }
            else {
                count=0;
                break;
            }
        }
        return count;
    }
    //........................Function To check y Range Format.......................
    public int yFormat(String yval) {
        int count=0;
        for(int i = 0; i < yval.length(); i++) {
            if((Character.isDigit(yval.charAt(i)))||(Boolean)((String.valueOf(yval.charAt(i))).equals("."))) {
                ++count;
            }
            else {
                count=0;
                break;
            }
        }
        return count;
    }

}
