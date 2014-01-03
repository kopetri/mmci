package com.facebook.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility
{
  private static final String APPLICATION_FIELDS = "fields";
  public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
  private static final String HASH_ALGORITHM_MD5 = "MD5";
  private static final Object LOCK = new Object();
  static final String LOG_TAG = "FacebookSDK";
  private static final String SUPPORTS_ATTRIBUTION = "supports_attribution";
  private static final String URL_SCHEME = "https";
  private static volatile boolean attributionAllowedForLastAppChecked = false;
  private static volatile String lastAppCheckedForAttributionStatus = "";
  
  public static <T> ArrayList<T> arrayList(T... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(paramVarArgs[j]);
    }
    return localArrayList;
  }
  
  public static Uri buildUri(String paramString1, String paramString2, Bundle paramBundle)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("https");
    localBuilder.authority(paramString1);
    localBuilder.path(paramString2);
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if ((localObject instanceof String)) {
        localBuilder.appendQueryParameter(str, (String)localObject);
      }
    }
    return localBuilder.build();
  }
  
  private static void clearCookiesForDomain(Context paramContext, String paramString)
  {
    CookieSyncManager.createInstance(paramContext).sync();
    CookieManager localCookieManager = CookieManager.getInstance();
    String str = localCookieManager.getCookie(paramString);
    if (str == null) {
      return;
    }
    String[] arrayOfString1 = str.split(";");
    int i = arrayOfString1.length;
    for (int j = 0; j < i; j++)
    {
      String[] arrayOfString2 = arrayOfString1[j].split("=");
      if (arrayOfString2.length > 0) {
        localCookieManager.setCookie(paramString, arrayOfString2[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
      }
    }
    localCookieManager.removeExpiredCookie();
  }
  
  public static void clearFacebookCookies(Context paramContext)
  {
    clearCookiesForDomain(paramContext, "facebook.com");
    clearCookiesForDomain(paramContext, ".facebook.com");
    clearCookiesForDomain(paramContext, "https://facebook.com");
    clearCookiesForDomain(paramContext, "https://.facebook.com");
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException) {}
  }
  
  static Map<String, Object> convertJSONObjectToHashMap(JSONObject paramJSONObject)
  {
    HashMap localHashMap = new HashMap();
    JSONArray localJSONArray = paramJSONObject.names();
    int i = 0;
    while (i < localJSONArray.length()) {
      try
      {
        String str = localJSONArray.getString(i);
        Object localObject = paramJSONObject.get(str);
        if ((localObject instanceof JSONObject)) {
          localObject = convertJSONObjectToHashMap((JSONObject)localObject);
        }
        localHashMap.put(str, localObject);
        label65:
        i++;
      }
      catch (JSONException localJSONException)
      {
        break label65;
      }
    }
    return localHashMap;
  }
  
  public static void disconnectQuietly(URLConnection paramURLConnection)
  {
    if ((paramURLConnection instanceof HttpURLConnection)) {
      ((HttpURLConnection)paramURLConnection).disconnect();
    }
  }
  
  public static String getMetadataApplicationId(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo.metaData != null)
      {
        String str = localApplicationInfo.metaData.getString("com.facebook.sdk.ApplicationId");
        return str;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public static Object getStringPropertyAsJSON(JSONObject paramJSONObject, String paramString1, String paramString2)
    throws JSONException
  {
    Object localObject = paramJSONObject.opt(paramString1);
    if ((localObject != null) && ((localObject instanceof String))) {
      localObject = new JSONTokener((String)localObject).nextValue();
    }
    if ((localObject != null) && (!(localObject instanceof JSONObject)) && (!(localObject instanceof JSONArray)))
    {
      if (paramString2 != null)
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.putOpt(paramString2, localObject);
        return localJSONObject;
      }
      throw new FacebookException("Got an unexpected non-JSON object.");
    }
    return localObject;
  }
  
  public static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  public static <T> boolean isNullOrEmpty(Collection<T> paramCollection)
  {
    return (paramCollection == null) || (paramCollection.size() == 0);
  }
  
  public static <T> boolean isSubset(Collection<T> paramCollection1, Collection<T> paramCollection2)
  {
    if ((paramCollection2 == null) || (paramCollection2.size() == 0))
    {
      boolean bool;
      if (paramCollection1 != null)
      {
        int i = paramCollection1.size();
        bool = false;
        if (i != 0) {}
      }
      else
      {
        bool = true;
      }
      return bool;
    }
    HashSet localHashSet = new HashSet(paramCollection2);
    Iterator localIterator = paramCollection1.iterator();
    while (localIterator.hasNext()) {
      if (!localHashSet.contains(localIterator.next())) {
        return false;
      }
    }
    return true;
  }
  
  public static void logd(String paramString, Exception paramException) {}
  
  public static void logd(String paramString1, String paramString2) {}
  
  static String md5hash(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuilder localStringBuilder = new StringBuilder();
      int i = arrayOfByte.length;
      for (int j = 0; j < i; j++)
      {
        int k = arrayOfByte[j];
        localStringBuilder.append(Integer.toHexString(0xF & k >> 4));
        localStringBuilder.append(Integer.toHexString(0xF & k >> 0));
      }
      return localStringBuilder.toString();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      return null;
    }
  }
  
  public static void putObjectInBundle(Bundle paramBundle, String paramString, Object paramObject)
  {
    if ((paramObject instanceof String))
    {
      paramBundle.putString(paramString, (String)paramObject);
      return;
    }
    if ((paramObject instanceof Parcelable))
    {
      paramBundle.putParcelable(paramString, (Parcelable)paramObject);
      return;
    }
    if ((paramObject instanceof byte[]))
    {
      paramBundle.putByteArray(paramString, (byte[])paramObject);
      return;
    }
    throw new FacebookException("attempted to add unsupported type to Bundle");
  }
  
  public static boolean queryAppAttributionSupportAndWait(String paramString)
  {
    for (;;)
    {
      Object localObject3;
      synchronized (LOCK)
      {
        if (paramString.equals(lastAppCheckedForAttributionStatus))
        {
          boolean bool2 = attributionAllowedForLastAppChecked;
          return bool2;
        }
        Bundle localBundle = new Bundle();
        localBundle.putString("fields", "supports_attribution");
        Request localRequest = Request.newGraphPathRequest(null, paramString, null);
        localRequest.setParameters(localBundle);
        GraphObject localGraphObject = localRequest.executeAndWait().getGraphObject();
        localObject3 = Boolean.valueOf(false);
        if (localGraphObject != null) {
          localObject3 = localGraphObject.getProperty("supports_attribution");
        }
        if ((localObject3 instanceof Boolean)) {
          break label142;
        }
        localObject4 = Boolean.valueOf(false);
        lastAppCheckedForAttributionStatus = paramString;
        if (((Boolean)localObject4).booleanValue() == true)
        {
          bool1 = true;
          attributionAllowedForLastAppChecked = bool1;
          return bool1;
        }
      }
      boolean bool1 = false;
      continue;
      label142:
      Object localObject4 = localObject3;
    }
  }
  
  /* Error */
  public static String readStreamToString(java.io.InputStream paramInputStream)
    throws IOException
  {
    // Byte code:
    //   0: new 373	java/io/BufferedInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 376	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   8: astore_1
    //   9: new 378	java/io/InputStreamReader
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 379	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   17: astore_2
    //   18: new 134	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   25: astore_3
    //   26: sipush 2048
    //   29: newarray char
    //   31: astore 7
    //   33: aload_2
    //   34: aload 7
    //   36: invokevirtual 383	java/io/InputStreamReader:read	([C)I
    //   39: istore 8
    //   41: iload 8
    //   43: iconst_m1
    //   44: if_icmpeq +37 -> 81
    //   47: aload_3
    //   48: aload 7
    //   50: iconst_0
    //   51: iload 8
    //   53: invokevirtual 386	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: goto -24 -> 33
    //   60: astore 4
    //   62: aload_2
    //   63: astore 5
    //   65: aload_1
    //   66: astore 6
    //   68: aload 6
    //   70: invokestatic 388	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   73: aload 5
    //   75: invokestatic 388	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   78: aload 4
    //   80: athrow
    //   81: aload_3
    //   82: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   85: astore 10
    //   87: aload_1
    //   88: invokestatic 388	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   91: aload_2
    //   92: invokestatic 388	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   95: aload 10
    //   97: areturn
    //   98: astore 4
    //   100: aconst_null
    //   101: astore 6
    //   103: aconst_null
    //   104: astore 5
    //   106: goto -38 -> 68
    //   109: astore 4
    //   111: aload_1
    //   112: astore 6
    //   114: aconst_null
    //   115: astore 5
    //   117: goto -49 -> 68
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	120	0	paramInputStream	java.io.InputStream
    //   8	104	1	localBufferedInputStream1	java.io.BufferedInputStream
    //   17	75	2	localInputStreamReader1	java.io.InputStreamReader
    //   25	57	3	localStringBuilder	StringBuilder
    //   60	19	4	localObject1	Object
    //   98	1	4	localObject2	Object
    //   109	1	4	localObject3	Object
    //   63	53	5	localInputStreamReader2	java.io.InputStreamReader
    //   66	47	6	localBufferedInputStream2	java.io.BufferedInputStream
    //   31	18	7	arrayOfChar	char[]
    //   39	13	8	i	int
    //   85	11	10	str	String
    // Exception table:
    //   from	to	target	type
    //   18	33	60	finally
    //   33	41	60	finally
    //   47	57	60	finally
    //   81	87	60	finally
    //   0	9	98	finally
    //   9	18	109	finally
  }
  
  public static boolean stringsEqualOrEmpty(String paramString1, String paramString2)
  {
    boolean bool1 = TextUtils.isEmpty(paramString1);
    boolean bool2 = TextUtils.isEmpty(paramString2);
    if ((bool1) && (bool2)) {
      return true;
    }
    if ((!bool1) && (!bool2)) {
      return paramString1.equals(paramString2);
    }
    return false;
  }
  
  public static <T> Collection<T> unmodifiableCollection(T... paramVarArgs)
  {
    return Collections.unmodifiableCollection(Arrays.asList(paramVarArgs));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.internal.Utility
 * JD-Core Version:    0.7.0.1
 */