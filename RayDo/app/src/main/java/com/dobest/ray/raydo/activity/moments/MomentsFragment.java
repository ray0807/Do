package com.dobest.ray.raydo.activity.moments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dobest.ray.corelibs.ptr.PtrListView;
import com.dobest.ray.corelibs.views.NoScrollingListView;
import com.dobest.ray.raydo.R;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class MomentsFragment extends Fragment {
    private NoScrollingListView ptr_list;
    /**
     * 下拉刷新控件
     */

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
        ptr_list= (NoScrollingListView) view.findViewById(R.id.ptr_list);

        ptr_list.setAdapter(new myAdapter());
    }



    private void init() {
    }

    private void addListeners() {

    }

    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {

            return 30;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Log.i("wanglei","getCount"+getCount());
            if (view==null){
                view= LayoutInflater.from(mActivity).inflate(R.layout.text_layout,null);
            }
            return view;
        }
    }

}
