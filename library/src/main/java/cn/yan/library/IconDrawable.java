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
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
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

    private Bitmap mContentBitmap;

    public IconDrawable() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
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

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();

        int count = canvas.save();
        canvas.translate(rect.left, rect.top);

        if (mContentBitmap == null && !TextUtils.isEmpty(mContentText)) {
            canvas.drawColor(Color.GREEN);
            int fontSize = this.mFontSize < 0 ? (Math.min(rect.width(), rect.height()) / 2) : this.mFontSize;
            mTextPaint.setTextSize(fontSize);
            canvas.drawText(mContentText, rect.width() / 2, rect.height() / 2 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);
        } else {
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
