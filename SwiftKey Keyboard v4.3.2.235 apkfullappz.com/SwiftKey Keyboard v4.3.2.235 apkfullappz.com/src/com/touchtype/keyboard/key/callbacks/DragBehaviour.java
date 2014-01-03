package com.touchtype.keyboard.key.callbacks;

import com.google.common.collect.Iterables;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class DragBehaviour
  extends HashMap<Integer, DragThreshold>
{
  private boolean mReportNonDrag;
  
  DragBehaviour(int paramInt, DragThreshold paramDragThreshold)
  {
    this(false);
    addBehaviour(paramInt, paramDragThreshold);
  }
  
  public DragBehaviour(boolean paramBoolean)
  {
    this.mReportNonDrag = paramBoolean;
  }
  
  public static DragBehaviour combineBehaviours(DragBehaviour paramDragBehaviour1, DragBehaviour paramDragBehaviour2)
  {
    if (paramDragBehaviour1 == null) {
      return paramDragBehaviour2;
    }
    if (paramDragBehaviour2 == null) {
      return paramDragBehaviour1;
    }
    if (paramDragBehaviour1.size() == 0)
    {
      boolean bool5;
      if (!paramDragBehaviour1.mReportNonDrag)
      {
        boolean bool6 = paramDragBehaviour2.mReportNonDrag;
        bool5 = false;
        if (!bool6) {}
      }
      else
      {
        bool5 = true;
      }
      paramDragBehaviour2.mReportNonDrag = bool5;
      return paramDragBehaviour2;
    }
    if (paramDragBehaviour2.size() == 0)
    {
      boolean bool3;
      if (!paramDragBehaviour1.mReportNonDrag)
      {
        boolean bool4 = paramDragBehaviour2.mReportNonDrag;
        bool3 = false;
        if (!bool4) {}
      }
      else
      {
        bool3 = true;
      }
      paramDragBehaviour1.mReportNonDrag = bool3;
      return paramDragBehaviour1;
    }
    boolean bool1;
    if (!paramDragBehaviour1.mReportNonDrag)
    {
      boolean bool2 = paramDragBehaviour2.mReportNonDrag;
      bool1 = false;
      if (!bool2) {}
    }
    else
    {
      bool1 = true;
    }
    DragBehaviour localDragBehaviour = new DragBehaviour(bool1);
    Iterator localIterator = Iterables.concat(paramDragBehaviour1.entrySet(), paramDragBehaviour2.entrySet()).iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localDragBehaviour.addBehaviour(((Integer)localEntry.getKey()).intValue(), (DragThreshold)localEntry.getValue());
    }
    return localDragBehaviour;
  }
  
  public static DragBehaviour combineBehaviours(List<DragBehaviour> paramList)
  {
    DragBehaviour localDragBehaviour = null;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      localDragBehaviour = combineBehaviours(localDragBehaviour, (DragBehaviour)localIterator.next());
    }
    return localDragBehaviour;
  }
  
  private static float combineThresholds(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 < 0.0F) {
      return paramFloat2;
    }
    if (paramFloat2 < 0.0F) {
      return paramFloat1;
    }
    return Math.min(paramFloat1, paramFloat2);
  }
  
  DragBehaviour addBehaviour(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (containsKey(Integer.valueOf(paramInt)))
    {
      DragThreshold localDragThreshold = (DragThreshold)get(Integer.valueOf(paramInt));
      paramFloat1 = combineThresholds(localDragThreshold.mDragThreshold, paramFloat1);
      paramFloat2 = combineThresholds(localDragThreshold.mDragClickThreshold, paramFloat2);
    }
    put(Integer.valueOf(paramInt), new DragThreshold(paramFloat1, paramFloat2));
    return this;
  }
  
  DragBehaviour addBehaviour(int paramInt, DragThreshold paramDragThreshold)
  {
    return addBehaviour(paramInt, paramDragThreshold.mDragThreshold, paramDragThreshold.mDragClickThreshold);
  }
  
  public boolean shouldReportNonDrags()
  {
    return this.mReportNonDrag;
  }
  
  public static final class DragThreshold
  {
    public final float mDragClickThreshold;
    public final float mDragThreshold;
    
    public DragThreshold(float paramFloat1, float paramFloat2)
    {
      this.mDragThreshold = paramFloat1;
      this.mDragClickThreshold = paramFloat2;
    }
    
    public String toString()
    {
      return "DragThreshold: " + this.mDragThreshold + ", DragClickThreshold: " + this.mDragClickThreshold;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.callbacks.DragBehaviour
 * JD-Core Version:    0.7.0.1
 */