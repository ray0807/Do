package com.dobest.ray.raydo.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by wangl01 on 2015/11/24.
 */
public class NoScrollRecyclerView extends RecyclerView{
    public NoScrollRecyclerView(Context context) {
        super(context);
    }

    public NoScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
