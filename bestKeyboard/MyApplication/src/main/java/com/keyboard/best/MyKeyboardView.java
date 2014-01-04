package com.keyboard.best;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by AJ on 03.01.14.
 */
public class MyKeyboardView extends RelativeLayout {
    private final Context mContext;
    private SoftKeyboard mSoftKeyboard;
    private Button[] buttons = new Button[26];

    public MyKeyboardView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
    }

    public void setSoftKeyboard(SoftKeyboard sk){
        mSoftKeyboard = sk;
    }

    public void sendCharacter(String character){
        if(mSoftKeyboard != null){
            mSoftKeyboard.sendCharacter(character);
        }
    }

    @Override
    protected void onFinishInflate() {
        int id = R.id.btn_1;
        for (int i=0;i<buttons.length;i++){
            buttons[i] = (Button) findViewById(id+i);
            buttons[i].setOnClickListener(mButtonClickListener);
        }
    }

    private final OnClickListener mButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            CharSequence text = "hmm";

            switch (v.getId()) {
                case R.id.btn_1:
                    sendCharacter("a");
                    break;
                case R.id.btn_2:
                    sendCharacter("b");
                    break;
                case R.id.btn_3:
                    sendCharacter("c");
                    break;
                case R.id.btn_4:
                    sendCharacter("d");
                    break;
                case R.id.btn_5:
                    sendCharacter("e");
                    break;
                case R.id.btn_6:
                    sendCharacter("f");
                    break;
                case R.id.btn_7:
                    sendCharacter("g");
                    break;
                case R.id.btn_8:
                    sendCharacter("h");
                    break;
                case R.id.btn_9:
                    sendCharacter("i");
                    break;
                case R.id.btn_10:
                    sendCharacter("j");
                    break;
                case R.id.btn_11:
                    sendCharacter("k");
                    break;
                case R.id.btn_12:
                    sendCharacter("l");
                    break;
                case R.id.btn_13:
                    sendCharacter("m");
                    break;
                case R.id.btn_14:
                    sendCharacter("n");
                    break;
                case R.id.btn_15:
                    sendCharacter("o");
                    break;
                case R.id.btn_16:
                    sendCharacter("p");
                    break;
                case R.id.btn_17:
                    sendCharacter("q");
                    break;
                case R.id.btn_18:
                    sendCharacter("r");
                    break;
                case R.id.btn_19:
                    sendCharacter("s");
                    break;
                case R.id.btn_20:
                    sendCharacter("t");
                    break;
                case R.id.btn_21:
                    sendCharacter("u");
                    break;
                case R.id.btn_22:
                    sendCharacter("v");
                    break;
                case R.id.btn_23:
                    sendCharacter("w");
                    break;
                case R.id.btn_24:
                    sendCharacter("x");
                    break;
                case R.id.btn_25:
                    sendCharacter("y");
                    break;
                case R.id.btn_26:
                    sendCharacter("z");
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    };
}
