package com.mycompany.sample.AppManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
//import io.github.cdimascio.dotenv.Dotenv;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@JsonTypeName("Computer")
public class Computer {
    private String namePC;
    private String macAddr;
    private String ip;
    
    private final WakeOnLan wakeOnLan = new WakeOnLan();
    private static final String AUTH_TOKEN = "";

    //private static final Dotenv dotenv = Dotenv.configure().filename(".env").load();

    public Computer(String namePC, String macAddr, String ip) {
        this.namePC = namePC;
        this.macAddr = macAddr;
        this.ip = ip;
    }

    public Computer() {
        // empty constructor
    }

    private int sendCommand(String command) {
        try {
            String targetUrl = "http://" + ip + ":8080/command";
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000); // 3 seconds
            conn.setReadTimeout(3000);    // 3 seconds

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
            conn.setDoOutput(true);

            StringBuilder jsonPayload = new StringBuilder();
            jsonPayload.append("{\"command\":\"").append(command).append("\"}");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.toString().getBytes());
                os.flush();
            }

            return conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return 500; 
        }
    }

    public int ping() {
        try {
            String targetUrl = "http://" + ip + ":8080/ping";
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000); 
            conn.setReadTimeout(2000);   
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
            return conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int turnOn() {
        return wakeOnLan.sendWakeOnLan(macAddr);
    }

    public int reboot() {
        return sendCommand("reboot");
    }

    public int turnOff() {
        return sendCommand("turnOff");
    }

    public int lock() {
        return sendCommand("lock");
    }
    
    @JsonProperty("namepc")
    public String getNamePC() {
        return namePC;
    }

    public void setNamePC(String namePC) {
        this.namePC = namePC;
    }

    @JsonProperty("macadrr")
    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Computer{" + "MACAdrr='" + macAddr + '\'' + ", Ip='" + ip + '\'' +'}';
    }
}
