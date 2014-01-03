package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;

public final class AsianComposingPopup
  extends PopupWindow
{
  private final int mHeight;
  private final InputEventModel mInputEventModel;
  private final AsianCandidateLayout mParent;
  private final TextView mTextView;
  
  public AsianComposingPopup(AsianCandidateLayout paramAsianCandidateLayout, InputEventModel paramInputEventModel)
  {
    super(paramAsianCandidateLayout.getContext());
    this.mParent = paramAsianCandidateLayout;
    this.mInputEventModel = paramInputEventModel;
    setFocusable(false);
    setTouchable(true);
    setBackgroundDrawable(paramAsianCandidateLayout.getResources().getDrawable(2131165199));
    setWindowLayoutMode(-2, -2);
    FrameLayout localFrameLayout = (FrameLayout)((LayoutInflater)paramAsianCandidateLayout.getContext().getSystemService("layout_inflater")).inflate(2130903044, null);
    setContentView(localFrameLayout);
    this.mTextView = ((TextView)localFrameLayout.findViewById(2131230770));
    this.mTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AsianComposingPopup.this.mInputEventModel.commitBuffer();
      }
    });
    this.mHeight = Math.max(paramAsianCandidateLayout.getResources().getDrawable(2130837509).getMinimumHeight(), this.mTextView.getLineHeight());
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    this.mTextView.setText(paramCharSequence);
  }
  
  public void show()
  {
    if ((this.mParent.getWindowToken() != null) && (!isShowing()))
    {
      Rect localRect = new Rect();
      this.mParent.getDrawingRect(localRect);
      this.mParent.getParent().getChildVisibleRect(this.mParent, localRect, new Point());
      int i = localRect.top - this.mHeight;
      showAtLocation(this.mParent, 51, 0, i);
      update(0, i, 0, 0);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianComposingPopup
 * JD-Core Version:    0.7.0.1
 */