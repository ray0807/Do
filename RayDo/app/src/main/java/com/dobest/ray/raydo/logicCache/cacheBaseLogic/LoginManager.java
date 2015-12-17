package com.dobest.ray.raydo.logicCache.cacheBaseLogic;

import android.content.Context;

import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.constants.Urls;
import com.dobest.ray.raydo.logic.BaseManager;
import com.dobest.ray.raydo.logicCache.BaseManagerCache;


public class LoginManager extends BaseManagerCache {

    public void doLogin(Context c, String name, String password,
                        BaseLogic.NListener<BaseData> l) {

        logic.edit(c).addParam("name", name).addParam("password", password)
                .setUrl(Urls.LOGIN_URL).setListener(l)
                .setBaseClass(BaseData.class);
        logic.doPost();
    }


    public void doRegister(Context c, String name, String password,
                           BaseLogic.NListener<BaseData> l) {
        logic.edit(c).addParam("name", name).addParam("password", password)
                .setUrl(Urls.REGISTER_URL).setListener(l)
                .setBaseClass(BaseData.class);
        logic.doPost();
    }
}
