package org.example.Model;

public class distance {
    public     point point;
    public  double distance;

    public   distance(point p, double d) {
            this.point = p;
            distance = d;
        }

    public distance() {
        }

        public point getPoint() {
            return point;
        }

        public void setPoint(point point) {
            this.point = point;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }