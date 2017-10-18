package com.bixing.tiannews;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.bean.UserBean;
import com.bixing.tiannews.bean.UserResponsBean;
import com.bixing.tiannews.http.HttpCallBack;
import com.bixing.tiannews.http.HttpManager;
import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.GsonManager;
import com.bixing.tiannews.utils.SpfManger;
import com.bixing.tiannews.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by sjw on 2017/9/25.
 */

public class LoginActivity extends BaseActivity {
    private EditText et_phone;
    private EditText et_pwd;
    private Button btn_login;
    private Button btn_reg;
    private String TAG = "LoginActivity";
    private String WX_APP_ID = "wx81a7f636c98f032a";

    @Override
    protected int getContviewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_reg = (Button)findViewById(R.id.btn_reg);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.btn_wixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXLogin();
            }
        });

    }

    @Override
    protected void iniData() {

    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        IWXAPI WXapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        WXapi.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "biyingzhilian";
        WXapi.sendReq(req);

    }

    private void login() {
        String name = et_phone.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            ToastUtils.toast(this, "账号或密码不能为空");
            return;
        }
        HttpManager.acLogin(name, pwd, new HttpCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(final String response) {
                DebugLog.d(TAG, "response " + response);
                final UserResponsBean responsBean = GsonManager.fromJsonStr(response, UserResponsBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responsBean.isSucc()) {
                            UserBean bean = responsBean.getData();
                            ToastUtils.toast(LoginActivity.this, "登录成功");
                            SpfManger.saveUser(LoginActivity.this, bean);
                            SpfManger.saveToken(LoginActivity.this, responsBean.getToken());
                            finish();

                        } else {
                            ToastUtils.toast(LoginActivity.this, responsBean.getMsg());
                        }
                    }
                });

            }
        });
    }
}
