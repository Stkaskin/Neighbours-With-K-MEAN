package org.example;

import org.example.Model.point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class draw extends Main{
    static void  writeSquare(int x, int y, int x1, int y1, Color color) {
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
  public   static   void WriteDoublePoint(point w1, point w2, Color color) {

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
    public static boolean NeigbourColorSwitch(point point, point point_, int temp, int value, Color color, boolean square, boolean pointcross, boolean pointallcross) {
        if (temp < value) {
            boolean t = false;

            for (Map.Entry<point, point> pointpointEntry : neigmatch.entrySet()) {
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
                new draw().WriteDoublePoint(point, point_, color);
            }
            if (pointallcross)
                new draw().WriteDoublePoint(point, point_, color);
            if (square)
                draw.writeSquare(point.x, point.y, point_.x, point_.y, color);
            return true;
        } else
            return false;

    }   public static void WritePointRandom(point p, Color color) {
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


    } public static void WriteBitmapBlank() throws IOException {
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
