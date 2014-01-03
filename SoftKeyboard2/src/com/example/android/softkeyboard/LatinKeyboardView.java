/*
 * Copyright (C) 2008-2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.softkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodSubtype;

public class LatinKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;
    private float[] xline;
    private float[] yline;
    private Paint paint;
    private float radius = 10.0f;

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();
        //keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(xline!=null){
            for (int i=0;i<xline.length;i++){
                canvas.drawCircle(xline[i],yline[i],radius,paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        switch(me.getAction()  ){
            case MotionEvent.ACTION_DOWN:
                xline = new float[1];
                yline = new float[1];
                xline[xline.length-1] = me.getX();
                yline[yline.length-1] = me.getY();
                paint.setColor(Color.BLACK);
                break;
            case MotionEvent.ACTION_UP:
                paint.setColor(Color.TRANSPARENT);
                break;
            case MotionEvent.ACTION_MOVE:
                float[] xtmp = new float[xline.length+1];
                float[] ytmp = new float[yline.length+1];
                for (int i=0;i<xline.length;i++){
                    xtmp[i] = xline[i];
                    ytmp[i] = yline[i];
                }
                xtmp[xtmp.length-1] = me.getX();
                ytmp[ytmp.length-1] = me.getY();
                xline = xtmp;
                yline = ytmp;
        }
        invalidate();
        return true;
    }
}
