package com.touchtype.keyboard.key.callbacks;

public final class DragFilterFactory
{
  public static DragFilter EMPTY_FILTER = new EmptyFilter();
  
  public static DragFilter makeFilter(int paramInt)
  {
    return new SingleDragFilter(paramInt);
  }
  
  public static DragFilter makeFilter(DragEvent.Direction paramDirection)
  {
    return new SingleDragFilter(paramDirection.mAngle);
  }
  
  public static DragFilter makeFilter(DragEvent.Range paramRange)
  {
    return new RangeDragFilter(paramRange, null);
  }
  
  public static DragFilter makeNonDragFilter()
  {
    return new NotDraggingFilter();
  }
  
  static final class EmptyFilter
    implements DragFilter
  {
    public DragBehaviour createDragBehaviour(DragBehaviour.DragThreshold paramDragThreshold)
    {
      return new DragBehaviour(false);
    }
    
    public float getDrag(DragEvent paramDragEvent, float paramFloat)
    {
      return paramFloat;
    }
    
    public Float getDrag(DragEvent paramDragEvent)
    {
      return null;
    }
  }
  
  static final class NotDraggingFilter
    implements DragFilter
  {
    public DragBehaviour createDragBehaviour(DragBehaviour.DragThreshold paramDragThreshold)
    {
      return new DragBehaviour(true);
    }
    
    public float getDrag(DragEvent paramDragEvent, float paramFloat)
    {
      return paramFloat;
    }
    
    public Float getDrag(DragEvent paramDragEvent)
    {
      if (paramDragEvent.mDrag == 0.0F) {
        return Float.valueOf(paramDragEvent.mDrag);
      }
      return null;
    }
  }
  
  static final class RangeDragFilter
    implements DragFilter
  {
    private final DragFilter mNegativeFilter;
    private final DragFilter mPositiveFilter;
    
    private RangeDragFilter(DragEvent.Range paramRange)
    {
      this.mPositiveFilter = new DragFilterFactory.SingleDragFilter(paramRange.mPositiveHalf.mAngle);
      this.mNegativeFilter = new DragFilterFactory.SingleDragFilter(paramRange.mNegativeHalf.mAngle);
    }
    
    public DragBehaviour createDragBehaviour(DragBehaviour.DragThreshold paramDragThreshold)
    {
      return DragBehaviour.combineBehaviours(this.mPositiveFilter.createDragBehaviour(paramDragThreshold), this.mNegativeFilter.createDragBehaviour(paramDragThreshold));
    }
    
    public float getDrag(DragEvent paramDragEvent, float paramFloat)
    {
      Float localFloat = getDrag(paramDragEvent);
      if (localFloat != null) {
        paramFloat = localFloat.floatValue();
      }
      return paramFloat;
    }
    
    public Float getDrag(DragEvent paramDragEvent)
    {
      Float localFloat = this.mNegativeFilter.getDrag(paramDragEvent);
      if (localFloat != null) {
        return Float.valueOf(-localFloat.floatValue());
      }
      return this.mPositiveFilter.getDrag(paramDragEvent);
    }
  }
  
  static final class SingleDragFilter
    implements DragFilter
  {
    private final int mAngle;
    
    SingleDragFilter(int paramInt)
    {
      this.mAngle = paramInt;
    }
    
    public DragBehaviour createDragBehaviour(DragBehaviour.DragThreshold paramDragThreshold)
    {
      return new DragBehaviour(this.mAngle, paramDragThreshold);
    }
    
    public float getDrag(DragEvent paramDragEvent, float paramFloat)
    {
      Float localFloat = getDrag(paramDragEvent);
      if (localFloat != null) {
        paramFloat = localFloat.floatValue();
      }
      return paramFloat;
    }
    
    public Float getDrag(DragEvent paramDragEvent)
    {
      if ((paramDragEvent != null) && (paramDragEvent.mAngle == this.mAngle)) {
        return Float.valueOf(paramDragEvent.mDrag);
      }
      return null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.callbacks.DragFilterFactory
 * JD-Core Version:    0.7.0.1
 */