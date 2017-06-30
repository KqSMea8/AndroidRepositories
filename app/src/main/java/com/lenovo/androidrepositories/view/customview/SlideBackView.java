package com.lenovo.androidrepositories.view.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by jyjs on 2017/6/30.
 */

public class SlideBackView extends FrameLayout {
    private Scroller mScroller;
    private float mXDown;
    private float mXLast;
    private float mXMove;
    private float mTouchSlop;
    private long startTime;
    private float maxVelocity;

    /**
     * 当前Activity的DecorView
     */
    private ViewGroup mDecorView;
    /**
     * DecorView下的LinearLayout
     */
    private View mRootView;
    /**
     * 需要边缘滑动删除的Activity
     */
    private Activity mActivity;

    public SlideBackView(@NonNull Context context) {
        this(context, null);
    }

    public SlideBackView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBackView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mActivity = (Activity) context;
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mDecorView = (ViewGroup) mActivity.getWindow().getDecorView();
        mRootView = mDecorView.getChildAt(0);
        mRootView.setBackgroundColor(Color.TRANSPARENT);
        mDecorView.removeView(mRootView);
        this.addView(mRootView);
        mDecorView.addView(this);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = (int) ev.getX();
                mXLast = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = (int) ev.getX();
                float diff = Math.abs(mXMove - mXDown);
                mXLast = mXMove;
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = (int) event.getX();
                mXLast = mXDown;
                if (startTime == 0) {
                    startTime = SystemClock.uptimeMillis();
                }
                maxVelocity = 0;
                return true;
            case MotionEvent.ACTION_MOVE:
                mXDown = (int) event.getX();
                int scrollX = (int) (mXLast - mXDown);
                long nowTime = SystemClock.uptimeMillis();
                maxVelocity = (int) (scrollX * 1000 / ((nowTime - startTime) == 0 ? 1 : (nowTime - startTime)));
                startTime = nowTime;
                mXLast = mXDown;
                if (-getScrollX() - scrollX < getLeft()) {
                    scrollTo(getLeft(), 0);
                    return true;
                } else if (-getScrollX() - scrollX > getRight()) {
                    scrollTo(getRight(), 0);
                    return true;
                }
                scrollBy(scrollX, 0);
                return true;
            case MotionEvent.ACTION_UP:
                int tagIndex = (getScrollX() - getWidth() * 2 / 3) / getWidth();
                if (maxVelocity > 200) {
                    tagIndex = 0;
                } else if (maxVelocity < -200) {
                    tagIndex = -1;
                }
                int dx = tagIndex * getWidth() - getScrollX();
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (-getScrollX() == getRight()) {
            mActivity.finish();
            mActivity.overridePendingTransition(0, 0);
        }
    }
}
