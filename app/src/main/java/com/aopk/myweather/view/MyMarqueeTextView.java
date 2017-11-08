package com.aopk.myweather.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/8.
 */

public class MyMarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    public MyMarqueeTextView(Context context) {
        super(context);
    }

    public MyMarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
