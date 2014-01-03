package com.touchtype.keyboard.view;

import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

final class PaneDragHandler
  implements View.OnTouchListener
{
  private float mDragStartX;
  private float mDragStartY;
  private final Pane mPane;
  private int mPointerId;
  
  public PaneDragHandler(Pane paramPane)
  {
    this.mPane = paramPane;
  }
  
  @TargetApi(11)
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getActionMasked())
    {
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return false;
          } while (this.mPane.isDraggedBySomeoneElse(this));
          this.mPointerId = paramMotionEvent.getPointerId(0);
          this.mPane.onDragStarted(this);
          this.mDragStartX = paramMotionEvent.getRawX();
          this.mDragStartY = paramMotionEvent.getRawY();
          return true;
        } while ((this.mPane.isDraggedBySomeoneElse(this)) || (this.mPointerId == -1));
        this.mPane.onDragFinished();
        this.mPointerId = -1;
        return true;
      } while ((this.mPane.isDraggedBySomeoneElse(this)) || (this.mPointerId == -1));
      if (paramMotionEvent.getPointerId(0) != this.mPointerId)
      {
        this.mPane.onDragFinished();
        this.mPointerId = -1;
        return false;
      }
      this.mPane.onDragBy((int)(paramMotionEvent.getRawX() - this.mDragStartX), (int)(paramMotionEvent.getRawY() - this.mDragStartY), this);
      return true;
    } while ((this.mPane.isDraggedBySomeoneElse(this)) || (this.mPointerId == -1));
    this.mPane.dragCancelled();
    this.mPointerId = -1;
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.PaneDragHandler
 * JD-Core Version:    0.7.0.1
 */