package com.bixing.tiannews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.R;
import com.bixing.tiannews.bean.BaseBean;
import com.bixing.tiannews.bean.CommonJson;
import com.bixing.tiannews.bean.CommonJson4List;
import com.bixing.tiannews.bean.NewsTypeBean;
import com.bixing.tiannews.bean.NewsTypeResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.viewpagerindicator.TabPageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by sjw on 2017/9/23.
 */

public class MainFragment extends BaseFragment {
    private String TAG = "MainFragment";
    private List<NewsTypeBean> itemList = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ViewPager pager;
    private TabPageIndicator indicator;
    private String[] defaultTab = { "推荐", "热点" };
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        // ViewPager的adapter
        adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
        pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // 实例化TabPageIndicator然后设置ViewPager与之关联
        indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        // 如果我们要对ViewPager设置监听，用indicator设置就行了
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Fragment fragment = fragments.get(arg0);
                if (fragment instanceof ItemFragment2) {
                    ItemFragment2 fragment2 = (ItemFragment2)fragment;
                    fragment2.setDataList(itemList.get(arg0).getChild());
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     * ViewPager适配器
     *
     * @author len
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 新建一个Fragment来展示ViewPager item的内容，并传递参数
            return fragments.get(position);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return itemList.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private void initData() {

        HttpManager.newsType(new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                DebugLog.d(TAG, "response " + response);
                NewsTypeResponsBean resBean = GsonManager.fromJsonStr(response, NewsTypeResponsBean.class);
                final List<NewsTypeBean> list = resBean.getData();
                DebugLog.d(TAG, "list " + list.size());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            bindDataToView(list);
                        }
                    }
                });
            }

        });
    }

    private void bindDataToView(List<NewsTypeBean> data) {
        itemList.clear();
        fragments.clear();
        for (int i = 0; i < defaultTab.length; i++) {
            NewsTypeBean bean = new NewsTypeBean();
            itemList.add(bean);
            bean.setTitle(defaultTab[i]);
            if (i == 0) {
                // bean.setTypeId("1");
            } else {
                // bean.setTypeId("2");
            }
        }
        itemList.addAll(data);
        for (NewsTypeBean bean : itemList) {
            if (bean.getChild() != null && bean.getChild().size() > 0) {
                ItemFragment2 itemFragment2 = new ItemFragment2();
                fragments.add(itemFragment2);

            } else {
                ItemFragment itemFragment = new ItemFragment();
                Bundle budle = new Bundle();
                budle.putString("typeID", bean.getTypeId());
                budle.putString("typeTitle", bean.getTitle());
                itemFragment.setArguments(budle);
                fragments.add(itemFragment);
            }
        }

        adapter.notifyDataSetChanged();
        indicator.notifyDataSetChanged();
        pager.setOffscreenPageLimit(itemList.size() - 1 < 0 ? 1 : itemList.size() - 1);
        DebugLog.d(TAG, "adapter " + adapter.getCount() + "size " + itemList.size());
    }
}
