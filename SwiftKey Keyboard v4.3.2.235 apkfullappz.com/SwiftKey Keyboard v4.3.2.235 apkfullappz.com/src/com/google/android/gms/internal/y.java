package com.google.android.gms.internal;

import android.net.Uri;
import android.net.Uri.Builder;

public final class y
{
  private static final Uri bb = new Uri.Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("drawable").build();
  
  public static Uri i(String paramString)
  {
    x.b(paramString, "Resource name must not be null.");
    return bb.buildUpon().appendPath(paramString).build();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.y
 * JD-Core Version:    0.7.0.1
 */