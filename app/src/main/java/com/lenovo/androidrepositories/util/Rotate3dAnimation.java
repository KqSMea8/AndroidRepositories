package com.lenovo.androidrepositories.util;


import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by jyjs on 2017/7/3.
 */

public class Rotate3dAnimation extends Animation {


    private float mFromDegrees;
    private float mToDegrees;


    private int mPivotXType = ABSOLUTE;
    private int mPivotYType = ABSOLUTE;
    private float mPivotXValue = 0.0f;
    private float mPivotYValue = 0.0f;

    private float mPivotX;
    private float mPivotY;
    private Camera camera;

    public Rotate3dAnimation(float fromDegrees, float toDegrees, int pivoXType, float pivoX, int pivoYType, float pivoY) {
        setInterpolator(new AccelerateInterpolator());
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXType = pivoXType;
        mPivotYType = pivoYType;

        this.mPivotXValue = pivoX;
        this.mPivotYValue = pivoY;
        initializePivotPoint();
    }

    public Rotate3dAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Called at the end of constructor methods to initialize, if possible, values for
     * the pivot point. This is only possible for ABSOLUTE pivot values.
     */
    private void initializePivotPoint() {
        if (mPivotXType == ABSOLUTE) {
            mPivotX = mPivotXValue;
        }
        if (mPivotYType == ABSOLUTE) {
            mPivotY = mPivotYValue;
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
        mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = mFromDegrees + (mToDegrees - mFromDegrees) * interpolatedTime;
        Matrix matrix = t.getMatrix();
        camera.save();
        camera.rotate(0, degrees, 0);
        camera.translate(-degrees * 5, 0, 0);
        camera.getMatrix(matrix);
        camera.restore();
        Log.e("print", mPivotX + "  --  " + mPivotY);
        matrix.preTranslate(-mPivotX, -mPivotY);
        matrix.postTranslate(mPivotX, mPivotY);
//        matrix.postScale(1 - interpolatedTime * 0.1f, 1 - interpolatedTime * 0.1f, mPivotX, mPivotY);
    }
}
