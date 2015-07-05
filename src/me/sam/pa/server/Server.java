package me.sam.pa.server;

public class Server implements Runnable {
    private static final long serialVersionUID = 1L;

    private Thread thread;
    private boolean running = false;

    public Server() {}

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "PA_Server");
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
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

    public void update() {
    }

    public static void main(String[] args) {
        Server pa = new Server();
        pa.start();
    }
}