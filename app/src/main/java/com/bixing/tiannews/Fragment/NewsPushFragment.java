package com.bixing.tiannews.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.bixing.tiannews.LoginActivity;
import com.bixing.tiannews.R;
import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.bean.ReportResponseBean;
import com.bixing.tiannews.bean.UploadFileBean;
import com.bixing.tiannews.bean.UploadFileResponseBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.SpfManger;
import com.bixing.tiannews.utils.ToastUtils;
import com.bixing.tiannews.widget.IvItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by sjw on 2017/9/25.
 */

public class NewsPushFragment extends BaseFragment {
    private String TAG = "NewsPushFragment";
    private EditText et_title;
    private EditText et_content;
    private EditText et_phone;
    private ImageView iv_add;
    private LinearLayout ll_add;
    private EditText et_addr;
    private Button btn_push, btn_cancle;
    private LinearLayout ll_login;
    private TextView tv_no_login;
    private List<String> files = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_push, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        iv_add = (ImageView)view.findViewById(R.id.iv_add);
        et_title = (EditText)view.findViewById(R.id.et_title);
        et_addr = (EditText)view.findViewById(R.id.et_addr);
        et_content = (EditText)view.findViewById(R.id.et_content);
        et_phone = (EditText)view.findViewById(R.id.et_phone);
        iv_add = (ImageView)view.findViewById(R.id.iv_add);
        ll_add = (LinearLayout)view.findViewById(R.id.ll_img);
        btn_cancle = (Button)view.findViewById(R.id.btn_cancle);
        btn_push = (Button)view.findViewById(R.id.btn_push);
        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add.removeAllViews();
                ll_add.addView(iv_add);
            }
        });
        ll_login = (LinearLayout)view.findViewById(R.id.ll_login);
        tv_no_login = (TextView)view.findViewById(R.id.tv_no_login);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });
        tv_no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        showViewByStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        showViewByStatus();
    }

    private void selectPic() {

        PhotoPicker.builder()
                .setPhotoCount(4 - ll_add.getChildCount())
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(true)
                .start(getActivity(), this, PhotoPicker.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                addImg(photos);

            }
        }
    }

    private void addImg(List<String> photos) {
        // ll_add.removeAllViews();

        files.clear();
        files.addAll(photos);
        for (String s : photos) {
            DebugLog.d(TAG, "photo " + s);
            IvItem ivItem = new IvItem(getContext());
            ivItem.setSelectImgPath(s);
            ll_add.addView(ivItem);
        }

    }

    private void showViewByStatus() {
        if (isLogin()) {
            ll_login.setVisibility(View.VISIBLE);
            tv_no_login.setVisibility(View.GONE);
        } else {
            ll_login.setVisibility(View.GONE);
            tv_no_login.setVisibility(View.VISIBLE);
        }
    }

    private void uploadFile() {
        HttpManager.uploadFile(files, SpfManger.getToken(getActivity()), "reportImg", new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                DebugLog.d(TAG, "response " + response);
                final UploadFileResponseBean responseBean = GsonManager.fromJsonStr(response, UploadFileResponseBean.class);
                if (responseBean.isSucc()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reportNews(responseBean.getData());
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.toast(getContext(), "爆料失败");
                        }
                    });
                }
            }
        });

    }

    private void reportNews(UploadFileBean bean) {
        String[] imags = new String[3];
        imags[0] = bean.getImg0();
        imags[1] = bean.getImg1();
        imags[2] = bean.getImg2();
        String title = et_title.getText().toString();
        String addr = et_addr.getText().toString();
        String content = et_content.getText().toString();
        String phone = et_phone.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(addr) || TextUtils.isEmpty(content)) {
            ToastUtils.toast(getActivity(), "信息不完整");
            return;
        } ;
        List<String> list=new ArrayList<>();
        if (!TextUtils.isEmpty(bean.getImg0())){
            list.add(bean.getImg0());

        }
        if (!TextUtils.isEmpty(bean.getImg1())){
            list.add(bean.getImg1());


        } if (!TextUtils.isEmpty(bean.getImg2())){
            list.add(bean.getImg2());
        }



        HttpManager.report(SpfManger.getToken(getActivity()), title, content, phone, list, addr, new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(final String response) {
                final ReportResponseBean responseBean = GsonManager.fromJsonStr(response, ReportResponseBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseBean.isSucc()) {
                            ToastUtils.toast(getActivity(), responseBean.getMsg());
                        } else {
                            ToastUtils.toast(getActivity(), responseBean.getMsg());
                        }
                    }
                });
                DebugLog.d(TAG, "response " + response);
            }
        });
    }
}
