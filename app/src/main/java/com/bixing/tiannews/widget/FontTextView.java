package com.bixing.tiannews.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sjw on 2017/9/28.
 */

public class FontTextView extends TextView {
    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);

    }

    private void initFont(Context context) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/FZLTTHJW.TTF");
        setTypeface(typeFace);
    }
}
