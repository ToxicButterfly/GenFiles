package com.company.model;

import java.math.BigDecimal;

public class Line {
    private String date;
    private String eng;
    private String rus;
    private String integ;
    private BigDecimal doubl;

    public Line(String date, String eng, String rus, String integ, BigDecimal doubl) {
        this.date = date;
        this.eng = eng;
        this.rus = rus;
        this.integ = integ;
        this.doubl = doubl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getRus() {
        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public String getInteg() {
        return integ;
    }

    public void setInteg(String integ) {
        this.integ = integ;
    }

    public BigDecimal getDoubl() {
        return doubl;
    }

    public void setDoubl(BigDecimal doubl) {
        this.doubl = doubl;
    }

    @Override
    public String toString() {
        return "Line{" +
                "date='" + date + '\'' +
                ", eng='" + eng + '\'' +
                ", rus='" + rus + '\'' +
                ", integ='" + integ + '\'' +
                ", doubl='" + doubl + '\'' +
                '}';
    }
}

