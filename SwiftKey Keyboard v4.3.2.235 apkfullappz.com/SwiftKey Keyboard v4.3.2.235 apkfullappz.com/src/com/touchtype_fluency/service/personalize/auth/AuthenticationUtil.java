package com.touchtype_fluency.service.personalize.auth;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

public final class AuthenticationUtil
{
  public static final String ACCOUNT_NAME = "account_name";
  public static final String CALLER_CALLBACK = "Caller Callback";
  public static final String CALLER_ID = "Caller Id";
  public static final String CALLER_SCOPES = "Caller Scopes";
  public static final String PARAMS = "params";
  public static final int REQUEST_CODE_RECOVER_FROM_GOOGLE_AUTH_ERROR = 1001;
  public static final String SESSION = "session";
  
  public static String computeSHA1Signature(String paramString1, String paramString2)
    throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramString2.getBytes("UTF-8"), "HmacSHA1");
    Mac localMac = Mac.getInstance("HmacSHA1");
    localMac.init(localSecretKeySpec);
    return new String(Base64.encode(localMac.doFinal(paramString1.getBytes("UTF-8")), 0)).trim();
  }
  
  public static String extractParameterValue(String paramString1, String paramString2)
  {
    String str = "";
    int i = paramString1.indexOf(paramString2);
    if (i != -1)
    {
      int j = paramString1.indexOf('&', i);
      if (j == -1) {
        j = paramString1.length();
      }
      str = paramString1.substring(1 + (i + paramString2.length()), j);
    }
    return str;
  }
  
  public static String getPOSTedAccessToken(String paramString)
  {
    try
    {
      String str = new JSONObject(getPostData(paramString)).getString("access_token");
      return str;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  /* Error */
  public static String getPostData(String paramString)
  {
    // Byte code:
    //   0: new 121	org/apache/http/client/methods/HttpPost
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 122	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   8: astore_1
    //   9: aconst_null
    //   10: invokestatic 128	com/touchtype_fluency/service/http/SSLClientFactory:createHttpClient	(Lorg/apache/http/params/HttpParams;)Lorg/apache/http/client/HttpClient;
    //   13: astore_2
    //   14: aload_2
    //   15: aload_1
    //   16: invokeinterface 134 2 0
    //   21: invokeinterface 140 1 0
    //   26: invokestatic 146	org/apache/http/util/EntityUtils:toString	(Lorg/apache/http/HttpEntity;)Ljava/lang/String;
    //   29: astore 6
    //   31: aload_2
    //   32: invokeinterface 150 1 0
    //   37: invokeinterface 155 1 0
    //   42: aload 6
    //   44: areturn
    //   45: astore 5
    //   47: aload_2
    //   48: invokeinterface 150 1 0
    //   53: invokeinterface 155 1 0
    //   58: aconst_null
    //   59: areturn
    //   60: astore 4
    //   62: aload_2
    //   63: invokeinterface 150 1 0
    //   68: invokeinterface 155 1 0
    //   73: aconst_null
    //   74: areturn
    //   75: astore_3
    //   76: aload_2
    //   77: invokeinterface 150 1 0
    //   82: invokeinterface 155 1 0
    //   87: aload_3
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	paramString	String
    //   8	8	1	localHttpPost	org.apache.http.client.methods.HttpPost
    //   13	64	2	localHttpClient	org.apache.http.client.HttpClient
    //   75	13	3	localObject	Object
    //   60	1	4	localIOException	java.io.IOException
    //   45	1	5	localClientProtocolException	org.apache.http.client.ClientProtocolException
    //   29	14	6	str	String
    // Exception table:
    //   from	to	target	type
    //   14	31	45	org/apache/http/client/ClientProtocolException
    //   14	31	60	java/io/IOException
    //   14	31	75	finally
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.AuthenticationUtil
 * JD-Core Version:    0.7.0.1
 */