package oauth.signpost;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public abstract class AbstractOAuthProvider
  implements OAuthProvider
{
  private String accessTokenEndpointUrl;
  private String authorizationWebsiteUrl;
  private Map<String, String> defaultHeaders;
  private boolean isOAuth10a;
  private transient OAuthProviderListener listener;
  private String requestTokenEndpointUrl;
  private HttpParameters responseParameters;
  
  public AbstractOAuthProvider(String paramString1, String paramString2, String paramString3)
  {
    this.requestTokenEndpointUrl = paramString1;
    this.accessTokenEndpointUrl = paramString2;
    this.authorizationWebsiteUrl = paramString3;
    this.responseParameters = new HttpParameters();
    this.defaultHeaders = new HashMap();
  }
  
  protected void closeConnection(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse)
    throws Exception
  {}
  
  protected abstract HttpRequest createRequest(String paramString)
    throws Exception;
  
  public Map<String, String> getRequestHeaders()
  {
    return this.defaultHeaders;
  }
  
  public HttpParameters getResponseParameters()
  {
    return this.responseParameters;
  }
  
  protected void handleUnexpectedResponse(int paramInt, HttpResponse paramHttpResponse)
    throws Exception
  {
    if (paramHttpResponse == null) {
      return;
    }
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramHttpResponse.getContent()));
    StringBuilder localStringBuilder = new StringBuilder();
    for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine()) {
      localStringBuilder.append(str);
    }
    switch (paramInt)
    {
    default: 
      throw new OAuthCommunicationException("Service provider responded in error: " + paramInt + " (" + paramHttpResponse.getReasonPhrase() + ")", localStringBuilder.toString());
    }
    throw new OAuthNotAuthorizedException(localStringBuilder.toString());
  }
  
  public void retrieveAccessToken(OAuthConsumer paramOAuthConsumer, String paramString)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    if ((paramOAuthConsumer.getToken() == null) || (paramOAuthConsumer.getTokenSecret() == null)) {
      throw new OAuthExpectationFailedException("Authorized request token or token secret not set. Did you retrieve an authorized request token before?");
    }
    if ((this.isOAuth10a) && (paramString != null))
    {
      retrieveToken(paramOAuthConsumer, this.accessTokenEndpointUrl, new String[] { "oauth_verifier", paramString });
      return;
    }
    retrieveToken(paramOAuthConsumer, this.accessTokenEndpointUrl, new String[0]);
  }
  
  public String retrieveRequestToken(OAuthConsumer paramOAuthConsumer, String paramString)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    paramOAuthConsumer.setTokenWithSecret(null, null);
    retrieveToken(paramOAuthConsumer, this.requestTokenEndpointUrl, new String[] { "oauth_callback", paramString });
    String str1 = this.responseParameters.getFirst("oauth_callback_confirmed");
    this.responseParameters.remove("oauth_callback_confirmed");
    this.isOAuth10a = Boolean.TRUE.toString().equals(str1);
    if (this.isOAuth10a)
    {
      String str3 = this.authorizationWebsiteUrl;
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = "oauth_token";
      arrayOfString2[1] = paramOAuthConsumer.getToken();
      return OAuth.addQueryParameters(str3, arrayOfString2);
    }
    String str2 = this.authorizationWebsiteUrl;
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "oauth_token";
    arrayOfString1[1] = paramOAuthConsumer.getToken();
    arrayOfString1[2] = "oauth_callback";
    arrayOfString1[3] = paramString;
    return OAuth.addQueryParameters(str2, arrayOfString1);
  }
  
  /* Error */
  protected void retrieveToken(OAuthConsumer paramOAuthConsumer, String paramString, String... paramVarArgs)
    throws OAuthMessageSignerException, OAuthCommunicationException, OAuthNotAuthorizedException, OAuthExpectationFailedException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 170	oauth/signpost/AbstractOAuthProvider:getRequestHeaders	()Ljava/util/Map;
    //   4: astore 4
    //   6: aload_1
    //   7: invokeinterface 173 1 0
    //   12: ifnull +12 -> 24
    //   15: aload_1
    //   16: invokeinterface 176 1 0
    //   21: ifnonnull +13 -> 34
    //   24: new 111	oauth/signpost/exception/OAuthExpectationFailedException
    //   27: dup
    //   28: ldc 178
    //   30: invokespecial 122	oauth/signpost/exception/OAuthExpectationFailedException:<init>	(Ljava/lang/String;)V
    //   33: athrow
    //   34: aconst_null
    //   35: astore 5
    //   37: aconst_null
    //   38: astore 6
    //   40: aload_0
    //   41: aload_2
    //   42: invokevirtual 180	oauth/signpost/AbstractOAuthProvider:createRequest	(Ljava/lang/String;)Loauth/signpost/http/HttpRequest;
    //   45: astore 5
    //   47: aload 4
    //   49: invokeinterface 186 1 0
    //   54: invokeinterface 192 1 0
    //   59: astore 12
    //   61: aload 12
    //   63: invokeinterface 198 1 0
    //   68: istore 13
    //   70: aconst_null
    //   71: astore 6
    //   73: iload 13
    //   75: ifeq +57 -> 132
    //   78: aload 12
    //   80: invokeinterface 202 1 0
    //   85: checkcast 126	java/lang/String
    //   88: astore 14
    //   90: aload 5
    //   92: aload 14
    //   94: aload 4
    //   96: aload 14
    //   98: invokeinterface 206 2 0
    //   103: checkcast 126	java/lang/String
    //   106: invokeinterface 211 3 0
    //   111: goto -50 -> 61
    //   114: astore 11
    //   116: aload 11
    //   118: athrow
    //   119: astore 8
    //   121: aload_0
    //   122: aload 5
    //   124: aload 6
    //   126: invokevirtual 213	oauth/signpost/AbstractOAuthProvider:closeConnection	(Loauth/signpost/http/HttpRequest;Loauth/signpost/http/HttpResponse;)V
    //   129: aload 8
    //   131: athrow
    //   132: aload_3
    //   133: ifnull +27 -> 160
    //   136: new 32	oauth/signpost/http/HttpParameters
    //   139: dup
    //   140: invokespecial 33	oauth/signpost/http/HttpParameters:<init>	()V
    //   143: astore 31
    //   145: aload 31
    //   147: aload_3
    //   148: iconst_1
    //   149: invokevirtual 217	oauth/signpost/http/HttpParameters:putAll	([Ljava/lang/String;Z)V
    //   152: aload_1
    //   153: aload 31
    //   155: invokeinterface 221 2 0
    //   160: aload_0
    //   161: getfield 223	oauth/signpost/AbstractOAuthProvider:listener	Loauth/signpost/OAuthProviderListener;
    //   164: astore 15
    //   166: aconst_null
    //   167: astore 6
    //   169: aload 15
    //   171: ifnull +8 -> 179
    //   174: aload_0
    //   175: getfield 223	oauth/signpost/AbstractOAuthProvider:listener	Loauth/signpost/OAuthProviderListener;
    //   178: pop
    //   179: aload_1
    //   180: aload 5
    //   182: invokeinterface 227 2 0
    //   187: pop
    //   188: aload_0
    //   189: getfield 223	oauth/signpost/AbstractOAuthProvider:listener	Loauth/signpost/OAuthProviderListener;
    //   192: astore 18
    //   194: aconst_null
    //   195: astore 6
    //   197: aload 18
    //   199: ifnull +8 -> 207
    //   202: aload_0
    //   203: getfield 223	oauth/signpost/AbstractOAuthProvider:listener	Loauth/signpost/OAuthProviderListener;
    //   206: pop
    //   207: aload_0
    //   208: aload 5
    //   210: invokevirtual 231	oauth/signpost/AbstractOAuthProvider:sendRequest	(Loauth/signpost/http/HttpRequest;)Loauth/signpost/http/HttpResponse;
    //   213: astore 6
    //   215: aload 6
    //   217: invokeinterface 235 1 0
    //   222: istore 20
    //   224: aload_0
    //   225: getfield 223	oauth/signpost/AbstractOAuthProvider:listener	Loauth/signpost/OAuthProviderListener;
    //   228: astore 21
    //   230: iconst_0
    //   231: istore 22
    //   233: aload 21
    //   235: ifnull +22 -> 257
    //   238: aload_0
    //   239: getfield 223	oauth/signpost/AbstractOAuthProvider:listener	Loauth/signpost/OAuthProviderListener;
    //   242: aload 5
    //   244: aload 6
    //   246: invokeinterface 241 3 0
    //   251: istore 23
    //   253: iload 23
    //   255: istore 22
    //   257: iload 22
    //   259: ifeq +24 -> 283
    //   262: aload_0
    //   263: aload 5
    //   265: aload 6
    //   267: invokevirtual 213	oauth/signpost/AbstractOAuthProvider:closeConnection	(Loauth/signpost/http/HttpRequest;Loauth/signpost/http/HttpResponse;)V
    //   270: return
    //   271: astore 30
    //   273: new 81	oauth/signpost/exception/OAuthCommunicationException
    //   276: dup
    //   277: aload 30
    //   279: invokespecial 244	oauth/signpost/exception/OAuthCommunicationException:<init>	(Ljava/lang/Exception;)V
    //   282: athrow
    //   283: iload 20
    //   285: sipush 300
    //   288: if_icmplt +11 -> 299
    //   291: aload_0
    //   292: iload 20
    //   294: aload 6
    //   296: invokevirtual 246	oauth/signpost/AbstractOAuthProvider:handleUnexpectedResponse	(ILoauth/signpost/http/HttpResponse;)V
    //   299: aload 6
    //   301: invokeinterface 62 1 0
    //   306: invokestatic 250	oauth/signpost/OAuth:decodeForm	(Ljava/io/InputStream;)Loauth/signpost/http/HttpParameters;
    //   309: astore 24
    //   311: aload 24
    //   313: ldc 162
    //   315: invokevirtual 145	oauth/signpost/http/HttpParameters:getFirst	(Ljava/lang/Object;)Ljava/lang/String;
    //   318: astore 25
    //   320: aload 24
    //   322: ldc 252
    //   324: invokevirtual 145	oauth/signpost/http/HttpParameters:getFirst	(Ljava/lang/Object;)Ljava/lang/String;
    //   327: astore 26
    //   329: aload 24
    //   331: ldc 162
    //   333: invokevirtual 149	oauth/signpost/http/HttpParameters:remove	(Ljava/lang/Object;)Ljava/util/SortedSet;
    //   336: pop
    //   337: aload 24
    //   339: ldc 252
    //   341: invokevirtual 149	oauth/signpost/http/HttpParameters:remove	(Ljava/lang/Object;)Ljava/util/SortedSet;
    //   344: pop
    //   345: aload_0
    //   346: aload 24
    //   348: invokevirtual 255	oauth/signpost/AbstractOAuthProvider:setResponseParameters	(Loauth/signpost/http/HttpParameters;)V
    //   351: aload 25
    //   353: ifnull +8 -> 361
    //   356: aload 26
    //   358: ifnonnull +19 -> 377
    //   361: new 111	oauth/signpost/exception/OAuthExpectationFailedException
    //   364: dup
    //   365: ldc_w 257
    //   368: invokespecial 122	oauth/signpost/exception/OAuthExpectationFailedException:<init>	(Ljava/lang/String;)V
    //   371: athrow
    //   372: astore 10
    //   374: aload 10
    //   376: athrow
    //   377: aload_1
    //   378: aload 25
    //   380: aload 26
    //   382: invokeinterface 137 3 0
    //   387: aload_0
    //   388: aload 5
    //   390: aload 6
    //   392: invokevirtual 213	oauth/signpost/AbstractOAuthProvider:closeConnection	(Loauth/signpost/http/HttpRequest;Loauth/signpost/http/HttpResponse;)V
    //   395: return
    //   396: astore 29
    //   398: new 81	oauth/signpost/exception/OAuthCommunicationException
    //   401: dup
    //   402: aload 29
    //   404: invokespecial 244	oauth/signpost/exception/OAuthCommunicationException:<init>	(Ljava/lang/Exception;)V
    //   407: athrow
    //   408: astore 7
    //   410: new 81	oauth/signpost/exception/OAuthCommunicationException
    //   413: dup
    //   414: aload 7
    //   416: invokespecial 244	oauth/signpost/exception/OAuthCommunicationException:<init>	(Ljava/lang/Exception;)V
    //   419: athrow
    //   420: astore 9
    //   422: new 81	oauth/signpost/exception/OAuthCommunicationException
    //   425: dup
    //   426: aload 9
    //   428: invokespecial 244	oauth/signpost/exception/OAuthCommunicationException:<init>	(Ljava/lang/Exception;)V
    //   431: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	432	0	this	AbstractOAuthProvider
    //   0	432	1	paramOAuthConsumer	OAuthConsumer
    //   0	432	2	paramString	String
    //   0	432	3	paramVarArgs	String[]
    //   4	91	4	localMap	Map
    //   35	354	5	localHttpRequest	HttpRequest
    //   38	353	6	localHttpResponse	HttpResponse
    //   408	7	7	localException1	Exception
    //   119	11	8	localObject	Object
    //   420	7	9	localException2	Exception
    //   372	3	10	localOAuthExpectationFailedException	OAuthExpectationFailedException
    //   114	3	11	localOAuthNotAuthorizedException	OAuthNotAuthorizedException
    //   59	20	12	localIterator	java.util.Iterator
    //   68	6	13	bool1	boolean
    //   88	9	14	str1	String
    //   164	6	15	localOAuthProviderListener1	OAuthProviderListener
    //   192	6	18	localOAuthProviderListener2	OAuthProviderListener
    //   222	71	20	i	int
    //   228	6	21	localOAuthProviderListener3	OAuthProviderListener
    //   231	27	22	j	int
    //   251	3	23	bool2	boolean
    //   309	38	24	localHttpParameters1	HttpParameters
    //   318	61	25	str2	String
    //   327	54	26	str3	String
    //   396	7	29	localException3	Exception
    //   271	7	30	localException4	Exception
    //   143	11	31	localHttpParameters2	HttpParameters
    // Exception table:
    //   from	to	target	type
    //   40	61	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   61	70	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   78	111	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   136	160	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   160	166	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   174	179	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   179	194	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   202	207	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   207	230	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   238	253	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   291	299	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   299	351	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   361	372	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   377	387	114	oauth/signpost/exception/OAuthNotAuthorizedException
    //   40	61	119	finally
    //   61	70	119	finally
    //   78	111	119	finally
    //   116	119	119	finally
    //   136	160	119	finally
    //   160	166	119	finally
    //   174	179	119	finally
    //   179	194	119	finally
    //   202	207	119	finally
    //   207	230	119	finally
    //   238	253	119	finally
    //   291	299	119	finally
    //   299	351	119	finally
    //   361	372	119	finally
    //   374	377	119	finally
    //   377	387	119	finally
    //   410	420	119	finally
    //   262	270	271	java/lang/Exception
    //   40	61	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   61	70	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   78	111	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   136	160	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   160	166	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   174	179	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   179	194	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   202	207	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   207	230	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   238	253	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   291	299	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   299	351	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   361	372	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   377	387	372	oauth/signpost/exception/OAuthExpectationFailedException
    //   387	395	396	java/lang/Exception
    //   40	61	408	java/lang/Exception
    //   61	70	408	java/lang/Exception
    //   78	111	408	java/lang/Exception
    //   136	160	408	java/lang/Exception
    //   160	166	408	java/lang/Exception
    //   174	179	408	java/lang/Exception
    //   179	194	408	java/lang/Exception
    //   202	207	408	java/lang/Exception
    //   207	230	408	java/lang/Exception
    //   238	253	408	java/lang/Exception
    //   291	299	408	java/lang/Exception
    //   299	351	408	java/lang/Exception
    //   361	372	408	java/lang/Exception
    //   377	387	408	java/lang/Exception
    //   121	129	420	java/lang/Exception
  }
  
  protected abstract HttpResponse sendRequest(HttpRequest paramHttpRequest)
    throws Exception;
  
  public void setResponseParameters(HttpParameters paramHttpParameters)
  {
    this.responseParameters = paramHttpParameters;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     oauth.signpost.AbstractOAuthProvider
 * JD-Core Version:    0.7.0.1
 */