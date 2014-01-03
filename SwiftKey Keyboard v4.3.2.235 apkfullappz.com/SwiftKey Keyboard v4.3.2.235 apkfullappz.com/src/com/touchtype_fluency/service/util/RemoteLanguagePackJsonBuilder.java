package com.touchtype_fluency.service.util;

import com.google.common.io.CharStreams;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class RemoteLanguagePackJsonBuilder
{
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  private static final String HASH_ENCODING_NAME = "SHA-1";
  private static final String TAG = RemoteLanguagePackJsonBuilder.class.getSimpleName();
  
  private static String buildDisplayName(Locale paramLocale, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = paramLocale.getDisplayLanguage(paramLocale);
    localStringBuilder.append(str.substring(0, 1).toUpperCase(paramLocale)).append(str.substring(1));
    if (paramBoolean) {
      localStringBuilder.append(" (").append(paramLocale.getCountry()).append(")");
    }
    return localStringBuilder.toString();
  }
  
  private static JSONObject createExtraDataJSON(InputStream paramInputStream)
    throws IOException, JSONException
  {
    StringWriter localStringWriter = new StringWriter();
    InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream);
    char[] arrayOfChar = new char[4096];
    for (;;)
    {
      int i = localInputStreamReader.read(arrayOfChar);
      if (i == -1) {
        break;
      }
      if (i > 0) {
        localStringWriter.write(arrayOfChar, 0, i);
      }
    }
    Object localObject = new JSONTokener(localStringWriter.toString()).nextValue();
    if ((localObject instanceof JSONObject)) {
      return (JSONObject)localObject;
    }
    throw new JSONException("extraData.json should contain an object at the top level");
  }
  
  public static String createPreinstalledConfiguration(File paramFile)
  {
    for (;;)
    {
      int j;
      try
      {
        File[] arrayOfFile = paramFile.listFiles(new FilenameFilter()
        {
          public boolean accept(File paramAnonymousFile, String paramAnonymousString)
          {
            return paramAnonymousString.endsWith(".zip");
          }
        });
        if (arrayOfFile == null)
        {
          LogUtil.e(TAG, "Unable to create preinstalled JSON - no files in preinstall directory " + paramFile);
          return null;
        }
        Arrays.sort(arrayOfFile);
        JSONArray localJSONArray = new JSONArray();
        if (arrayOfFile != null)
        {
          int i = arrayOfFile.length;
          j = 0;
          if (j < i)
          {
            JSONObject localJSONObject = createPreinstalledJSON(arrayOfFile[j]);
            if (localJSONObject == null) {
              break label146;
            }
            localJSONArray.put(localJSONObject);
            break label146;
          }
          if (localJSONArray.length() > 0)
          {
            String str = localJSONArray.toString();
            return str;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        LogUtil.e(TAG, localJSONException.getMessage(), localJSONException);
        Assert.fail("This really shouldn't happen: " + localJSONException.getMessage());
      }
      return null;
      label146:
      j++;
    }
  }
  
  /* Error */
  private static JSONObject createPreinstalledJSON(File paramFile)
    throws JSONException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aconst_null
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: new 161	java/io/FileInputStream
    //   15: dup
    //   16: aload_0
    //   17: invokespecial 164	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   20: astore 6
    //   22: new 166	java/security/DigestInputStream
    //   25: dup
    //   26: aload 6
    //   28: ldc 11
    //   30: invokestatic 172	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   33: invokespecial 175	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
    //   36: astore 7
    //   38: new 177	java/util/zip/ZipInputStream
    //   41: dup
    //   42: aload 7
    //   44: invokespecial 178	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   47: astore 8
    //   49: aload 8
    //   51: invokevirtual 182	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   54: astore 18
    //   56: aload 18
    //   58: ifnull +167 -> 225
    //   61: aload 18
    //   63: invokevirtual 187	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   66: astore 25
    //   68: aload 25
    //   70: ldc 189
    //   72: invokevirtual 193	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   75: ifeq +83 -> 158
    //   78: aload 8
    //   80: invokestatic 195	com/touchtype_fluency/service/util/RemoteLanguagePackJsonBuilder:createPreinstalledJSON	(Ljava/io/InputStream;)Lorg/json/JSONObject;
    //   83: astore_1
    //   84: aload_1
    //   85: ldc 197
    //   87: aload_0
    //   88: invokevirtual 201	java/io/File:toURI	()Ljava/net/URI;
    //   91: invokevirtual 204	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   94: pop
    //   95: aload 8
    //   97: invokevirtual 207	java/util/zip/ZipInputStream:closeEntry	()V
    //   100: goto -51 -> 49
    //   103: astore 15
    //   105: aload 8
    //   107: astore 16
    //   109: aload 7
    //   111: astore 4
    //   113: aload 6
    //   115: astore 17
    //   117: aconst_null
    //   118: astore_1
    //   119: aload 16
    //   121: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   124: aload 4
    //   126: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   129: aload 17
    //   131: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   134: aload_1
    //   135: ifnull +21 -> 156
    //   138: aload_1
    //   139: ldc 215
    //   141: aload 4
    //   143: invokevirtual 219	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
    //   146: invokevirtual 223	java/security/MessageDigest:digest	()[B
    //   149: invokestatic 229	com/touchtype_fluency/service/util/StringUtil:digestToString	([B)Ljava/lang/String;
    //   152: invokevirtual 204	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   155: pop
    //   156: aload_1
    //   157: areturn
    //   158: aload 25
    //   160: ldc 231
    //   162: invokevirtual 193	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   165: ifeq -70 -> 95
    //   168: aload 8
    //   170: invokestatic 233	com/touchtype_fluency/service/util/RemoteLanguagePackJsonBuilder:createExtraDataJSON	(Ljava/io/InputStream;)Lorg/json/JSONObject;
    //   173: astore 26
    //   175: aload 26
    //   177: astore_2
    //   178: goto -83 -> 95
    //   181: astore 24
    //   183: aload 8
    //   185: invokevirtual 207	java/util/zip/ZipInputStream:closeEntry	()V
    //   188: aload 24
    //   190: athrow
    //   191: astore 12
    //   193: aload 8
    //   195: astore 13
    //   197: aload 7
    //   199: astore 4
    //   201: aload 6
    //   203: astore 14
    //   205: aload 13
    //   207: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   210: aload 4
    //   212: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   215: aload 14
    //   217: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   220: aconst_null
    //   221: astore_1
    //   222: goto -88 -> 134
    //   225: aload_2
    //   226: ifnull +89 -> 315
    //   229: aload_1
    //   230: ifnull +85 -> 315
    //   233: aload_2
    //   234: invokevirtual 237	org/json/JSONObject:keys	()Ljava/util/Iterator;
    //   237: astore 21
    //   239: aload 21
    //   241: invokeinterface 243 1 0
    //   246: ifeq +69 -> 315
    //   249: aload 21
    //   251: invokeinterface 246 1 0
    //   256: checkcast 38	java/lang/String
    //   259: astore 22
    //   261: aload_1
    //   262: aload 22
    //   264: aload_2
    //   265: aload 22
    //   267: invokevirtual 250	org/json/JSONObject:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   270: invokevirtual 204	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   273: pop
    //   274: goto -35 -> 239
    //   277: astore 10
    //   279: aload 8
    //   281: astore 5
    //   283: aload 7
    //   285: astore 4
    //   287: aload 6
    //   289: astore_3
    //   290: aload 10
    //   292: invokevirtual 251	java/security/NoSuchAlgorithmException:getMessage	()Ljava/lang/String;
    //   295: invokestatic 157	junit/framework/Assert:fail	(Ljava/lang/String;)V
    //   298: aload 5
    //   300: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   303: aload 4
    //   305: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   308: aload_3
    //   309: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   312: goto -178 -> 134
    //   315: sipush 4096
    //   318: newarray byte
    //   320: astore 19
    //   322: aload 7
    //   324: aload 19
    //   326: invokevirtual 254	java/security/DigestInputStream:read	([B)I
    //   329: istore 20
    //   331: iload 20
    //   333: iconst_m1
    //   334: if_icmpne -12 -> 322
    //   337: aload 8
    //   339: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   342: aload 7
    //   344: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   347: aload 6
    //   349: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   352: aload 7
    //   354: astore 4
    //   356: goto -222 -> 134
    //   359: astore 9
    //   361: aload 5
    //   363: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   366: aload 4
    //   368: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   371: aload_3
    //   372: invokestatic 213	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   375: aload 9
    //   377: athrow
    //   378: astore 9
    //   380: aload 6
    //   382: astore_3
    //   383: aconst_null
    //   384: astore 4
    //   386: aconst_null
    //   387: astore 5
    //   389: goto -28 -> 361
    //   392: astore 9
    //   394: aload 7
    //   396: astore 4
    //   398: aload 6
    //   400: astore_3
    //   401: aconst_null
    //   402: astore 5
    //   404: goto -43 -> 361
    //   407: astore 9
    //   409: aload 8
    //   411: astore 5
    //   413: aload 7
    //   415: astore 4
    //   417: aload 6
    //   419: astore_3
    //   420: goto -59 -> 361
    //   423: astore 10
    //   425: aconst_null
    //   426: astore 4
    //   428: aconst_null
    //   429: astore_3
    //   430: aconst_null
    //   431: astore_1
    //   432: aconst_null
    //   433: astore 5
    //   435: goto -145 -> 290
    //   438: astore 10
    //   440: aload 6
    //   442: astore_3
    //   443: aconst_null
    //   444: astore 4
    //   446: aconst_null
    //   447: astore_1
    //   448: aconst_null
    //   449: astore 5
    //   451: goto -161 -> 290
    //   454: astore 10
    //   456: aload 7
    //   458: astore 4
    //   460: aload 6
    //   462: astore_3
    //   463: aconst_null
    //   464: astore_1
    //   465: aconst_null
    //   466: astore 5
    //   468: goto -178 -> 290
    //   471: astore 33
    //   473: aconst_null
    //   474: astore 4
    //   476: aconst_null
    //   477: astore 14
    //   479: aconst_null
    //   480: astore 13
    //   482: goto -277 -> 205
    //   485: astore 31
    //   487: aload 6
    //   489: astore 14
    //   491: aconst_null
    //   492: astore 4
    //   494: aconst_null
    //   495: astore 13
    //   497: goto -292 -> 205
    //   500: astore 29
    //   502: aload 7
    //   504: astore 4
    //   506: aload 6
    //   508: astore 14
    //   510: aconst_null
    //   511: astore 13
    //   513: goto -308 -> 205
    //   516: astore 32
    //   518: aconst_null
    //   519: astore 4
    //   521: aconst_null
    //   522: astore 17
    //   524: aconst_null
    //   525: astore 16
    //   527: goto -410 -> 117
    //   530: astore 30
    //   532: aload 6
    //   534: astore 17
    //   536: aconst_null
    //   537: astore 4
    //   539: aconst_null
    //   540: astore 16
    //   542: goto -425 -> 117
    //   545: astore 28
    //   547: aload 7
    //   549: astore 4
    //   551: aload 6
    //   553: astore 17
    //   555: aconst_null
    //   556: astore 16
    //   558: goto -441 -> 117
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	561	0	paramFile	File
    //   1	464	1	localJSONObject1	JSONObject
    //   3	262	2	localObject1	Object
    //   5	458	3	localObject2	Object
    //   7	543	4	localObject3	Object
    //   10	457	5	localObject4	Object
    //   20	532	6	localFileInputStream1	java.io.FileInputStream
    //   36	512	7	localDigestInputStream	java.security.DigestInputStream
    //   47	363	8	localZipInputStream1	java.util.zip.ZipInputStream
    //   359	17	9	localObject5	Object
    //   378	1	9	localObject6	Object
    //   392	1	9	localObject7	Object
    //   407	1	9	localObject8	Object
    //   277	14	10	localNoSuchAlgorithmException1	java.security.NoSuchAlgorithmException
    //   423	1	10	localNoSuchAlgorithmException2	java.security.NoSuchAlgorithmException
    //   438	1	10	localNoSuchAlgorithmException3	java.security.NoSuchAlgorithmException
    //   454	1	10	localNoSuchAlgorithmException4	java.security.NoSuchAlgorithmException
    //   191	1	12	localJSONException1	JSONException
    //   195	317	13	localZipInputStream2	java.util.zip.ZipInputStream
    //   203	306	14	localFileInputStream2	java.io.FileInputStream
    //   103	1	15	localIOException1	IOException
    //   107	450	16	localZipInputStream3	java.util.zip.ZipInputStream
    //   115	439	17	localFileInputStream3	java.io.FileInputStream
    //   54	8	18	localZipEntry	java.util.zip.ZipEntry
    //   320	5	19	arrayOfByte	byte[]
    //   329	6	20	i	int
    //   237	13	21	localIterator	java.util.Iterator
    //   259	7	22	str1	String
    //   181	8	24	localObject9	Object
    //   66	93	25	str2	String
    //   173	3	26	localJSONObject2	JSONObject
    //   545	1	28	localIOException2	IOException
    //   500	1	29	localJSONException2	JSONException
    //   530	1	30	localIOException3	IOException
    //   485	1	31	localJSONException3	JSONException
    //   516	1	32	localIOException4	IOException
    //   471	1	33	localJSONException4	JSONException
    // Exception table:
    //   from	to	target	type
    //   49	56	103	java/io/IOException
    //   95	100	103	java/io/IOException
    //   183	191	103	java/io/IOException
    //   233	239	103	java/io/IOException
    //   239	274	103	java/io/IOException
    //   315	322	103	java/io/IOException
    //   322	331	103	java/io/IOException
    //   61	95	181	finally
    //   158	175	181	finally
    //   49	56	191	org/json/JSONException
    //   95	100	191	org/json/JSONException
    //   183	191	191	org/json/JSONException
    //   233	239	191	org/json/JSONException
    //   239	274	191	org/json/JSONException
    //   315	322	191	org/json/JSONException
    //   322	331	191	org/json/JSONException
    //   49	56	277	java/security/NoSuchAlgorithmException
    //   95	100	277	java/security/NoSuchAlgorithmException
    //   183	191	277	java/security/NoSuchAlgorithmException
    //   233	239	277	java/security/NoSuchAlgorithmException
    //   239	274	277	java/security/NoSuchAlgorithmException
    //   315	322	277	java/security/NoSuchAlgorithmException
    //   322	331	277	java/security/NoSuchAlgorithmException
    //   12	22	359	finally
    //   290	298	359	finally
    //   22	38	378	finally
    //   38	49	392	finally
    //   49	56	407	finally
    //   95	100	407	finally
    //   183	191	407	finally
    //   233	239	407	finally
    //   239	274	407	finally
    //   315	322	407	finally
    //   322	331	407	finally
    //   12	22	423	java/security/NoSuchAlgorithmException
    //   22	38	438	java/security/NoSuchAlgorithmException
    //   38	49	454	java/security/NoSuchAlgorithmException
    //   12	22	471	org/json/JSONException
    //   22	38	485	org/json/JSONException
    //   38	49	500	org/json/JSONException
    //   12	22	516	java/io/IOException
    //   22	38	530	java/io/IOException
    //   38	49	545	java/io/IOException
  }
  
  private static JSONObject createPreinstalledJSON(InputStream paramInputStream)
    throws IOException, JSONException
  {
    String str1 = CharStreams.toString(new InputStreamReader(paramInputStream));
    JSONObject localJSONObject = new JSONObject();
    Object localObject = new JSONTokener(str1).nextValue();
    if ((localObject instanceof JSONObject))
    {
      JSONArray localJSONArray = ((JSONObject)localObject).getJSONArray("tags");
      int i = localJSONArray.length();
      String str2 = null;
      int j = 0;
      if (j < i)
      {
        String str3 = localJSONArray.getString(j);
        String[] arrayOfString;
        if (str3.startsWith("id:"))
        {
          arrayOfString = str3.split("[:_]");
          if (arrayOfString.length == 3)
          {
            Locale localLocale1 = new Locale(arrayOfString[1], arrayOfString[2]);
            localJSONObject.put("language", arrayOfString[1]).put("country", arrayOfString[2]);
            str2 = buildDisplayName(localLocale1, true);
          }
        }
        for (;;)
        {
          localJSONObject.put("name", str2);
          j++;
          break;
          if ((arrayOfString.length == 2) && (!localJSONObject.has("language")))
          {
            Locale localLocale2 = new Locale(arrayOfString[1]);
            localJSONObject.put("language", arrayOfString[1]);
            str2 = buildDisplayName(localLocale2, false);
            continue;
            if (str3.startsWith("name:")) {
              str2 = str3.split(":")[1];
            }
          }
        }
      }
    }
    else
    {
      throw new JSONException("preinstalled zip contains .config, but it isn't JSON");
    }
    return localJSONObject;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.RemoteLanguagePackJsonBuilder
 * JD-Core Version:    0.7.0.1
 */