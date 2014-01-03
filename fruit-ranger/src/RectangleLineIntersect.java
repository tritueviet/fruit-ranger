// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 11/15/2012 3:42:07 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RectangleLineIntersect.java

public class RectangleLineIntersect {

    public RectangleLineIntersect() {
    }

    private static int outcode(double pX, double pY, double rectX, double rectY,
            double rectWidth, double rectHeight) {
        int out = 0;
        if (rectWidth <= 0.0D) {
            out |= 5;
        } else if (pX < rectX) {
            out |= 1;
        } else if (pX > rectX + rectWidth) {
            out |= 4;
        }
        if (rectHeight <= 0.0D) {
            out |= 0xa;
        } else if (pY < rectY) {
            out |= 2;
        } else if (pY > rectY + rectHeight) {
            out |= 8;
        }
        return out;
    }

    public static boolean intersectsLine(double lineX1, double lineY1, double lineX2, double lineY2,
            double rectX, double rectY, double rectWidth, double rectHeight) {
        int out2;
        if ((out2 = outcode(lineX2, lineY2, rectX, rectY, rectWidth, rectHeight)) == 0) {
            return true;
        }
        int out1;
        while ((out1 = outcode(lineX1, lineY1, rectX, rectY, rectWidth, rectHeight)) != 0) {
            if ((out1 & out2) != 0) {
                return false;
            }
            if ((out1 & 5) != 0) {
                double x = rectX;
                if ((out1 & 4) != 0) {
                    x += rectWidth;
                }
                lineY1 += ((x - lineX1) * (lineY2 - lineY1)) / (lineX2 - lineX1);
                lineX1 = x;
            } else {
                double y = rectY;
                if ((out1 & 8) != 0) {
                    y += rectHeight;
                }
                lineX1 += ((y - lineY1) * (lineX2 - lineX1)) / (lineY2 - lineY1);
                lineY1 = y;
            }
        }
        return true;
    }
    private static final int OUT_LEFT = 1;
    private static final int OUT_TOP = 2;
    private static final int OUT_RIGHT = 4;
    private static final int OUT_BOTTOM = 8;
}
