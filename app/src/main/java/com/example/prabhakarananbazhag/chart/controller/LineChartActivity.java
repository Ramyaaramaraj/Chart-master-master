package com.example.prabhakarananbazhag.chart.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.prabhakarananbazhag.chart.R;
import com.example.prabhakarananbazhag.chart.model.LineChartData;
import com.example.prabhakarananbazhag.chart.view.LineChartView;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class LineChartActivity extends AppCompatActivity{
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart_activity);
        LineChartView dl = (LineChartView) findViewById(R.id.line);
        try {
            dl.setvalues(getjson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public LineChartData getjson() throws JSONException {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("Linedetails.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson=new Gson();
        LineChartData line = gson.fromJson(json,LineChartData.class);
        return  line;
    }
}


