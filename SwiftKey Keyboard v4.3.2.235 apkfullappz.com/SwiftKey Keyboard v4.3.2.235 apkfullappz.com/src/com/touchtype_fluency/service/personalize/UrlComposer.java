package com.touchtype_fluency.service.personalize;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.common.base.Joiner;
import com.touchtype.util.LogUtil;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import junit.framework.Assert;

public class UrlComposer
{
  private static final String TAG = UrlComposer.class.getSimpleName();
  private final String mApiVersion;
  private String mEstablishedUrl = null;
  private final String mGuid;
  private final String mLoadBalanceUrl;
  private final String mServicePath;
  
  public UrlComposer(String paramString1, String paramString2, Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    this.mApiVersion = localResources.getString(2131296325);
    this.mLoadBalanceUrl = localResources.getString(2131296324);
    this.mGuid = (paramContext.getPackageName().replace(".", "_") + "-" + paramString1);
    this.mServicePath = paramString2;
  }
  
  private String makeFullUrl(String paramString)
  {
    Joiner localJoiner = Joiner.on("/");
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.mApiVersion;
    arrayOfObject[1] = this.mGuid;
    arrayOfObject[2] = this.mServicePath;
    return localJoiner.join(paramString, "v", arrayOfObject);
  }
  
  public String addAuthToParams(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer;
    try
    {
      byte[] arrayOfByte = MessageDigest.getInstance("SHA-1").digest((paramString2 + "-" + this.mGuid).getBytes("UTF-8"));
      localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfByte.length; i++)
      {
        String str = Integer.toHexString(0xFF & arrayOfByte[i]);
        if (str.length() == 1) {
          localStringBuffer.append('0');
        }
        localStringBuffer.append(str);
      }
      if (paramString1 == null) {
        break label160;
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      LogUtil.e(TAG, "No SHA-1 algorithm found", localNoSuchAlgorithmException);
      return paramString1;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      LogUtil.e(TAG, localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
      return paramString1;
    }
    if (paramString1.equals("")) {
      label160:
      return "?auth=" + localStringBuffer.toString();
    }
    return paramString1 + "&auth=" + localStringBuffer.toString();
  }
  
  public String addLocaleToParams(String paramString, Set<String> paramSet)
    throws UnsupportedEncodingException
  {
    Locale localLocale = Locale.getDefault();
    String str6;
    String str5;
    if (localLocale != null)
    {
      String str3 = localLocale.getCountry();
      if (!TextUtils.isEmpty(str3))
      {
        StringBuilder localStringBuilder3 = new StringBuilder().append(paramString);
        if (!TextUtils.isEmpty(paramString)) {
          break label222;
        }
        str6 = "?";
        paramString = str6 + "locale_country=" + str3;
      }
      String str4 = localLocale.getLanguage();
      if (!TextUtils.isEmpty(str4))
      {
        StringBuilder localStringBuilder2 = new StringBuilder().append(paramString);
        if (!TextUtils.isEmpty(paramString)) {
          break label229;
        }
        str5 = "?";
        label105:
        paramString = str5 + "locale_language=" + str4;
      }
    }
    if ((paramSet != null) && (paramSet.size() > 0))
    {
      Iterator localIterator = paramSet.iterator();
      label147:
      if (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        StringBuilder localStringBuilder1 = new StringBuilder().append(paramString);
        if (TextUtils.isEmpty(paramString)) {}
        for (String str2 = "?";; str2 = "&")
        {
          paramString = str2 + "languages_enabled[]=" + URLEncoder.encode(str1, "UTF-8");
          break label147;
          label222:
          str6 = "&";
          break;
          label229:
          str5 = "&";
          break label105;
        }
      }
    }
    return paramString;
  }
  
  public String addTextRetentionToParams(String paramString, boolean paramBoolean)
  {
    return paramString + "&retain_text=" + paramBoolean;
  }
  
  public String getDeleteUrlString()
  {
    Joiner localJoiner = Joiner.on("/");
    String str = this.mLoadBalanceUrl;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mApiVersion;
    arrayOfObject[1] = this.mGuid;
    return localJoiner.join(str, "v", arrayOfObject);
  }
  
  public URL getEstablishedUrl()
  {
    try
    {
      URL localURL1 = new URL(getEstablishedUrlString());
      localURL2 = localURL1;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Error formatting URL " + getEstablishedUrlString(), localMalformedURLException);
        URL localURL2 = null;
      }
    }
    Assert.assertNotNull(localURL2);
    return localURL2;
  }
  
  public String getEstablishedUrlString()
  {
    if (this.mEstablishedUrl != null) {}
    for (String str = this.mEstablishedUrl;; str = this.mLoadBalanceUrl) {
      return makeFullUrl(str);
    }
  }
  
  public String getGUID()
  {
    return this.mGuid;
  }
  
  public URL getInitialUrl()
  {
    try
    {
      URL localURL = new URL(getInitialUrlString());
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      LogUtil.e(TAG, "Error formatting URL " + getEstablishedUrlString(), localMalformedURLException);
    }
    return null;
  }
  
  public String getInitialUrlString()
  {
    return makeFullUrl(this.mLoadBalanceUrl);
  }
  
  public void setEstablishedUrl(String paramString)
  {
    this.mEstablishedUrl = paramString;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.UrlComposer
 * JD-Core Version:    0.7.0.1
 */