package com.keyboard.best;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import android.view.KeyEvent;
import android.view.MotionEvent;
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
    private Button mbtn1;
    private Button mbtn2;

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
        //mbtn1 = (Button) findViewById(R.id.btn_1);
        //mbtn1.setOnHoverListener(mButtonHoverListener);
        //mbtn1.setOnFocusChangeListener(mButtonFocusListener);
        //mbtn1.setOnTouchListener(mButtonTouchListener);
        //mbtn1.setOnClickListener(mButtonClickListener);

        //mbtn2 = (Button) findViewById(R.id.btn_2);
        //mbtn2.setOnHoverListener(mButtonHoverListener);
        //mbtn2.setOnFocusChangeListener(mButtonFocusListener);
        //mbtn2.setOnTouchListener(mButtonTouchListener);
        //mbtn2.setOnClickListener(mButtonClickListener);
    }

    private final OnFocusChangeListener mButtonFocusListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean bool) {

            /*
            CharSequence text = "hmm";

            switch (v.getId()) {
                case R.id.btn_1:
                    //TOAST
                    text = "BTN1!";
                    sendCharacter("a");
                    break;
                case R.id.btn_2:
                    //TOAST
                    text = "BTN2!";
                    sendCharacter("b");
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mContext, text, duration);
            toast.show();
            */
        }

    };

    private final OnTouchListener mButtonTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /*
            CharSequence text = "hmm";

            switch (v.getId()) {
                case R.id.btn_1:
                    //TOAST
                    text = "BTN1!";
                    sendCharacter("a");
                    break;
                case R.id.btn_2:
                    //TOAST
                    text = "BTN2!";
                    sendCharacter("b");
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mContext, text, duration);
            toast.show();
            */
            return true;

        }

    };

    private final OnHoverListener mButtonHoverListener = new OnHoverListener() {
        @Override
        public boolean onHover(View v, MotionEvent me) {
            /*
            CharSequence text = "hmm";

            switch (v.getId()) {
                case R.id.btn_1:
                    //TOAST
                    text = "BTN1!";
                    sendCharacter("a");
                    break;
                case R.id.btn_2:
                    //TOAST
                    text = "BTN2!";
                    sendCharacter("b");
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mContext, text, duration);
            toast.show();
            */
            return true;
        }
    };

    private final OnClickListener mButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            /*
            CharSequence text = "hmm";

            switch (v.getId()) {
                case R.id.btn_1:
                    //TOAST
                    text = "BTN1!";
                    sendCharacter("a");
                    break;
                case R.id.btn_2:
                    //TOAST
                    text = "BTN2!";
                    sendCharacter("b");
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mContext, text, duration);
            toast.show();
            */
        }
    };
}
