package com.bixing.tiannews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.R;
import com.bixing.tiannews.adapter.ServiceAdapter;
import com.bixing.tiannews.bean.ResponseServiceBean;
import com.bixing.tiannews.bean.ServiceBean;
import com.bixing.tiannews.bean.ServiceItemBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjw on 2017/9/24.
 */

public class ServFragment extends BaseFragment {
    private String TAG = "ServFragment";
    private List<ServiceItemBean> itemBeanList = new ArrayList<>();
    private ServiceAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_serv, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();

    }

    private void initView(View v) {
        recyclerView = (RecyclerView)v.findViewById(R.id.recylerview);
        manager = new GridLayoutManager(getContext(), 4);
        adapter = new ServiceAdapter(getContext());
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                DebugLog.d(TAG, "type " + type);
                return type == 0 ?4 : 1;

            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        getNetData();
    }

    private void getNetData() {
        HttpManager.service(new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                DebugLog.d(TAG, "response " + response);

                final ResponseServiceBean responseServiceBean = GsonManager.fromJsonStr(response, ResponseServiceBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseServiceBean.isSucc()) {
                            bindDataToView(responseServiceBean.getData());
                        } else {
                            ToastUtils.toast(getContext(), responseServiceBean.getMsg());
                        }
                    }
                });
            }
        });
    }

    private void bindDataToView(List<ServiceBean> list) {
        itemBeanList.clear();
        if (list != null && list.size() > 0) {
            for (ServiceBean serviceBean : list) {
                ServiceItemBean itemBean = new ServiceItemBean();
                itemBean.setTitle(serviceBean.getTitle());
                itemBean.setChild(false);
                itemBeanList.add(itemBean);
                if(serviceBean.getChild()!=null){
                    for (ServiceBean.Child child : serviceBean.getChild()) {
                        ServiceItemBean itemBeanChild = new ServiceItemBean();
                        itemBeanChild.setChild(true);
                        itemBeanChild.setUrl(child.getUrl());
                        itemBeanChild.setTitle(child.getTitle());
                        itemBeanChild.setIcon(child.getIcon());
                        itemBeanChild.setServiceId(child.getServiceId());
                        itemBeanList.add(itemBeanChild);
                    }
                }else {
                    ToastUtils.toast(getActivity(),"数据为空");
                }

            }
        }
        adapter.refreData(itemBeanList);
    }
}
