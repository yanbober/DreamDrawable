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
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * 类似 Flyme 6.0 联系人 Icon 的 Drawable。
 * 当设置图片后显示联系人图片，当设置一个字母或者一个汉字时显示字符图。
 */

public class IconDrawable extends Drawable {
    private Paint mTextPaint;
    private String mContentText;
    private int mFontSize;
    private int mBgColor = Color.GREEN;
    private int mTextColor = Color.WHITE;

    private Bitmap mContentBitmap;

    private Path mClipPath;
    private Matrix mMatrix;

    public IconDrawable() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mClipPath = new Path();
        mMatrix = new Matrix();
    }

    public IconDrawable setTextLabel(String str) {
        mContentText = str;
        mContentBitmap = null;
        invalidateSelf();
        return this;
    }

    public IconDrawable setIconLabel(Bitmap bitmap) {
        mContentBitmap = bitmap;
        mContentText = null;
        invalidateSelf();
        return this;
    }

    public IconDrawable setTextFontSize(int size) {
        mFontSize = size;
        invalidateSelf();
        return this;
    }

    public IconDrawable setTextColor(int color) {
        mTextColor = color;
        invalidateSelf();
        return this;
    }

    public IconDrawable setBackgroundColor(int color) {
        mBgColor = color;
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();

        int count = canvas.saveLayer(new RectF(rect), null, Canvas.ALL_SAVE_FLAG);
        canvas.translate(rect.left, rect.top);
        mClipPath.reset();
        mClipPath.addCircle(rect.width() / 2, rect.height() / 2,
                            Math.min(rect.width(), rect.height()) / 2, Path.Direction.CCW);
        canvas.clipPath(mClipPath);

        if (mContentBitmap == null && !TextUtils.isEmpty(mContentText)) {
            mTextPaint.setColor(mBgColor);
            canvas.drawRect(rect, mTextPaint);
            int fontSize = this.mFontSize <= 0 ? (Math.min(rect.width(), rect.height()) / 2) : this.mFontSize;
            mTextPaint.setTextSize(fontSize);
            mTextPaint.setColor(mTextColor);
            canvas.drawText(mContentText, rect.width() / 2, rect.height() / 2 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);
        } else {

            mMatrix.setScale(rect.width() * 1.0f / mContentBitmap.getWidth(),
                    rect.height() * 1.0f / mContentBitmap.getHeight());
            canvas.setMatrix(mMatrix);
            canvas.drawBitmap(mContentBitmap, rect, rect, null);
        }
        canvas.restoreToCount(count);
    }

    @Override
    public void setAlpha(int alpha) {
        mTextPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mTextPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        Rect rect = getContentRect();
        return (rect.width() > 0 ? rect.width() : -1);
    }

    @Override
    public int getIntrinsicHeight() {
        Rect rect = getContentRect();
        return (rect.height() > 0 ? rect.height() : -1);
    }

    private Rect getContentRect() {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mContentText, 0, mContentText.length(), rect);
        return rect;
    }
}
