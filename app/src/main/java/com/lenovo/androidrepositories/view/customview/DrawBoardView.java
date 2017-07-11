package com.lenovo.androidrepositories.view.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 画板
 * Created by jyjs on 2017/6/26.
 */

public class DrawBoardView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private Point point1, point2;

    public DrawBoardView(Context context) {
        this(context, null);
    }

    public DrawBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        point1 = new Point();
        point2 = new Point();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.RGB_565);
        Canvas canvas1 = new Canvas(bitmap);
        canvas1.drawColor(Color.WHITE);
        canvas1.save();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.save();
    }

    public void clear() {
        bitmap.recycle();
        bitmap = null;
        System.gc();
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.RGB_565);
        Canvas canvas1 = new Canvas(bitmap);
        canvas1.drawColor(Color.WHITE);
        canvas1.save();
        invalidate();
    }

    public Bitmap getBitmap() {
        return Bitmap.createBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point1.x = (int) event.getX(0);
                point1.y = (int) event.getY(0);
                return true;
            case MotionEvent.ACTION_MOVE:
                point2.x = (int) event.getX(0);
                point2.y = (int) event.getY(0);
                Canvas canvas1 = new Canvas(bitmap);
                canvas1.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
                canvas1.save();
                point1.x = point2.x;
                point1.y = point2.y;
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

}
