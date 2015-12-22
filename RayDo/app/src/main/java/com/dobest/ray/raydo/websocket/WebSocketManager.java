package com.dobest.ray.raydo.websocket;

import android.util.Log;

import com.dobest.ray.corelibs.utils.ToastMgr;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by wangl01 on 2015/12/22.
 */
public class WebSocketManager {
    private final WebSocketConnection mConnection = new WebSocketConnection();

    private void start(String name) {
        //测试数据
        final String wsuri = "ws://114.215.94.193:8088/ws/join?uname=" + name;

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

    public void closeWebSocket() {
        if (mConnection.isConnected()) {
            mConnection.disconnect();
        }
    }

    public void sendMessage(String content) {
        mConnection.sendTextMessage(content);
    }

    public void startWebSocket(String name) {
        start(name);
    }
}
