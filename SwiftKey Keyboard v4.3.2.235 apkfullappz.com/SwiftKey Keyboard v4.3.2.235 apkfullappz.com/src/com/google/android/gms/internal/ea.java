package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public final class ea
  extends FrameLayout
{
  private String[] hS = null;
  private final ImageView hT;
  private final TextView hU;
  
  public ea(Context paramContext)
  {
    super(paramContext);
    this.hT = new ImageView(paramContext);
    addView(this.hT, new FrameLayout.LayoutParams(-2, -2, 17));
    this.hU = new TextView(paramContext);
    addView(this.hU, new FrameLayout.LayoutParams(-2, -2, 17));
    bringChildToFront(this.hU);
  }
  
  public void b(Uri paramUri)
  {
    this.hT.setImageURI(paramUri);
  }
  
  public void f(String[] paramArrayOfString)
  {
    this.hS = paramArrayOfString;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    Paint localPaint = new Paint();
    localPaint.setTextSize(this.hU.getTextSize());
    localPaint.setTypeface(this.hU.getTypeface());
    int j;
    int k;
    int m;
    String str;
    label62:
    int n;
    if (this.hS != null)
    {
      j = this.hS.length;
      k = 0;
      m = 0;
      str = null;
      if (k >= j) {
        break label132;
      }
      if (this.hS[k] == null) {
        break label168;
      }
      n = (int)localPaint.measureText(this.hS[k]);
      if ((n > i) || (n < m)) {
        break label168;
      }
      str = this.hS[k];
    }
    for (;;)
    {
      k++;
      m = n;
      break label62;
      j = 0;
      break;
      label132:
      if ((str == null) || (!str.equals(this.hU.getText()))) {
        this.hU.setText(str);
      }
      super.onMeasure(paramInt1, paramInt2);
      return;
      label168:
      n = m;
    }
  }
  
  public void setGravity(int paramInt)
  {
    this.hU.setGravity(paramInt);
  }
  
  public void setSingleLine()
  {
    this.hU.setSingleLine();
  }
  
  public void setTextColor(int paramInt)
  {
    this.hU.setTextColor(paramInt);
  }
  
  public void setTextSize(int paramInt, float paramFloat)
  {
    this.hU.setTextSize(paramInt, paramFloat);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ea
 * JD-Core Version:    0.7.0.1
 */