package com.lenovo.androidrepositories.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by jyjs on 2017/7/4.
 */

public class Rotate3dAnimationView extends ViewGroup {

    private Scroller mScroller;

    private int mTouchSlop;

    private float mDownX;
    private float mMoveX;
    private float mLastX;

    public Rotate3dAnimationView(Context context) {
        this(context, null);
    }

    public Rotate3dAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Rotate3dAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context, new LinearInterpolator(), true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int left = (getWidth() - view.getMeasuredWidth()) / 2;
            int top = (getHeight() - view.getMeasuredHeight()) / 2;
            view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
        }
        
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mLastX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getX();
                float diff = Math.abs(mMoveX - mLastX);
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
                mDownX = event.getX();
                mLastX = mDownX;
                return true;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getX();
                int distance = (int) (mLastX - mMoveX);
                mLastX = mMoveX;
                scrollBy(distance, 0);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:

                mScroller.startScroll(getScrollX(), 0, 50, 0);
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
        }
    }
}
