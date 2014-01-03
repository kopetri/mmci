package com.google.android.gms.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.internal.aa;
import com.google.android.gms.internal.x;
import com.google.android.gms.internal.z;
import com.google.android.gms.internal.z.a;

public final class SignInButton
  extends FrameLayout
  implements View.OnClickListener
{
  private int K;
  private int L;
  private View M;
  private View.OnClickListener N = null;
  
  public SignInButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SignInButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setStyle(0, 0);
  }
  
  private static Button c(Context paramContext, int paramInt1, int paramInt2)
  {
    aa localaa = new aa(paramContext);
    localaa.a(paramContext.getResources(), paramInt1, paramInt2);
    return localaa;
  }
  
  private void d(Context paramContext)
  {
    if (this.M != null) {
      removeView(this.M);
    }
    try
    {
      this.M = z.d(paramContext, this.K, this.L);
      addView(this.M);
      this.M.setEnabled(isEnabled());
      this.M.setOnClickListener(this);
      return;
    }
    catch (z.a locala)
    {
      for (;;)
      {
        Log.w("SignInButton", "Sign in button not found, using placeholder instead");
        this.M = c(paramContext, this.K, this.L);
      }
    }
  }
  
  public void onClick(View paramView)
  {
    if ((this.N != null) && (paramView == this.M)) {
      this.N.onClick(this);
    }
  }
  
  public void setColorScheme(int paramInt)
  {
    setStyle(this.K, paramInt);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.M.setEnabled(paramBoolean);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.N = paramOnClickListener;
    if (this.M != null) {
      this.M.setOnClickListener(this);
    }
  }
  
  public void setSize(int paramInt)
  {
    setStyle(paramInt, this.L);
  }
  
  public void setStyle(int paramInt1, int paramInt2)
  {
    boolean bool1 = true;
    boolean bool2;
    if ((paramInt1 >= 0) && (paramInt1 < 3))
    {
      bool2 = bool1;
      x.a(bool2, "Unknown button size " + paramInt1);
      if ((paramInt2 < 0) || (paramInt2 >= 2)) {
        break label89;
      }
    }
    for (;;)
    {
      x.a(bool1, "Unknown color scheme " + paramInt2);
      this.K = paramInt1;
      this.L = paramInt2;
      d(getContext());
      return;
      bool2 = false;
      break;
      label89:
      bool1 = false;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.SignInButton
 * JD-Core Version:    0.7.0.1
 */