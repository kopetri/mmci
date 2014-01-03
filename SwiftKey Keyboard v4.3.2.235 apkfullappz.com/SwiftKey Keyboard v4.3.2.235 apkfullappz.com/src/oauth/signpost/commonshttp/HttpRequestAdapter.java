package oauth.signpost.commonshttp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import oauth.signpost.http.HttpRequest;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;

public final class HttpRequestAdapter
  implements HttpRequest
{
  private HttpEntity entity;
  private HttpUriRequest request;
  
  public HttpRequestAdapter(HttpUriRequest paramHttpUriRequest)
  {
    this.request = paramHttpUriRequest;
    if ((paramHttpUriRequest instanceof HttpEntityEnclosingRequest)) {
      this.entity = ((HttpEntityEnclosingRequest)paramHttpUriRequest).getEntity();
    }
  }
  
  public String getContentType()
  {
    if (this.entity == null) {}
    Header localHeader;
    do
    {
      return null;
      localHeader = this.entity.getContentType();
    } while (localHeader == null);
    return localHeader.getValue();
  }
  
  public String getHeader(String paramString)
  {
    Header localHeader = this.request.getFirstHeader(paramString);
    if (localHeader == null) {
      return null;
    }
    return localHeader.getValue();
  }
  
  public InputStream getMessagePayload()
    throws IOException
  {
    if (this.entity == null) {
      return null;
    }
    return this.entity.getContent();
  }
  
  public String getMethod()
  {
    return this.request.getRequestLine().getMethod();
  }
  
  public String getRequestUrl()
  {
    return this.request.getURI().toString();
  }
  
  public void setHeader(String paramString1, String paramString2)
  {
    this.request.setHeader(paramString1, paramString2);
  }
  
  public Object unwrap()
  {
    return this.request;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     oauth.signpost.commonshttp.HttpRequestAdapter
 * JD-Core Version:    0.7.0.1
 */