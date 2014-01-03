package com.touchtype.keyboard.view.touch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.SparseArray;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.util.LogUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressLint({"UseSparseArrays"})
public class TouchHandler
{
  private static final String TAG = TouchHandler.class.getSimpleName();
  public final Map<Integer, Key> mActivatedKeys = new HashMap(5);
  public final Key mEmptyKey;
  private SparseArray<PointF> mLastPoints = new SparseArray(5);
  private boolean mNeedsSuddenJumpingHack = false;
  
  public TouchHandler(Context paramContext, Key paramKey)
  {
    String[] arrayOfString = paramContext.getResources().getStringArray(2131623961);
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++) {
      if (arrayOfString[j].equalsIgnoreCase(Build.DEVICE))
      {
        LogUtil.w(TAG, "Detected bad touch hardware, using the sudden-jumping-touch hack, this means swipe gestures may be compromised");
        this.mNeedsSuddenJumpingHack = true;
      }
    }
    this.mEmptyKey = paramKey;
  }
  
  private void cancelAllKeys()
  {
    Iterator localIterator = this.mActivatedKeys.values().iterator();
    while (localIterator.hasNext()) {
      ((Key)localIterator.next()).cancel();
    }
    this.mActivatedKeys.clear();
    this.mLastPoints.clear();
  }
  
  private static PointF getHistoricalPoint(TouchEvent paramTouchEvent, int paramInt1, int paramInt2)
  {
    return new PointF(paramTouchEvent.getHistoricalX(paramInt1, paramInt2), paramTouchEvent.getHistoricalY(paramInt1, paramInt2));
  }
  
  public void cancelPointer(int paramInt)
  {
    Key localKey = (Key)this.mActivatedKeys.remove(Integer.valueOf(paramInt));
    if (localKey != null) {
      localKey.cancel();
    }
  }
  
  public void closing()
  {
    cancelAllKeys();
  }
  
  protected void downPointer(Key paramKey, TouchEvent.Touch paramTouch, int paramInt)
  {
    this.mActivatedKeys.put(Integer.valueOf(paramInt), paramKey);
    this.mLastPoints.put(paramInt, paramTouch.getPoint());
    paramKey.down(paramTouch);
  }
  
  public Integer findActivePointerIdForKey(Key paramKey)
  {
    Integer localInteger = Integer.valueOf(-1);
    Iterator localIterator = this.mActivatedKeys.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (localEntry.getValue() == paramKey) {
        return (Integer)localEntry.getKey();
      }
      float f1 = ((Key)localEntry.getValue()).getArea().getBounds().centerX() - paramKey.getArea().getBounds().centerX();
      float f2 = ((Key)localEntry.getValue()).getArea().getBounds().centerY() - paramKey.getArea().getBounds().centerY();
      if (f1 * f1 + f2 * f2 < 3.4028235E+38F) {
        localInteger = (Integer)localEntry.getKey();
      }
    }
    return localInteger;
  }
  
  public void handleTouchEvent(TouchEvent paramTouchEvent, int paramInt, Key paramKey)
  {
    int i = paramTouchEvent.getActionIndex();
    int j = paramTouchEvent.getActionMasked();
    int k = paramTouchEvent.getPointerId(paramInt);
    TouchEvent.Touch localTouch = paramTouchEvent.extractCurrentTouch(paramInt);
    if (j == 3)
    {
      cancelAllKeys();
      cancelPointer(k);
      return;
    }
    if (paramInt != i)
    {
      movePointer(paramKey, localTouch, k);
      return;
    }
    switch (j)
    {
    case 3: 
    case 4: 
    default: 
      return;
    case 0: 
    case 5: 
      downPointer(paramKey, localTouch, k);
      return;
    case 1: 
    case 6: 
      movePointer(paramKey, localTouch, k);
      upPointer(paramKey, localTouch, k);
      return;
    }
    movePointer(paramKey, localTouch, k);
  }
  
  protected PointF jumpedFrom(Key paramKey, TouchEvent paramTouchEvent, int paramInt)
  {
    PointF localPointF1;
    if (!this.mNeedsSuddenJumpingHack) {
      localPointF1 = null;
    }
    float f1;
    float f2;
    label139:
    PointF localPointF2;
    do
    {
      return localPointF1;
      f1 = 2.0F * paramKey.getArea().getBounds().width();
      f2 = 1.0F * paramKey.getArea().getBounds().height();
      localPointF1 = new PointF(paramTouchEvent.getX(paramInt), paramTouchEvent.getY(paramInt));
      for (int i = -1 + paramTouchEvent.getHistorySize();; i--)
      {
        if (i < 0) {
          break label139;
        }
        PointF localPointF3 = getHistoricalPoint(paramTouchEvent, paramInt, i);
        if ((Math.abs(localPointF1.x - localPointF3.x) > f1) || (Math.abs(localPointF1.y - localPointF3.y) > f2)) {
          break;
        }
        localPointF1.set(localPointF3);
      }
      localPointF2 = (PointF)this.mLastPoints.get(paramTouchEvent.getPointerId(paramInt));
    } while ((localPointF2 != null) && ((Math.abs(localPointF1.x - localPointF2.x) > f1) || (Math.abs(localPointF1.y - localPointF2.y) > f2)));
    return null;
  }
  
  protected void movePointer(Key paramKey, TouchEvent.Touch paramTouch, int paramInt)
  {
    Key localKey = (Key)this.mActivatedKeys.get(Integer.valueOf(paramInt));
    if (localKey == null) {
      localKey = this.mEmptyKey;
    }
    PointF localPointF = jumpedFrom(localKey, paramTouch.getTouchEvent(), paramTouch.getPointerIndex());
    int i;
    TouchEvent.Touch localTouch;
    if (localPointF != null)
    {
      i = 1;
      if (i == 0) {
        break label120;
      }
      localTouch = TouchEvent.createDummyTouchEvent(localPointF, 2, paramTouch.getTouchEvent().mMatrix).extractCurrentTouch(0);
      label83:
      this.mLastPoints.put(paramInt, paramTouch.getPoint());
      if ((i != 0) || (!localKey.handleGesture(paramTouch))) {
        break label126;
      }
    }
    label120:
    label126:
    while (localKey == paramKey)
    {
      return;
      i = 0;
      break;
      localTouch = null;
      break label83;
    }
    if (i != 0)
    {
      localKey.up(localTouch);
      paramKey.down(paramTouch);
    }
    for (;;)
    {
      this.mActivatedKeys.put(Integer.valueOf(paramInt), paramKey);
      return;
      localKey.slideOut(paramTouch);
      paramKey.slideIn(paramTouch);
    }
  }
  
  public void setInitialTouch(Matrix paramMatrix, int paramInt, Key paramKey)
  {
    downPointer(paramKey, TouchEvent.createDummyTouchEvent(new PointF(paramKey.getArea().getBounds().centerX(), paramKey.getArea().getBounds().centerY()), 0, paramMatrix).extractCurrentTouch(0), paramInt);
  }
  
  protected void upPointer(Key paramKey, TouchEvent.Touch paramTouch, int paramInt)
  {
    Key localKey = (Key)this.mActivatedKeys.remove(Integer.valueOf(paramInt));
    this.mLastPoints.remove(paramInt);
    if (localKey != null) {
      localKey.up(paramTouch);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.touch.TouchHandler
 * JD-Core Version:    0.7.0.1
 */