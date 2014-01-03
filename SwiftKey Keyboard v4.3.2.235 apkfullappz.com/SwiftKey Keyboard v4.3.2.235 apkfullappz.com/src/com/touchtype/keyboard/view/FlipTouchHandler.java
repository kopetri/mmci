package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import com.touchtype.preferences.TouchTypePreferences;

public class FlipTouchHandler
  implements View.OnTouchListener
{
  private static final String TAG = FlipTouchHandler.class.getSimpleName();
  private final Context mContext;
  private final Handler mHandler;
  private long mLastTime;
  private final View.OnTouchListener mListener;
  private final TouchTypePreferences mPreferences;
  private Runnable mRunnable;
  private boolean mRunnableTriggered = false;
  
  public FlipTouchHandler(View.OnTouchListener paramOnTouchListener, Context paramContext, TouchTypePreferences paramTouchTypePreferences)
  {
    this.mContext = paramContext;
    this.mPreferences = paramTouchTypePreferences;
    this.mHandler = new Handler();
    this.mListener = paramOnTouchListener;
  }
  
  private void showToast()
  {
    if (this.mPreferences.isFlagSet("first_touch_on_flip"))
    {
      Toast.makeText(this.mContext, this.mContext.getResources().getString(2131297290), 1).show();
      this.mPreferences.clearFlag("first_touch_on_flip");
    }
  }
  
  public boolean onTouch(final View paramView, final MotionEvent paramMotionEvent)
  {
    long l = System.currentTimeMillis();
    switch (paramMotionEvent.getActionMasked())
    {
    }
    for (;;)
    {
      return true;
      this.mRunnable = new Runnable()
      {
        public void run()
        {
          FlipTouchHandler.this.mListener.onTouch(paramView, paramMotionEvent);
          FlipTouchHandler.access$102(FlipTouchHandler.this, true);
        }
      };
      this.mLastTime = System.currentTimeMillis();
      this.mHandler.postDelayed(this.mRunnable, 300L);
      continue;
      this.mHandler.removeCallbacks(this.mRunnable);
      continue;
      this.mHandler.removeCallbacks(this.mRunnable);
      if ((!this.mRunnableTriggered) && (l - this.mLastTime < 300L)) {
        showToast();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.FlipTouchHandler
 * JD-Core Version:    0.7.0.1
 */