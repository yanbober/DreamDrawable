package cn.yan.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * 底部反射倒影 Drawable。
 */

public class ReflectionDrawable extends Drawable {
    private Bitmap mSrcBitmap;
    private Bitmap mReflectBitmap;

    private Paint mPaint;

    public ReflectionDrawable(Bitmap bitmap) {
        mSrcBitmap = bitmap;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        mReflectBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0,
                                            mSrcBitmap.getWidth(), mSrcBitmap.getHeight(),
                                            matrix, true);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        Rect rectSrc = new Rect(rect.left, rect.top, rect.right, rect.bottom - 200);
        Rect rectReflect = new Rect(rect.left, rect.bottom - 200, rect.right, rect.bottom);

        canvas.drawBitmap(mSrcBitmap, rect, rectSrc, null);

        canvas.drawBitmap(mReflectBitmap, new Rect(0, 0, rectReflect.width(), 200), rectReflect, null);
        mPaint.setShader(new LinearGradient(rectReflect.left, rectReflect.top,
                rectReflect.left, rectReflect.bottom,
                0x08000000, Color.BLACK,
                Shader.TileMode.CLAMP));
        canvas.drawRect(rectReflect, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
