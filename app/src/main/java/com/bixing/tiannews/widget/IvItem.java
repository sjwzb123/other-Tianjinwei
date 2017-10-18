package com.bixing.tiannews.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bixing.tiannews.R;
import com.bixing.tiannews.utils.DisplayUtils;
import com.bumptech.glide.Glide;

import java.util.zip.Inflater;

/**
 * Created by sjw on 2017/9/25.
 */

public class IvItem extends LinearLayout {
    private ImageView iv;
    private ImageView iv_dele;
    private Context c;

    public IvItem(Context context) {
        super(context);
        this.c = context;
        initView(context);
        setPadding(5, 0, 0, 0);
    }

    private void initView(Context c) {
        View v = LayoutInflater.from(c).inflate(R.layout.iv_item, null);
        v.setLayoutParams(new LayoutParams(DisplayUtils.getW((Activity) c)/3-60, LinearLayout.LayoutParams.WRAP_CONTENT));
        iv = (ImageView)v.findViewById(R.id.iv_select);
        iv_dele = (ImageView)v.findViewById(R.id.iv_dele);
        addView(v);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public void setSelectImgPath(String path) {
        Glide.with(c).load(path).into(iv);
    }
}
