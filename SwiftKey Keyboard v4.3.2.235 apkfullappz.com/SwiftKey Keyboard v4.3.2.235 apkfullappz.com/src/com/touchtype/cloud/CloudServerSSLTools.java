package com.touchtype.cloud;

import com.touchtype.util.LogUtil;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

final class CloudServerSSLTools
{
  public static final HostnameVerifier DO_NOT_VERIFY_HOST = new HostnameVerifier()
  {
    public boolean verify(String paramAnonymousString, SSLSession paramAnonymousSSLSession)
    {
      return true;
    }
  };
  private static SSLContext sslContext;
  
  static
  {
    TrustManager[] arrayOfTrustManager = new TrustManager[1];
    arrayOfTrustManager[0 = new X509TrustManager()
    {
      public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        throws CertificateException
      {}
      
      public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        throws CertificateException
      {}
      
      public X509Certificate[] getAcceptedIssuers()
      {
        return new X509Certificate[0];
      }
    };
    try
    {
      SSLContext localSSLContext = SSLContext.getInstance("TLS");
      sslContext = localSSLContext;
      localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
      return;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      LogUtil.e("CloudServerSSLTools", localNoSuchAlgorithmException.toString(), localNoSuchAlgorithmException);
      return;
    }
    catch (KeyManagementException localKeyManagementException)
    {
      LogUtil.e("CloudServerSSLTools", localKeyManagementException.toString(), localKeyManagementException);
    }
  }
  
  public static SSLContext getTrustAllSslContext()
  {
    return sslContext;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.CloudServerSSLTools
 * JD-Core Version:    0.7.0.1
 */