package com.bixing.tiannews.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bixing.tiannews.R;
import com.bixing.tiannews.adapter.NewsAdapter;
import com.bixing.tiannews.adapter.RecycleViewDivider;
import com.bixing.tiannews.bean.NewsBean;
import com.bixing.tiannews.bean.NewsResponsBean;
import com.bixing.tiannews.bean.NewsTypeBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by sjw on 2017/9/28.
 */

public class NewsListView extends LinearLayout {
    private XRecyclerView recyclerView;
    private NewsAdapter adapter;
    private int page = 1;
    private Context context;
    private String TAG = "NewsListView";
    private NewsTypeBean newsBean;

    public NewsListView(Context context) {
        super(context);
        this.context=context;
        initView(context);
    }

//    public NewsListView(Context context) {
//        super(context);
//        this.context = context;
//        initView(context);
//
//    }

//    public NewsListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//        this.context = context;
//    }

    public void setNewsTypeBean(NewsTypeBean bean) {
        this.newsBean = bean;
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item, null);
        addView(view);
        recyclerView = (XRecyclerView)view.findViewById(R.id.recylerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetData();
            }

            @Override
            public void onLoadMore() {
                getNetData();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (page == 1) {
            getNetData();
        }
    }

    private void getNetData() {

        HttpManager.news(newsBean.getTypeId(), String.valueOf(page), "", new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                if (TextUtils.isEmpty(response)) {
                    return;
                }

                final NewsResponsBean responsBean = GsonManager.fromJsonStr(response, NewsResponsBean.class);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responsBean.isSucc()) {
                            if (responsBean.getData() != null) {
                                adapter.loadMore(responsBean.getData());
                                page++;
                            }
                            //ToastUtils.toast(getContext(), responsBean.getMsg());

                        } else {
                            ToastUtils.toast(getContext(), responsBean.getMsg());
                        }
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();

                    }
                });
            }
        });
    }

}
