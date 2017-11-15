package com.bixing.tiannews.http;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bixing.tiannews.utils.DebugLog;
import com.bixing.tiannews.utils.SpfManger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.id.list;

/**
 * Created by sjw on 2017/9/18.
 */

public class HttpManager {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static String TAG = "HttpManager";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static void getSmsCode(String phone, String smsType, HttpCallBack callBack) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("phone", phone);
            jsonObject.put("smsType", smsType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.getSmsCode, callBack);

    }

    public static void myReport(String token, String page, HttpCallBack c) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.myReport, c);
    }

    /**
     * token
     * phone
     * smsCode
     */
    public static void updatePhone(String token, String phone, String smsCode, HttpCallBack c) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("phone", phone);
            jsonObject.put("smsCode", smsCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        request(jsonObject.toString(), HttpConfig.updatePhone, c);
    }

    public static void reg(String phone, String password, String smsType, String smsCode, String token, HttpCallBack callBack) {
        JSONObject params = new JSONObject();
        try {

            params.put("phone", phone);
            params.put("password", password);
            params.put("smsType", smsType);
            params.put("smsCode", smsCode);
            params.put("token", token);

        } catch (Exception e) {

        }
        request(params.toString(), HttpConfig.reg, callBack);

    }

    public static void service(HttpCallBack callBack) {
        request("{}", HttpConfig.service, callBack);
    }

    /**
     * phone
     * password
     * smsCode
     */
    public static void acLogin(String phone, String password, HttpCallBack callBack) {
        JSONObject map = new JSONObject();
        try {
            map.put("phone", phone);
            map.put("password", password);
        } catch (Exception e) {

        }
        request(map.toString(), HttpConfig.acLogin, callBack);
    }

    public static void uploadFile(List<String> list, String token, String fileType, final HttpCallBack callBack) {
        int i = 0;
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (String path : list) {
            File f = new File(path);
            builder.addFormDataPart("img" + i++, f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
        }
        // 添加其它信息
        builder.addFormDataPart("token", token);
        builder.addFormDataPart("fileType", fileType);
        MultipartBody requestBody = builder.build();
        // 构建请求
        Request request = new Request.Builder()
                .url(HttpConfig.uploadFile)// 地址
                .post(requestBody)// 添加请求体
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure("1", e.getMessage().toString());
                }
                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack != null) {
                    callBack.onSucc(response.body().string());
                }
                // System.out.println("上传照片成功：response = " + response.body().string());

            }
        });

    }

    public static void wxOpenId(String code) {
        JSONObject params = new JSONObject();
        try {
            params.put("code", code);
        } catch (Exception e) {

        }
        request(params.toString(), HttpConfig.wxOpenId);

    }

    /**
     * openId
     * nickName
     * avatarUrl
     * city
     * country
     * gender
     * language
     * province
     * token
     */
    public static void wxReg(String openId, String nickName, String acatarUrl, String city, String country, String gender, String language, String province, String token) {
        JSONObject map = new JSONObject();
        try {
            map.put("openId", openId);
            map.put("nickName", nickName);
            map.put("acatarUrl", acatarUrl);
            map.put("city", city);
            map.put("country", country);
            map.put("gender", gender);
            map.put("language", language);
            map.put("province", province);
            map.put("token", token);
        } catch (Exception e) {

        }

        request(map.toString(), HttpConfig.wxReg);
    }

    /**
     * openId
     */
    public static void wxLogin(String openId) {
        JSONObject map = new JSONObject();
        try {
            map.put("openId", openId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(map.toString(), HttpConfig.wxLogin);
    }

    /***
     * token
     * avatarUrl
     * nickName
     */
    public static void updateUser(String token, String avatarUrl, String nickName, HttpCallBack callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("avatarUrl", avatarUrl);
            jsonObject.put("nickName", nickName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.updateUser, callBack);
    }

    /**
     * token
     * password
     * newPassword
     */
    public static void updatePwd(String token, String code, String newPassword, HttpCallBack callBack) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("password", newPassword);
            jsonObject.put("smsCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.updatePwd, callBack);
    }

    /**
     * token
     * title
     * content
     * phone
     * imgPaths
     * addr
     */
    public static void report(String token, String title, String content, String phone, List<String> imgPaths, String addr, HttpCallBack callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("title", title);
            jsonObject.put("content", content);
            jsonObject.put("phone", phone);
            JSONArray jsonArray = new JSONArray(imgPaths);
            jsonObject.put("imgPaths", jsonArray);
            jsonObject.put("addr", addr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.report, callBack);
    }

    public static void newsType(HttpCallBack callBack) {
        request("", HttpConfig.newsType, callBack);
    }

    public static void news(String typeId, String page, String searchKey, HttpCallBack callBack) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("fixedNav", "1");
            jsonObject.put("search", searchKey);
            jsonObject.put("typeId", typeId);
            jsonObject.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.news, callBack);
    }

    private static void request(String json, String url, final HttpCallBack callBack) {
        RequestBody body = RequestBody.create(JSON, json);
        DebugLog.d(TAG, "request " + " json = " + json);
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DebugLog.d(TAG, "onFailure : " + e.toString());
                if (callBack != null) {
                    callBack.onFailure("1", e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // DebugLog.d(TAG, "onResponse : body " + response.body().string());
                if (callBack != null) {
                    callBack.onSucc(response.body().string());
                }

                // try {
                // JSONObject obj = new JSONObject(response.body().string());
                // DebugLog.d(TAG, "response " + obj.toString());
                // } catch (JSONException e) {
                // e.printStackTrace();
                // }
            }
        });
    }

    public static void newsConfig(HttpCallBack callBack) {
        request("", HttpConfig.newsConfig, callBack);
    }

    public static void collectList(String token, String page, HttpCallBack callBack) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", page);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), HttpConfig.collectList,callBack);
    }

    private static void notifyToMainUIThread(HttpCallBack callBack, Class c) {

    }

    private static void request(String json, String url) {
        RequestBody body = RequestBody.create(JSON, json);
        DebugLog.d(TAG, "request " + " json = " + json);
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DebugLog.d(TAG, "onFailure : " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DebugLog.d(TAG, "onResponse : " + response.toString() + " body " + response.body().string());
                // try {
                // JSONObject obj = new JSONObject(response.body().string());
                // DebugLog.d(TAG, "response " + obj.toString());
                // } catch (JSONException e) {
                // e.printStackTrace();
                // }
            }
        });
    }

}
