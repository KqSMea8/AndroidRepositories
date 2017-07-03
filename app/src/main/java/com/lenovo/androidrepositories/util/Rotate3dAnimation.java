package com.lenovo.androidrepositories.util;


import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by jyjs on 2017/7/3.
 */

public class Rotate3dAnimation extends Animation {
    Camera camera;

    public Rotate3dAnimation() {
        super();
    }

    public Rotate3dAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();


        camera.save();
        camera.rotateZ(0.3f);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(0, 0);
        matrix.postTranslate(0, 0);

    }
}
