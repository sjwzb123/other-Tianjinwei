package com.bixing.tiannews;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.adapter.NewsAdapter;
import com.bixing.tiannews.adapter.RecycleViewDivider;
import com.bixing.tiannews.bean.NewsResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by sjw on 2017/9/28.
 */

public class SearchActivity extends BaseActivity {
    private EditText et_key;
    private TextView tv_search;
    private int page = 1;
    private String TAG = "SearchActivity";
    private XRecyclerView recyclerView;
    private NewsAdapter adapter;

    @Override
    protected int getContviewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        et_key = (EditText)findViewById(R.id.et_key);
        tv_search = (TextView)findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSerchData();
            }
        });
        recyclerView = (XRecyclerView)findViewById(R.id.recylerview);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void iniData() {

    }

    private void getSerchData() {
        String searchKey = et_key.getText().toString();
        HttpManager.news("", String.valueOf(page), searchKey, new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(final String response) {
                DebugLog.d(TAG, "response " + response);
                final NewsResponsBean responsBean = GsonManager.fromJsonStr(response, NewsResponsBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responsBean.isSucc()) {
                            ToastUtils.toast(SearchActivity.this, responsBean.getMsg());
                            if (adapter != null && responsBean.getData() != null) {
                                adapter.loadMore(responsBean.getData());
                            }
                        } else {
                            ToastUtils.toast(SearchActivity.this, responsBean.getMsg());
                        }
                    }
                });
            }
        });
    }
}
