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
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * 类似腾讯 QQ 手机版 item 头像 Drawable。
 * 当有一个时显示圆形，当有多个时自动适用，最多支持五个呈五角星形式显示。
 */

public class MultiIconDrawable extends Drawable {
    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
