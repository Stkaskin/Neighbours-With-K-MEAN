package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    static int height = 1000;
    static int weight = 1000;
    static BufferedImage img;
    static Map<point, point> neigmatch = new HashMap<>();
    static ArrayList<point> referancePoints = new ArrayList<>();

    class point {
        int x = 0;
        int y = 0;
        Color color;
        ArrayList<point> neighbours = new ArrayList<>();

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
        new Main().startKmean();
        KmeanStepNext();
        new Main().startKmean();
        KmeanStepNext();
        new Main().startKmean();
        KmeanStepNext();
        new Main().findNeighbour();
        new Main().NeigbourColorChange();
     /*   for (Map.Entry<Main.point, Main.point> pointpointEntry:neigmatch.entrySet())
        {
            new Main().WriteDoublePoint(pointpointEntry.getKey(),pointpointEntry.getValue());
        }*/

        new Main().run();

        System.out.println("Hello world!");

    }

    private void writeSquare(int x, int y, int x1, int y1, Color color) {
        int locx = 0;
        int locx1 = 0;
        int locy = 0;
        int locy1 = 0;
        if (x < x1) {
            // x-4
            locx = x - 4;
            locx1 = x1 + 4;
        } else {
            locx = x1 - 4;
            locx1 = x + 4;
        }
        if (y < y1) {
            // x-4
            locy = y - 4;
            locy1 = y1 + 4;
        } else {
            locy = y1 - 4;
            locy1 = y + 4;
        }
        for (int i = locx; i < locx1; i++) {
            img.setRGB(i, locy, color.getRGB());

        }
        for (int i = locx; i < locx1; i++) {
            img.setRGB(i, locy1, color.getRGB());

        }

        for (int i = locy; i < locy1; i++) {
            img.setRGB(locx - 4, i, color.getRGB());

        }
        for (int i = locy; i < locy1; i++) {
            img.setRGB(locx1 + 4, i, color.getRGB());

        }


    }

    private void WriteDoublePoint(point w1, point w2,Color color) {

        double pointerx = w1.x;
        double pointery = w1.y;

        double x = w1.x - w2.x;
        int a = x > 0 ? -1 : +1;
        double y = w2.y - w1.y;
        double c = y / Math.abs(x);

        for (int i = 0; i < Math.abs(x); i++) {
            pointerx += a;
            pointery += c;
            img.setRGB((int) pointerx, (int) pointery, color.getRGB());
        }

    }

    private boolean NeigbourColorSwitch(point point, point point_, int temp, int value, Color color, boolean square, boolean pointcross, boolean pointallcross) {
        if (temp < value) {
            boolean t = false;

            for (Map.Entry<Main.point, Main.point> pointpointEntry : neigmatch.entrySet()) {
                if (pointpointEntry.getKey().equals(point)) {
                    if (pointpointEntry.getValue().equals(point_))
                        t = true;
                }
                if (pointpointEntry.getValue().equals(point)) {
                    if (pointpointEntry.getKey().equals(point_))
                        t = true;
                }

            }
            if (!t && pointcross) {

                neigmatch.put(point, point_);
                neigmatch.put(point_, point);
                new Main().WriteDoublePoint(point, point_,color);
            }
            if (pointallcross)
                new Main().WriteDoublePoint(point, point_,color);
            if (square)
                writeSquare(point.x, point.y, point_.x, point_.y, color);
            return true;
        } else
            return false;

    }

    private void NeigbourColorChange() {

        for (point point : randompoints) {
            for (point point_ : point.neighbours) {
                int temp = pisagor(point.x, point.y, point_.x, point_.y);
                if (NeigbourColorSwitch(point, point_, temp, 20, Color.YELLOW,true,true,true)) ;
                else if (
                        NeigbourColorSwitch(point, point_, temp, 50, Color.GRAY,true,true,true)) ;
                else if (NeigbourColorSwitch(point, point_, temp, 70, Color.RED,true,true,true)) ;
                else if (NeigbourColorSwitch(point, point_, temp, 70, Color.green,true,true,true)) ;


            }

        }
    }

    private void findNeighbour() {
        for (point point : randompoints) {
            for (point point_ : randompoints) {
                if (point == point_)
                    continue;
                int temp = pisagor(point.x, point.y, point_.x, point.y);
                if (temp < 70)
                    point.neighbours.add(point_);
            }
        }
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
        p.x = new Random().nextInt(10, 990);

        p.y = new Random().nextInt(10, 990);
        p.color = color;
        referancePoints.add(p);
    }

    private void setRandompoints() {
        point p = new point();
        p.x = new Random().nextInt(10, 990);
        p.y = new Random().nextInt(10, 990);
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
            img.setRGB(p.x - 1, p.y, col);
            img.setRGB(p.x - 2, p.y - 1, col);
            img.setRGB(p.x - 2, p.y + 1, col);
            img.setRGB(p.x, p.y - 1, col);
            img.setRGB(p.x + 1, p.y - 2, col);
            img.setRGB(p.x - 1, p.y - 2, col);
            img.setRGB(p.x + 1, p.y, col);
            img.setRGB(p.x + 2, p.y + 1, col);
            img.setRGB(p.x + 2, p.y - 1, col);
            img.setRGB(p.x, p.y + 1, col);
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