package com.bixing.tiannews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.adapter.CollectAdapter;
import com.bixing.tiannews.adapter.RecycleViewDivider;
import com.bixing.tiannews.adapter.ReportAdapter;
import com.bixing.tiannews.bean.CollectBean;
import com.bixing.tiannews.bean.CollectResponseBean;
import com.bixing.tiannews.bean.GetReportResponseBean;
import com.bixing.tiannews.bean.ReportBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.SpfManger;
import com.bixing.tiannews.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by sjw on 2017/11/15.
 */

public class MyCollectListActivity extends BaseActivity{
    private XRecyclerView recyclerView;
    private int page = 1;
    private CollectAdapter adapter;
    private String TAG = "MyReportActivity";

    @Override
    protected int getContviewId() {
        return R.layout.activity_coll;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView)findViewById(R.id.recylerview);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 2, R.color.color_F6F5F4));
        adapter = new CollectAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void iniData() {
        getNetData();
    }

    private void getNetData() {
        HttpManager.collectList(SpfManger.getToken(this), String.valueOf(page), new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                DebugLog.d(TAG, "resposne " + response);
                final CollectResponseBean reportResponseBean = GsonManager.fromJsonStr(response, CollectResponseBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (reportResponseBean.isSucc()) {
                            if (reportResponseBean.getData() != null && reportResponseBean.getData().size() > 0) {
                                bindDataToView(reportResponseBean.getData());
                            } else {
                                ToastUtils.toast(MyCollectListActivity.this, "没有内容");
                            }

                        } else {
                            ToastUtils.toast(MyCollectListActivity.this, reportResponseBean.getMsg());
                        }
                    }
                });
            }
        });
    }

    private void bindDataToView(List<CollectBean> list) {

        adapter.loadMore(list);
    }
}
