package com.touchtype.logcat;

import android.content.Context;
import android.widget.Toast;
import java.io.File;

public final class LogcatAWSResponseHandler
{
  public static void handleAWSResponse(Context paramContext, Integer paramInteger, File paramFile1, File paramFile2)
  {
    if ((paramInteger.intValue() >= 200) && (paramInteger.intValue() < 300)) {
      handleSuccessfulAWSResponse(paramContext, paramFile1, paramFile2);
    }
    for (String str = paramContext.getString(2131296840);; str = paramContext.getString(2131296841))
    {
      Toast.makeText(paramContext, str, 0).show();
      return;
    }
  }
  
  private static void handleSuccessfulAWSResponse(Context paramContext, File paramFile1, File paramFile2)
  {
    LogcatDumper.createNewLogFile(paramContext, LogcatDumper.getNewLogFile(paramContext));
    paramFile1.delete();
    paramFile2.delete();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.logcat.LogcatAWSResponseHandler
 * JD-Core Version:    0.7.0.1
 */