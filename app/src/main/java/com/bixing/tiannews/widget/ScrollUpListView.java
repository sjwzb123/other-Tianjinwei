package com.bixing.tiannews.widget;

import java.util.ArrayList;
import java.util.List;

import com.bixing.tiannews.R;
import com.bixing.tiannews.WebActivity;
import com.bixing.tiannews.bean.horseBean;
import com.bixing.tiannews.utils.SpfManger;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.attr.animation;

/**
 * Created by sjw on 2017/11/14.
 */

public class ScrollUpListView extends ListView {
    private List<horseBean> list = new ArrayList<>();
    private ScrollAdapter adapter;
    private int index;
    private boolean isStart;
    private View lastView;
    public ScrollUpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        adapter = new ScrollAdapter();
        setAdapter(adapter);
        startScroll();
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("title",list.get(position%list.size()).getTitle());
                intent.putExtra("isShare", true);
                intent.putExtra("url", list.get(position%list.size()).getDetailUrl() + "/" + SpfManger.getToken(getContext()));
                getContext().startActivity(intent);
            }
        });
    }

    public void startScroll() {

        postDelayed(new Runnable() {
            @Override
            public void run() {
                postDelayed(this, 2000);

//                if (index++ >= list.size()) {
//                    index = 0;
//                }
                index++;
                if (list.size() > 0) {
                    smoothScrollToPositionFromTop(index,60);
                    // setSelection(index++);
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setSelection(index);
                        }
                    },230);
                }


            }
        }, 2000);
    }

    public void setDataList(List<horseBean> dataList) {
        list.clear();
        if (dataList != null) {
            list.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
    }

    class ScrollAdapter extends BaseAdapter {
        Animation animation;
        Animation animation2;
        ScrollAdapter(){
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.trans);
            animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.transhide);
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (lastView!=null){
                // lastView.startAnimation(animation2);
            }
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null); // mContext指的是调用的Activtty
                holder.tv = (TextView)convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tv.setText(list.get(position%list.size()).getTitle());
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            // convertView.startAnimation(animation);
            lastView=convertView;

            return convertView;
        }

    }

    class ViewHolder {
        TextView tv;
    }

}
