package com.bixing.tiannews.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bixing.tiannews.R;
import com.bixing.tiannews.WebActivity;
import com.bixing.tiannews.bean.NewsBean;
import com.bixing.tiannews.bean.ServiceBean;
import com.bixing.tiannews.bean.ServiceItemBean;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.DisplayUtils;
import com.bixing.tiannews.utils.SpfManger;
import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sjw on 2017/9/24.
 */

public class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String TAG = "ServiceAdapter";

    public ServiceAdapter(Context context) {
        this.context = context;

    }

    public enum ITEM_TYPE {
        TITLE, CHILD,
    }

    private List<ServiceItemBean> data = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DebugLog.d(TAG, " type " + viewType);
        if (viewType == ITEM_TYPE.TITLE.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.service_item_title, null);
            ServiceTitle holder = new ServiceTitle(view);
            return holder;
        }
        if (viewType == ITEM_TYPE.CHILD.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.service_item_child, null);
            ServiceChild holder = new ServiceChild(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ServiceChild) {
            ((ServiceChild)holder).setData(data.get(position));
        }
        if (holder instanceof ServiceTitle) {
            ((ServiceTitle)holder).setData(data.get(position));
        }

    }

    public void loadMore(List<ServiceItemBean> list) {
        DebugLog.d(TAG, "loadMore " + list.size());
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void refreData(List<ServiceItemBean> list) {
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

        return data.get(position).isChild() ? ITEM_TYPE.CHILD.ordinal() : ITEM_TYPE.TITLE.ordinal();
    }

    class ServiceChild extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv;
        private ServiceItemBean bean;

        public ServiceChild(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {

            tv_title = (TextView)view.findViewById(R.id.tv_title);

            iv = (ImageView)view.findViewById(R.id.iv_icon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url",bean.getUrl());
                    context.startActivity(intent);
                }
            });

        }

        public void setData(ServiceItemBean bean) {
            this.bean = bean;
            tv_title.setText(bean.getTitle());
            Glide.with(context).load(bean.getIcon()).centerCrop().into(iv);
            // DebugLog.d(TAG, "imgUrl " + bean.getImgs()[0]);
            // Glide.with(context).load(bean.getImgs()[0]).into(iv);

        }
    }

    class ServiceTitle extends RecyclerView.ViewHolder {

        private TextView tv_title;

        public ServiceTitle(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {

            tv_title = (TextView)view.findViewById(R.id.tv_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        public void setData(ServiceItemBean bean) {

            tv_title.setText(bean.getTitle());

            // DebugLog.d(TAG, "imgUrl " + bean.getImgs()[0]);
            // Glide.with(context).load(bean.getImgs()[0]).into(iv);

        }
    }
}
