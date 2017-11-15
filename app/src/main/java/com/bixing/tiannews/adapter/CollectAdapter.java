package com.bixing.tiannews.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bixing.tiannews.R;
import com.bixing.tiannews.ReportDetailActivtiy;
import com.bixing.tiannews.WebActivity;
import com.bixing.tiannews.bean.CollectBean;
import com.bixing.tiannews.bean.ReportBean;
import com.bixing.tiannews.utils.SpfManger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sjw on 2017/9/30.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CollectBean> data = new ArrayList<>();
    private Context context;

    public CollectAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportHolder(LayoutInflater.from(context).inflate(R.layout.item_report, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReportHolder reportHolder = (ReportHolder)holder;
        ((ReportHolder)holder).setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void loadMore(List<CollectBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void refre(List<CollectBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    class ReportHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_status;
        private CollectBean bean;

        public ReportHolder(View itemView) {
            super(itemView);

            initView(itemView);
        }

        public void initView(View view) {

            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_time = (TextView)view.findViewById(R.id.tv_time);
            tv_status = (TextView)view.findViewById(R.id.tv_status);
            tv_status.setVisibility(View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("title", bean.getTitle());
                    intent.putExtra("isShare", true);
                    intent.putExtra("url", bean.getDetailUrl() + "/" + SpfManger.getToken(context));
                    context.startActivity(intent);
                }
            });
        }

        public void setData(CollectBean bean) {
            this.bean = bean;
            tv_title.setText(bean.getTitle());
            tv_time.setText(bean.getCreateTime());
            // tv_status.setText(bean.getStatus());
        }
    }
}
