package com.mycompany.sample.AppManager;

public class WakeOnLan {
    public int sendWakeOnLan(String macAddress) {
        try {
            byte[] macBytes = getMacBytes(macAddress);
            byte[] packet = new byte[6 + 16 * macBytes.length];
            // 6 bytes of 0xFF
            for (int i = 0; i < 6; i++) {
                packet[i] = (byte) 0xFF;
            }
            // 16 repetitions of the MAC
            for (int i = 6; i < packet.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, packet, i, macBytes.length);
            }

            java.net.InetAddress address = java.net.InetAddress.getByName("255.255.255.255"); 
            java.net.DatagramPacket datagramPacket = new java.net.DatagramPacket(packet, packet.length, address, 9); 
            java.net.DatagramSocket socket = new java.net.DatagramSocket();
            socket.setBroadcast(true);
            socket.send(datagramPacket);
            socket.close();

            System.out.println("Wake-on-LAN magic packet sent to " + macAddress);

            return 200; 

        } catch (Exception e) {
            e.printStackTrace();
            
            return 500;
        }
    }

    private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        String[] hex = macStr.split("([:\\-])");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address: " + macStr);
        }
        byte[] bytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }
        return bytes;
    }
}
