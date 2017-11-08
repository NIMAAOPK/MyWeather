package com.aopk.myweather.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by asus on 2017/10/26.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
        changeTypetFace(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        changeTypetFace(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        changeTypetFace(context);
    }

    private void changeTypetFace(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"font/etouch_light.ttf");
        super.setTypeface(typeface);
    }
}
