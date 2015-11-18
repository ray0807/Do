package com.dobest.ray.raydo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.corelibs.utils.Tools;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.bean.childBean.LoginBean;
import com.dobest.ray.raydo.logic.baseLogic.HotNewsManager;
import com.dobest.ray.raydo.logicCache.cacheBaseLogic.LoginManager;

/**
 * Created by wangl01 on 2015/11/13.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, BaseLogic.NListener<BaseData> {
    private TextInputLayout layout_wrap_user_name;
    private TextInputLayout layout_wrap_user_password;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_reg;
    private LoginManager manager;
    private LoginBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_login);
        findViews();
        init();
        addListeners();
    }

    @Override
    public void findViews() {
        layout_wrap_user_name = (TextInputLayout) findViewById(R.id.layout_wrap_user_name);
        layout_wrap_user_password = (TextInputLayout) findViewById(R.id.layout_wrap_user_password);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void init() {
        manager = new LoginManager();

        bean = new LoginBean();
    }

    @Override
    public void addListeners() {
        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    layout_wrap_user_password.setError("最少输入6个字符");
                } else {
                    layout_wrap_user_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        manager.doLogin(this,this);
        Intent it = null;
        switch (v.getId()) {
            case R.id.btn_login:
                bean.name = et_username.getText().toString().trim();
                bean.password = et_password.getText().toString().trim();
                if (Tools.validatePhone(bean.name)) {
                    if (Tools.validateLoginPassWord(bean.password)) {
//                        manager.doLogin(this,bean.name,bean.password,this);
                    } else {
                        ToastMgr.show("请填写有效密码");
                    }

                } else {
                    ToastMgr.show("请填写有效账户");
                }

                break;
            case R.id.btn_reg:
                showToast("不用注册了，账号是 15971470520 ，密码是：111111");
                break;

        }
        if (it != null) {
            startActivity(it);
        }
    }


    @Override
    public void onResponse(BaseData data) {

    }

    @Override
    public void onErrResponse(VolleyError error) {

    }

    @Override
    public void onAllPageLoaded(int nowPage, int totalPage) {

    }
}
