package com.touchtype_fluency.service.personalize.tasks;

import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.http.SSLClientFactory;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;
import java.io.IOException;
import javax.net.ssl.SSLException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

class PollRequestTask
  extends PersonalizationTask
{
  private static final int POLL_LIMIT_EXCEEDED = -1;
  private static final int RETRIES = 5;
  private DownloadTask mNextTask;
  private int mPollDelayIndex = 0;
  
  public PollRequestTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, PersonalizationTaskListener paramPersonalizationTaskListener, DownloadTask paramDownloadTask)
  {
    super(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, 5);
    this.mNextTask = paramDownloadTask;
  }
  
  protected static int calculatePollDelay(int paramInt)
  {
    int[] arrayOfInt = { 2, 2, 2, 2, 4, 4, 4, 4, 8, 8, 8, 8, 16, 16, 16, 16, 30, 30, 30, 30, 60, 60, 60, 60, 120, 120, 120, 120, 240, 240, 240, 240 };
    if (paramInt < arrayOfInt.length) {
      return 1000 * arrayOfInt[paramInt];
    }
    return -1;
  }
  
  public void compute()
    throws TaskFailException
  {
    String str = getLocation();
    HttpClient localHttpClient = null;
    try
    {
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      localBasicHttpParams.setParameter("http.protocol.handle-redirects", Boolean.valueOf(false));
      HttpGet localHttpGet = new HttpGet(str);
      localHttpClient = SSLClientFactory.createHttpClient(localBasicHttpParams);
      HttpResponse localHttpResponse = localHttpClient.execute(localHttpGet);
      if (localHttpClient != null) {
        localHttpClient.getConnectionManager().shutdown();
      }
      evaluateHttpResponse(localHttpResponse);
      return;
    }
    catch (SSLException localSSLException)
    {
      LogUtil.e(this.TAG, "SSLException making Poll request: " + localSSLException.getMessage(), localSSLException);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.w(this.TAG, localIOException.getMessage(), localIOException);
      return;
    }
    finally
    {
      if (localHttpClient != null) {
        localHttpClient.getConnectionManager().shutdown();
      }
      evaluateHttpResponse(null);
    }
  }
  
  protected void evaluateHttpResponse(HttpResponse paramHttpResponse)
    throws TaskFailException
  {
    int i = -1;
    if (paramHttpResponse != null) {
      i = paramHttpResponse.getStatusLine().getStatusCode();
    }
    switch (i)
    {
    default: 
      throw new TaskFailException("Did not receive a valid response from Poll request", PersonalizationFailedReason.OTHER);
    case 301: 
      Header localHeader = paramHttpResponse.getLastHeader("Location");
      if (localHeader != null)
      {
        this.mNextTask.setLocation(localHeader.getValue());
        schedule(this.mNextTask, 0);
        return;
      }
      notifyListener(false, PersonalizationFailedReason.OTHER);
      return;
    case 409: 
      notifyListener(false, PersonalizationFailedReason.fromString(paramHttpResponse.getFirstHeader("X-Failure-Reason").getValue()));
      return;
    }
    int j = calculatePollDelay(this.mPollDelayIndex);
    if (j != -1)
    {
      this.mPollDelayIndex = (1 + this.mPollDelayIndex);
      schedule(this, j);
      return;
    }
    notifyListener(false, PersonalizationFailedReason.OTHER);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.PollRequestTask
 * JD-Core Version:    0.7.0.1
 */