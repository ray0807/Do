package com.dobest.ray.raydo.activity.chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.R;

import carbon.widget.Button;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private final WebSocketConnection mConnection = new WebSocketConnection();
    private carbon.widget.EditText et_content;
    private carbon.widget.EditText et_name;
    private carbon.widget.Button btn_send;
    private carbon.widget.Button btn_bind;
    /**
     * activity 实例
     */
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        init();
        addListeners();
    }

    private void findViews(View view) {
        et_content = (carbon.widget.EditText) view.findViewById(R.id.et_content);
        et_name = (carbon.widget.EditText) view.findViewById(R.id.et_name);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_bind = (Button) view.findViewById(R.id.btn_bind);
    }

    private void init() {

    }

    private void addListeners() {
        btn_send.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mConnection.isConnected()) {
            mConnection.disconnect();
        }
    }

    private void start() {
        //测试数据
        final String wsuri = "ws://114.215.94.193:8088/ws/join?uname=" + et_name.getText().toString().trim();

        try {
            mConnection.connect(wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d("wanglei", "Status: Connected to " + wsuri);

                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d("wanglei", "Got echo: " + payload);
                    ToastMgr.show("收到消息：" + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d("wanglei", "Connection lost.");
                }
            });
        } catch (WebSocketException e) {

            Log.d("wanglei", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String content = et_content.getText().toString();
                if (content.length() == 0)
                    return;
                mConnection.sendTextMessage(content);
                break;
            case R.id.btn_bind:
                //应该是初始化的时候链接服务器 但是为了测试数据
                if (et_name.getText().toString().trim().length()==0){
                    ToastMgr.show("你应该先填写发送人姓名");
                    return;
                }
                start();
                break;
        }
    }
}
