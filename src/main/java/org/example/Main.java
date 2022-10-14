package org.example;

import org.example.Model.neighbour;
import org.example.Model.point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Main {
    static int height = 1000;
    static int weight = 1000;
    static BufferedImage img;
    static Map<point, point> neigmatch = new HashMap<>();
    //duraklar
    static ArrayList<point> randompoints = new ArrayList<>();
    static Color defautColor = Color.WHITE;
    //referance servisler
    static ArrayList<point> referancePoints = new ArrayList<>();



    public static void main(String[] args) throws Exception {
        draw.WriteBitmapBlank();
        new Main().ReferancePointsAdd(Color.BLUE);
        new Main().ReferancePointsAdd(Color.PINK);
        new Main().ReferancePointsAdd(Color.BLACK);
        new Main().ReferancePointsAdd(Color.CYAN);
        new Main().ReferancePointsAdd(Color.MAGENTA);
        for (var ref : referancePoints) {
            new draw().writeSquare(ref.x, ref.y, ref.x + 10, ref.y + 10, Color.RED);

        }
        for (int i = 0; i < 150; i++)
            new Main().setRandompoints();
        startProgram();
        new Main().startKmean();
        KmeanStepNext();
        new Main().startKmean();
        KmeanStepNext();
        new Main().startKmean();
        KmeanStepNext();
        // new Main().findNeigboursLevel(0, 4);
       Find.findNeigboursMinMax(0, 100, 5);
        // new Main().findNeighbour(0, 20);
        // new Main().findNeighbour(21, 50);
        // new Main().findNeighbour(51, 100);
        //  new Main().findNeighbour(101, 200);
        //new Main().findNeighbour(201,300);
        new Main().NeigbourColorChange();
     /*   for (Map.Entry<Main.point, Main.point> pointpointEntry:neigmatch.entrySet())
        {
            new Main().WriteDoublePoint(pointpointEntry.getKey(),pointpointEntry.getValue());
        }*/
        for (var r : randompoints) {

           draw.WritePointRandom(r, r.color);
        }
        new Main().run();

        System.out.println("Hello world!");

    }







    private void NeigbourColorChange() {

        for (point point : randompoints) {
            for (org.example.Model.neighbour neighbour : point.neighbours) {
                int temp = pisagor(point.x, point.y, neighbour.point.x, neighbour.point.y);
                if (draw.NeigbourColorSwitch(point, neighbour.point, temp, 20, Color.YELLOW, false, true, false)) ;
                else if (
                        draw.NeigbourColorSwitch(point, neighbour.point, temp, 50, Color.GRAY, false, true, false)) ;
                else if (draw.NeigbourColorSwitch(point, neighbour.point, temp, 70, Color.RED, false, true, false)) ;
                else if (draw.NeigbourColorSwitch(point, neighbour.point, temp, 120, Color.green, false, true, false)) ;
                else if (draw.NeigbourColorSwitch(point, neighbour.point, temp, 200, Color.BLACK, false, true, false)) ;
                else if (draw.NeigbourColorSwitch(point, neighbour.point, temp, 300, Color.PINK, false, true, false)) ;


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
            draw.WritePointRandom(point, referancePoint.color);

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
            if (count != 0) {
                referance.x = totalx / count;
                referance.y = totaly / count;
            }
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

    public static int pisagor(int x, int y, int x1, int y1) {
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
        draw.WritePointRandom(p, Color.RED);
        randompoints.add(p);

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




}