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
