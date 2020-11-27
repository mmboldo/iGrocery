package com.mycompany.igrocery;

public class Events {
    String EVENT, TIME, DATE;

    public Events(String EVENT, String TIME, String DATE) {
        this.EVENT = EVENT;
        this.TIME = TIME;
        this.DATE = DATE;
    }
    public Events() {
    }

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

}
