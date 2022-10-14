package org.example;

import org.example.Model.distance;
import org.example.Model.neighbour;
import org.example.Model.point;

import java.util.ArrayList;
import java.util.Comparator;

public class Find extends Main{
    public static void findNeigboursMinMax(int min, int max, int near) {
        int ref = -1;
        for (var p : randompoints) {
            ref++;
            findNeighbourOne(ref, min, max, near);


        }
    }



    public static void findNeighbourOne(int point, int min, int max, int found) {
        ArrayList<distance> dis = new ArrayList<>();
        for (int j = 0; j < randompoints.size(); j++) {
            var point_=randompoints.get(j);
            if (randompoints.get(point) == point_)
                continue;
            double temp = pisagor(randompoints.get(point).x, randompoints.get(point).y, point_.x, point_.y);
            if (min < temp && temp < max) {

                dis.add(new distance(point_, temp));
            }
         /*   found--;
            if (found == 0)
                break;*/
        }

        dis.sort(Comparator.comparing(distance::getDistance));
        for (int i = 0; i < dis.size() && i < found; i++) {
            randompoints.get(point).neighbours.add(new neighbour(dis.get(i).point, 0, 0, dis.get(i).distance));
        }
        if (dis.size()<found)
            findNeighbourOne(point,min,max*2,found);


    }

    public static void findNeighbour(int min, int max) {
        for (point point : randompoints) {
            for (point point_ : randompoints) {
                if (point == point_)
                    continue;
                int temp = pisagor(point.x, point.y, point_.x, point_.y);
                if (min < temp && temp < max)
                    point.neighbours.add(new neighbour(point_, 0, 0, temp));
            }
        }
    }
    private void findNeigboursLevel(int minlevel, int maxlevel) {
        int ref = -1;
        for (var p : randompoints) {
            ref++;
            int[] mins = new int[]{0, 21, 51, 71, 101, 201, 301, 501};
            int[] max = new int[]{20, 50, 70, 100, 200, 300, 500, 1000};
            int found = 0;
            for (int i = minlevel; i < max.length && i < maxlevel; i++) {


                Find.findNeighbourOne(ref, mins[i], max[i], max.length - i);

            }
        }
    }
}
