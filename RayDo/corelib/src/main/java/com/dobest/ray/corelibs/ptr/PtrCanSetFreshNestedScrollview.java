package com.dobest.ray.corelibs.ptr;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.dobest.ray.corelib.R;
import com.in.recycler.pro.PtrFrameLayout;
import com.in.recycler.pro.PtrHandler;
import com.in.recycler.pro.header.StoreHouseHeader;

/**
 * Created by wangl01 on 2015/11/20.
 */
public class PtrCanSetFreshNestedScrollview extends PtrLollipopBaseView<NestedScrollView> {
    private NestedScrollView mNestedScrollView;
    private View child;

    public PtrCanSetFreshNestedScrollview(Context context) {
        super(context);
    }

    public PtrCanSetFreshNestedScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrCanSetFreshNestedScrollview(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    @Override
    protected PtrFrameLayout onPtrFrameCreated(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.ptr_nested_scrollview, this);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.ptr_content);
        return (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
    }

    /**
     * 设置刷新header
     *
     * @return
     */
    @Override
    protected View onPtrHeaderCreted() {
        final StoreHouseHeader header = new StoreHouseHeader(mContext);
        header.setPadding(0, 45, 0, 0);
        header.initWithString("WANGLEI");
        header.setTextColor(getResources().getColor(R.color.primary_dark_material_light));
        return header;
    }

    @Override
    protected void setupPtrFrame() {
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(300);
        mPtrFrameLayout.setPinContent(false);
        mPtrFrameLayout.setDurationToClose(300);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return CanRefresh;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (mHandler != null)
                    mHandler.onRefreshing(frame);
            }
        });
    }
    private boolean CanRefresh=true;
    public void setCanRefresh(boolean CanRefresh){
        this.CanRefresh=CanRefresh;
    }

    @Override
    public NestedScrollView getPtrView() {
        return mNestedScrollView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount > 2) {
            throw new IllegalStateException("PtrScrollView can only hold one child");
        }

        if (childCount > 1) {
            child = getChildAt(1);
            removeView(child);
            mNestedScrollView.addView(child);
        }
    }

}
