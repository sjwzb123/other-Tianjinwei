package com.bixing.tiannews;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.bean.ReportBean;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.DisplayUtils;
import com.bumptech.glide.Glide;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sjw on 2017/10/12.
 */

public class ReportDetailActivtiy extends BaseActivity {
    private TextView tv_title, tv_time, tv_phone, tv_addr, tv_content, tv_status;
    private ReportBean bean;
    private LinearLayout ll_img;
    private TextView tv_msg;

    @Override
    protected int getContviewId() {
        return R.layout.activity_report_detail;
    }

    @Override
    protected void initView() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_addr = (TextView)findViewById(R.id.tv_addr);
        tv_content = (TextView)findViewById(R.id.tv_content);
        ll_img = (LinearLayout)findViewById(R.id.ll_img);
        tv_status = (TextView)findViewById(R.id.tv_status);
        tv_msg = (TextView)findViewById(R.id.tv_mssage);

    }

    @Override
    protected void iniData() {
        bean = (ReportBean)getIntent().getSerializableExtra("reportBean");
        tv_addr.setText("地点：" + bean.getAddr());
        tv_content.setText("内容：" + bean.getContent());
        tv_phone.setText("联系方式：" + bean.getPhone());
        tv_title.setText(bean.getTitle());
        tv_time.setText("时间：" + bean.getCreateTime());
        tv_status.setText(bean.getStatus());
        tv_msg.setText(bean.getMsg());
        DebugLog.d("initData", "size " + bean.getImg());
        for (String imgPath : bean.getImg()) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtils.getW(this) - 40, 500));
            Glide.with(this).load(imgPath).into(imageView);
            DebugLog.d("imgPath", imgPath);
            ll_img.addView(imageView);

        }

    }
}
