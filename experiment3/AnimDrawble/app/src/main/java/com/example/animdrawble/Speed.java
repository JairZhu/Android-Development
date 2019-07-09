package com.example.animdrawble;

import android.view.animation.Interpolator;

public class Speed implements Interpolator {
    private float maxLength, length;
    public Speed(double maxLength, double length) {
        this.maxLength = (float) maxLength;
        this.length = (float) length;
    }
    @Override
    public float getInterpolation(float input) {
        return input * (maxLength / length) > 1 ? 1 : input * (maxLength / length);
    }
}
