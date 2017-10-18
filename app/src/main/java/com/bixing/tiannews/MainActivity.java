package com.bixing.tiannews;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.Fragment.MainFragment;
import com.bixing.tiannews.Fragment.NewsPushFragment;
import com.bixing.tiannews.Fragment.ReadNewsFragment;
import com.bixing.tiannews.Fragment.ServFragment;
import com.bixing.tiannews.Fragment.UserFragment;
import com.githang.viewpagerindicator.IconPagerAdapter;
import com.githang.viewpagerindicator.IconTabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjw on 2017/9/23.
 */

public class MainActivity extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private ViewPager mViewPager;
    private IconTabPageIndicator mIndicator;
    private LinearLayout ll_top;
    private RelativeLayout rl_top;
    private TextView tv_title;
    private String[] titles = {"天津卫", "服务", "读报", "报料", "我的"};

    private List<BaseFragment> initFragments() {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();

        BaseFragment mainFragment = new MainFragment();
        mainFragment.setTitle("首页");
        mainFragment.setIconId(R.drawable.tab_img_select);
        fragments.add(mainFragment);

        ServFragment servFragment = new ServFragment();
        servFragment.setTitle("服务");
        servFragment.setIconId(R.drawable.tab_img_fuwu_select);
        fragments.add(servFragment);

        ReadNewsFragment readNewsFragment = new ReadNewsFragment();
        readNewsFragment.setTitle("读报");
        readNewsFragment.setIconId(R.drawable.tab_img_dubao_select);
        fragments.add(readNewsFragment);

        NewsPushFragment newsPushFragment = new NewsPushFragment();
        newsPushFragment.setTitle("报料");
        newsPushFragment.setIconId(R.drawable.tab_img_baoliao_select);
        fragments.add(newsPushFragment);
        UserFragment userFragment = new UserFragment();
        userFragment.setTitle("我的");
        userFragment.setIconId(R.drawable.tab_img_wode_select);
        fragments.add(userFragment);
        return fragments;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected int getContviewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        askPermisson();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);
        List<BaseFragment> fragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3 || position == 4) {
                    if (!isLogin()) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }
                }
                if (position == 0) {
                    ll_top.setVisibility(View.VISIBLE);
                    rl_top.setVisibility(View.GONE);
                } else {
                    ll_top.setVisibility(View.GONE);
                    rl_top.setVisibility(View.VISIBLE);
                }
                tv_title.setText(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ll_top = (LinearLayout) findViewById(R.id.ll_top);
        rl_top = (RelativeLayout) findViewById(R.id.ll_top1);
        tv_title = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void askPermisson() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            //
        }
    }

    @Override
    protected void iniData() {

    }

    class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        private List<BaseFragment> mFragments;

        public FragmentAdapter(List<BaseFragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getIconResId(int index) {
            return mFragments.get(index).getIconId();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }
    }
}
