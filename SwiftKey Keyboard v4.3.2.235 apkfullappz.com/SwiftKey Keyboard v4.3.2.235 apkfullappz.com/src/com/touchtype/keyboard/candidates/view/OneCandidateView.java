package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.view.OneCandidateViewInterface;

public final class OneCandidateView
  extends SingleContentView
  implements OneCandidateViewInterface
{
  public OneCandidateView(Context paramContext, ViewGroup.LayoutParams paramLayoutParams, Drawable paramDrawable)
  {
    super(paramContext, paramLayoutParams, paramDrawable);
    this.mStyleId = KeyStyle.StyleId.CANDIDATE;
  }
  
  public void candidateSelected(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (KeyStyle.StyleId localStyleId = KeyStyle.StyleId.TOPCANDIDATE;; localStyleId = KeyStyle.StyleId.CANDIDATE)
    {
      setStyleId(localStyleId);
      return;
    }
  }
  
  public View getView()
  {
    return this;
  }
  
  public void setContent(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId)
  {
    super.setContent(paramKeyContent, paramStyleId);
  }
  
  public void setThemeAttributes(Drawable paramDrawable)
  {
    setBackgroundDrawable(paramDrawable);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.OneCandidateView
 * JD-Core Version:    0.7.0.1
 */