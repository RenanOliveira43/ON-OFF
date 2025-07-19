package com.mycompany.sample.AppManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Computer")
public class Computer {
    private String namePC;
    private String MACAdrr;
    private String Ip;

    public Computer() {
        // empty constructor
    }

    public Computer(String namePC, String macString, String ip) {
        this.namePC = namePC;
        this.MACAdrr = macString;
        this.Ip = ip;
    }

    public void turnOn() {

    }

    public void turnOff() {

    }

    public void lock() {

    }

    @JsonProperty("namepc")
    public String getNamePC() {
        return namePC;
    }

    public void setNamePC(String namePC) {
        this.namePC = namePC;
    }

    @JsonProperty("macadrr")
    public String getMACAdrr() {
        return MACAdrr;
    }

    public void setMACAdrr(String MACAdrr) {
        this.MACAdrr = MACAdrr;
    }

    @JsonProperty("ip")
    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        this.Ip = ip;
    }

    @Override
    public String toString() {
        return "Computer{" + "MACAdrr='" + MACAdrr + '\'' + ", Ip='" + Ip + '\'' +'}';
    }
}
