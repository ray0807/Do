package com.dobest.ray.raydo.utils;

import android.content.Context;

import com.dobest.ray.corelibs.cache.ACache;

/**
 * Created by wangl01 on 2015/11/24.
 * 缓存说明
 */
public class CacheDesc {
    private static CacheDesc instance;

    private CacheDesc() {

    }

    public static CacheDesc getInstance() {
        if (instance == null) {
            instance = new CacheDesc();
        }
        return instance;
    }

    /**
     * 设置头像缓存
     *
     * @param context
     * @param ImageUrl
     * @return
     */
    public void saveImageUrl(Context context, String ImageUrl) {
        ACache.get(context).put("ImageUrl", ImageUrl);
    }

    /**
     * 获取图片Url
     *
     * @param context
     * @param ImageUrl
     * @return
     */
    public String getImageUrl(Context context, String ImageUrl) {
        return ACache.get(context).getAsString("ImageUrl");
    }

    /**
     * 清除所有数据
     * @param context
     */
    public void clear(Context context) {
        ACache.get(context).clear();
    }
}
