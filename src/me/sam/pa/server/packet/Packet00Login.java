package me.sam.pa.server.packet;

import me.sam.pa.server.PAClient;
import me.sam.pa.server.PAServer;

public class Packet00Login extends Packet {

    private String username;
    private int x, y, z;

    public Packet00Login(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.z = Integer.parseInt(dataArray[3]);
    }

    public Packet00Login(String username, int x, int y, int z) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void writeData(PAClient client) {
        client.sendData(getData()); // sendData will send the Packet's data to the local player. (You)
    }

    @Override
    public void writeData(PAServer server) {
        server.broadcastData(getData()); // broadcastData will send the Packet's data to all online Clients.
    }

    @Override
    public byte[] getData() {
        return ("00" + this.username + "," + getX() + "," + getY() + "," + getZ()).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}