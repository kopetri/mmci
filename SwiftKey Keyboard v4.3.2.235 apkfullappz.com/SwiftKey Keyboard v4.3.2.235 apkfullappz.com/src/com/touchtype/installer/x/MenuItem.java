package com.touchtype.installer.x;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touchtype.R.styleable;
import java.util.HashMap;
import java.util.Map;

public class MenuItem
  extends LinearLayout
  implements View.OnFocusChangeListener, View.OnKeyListener, View.OnTouchListener
{
  private static Map<State, Integer> mStateDrawables;
  private static Map<State, Integer> mStateSubtitleColors;
  private static Map<State, Integer> mStateTitleColors;
  private boolean mActive;
  private final AttributeSet mAtts;
  private ImageView mChevronView;
  private boolean mCompleted;
  private LinearLayout mContainer;
  private final Context mContext;
  private State mCurrentState;
  private Integer mIcon;
  private ImageView mIconView;
  private TextView mPercent;
  private int mPosition;
  private TextView mPositionView;
  private ProgressBar mProgressBar;
  private RelativeLayout mProgressGroup;
  private Resources mResources = getResources();
  private String mSummary;
  private TextView mSummaryTextView;
  private String mTitle;
  private TextView mTitleView;
  
  static
  {
    HashMap localHashMap1 = new HashMap();
    mStateDrawables = localHashMap1;
    localHashMap1.put(State.COMPLETE, Integer.valueOf(2130838075));
    mStateDrawables.put(State.ACTIVE, Integer.valueOf(2130838072));
    mStateDrawables.put(State.TODO, Integer.valueOf(2130838078));
    mStateDrawables.put(State.COMPLETE_FOCUS, Integer.valueOf(2130838076));
    mStateDrawables.put(State.ACTIVE_FOCUS, Integer.valueOf(2130838073));
    mStateDrawables.put(State.TODO_FOCUS, Integer.valueOf(2130838079));
    HashMap localHashMap2 = new HashMap();
    mStateTitleColors = localHashMap2;
    localHashMap2.put(State.COMPLETE, Integer.valueOf(2131165349));
    mStateTitleColors.put(State.ACTIVE, Integer.valueOf(2131165348));
    mStateTitleColors.put(State.TODO, Integer.valueOf(2131165350));
    HashMap localHashMap3 = new HashMap();
    mStateSubtitleColors = localHashMap3;
    localHashMap3.put(State.COMPLETE, Integer.valueOf(2131165352));
    mStateSubtitleColors.put(State.ACTIVE, Integer.valueOf(2131165351));
    mStateSubtitleColors.put(State.TODO, Integer.valueOf(2131165353));
  }
  
  public MenuItem(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mAtts = paramAttributeSet;
    this.mContext = paramContext;
    init();
    setupView();
    setupInternalState();
  }
  
  private void updatePositionView()
  {
    if (this.mPositionView != null)
    {
      TextView localTextView = this.mPositionView;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mPosition);
      localTextView.setText(String.format("%d", arrayOfObject));
    }
  }
  
  public AttributeSet getAtts()
  {
    return this.mAtts;
  }
  
  public void hideChevron()
  {
    this.mChevronView.setVisibility(8);
  }
  
  public void hideProgress()
  {
    this.mProgressGroup.setVisibility(8);
  }
  
  public void hideSummary()
  {
    this.mSummaryTextView.setVisibility(8);
  }
  
  protected void init()
  {
    String str = getContext().getResources().getString(2131296303);
    TypedArray localTypedArray = getContext().obtainStyledAttributes(this.mAtts, R.styleable.MenuItem);
    this.mTitle = String.format(localTypedArray.getString(0), new Object[] { str });
    this.mSummary = String.format(localTypedArray.getString(1), new Object[] { str });
    this.mPosition = localTypedArray.getInteger(2, 1);
    this.mActive = localTypedArray.getBoolean(3, false);
    this.mCompleted = localTypedArray.getBoolean(4, false);
    this.mIcon = Integer.valueOf(localTypedArray.getResourceId(5, 0));
    localTypedArray.recycle();
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.mCompleted)
      {
        this.mContainer.setBackgroundResource(2130838076);
        return;
      }
      if (this.mActive)
      {
        this.mContainer.setBackgroundResource(2130838073);
        return;
      }
      this.mContainer.setBackgroundResource(2130838079);
      return;
    }
    setDefaultColors();
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    this.mContainer.setBackgroundResource(2130838080);
    RevertButtonState localRevertButtonState = new RevertButtonState(null);
    new Handler().post(localRevertButtonState);
    return false;
  }
  
  public void setCompleted(boolean paramBoolean)
  {
    this.mCompleted = paramBoolean;
  }
  
  public void setDefaultColors()
  {
    this.mContainer.setBackgroundResource(((Integer)mStateDrawables.get(this.mCurrentState)).intValue());
    int i = ((Integer)mStateTitleColors.get(this.mCurrentState)).intValue();
    this.mTitleView.setTextColor(this.mResources.getColor(i));
    if (this.mPositionView != null) {
      this.mPositionView.setTextColor(this.mResources.getColor(i));
    }
    this.mSummaryTextView.setTextColor(this.mResources.getColor(((Integer)mStateSubtitleColors.get(this.mCurrentState)).intValue()));
  }
  
  public void setPosition(int paramInt)
  {
    this.mPosition = paramInt;
    updatePositionView();
  }
  
  public void setProgress(int paramInt)
  {
    this.mProgressBar.setProgress(paramInt);
    this.mPercent.setText(Integer.toString(paramInt) + "%");
  }
  
  public void setSummary(String paramString)
  {
    this.mSummaryTextView.setText(paramString);
  }
  
  public void setTitle(String paramString)
  {
    this.mTitleView.setText(paramString);
  }
  
  protected void setupInternalState()
  {
    this.mSummaryTextView = ((TextView)findViewById(2131230867));
    this.mProgressGroup = ((RelativeLayout)findViewById(2131230872));
    this.mProgressBar = ((ProgressBar)findViewById(2131230873));
    this.mPercent = ((TextView)findViewById(2131230874));
    this.mTitleView = ((TextView)findViewById(2131230866));
    this.mIconView = ((ImageView)findViewById(2131230868));
    this.mContainer = ((LinearLayout)findViewById(2131230863));
    this.mContainer.setFocusable(true);
    this.mContainer.setOnFocusChangeListener(this);
    this.mContainer.setOnTouchListener(this);
    this.mPositionView = ((TextView)findViewById(2131230864));
    this.mChevronView = ((ImageView)findViewById(2131230871));
    ImageView localImageView = (ImageView)findViewById(2131230869);
    if (this.mCompleted)
    {
      localImageView.setVisibility(0);
      this.mCurrentState = State.COMPLETE;
    }
    for (;;)
    {
      if ((this.mTitleView != null) && (this.mTitle != null)) {
        this.mTitleView.setText(this.mTitle);
      }
      if ((this.mSummaryTextView != null) && (this.mSummary != null)) {
        this.mSummaryTextView.setText(this.mSummary);
      }
      updatePositionView();
      if ((this.mIconView != null) && (this.mIcon.intValue() > 0)) {
        this.mIconView.setImageResource(this.mIcon.intValue());
      }
      setDefaultColors();
      return;
      if (this.mActive)
      {
        this.mChevronView.setVisibility(0);
        this.mCurrentState = State.ACTIVE;
      }
      else
      {
        this.mCurrentState = State.TODO;
      }
    }
  }
  
  protected void setupView()
  {
    LayoutInflater.from(this.mContext).inflate(2130903085, this);
  }
  
  public void showProgress()
  {
    this.mProgressGroup.setVisibility(0);
  }
  
  public void showSummary()
  {
    this.mSummaryTextView.setVisibility(0);
  }
  
  private final class RevertButtonState
    implements Runnable
  {
    private RevertButtonState() {}
    
    public void run()
    {
      try
      {
        Thread.sleep(50L);
        label6:
        MenuItem.this.setDefaultColors();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        break label6;
      }
    }
  }
  
  private static enum State
  {
    static
    {
      ACTIVE = new State("ACTIVE", 1);
      TODO = new State("TODO", 2);
      DISABLED = new State("DISABLED", 3);
      COMPLETE_FOCUS = new State("COMPLETE_FOCUS", 4);
      ACTIVE_FOCUS = new State("ACTIVE_FOCUS", 5);
      TODO_FOCUS = new State("TODO_FOCUS", 6);
      COMPLETE_TOUCH = new State("COMPLETE_TOUCH", 7);
      ACTIVE_TOUCH = new State("ACTIVE_TOUCH", 8);
      TODO_TOUCH = new State("TODO_TOUCH", 9);
      State[] arrayOfState = new State[10];
      arrayOfState[0] = COMPLETE;
      arrayOfState[1] = ACTIVE;
      arrayOfState[2] = TODO;
      arrayOfState[3] = DISABLED;
      arrayOfState[4] = COMPLETE_FOCUS;
      arrayOfState[5] = ACTIVE_FOCUS;
      arrayOfState[6] = TODO_FOCUS;
      arrayOfState[7] = COMPLETE_TOUCH;
      arrayOfState[8] = ACTIVE_TOUCH;
      arrayOfState[9] = TODO_TOUCH;
      $VALUES = arrayOfState;
    }
    
    private State() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.MenuItem
 * JD-Core Version:    0.7.0.1
 */