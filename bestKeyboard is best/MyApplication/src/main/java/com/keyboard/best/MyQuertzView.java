package com.keyboard.best;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by AJ on 05.01.14.
 */
public class MyQuertzView extends RelativeLayout {
    private final Context mContext;
    private SoftKeyboard mSoftKeyboard;

    private Paint mTextPaint;
    private Paint mTextShadowPaint;

    //show the text?
    private boolean mShowText = false;

    //Margin
    private float mTextMarginTop = 0.0f;
    private float mTextMarginLeft = 0.0f;
    private float mTextMarginRight = 0.0f;
    private float mTextMarginBottom = 0.0f;

    //will be initialized (it's the size of the view)
    private float mTextWidth = 0.0f;
    private float mTextHeight = 0.0f;


    private float mMaxTextSize = 0.0f;
    private float mMaxTextWidth = 0.0f;

    private int mTextPos = TEXTPOS_LEFT;

    private int mTextColor;
    private int mTextShadowColor;

    private float mKeySpacing = 0.0f;
    private float currentKeySpacing = 0.0f;

    private float mViewHeight = 0.0f;
    private float mViewWidth = 0.0f;

    /**
     * Draw text to the left
     */
    public static final int TEXTPOS_LEFT = 0;

    /**
     * Draw text to the right
     */
    public static final int TEXTPOS_RIGHT = 1;




    //KEY LINE

    private MyKey[] myKeys;




    public MyQuertzView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyQwertzView,
                0, 0
        );

        try {
            // Retrieve the values from the TypedArray and store into
            // fields of this class.
            //
            // The R.styleable.PieChart_* constants represent the index for
            // each custom attribute in the R.styleable.PieChart array.
            mShowText = a.getBoolean(R.styleable.MyQwertzView_showText, false);
            mTextHeight = a.getDimension(R.styleable.MyQwertzView_TextHeight, 30.0f);
            mTextMarginTop = a.getDimension(R.styleable.MyQwertzView_TextMarginTop, 0.0f);
            mTextMarginLeft = a.getDimension(R.styleable.MyQwertzView_TextMarginLeft, 0.0f);
            mTextMarginRight = a.getDimension(R.styleable.MyQwertzView_TextMarginRight, 0.0f);
            mTextMarginBottom = a.getDimension(R.styleable.MyQwertzView_TextMarginBottom, 0.0f);
            mKeySpacing= a.getDimension(R.styleable.MyQwertzView_KeySpacing, 0.0f);
            currentKeySpacing = mKeySpacing;
            mTextPos = a.getInteger(R.styleable.MyQwertzView_TextPosition, 0);
            mTextColor = a.getColor(R.styleable.MyQwertzView_TextColor, 0xffffffff);
            mTextShadowColor = a.getColor(R.styleable.MyQwertzView_TextShadowColor, 0xff000000);

        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }

        initPaint();
        genKeys();
    }

    protected void onSizeChanged(int l, int t, int r, int b){
        mViewHeight = getHeight();
        mViewWidth = getWidth();

        //calculate the max text height;
        calcMaxTextSize();

        updateKeyValues();
    }


    private void initPaint() {

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextShadowPaint.setColor(mTextShadowColor);
        if (mTextHeight == 0) {
            mTextHeight = mTextPaint.getTextSize();
        } else {
            mTextPaint.setTextSize(mTextHeight);
            mTextShadowPaint.setTextSize(mTextHeight);
        }
    }

    private void genKeys(){
        char c = 'A';
        myKeys = new MyKey[26];
        for(int i = 0; i < 26; i++){
            myKeys[i] = new MyKey(String.valueOf(c));
            c++;
        }
    }

    private void updateKeyValues(){
        calcMaxTextWidth();


        //calculate values by current constants
        float currentWidth = 0.0f;
        for(int i = 0; i < 26; i++){
            currentWidth += mTextPaint.measureText(String.valueOf(myKeys[i].getKeyValue()));
        }


        //check if we need to adjust..
        if((currentWidth + (25.0f * currentKeySpacing)) > mMaxTextWidth){
            //adjust spacing...
            //i am too fuckin lazy to adjust text size and stuff...

            float widthDiff = (currentWidth  + (25.0f * currentKeySpacing)) - mMaxTextWidth;
            currentKeySpacing -= (widthDiff / 25.0f);
        }
        else{
            //check if old value is ok and use it
            if((currentWidth + (25.0f * mKeySpacing)) > mMaxTextWidth){
                //we are screwed...
                float widthDiff = (currentWidth  + (25.0f * mKeySpacing)) - mMaxTextWidth;
                currentKeySpacing -= (widthDiff / 25.0f);
            }
            else{
                currentKeySpacing = mKeySpacing;
            }
        }

        float currentPos = mTextMarginLeft;
        for(int i = 0; i < 26; i++){
            myKeys[i].setKeyHeight(mTextHeight);
            myKeys[i].setKeyWidth(mTextPaint.measureText(String.valueOf(myKeys[i].getKeyValue())));
            //LEFT OF THE KEY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            myKeys[i].setKeyPosX(currentPos);
            //ITS THE LOWER Y COORD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            myKeys[i].setKeyPosY(mMaxTextSize + mTextMarginTop);
            currentPos += mTextPaint.measureText(String.valueOf(myKeys[i].getKeyValue())) + currentKeySpacing;
        }

    }

    private void calcMaxTextWidth(){
        mMaxTextWidth = mViewWidth - mTextMarginLeft - mTextMarginRight;
    }

    private void calcMaxTextSize(){
        float viewHeight = mViewHeight;
        mMaxTextSize = viewHeight - mTextMarginBottom - mTextMarginTop;

        if(mMaxTextSize < mTextHeight){
            mTextHeight = mMaxTextSize;
            mTextPaint.setTextSize(mTextHeight);
            mTextShadowPaint.setTextSize(mTextHeight);
        }
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        // Draw the label text

        float currentPos = mTextMarginLeft;
        for(int i = 0; i < 26; i++){
            //lil bit of shadow for overlapping letters
            canvas.drawText(myKeys[i].getKeyValue(), myKeys[i].getKeyPosX() - 3.0f , myKeys[i].getKeyPosY() + 3.0f , mTextShadowPaint);
            canvas.drawText(myKeys[i].getKeyValue(), myKeys[i].getKeyPosX() ,  myKeys[i].getKeyPosY(), mTextPaint);
        }

    }
}

