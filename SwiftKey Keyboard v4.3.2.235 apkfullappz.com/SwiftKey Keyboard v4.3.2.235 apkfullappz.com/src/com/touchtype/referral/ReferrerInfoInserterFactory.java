package com.touchtype.referral;

import android.content.Context;
import com.touchtype.util.LogUtil;

public final class ReferrerInfoInserterFactory
{
  public static ReferrerInfoInserter inserter(Context paramContext, String paramString1, String paramString2)
  {
    if (paramString1.equals("google")) {
      try
      {
        GoogleReferrerInfoInserter localGoogleReferrerInfoInserter = new GoogleReferrerInfoInserter(paramContext, (ReferrerInfo)Class.forName(paramString2).newInstance());
        return localGoogleReferrerInfoInserter;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = localClassNotFoundException.toString();
        LogUtil.e("REFERRERINFOINSERTERFACTORY", String.format("inserter() - referrerInfo class error: %s", arrayOfObject3));
        return new NullReferrerInfoInserter();
      }
      catch (InstantiationException localInstantiationException)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localInstantiationException.toString();
        LogUtil.e("REFERRERINFOINSERTERFACTORY", String.format("inserter() - referrerInfo class error: %s", arrayOfObject2));
        return new NullReferrerInfoInserter();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localIllegalAccessException.toString();
        LogUtil.e("REFERRERINFOINSERTERFACTORY", String.format("inserter() - referrerInfo class error: %s", arrayOfObject1));
        return new NullReferrerInfoInserter();
      }
    }
    return new NullReferrerInfoInserter();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.ReferrerInfoInserterFactory
 * JD-Core Version:    0.7.0.1
 */