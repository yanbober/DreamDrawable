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
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

/**
 * 带底部反射倒影的Drawable。
 * 注意：content自动缩放适配倒影height以外的高度。
 * 通过setReflectionHeight(int height)方法设置底部倒影的高度。
 */

public class ReflectionDrawable extends Drawable {
    private Bitmap mSrcBitmap;
    private Bitmap mReflectBitmap;

    private Paint mPaint;
    private int mReflectionHeight;

    public ReflectionDrawable(Bitmap bitmap) {
        mSrcBitmap = bitmap;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        mReflectBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0,
                                            mSrcBitmap.getWidth(), mSrcBitmap.getHeight(),
                                            matrix, true);
    }

    public ReflectionDrawable setReflectionHeight(@IntRange(from = 0) int height) {
        mReflectionHeight = height;
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        Rect rectSrc = new Rect(rect.left, rect.top, rect.right, rect.bottom - mReflectionHeight);
        Rect rectReflect = new Rect(rect.left, rect.bottom - mReflectionHeight, rect.right, rect.bottom);

        canvas.drawBitmap(mSrcBitmap, new Rect(0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight()), rectSrc, null);

        canvas.drawBitmap(mReflectBitmap, new Rect(0, 0, mReflectBitmap.getWidth(), mReflectionHeight), rectReflect, null);
        mPaint.setShader(new LinearGradient(rectReflect.left, rectReflect.top,
                rectReflect.left, rectReflect.bottom,
                Color.TRANSPARENT, Color.BLACK,
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
