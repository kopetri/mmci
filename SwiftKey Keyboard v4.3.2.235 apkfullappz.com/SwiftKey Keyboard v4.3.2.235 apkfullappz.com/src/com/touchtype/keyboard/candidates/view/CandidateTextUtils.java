package com.touchtype.keyboard.candidates.view;

import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.TextView;

public final class CandidateTextUtils
{
  public static void setFontSize(TextView paramTextView, CharSequence paramCharSequence, int paramInt)
  {
    int i = paramTextView.getWidth() - paramTextView.getTotalPaddingLeft() - paramTextView.getTotalPaddingRight();
    int j = Math.min(paramInt, paramTextView.getHeight() - paramTextView.getPaddingTop() - paramTextView.getPaddingBottom());
    String str = paramCharSequence.toString();
    if (str.length() > 0)
    {
      TextPaint localTextPaint = paramTextView.getPaint();
      float f1 = j;
      localTextPaint.setTextSize(f1);
      float f2 = localTextPaint.measureText(str);
      if (f2 >= i) {
        f1 = (float)Math.floor(j * i / f2);
      }
      paramTextView.setTextSize(0, f1);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.CandidateTextUtils
 * JD-Core Version:    0.7.0.1
 */