package com.example.prabhakarananbazhag.chart.model;

import java.io.Serializable;
import java.util.List;

public class PieChartData  implements ChartData, Serializable{

    private List<Team> team;



    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }



    public  class Team {
        public Float matches;
        public String name;
        public String  colour;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float getMatches() {
            return matches;
        }

        public void setMatches(Float matches) {
            this.matches = matches;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }


    }


}