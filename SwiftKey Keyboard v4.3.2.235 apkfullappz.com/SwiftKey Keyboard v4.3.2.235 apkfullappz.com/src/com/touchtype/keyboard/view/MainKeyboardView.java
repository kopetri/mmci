package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Matrix;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.View;
import android.widget.PopupWindow;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.MainKeyboard;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.InterimMenu;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.key.KeyStateListener;
import com.touchtype.keyboard.key.PopupContent;
import com.touchtype.keyboard.popups.KeyboardPopupMenu;
import com.touchtype.keyboard.popups.KeyboardResizeMenu;
import com.touchtype.keyboard.popups.LayoutSettingsMenu;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.theme.painter.PopupPainter;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.view.touch.TouchEvent;
import com.touchtype.keyboard.view.touch.TouchHandler;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class MainKeyboardView
  extends BaseKeyboardView<MainKeyboard>
  implements OnFlowStateChangedListener, OnShiftStateChangedListener
{
  private final KeyboardChoreographer mChoreographer;
  private int mCurrentPointerId = 0;
  protected KeyboardPopupMenu mCurrentPopup;
  private int mFlowingPointerId = 0;
  private final Set<InterimMenuCallback> mInterimMenuCallbacks = new HashSet();
  private boolean mIsFlowing;
  private final KeyboardBehaviour mKeyboardBehaviour;
  private View mPopupParent;
  protected final PopupProvider<PreviewPopup> mPopupProvider;
  protected PopupWindow mPopupWindow;
  protected PopupWindow mPopupWindowBackground;
  protected boolean mPopupWindowOnScreen;
  private TouchTypeSoftKeyboard.ShiftState mShiftState;
  private SparseArray<BaseKeyboardView<?>> mTouchDelegates = new SparseArray(5);
  
  public MainKeyboardView(Context paramContext, MainKeyboard paramMainKeyboard, InputEventModel paramInputEventModel, View paramView, KeyboardChoreographer paramKeyboardChoreographer, KeyboardBehaviour paramKeyboardBehaviour)
  {
    super(paramContext, paramMainKeyboard);
    this.mChoreographer = paramKeyboardChoreographer;
    this.mKeyboardBehaviour = paramKeyboardBehaviour;
    setBackgroundDrawable(ThemeManager.getInstance(paramContext).getThemeHandler().getProperties().getBackground());
    this.mPopupProvider = new DelayedDismissalPopupProvider()
    {
      protected PreviewPopup createPopup()
      {
        return new PreviewPopup(MainKeyboardView.this);
      }
    };
    paramInputEventModel.addFlowStateChangedListener(this);
    paramInputEventModel.addShiftStateChangedListener(this);
    this.mShiftState = paramInputEventModel.getShiftState();
  }
  
  private InterimMenuCallback createInterimCallback(final KeyState paramKeyState)
  {
    InterimMenuCallback local4 = new InterimMenuCallback()
    {
      public void onInterimClosed()
      {
        paramKeyState.setInterimState(KeyState.InterimMenu.NONE);
      }
    };
    this.mInterimMenuCallbacks.add(local4);
    return local4;
  }
  
  private KeyStateListener createInterimListener()
  {
    KeyStateListener local3 = new KeyStateListener()
    {
      public void onKeyStateChanged(KeyState paramAnonymousKeyState)
      {
        if (!MainKeyboardView.this.isShown()) {
          return;
        }
        switch (MainKeyboardView.5.$SwitchMap$com$touchtype$keyboard$key$KeyState$InterimMenu[paramAnonymousKeyState.getInterimState().ordinal()])
        {
        default: 
          return;
        }
        MainKeyboardView.this.showLayoutMenuPopup(MainKeyboardView.this.createInterimCallback(paramAnonymousKeyState));
        paramAnonymousKeyState.setPressed(false);
      }
    };
    this.mListeners.add(local3);
    return local3;
  }
  
  private KeyStateListener createPopupListener(final Key paramKey)
  {
    KeyStateListener local2 = new KeyStateListener()
    {
      private WeakReference<PreviewPopup> mPopup;
      
      private PreviewPopup fetchPopup()
      {
        if ((this.mPopup == null) || (this.mPopup.get() == null)) {}
        try
        {
          this.mPopup = new WeakReference(MainKeyboardView.this.mPopupProvider.getPopup());
          return (PreviewPopup)this.mPopup.get();
        }
        catch (IllegalStateException localIllegalStateException) {}
        return null;
      }
      
      private void showPopup(PopupPainter<PreviewPopup> paramAnonymousPopupPainter)
      {
        PreviewPopup localPreviewPopup = fetchPopup();
        if (paramAnonymousPopupPainter.paint(localPreviewPopup)) {
          localPreviewPopup.show();
        }
        while (localPreviewPopup == null) {
          return;
        }
        MainKeyboardView.this.mPopupProvider.detachPopup(localPreviewPopup);
        this.mPopup = null;
      }
      
      public void onKeyStateChanged(KeyState paramAnonymousKeyState)
      {
        PopupContent localPopupContent = paramAnonymousKeyState.getPopupContent();
        ThemeRenderer localThemeRenderer = ThemeManager.getInstance(MainKeyboardView.this.getContext()).getThemeHandler().getRenderer();
        showPopup(localPopupContent.applyShiftState(MainKeyboardView.this.mShiftState).render(localThemeRenderer, paramKey.getArea(), localPopupContent.getStyleId()));
      }
    };
    this.mListeners.add(local2);
    return local2;
  }
  
  private BaseKeyboardView<?> getDelegate(int paramInt)
  {
    BaseKeyboardView localBaseKeyboardView = (BaseKeyboardView)this.mTouchDelegates.get(paramInt);
    if (localBaseKeyboardView != null) {
      return localBaseKeyboardView;
    }
    return this;
  }
  
  private TouchEvent transformToDelegate(TouchEvent paramTouchEvent, BaseKeyboardView<?> paramBaseKeyboardView)
  {
    if (paramBaseKeyboardView == this) {
      return paramTouchEvent;
    }
    int[] arrayOfInt1 = new int[2];
    int[] arrayOfInt2 = new int[2];
    paramBaseKeyboardView.getLocationOnScreen(arrayOfInt1);
    getLocationOnScreen(arrayOfInt2);
    Matrix localMatrix = new Matrix();
    localMatrix.postTranslate(arrayOfInt2[0] - arrayOfInt1[0], arrayOfInt2[1] - arrayOfInt1[1]);
    localMatrix.postConcat(paramBaseKeyboardView.mMatrix);
    return TouchEvent.replace(paramTouchEvent, localMatrix);
  }
  
  public void closing()
  {
    dismissPopupWindow(false);
    super.closing();
    this.mPopupProvider.recycle();
  }
  
  public int delegateTouchEventsTo(BaseKeyboardView<?> paramBaseKeyboardView, Key paramKey)
  {
    int i = this.mTouchHandler.findActivePointerIdForKey(paramKey).intValue();
    if (i != -1) {
      this.mTouchDelegates.put(i, paramBaseKeyboardView);
    }
    return i;
  }
  
  public void dismissPopupWindow(boolean paramBoolean)
  {
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
    {
      if ((!paramBoolean) && (this.mPopupWindowOnScreen)) {
        TouchTypePreferences.getInstance(getContext()).getTouchTypeStats().incrementStatistic("stats_shortcut_popup_closes");
      }
      if (this.mPopupWindowBackground != null)
      {
        this.mPopupWindowBackground.dismiss();
        this.mPopupWindowBackground = null;
      }
      this.mPopupWindow.dismiss();
      this.mPopupWindow = null;
      this.mPopupWindowOnScreen = false;
    }
    if (!this.mInterimMenuCallbacks.isEmpty())
    {
      Iterator localIterator = this.mInterimMenuCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((InterimMenuCallback)localIterator.next()).onInterimClosed();
      }
      this.mInterimMenuCallbacks.clear();
    }
  }
  
  public KeyboardPopupMenu getCurrentPopup()
  {
    return this.mCurrentPopup;
  }
  
  public View getPopupParent()
  {
    return this.mPopupParent;
  }
  
  public IBinder getWindowToken()
  {
    if (this.mPopupParent != null) {
      return this.mPopupParent.getWindowToken();
    }
    return super.getWindowToken();
  }
  
  public boolean handleBack()
  {
    PopupWindow localPopupWindow = this.mPopupWindow;
    boolean bool1 = false;
    if (localPopupWindow != null)
    {
      boolean bool2 = this.mPopupWindow.isShowing();
      bool1 = false;
      if (bool2)
      {
        dismissPopupWindow(false);
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public void handleFlowStateChanged(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.mIsFlowing)) {
      this.mFlowingPointerId = this.mCurrentPointerId;
    }
    this.mIsFlowing = paramBoolean;
  }
  
  public void handleShiftStateChanged(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    this.mShiftState = paramShiftState;
    invalidateAllKeys();
  }
  
  protected boolean innerTouchEvent(TouchEvent paramTouchEvent)
  {
    if (this.mPopupWindowOnScreen) {
      return false;
    }
    int i = 0;
    if (i < paramTouchEvent.getPointerCount())
    {
      int j = paramTouchEvent.getPointerId(i);
      BaseKeyboardView localBaseKeyboardView = getDelegate(j);
      TouchEvent localTouchEvent = transformToDelegate(paramTouchEvent, localBaseKeyboardView);
      if (!this.mIsFlowing)
      {
        this.mCurrentPointerId = j;
        label53:
        localBaseKeyboardView.mTouchHandler.handleTouchEvent(localTouchEvent, i, localBaseKeyboardView.getKeyFromTouchEvent(localTouchEvent, i));
      }
      for (;;)
      {
        i++;
        break;
        if (j == this.mFlowingPointerId) {
          break label53;
        }
        localBaseKeyboardView.mTouchHandler.cancelPointer(j);
      }
    }
    return true;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    KeyStateListener localKeyStateListener = createInterimListener();
    Iterator localIterator = ((MainKeyboard)this.mKeyboard).getKeys().iterator();
    while (localIterator.hasNext())
    {
      Key localKey = (Key)localIterator.next();
      localKey.getState().addListener(KeyState.StateType.POPUP, createPopupListener(localKey));
      localKey.getState().addListener(KeyState.StateType.INTERIM, localKeyStateListener);
    }
  }
  
  public void onDetachedFromWindow()
  {
    this.mPopupProvider.recycle();
    super.onDetachedFromWindow();
  }
  
  public void reattachTouchEvents(int paramInt)
  {
    this.mTouchDelegates.remove(paramInt);
  }
  
  public void setPopupParent(View paramView)
  {
    this.mPopupParent = paramView;
  }
  
  public void showLayoutMenuPopup(InterimMenuCallback paramInterimMenuCallback)
  {
    if (paramInterimMenuCallback != null) {
      this.mInterimMenuCallbacks.add(paramInterimMenuCallback);
    }
    if (!this.mPopupWindowOnScreen)
    {
      this.mPopupWindowOnScreen = true;
      Context localContext = getContext();
      this.mPopupWindow = new PopupWindow(localContext);
      this.mPopupWindowBackground = new PopupWindow(localContext);
      this.mCurrentPopup = new LayoutSettingsMenu(localContext, this, this.mPopupParent, this.mPopupWindow, this.mPopupWindowBackground, this.mChoreographer, this.mKeyboardBehaviour.getQuickSwitchLayouts());
      this.mCurrentPopup.show();
      invalidateAllKeys();
    }
  }
  
  public void showResizePopup()
  {
    if (!this.mPopupWindowOnScreen)
    {
      this.mPopupWindowOnScreen = true;
      Context localContext = getContext();
      this.mPopupWindow = new PopupWindow(localContext);
      this.mPopupWindowBackground = new PopupWindow(localContext);
      this.mCurrentPopup = new KeyboardResizeMenu(localContext, this, this.mPopupParent, this.mPopupWindow, this.mPopupWindowBackground, this.mChoreographer);
      this.mCurrentPopup.show();
      invalidateAllKeys();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.MainKeyboardView
 * JD-Core Version:    0.7.0.1
 */