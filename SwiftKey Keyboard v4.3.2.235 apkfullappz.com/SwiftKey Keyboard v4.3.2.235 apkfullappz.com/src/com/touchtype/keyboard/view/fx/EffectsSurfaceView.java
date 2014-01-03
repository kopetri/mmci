package com.touchtype.keyboard.view.fx;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.view.touch.FlowEvent;
import java.lang.reflect.Method;

public class EffectsSurfaceView
  extends GLSurfaceView
  implements EffectsRenderer.Observer, FlowStrokeObserver
{
  private Handler mHandler;
  private boolean mPaused = true;
  private View mPopupParent;
  private EffectsRenderer mRenderer = null;
  private final Runnable onPauseRunnable = new Runnable()
  {
    public void run()
    {
      EffectsSurfaceView.this.onPause();
    }
  };
  
  public EffectsSurfaceView(Context paramContext)
  {
    super(paramContext);
    start();
  }
  
  public EffectsSurfaceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    start();
  }
  
  private void setWindowType()
  {
    try
    {
      Class localClass = getClass();
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Integer.TYPE;
      Method localMethod = localClass.getMethod("setWindowType", arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(1000);
      localMethod.invoke(this, arrayOfObject);
      return;
    }
    catch (Exception localException) {}
  }
  
  private void start()
  {
    this.mHandler = new Handler();
    this.mRenderer = new EffectsRenderer(ThemeManager.getInstance(getContext()).getThemeHandler().getFlowInkHeadColor(), ThemeManager.getInstance(getContext()).getThemeHandler().getFlowInkTailColor(), this);
    FlowEventBroadcaster.get().setObserver(this);
    setDebugFlags(1);
    setEGLConfigChooser(8, 8, 8, 8, 0, 0);
    getHolder().setFormat(1);
    setWindowType();
    setRenderer(this.mRenderer);
    setRenderMode(1);
    this.mHandler.postDelayed(this.onPauseRunnable, 5000L);
  }
  
  public IBinder getWindowToken()
  {
    if (this.mPopupParent != null) {
      return this.mPopupParent.getWindowToken();
    }
    return super.getWindowToken();
  }
  
  public void listenToFlowEvents()
  {
    FlowEventBroadcaster.get().setObserver(this);
  }
  
  public void onFlowEvent(FlowEvent paramFlowEvent)
  {
    onResume();
    this.mRenderer.onFlowEvent(paramFlowEvent);
    this.mHandler.removeCallbacks(this.onPauseRunnable);
  }
  
  public void onPause()
  {
    if (!this.mPaused)
    {
      this.mPaused = true;
      super.onPause();
    }
  }
  
  public void onRendererFinished()
  {
    this.mHandler.postDelayed(this.onPauseRunnable, 5000L);
  }
  
  public void onResume()
  {
    if (this.mPaused)
    {
      super.onResume();
      this.mPaused = false;
    }
  }
  
  public void setPopupParent(View paramView)
  {
    this.mPopupParent = paramView;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.fx.EffectsSurfaceView
 * JD-Core Version:    0.7.0.1
 */