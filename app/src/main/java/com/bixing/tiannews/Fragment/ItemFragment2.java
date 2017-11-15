package com.bixing.tiannews.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.bixing.tiannews.R;
import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.bean.NewsBean;
import com.bixing.tiannews.bean.NewsTypeBean;
import com.bixing.tiannews.bean.NewsTypeResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.widget.NewsListView;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by sjw on 2017/9/23.
 */

public class ItemFragment2 extends BaseFragment {
    private String TAG = "ItemFragment2";
    private List<NewsTypeBean> itemList = new ArrayList<>();
    private PagerAdapter adapter;
    private ViewPager pager;
    // private TabPageIndicator indicator;
    private RadioGroup rg;
    private List<NewsListView> fragments = new ArrayList<>();
    private int rbid = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DebugLog.d(TAG, "onCreateView--------------");
        return inflater.inflate(R.layout.fragment_item2, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DebugLog.d(TAG, "onViewCreated--------------");
        initView(view);
        initData();
    }

    private void initView(View view) {
        // ViewPager的adapter
        adapter = new TabPageIndicatorAdapter();
        pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        rg = (RadioGroup)view.findViewById(R.id.rg);
        rbid = 0;
        if (itemList != null && itemList.size() > 0) {
            for (NewsTypeBean bean : itemList) {
                addTitle(bean.getTitle());
            }
        }
        // 实例化TabPageIndicator然后设置ViewPager与之关联
        // indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        // indicator.setViewPager(pager);
        pager.setOffscreenPageLimit(fragments.size() - 1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (adapter.getCount() > 0) {
                    pager.setCurrentItem(checkedId);
                }

            }
        });
        // 如果我们要对ViewPager设置监听，用indicator设置就行了
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                RadioButton rb = (RadioButton)rg.getChildAt(arg0);
                if (rb != null) {
                    rb.setChecked(true);
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
    class TabPageIndicatorAdapter extends PagerAdapter {
        /*
         * 使用 pagerAdapter 注意几点：
         * 重写方法的时候是 含有 ViewGroup的方法 :
         * 适合用在 使用 layout 布局实现
         * instantiateItem(ViewGroup container, int position)
         * destroyItem(ViewGroup container, int position, Object object)
         * 
         */

        public TabPageIndicatorAdapter() {

        }

        /**
         * 返回 页卡 的数量
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }

        /**
         * 判断 是 view 是否来自对象
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        /**
         * 实例化 一个 页卡
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 添加一个 页卡

            container.addView(fragments.get(position));

            return fragments.get(position);
        }

        /**
         * 销毁 一个 页卡
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 删除
            container.removeView(fragments.get(position));
        }

        /**
         * 重写 标题的 方法
         */
        @Override
        public CharSequence getPageTitle(int position) {
            // 给页面添加标题
            return itemList.get(position).getTitle();
        }
    }

    private void initData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    public void setDataList(List<NewsTypeBean> data) {
        DebugLog.d(TAG, "data " + data.size());
        itemList.clear();
        rbid = 0;
        if (rg != null) {
            rg.removeAllViews();
        }

        itemList.addAll(data);
        fragments.clear();
        if (data == null||data.size()==0) {
            return;
        }
        for (NewsTypeBean bean : data) {
            NewsListView itemFragment = new NewsListView(getContext());
            itemFragment.setNewsTypeBean(bean);
            fragments.add(itemFragment);

        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            // indicator.notifyDataSetChanged();
        }
        for (NewsTypeBean bean : data) {

            if (rg != null) {
                addTitle(bean.getTitle());

            }

        }

    }

    private void addTitle(String title) {

        RadioButton rb = (RadioButton)LayoutInflater.from(getContext()).inflate(R.layout.rb_item, null);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        rb.setLayoutParams(params);
        rb.setText(title);
        if (rbid == 0) {
            rb.setChecked(true);
        }
        params.setMargins(10, 0, 20, 0);
        rb.setId(rbid++);
        rg.addView(rb);
    }

}
