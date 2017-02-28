package cn.yan.dreamdrawable;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import cn.yan.library.LauncherIconDrawable;
import cn.yan.library.RoundDrawable;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) this.findViewById(R.id.imageview);
        mImageView.setImageDrawable(new LauncherIconDrawable(mImageView.getDrawable().mutate()));
    }
}
