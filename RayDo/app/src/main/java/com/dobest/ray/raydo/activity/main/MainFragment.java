package com.dobest.ray.raydo.activity.main;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dobest.ray.raydo.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class MainFragment extends Fragment {

    private SimpleDraweeView fb_imageview1;
    private SimpleDraweeView fb_imageview2;
    private SimpleDraweeView fb_imageview3;

    private static final  String ImageUrl1="http://img5.imgtn.bdimg.com/it/u=3368709273,2917712832&fm=21&gp=0.jpg";
    private static final  String ImageUrl2="http://img5.imgtn.bdimg.com/it/u=1929151791,3740603256&fm=21&gp=0.jpg";
    private static final  String ImageUrl3="http://ww3.sinaimg.cn/bmiddle/b0cb01d6jw1eoo44fag8ig208w050x6p.gif";

    /**
     * activity 实例
     */
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, savedInstanceState);
    }
    private void findViews(View view) {
        fb_imageview1 = (SimpleDraweeView) view.findViewById(R.id.fb_imageview1);
        fb_imageview2 = (SimpleDraweeView) view.findViewById(R.id.fb_imageview2);
        fb_imageview3 = (SimpleDraweeView) view.findViewById(R.id.fb_imageview3);
    }
    private void init() {
        fb_imageview1.setImageURI(Uri.parse(ImageUrl1));
        fb_imageview2.setImageURI(Uri.parse(ImageUrl2));

        fb_imageview1.setAspectRatio(1f);
        fb_imageview2.setAspectRatio(1f);

        //显示一张HTTP的GIF图片
        DraweeController draweeController1 = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(ImageUrl3))
                .setAutoPlayAnimations(true).build();
        fb_imageview3.setController(draweeController1);
        fb_imageview3.setAspectRatio(1f);
    }

    private void addListeners() {

    }



}
