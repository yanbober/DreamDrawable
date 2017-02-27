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
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
/**
 * 圆角 Drawable，通过 setCornerRadius(int radius) 方法设置四个圆角半径。
 * 若想成为圆形 Drawable，只用将 radius 值设置为控件宽度的一半即可。
 */
public class RoundDrawable extends Drawable {
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix;
    private float mCornerRadius;

    public RoundDrawable(Bitmap bitmap) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = bitmap;

        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);

        mMatrix = new Matrix();
    }

    public RoundDrawable setCornerRadius(float radius) {
        mCornerRadius = radius;
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        mMatrix.preTranslate(rect.left, rect.top);
        mMatrix.preScale(rect.width()*1.0f/mBitmap.getWidth(),
                            rect.height()*1.0f/mBitmap.getHeight());
        mShader.setLocalMatrix(mMatrix);
        canvas.drawRoundRect(new RectF(rect), mCornerRadius, mCornerRadius, mPaint);
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmap.getHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
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
