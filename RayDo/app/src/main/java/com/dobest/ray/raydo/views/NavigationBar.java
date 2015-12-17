package com.dobest.ray.raydo.views;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dobest.ray.raydo.R;
public class NavigationBar extends FrameLayout {

    private Context mContext;
    private TextView title;
    private LinearLayout back;
    private LinearLayout ll_text_right;

    private ImageView Navi_img_back;

    private TextView tv_back;
    private TextView tv_text_right;

    private OnClickListener defaltListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) mContext).finish();
        }
    };

    public NavigationBar(Context context) {
        super(context);
        mContext = context;
        initBar();
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBar();
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initBar();
    }

    private void initBar() {
        LayoutInflater.from(mContext).inflate(R.layout.common_title_layout, this);
        title = (TextView) findViewById(R.id.tv_top_title);
        back = (LinearLayout) findViewById(R.id.ll_back_operate);
        ll_text_right = (LinearLayout) findViewById(R.id.ll_text_right);
        Navi_img_back = (ImageView) findViewById(R.id.Navi_img_back);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_text_right = (TextView) findViewById(R.id.tv_text_right);
    }

    public void setListener() {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(defaltListener);
    }

    public void setBackListener(OnClickListener listener) {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(listener);
    }

    public void setBackListener(OnClickListener clickListener, int id) {
        Navi_img_back.setBackgroundResource(id);
        tv_back.setText("");
        back.setVisibility(VISIBLE);
        back.setOnClickListener(clickListener);
    }

    public void setTextColor(int color) {
        title.setTextColor(color);
    }

    public void setTitle(String _title) {
        title.setText(_title);
        title.setVisibility(View.VISIBLE);
    }

    public void setTitle(int _resId) {
        title.setVisibility(View.VISIBLE);
        title.setText(_resId);
    }

    public void showBackButton() {
        back.setVisibility(View.VISIBLE);
    }

    public void hideBackButton() {
        back.setVisibility(View.GONE);
    }


    public void setTextRight(String text, OnClickListener listener) {
        ll_text_right.setVisibility(View.VISIBLE);
        tv_text_right.setText(text);
        ll_text_right.setOnClickListener(listener);
    }


}
