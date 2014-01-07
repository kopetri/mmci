package com.keyboard.best;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by AJ on 05.01.14.
 */
public class MyKey {
    private String mKeyValue = "?";
    private float mKeyWidth = 0.0f;
    private float mKeyHeight = 0.0f;

    private float mPosX = 0.0f;
    private float mPosY = 0.0f;

    public MyKey(String keyValue){
        mKeyValue = keyValue;
    }

    public String getKeyValue() {
        return mKeyValue;
    }

    public void setKeyValue(String keyValue) {
        mKeyValue = keyValue;
    }

    public float getKeyHeight() {
        return mKeyHeight;
    }

    public void setKeyHeight(float keyHeight) {
        mKeyHeight = keyHeight;
    }

    public float getKeyWidth() {
        return mKeyWidth;
    }

    public void setKeyWidth(float keyWidth) {
        mKeyWidth= keyWidth;
    }

    public float getKeyPosX() {
        return mPosX;
    }

    public void setKeyPosX(float keyPosX) {
        mPosX = keyPosX;
    }

    public float getKeyPosY() {
        return mPosY;
    }

    public void setKeyPosY(float keyPosY) {
        mPosY= keyPosY;
    }

}
