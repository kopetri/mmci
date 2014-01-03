package com.touchtype_fluency.service.personalize.tasks;

import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

class InitialRequestTask
  extends PersonalizationTask
{
  private static final int CODE_ERROR = -1;
  private static final String REQUEST_ID = "request_id";
  private static final int RETRIES = 5;
  private final String mAuthParams;
  private final PollRequestTask mNextTask;
  
  public InitialRequestTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, String paramString1, PersonalizationTaskListener paramPersonalizationTaskListener, String paramString2, PollRequestTask paramPollRequestTask)
  {
    super(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, 5, paramString2);
    this.mAuthParams = paramString1;
    this.mNextTask = paramPollRequestTask;
  }
  
  protected static String buildPollURL(String paramString1, String paramString2)
  {
    return String.format("%s?%s=%s", new Object[] { paramString1, "request_id", paramString2 });
  }
  
  /* Error */
  public void compute()
    throws TaskFailException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 49	com/touchtype_fluency/service/personalize/tasks/InitialRequestTask:getLocation	()Ljava/lang/String;
    //   4: astore_1
    //   5: aconst_null
    //   6: astore_2
    //   7: new 51	org/apache/http/params/BasicHttpParams
    //   10: dup
    //   11: invokespecial 53	org/apache/http/params/BasicHttpParams:<init>	()V
    //   14: astore_3
    //   15: aload_3
    //   16: ldc 55
    //   18: iconst_0
    //   19: invokestatic 61	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   22: invokeinterface 67 3 0
    //   27: pop
    //   28: new 69	org/apache/http/client/methods/HttpPost
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 72	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   36: astore 9
    //   38: aload 9
    //   40: new 74	org/apache/http/entity/InputStreamEntity
    //   43: dup
    //   44: new 76	java/io/ByteArrayInputStream
    //   47: dup
    //   48: aload_0
    //   49: getfield 23	com/touchtype_fluency/service/personalize/tasks/InitialRequestTask:mAuthParams	Ljava/lang/String;
    //   52: ldc 78
    //   54: invokevirtual 82	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   57: invokespecial 85	java/io/ByteArrayInputStream:<init>	([B)V
    //   60: aload_0
    //   61: getfield 23	com/touchtype_fluency/service/personalize/tasks/InitialRequestTask:mAuthParams	Ljava/lang/String;
    //   64: invokevirtual 89	java/lang/String:length	()I
    //   67: i2l
    //   68: invokespecial 92	org/apache/http/entity/InputStreamEntity:<init>	(Ljava/io/InputStream;J)V
    //   71: invokevirtual 96	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   74: aload_3
    //   75: invokestatic 102	com/touchtype_fluency/service/http/SSLClientFactory:createHttpClient	(Lorg/apache/http/params/HttpParams;)Lorg/apache/http/client/HttpClient;
    //   78: astore_2
    //   79: aload_2
    //   80: aload 9
    //   82: invokeinterface 108 2 0
    //   87: astore 10
    //   89: aload 10
    //   91: astore 6
    //   93: aload_2
    //   94: ifnull +151 -> 245
    //   97: aload_2
    //   98: invokeinterface 112 1 0
    //   103: invokeinterface 117 1 0
    //   108: aload_0
    //   109: aload 6
    //   111: invokevirtual 121	com/touchtype_fluency/service/personalize/tasks/InitialRequestTask:evaluateInitialResponse	(Lorg/apache/http/HttpResponse;)V
    //   114: return
    //   115: astore 7
    //   117: aload_0
    //   118: getfield 124	com/touchtype_fluency/service/personalize/tasks/InitialRequestTask:TAG	Ljava/lang/String;
    //   121: new 126	java/lang/StringBuilder
    //   124: dup
    //   125: ldc 128
    //   127: invokespecial 129	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   130: aload 7
    //   132: invokevirtual 132	javax/net/ssl/SSLException:getMessage	()Ljava/lang/String;
    //   135: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   141: aload 7
    //   143: invokestatic 145	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   146: aconst_null
    //   147: astore 6
    //   149: aload_2
    //   150: ifnull -42 -> 108
    //   153: aload_2
    //   154: invokeinterface 112 1 0
    //   159: invokeinterface 117 1 0
    //   164: aconst_null
    //   165: astore 6
    //   167: goto -59 -> 108
    //   170: astore 5
    //   172: aload_0
    //   173: getfield 124	com/touchtype_fluency/service/personalize/tasks/InitialRequestTask:TAG	Ljava/lang/String;
    //   176: aload 5
    //   178: invokevirtual 146	java/io/IOException:getMessage	()Ljava/lang/String;
    //   181: aload 5
    //   183: invokestatic 145	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   186: aconst_null
    //   187: astore 6
    //   189: aload_2
    //   190: ifnull -82 -> 108
    //   193: aload_2
    //   194: invokeinterface 112 1 0
    //   199: invokeinterface 117 1 0
    //   204: aconst_null
    //   205: astore 6
    //   207: goto -99 -> 108
    //   210: astore 4
    //   212: aload_2
    //   213: ifnull +14 -> 227
    //   216: aload_2
    //   217: invokeinterface 112 1 0
    //   222: invokeinterface 117 1 0
    //   227: aload 4
    //   229: athrow
    //   230: astore 4
    //   232: goto -20 -> 212
    //   235: astore 5
    //   237: goto -65 -> 172
    //   240: astore 7
    //   242: goto -125 -> 117
    //   245: goto -137 -> 108
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	248	0	this	InitialRequestTask
    //   4	29	1	str	String
    //   6	211	2	localHttpClient	org.apache.http.client.HttpClient
    //   14	61	3	localBasicHttpParams	org.apache.http.params.BasicHttpParams
    //   210	18	4	localObject1	Object
    //   230	1	4	localObject2	Object
    //   170	12	5	localIOException1	IOException
    //   235	1	5	localIOException2	IOException
    //   91	115	6	localHttpResponse1	HttpResponse
    //   115	27	7	localSSLException1	javax.net.ssl.SSLException
    //   240	1	7	localSSLException2	javax.net.ssl.SSLException
    //   36	45	9	localHttpPost	org.apache.http.client.methods.HttpPost
    //   87	3	10	localHttpResponse2	HttpResponse
    // Exception table:
    //   from	to	target	type
    //   7	38	115	javax/net/ssl/SSLException
    //   7	38	170	java/io/IOException
    //   7	38	210	finally
    //   117	146	210	finally
    //   172	186	210	finally
    //   38	89	230	finally
    //   38	89	235	java/io/IOException
    //   38	89	240	javax/net/ssl/SSLException
  }
  
  protected void evaluateInitialResponse(HttpResponse paramHttpResponse)
    throws TaskFailException
  {
    if (paramHttpResponse == null) {}
    for (int i = -1;; i = paramHttpResponse.getStatusLine().getStatusCode()) {
      switch (i)
      {
      default: 
        throw new TaskFailException("Did not receive a valid response from Initial request", PersonalizationFailedReason.OTHER);
      }
    }
    try
    {
      String str1 = new JSONObject(EntityUtils.toString(paramHttpResponse.getEntity())).getString("request_id");
      if (str1 != null)
      {
        String str2 = buildPollURL(getLocation(), str1);
        this.mNextTask.setLocation(str2);
        schedule(this.mNextTask, 0);
        return;
      }
      throw new TaskFailException("Error parsing JSON response from Initial request", PersonalizationFailedReason.OTHER);
    }
    catch (ParseException localParseException)
    {
      localParseException = localParseException;
      LogUtil.e(this.TAG, localParseException.getMessage(), localParseException);
      throw new TaskFailException("Error parsing JSON response from Initial request", PersonalizationFailedReason.OTHER);
    }
    catch (JSONException localJSONException)
    {
      localJSONException = localJSONException;
      LogUtil.e(this.TAG, localJSONException.getMessage(), localJSONException);
      throw new TaskFailException("Error parsing JSON response from Initial request", PersonalizationFailedReason.OTHER);
    }
    catch (IOException localIOException)
    {
      localIOException = localIOException;
      LogUtil.e(this.TAG, localIOException.getMessage(), localIOException);
      throw new TaskFailException("Error parsing JSON response from Initial request", PersonalizationFailedReason.OTHER);
    }
    finally {}
    LogUtil.w(this.TAG, "Servers are returning busy");
    notifyListener(false, PersonalizationFailedReason.SERVER_BUSY);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.InitialRequestTask
 * JD-Core Version:    0.7.0.1
 */