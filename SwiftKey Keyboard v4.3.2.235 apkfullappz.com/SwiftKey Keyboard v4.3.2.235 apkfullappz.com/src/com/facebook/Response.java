package com.facebook;

import android.content.Context;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FileLruCache.Limits;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import com.facebook.model.GraphObjectList;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Response
{
  private static final String BODY_KEY = "body";
  private static final String CODE_KEY = "code";
  private static final int INVALID_SESSION_FACEBOOK_ERROR_CODE = 190;
  public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
  private static final String RESPONSE_CACHE_TAG = "ResponseCache";
  private static final String RESPONSE_LOG_TAG = "Response";
  private static FileLruCache responseCache;
  private final HttpURLConnection connection;
  private final FacebookRequestError error;
  private final GraphObject graphObject;
  private final GraphObjectList<GraphObject> graphObjectList;
  private final boolean isFromCache;
  private final Request request;
  
  static
  {
    if (!Response.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  Response(Request paramRequest, HttpURLConnection paramHttpURLConnection, FacebookRequestError paramFacebookRequestError)
  {
    this.request = paramRequest;
    this.connection = paramHttpURLConnection;
    this.graphObject = null;
    this.graphObjectList = null;
    this.isFromCache = false;
    this.error = paramFacebookRequestError;
  }
  
  Response(Request paramRequest, HttpURLConnection paramHttpURLConnection, GraphObject paramGraphObject, boolean paramBoolean)
  {
    this.request = paramRequest;
    this.connection = paramHttpURLConnection;
    this.graphObject = paramGraphObject;
    this.graphObjectList = null;
    this.isFromCache = paramBoolean;
    this.error = null;
  }
  
  Response(Request paramRequest, HttpURLConnection paramHttpURLConnection, GraphObjectList<GraphObject> paramGraphObjectList, boolean paramBoolean)
  {
    this.request = paramRequest;
    this.connection = paramHttpURLConnection;
    this.graphObject = null;
    this.graphObjectList = paramGraphObjectList;
    this.isFromCache = paramBoolean;
    this.error = null;
  }
  
  static List<Response> constructErrorResponses(List<Request> paramList, HttpURLConnection paramHttpURLConnection, FacebookException paramFacebookException)
  {
    int i = paramList.size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Response((Request)paramList.get(j), paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, paramFacebookException)));
    }
    return localArrayList;
  }
  
  private static Response createResponseFromObject(Request paramRequest, HttpURLConnection paramHttpURLConnection, Object paramObject1, boolean paramBoolean, Object paramObject2)
    throws JSONException
  {
    if ((paramObject1 instanceof JSONObject))
    {
      JSONObject localJSONObject = (JSONObject)paramObject1;
      FacebookRequestError localFacebookRequestError = FacebookRequestError.checkResponseAndCreateError(localJSONObject, paramObject2, paramHttpURLConnection);
      if (localFacebookRequestError != null)
      {
        if (localFacebookRequestError.getErrorCode() == 190)
        {
          Session localSession = paramRequest.getSession();
          if (localSession != null) {
            localSession.closeAndClearTokenInformation();
          }
        }
        return new Response(paramRequest, paramHttpURLConnection, localFacebookRequestError);
      }
      Object localObject = Utility.getStringPropertyAsJSON(localJSONObject, "body", "FACEBOOK_NON_JSON_RESULT");
      if ((localObject instanceof JSONObject)) {
        return new Response(paramRequest, paramHttpURLConnection, GraphObject.Factory.create((JSONObject)localObject), paramBoolean);
      }
      if ((localObject instanceof JSONArray)) {
        return new Response(paramRequest, paramHttpURLConnection, GraphObject.Factory.createList((JSONArray)localObject, GraphObject.class), paramBoolean);
      }
      paramObject1 = JSONObject.NULL;
    }
    if (paramObject1 == JSONObject.NULL) {
      return new Response(paramRequest, paramHttpURLConnection, null, paramBoolean);
    }
    throw new FacebookException("Got unexpected object type in response, class: " + paramObject1.getClass().getSimpleName());
  }
  
  private static List<Response> createResponsesFromObject(HttpURLConnection paramHttpURLConnection, List<Request> paramList, Object paramObject, boolean paramBoolean)
    throws FacebookException, JSONException
  {
    assert ((paramHttpURLConnection != null) || (paramBoolean));
    int i = paramList.size();
    ArrayList localArrayList = new ArrayList(i);
    Object localObject = paramObject;
    Request localRequest2;
    if (i == 1) {
      localRequest2 = (Request)paramList.get(0);
    }
    for (;;)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("body", paramObject);
        if (paramHttpURLConnection == null) {
          continue;
        }
        k = paramHttpURLConnection.getResponseCode();
        localJSONObject.put("code", k);
        JSONArray localJSONArray2 = new JSONArray();
        localJSONArray2.put(localJSONObject);
        paramObject = localJSONArray2;
      }
      catch (JSONException localJSONException2)
      {
        int k;
        localArrayList.add(new Response(localRequest2, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localJSONException2)));
        continue;
      }
      catch (IOException localIOException)
      {
        localArrayList.add(new Response(localRequest2, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localIOException)));
        continue;
        JSONArray localJSONArray1 = (JSONArray)paramObject;
        int j = 0;
        if (j >= localJSONArray1.length()) {
          break label351;
        }
        Request localRequest1 = (Request)paramList.get(j);
        try
        {
          localArrayList.add(createResponseFromObject(localRequest1, paramHttpURLConnection, localJSONArray1.get(j), paramBoolean, localObject));
          j++;
        }
        catch (JSONException localJSONException1)
        {
          localArrayList.add(new Response(localRequest1, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localJSONException1)));
          continue;
        }
        catch (FacebookException localFacebookException)
        {
          localArrayList.add(new Response(localRequest1, paramHttpURLConnection, new FacebookRequestError(paramHttpURLConnection, localFacebookException)));
          continue;
        }
      }
      if (((paramObject instanceof JSONArray)) && (((JSONArray)paramObject).length() == i)) {
        continue;
      }
      throw new FacebookException("Unexpected number of results");
      k = 200;
    }
    label351:
    return localArrayList;
  }
  
  static List<Response> createResponsesFromStream(InputStream paramInputStream, HttpURLConnection paramHttpURLConnection, RequestBatch paramRequestBatch, boolean paramBoolean)
    throws FacebookException, JSONException, IOException
  {
    String str = Utility.readStreamToString(paramInputStream);
    LoggingBehavior localLoggingBehavior = LoggingBehavior.INCLUDE_RAW_RESPONSES;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(str.length());
    arrayOfObject[1] = str;
    Logger.log(localLoggingBehavior, "Response", "Response (raw)\n  Size: %d\n  Response:\n%s\n", arrayOfObject);
    return createResponsesFromString(str, paramHttpURLConnection, paramRequestBatch, paramBoolean);
  }
  
  static List<Response> createResponsesFromString(String paramString, HttpURLConnection paramHttpURLConnection, RequestBatch paramRequestBatch, boolean paramBoolean)
    throws FacebookException, JSONException, IOException
  {
    List localList = createResponsesFromObject(paramHttpURLConnection, paramRequestBatch, new JSONTokener(paramString).nextValue(), paramBoolean);
    LoggingBehavior localLoggingBehavior = LoggingBehavior.REQUESTS;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramRequestBatch.getId();
    arrayOfObject[1] = Integer.valueOf(paramString.length());
    arrayOfObject[2] = localList;
    Logger.log(localLoggingBehavior, "Response", "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", arrayOfObject);
    return localList;
  }
  
  /* Error */
  static List<Response> fromHttpConnection(HttpURLConnection paramHttpURLConnection, RequestBatch paramRequestBatch)
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 261
    //   4: istore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore 5
    //   13: iload_2
    //   14: ifeq +133 -> 147
    //   17: aload_1
    //   18: checkcast 261	com/facebook/internal/CacheableRequestBatch
    //   21: astore 15
    //   23: invokestatic 265	com/facebook/Response:getResponseCache	()Lcom/facebook/internal/FileLruCache;
    //   26: astore_3
    //   27: aload 15
    //   29: invokevirtual 268	com/facebook/internal/CacheableRequestBatch:getCacheKeyOverride	()Ljava/lang/String;
    //   32: astore 4
    //   34: aload 4
    //   36: invokestatic 272	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   39: ifeq +21 -> 60
    //   42: aload_1
    //   43: invokevirtual 273	com/facebook/RequestBatch:size	()I
    //   46: iconst_1
    //   47: if_icmpne +81 -> 128
    //   50: aload_1
    //   51: iconst_0
    //   52: invokevirtual 276	com/facebook/RequestBatch:get	(I)Lcom/facebook/Request;
    //   55: invokevirtual 279	com/facebook/Request:getUrlForSingleRequest	()Ljava/lang/String;
    //   58: astore 4
    //   60: aload 15
    //   62: invokevirtual 282	com/facebook/internal/CacheableRequestBatch:getForceRoundTrip	()Z
    //   65: istore 16
    //   67: aconst_null
    //   68: astore 5
    //   70: iload 16
    //   72: ifne +75 -> 147
    //   75: aconst_null
    //   76: astore 5
    //   78: aload_3
    //   79: ifnull +68 -> 147
    //   82: aload 4
    //   84: invokestatic 272	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   87: istore 17
    //   89: aconst_null
    //   90: astore 5
    //   92: iload 17
    //   94: ifne +53 -> 147
    //   97: aload_3
    //   98: aload 4
    //   100: invokevirtual 287	com/facebook/internal/FileLruCache:get	(Ljava/lang/String;)Ljava/io/InputStream;
    //   103: astore 5
    //   105: aload 5
    //   107: ifnull +35 -> 142
    //   110: aload 5
    //   112: aconst_null
    //   113: aload_1
    //   114: iconst_1
    //   115: invokestatic 289	com/facebook/Response:createResponsesFromStream	(Ljava/io/InputStream;Ljava/net/HttpURLConnection;Lcom/facebook/RequestBatch;Z)Ljava/util/List;
    //   118: astore 22
    //   120: aload 5
    //   122: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   125: aload 22
    //   127: areturn
    //   128: getstatic 250	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   131: ldc 23
    //   133: ldc_w 295
    //   136: invokestatic 298	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   139: goto -79 -> 60
    //   142: aload 5
    //   144: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   147: aload_0
    //   148: invokevirtual 190	java/net/HttpURLConnection:getResponseCode	()I
    //   151: sipush 400
    //   154: if_icmplt +67 -> 221
    //   157: aload_0
    //   158: invokevirtual 302	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   161: astore 5
    //   163: aload 5
    //   165: aload_0
    //   166: aload_1
    //   167: iconst_0
    //   168: invokestatic 289	com/facebook/Response:createResponsesFromStream	(Ljava/io/InputStream;Ljava/net/HttpURLConnection;Lcom/facebook/RequestBatch;Z)Ljava/util/List;
    //   171: astore 14
    //   173: aload 5
    //   175: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   178: aload 14
    //   180: areturn
    //   181: astore 21
    //   183: aload 5
    //   185: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   188: goto -41 -> 147
    //   191: astore 20
    //   193: aload 5
    //   195: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   198: goto -51 -> 147
    //   201: astore 19
    //   203: aload 5
    //   205: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   208: goto -61 -> 147
    //   211: astore 18
    //   213: aload 5
    //   215: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   218: aload 18
    //   220: athrow
    //   221: aload_0
    //   222: invokevirtual 305	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   225: astore 5
    //   227: aload_3
    //   228: ifnull -65 -> 163
    //   231: aload 4
    //   233: ifnull -70 -> 163
    //   236: aload 5
    //   238: ifnull -75 -> 163
    //   241: aload_3
    //   242: aload 4
    //   244: aload 5
    //   246: invokevirtual 309	com/facebook/internal/FileLruCache:interceptAndPut	(Ljava/lang/String;Ljava/io/InputStream;)Ljava/io/InputStream;
    //   249: astore 13
    //   251: aload 13
    //   253: ifnull -90 -> 163
    //   256: aload 13
    //   258: astore 5
    //   260: goto -97 -> 163
    //   263: astore 11
    //   265: getstatic 250	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   268: ldc 26
    //   270: ldc_w 311
    //   273: iconst_1
    //   274: anewarray 4	java/lang/Object
    //   277: dup
    //   278: iconst_0
    //   279: aload 11
    //   281: aastore
    //   282: invokestatic 234	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   285: aload_1
    //   286: aload_0
    //   287: aload 11
    //   289: invokestatic 313	com/facebook/Response:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   292: astore 12
    //   294: aload 5
    //   296: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   299: aload 12
    //   301: areturn
    //   302: astore 9
    //   304: getstatic 250	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   307: ldc 26
    //   309: ldc_w 311
    //   312: iconst_1
    //   313: anewarray 4	java/lang/Object
    //   316: dup
    //   317: iconst_0
    //   318: aload 9
    //   320: aastore
    //   321: invokestatic 234	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   324: aload_1
    //   325: aload_0
    //   326: new 150	com/facebook/FacebookException
    //   329: dup
    //   330: aload 9
    //   332: invokespecial 316	com/facebook/FacebookException:<init>	(Ljava/lang/Throwable;)V
    //   335: invokestatic 313	com/facebook/Response:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   338: astore 10
    //   340: aload 5
    //   342: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   345: aload 10
    //   347: areturn
    //   348: astore 7
    //   350: getstatic 250	com/facebook/LoggingBehavior:REQUESTS	Lcom/facebook/LoggingBehavior;
    //   353: ldc 26
    //   355: ldc_w 311
    //   358: iconst_1
    //   359: anewarray 4	java/lang/Object
    //   362: dup
    //   363: iconst_0
    //   364: aload 7
    //   366: aastore
    //   367: invokestatic 234	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   370: aload_1
    //   371: aload_0
    //   372: new 150	com/facebook/FacebookException
    //   375: dup
    //   376: aload 7
    //   378: invokespecial 316	com/facebook/FacebookException:<init>	(Ljava/lang/Throwable;)V
    //   381: invokestatic 313	com/facebook/Response:constructErrorResponses	(Ljava/util/List;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookException;)Ljava/util/List;
    //   384: astore 8
    //   386: aload 5
    //   388: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   391: aload 8
    //   393: areturn
    //   394: astore 6
    //   396: aload 5
    //   398: invokestatic 293	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   401: aload 6
    //   403: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	404	0	paramHttpURLConnection	HttpURLConnection
    //   0	404	1	paramRequestBatch	RequestBatch
    //   4	10	2	bool1	boolean
    //   6	236	3	localFileLruCache	FileLruCache
    //   8	235	4	str	String
    //   11	386	5	localObject1	Object
    //   394	8	6	localObject2	Object
    //   348	29	7	localIOException1	IOException
    //   384	8	8	localList1	List
    //   302	29	9	localJSONException1	JSONException
    //   338	8	10	localList2	List
    //   263	25	11	localFacebookException1	FacebookException
    //   292	8	12	localList3	List
    //   249	8	13	localInputStream	InputStream
    //   171	8	14	localList4	List
    //   21	40	15	localCacheableRequestBatch	com.facebook.internal.CacheableRequestBatch
    //   65	6	16	bool2	boolean
    //   87	6	17	bool3	boolean
    //   211	8	18	localObject3	Object
    //   201	1	19	localIOException2	IOException
    //   191	1	20	localJSONException2	JSONException
    //   181	1	21	localFacebookException2	FacebookException
    //   118	8	22	localList5	List
    // Exception table:
    //   from	to	target	type
    //   97	105	181	com/facebook/FacebookException
    //   110	120	181	com/facebook/FacebookException
    //   97	105	191	org/json/JSONException
    //   110	120	191	org/json/JSONException
    //   97	105	201	java/io/IOException
    //   110	120	201	java/io/IOException
    //   97	105	211	finally
    //   110	120	211	finally
    //   147	163	263	com/facebook/FacebookException
    //   163	173	263	com/facebook/FacebookException
    //   221	227	263	com/facebook/FacebookException
    //   241	251	263	com/facebook/FacebookException
    //   147	163	302	org/json/JSONException
    //   163	173	302	org/json/JSONException
    //   221	227	302	org/json/JSONException
    //   241	251	302	org/json/JSONException
    //   147	163	348	java/io/IOException
    //   163	173	348	java/io/IOException
    //   221	227	348	java/io/IOException
    //   241	251	348	java/io/IOException
    //   147	163	394	finally
    //   163	173	394	finally
    //   221	227	394	finally
    //   241	251	394	finally
    //   265	294	394	finally
    //   304	340	394	finally
    //   350	386	394	finally
  }
  
  static FileLruCache getResponseCache()
  {
    if (responseCache == null)
    {
      Context localContext = Session.getStaticContext();
      if (localContext != null) {
        responseCache = new FileLruCache(localContext, "ResponseCache", new FileLruCache.Limits());
      }
    }
    return responseCache;
  }
  
  public final HttpURLConnection getConnection()
  {
    return this.connection;
  }
  
  public final FacebookRequestError getError()
  {
    return this.error;
  }
  
  public final GraphObject getGraphObject()
  {
    return this.graphObject;
  }
  
  public final <T extends GraphObject> T getGraphObjectAs(Class<T> paramClass)
  {
    if (this.graphObject == null) {
      return null;
    }
    if (paramClass == null) {
      throw new NullPointerException("Must pass in a valid interface that extends GraphObject");
    }
    return this.graphObject.cast(paramClass);
  }
  
  public final GraphObjectList<GraphObject> getGraphObjectList()
  {
    return this.graphObjectList;
  }
  
  public final <T extends GraphObject> GraphObjectList<T> getGraphObjectListAs(Class<T> paramClass)
  {
    if (this.graphObjectList == null) {
      return null;
    }
    return this.graphObjectList.castToListOf(paramClass);
  }
  
  public final boolean getIsFromCache()
  {
    return this.isFromCache;
  }
  
  public Request getRequest()
  {
    return this.request;
  }
  
  public Request getRequestForPagedResults(PagingDirection paramPagingDirection)
  {
    GraphObject localGraphObject = this.graphObject;
    String str = null;
    PagingInfo localPagingInfo;
    if (localGraphObject != null)
    {
      localPagingInfo = ((PagedResults)this.graphObject.cast(PagedResults.class)).getPaging();
      str = null;
      if (localPagingInfo != null) {
        if (paramPagingDirection != PagingDirection.NEXT) {
          break label64;
        }
      }
    }
    label64:
    for (str = localPagingInfo.getNext(); Utility.isNullOrEmpty(str); str = localPagingInfo.getPrevious()) {
      return null;
    }
    if ((str != null) && (str.equals(this.request.getUrlForSingleRequest()))) {
      return null;
    }
    try
    {
      Request localRequest = new Request(this.request.getSession(), new URL(str));
      return localRequest;
    }
    catch (MalformedURLException localMalformedURLException) {}
    return null;
  }
  
  public String toString()
  {
    for (;;)
    {
      try
      {
        Object[] arrayOfObject = new Object[1];
        if (this.connection == null) {
          continue;
        }
        i = this.connection.getResponseCode();
        arrayOfObject[0] = Integer.valueOf(i);
        String str2 = String.format("%d", arrayOfObject);
        str1 = str2;
      }
      catch (IOException localIOException)
      {
        int i;
        String str1 = "unknown";
        continue;
      }
      return "{Response:  responseCode: " + str1 + ", graphObject: " + this.graphObject + ", error: " + this.error + ", isFromCache:" + this.isFromCache + "}";
      i = 200;
    }
  }
  
  static abstract interface PagedResults
    extends GraphObject
  {
    public abstract GraphObjectList<GraphObject> getData();
    
    public abstract Response.PagingInfo getPaging();
  }
  
  public static enum PagingDirection
  {
    static
    {
      PagingDirection[] arrayOfPagingDirection = new PagingDirection[2];
      arrayOfPagingDirection[0] = NEXT;
      arrayOfPagingDirection[1] = PREVIOUS;
      $VALUES = arrayOfPagingDirection;
    }
    
    private PagingDirection() {}
  }
  
  static abstract interface PagingInfo
    extends GraphObject
  {
    public abstract String getNext();
    
    public abstract String getPrevious();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.Response
 * JD-Core Version:    0.7.0.1
 */