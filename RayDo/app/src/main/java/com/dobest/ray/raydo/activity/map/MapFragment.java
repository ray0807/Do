package com.dobest.ray.raydo.activity.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dobest.ray.corelibs.views.NoScrollingListView;
import com.dobest.ray.raydo.R;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class MapFragment extends Fragment {

    /**
     * activity 实例
     */
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return inflater.inflate(R.layout.fragment_moments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    private void findViews(View view) {

    }

    private void init() {
    }

    private void addListeners() {

    }
}
