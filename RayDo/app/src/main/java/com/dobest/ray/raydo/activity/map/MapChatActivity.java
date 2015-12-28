package com.dobest.ray.raydo.activity.map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.App;
import com.dobest.ray.raydo.Interface.LocationCallBack;
import com.dobest.ray.raydo.Interface.MessageFriendLatLogCallBack;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.BaseActivity;
import com.dobest.ray.raydo.bean.ChatBean;
import com.dobest.ray.raydo.bean.MessageBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangl01 on 2015/12/22.
 */
public class MapChatActivity extends BaseActivity implements LocationCallBack, MessageFriendLatLogCallBack, OnGetGeoCoderResultListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarkerA;
    private boolean isFirst = true;
    private Polyline mPolyline;
    private LatLng MyLatlng;
    private LatLng FriendLatlng;

    private GeoCoder mSearch = null;

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_marka);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_map);
        findViews();
        init();
        addListeners();
    }

    @Override
    public void findViews() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //设置为普通模式
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL, true, null));

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        App.getInstance().setLocationCallBack(this);
        App.getInstance().setMessageCallBack(this);


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                if (marker == mMarkerA) {
                    ToastMgr.show("跟朋友打招呼");
                    ChatBean chat = new ChatBean();
                    chat.chatContent = "你的朋友跟你打招呼了";
                    App.getInstance().sendMessage(chat);
                }
                return true;
            }
        });

    }

    @Override
    public void init() {
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    @Override
    public void addListeners() {

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        App.getInstance().removeLocationCallBack(this);
        App.getInstance().removeMessageCallBack(this);
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        // 回收 bitmap 资源
        bdA.recycle();
        super.onDestroy();

    }

    /**
     * 自己的位置
     *
     * @param location
     */
    @Override
    public void returnLocation(BDLocation location) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MyLatlng = ll;
        Log.i("wanglei", "location map1");

        if (ll != null && isFirst) {
            Log.i("wanglei", "location map2");
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            if (u != null) {
                Log.i("wanglei", "location map3");
                mBaiduMap.animateMapStatus(u);
                isFirst = false;
            }
        }
    }

    /**
     * 获取朋友的位置
     *
     * @param bean
     */
    @Override
    public void getFriendMessage(MessageBean bean) {
        // add marker overlay
        ChatBean chatBean = new Gson().fromJson(bean.Content, ChatBean.class);
        if (chatBean == null)
            return;

        FriendLatlng = chatBean.mLatLng;
        if (FriendLatlng != null) {
            if (mMarkerA != null) {
                mMarkerA.remove();
                mMarkerA = null;
            }
            MarkerOptions ooA = new MarkerOptions().position(FriendLatlng).icon(bdA)
                    .zIndex(9);
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
            if (MyLatlng != null)
                drawLine(FriendLatlng,MyLatlng);

        }
        if (chatBean.chatContent != null && chatBean.chatContent.length() > 0) {
            context = chatBean.chatContent;
            if (FriendLatlng != null) {
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(FriendLatlng));
            }

        }
    }

    private String context;

    private void drawLine(LatLng you,LatLng me) {
        // 添加普通折线绘制
        if (mPolyline != null) {
            mPolyline.remove();
            mPolyline = null;
        }
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(me);
        points.add(you);
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        ToastMgr.show(context + "他到了：" + geoCodeResult.getAddress());
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        ToastMgr.show(context + "他到了：" + reverseGeoCodeResult.getAddress());
    }
}
