package com.mycompany.sample.AppManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@JsonTypeName("Computer")
public class Computer {
    private String namePC;
    private String macAddr;
    private String ip;

    private static final Dotenv dotenv = Dotenv.load();
    private static final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");

    public Computer(String namePC, String macAddr, String ip) {
        this.namePC = namePC;
        this.macAddr = macAddr;
        this.ip = ip;
    }

    public Computer() {
        // empty constructor
    }

    private void sendCommand(String command) {
        try {
            String targetUrl = "http://" + ip + ":8080/command";
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);

            conn.setDoOutput(true);

            StringBuilder jsonPayload = new StringBuilder();
            jsonPayload.append("{\"command\":\"").append(command).append("\"");

            if ("turnOn".equalsIgnoreCase(command)) {
                jsonPayload.append(",\"mac\":\"").append(macAddr).append("\"");
            }

            jsonPayload.append("}");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.toString().getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Command '" + command + "' sent to " + namePC + " - Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOn() {
        sendCommand("turnOn");
    }

    public void reboot() {
        sendCommand("reboot");
    }

    public void turnOff() {
        sendCommand("turnOff");
    }

    public void lock() {
        sendCommand("lock");
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
