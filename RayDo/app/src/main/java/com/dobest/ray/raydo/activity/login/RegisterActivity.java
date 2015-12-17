package com.dobest.ray.raydo.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.BaseActivity;
import com.dobest.ray.raydo.activity.MainActivity;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.logicCache.cacheBaseLogic.LoginManager;
import com.dobest.ray.raydo.views.NavigationBar;

import carbon.widget.Button;

/**
 * Created by wangl01 on 2015/11/18.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,BaseLogic.NListener<BaseData> {
    private NavigationBar navbar_activity_reg;
    private Button btn_reg;

    private TextInputLayout layout_wrap_user_name;
    private TextInputLayout layout_wrap_user_password;
    private EditText et_username;
    private EditText et_password;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_register);
        findViews();
        init();
        addListeners();
    }

    @Override
    public void findViews() {
        navbar_activity_reg = (NavigationBar) findViewById(R.id.navbar_activity_reg);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        layout_wrap_user_name = (TextInputLayout) findViewById(R.id.layout_wrap_user_name);
        layout_wrap_user_password = (TextInputLayout) findViewById(R.id.layout_wrap_user_password);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void init() {
        navbar_activity_reg.setTitle("注册");
        navbar_activity_reg.setBackListener(this);

        manager=new LoginManager();
    }

    @Override
    public void addListeners() {
        btn_reg.setOnClickListener(this);
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
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    layout_wrap_user_name.setError("最少输入6个字符");
                } else {
                    layout_wrap_user_name.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back_operate:
                finish();
                break;
            case R.id.btn_reg:
                if (et_username.getText().toString().length()>=6&&et_password.getText().toString().length()>=6){
                    manager.doRegister(this,et_username.getText().toString(),et_password.getText().toString(),this);
                }else{
                    Snackbar.make(v, "请输入正确的账号密码", Snackbar.LENGTH_LONG).show();
                }
                break;

        }
    }

    @Override
    public void onResponse(BaseData data) {
        if (data.errorCode == 0) {
            Snackbar.make(navbar_activity_reg, "注册成功", Snackbar.LENGTH_LONG).show();
        } else {
            ToastMgr.show(data.msg);
        }
    }

    @Override
    public void onErrResponse(VolleyError error) {

    }

    @Override
    public void onAllPageLoaded(int nowPage, int totalPage) {

    }
}
