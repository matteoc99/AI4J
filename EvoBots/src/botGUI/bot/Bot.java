package botGUI.bot;

import botGUI.Chunk;
import botGUI.ImmobileObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * @author Matteo Cosi
 * @since 14/06/2017
 */
public class Bot extends JPanel {


    private int sensor_length = 60;
    /**
     * the Body of the Bot
     */
    Body body;

    /**
     * The sensor of the Bot
     */
    Sensor sensor;
    /**
     * Describes the rotation of the sensor
     * 0-360 degrees
     */
    public int sensorRotation = 0;


    public int xDir = 1;
    public int yDir = 1;

    public Bot() {
        xDir = (int) (Math.random() * 1);
        yDir = (int) (Math.random() * 1);
        sensor = new Sensor();
        body = new Body();

        setSize(sensor.getWidth() + body.getWidth() + sensor_length, sensor.getHeight() + body.getHeight() + sensor_length);

        body.setLocation(getWidth() / 2 - body.getWidth() / 2, getHeight() / 2 - body.getHeight() / 2);
        rotateAndResize();
        add(sensor);
        add(body);

        //transparent
        setOpaque(false);

        repaint();
    }


    @Override
    public void paint(Graphics g) {
        sensorRotation = sensorRotation % 360;
        g.setColor(Color.BLACK);
        g.drawLine(body.getX() + body.getWidth() / 2, body.getY() + body.getHeight() / 2,
                sensor.getX() + sensor.getWidth() / 2, sensor.getY() + sensor.getHeight() / 2);
        g.drawOval(0, 0, getWidth(), getHeight());

        super.paint(g);
        repaint();
    }

    /**
     * rotate and resize and reposition the Sensor
     */
    private void rotateAndResize() {
        int radius = sensor_length - sensor.getWidth() / 2;
        int x = (int) (radius * Math.cos(Math.toRadians(sensorRotation)));
        int y = (int) (radius * Math.sin(Math.toRadians(sensorRotation)));
        if (x < getWidth() / 2)
            x += getWidth() / 2;
        else
            x -= getWidth() / 2;
        if (y < getHeight() / 2)
            y += getHeight() / 2;
        else
            y -= getHeight() / 2;
        sensor.setLocation(getWidth() - x - sensor.getWidth() / 2, getHeight() - y - sensor.getHeight() / 2);

    }

    public void move() {
        setLocation(getX() + xDir, getY() + yDir);
        sensorRotation++;
        if (sensor_length > 1 && sensorRotation % 20 == 0)
            sensor_length--;

        rotateAndResize();
    }
}
