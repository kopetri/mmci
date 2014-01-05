package com.keyboard.best;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by AJ on 03.01.14.
 */


public class SoftKeyboard extends InputMethodService {

    private InputMethodManager mInputMethodManager;
    private String mWordSeparators;
    private int mLastDisplayWidth;
    private MyKeyboardView mQwertyKeyboard;

    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mWordSeparators = getResources().getString(R.string.word_separators);
    }

    /**
     * This is the point where you can do all of your UI initialization.  It
     * is called after creation and any configuration change.
     */
    @Override public void onInitializeInterface() {

    }

    @Override
    public View onCreateInputView() {
        MyKeyboardView qwertyKeyboard = (MyKeyboardView) getLayoutInflater().inflate(R.layout.input, null);
        mQwertyKeyboard = qwertyKeyboard;
        mQwertyKeyboard.setSoftKeyboard(this);
        return qwertyKeyboard;
    }

    public void sendCharacter(String character) {
        getCurrentInputConnection().commitText(character, 1);
    }
}
