package com.bixing.tiannews.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bixing.tiannews.R;
import com.bixing.tiannews.WebActivity;
import com.bixing.tiannews.bean.BannerBean;
import com.bixing.tiannews.bean.BaseBean;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.DisplayUtils;
import com.bixing.tiannews.utils.SpfManger;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjw on 2017/11/14.
 */

public class HeadView extends FrameLayout {
    private RollPagerView rollPagerView;
    private BannerAdapter adapter;
    private List<BannerBean> list = new ArrayList<>();

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.head_view, this);
        int h= DisplayUtils.getW((Activity) getContext())*9/16;
        DebugLog.d("initV ","h  "+h);

        rollPagerView = (RollPagerView)findViewById(R.id.roll_view_pager);
        rollPagerView.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtils.getW((Activity) getContext()),h ));
        // 设置播放时间间隔
        rollPagerView.setPlayDelay(2000);
        // 设置透明度
        rollPagerView.setAnimationDurtion(500);
        // 设置适配器
        adapter = new BannerAdapter();
        rollPagerView.setAdapter(adapter);
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("isShare", true);
                intent.putExtra("url", list.get(position).getDetailUrl() + "/" + SpfManger.getToken(getContext()));
                getContext().startActivity(intent);
            }
        });

        // 设置指示器（顺序依次）
        // 自定义指示器图片
        // 设置圆点指示器颜色
        // 设置文字指示器
        // 隐藏指示器
        // mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        rollPagerView.setHintView(new ColorPointHintView(getContext(), Color.WHITE,Color.GRAY));

    }

    public void setDataList(List<BannerBean> dataList) {
        list.clear();
        if (dataList != null) {
            list.addAll(dataList);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class BannerAdapter extends StaticPagerAdapter {

        @Override
        public View getView(ViewGroup container, int position) {
            View view=LayoutInflater.from(getContext()).inflate(R.layout.banner_item,null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_banner);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int h= DisplayUtils.getW((Activity) getContext())*9/16;
            view.setLayoutParams(new RelativeLayout.LayoutParams(DisplayUtils.getW((Activity) getContext()),h ));
            BannerBean bannerBean = list.get(position);
            Glide.with(getContext()).load(bannerBean.getImg()).into(iv);
            TextView tv= (TextView) view.findViewById(R.id.tv_title);
            tv.setText(bannerBean.getTitle());
            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
