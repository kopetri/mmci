package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.view.OneCandidateViewInterface;

public final class OneCandidateViewCompatibility
  extends TextView
  implements OneCandidateViewInterface
{
  private final int mMaximumTextSize;
  private KeyStyle.StyleId mStyleId = KeyStyle.StyleId.TOPCANDIDATE;
  
  public OneCandidateViewCompatibility(Context paramContext, ViewGroup.LayoutParams paramLayoutParams, Drawable paramDrawable)
  {
    super(paramContext);
    setLayoutParams(paramLayoutParams);
    this.mMaximumTextSize = getContext().getResources().getDimensionPixelSize(2131361841);
    setEllipsize(null);
    setSingleLine(true);
    setBackgroundDrawable(paramDrawable);
    setGravity(17);
  }
  
  private void setTextPaintAttributes()
  {
    TextPaint localTextPaint = ThemeManager.getInstance(getContext()).getThemeHandler().getRenderer().getTextPaint(this.mStyleId, KeyStyle.SubStyle.MAIN);
    setTypeface(localTextPaint.getTypeface());
    setTextColor(localTextPaint.getColor());
  }
  
  public void candidateSelected(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (KeyStyle.StyleId localStyleId = KeyStyle.StyleId.TOPCANDIDATE;; localStyleId = KeyStyle.StyleId.CANDIDATE)
    {
      this.mStyleId = localStyleId;
      setTextPaintAttributes();
      return;
    }
  }
  
  public View getView()
  {
    return this;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824));
  }
  
  public void setContent(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId)
  {
    if ((paramKeyContent instanceof TextContent))
    {
      String str = ((TextContent)paramKeyContent).getLabel();
      if (str != getText())
      {
        setTag(str);
        setText(str);
        CandidateTextUtils.setFontSize(this, str, this.mMaximumTextSize);
      }
    }
    this.mStyleId = paramStyleId;
  }
  
  public void setThemeAttributes(Drawable paramDrawable)
  {
    setBackgroundDrawable(paramDrawable);
    setTextPaintAttributes();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.OneCandidateViewCompatibility
 * JD-Core Version:    0.7.0.1
 */