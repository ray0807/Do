package com.dobest.ray.raydo.activity.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dobest.ray.raydo.R;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class MapFragment extends Fragment {
    //声明变量
    private MapView bmapView;
    /**
     * activity 实例
     */
    private Activity mActivity;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) v.findViewById(R.id.bmapView);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //实例化地图
        findViews(view);
        init();
        addListeners();
    }

    private void findViews(View view) {

        Log.d("wanglei", "findViews: mMapView:" + (mMapView == null));
        mBaiduMap = mMapView.getMap();
        //设置为普通模式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(mActivity);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private void init() {

    }

    private void addListeners() {

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
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {

                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            Log.i("wanglei", "location:getLatitude" + location.getLatitude() + "|getLongitude:" + location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
//            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

}
