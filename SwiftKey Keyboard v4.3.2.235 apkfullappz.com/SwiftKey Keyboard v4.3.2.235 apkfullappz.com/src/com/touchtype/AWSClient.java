package com.touchtype;

import android.util.Base64;
import com.google.common.io.Files;
import com.touchtype.common.util.FileUtils;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.http.SSLClientFactory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.BasicHttpParams;

public class AWSClient
{
  private static final String TAG = AWSClient.class.getSimpleName();
  private final String mBucketName;
  private final HttpClient mHttpClient;
  private Mac mMac = null;
  private SecretKeySpec mSigningKey = null;
  
  public AWSClient(String paramString)
    throws Exception
  {
    this.mBucketName = paramString;
    this.mHttpClient = SSLClientFactory.createHttpClient(new BasicHttpParams());
    setKey("Et8YfDukwrviCPQDoTw0vBzoYJ1UdfesDK7UC7VP");
  }
  
  private String encodeBase64(byte[] paramArrayOfByte)
  {
    String str = Base64.encodeToString(paramArrayOfByte, 2);
    if (str.endsWith("\r\n")) {
      str = str.substring(0, -2 + str.length());
    }
    return str;
  }
  
  private void setKey(String paramString)
    throws Exception
  {
    this.mSigningKey = new SecretKeySpec(paramString.getBytes("UTF8"), "HmacSHA1");
    this.mMac = Mac.getInstance("HmacSHA1");
    this.mMac.init(this.mSigningKey);
  }
  
  private String sign(String paramString)
    throws Exception
  {
    return encodeBase64(this.mMac.doFinal(paramString.getBytes("UTF8")));
  }
  
  public int put(File paramFile)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
    String str1 = FileUtils.getFileContentType(paramFile.getName());
    String str2 = localSimpleDateFormat.format(localCalendar.getTime());
    String str3 = "/" + this.mBucketName + "/" + paramFile.getName();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("PUT\n");
    localStringBuffer.append("\n");
    localStringBuffer.append(str1).append("\n");
    localStringBuffer.append(str2).append("\n");
    localStringBuffer.append(str3);
    String str4 = String.format("AWS AKIAIFOSN2OP65NBTM4A:%s", new Object[] { sign(localStringBuffer.toString()) });
    HttpPut localHttpPut = new HttpPut("https://s3-eu-west-1.amazonaws.com" + str3);
    localHttpPut.addHeader("Host", "s3-eu-west-1.amazonaws.com");
    localHttpPut.addHeader("Date", str2);
    localHttpPut.addHeader("Authorization", str4);
    localHttpPut.addHeader("Content-Type", str1);
    localHttpPut.addHeader("Expect", "100-continue");
    localHttpPut.setEntity(new ByteArrayEntity(Files.toByteArray(paramFile)));
    try
    {
      int i = this.mHttpClient.execute(localHttpPut).getStatusLine().getStatusCode();
      return i;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      LogUtil.e(TAG, localClientProtocolException.getMessage());
      return -1;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage());
      return -1;
    }
    finally
    {
      this.mHttpClient.getConnectionManager().shutdown();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.AWSClient
 * JD-Core Version:    0.7.0.1
 */