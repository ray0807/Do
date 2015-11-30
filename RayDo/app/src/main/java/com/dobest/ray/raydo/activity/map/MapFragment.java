package com.dobest.ray.raydo.activity.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.dobest.ray.raydo.R;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class MapFragment extends Fragment {
    //声明变量
//    private MapView mapView;
//    private AMap aMap;

    /**
     * activity 实例
     */
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //实例化地图
//        mapView = (MapView) view.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);// 此方法必须重写
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    private void findViews(View view) {
//        if (aMap == null) {
//            aMap = mapView.getMap();
//        }
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
        super.onResume();
//        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }
}
