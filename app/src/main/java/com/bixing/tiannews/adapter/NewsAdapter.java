package com.bixing.tiannews.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bixing.tiannews.Fragment.ItemFragment;
import com.bixing.tiannews.R;
import com.bixing.tiannews.WebActivity;
import com.bixing.tiannews.bean.NewsBean;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.DisplayUtils;
import com.bixing.tiannews.utils.SpfManger;
import com.bixing.tiannews.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjw on 2017/9/24.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String TAG = "NewsAdapter";

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public enum ITEM_TYPE {
        NO_IMG, ONE_IMG, THREE_IMG, VIDEO_TYPE;
    }

    private List<NewsBean> data = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DebugLog.d(TAG, " type " + viewType);
        if (viewType == ITEM_TYPE.NO_IMG.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_no_img, null);
            NewsViewNoImgHolder holder = new NewsViewNoImgHolder(view);
            return holder;
        }
        if (viewType == ITEM_TYPE.ONE_IMG.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_one_img, null);
            NewsViewOneImgHolder holder = new NewsViewOneImgHolder(view);
            return holder;
        } else if (viewType == ITEM_TYPE.THREE_IMG.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_more_img, null);
            NewsViewThreeImgHolder holder = new NewsViewThreeImgHolder(view);
            return holder;
        } else if (viewType == ITEM_TYPE.VIDEO_TYPE.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_video, null);
            NewsViewVideoHolder holder = new NewsViewVideoHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewNoImgHolder) {
            ((NewsViewNoImgHolder)holder).setData(data.get(position));
        }
        if (holder instanceof NewsViewOneImgHolder) {
            ((NewsViewOneImgHolder)holder).setData(data.get(position));
        }
        if (holder instanceof NewsViewThreeImgHolder) {
            ((NewsViewThreeImgHolder)holder).setData(data.get(position));
        }
        if (holder instanceof NewsViewVideoHolder) {
            ((NewsViewVideoHolder)holder).setData(data.get(position));
        }
    }

    public void loadMore(List<NewsBean> list) {
        DebugLog.d(TAG, "loadMore " + list.size());
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void refreData(List<NewsBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {

        return data.get(position).getBlockType();
    }

    class NewsViewVideoHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private ImageView iv_video;
        private TextView tv_ping;
        private TextView tv_zan;
        private NewsBean newsBean;

        public NewsViewVideoHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            tv_ping = (TextView)view.findViewById(R.id.tv_ping);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_zan = (TextView)view.findViewById(R.id.tv_zan);
            iv_video = (ImageView)view.findViewById(R.id.iv_video);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", newsBean.getDetailUrl() + "/" + SpfManger.getToken(context));
                    context.startActivity(intent);
                }
            });
        }

        public void setData(NewsBean bean) {
            this.newsBean = bean;
            tv_title.setText(bean.getTitle());
            tv_zan.setText(bean.getLike());
            tv_ping.setText(bean.getComment());
            if (bean.getImg().size() > 0) {
                Glide.with(context).load(bean.getImg().get(0)).into(iv_video);
            }

        }
    }

    class NewsViewOneImgHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;

        private TextView tv_ping;
        private TextView tv_zan;
        private ImageView iv;
        private NewsBean newsBean;

        public NewsViewOneImgHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            tv_ping = (TextView)view.findViewById(R.id.tv_ping);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_zan = (TextView)view.findViewById(R.id.tv_zan);
            iv = (ImageView)view.findViewById(R.id.iv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("isShare", true);
                    intent.putExtra("url", newsBean.getDetailUrl() + "/" + SpfManger.getToken(context));
                    context.startActivity(intent);
                }
            });
        }

        public void setData(NewsBean bean) {
            this.newsBean = bean;
            tv_title.setText(bean.getTitle());
            tv_zan.setText(bean.getLike());
            tv_ping.setText(bean.getComment());
            if (bean.getImg() != null && bean.getImg().size() > 0) {
                Glide.with(context).load(bean.getImg().get(0)).into(iv);
            }

        }
    }

    class NewsViewNoImgHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_ping;
        private TextView tv_zan;
        private ImageView iv;
        private NewsBean bean;

        public NewsViewNoImgHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            tv_ping = (TextView)view.findViewById(R.id.tv_ping);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_zan = (TextView)view.findViewById(R.id.tv_zan);
            iv = (ImageView)view.findViewById(R.id.iv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", bean.getDetailUrl() + "/" + SpfManger.getToken(context));
                    context.startActivity(intent);
                }
            });

        }

        public void setData(NewsBean bean) {
            this.bean = bean;
            tv_title.setText(bean.getTitle());
            tv_zan.setText(bean.getLike());
            tv_ping.setText(bean.getComment());
            // DebugLog.d(TAG, "imgUrl " + bean.getImgs()[0]);
            // Glide.with(context).load(bean.getImgs()[0]).into(iv);

        }
    }

    class NewsViewThreeImgHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private LinearLayout ll_img;
        private TextView tv_ping;
        private TextView tv_zan;
        private NewsBean newsBean;

        public NewsViewThreeImgHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            tv_ping = (TextView)view.findViewById(R.id.tv_ping);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_zan = (TextView)view.findViewById(R.id.tv_zan);
            ll_img = (LinearLayout)view.findViewById(R.id.ll_img);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", newsBean.getDetailUrl() + "/" + SpfManger.getToken(context));
                    context.startActivity(intent);
                }
            });
        }

        public void setData(NewsBean bean) {
            this.newsBean = bean;
            tv_title.setText(bean.getTitle());
            tv_zan.setText(bean.getLike());
            tv_ping.setText(bean.getComment());
            ll_img.removeAllViews();
            for (String imgUrl : bean.getImg()) {
                ImageView imageView = new ImageView(context);
                imageView.setPadding(5, 0, 0, 0);
                Glide.with(context).load(imgUrl).centerCrop().into(imageView);
                int w = DisplayUtils.getW((Activity)context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(w / 3, 200));
                ll_img.addView(imageView);
                // imageView.setImageResource(R.mipmap.ic_launcher);
                // Glide.with(context).load(imgUrl).into(imageView);
                DebugLog.d(TAG, "imgUrl" + imgUrl);

            }
        }
    }
}