package com.lenovo.androidrepositories.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.util.concurrent.TimeUnit;

/**
 * Created by jyjs on 2017/6/29.
 */

public class CustomViewPager extends ViewGroup {

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    private int curPosition = 0;

    private int childWith;


    private VelocityTracker velocityTracker;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context, sInterpolator, true);

        // 获取TouchSlop值
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        velocityTracker = VelocityTracker.obtain();

    }

    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;

        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
            childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
        // 初始化左右边界值
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
        childWith = getChildAt(0).getWidth();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        velocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }

                break;


        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = event.getX();
                mXLastMove = mXDown;
                return true;
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getX();
                int scrolledX = (int) (mXLastMove - mXMove);
                mXLastMove = mXMove;
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                return true;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                Log.e("print", "onTouchEvent: " + velocityTracker.getXVelocity());
                if (velocityTracker.getXVelocity() < -200) {
                    targetIndex = getScrollX() / getWidth() + 1;
                    if (targetIndex > getChildCount() - 1) {
                        targetIndex = getChildCount() - 1;
                    }
                } else if (velocityTracker.getXVelocity() > 200) {
                    targetIndex = getScrollX() / getWidth();
                }
                smoothScrollToItem(targetIndex);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 滚动到指定位置
     *
     * @param targetIndex
     */
    private void smoothScrollToItem(int targetIndex) {
        curPosition = targetIndex;
        int dx = targetIndex * getWidth() - getScrollX();
        // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
        mScroller.startScroll(getScrollX(), 0, dx, 0);
        invalidate();
    }


    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


}
