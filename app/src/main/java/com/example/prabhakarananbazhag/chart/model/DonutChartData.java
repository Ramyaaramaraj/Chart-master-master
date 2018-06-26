package com.example.prabhakarananbazhag.chart.model;

import java.io.Serializable;
import java.util.List;

public class DonutChartData  implements Serializable {
    int width;
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    List<Fields> field;

    public List<Fields> getField() {
        return field;
    }

    public void setField(List<Fields> field) {
        this.field = field;
    }

    public class Fields {
        public String field_name;
        public Float field_value;
        public String field_color;

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public Float getField_value() {
            return field_value;
        }

        public void setField_value(Float field_value) {
            this.field_value = field_value;
        }

        public String getField_color() {
            return field_color;
        }

        public void setField_color(String field_color) {
            this.field_color = field_color;
        }
    }
}