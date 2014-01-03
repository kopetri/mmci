package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public class EmptyDelegate
  extends TouchSubDelegate
{
  public void cancel() {}
  
  public void down(TouchEvent.Touch paramTouch) {}
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    return false;
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return false;
  }
  
  public void slideIn(TouchEvent.Touch paramTouch) {}
  
  public void slideOut(TouchEvent.Touch paramTouch) {}
  
  public void up(TouchEvent.Touch paramTouch) {}
  
  public static final class FlowCompleteDelegate
    extends EmptyDelegate
  {
    public final InputEventModel mInputEventModel;
    
    public FlowCompleteDelegate(InputEventModel paramInputEventModel)
    {
      this.mInputEventModel = paramInputEventModel;
    }
    
    public void up(TouchEvent.Touch paramTouch)
    {
      this.mInputEventModel.onFlowComplete();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.EmptyDelegate
 * JD-Core Version:    0.7.0.1
 */