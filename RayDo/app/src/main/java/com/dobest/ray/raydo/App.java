package com.dobest.ray.raydo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dobest.ray.corelibs.Configuration;
import com.dobest.ray.corelibs.cache.BitmapLruCache;
import com.dobest.ray.corelibs.cache.DataCache;
import com.dobest.ray.corelibs.http.RequestManager;
import com.dobest.ray.corelibs.utils.MethodsCompat;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.Interface.LocationCallBack;
import com.dobest.ray.raydo.Interface.MessageFriendLatLogCallBack;
import com.dobest.ray.raydo.bean.MessageBean;
import com.dobest.ray.raydo.constants.Paths;
import com.dobest.ray.raydo.utils.CustomCrashHandler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;


public class App extends Application {

    static App instance;


    public final boolean isDebug = false;// 是否为调试模式
    public ImageLoader mImageLoader;
    public DataCache dataCache;
    public DataCache userCache;

    public String[] floorNos;

    public String[] getFloorNos() {
        return floorNos;
    }

    public void setFloorNos(String[] floorNos) {
        this.floorNos = floorNos;
    }

    private final int RATE = 8; // 默认分配最大空间的几分之一


    /**
     * 测试模式
     */
    public static boolean debug = true;


    /**
     * 是否打开水印
     */
    private boolean isOpenWaterMark = true;

    public boolean getIsOpenWaterMark() {
        return isOpenWaterMark;
    }

    public App() {
        instance = this;
    }

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Configuration.setShowNetworkJson(true);
        Configuration.setShowNetworkParams(true);
        RequestManager.init(this, Paths.HttpCache);
        // 确定在LruCache中，分配缓存空间大小,默认程序分配最大空间的 1/8
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int maxSize = manager.getMemoryClass() / RATE; // 比如 64M/8,单位为M
        // BitmapLruCache自定义缓存class，该框架本身支持二级缓存，在BitmapLruCache封装一个软引用缓存
        mImageLoader = new ImageLoader(RequestManager.getRequestQueue(),
                new BitmapLruCache(1024 * 1024 * maxSize));

        // CrashHandler crashHandler = CrashHandler.getInstance(); // 获取异常捕捉单例对象
        // crashHandler.init(getApplicationContext());
        File jsonCacheFile = new File(Environment.getExternalStorageDirectory()
                + Paths.JsonCache);
        if (!jsonCacheFile.exists()) {
            jsonCacheFile.mkdirs();
        }
        dataCache = DataCache.get(jsonCacheFile);

        // 获取个人登录本地存储的对象
        File userCacheFile = new File(Environment.getExternalStorageDirectory()
                + Paths.UserCache);
        if (!userCacheFile.exists()) {
            userCacheFile.mkdirs();
        }
        userCache = DataCache.get(userCacheFile);

        // 创建图片上传的文件夹
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory()
                .getPath() + Paths.UploadCache);
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();
        }

        ToastMgr.init(this);
        clearApkCache();

//		initCrush();
        Fresco.initialize(this);

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        initLocation();
    }

    private void initCrush() {
        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler
                .getInstance();
        mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());

    }

    public DataCache getCache() {
        return dataCache;
    }

    public DataCache getUserCache() {
        return userCache;
    }


    /**
     * 清除app缓存
     */
    public void clearAppCache(Context context) throws Exception {
        // 清除数据缓存，分别为files、SD卡、data、数据库
        clearCacheFolder(getFilesDir(), System.currentTimeMillis());

        clearCacheFolder(new File(getDiscCacheDir(context)),
                System.currentTimeMillis());
        clearCacheFolder(getCacheDir(), System.currentTimeMillis());
        clearCacheFolder(new File(Environment.getExternalStorageDirectory()
                .getPath() + "/raydo"), System.currentTimeMillis());
        // clearCacheFolder(new
        // File("/data/data/com.bm.lingtaozhisheng/databases"),
        // System.currentTimeMillis());
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(Build.VERSION_CODES.FROYO)) {
            clearCacheFolder(MethodsCompat.getExternalCacheDir(this),
                    System.currentTimeMillis());
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public long getCacheLenth(Context context) {
        long len = 0l;
        File cacheFile = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/raydo");

        try {
            len = /* getFileSize(getFilesDir()) + */getFileSize(cacheFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return len;
    }

    /**
     * 获取文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 获取SD卡上的缓存路径
     *
     * @param context
     * @return
     */
    private static String getDiscCacheDir(Context context) {
        // android 2.2 以后才支持的特性
        if (hasExternalCacheDir()) {
            // 内存卡可用时
            if (sdCardIsAvailable()) {
                return context.getExternalCacheDir().getPath();
            }
        }
        // Before Froyo we need to construct the external cache dir ourselves
        // 2.2以前我们需要自己构造
        final String cacheDir = "/Android/data/" + Paths.ApkCache + "/cache/";
        return Environment.getExternalStorageDirectory().getPath() + cacheDir;
    }

    /**
     * 清除缓存目录
     *
     * @param dir 目录
     *            当前系统时间
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return deletedFiles;
    }

    public void clearApkCache() {
        clearCacheFolder(new File(Environment.getExternalStorageDirectory()
                + Paths.ApkCache), System.currentTimeMillis());
    }

    public void clearImageCache() {
        clearCacheFolder(new File(Environment.getExternalStorageDirectory()
                + Paths.ApkImage), System.currentTimeMillis());
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }


    //=====================百度地图定位========================

    // 定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LatLng currentLatlng = null;
    private List<LocationCallBack> observers = new ArrayList<LocationCallBack>();

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            currentLatlng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if (isCanStart && currentLatlng != null)
                sendMessage(new Gson().toJson(currentLatlng));
            //返回我自己的经纬度
            if (observers != null && observers.size() > 0) {
                for (LocationCallBack callback : observers) {
                    callback.returnLocation(location);
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void initLocation() {
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public void destroyLocation() {
        // 退出时销毁定位
        mLocClient.stop();
    }

    public LatLng getCurrentLatlng() {
        return currentLatlng;
    }

    public void setLocationCallBack(LocationCallBack locationCallBack) {
        observers.add(locationCallBack);
    }

    public void removeLocationCallBack(LocationCallBack locationCallBack) {
        if (observers.contains(locationCallBack))
            observers.remove(locationCallBack);
    }
    //=====================百度地图定位=======================

    //=====================websocket=======================

    private final WebSocketConnection mConnection = new WebSocketConnection();

    private boolean isCanStart = false;

    public String name;

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
//                    ToastMgr.show("收到消息：" + payload);
                    Gson gson = new Gson();
                    MessageBean msg = gson.fromJson(payload, MessageBean.class);
                    if (msg != null) {
                        Log.i("wanglei","0");
                        setMessageData(msg);
                    }

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
        this.name = name;
        start(name);
    }

    public void setWebSocketEnable(boolean isCanStart) {
        this.isCanStart = isCanStart;
    }

    private void setMessageData(MessageBean msg) {
        //如果不是自己就不要发送
        Log.i("wanglei","1");

        if (!msg.User.equals(name)) {
            Log.i("wanglei","2");
            LatLng yourFriendLatLng = new Gson().fromJson(msg.Content, LatLng.class);
            if (fMessageCallback != null && fMessageCallback.size() > 0) {
                Log.i("wanglei","3");
                for (MessageFriendLatLogCallBack callback :fMessageCallback) {
                    callback.getFriendLatlng(yourFriendLatLng);
                }
            }
        }
    }

    private List<MessageFriendLatLogCallBack> fMessageCallback = new ArrayList<MessageFriendLatLogCallBack>();

    public void setMessageCallBack(MessageFriendLatLogCallBack messageCallBack) {
        fMessageCallback.add(messageCallBack);
    }

    public void removeMessageCallBack(MessageFriendLatLogCallBack messageCallBack) {
        if (fMessageCallback.contains(messageCallBack))
            fMessageCallback.remove(messageCallBack);
    }
    //=====================websocket=======================

}
