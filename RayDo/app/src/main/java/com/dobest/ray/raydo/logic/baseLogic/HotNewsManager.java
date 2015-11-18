package com.dobest.ray.raydo.logic.baseLogic;

import android.content.Context;

import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.constants.Urls;
import com.dobest.ray.raydo.logic.BaseManager;


public class HotNewsManager extends BaseManager {

    public void doLogin(
            BaseLogic.NListener<BaseData> l) {

        logic.edit().addParam("name", "ray").addParam("password", "111111")
                .setUrl(Urls.LOGIN_URL).setListener(l)
                .setBaseClass(BaseData.class);
        logic.doPost();
    }


}
