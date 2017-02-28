package cn.yan.dreamdrawable;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import cn.yan.library.IconDrawable;
import cn.yan.library.IconView;
import cn.yan.library.LauncherIconDrawable;
import cn.yan.library.ReflectionDrawable;
import cn.yan.library.RoundDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoDataHandle();
    }

    private void demoDataHandle() {
        //TODO Ignore the code style, write to this style that just to test customer drawable.
        handleRoundDrawable();
        handleReflectionDrawable();
        handleLauncherIconDrawable();
        handleIconDrawable();
    }

    private void handleRoundDrawable() {
        ImageView imageView1 = (ImageView) this.findViewById(R.id.imageview1);
        imageView1.setImageDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.timg)).setCornerRadius(50));

        ImageView imageView2 = (ImageView) this.findViewById(R.id.imageview2);
        imageView2.setImageDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.timg)).setCornerRadius(300));

        ImageView imageView3 = (ImageView) this.findViewById(R.id.imageview3);
        imageView3.setImageDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.timg)).setCornerRadius(250));
    }

    private void handleReflectionDrawable() {
        ImageView imageView4 = (ImageView) this.findViewById(R.id.imageview4);
        imageView4.setImageDrawable(new ReflectionDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.timg)).setReflectionHeight(80));

        ImageView imageView5 = (ImageView) this.findViewById(R.id.imageview5);
        imageView5.setImageDrawable(new ReflectionDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.timg)).setReflectionHeight(120));
    }

    private void handleLauncherIconDrawable() {
        ImageView imageView6 = (ImageView) this.findViewById(R.id.imageview6);
        final LauncherIconDrawable drawable = new LauncherIconDrawable(imageView6.getDrawable().mutate());
        imageView6.setImageDrawable(drawable);
        drawable.setPercentColor(Color.DKGRAY);
        imageView6.setOnClickListener(new View.OnClickListener() {
            float percent = 0;
            @Override
            public void onClick(View view) {
                drawable.setCurPercent(percent*1.0f/100);
                percent += 5;
                if (percent > 100) {
                    percent = 0;
                }
            }
        });

        ImageView imageView7 = (ImageView) this.findViewById(R.id.imageview7);
        final LauncherIconDrawable drawable1 = new LauncherIconDrawable(imageView7.getDrawable().mutate());
        imageView7.setImageDrawable(drawable1);
        drawable1.setDefaultColor(Color.DKGRAY);
        drawable1.setPercentColor(Color.WHITE);
        imageView7.setOnClickListener(new View.OnClickListener() {
            float percent = 0;
            @Override
            public void onClick(View view) {
                drawable1.setCurPercent(percent*1.0f/100);
                percent += 5;
                if (percent > 100) {
                    percent = 0;
                }
            }
        });
    }

    private void handleIconDrawable() {
        IconView imageView8 = (IconView) this.findViewById(R.id.imageview8);
        IconDrawable drawable = imageView8.getIconDrawable();
        drawable.setBackgroundColor(Color.GREEN);
        drawable.setTextLabel("M");

        IconView imageView9 = (IconView) this.findViewById(R.id.imageview9);
        IconDrawable drawable1 = imageView9.getIconDrawable();
        drawable1.setBackgroundColor(Color.YELLOW);
        drawable1.setTextColor(Color.DKGRAY);
        drawable1.setTextLabel("王");

        IconView imageView10 = (IconView) this.findViewById(R.id.imageview10);
        IconDrawable drawable2 = imageView10.getIconDrawable();
        drawable2.setIconLabel(BitmapFactory.decodeResource(getResources(), R.drawable.timg));

        IconView imageView11 = (IconView) this.findViewById(R.id.imageview11);
        IconDrawable drawable3 = imageView11.getIconDrawable();
        drawable3.setIconLabel(BitmapFactory.decodeResource(getResources(), R.drawable.timg1));
    }
}
