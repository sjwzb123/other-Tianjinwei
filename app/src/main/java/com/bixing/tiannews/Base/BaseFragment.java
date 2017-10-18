package com.bixing.tiannews.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bixing.tiannews.R;
import com.bixing.tiannews.utils.SpfManger;

/**
 * Created by sjw on 2017/9/23.
 */

public class BaseFragment extends Fragment{
    private String title;
    private int iconId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    public boolean isLogin() {
        String token = SpfManger.getToken(getActivity());
        return !TextUtils.isEmpty(token);
    }


}
