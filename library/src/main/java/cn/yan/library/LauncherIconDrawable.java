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

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * 类似许多 Android Launcher 桌面图标更新的 Drawable。
 * 譬如 miui 桌面 app 更新图标进度。
 */
public class LauncherIconDrawable extends Drawable {
    private final Drawable mDrawable;
    private float mPercent;

    private ColorFilter mDefaultColorFilter;
    private ColorFilter mPercentColorFilter;

    public LauncherIconDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public LauncherIconDrawable setDefaultColor(@ColorInt int defaultColor) {
        return setDefaultColor(defaultColor, PorterDuff.Mode.MULTIPLY);
    }

    public LauncherIconDrawable setDefaultColor(@ColorInt int defaultColor, PorterDuff.Mode mode) {
        mDefaultColorFilter = new PorterDuffColorFilter(defaultColor, mode);
        invalidateSelf();
        return this;
    }

    public LauncherIconDrawable setPercentColor(@ColorInt int percentColor) {
        return setPercentColor(percentColor, PorterDuff.Mode.MULTIPLY);
    }

    public LauncherIconDrawable setPercentColor(@ColorInt int percentColor, PorterDuff.Mode mode) {
        mPercentColorFilter = new PorterDuffColorFilter(percentColor, mode);
        invalidateSelf();
        return this;
    }

    public void setCurPercent(@FloatRange(from = 0f, to = 1f) float fillPercent) {
        mPercent = fillPercent;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        int curOffset = (int) (mPercent * rect.height());
        canvas.save();
        canvas.clipRect(rect.left, rect.top, rect.width(), rect.height() - curOffset);
        mDrawable.setColorFilter(mDefaultColorFilter);
        mDrawable.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.clipRect(rect.left, rect.height() - curOffset, rect.width(), rect.height());
        mDrawable.setColorFilter(mPercentColorFilter);
        mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mDrawable.setBounds(getBounds());
    }

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
        mDrawable.invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        mDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mDrawable.setColorFilter(cf);
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public int getOpacity() {
        return mDrawable.getOpacity();
    }
}
