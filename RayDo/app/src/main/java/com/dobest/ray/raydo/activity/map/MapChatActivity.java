package com.dobest.ray.raydo.activity.map;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.App;
import com.dobest.ray.raydo.Interface.LocationCallBack;
import com.dobest.ray.raydo.Interface.MessageFriendLatLogCallBack;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.BaseActivity;

/**
 * Created by wangl01 on 2015/12/22.
 */
public class MapChatActivity extends BaseActivity implements LocationCallBack,MessageFriendLatLogCallBack {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

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

    }

    @Override
    public void init() {

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
        super.onDestroy();

    }

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
        if (ll != null) {
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            if (u != null)
                mBaiduMap.animateMapStatus(u);
        }

    }

    @Override
    public void getFriendLatlng(LatLng yourFriendLatLng) {
        ToastMgr.show("朋友的经纬度："+yourFriendLatLng.toString());

    }
}
