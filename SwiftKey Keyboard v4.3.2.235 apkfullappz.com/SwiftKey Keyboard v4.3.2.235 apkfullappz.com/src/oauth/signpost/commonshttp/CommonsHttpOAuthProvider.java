package oauth.signpost.commonshttp;

import java.io.IOException;
import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.http.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public final class CommonsHttpOAuthProvider
  extends AbstractOAuthProvider
{
  private transient HttpClient httpClient = new DefaultHttpClient();
  
  public CommonsHttpOAuthProvider(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, paramString2, paramString3);
  }
  
  protected void closeConnection(HttpRequest paramHttpRequest, oauth.signpost.http.HttpResponse paramHttpResponse)
    throws Exception
  {
    HttpEntity localHttpEntity;
    if (paramHttpResponse != null)
    {
      localHttpEntity = ((org.apache.http.HttpResponse)paramHttpResponse.unwrap()).getEntity();
      if (localHttpEntity == null) {}
    }
    try
    {
      localHttpEntity.consumeContent();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  protected HttpRequest createRequest(String paramString)
    throws Exception
  {
    return new HttpRequestAdapter(new HttpPost(paramString));
  }
  
  protected oauth.signpost.http.HttpResponse sendRequest(HttpRequest paramHttpRequest)
    throws Exception
  {
    return new HttpResponseAdapter(this.httpClient.execute((HttpUriRequest)paramHttpRequest.unwrap()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     oauth.signpost.commonshttp.CommonsHttpOAuthProvider
 * JD-Core Version:    0.7.0.1
 */