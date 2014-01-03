package com.touchtype.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class IntentWrapper
{
  private static void addFlagsToIntent(Intent paramIntent)
  {
    paramIntent.setFlags(65536);
    paramIntent.addFlags(67108864);
    paramIntent.addFlags(268435456);
    paramIntent.addFlags(32768);
  }
  
  public static void launchIntent(Context paramContext, Uri paramUri)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(paramUri);
    addFlagsToIntent(localIntent);
    startIntent(paramContext, localIntent);
  }
  
  public static void launchIntent(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramContext.getPackageName(), paramString);
    addFlagsToIntent(localIntent);
    startIntent(paramContext, localIntent);
  }
  
  private static void startIntent(Context paramContext, Intent paramIntent)
  {
    try
    {
      paramContext.startActivity(paramIntent);
      return;
    }
    catch (IllegalStateException localIllegalStateException) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.IntentWrapper
 * JD-Core Version:    0.7.0.1
 */