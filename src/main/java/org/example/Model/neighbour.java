package org.example.Model;

import org.example.Main;

public class neighbour {
        public  point point;
        public  double distance;
        public double minute;
        public  double distancepisagor;

        neighbour() {
        }

        public neighbour(point point, double distance, double minute, double distancepisagor) {
                this.point = point;
                this.distance = distance;
                this.minute = minute;
                this.distancepisagor = distancepisagor;
        }
}
