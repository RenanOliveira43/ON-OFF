package com.mycompany.sample;

public class Computer {
    private String MACAdrr;
    private String Ip;

    public Computer(String macString, String ip) {
        this.MACAdrr = macString;
        this.Ip = ip;
    }

    public String getMACAdrr() {
        return MACAdrr;
    }

    public void setMACAdrr(String MACAdrr) {
        this.MACAdrr = MACAdrr;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        this.Ip = ip;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "MACAdrr='" + MACAdrr + '\'' +
                ", Ip='" + Ip + '\'' +
                '}';
    }

}
