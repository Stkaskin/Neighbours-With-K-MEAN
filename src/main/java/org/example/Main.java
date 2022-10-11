package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Thread {
    static int height = 1000;
    static int weight = 1000;
    static BufferedImage img;
    static ArrayList<point> referancePoints = new ArrayList<>();

    class point {
        int x = 0;
        int y = 0;
        Color color;

    }

    static ArrayList<point> randompoints = new ArrayList<>();
    static Color defautColor = Color.WHITE;

    public static void main(String[] args) throws Exception {
        WriteBitmapBlank();
        new Main().ReferancePointsAdd(Color.BLUE);
        new Main().ReferancePointsAdd(Color.PINK);
        new Main().ReferancePointsAdd(Color.BLACK);
        new Main().ReferancePointsAdd(Color.CYAN);
        new Main().ReferancePointsAdd(Color.MAGENTA);

        for (int i = 0; i < 150; i++)
            new Main().setRandompoints();
        startProgram();
        new Main().start();
        new Main().startKmean();
        KmeanStepNext();
        new Main().startKmean();
        KmeanStepNext();
        new Main().startKmean();
        KmeanStepNext();
        System.out.println("Hello world!");
    }

    private void startKmean() {
        ArrayList<point> v1 = new ArrayList<>();
        ArrayList<point> v2 = new ArrayList<>();

        for (point point : randompoints) {
            int b_ = 99999;
            point referancePoint = null;
            for (point referance : referancePoints) {
                int temp = pisagor(point.x, point.y, referance.x, referance.y);
                if (temp < b_) {
                    b_ = temp;
                    referancePoint = referance;
                }

            }
            point.color = referancePoint.color;
            WritePointRandom(point, referancePoint.color);

        }

    }

    private static void KmeanStepNext() {
        for (point referance : referancePoints) {
            int totalx = 0;
            int totaly = 0;
            int count = 0;
            for (point point : randompoints) {
                if (point.color == referance.color) {
                    totalx += point.x;
                    totaly += point.y;
                    count++;
                }
            }
            referance.x = totalx / count;
            referance.y = totaly / count;
        }
    }

    private static void startProgram() {
     /*   for (point point : randompoints) {
            for (point point_ : randompoints) {
                if (point_ == point)
                    continue;
                pisagor(point_.x, point_.y, point.x, point.y);
            }
        }*/
    }

    private static int pisagor(int x, int y, int x1, int y1) {
        int x_ = Math.abs(x - x1);
        int y_ = Math.abs(y - y1);
        int c = (int) Math.sqrt(Math.pow(x_, 2) + Math.pow(y_, 2));
        return c;
    }

    private void ReferancePointsAdd(Color color) {
        point p = new point();
        p.x = new Random().nextInt(0, 1000);

        p.y = new Random().nextInt(0, 1000);
        p.color = color;
        referancePoints.add(p);
    }

    private void setRandompoints() {
        point p = new point();
        p.x = new Random().nextInt(0, 1000);
        p.y = new Random().nextInt(0, 1000);
        WritePointRandom(p, Color.RED);
        randompoints.add(p);

    }

    private static void WritePointRandom(point p, Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int col = (r << 16) | (g << 8) | b;
        File f = new File("MyFile.png");
        try {
            img.setRGB(p.x-1, p.y, col);
            img.setRGB(p.x-2, p.y-1, col);
            img.setRGB(p.x-2, p.y+1, col);
            img.setRGB(p.x, p.y-1, col);
            img.setRGB(p.x+1, p.y-2, col);
            img.setRGB(p.x-1, p.y-2, col);
            img.setRGB(p.x+1, p.y, col);
            img.setRGB(p.x+2, p.y+1, col);
            img.setRGB(p.x+2, p.y-1, col);
            img.setRGB(p.x, p.y+1, col);

            img.setRGB(p.x, p.y, col);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void run() {
        File f = new File("MyFile.png");
        System.out.println("This code is running in a thread");
        try {
            ImageIO.write(img, "PNG", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void WriteBitmapBlank() throws IOException {
        try {
            img = new BufferedImage(
                    weight, height, BufferedImage.TYPE_INT_RGB);

            File f = new File("MyFile.png");
            int r = defautColor.getRed();
            int g = defautColor.getGreen();
            int b = defautColor.getBlue();

            int col = (r << 16) | (g << 8) | b;
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < weight; y++) {
                    img.setRGB(x, y, col);
                }
            }
            boolean b2 = ImageIO.write(img, "PNG", f);
            b2 = b2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}