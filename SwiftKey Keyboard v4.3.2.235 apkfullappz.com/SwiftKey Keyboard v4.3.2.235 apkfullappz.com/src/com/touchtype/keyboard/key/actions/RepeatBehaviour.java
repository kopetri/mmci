package com.touchtype.keyboard.key.actions;

public abstract class RepeatBehaviour
{
  public static final RepeatBehaviour EMPTY_REPEAT_BEHAVIOUR = new RepeatBehaviour()
  {
    public int getRepeatInterval(int paramAnonymousInt)
    {
      return 0;
    }
    
    public int getRepeatStartDelay()
    {
      return 0;
    }
  };
  
  public abstract int getRepeatInterval(int paramInt);
  
  public abstract int getRepeatStartDelay();
  
  public RepeatBehaviour mergeWith(final RepeatBehaviour paramRepeatBehaviour)
  {
    RepeatBehaviour localRepeatBehaviour;
    if (this == paramRepeatBehaviour) {
      localRepeatBehaviour = this;
    }
    do
    {
      return localRepeatBehaviour;
      if (this == EMPTY_REPEAT_BEHAVIOUR) {
        return paramRepeatBehaviour;
      }
      if (paramRepeatBehaviour == EMPTY_REPEAT_BEHAVIOUR) {
        return this;
      }
      localRepeatBehaviour = null;
    } while (paramRepeatBehaviour == null);
    new RepeatBehaviour()
    {
      public int getRepeatInterval(int paramAnonymousInt)
      {
        return Math.max(RepeatBehaviour.this.getRepeatInterval(paramAnonymousInt), paramRepeatBehaviour.getRepeatInterval(paramAnonymousInt));
      }
      
      public int getRepeatStartDelay()
      {
        return Math.max(RepeatBehaviour.this.getRepeatStartDelay(), paramRepeatBehaviour.getRepeatStartDelay());
      }
    };
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.RepeatBehaviour
 * JD-Core Version:    0.7.0.1
 */