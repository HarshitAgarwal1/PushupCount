package com.example.pushupcounter.Adapters;

public class Entry {
    private String slno , pushcount;
    private Float chronometercount ;
    private Integer  counter ;

    public String getSlno() {
        return slno;
    }



    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getPushcount() {
        return pushcount;
    }

    public void setPushcount(String pushcount) {
        this.pushcount = pushcount;
    }

    public Float getChronometercount() {
        return chronometercount;
    }

    public void setChronometercount(Float chronometercount) {
        this.chronometercount = chronometercount;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Entry(String slno, String pushcount, Float chronometercount, Integer counter) {
        this.slno = slno;
        this.pushcount = pushcount;
        this.chronometercount = chronometercount;
        this.counter = counter;
    }
}