package me.sam.pa.client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Client extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static String title = "Project Alicization";

    private static DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    private static int width = dm.getWidth();
    private static int height = dm.getHeight();

    private JFrame frame;

    private Thread thread;
    private boolean running = false;

    public Client() {
        Dimension size = new Dimension(getWindowWidth(), getWindowHeight());
        setPreferredSize(size);
        frame = new JFrame();
    }

    public static int getWindowWidth() {
        return width;
    }

    public static int getWindowHeight() {
        return height;
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "PA_Client");
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
        int frames = 0;
        int updates = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    public void update() {}

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(0x47AFFF));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Client pa = new Client();
        pa.frame.setResizable(false);
        pa.frame.setTitle(title);
        pa.frame.add(pa);
        pa.frame.pack();
        pa.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pa.frame.setLocationRelativeTo(null);
        pa.frame.setVisible(true);
        pa.start();
    }
}