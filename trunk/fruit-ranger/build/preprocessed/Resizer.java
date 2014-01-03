// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 11/15/2012 3:33:49 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Resizer.java

import javax.microedition.lcdui.Image;

public class Resizer {

    public Resizer() {
    }

    static int[] getPixels(Image src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int pixels[] = new int[w * h];
        src.getRGB(pixels, 0, w, 0, 0, w, h);
        return pixels;
    }

    static Image drawPixels(int pixels[], int w, int h) {
        return Image.createRGBImage(pixels, w, h, true);
    }

    static Image resizeImage(Image src, float factor) {
        return resizeImage(src, factor, 1);
    }

    static Image resizeImage(Image src, float factor, int mode) {
        int srcW = src.getWidth();
        int srcH = src.getHeight();
        int destW = (int) ((float) srcW * factor) + 5;
        int destH = srcH;
        int destPixels[] = new int[destW * destH];
        int srcPixels[] = getPixels(src);
        if (mode == 0) {
            for (int destY = 0; destY < destH; destY++) {
                for (int destX = 0; destX < destW; destX++) {
                    int srcX = (destX * srcW) / destW;
                    int srcY = (destY * srcH) / destH;
                    destPixels[destX + destY * destW] = srcPixels[srcX + srcY * srcW];
                }

            }

        } else {
            int ratioW = (srcW << 13) / destW;
            int ratioH = (srcH << 13) / destH;
            int tmpPixels[] = new int[destW * srcH];
            for (int y = 0; y < srcH; y++) {
                for (int destX = 0; destX < destW; destX++) {
                    int count = 0;
                    int a = 0;
                    int r = 0;
                    int b = 0;
                    int g = 0;
                    int srcX = destX * ratioW >> 13;
                    int srcX2 = (destX + 1) * ratioW >> 13;
                    do {
                        int argb = srcPixels[srcX + y * srcW];
                        a += (argb & 0xff000000) >> 24;
                        r += (argb & 0xff0000) >> 16;
                        g += (argb & 0xff00) >> 8;
                        b += argb & 0xff;
                        count++;
                    } while (++srcX <= srcX2 && srcX + y * srcW < srcPixels.length);
                    a /= count;
                    r /= count;
                    g /= count;
                    b /= count;
                    tmpPixels[destX + y * destW] = a << 24 | r << 16 | g << 8 | b;
                }

            }

            for (int x = 0; x < destW; x++) {
                for (int destY = 0; destY < destH; destY++) {
                    int count = 0;
                    int a = 0;
                    int r = 0;
                    int b = 0;
                    int g = 0;
                    int srcY = destY * ratioH >> 13;
                    int srcY2 = (destY + 1) * ratioH >> 13;
                    do {
                        int argb = tmpPixels[x + srcY * destW];
                        a += (argb & 0xff000000) >> 24;
                        r += (argb & 0xff0000) >> 16;
                        g += (argb & 0xff00) >> 8;
                        b += argb & 0xff;
                        count++;
                    } while (++srcY <= srcY2 && x + srcY * destW < tmpPixels.length);
                    a /= count;
                    a = a <= 255 ? a : 255;
                    r /= count;
                    r = r <= 255 ? r : 255;
                    g /= count;
                    g = g <= 255 ? g : 255;
                    b /= count;
                    b = b <= 255 ? b : 255;
                    destPixels[x + destY * destW] = a << 24 | r << 16 | g << 8 | b;
                }

            }

        }
        return drawPixels(destPixels, destW, destH);
    }
    private static final int FP_SHIFT = 13;
    private static final int FP_ONE = 8192;
    private static final int FP_HALF = 4096;
    public static final int MODE_POINT_SAMPLE = 0;
    public static final int MODE_BOX_FILTER = 1;
}
