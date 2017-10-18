package com.bixing.tiannews.Fragment;

import com.bixing.tiannews.R;
import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.adapter.NewsAdapter;
import com.bixing.tiannews.adapter.RecycleViewDivider;
import com.bixing.tiannews.bean.NewsResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sjw on 2017/9/24.
 */

public class ItemFragment extends BaseFragment {
    private String TAG = "ItemFragment";
    private String typeId;
    private XRecyclerView recyclerView;
    private NewsAdapter adapter;
    private int page = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DebugLog.d(TAG,"onViewCreated  "+typeTitle);
        initView(view);
         //getNetData();
    }

    private void initView(View view) {
        recyclerView = (XRecyclerView)view.findViewById(R.id.recylerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(getActivity());
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        DebugLog.d(TAG, "setUserVisibleHint " + typeTitle);
        if (isVisibleToUser && page == 1) {
            getNetData();
        }
    }

    private void getNetData() {
        DebugLog.d(TAG, "getNetData " + typeTitle + " typeId " + typeId);
        HttpManager.news(typeId, String.valueOf(page), "", new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                if (TextUtils.isEmpty(response)) {
                    return;
                }

                DebugLog.d(TAG, typeTitle + " response " + response);
                final NewsResponsBean responsBean = GsonManager.fromJsonStr(response, NewsResponsBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responsBean.isSucc()) {
                            if (responsBean.getData() != null) {
                                adapter.refreData(responsBean.getData());
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

    String typeTitle;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        typeId = args.getString("typeID");
        typeTitle = args.getString("typeTitle");
        DebugLog.d(TAG, "typeId " + typeId + " typeTitle " + typeTitle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
