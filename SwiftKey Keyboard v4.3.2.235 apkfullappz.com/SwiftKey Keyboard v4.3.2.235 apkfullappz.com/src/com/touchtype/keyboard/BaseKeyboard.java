package com.touchtype.keyboard;

import android.graphics.PointF;
import com.touchtype.keyboard.key.Key;
import java.util.List;

public class BaseKeyboard<T extends Key>
{
  private final PointF lastTouch = new PointF(-1.0F, -1.0F);
  private int lastTouchedKeyIndex = -1;
  public final T mEmptyKey;
  protected final List<T> mKeys;
  private final float mRowWeight;
  
  public BaseKeyboard(List<T> paramList, T paramT, float paramFloat)
  {
    this.mKeys = paramList;
    this.mEmptyKey = paramT;
    this.mRowWeight = paramFloat;
  }
  
  public T getKey(int paramInt)
  {
    return (Key)this.mKeys.get(paramInt);
  }
  
  public T getKeyAt(float paramFloat1, float paramFloat2)
  {
    int i = getKeyIndexAt(paramFloat1, paramFloat2);
    if (i != -1) {
      return (Key)getKeys().get(i);
    }
    return this.mEmptyKey;
  }
  
  public int getKeyIndexAt(float paramFloat1, float paramFloat2)
  {
    if (this.lastTouch.equals(paramFloat1, paramFloat2)) {
      return this.lastTouchedKeyIndex;
    }
    List localList = getKeys();
    for (int i = 0; i < localList.size(); i++) {
      if (((Key)localList.get(i)).contains(paramFloat1, paramFloat2))
      {
        this.lastTouch.set(paramFloat1, paramFloat2);
        this.lastTouchedKeyIndex = i;
        return this.lastTouchedKeyIndex;
      }
    }
    this.lastTouch.set(-1.0F, -1.0F);
    return -1;
  }
  
  public List<T> getKeys()
  {
    return this.mKeys;
  }
  
  public float getTotalRowWeight()
  {
    return this.mRowWeight;
  }
  
  public String toString()
  {
    return this.mKeys.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.BaseKeyboard
 * JD-Core Version:    0.7.0.1
 */