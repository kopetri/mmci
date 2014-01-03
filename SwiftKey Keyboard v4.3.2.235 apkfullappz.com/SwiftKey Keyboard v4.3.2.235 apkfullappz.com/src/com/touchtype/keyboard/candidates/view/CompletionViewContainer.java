package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.CompletionInfo;
import android.widget.LinearLayout;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;

public class CompletionViewContainer
  extends LinearLayout
  implements View.OnTouchListener
{
  private View mButtonLeft;
  private View mButtonLeftLayout;
  private View mButtonRight;
  private View mButtonRightLayout;
  private CompletionView mCompletionView;
  
  public CompletionViewContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    if (this.mCompletionView == null)
    {
      this.mButtonLeftLayout = findViewById(2131230813);
      this.mButtonLeft = findViewById(2131230814);
      if (this.mButtonLeft != null) {
        this.mButtonLeft.setOnTouchListener(this);
      }
      this.mButtonRightLayout = findViewById(2131230816);
      this.mButtonRight = findViewById(2131230817);
      if (this.mButtonRight != null) {
        this.mButtonRight.setOnTouchListener(this);
      }
      this.mCompletionView = ((CompletionView)findViewById(2131230815));
    }
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      if (paramView != this.mButtonRight) {
        break label24;
      }
      this.mCompletionView.scrollNext();
    }
    for (;;)
    {
      return false;
      label24:
      if (paramView == this.mButtonLeft) {
        this.mCompletionView.scrollPrev();
      }
    }
  }
  
  public void requestLayout()
  {
    int i = 1;
    int n;
    label50:
    int i2;
    label71:
    View localView1;
    int i1;
    if (this.mCompletionView != null)
    {
      int j = this.mCompletionView.getWidth();
      int k = this.mCompletionView.computeHorizontalScrollRange();
      int m = this.mCompletionView.getTargetScrollX();
      if (m <= 0) {
        break label110;
      }
      n = i;
      if (m + j >= k) {
        break label116;
      }
      if (this.mButtonLeftLayout != null)
      {
        View localView2 = this.mButtonLeftLayout;
        if (n == 0) {
          break label121;
        }
        i2 = 0;
        localView2.setVisibility(i2);
      }
      if (this.mButtonRightLayout != null)
      {
        localView1 = this.mButtonRightLayout;
        i1 = 0;
        if (i == 0) {
          break label128;
        }
      }
    }
    for (;;)
    {
      localView1.setVisibility(i1);
      super.requestLayout();
      return;
      label110:
      n = 0;
      break;
      label116:
      i = 0;
      break label50;
      label121:
      i2 = 8;
      break label71;
      label128:
      i1 = 8;
    }
  }
  
  public void setCompletions(CompletionInfo[] paramArrayOfCompletionInfo, TouchTypeSoftKeyboard paramTouchTypeSoftKeyboard)
  {
    if (this.mCompletionView != null) {
      this.mCompletionView.setCompletions(paramArrayOfCompletionInfo, paramTouchTypeSoftKeyboard);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.CompletionViewContainer
 * JD-Core Version:    0.7.0.1
 */