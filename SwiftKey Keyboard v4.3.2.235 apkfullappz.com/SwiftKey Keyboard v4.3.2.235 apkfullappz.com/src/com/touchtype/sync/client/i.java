package com.touchtype.sync.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.ssl.X509HostnameVerifier;

final class i
{
  private static final Random d = new Random();
  private final String a;
  private final LoggingListener b;
  private final SSLTools c;
  
  i(String paramString, SSLTools paramSSLTools, LoggingListener paramLoggingListener)
  {
    this.c = paramSSLTools;
    this.b = paramLoggingListener;
    this.a = paramString;
  }
  
  private Runnable a(String paramString, final a parama, final a parama1, b paramb1, b paramb2, final j paramj)
  {
    final String str = a(paramString, paramb1);
    if (paramb2 != null) {}
    for (final byte[] arrayOfByte = paramb2.toString().getBytes();; arrayOfByte = null) {
      new Runnable()
      {
        /* Error */
        public final void run()
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_1
          //   2: aload_0
          //   3: getfield 28	com/touchtype/sync/client/i$1:a	Ljava/lang/String;
          //   6: invokestatic 51	com/touchtype/sync/client/i:a	(Ljava/lang/String;)Ljava/net/URL;
          //   9: astore 15
          //   11: ldc2_w 52
          //   14: invokestatic 56	com/touchtype/sync/client/i:a	()Ljava/util/Random;
          //   17: sipush 1000
          //   20: invokevirtual 62	java/util/Random:nextInt	(I)I
          //   23: i2l
          //   24: ladd
          //   25: lstore 16
          //   27: aload_0
          //   28: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   31: invokestatic 65	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
          //   34: ldc 67
          //   36: aload 15
          //   38: invokevirtual 73	java/net/URL:toString	()Ljava/lang/String;
          //   41: invokestatic 78	com/touchtype/sync/client/e:a	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
          //   44: lload 16
          //   46: lstore 18
          //   48: iconst_1
          //   49: istore 20
          //   51: aconst_null
          //   52: astore 13
          //   54: iload 20
          //   56: iconst_5
          //   57: if_icmpgt +169 -> 226
          //   60: aload_0
          //   61: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   64: aload 15
          //   66: invokestatic 81	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/net/URL;)Ljava/net/HttpURLConnection;
          //   69: astore 13
          //   71: aload 13
          //   73: iconst_0
          //   74: invokevirtual 87	java/net/HttpURLConnection:setUseCaches	(Z)V
          //   77: aload 13
          //   79: aload_0
          //   80: getfield 30	com/touchtype/sync/client/i$1:b	Lcom/touchtype/sync/client/i$a;
          //   83: invokevirtual 92	com/touchtype/sync/client/i$a:name	()Ljava/lang/String;
          //   86: invokevirtual 96	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
          //   89: aload_0
          //   90: getfield 32	com/touchtype/sync/client/i$1:c	Lcom/touchtype/sync/client/a;
          //   93: ifnull +17 -> 110
          //   96: aload 13
          //   98: ldc 98
          //   100: aload_0
          //   101: getfield 32	com/touchtype/sync/client/i$1:c	Lcom/touchtype/sync/client/a;
          //   104: invokevirtual 101	com/touchtype/sync/client/a:toString	()Ljava/lang/String;
          //   107: invokevirtual 105	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
          //   110: aload_0
          //   111: getfield 34	com/touchtype/sync/client/i$1:d	[B
          //   114: ifnull +42 -> 156
          //   117: aload 13
          //   119: aload_0
          //   120: getfield 34	com/touchtype/sync/client/i$1:d	[B
          //   123: arraylength
          //   124: invokevirtual 109	java/net/HttpURLConnection:setFixedLengthStreamingMode	(I)V
          //   127: aload 13
          //   129: ldc 111
          //   131: ldc 113
          //   133: invokevirtual 105	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
          //   136: aload 13
          //   138: iconst_1
          //   139: invokevirtual 116	java/net/HttpURLConnection:setDoOutput	(Z)V
          //   142: aload 13
          //   144: invokevirtual 120	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
          //   147: astore_1
          //   148: aload_1
          //   149: aload_0
          //   150: getfield 34	com/touchtype/sync/client/i$1:d	[B
          //   153: invokevirtual 126	java/io/OutputStream:write	([B)V
          //   156: iload 20
          //   158: iconst_5
          //   159: if_icmpge +31 -> 190
          //   162: aload 13
          //   164: invokevirtual 130	java/net/HttpURLConnection:getResponseCode	()I
          //   167: sipush 500
          //   170: if_icmpne +20 -> 190
          //   173: lload 18
          //   175: invokestatic 136	java/lang/Thread:sleep	(J)V
          //   178: lload 18
          //   180: iconst_1
          //   181: lshl
          //   182: lstore 18
          //   184: iinc 20 1
          //   187: goto -133 -> 54
          //   190: aload_0
          //   191: getfield 36	com/touchtype/sync/client/i$1:e	Lcom/touchtype/sync/client/j;
          //   194: aload 13
          //   196: invokevirtual 141	com/touchtype/sync/client/j:a	(Ljava/net/HttpURLConnection;)V
          //   199: aload_0
          //   200: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   203: aload_1
          //   204: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   207: aload_0
          //   208: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   211: aload_1
          //   212: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   215: aload 13
          //   217: ifnull +8 -> 225
          //   220: aload 13
          //   222: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   225: return
          //   226: aload_0
          //   227: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   230: aload_1
          //   231: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   234: aload 13
          //   236: ifnull +8 -> 244
          //   239: aload 13
          //   241: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   244: aload_0
          //   245: getfield 36	com/touchtype/sync/client/i$1:e	Lcom/touchtype/sync/client/j;
          //   248: invokevirtual 149	com/touchtype/sync/client/j:a	()V
          //   251: return
          //   252: astore 12
          //   254: aconst_null
          //   255: astore 13
          //   257: aload_0
          //   258: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   261: invokestatic 65	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
          //   264: ldc 67
          //   266: ldc 151
          //   268: invokestatic 78	com/touchtype/sync/client/e:a	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
          //   271: invokestatic 155	java/lang/Thread:currentThread	()Ljava/lang/Thread;
          //   274: invokevirtual 158	java/lang/Thread:interrupt	()V
          //   277: aload_0
          //   278: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   281: aload_1
          //   282: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   285: aload 13
          //   287: ifnull -43 -> 244
          //   290: aload 13
          //   292: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   295: goto -51 -> 244
          //   298: astore 10
          //   300: aconst_null
          //   301: astore_3
          //   302: aload 10
          //   304: astore 11
          //   306: aconst_null
          //   307: astore 5
          //   309: aload_0
          //   310: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   313: invokestatic 65	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
          //   316: ldc 67
          //   318: new 160	java/lang/StringBuilder
          //   321: dup
          //   322: ldc 162
          //   324: invokespecial 164	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   327: aload 11
          //   329: invokevirtual 167	java/io/IOException:getMessage	()Ljava/lang/String;
          //   332: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   335: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   338: invokestatic 174	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
          //   341: aload_0
          //   342: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   345: aload 5
          //   347: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   350: aload_3
          //   351: ifnull -107 -> 244
          //   354: aload_3
          //   355: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   358: goto -114 -> 244
          //   361: astore 8
          //   363: aconst_null
          //   364: astore_3
          //   365: aload 8
          //   367: astore 9
          //   369: aconst_null
          //   370: astore 5
          //   372: aload_0
          //   373: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   376: invokestatic 65	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
          //   379: ldc 67
          //   381: new 160	java/lang/StringBuilder
          //   384: dup
          //   385: ldc 176
          //   387: invokespecial 164	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   390: aload 9
          //   392: invokevirtual 177	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
          //   395: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   398: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   401: invokestatic 174	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
          //   404: aload_0
          //   405: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   408: aload 5
          //   410: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   413: aload_3
          //   414: ifnull -170 -> 244
          //   417: aload_3
          //   418: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   421: goto -177 -> 244
          //   424: astore 6
          //   426: aconst_null
          //   427: astore_3
          //   428: aload 6
          //   430: astore 7
          //   432: aconst_null
          //   433: astore 5
          //   435: aload_0
          //   436: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   439: invokestatic 65	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
          //   442: ldc 67
          //   444: new 160	java/lang/StringBuilder
          //   447: dup
          //   448: ldc 179
          //   450: invokespecial 164	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   453: aload 7
          //   455: invokevirtual 180	java/lang/Throwable:getMessage	()Ljava/lang/String;
          //   458: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   461: invokevirtual 172	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   464: invokestatic 174	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
          //   467: aload_0
          //   468: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   471: aload 5
          //   473: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   476: aload_3
          //   477: ifnull -233 -> 244
          //   480: aload_3
          //   481: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   484: goto -240 -> 244
          //   487: astore_2
          //   488: aconst_null
          //   489: astore_3
          //   490: aload_2
          //   491: astore 4
          //   493: aconst_null
          //   494: astore 5
          //   496: aload_0
          //   497: getfield 26	com/touchtype/sync/client/i$1:f	Lcom/touchtype/sync/client/i;
          //   500: aload 5
          //   502: invokestatic 144	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;Ljava/io/OutputStream;)V
          //   505: aload_3
          //   506: ifnull +7 -> 513
          //   509: aload_3
          //   510: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
          //   513: aload 4
          //   515: athrow
          //   516: astore 14
          //   518: aload 13
          //   520: astore_3
          //   521: aload_1
          //   522: astore 5
          //   524: aload 14
          //   526: astore 4
          //   528: goto -32 -> 496
          //   531: astore 4
          //   533: goto -37 -> 496
          //   536: astore 24
          //   538: aload 13
          //   540: astore_3
          //   541: aload_1
          //   542: astore 5
          //   544: aload 24
          //   546: astore 7
          //   548: goto -113 -> 435
          //   551: astore 23
          //   553: aload 13
          //   555: astore_3
          //   556: aload_1
          //   557: astore 5
          //   559: aload 23
          //   561: astore 9
          //   563: goto -191 -> 372
          //   566: astore 22
          //   568: aload 13
          //   570: astore_3
          //   571: aload_1
          //   572: astore 5
          //   574: aload 22
          //   576: astore 11
          //   578: goto -269 -> 309
          //   581: astore 21
          //   583: goto -326 -> 257
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	586	0	this	1
          //   1	571	1	localOutputStream1	java.io.OutputStream
          //   487	4	2	localObject1	Object
          //   301	270	3	localHttpURLConnection1	java.net.HttpURLConnection
          //   491	36	4	localObject2	Object
          //   531	1	4	localObject3	Object
          //   307	266	5	localOutputStream2	java.io.OutputStream
          //   424	5	6	localThrowable1	java.lang.Throwable
          //   430	117	7	localObject4	Object
          //   361	5	8	localIllegalArgumentException1	IllegalArgumentException
          //   367	195	9	localObject5	Object
          //   298	5	10	localIOException1	IOException
          //   304	273	11	localObject6	Object
          //   252	1	12	localInterruptedException1	java.lang.InterruptedException
          //   52	517	13	localHttpURLConnection2	java.net.HttpURLConnection
          //   516	9	14	localObject7	Object
          //   9	56	15	localURL	URL
          //   25	20	16	l1	long
          //   46	137	18	l2	long
          //   49	136	20	i	int
          //   581	1	21	localInterruptedException2	java.lang.InterruptedException
          //   566	9	22	localIOException2	IOException
          //   551	9	23	localIllegalArgumentException2	IllegalArgumentException
          //   536	9	24	localThrowable2	java.lang.Throwable
          // Exception table:
          //   from	to	target	type
          //   2	44	252	java/lang/InterruptedException
          //   2	44	298	java/io/IOException
          //   2	44	361	java/lang/IllegalArgumentException
          //   2	44	424	java/lang/Throwable
          //   2	44	487	finally
          //   60	110	516	finally
          //   110	156	516	finally
          //   162	178	516	finally
          //   190	207	516	finally
          //   257	277	516	finally
          //   309	341	531	finally
          //   372	404	531	finally
          //   435	467	531	finally
          //   60	110	536	java/lang/Throwable
          //   110	156	536	java/lang/Throwable
          //   162	178	536	java/lang/Throwable
          //   190	207	536	java/lang/Throwable
          //   60	110	551	java/lang/IllegalArgumentException
          //   110	156	551	java/lang/IllegalArgumentException
          //   162	178	551	java/lang/IllegalArgumentException
          //   190	207	551	java/lang/IllegalArgumentException
          //   60	110	566	java/io/IOException
          //   110	156	566	java/io/IOException
          //   162	178	566	java/io/IOException
          //   190	207	566	java/io/IOException
          //   60	110	581	java/lang/InterruptedException
          //   110	156	581	java/lang/InterruptedException
          //   162	178	581	java/lang/InterruptedException
          //   190	207	581	java/lang/InterruptedException
        }
      };
    }
  }
  
  private String a(String paramString, b paramb)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.a).append("/v2").append(paramString);
    if (paramb != null) {
      localStringBuilder.append("?").append(paramb.toString());
    }
    return localStringBuilder.toString();
  }
  
  private static URL b(String paramString)
  {
    try
    {
      URL localURL = new URL(paramString);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new IllegalArgumentException("invalid url: " + paramString);
    }
  }
  
  final Runnable a(String paramString, j paramj)
  {
    return a("/auth/email/" + paramString, a.a, null, null, null, paramj);
  }
  
  final Runnable a(String paramString1, String paramString2, CommonUtilities.Platform paramPlatform, Map<String, String> paramMap, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    b localb = new b();
    localb.a("platform", paramPlatform.toString());
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localb.a((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    return a("/subscription", a.b, locala, null, localb, paramj);
  }
  
  final Runnable a(String paramString1, String paramString2, j paramj)
  {
    b localb = new b();
    localb.a("code", paramString2);
    return a("/auth/email/" + paramString1, a.c, null, null, localb, paramj);
  }
  
  final Runnable a(String paramString1, String paramString2, String paramString3, CommonUtilities.AuthTokenType paramAuthTokenType, String paramString4, String paramString5, String paramString6, boolean paramBoolean1, String paramString7, boolean paramBoolean2, j paramj)
  {
    b localb = new b();
    localb.a(paramAuthTokenType.toString(), paramString3);
    if (paramBoolean1) {
      localb.a("action", "login");
    }
    if (paramString1 != null) {
      localb.a("email", paramString1);
    }
    localb.a("device_id", paramString5);
    localb.a("description", paramString2);
    localb.a("package_name", paramString7);
    localb.a("device_code", paramString4);
    localb.a("language", paramString6);
    localb.a("opt_in", String.valueOf(paramBoolean2));
    return a("/auth/google", a.b, null, null, localb, paramj);
  }
  
  public final Runnable a(String paramString1, String paramString2, String paramString3, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    return a("/devices/" + paramString3, a.d, locala, null, null, paramj);
  }
  
  final Runnable a(final String paramString1, final String paramString2, String paramString3, String paramString4, final j paramj)
  {
    final a locala = new a(paramString3, paramString4);
    new Runnable()
    {
      /* Error */
      public final void run()
      {
        // Byte code:
        //   0: new 46	java/io/File
        //   3: dup
        //   4: aload_0
        //   5: getfield 24	com/touchtype/sync/client/i$2:a	Ljava/lang/String;
        //   8: invokespecial 49	java/io/File:<init>	(Ljava/lang/String;)V
        //   11: astore_1
        //   12: aload_1
        //   13: invokevirtual 53	java/io/File:exists	()Z
        //   16: ifne +25 -> 41
        //   19: aload_0
        //   20: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   23: invokestatic 56	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
        //   26: ldc 58
        //   28: ldc 60
        //   30: invokestatic 65	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
        //   33: aload_0
        //   34: getfield 26	com/touchtype/sync/client/i$2:b	Lcom/touchtype/sync/client/j;
        //   37: invokevirtual 69	com/touchtype/sync/client/j:a	()V
        //   40: return
        //   41: aconst_null
        //   42: astore_2
        //   43: aload_0
        //   44: getfield 28	com/touchtype/sync/client/i$2:c	Ljava/lang/String;
        //   47: invokestatic 72	com/touchtype/sync/client/i:a	(Ljava/lang/String;)Ljava/net/URL;
        //   50: astore 14
        //   52: ldc2_w 73
        //   55: invokestatic 77	com/touchtype/sync/client/i:a	()Ljava/util/Random;
        //   58: sipush 1000
        //   61: invokevirtual 83	java/util/Random:nextInt	(I)I
        //   64: i2l
        //   65: ladd
        //   66: lstore 15
        //   68: aload_0
        //   69: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   72: invokestatic 56	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
        //   75: ldc 58
        //   77: aload 14
        //   79: invokevirtual 89	java/net/URL:toString	()Ljava/lang/String;
        //   82: invokestatic 91	com/touchtype/sync/client/e:a	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
        //   85: iconst_1
        //   86: istore 17
        //   88: lload 15
        //   90: lstore 18
        //   92: aload_0
        //   93: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   96: invokestatic 94	com/touchtype/sync/client/i:b	(Lcom/touchtype/sync/client/i;)Lorg/apache/http/impl/client/DefaultHttpClient;
        //   99: astore 23
        //   101: aload 23
        //   103: astore 5
        //   105: new 96	org/apache/http/client/methods/HttpPost
        //   108: dup
        //   109: aload 14
        //   111: invokevirtual 89	java/net/URL:toString	()Ljava/lang/String;
        //   114: invokespecial 97	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
        //   117: astore 24
        //   119: aload 24
        //   121: ldc 99
        //   123: aload_0
        //   124: getfield 30	com/touchtype/sync/client/i$2:d	Lcom/touchtype/sync/client/a;
        //   127: invokevirtual 102	com/touchtype/sync/client/a:toString	()Ljava/lang/String;
        //   130: invokevirtual 106	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   133: new 108	org/apache/http/entity/mime/MultipartEntity
        //   136: dup
        //   137: invokespecial 109	org/apache/http/entity/mime/MultipartEntity:<init>	()V
        //   140: astore 26
        //   142: aload 26
        //   144: ldc 111
        //   146: new 113	org/apache/http/entity/mime/content/FileBody
        //   149: dup
        //   150: aload_1
        //   151: aload_1
        //   152: invokevirtual 116	java/io/File:getName	()Ljava/lang/String;
        //   155: ldc 118
        //   157: aconst_null
        //   158: invokespecial 121	org/apache/http/entity/mime/content/FileBody:<init>	(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
        //   161: invokevirtual 125	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
        //   164: aload 26
        //   166: new 127	org/apache/http/entity/mime/FormBodyPart
        //   169: dup
        //   170: ldc 129
        //   172: new 131	org/apache/http/entity/mime/content/StringBody
        //   175: dup
        //   176: aload_0
        //   177: getfield 32	com/touchtype/sync/client/i$2:e	Ljava/lang/String;
        //   180: ldc 133
        //   182: ldc 135
        //   184: invokestatic 141	java/nio/charset/Charset:forName	(Ljava/lang/String;)Ljava/nio/charset/Charset;
        //   187: invokespecial 144	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/nio/charset/Charset;)V
        //   190: invokespecial 146	org/apache/http/entity/mime/FormBodyPart:<init>	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
        //   193: invokevirtual 149	org/apache/http/entity/mime/MultipartEntity:addPart	(Lorg/apache/http/entity/mime/FormBodyPart;)V
        //   196: aload 24
        //   198: aload 26
        //   200: invokevirtual 153	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
        //   203: aload 5
        //   205: aload 24
        //   207: invokevirtual 159	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
        //   210: astore 27
        //   212: aload 27
        //   214: invokeinterface 165 1 0
        //   219: invokeinterface 171 1 0
        //   224: istore 28
        //   226: iload 17
        //   228: iconst_5
        //   229: if_icmpge +35 -> 264
        //   232: iload 28
        //   234: sipush 500
        //   237: if_icmpne +27 -> 264
        //   240: lload 18
        //   242: invokestatic 177	java/lang/Thread:sleep	(J)V
        //   245: lload 18
        //   247: iconst_1
        //   248: lshl
        //   249: lstore 34
        //   251: iinc 17 1
        //   254: lload 34
        //   256: lstore 18
        //   258: aload 5
        //   260: astore_2
        //   261: goto -169 -> 92
        //   264: aload 27
        //   266: invokeinterface 181 1 0
        //   271: astore 29
        //   273: aload 29
        //   275: ifnonnull +34 -> 309
        //   278: aload_0
        //   279: getfield 26	com/touchtype/sync/client/i$2:b	Lcom/touchtype/sync/client/j;
        //   282: aconst_null
        //   283: ldc 183
        //   285: iload 28
        //   287: aconst_null
        //   288: aload 14
        //   290: invokevirtual 186	com/touchtype/sync/client/j:a	(Ljava/io/InputStream;Ljava/lang/String;ILjava/util/Map;Ljava/net/URL;)V
        //   293: aload 5
        //   295: ifnull -255 -> 40
        //   298: aload 5
        //   300: invokevirtual 190	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
        //   303: invokeinterface 195 1 0
        //   308: return
        //   309: aload 29
        //   311: invokeinterface 201 1 0
        //   316: astore 30
        //   318: aload 29
        //   320: invokeinterface 205 1 0
        //   325: astore 31
        //   327: aload 30
        //   329: ifnull +113 -> 442
        //   332: aload 30
        //   334: invokeinterface 210 1 0
        //   339: astore 32
        //   341: aload 32
        //   343: ifnull +105 -> 448
        //   346: aload 32
        //   348: ldc 212
        //   350: invokevirtual 218	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //   353: ifeq +95 -> 448
        //   356: new 220	java/util/zip/GZIPInputStream
        //   359: dup
        //   360: aload 31
        //   362: invokespecial 223	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
        //   365: astore 33
        //   367: aload_0
        //   368: getfield 26	com/touchtype/sync/client/i$2:b	Lcom/touchtype/sync/client/j;
        //   371: aload 33
        //   373: aload 29
        //   375: invokeinterface 226 1 0
        //   380: invokeinterface 210 1 0
        //   385: iload 28
        //   387: aconst_null
        //   388: aload 14
        //   390: invokevirtual 186	com/touchtype/sync/client/j:a	(Ljava/io/InputStream;Ljava/lang/String;ILjava/util/Map;Ljava/net/URL;)V
        //   393: goto -100 -> 293
        //   396: astore 25
        //   398: aload 5
        //   400: astore_2
        //   401: aload_0
        //   402: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   405: invokestatic 56	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
        //   408: ldc 58
        //   410: ldc 228
        //   412: invokestatic 91	com/touchtype/sync/client/e:a	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
        //   415: invokestatic 232	java/lang/Thread:currentThread	()Ljava/lang/Thread;
        //   418: invokevirtual 235	java/lang/Thread:interrupt	()V
        //   421: aload_2
        //   422: ifnull +12 -> 434
        //   425: aload_2
        //   426: invokevirtual 190	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
        //   429: invokeinterface 195 1 0
        //   434: aload_0
        //   435: getfield 26	com/touchtype/sync/client/i$2:b	Lcom/touchtype/sync/client/j;
        //   438: invokevirtual 69	com/touchtype/sync/client/j:a	()V
        //   441: return
        //   442: aconst_null
        //   443: astore 32
        //   445: goto -104 -> 341
        //   448: aload 31
        //   450: astore 33
        //   452: goto -85 -> 367
        //   455: astore 12
        //   457: aconst_null
        //   458: astore 5
        //   460: aload 12
        //   462: astore 13
        //   464: aload_0
        //   465: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   468: invokestatic 56	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
        //   471: ldc 58
        //   473: new 237	java/lang/StringBuilder
        //   476: dup
        //   477: ldc 239
        //   479: invokespecial 240	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   482: aload 13
        //   484: invokevirtual 243	java/io/IOException:getMessage	()Ljava/lang/String;
        //   487: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   490: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   493: invokestatic 65	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
        //   496: aload 5
        //   498: ifnull -64 -> 434
        //   501: aload 5
        //   503: invokevirtual 190	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
        //   506: invokeinterface 195 1 0
        //   511: goto -77 -> 434
        //   514: astore 10
        //   516: aconst_null
        //   517: astore 5
        //   519: aload 10
        //   521: astore 11
        //   523: aload_0
        //   524: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   527: invokestatic 56	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
        //   530: ldc 58
        //   532: new 237	java/lang/StringBuilder
        //   535: dup
        //   536: ldc 250
        //   538: invokespecial 240	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   541: aload 11
        //   543: invokevirtual 251	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
        //   546: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   549: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   552: invokestatic 65	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
        //   555: aload 5
        //   557: ifnull -123 -> 434
        //   560: aload 5
        //   562: invokevirtual 190	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
        //   565: invokeinterface 195 1 0
        //   570: goto -136 -> 434
        //   573: astore 8
        //   575: aconst_null
        //   576: astore 5
        //   578: aload 8
        //   580: astore 9
        //   582: aload_0
        //   583: getfield 22	com/touchtype/sync/client/i$2:f	Lcom/touchtype/sync/client/i;
        //   586: invokestatic 56	com/touchtype/sync/client/i:a	(Lcom/touchtype/sync/client/i;)Lcom/touchtype/sync/client/LoggingListener;
        //   589: ldc 58
        //   591: new 237	java/lang/StringBuilder
        //   594: dup
        //   595: ldc 253
        //   597: invokespecial 240	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   600: aload 9
        //   602: invokevirtual 254	java/lang/Throwable:getMessage	()Ljava/lang/String;
        //   605: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   608: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   611: invokestatic 65	com/touchtype/sync/client/e:c	(Lcom/touchtype/sync/client/LoggingListener;Ljava/lang/String;Ljava/lang/String;)V
        //   614: aload 5
        //   616: ifnull -182 -> 434
        //   619: aload 5
        //   621: invokevirtual 190	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
        //   624: invokeinterface 195 1 0
        //   629: goto -195 -> 434
        //   632: astore 7
        //   634: aconst_null
        //   635: astore 5
        //   637: aload 7
        //   639: astore 6
        //   641: aload 5
        //   643: ifnull +13 -> 656
        //   646: aload 5
        //   648: invokevirtual 190	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
        //   651: invokeinterface 195 1 0
        //   656: aload 6
        //   658: athrow
        //   659: astore 6
        //   661: goto -20 -> 641
        //   664: astore 4
        //   666: aload_2
        //   667: astore 5
        //   669: aload 4
        //   671: astore 6
        //   673: goto -32 -> 641
        //   676: astore 9
        //   678: goto -96 -> 582
        //   681: astore 22
        //   683: aload_2
        //   684: astore 5
        //   686: aload 22
        //   688: astore 9
        //   690: goto -108 -> 582
        //   693: astore 11
        //   695: goto -172 -> 523
        //   698: astore 21
        //   700: aload_2
        //   701: astore 5
        //   703: aload 21
        //   705: astore 11
        //   707: goto -184 -> 523
        //   710: astore 13
        //   712: goto -248 -> 464
        //   715: astore 20
        //   717: aload_2
        //   718: astore 5
        //   720: aload 20
        //   722: astore 13
        //   724: goto -260 -> 464
        //   727: astore_3
        //   728: goto -327 -> 401
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	731	0	this	2
        //   11	141	1	localFile	java.io.File
        //   42	676	2	localObject1	Object
        //   727	1	3	localInterruptedException1	java.lang.InterruptedException
        //   664	6	4	localObject2	Object
        //   103	616	5	localObject3	Object
        //   639	18	6	localObject4	Object
        //   659	1	6	localObject5	Object
        //   671	1	6	localObject6	Object
        //   632	6	7	localObject7	Object
        //   573	6	8	localThrowable1	java.lang.Throwable
        //   580	21	9	localThrowable2	java.lang.Throwable
        //   676	1	9	localThrowable3	java.lang.Throwable
        //   688	1	9	localObject8	Object
        //   514	6	10	localIllegalArgumentException1	IllegalArgumentException
        //   521	21	11	localIllegalArgumentException2	IllegalArgumentException
        //   693	1	11	localIllegalArgumentException3	IllegalArgumentException
        //   705	1	11	localObject9	Object
        //   455	6	12	localIOException1	IOException
        //   462	21	13	localIOException2	IOException
        //   710	1	13	localIOException3	IOException
        //   722	1	13	localObject10	Object
        //   50	339	14	localURL	URL
        //   66	23	15	l1	long
        //   86	166	17	i	int
        //   90	167	18	l2	long
        //   715	6	20	localIOException4	IOException
        //   698	6	21	localIllegalArgumentException4	IllegalArgumentException
        //   681	6	22	localThrowable4	java.lang.Throwable
        //   99	3	23	localDefaultHttpClient	org.apache.http.impl.client.DefaultHttpClient
        //   117	89	24	localHttpPost	org.apache.http.client.methods.HttpPost
        //   396	1	25	localInterruptedException2	java.lang.InterruptedException
        //   140	59	26	localMultipartEntity	org.apache.http.entity.mime.MultipartEntity
        //   210	55	27	localHttpResponse	org.apache.http.HttpResponse
        //   224	162	28	j	int
        //   271	103	29	localHttpEntity	org.apache.http.HttpEntity
        //   316	17	30	localHeader	org.apache.http.Header
        //   325	124	31	localInputStream	java.io.InputStream
        //   339	105	32	str	String
        //   365	86	33	localObject11	Object
        //   249	6	34	l3	long
        // Exception table:
        //   from	to	target	type
        //   105	226	396	java/lang/InterruptedException
        //   240	245	396	java/lang/InterruptedException
        //   264	273	396	java/lang/InterruptedException
        //   278	293	396	java/lang/InterruptedException
        //   309	327	396	java/lang/InterruptedException
        //   332	341	396	java/lang/InterruptedException
        //   346	367	396	java/lang/InterruptedException
        //   367	393	396	java/lang/InterruptedException
        //   43	85	455	java/io/IOException
        //   43	85	514	java/lang/IllegalArgumentException
        //   43	85	573	java/lang/Throwable
        //   43	85	632	finally
        //   105	226	659	finally
        //   240	245	659	finally
        //   264	273	659	finally
        //   278	293	659	finally
        //   309	327	659	finally
        //   332	341	659	finally
        //   346	367	659	finally
        //   367	393	659	finally
        //   464	496	659	finally
        //   523	555	659	finally
        //   582	614	659	finally
        //   92	101	664	finally
        //   401	421	664	finally
        //   105	226	676	java/lang/Throwable
        //   240	245	676	java/lang/Throwable
        //   264	273	676	java/lang/Throwable
        //   278	293	676	java/lang/Throwable
        //   309	327	676	java/lang/Throwable
        //   332	341	676	java/lang/Throwable
        //   346	367	676	java/lang/Throwable
        //   367	393	676	java/lang/Throwable
        //   92	101	681	java/lang/Throwable
        //   105	226	693	java/lang/IllegalArgumentException
        //   240	245	693	java/lang/IllegalArgumentException
        //   264	273	693	java/lang/IllegalArgumentException
        //   278	293	693	java/lang/IllegalArgumentException
        //   309	327	693	java/lang/IllegalArgumentException
        //   332	341	693	java/lang/IllegalArgumentException
        //   346	367	693	java/lang/IllegalArgumentException
        //   367	393	693	java/lang/IllegalArgumentException
        //   92	101	698	java/lang/IllegalArgumentException
        //   105	226	710	java/io/IOException
        //   240	245	710	java/io/IOException
        //   264	273	710	java/io/IOException
        //   278	293	710	java/io/IOException
        //   309	327	710	java/io/IOException
        //   332	341	710	java/io/IOException
        //   346	367	710	java/io/IOException
        //   367	393	710	java/io/IOException
        //   92	101	715	java/io/IOException
        //   43	85	727	java/lang/InterruptedException
        //   92	101	727	java/lang/InterruptedException
      }
    };
  }
  
  final Runnable a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, j paramj)
  {
    b localb = new b();
    localb.a("device_code", paramString3);
    localb.a("device_id", paramString4);
    localb.a("email", paramString1);
    localb.a("description", paramString2);
    localb.a("package_name", paramString6);
    localb.a("language", paramString5);
    localb.a("opt_in", String.valueOf(paramBoolean));
    return a("/auth/email", a.b, null, null, localb, paramj);
  }
  
  final Runnable a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean1, String paramString6, boolean paramBoolean2, j paramj)
  {
    b localb = new b();
    if (paramBoolean1) {
      localb.a("action", "login");
    }
    localb.a("access_token", paramString2);
    localb.a("device_id", paramString4);
    localb.a("description", paramString1);
    localb.a("package_name", paramString6);
    localb.a("device_code", paramString3);
    localb.a("language", paramString5);
    localb.a("opt_in", String.valueOf(paramBoolean2));
    return a("/auth/facebook", a.b, null, null, localb, paramj);
  }
  
  final Runnable a(String paramString1, String paramString2, String paramString3, boolean paramBoolean, j paramj)
  {
    b localb;
    if ((!paramBoolean) && (paramString1 != null))
    {
      localb = new b();
      localb.a("since", paramString1);
    }
    for (;;)
    {
      a locala = new a(paramString2, paramString3);
      return a("/sync", a.a, locala, localb, null, paramj);
      localb = null;
    }
  }
  
  public final Runnable a(String paramString1, String paramString2, boolean paramBoolean, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    b localb = new b();
    localb.a("opt_in", String.valueOf(paramBoolean));
    return a("/user/marketing/opt-in", a.b, locala, null, localb, paramj);
  }
  
  final Runnable b(String paramString1, String paramString2, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    return a("/devices", a.a, locala, null, null, paramj);
  }
  
  final Runnable b(String paramString1, String paramString2, String paramString3, String paramString4, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    b localb = new b();
    localb.a("description", paramString4);
    return a("/devices/" + paramString3, a.c, locala, null, localb, paramj);
  }
  
  public final Runnable c(String paramString1, String paramString2, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    return a("/sync", a.d, locala, null, null, paramj);
  }
  
  final Runnable c(String paramString1, String paramString2, String paramString3, String paramString4, j paramj)
  {
    a locala = new a(paramString3, paramString4);
    b localb = new b();
    localb.a("words", paramString1);
    if (paramString2 != null) {
      localb.a("locales", paramString2);
    }
    for (;;)
    {
      return a("/sync/stopwords", a.b, locala, null, localb, paramj);
      localb.a("locales", "[]");
    }
  }
  
  final Runnable d(String paramString1, String paramString2, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    return a("", a.d, locala, null, null, paramj);
  }
  
  public final Runnable e(String paramString1, String paramString2, j paramj)
  {
    a locala = new a(paramString1, paramString2);
    return a("/user/marketing", a.a, locala, null, null, paramj);
  }
  
  static enum a
  {
    static
    {
      a[] arrayOfa = new a[4];
      arrayOfa[0] = a;
      arrayOfa[1] = b;
      arrayOfa[2] = c;
      arrayOfa[3] = d;
      e = arrayOfa;
    }
    
    private a() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.i
 * JD-Core Version:    0.7.0.1
 */