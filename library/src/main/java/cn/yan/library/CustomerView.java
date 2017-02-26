package cn.yan.library;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yan on 17-2-26.
 */

public class CustomerView extends View {
    private ReflectionDrawable mDrawable;

    public CustomerView(Context context) {
        this(context, null);
    }

    public CustomerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDrawable = new ReflectionDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.timg));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }
}
