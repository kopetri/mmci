package com.touchtype_fluency.service.http;

import com.touchtype.util.LogUtil;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class SSLClientFactory
{
  static final int CONNECT_TIMEOUT_MS = 10000;
  private static final String HTTPS = "https";
  private static final int HTTPS_PORT = 443;
  static final int SO_TIMEOUT_MS = 30000;
  private static final String TAG = SSLClientFactory.class.getSimpleName();
  private static final String TLS_CONTEXT = "TLS";
  
  public static HttpClient createHttpClient(HttpParams paramHttpParams)
  {
    if (paramHttpParams == null) {}
    for (Object localObject = new BasicHttpParams();; localObject = paramHttpParams)
    {
      HttpConnectionParams.setConnectionTimeout((HttpParams)localObject, 10000);
      HttpConnectionParams.setSoTimeout((HttpParams)localObject, 30000);
      return voidSslCertificates(new DefaultHttpClient((HttpParams)localObject));
    }
  }
  
  private static HttpClient voidSslCertificates(HttpClient paramHttpClient)
  {
    try
    {
      VoidTrustManager localVoidTrustManager = new VoidTrustManager(null);
      SSLContext localSSLContext = SSLContext.getInstance("TLS");
      localSSLContext.init(null, new TrustManager[] { localVoidTrustManager }, null);
      VoidSSLSocketFactory localVoidSSLSocketFactory = new VoidSSLSocketFactory(localSSLContext);
      localVoidSSLSocketFactory.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager localClientConnectionManager = paramHttpClient.getConnectionManager();
      localClientConnectionManager.getSchemeRegistry().register(new Scheme("https", localVoidSSLSocketFactory, 443));
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localClientConnectionManager, paramHttpClient.getParams());
      return localDefaultHttpClient;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      LogUtil.e(TAG, localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
      return paramHttpClient;
    }
    catch (KeyManagementException localKeyManagementException)
    {
      LogUtil.e(TAG, localKeyManagementException.getMessage(), localKeyManagementException);
      return paramHttpClient;
    }
    catch (UnrecoverableKeyException localUnrecoverableKeyException)
    {
      LogUtil.e(TAG, localUnrecoverableKeyException.getMessage(), localUnrecoverableKeyException);
      return paramHttpClient;
    }
    catch (KeyStoreException localKeyStoreException)
    {
      LogUtil.e(TAG, localKeyStoreException.getMessage(), localKeyStoreException);
    }
    return paramHttpClient;
  }
  
  private static final class VoidSSLSocketFactory
    extends org.apache.http.conn.ssl.SSLSocketFactory
  {
    SSLContext sslContext = SSLContext.getInstance("TLS");
    
    public VoidSSLSocketFactory(KeyStore paramKeyStore)
      throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
      super();
      SSLClientFactory.VoidTrustManager localVoidTrustManager = new SSLClientFactory.VoidTrustManager(null);
      this.sslContext.init(null, new TrustManager[] { localVoidTrustManager }, null);
    }
    
    public VoidSSLSocketFactory(SSLContext paramSSLContext)
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException
    {
      super();
      this.sslContext = paramSSLContext;
    }
    
    public Socket createSocket()
      throws IOException
    {
      return this.sslContext.getSocketFactory().createSocket();
    }
    
    public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
      throws IOException, UnknownHostException
    {
      return this.sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
    }
  }
  
  private static final class VoidTrustManager
    implements X509TrustManager
  {
    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
      throws CertificateException
    {}
    
    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
      throws CertificateException
    {}
    
    public X509Certificate[] getAcceptedIssuers()
    {
      return null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.http.SSLClientFactory
 * JD-Core Version:    0.7.0.1
 */