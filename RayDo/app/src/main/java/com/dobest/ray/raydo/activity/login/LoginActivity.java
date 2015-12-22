package com.dobest.ray.raydo.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.dobest.ray.corelibs.cache.ACache;
import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.corelibs.utils.Tools;
import com.dobest.ray.raydo.App;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.BaseActivity;
import com.dobest.ray.raydo.activity.MainActivity;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.bean.childBean.LoginAccount;
import com.dobest.ray.raydo.bean.childBean.LoginBean;
import com.dobest.ray.raydo.logicCache.cacheBaseLogic.LoginManager;

import carbon.widget.Button;

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
    private LoginAccount Accounts;

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

        Intent it = null;
        switch (v.getId()) {
            case R.id.btn_login:
                if (App.getInstance().isDebug) {
                    it = new Intent(LoginActivity.this, MainActivity.class);
//                    jumpActivity(it);
                } else {
                    bean.name = et_username.getText().toString().trim();
                    bean.password = et_password.getText().toString().trim();
                    if (Tools.validatePhone(bean.name)) {
                        if (Tools.validateLoginPassWord(bean.password)) {
                            loadingDialog.show();
                            manager.doLogin(this, bean.name, bean.password, this);
                        } else {
                            ToastMgr.show("请填写有效密码");
                        }

                    } else {
                        ToastMgr.show("请填写有效账户");
                    }
                }
                break;
            case R.id.btn_reg:
                it = new Intent(this, RegisterActivity.class);
                break;

        }
        if (it != null) {
            startActivity(it);
        }
    }


    @Override
    public void onResponse(BaseData data) {
        if (data.errorCode == 0) {
            Accounts = data.result.Accounts;
            if (Accounts != null) {
                App.getInstance().startWebSocket(Accounts.Account);
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                jumpActivity(it);

            }
        } else {
            ToastMgr.show(data.msg);
        }
        loadingDialog.dismiss();
    }


    @Override
    public void onErrResponse(VolleyError error) {
        loadingDialog.dismiss();
    }

    @Override
    public void onAllPageLoaded(int nowPage, int totalPage) {

    }

    private void jumpActivity(Intent it) {
        if (it != null) {
            startActivity(it);
        }
    }
}
