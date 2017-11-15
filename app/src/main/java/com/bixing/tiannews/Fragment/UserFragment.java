package com.bixing.tiannews.Fragment;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.LoginActivity;
import com.bixing.tiannews.MyCollectListActivity;
import com.bixing.tiannews.MyReportActivity;
import com.bixing.tiannews.R;
import com.bixing.tiannews.UpdatePhoneActivity;
import com.bixing.tiannews.UpdatePwActivity;
import com.bixing.tiannews.bean.UploadFileResponseBean;
import com.bixing.tiannews.bean.UserBean;
import com.bixing.tiannews.bean.UserResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.SpfManger;
import com.bixing.tiannews.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by sjw on 2017/9/25.
 */

public class UserFragment extends BaseFragment {
    private ImageView iv_head;
    private TextView tv_nickName;
    private EditText et_nick;
    private ImageView iv_update;
    private Button btn_update;
    private UserBean userBean;
    private List<String> imgPaths = new ArrayList<>();
    private String TAG = "UserFragment";
    private LinearLayout ll_et;
    private Button btn_logout;
    private ScrollView ll_login;
    private TextView tv_no_login;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        userBean = SpfManger.getUser(getContext());
        if (userBean == null || !isLogin()) {
            ll_login.setVisibility(View.GONE);
            tv_no_login.setVisibility(View.VISIBLE);
            return;
        } else {
            ll_login.setVisibility(View.VISIBLE);
            tv_no_login.setVisibility(View.GONE);
        }
        initData();

    }

    private void initData() {
        if (!TextUtils.isEmpty(userBean.getNickName())) {
            tv_nickName.setText(userBean.getNickName());
            et_nick.setText(userBean.getNickName());
        } else {
            tv_nickName.setText("昵称");
        }
        if (!TextUtils.isEmpty(userBean.getAvatarUrl()))
            Glide.with(getContext()).load(userBean.getAvatarUrl()).into(iv_head);
        else {
            iv_head.setImageResource(R.drawable.myhead);
        }

    }

    private void initView(View view) {
        ll_login = (ScrollView)view.findViewById(R.id.ll_login);
        tv_no_login = (TextView)view.findViewById(R.id.tv_no_login);
        tv_no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ll_et = (LinearLayout)view.findViewById(R.id.ll_et);
        iv_head = (ImageView)view.findViewById(R.id.iv_head);
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });
        tv_nickName = (TextView)view.findViewById(R.id.tv_nick);
        et_nick = (EditText)view.findViewById(R.id.et_nick);
        iv_update = (ImageView)view.findViewById(R.id.iv_update);
        btn_update = (Button)view.findViewById(R.id.btn_ok);
        btn_logout = (Button)view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpfManger.clearAll(getContext());
                userBean = new UserBean();
                initData();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_et.setVisibility(View.VISIBLE);
                tv_nickName.setVisibility(View.GONE);
                iv_update.setVisibility(View.GONE);
                btn_update.setVisibility(View.VISIBLE);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_et.setVisibility(View.GONE);
                tv_nickName.setVisibility(View.VISIBLE);
                tv_nickName.setText(et_nick.getText().toString());
                iv_update.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.GONE);
                uploadHeadImg();

            }
        });
        view.findViewById(R.id.rl_update_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), UpdatePwActivity.class);
                    startActivity(intent);
                }

            }
        });
        view.findViewById(R.id.rl_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyReportActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.rl_update_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePhoneActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.rl_coll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCollectListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void selectPic() {
        PhotoPicker.builder()
                .setPhotoCount(1)
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
                // addImg(photos);
                setImg(photos);
            }
        }
    }

    private void setImg(List<String> photos) {
        imgPaths.clear();
        imgPaths.addAll(photos);
        Glide.with(getActivity()).load(photos.get(0)).into(iv_head);

    }

    private void uploadHeadImg() {
        HttpManager.uploadFile(imgPaths, SpfManger.getToken(getContext()), "headImg", new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(final String response) {
                if (TextUtils.isEmpty(response)){
                    ToastUtils.toast(getContext(), "信息异常");
                    return;
                }
                final UploadFileResponseBean responseBean = GsonManager.fromJsonStr(response, UploadFileResponseBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseBean != null && responseBean.isSucc()) {

                            updateUser(responseBean.getData().getImg0());
                        } else if (responseBean != null) {
                            ToastUtils.toast(getContext(), responseBean.getMsg());

                        } else {
                            ToastUtils.toast(getContext(), "信息异常");
                        }
                    }
                });
            }
        });
    }

    public void updateUser(String url) {
        String nickName = tv_nickName.getText().toString();

        HttpManager.updateUser(SpfManger.getToken(getContext()), url, nickName, new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                final UserResponsBean responsBean = GsonManager.fromJsonStr(response, UserResponsBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responsBean.isSucc()) {
                            ToastUtils.toast(getContext(), "用户资料更新成功");
                            SpfManger.saveUser(getContext(), responsBean.getData());
                        } else {
                            ToastUtils.toast(getContext(), responsBean.getMsg());
                        }
                    }
                });
                DebugLog.d(TAG, "response " + response);
            }
        });
    }
}
