package me.sam.pa.server.packet;

import me.sam.pa.server.PAClient;
import me.sam.pa.server.PAServer;

public class Packet01Disconnect extends Packet {

    private String username;

    public Packet01Disconnect(byte[] data) {
        super(01);
        this.username = readData(data);
    }

    public Packet01Disconnect(String username) {
        super(01);
        this.username = username;
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
        return (this.packetId + this.username).getBytes();
    }

    public String getUsername() {
        return username;
    }

}