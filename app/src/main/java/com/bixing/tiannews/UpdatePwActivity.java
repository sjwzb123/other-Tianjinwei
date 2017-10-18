package com.bixing.tiannews;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.bean.SMSCodeResponseBean;
import com.bixing.tiannews.bean.UserResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.SpfManger;
import com.bixing.tiannews.utils.ToastUtils;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sjw on 2017/9/28.
 */

public class UpdatePwActivity extends BaseActivity {
    private String TAG = "UpdatePwActivity";
    private EditText et_phone, et_pwd, et_pwd2;
    private Button btn_login;
    private Button btn_reg;
    private EditText et_code;
    private Button btn_get_code;

    @Override
    protected int getContviewId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        et_pwd2 = (EditText)findViewById(R.id.et_pwd2);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_reg = (Button)findViewById(R.id.btn_reg);
        et_code = (EditText)findViewById(R.id.et_code);
        btn_get_code = (Button)findViewById(R.id.btn_getcode);
        btn_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatePwActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void iniData() {

    }

    private void reg() {
        String pwd = et_pwd.getText().toString();
        String pwd2 = et_pwd2.getText().toString();
        String phone = et_phone.getText().toString();
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(code)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(pwd2)) {
            ToastUtils.toast(this, "密码不能为空");
            return;
        }
        if (!pwd.equals(pwd2)) {
            ToastUtils.toast(this, "两次输入的密码不一样");
            return;
        }
        HttpManager.updatePwd(SpfManger.getToken(this), code, pwd, new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response) {
                DebugLog.d(TAG, "response " + response);
                final UserResponsBean responsBean = GsonManager.fromJsonStr(response, UserResponsBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responsBean.isSucc()) {
                            finish();
                            ToastUtils.toast(UpdatePwActivity.this, responsBean.getMsg());

                        } else {
                            ToastUtils.toast(UpdatePwActivity.this, responsBean.getMsg());
                        }
                    }
                });

            }
        });
    }

    private void getCode() {
        String phone = et_phone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.toast(this, "电话号码不能为空");
            return;
        }
        HttpManager.getSmsCode(phone, "2", new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(final String response) {
                DebugLog.d(TAG, "response " + response);
                final SMSCodeResponseBean responseBean = GsonManager.fromJsonStr(response, SMSCodeResponseBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseBean.isSucc()) {
                            ToastUtils.toast(UpdatePwActivity.this, "验证码发送成功请注意查收！");
                        } else {
                            ToastUtils.toast(UpdatePwActivity.this, responseBean.getMsg());
                        }
                    }
                });
            }
        });
    }
}
