package oauth.signpost.commonshttp;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

public final class HttpResponseAdapter
  implements oauth.signpost.http.HttpResponse
{
  private org.apache.http.HttpResponse response;
  
  public HttpResponseAdapter(org.apache.http.HttpResponse paramHttpResponse)
  {
    this.response = paramHttpResponse;
  }
  
  public InputStream getContent()
    throws IOException
  {
    return this.response.getEntity().getContent();
  }
  
  public String getReasonPhrase()
    throws Exception
  {
    return this.response.getStatusLine().getReasonPhrase();
  }
  
  public int getStatusCode()
    throws IOException
  {
    return this.response.getStatusLine().getStatusCode();
  }
  
  public Object unwrap()
  {
    return this.response;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     oauth.signpost.commonshttp.HttpResponseAdapter
 * JD-Core Version:    0.7.0.1
 */