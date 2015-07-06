package me.sam.pa.server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {
    private static final long serialVersionUID = 1L;

    private Thread thread;
    private boolean running = false;
    private DatagramSocket socket;
    private int port;

    public Server(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        running = true;
        System.out.println("Server started on port " + port);
        thread = new Thread(this, "PA_Server");
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
            socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ups");
                updates = 0;
            }
        }
        stop();
    }

    public void update() {}

    public static void main(String[] args) {
           int port;
        if (args.length != 1) {
            port = 1337;
        } else {
            port = Integer.parseInt(args[0]);
        }
        Server s = new Server(port);
        s.start();
    }
}