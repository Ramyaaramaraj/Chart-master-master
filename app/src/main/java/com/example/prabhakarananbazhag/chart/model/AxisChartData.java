package com.example.prabhakarananbazhag.chart.model;

import java.util.ArrayList;

public class AxisChartData implements ChartData {
    private ArrayList<Xplot> xaxisplot;
    private ArrayList<Yplot> yaxisplot;
    private ArrayList<Labels> label;
    private ArrayList<Colours> colours;


    public ArrayList<Xplot> getXaxisplot() {
        return xaxisplot;
    }
    public void setXaxisplot(ArrayList<Xplot> xaxisplot) {
        this.xaxisplot = xaxisplot;
    }
    public ArrayList<Yplot> getYaxisplot() {
        return yaxisplot;
    }
    public void setYaxisplot(ArrayList<Yplot> yaxisplot) {
        this.yaxisplot = yaxisplot;
    }
    public ArrayList<Labels> getLabel() {
        return label;

    }
    public void setLabel(ArrayList<Labels> label) {
        this.label = label;
    }
    public ArrayList<Colours> getColours() {
        return colours;
    }
    public void setColours(ArrayList<Colours> colours) {
        this.colours = colours;
    }
    public static class Xplot {
        private String xaxis_point;
        public String getXaxis_point() {
            return xaxis_point;
        }
        public void setXaxis_point(String xaxis_point) {
            this.xaxis_point = xaxis_point;
        }
    }
    public static  class Yplot {
        private String yaxis_points;
        public String getYaxis_points() {
            return yaxis_points;
        }
        public void setYaxis_points(String yaxis_points) {
            this.yaxis_points = yaxis_points;
        }

    }
    public static class Labels {
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        private String title;
    }
    public static  class Colours {
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }
        private  String color;

    }
}
