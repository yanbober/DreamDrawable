/**
 * MIT License
 *
 * Copyright (c) 2017 yanbo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cn.yan.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类似腾讯 QQ 手机版 item 讨论组及个人头像 Drawable。
 * 当有一个时显示圆形，当有多个时自动适用，最多支持五个呈五角星形式显示。
 */

public class MultiIconDrawable extends Drawable {
    private Paint paint;
    private List<Bitmap> mBitmapList;

    public MultiIconDrawable() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);

        mBitmapList = new ArrayList<>();
    }

    public MultiIconDrawable setIcons(List<Bitmap> bitmaps) {
        if (bitmaps != null && bitmaps.size() > 5) {
            throw new IllegalArgumentException("MultiIconDrawable support max icon size is 5!");
        }
        mBitmapList.clear();
        mBitmapList.addAll(bitmaps);
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i=0;i<mBitmapList.size();i++){
            mBitmapList.set(i,zoomImage(mBitmapList.get(i),80,80));
        }
        Bitmap.Config config = mBitmapList.get(0).getConfig();
        int width = mBitmapList.get(0).getWidth() * 2;
        int height = width;
        int point_x = width / 2;
        int point_y = height / 2;
        int d = 0;
        ArrayList<Point> points = null;
        switch (mBitmapList.size()) {
            case 5: {
                d = (width * 2) / 5;
                points = getFivePoints(point_x, point_y, d);
                break;
            }
            case 4: {
                d = (width * 3) / 7;
                points = getFourPoints(point_x, point_y, d);
                break;
            }
            case 3: {
                d = (width * 1) / 2;
                points = getThreePoints(point_x, point_y, d);
                break;
            }
            case 2: {
                d = (width * 1) / 2;
                points = getTwoPoints(point_x, point_y, d);
                break;
            }
        }

        int r = d / 2;
        for (int i = 0; i < mBitmapList.size(); i++) {
            mBitmapList.set(i, makeRoundCorner(mBitmapList.get(i), d, d));
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas tempCanvas = new Canvas(bitmap);
        tempCanvas.drawColor(0xFFFFFFFF);
        for (int i = 0; i < mBitmapList.size(); i++) {
            tempCanvas.drawBitmap(mBitmapList.get(i), points.get(i).x, points.get(i).y, null);
            tempCanvas.drawCircle(points.get(i).x + r, points.get(i).y + r, r - 1, paint);
        }
        Bitmap bitmap1 = makeRoundCorner(bitmap, width, height);
        canvas.drawBitmap(bitmap1, getBounds(), getBounds(), null);
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();

        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    private Bitmap makeRoundCorner(Bitmap bitmap, int width, int height) {
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private ArrayList<Point> getFivePoints(int point_x, int point_y, int d) {
        ArrayList<Point> points = new ArrayList<Point>();
        //圆心至图片的距离
        int wr = point_y - d;
        //大圆心至小圆心的距离
        int R = wr + d / 2;
        //头像半径,头像直径为d
        int r = d / 2;
        int RCos18 = (int) (R * (Math.cos(0.1 * Math.PI)));
        int RSin18 = (int) (R * (Math.sin(0.1 * Math.PI)));
        int RCos36 = (int) (R * (Math.cos(0.2 * Math.PI)));
        int RSin36 = (int) (R * (Math.sin(0.2 * Math.PI)));
        Point point1 = new Point(point_x - r, 0);
        Point point2 = new Point();
        point2.x = RCos18 + point_x - r;
        point2.y = point_y - RSin18 - r;
        Point point3 = new Point();
        point3.x = RSin36 + point_x - r;
        point3.y = point_y + RCos36 - r;
        Point point4 = new Point();
        point4.x = point_x - RSin36 - r;
        point4.y = point3.y;
        Point point5 = new Point();
        point5.x = point_x - (int) (RCos18) - r;
        point5.y = point2.y;

        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);

        return points;
    }

    private ArrayList<Point> getFourPoints(int point_x, int point_y, int d) {
        ArrayList<Point> points = new ArrayList<Point>();
        Point point1 = new Point();
        point1.x = point_x - (9 * d) / 10;
        point1.y = point_y - (9 * d) / 10;
        Point point2 = new Point();
        point2.x = point_x - d / 10;
        point2.y = point1.y;
        Point point3 = new Point();
        point3.x = point2.x;
        point3.y = point_y - d / 10;
        Point point4 = new Point();
        point4.x = point1.x;
        point4.y = point3.y;
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        return points;
    }

    private ArrayList<Point> getThreePoints(int point_x, int point_y, int d) {
        ArrayList<Point> points = new ArrayList<Point>();
        int R = d / 2;
        int RCos45 = (int) (R * (Math.cos(0.25 * Math.PI)));
        Point point1 = new Point();
        point1.x = point_x - (d / 2);
        point1.y = point_y - R - (d / 2);
        Point point2 = new Point();
        point2.x = point_x - ((d / 2) - RCos45);
        point2.y = point_y - ((d / 2) - RCos45);
        Point point3 = new Point();
        point3.x = point_x - RCos45 - (d / 2);
        point3.y = point2.y;
        points.add(point1);
        points.add(point2);
        points.add(point3);
        return points;
    }

    private ArrayList<Point> getTwoPoints(int point_x, int point_y, int d) {
        ArrayList<Point> points = new ArrayList<Point>();
        Point point1 = new Point();
        point1.y = point_y - (d / 2);
        point1.x = point_x - (9 * d) / 10;
        Point point2 = new Point();
        point2.x = point_x - (d) / 10;
        point2.y = point1.y;
        points.add(point1);
        points.add(point2);
        return points;
    }
}
