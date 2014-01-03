package com.touchtype_fluency.service.personalize.tasks;

import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.http.SSLClientFactory;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;
import java.io.IOException;
import java.net.ProtocolException;
import javax.net.ssl.SSLException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

class DeleteRemoteTask
  extends PersonalizationTask
{
  private static final int DELETE_REQUEST_TIMEOUT = 10000;
  private static final int RESPONSE_CODE_FAILED = -1;
  private static final int RETRIES = 5;
  private final String mAuthParams;
  
  public DeleteRemoteTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, String paramString1, PersonalizationTaskListener paramPersonalizationTaskListener, String paramString2)
  {
    super(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, 5, paramString2);
    this.mAuthParams = paramString1;
  }
  
  public void compute()
    throws TaskFailException
  {
    String str = getLocation() + this.mAuthParams;
    try
    {
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      localBasicHttpParams.setParameter("http.connection.timeout", Integer.valueOf(10000));
      HttpDelete localHttpDelete = new HttpDelete(str);
      int i = SSLClientFactory.createHttpClient(localBasicHttpParams).execute(localHttpDelete).getStatusLine().getStatusCode();
      evaluateDeleteResponse(i);
      return;
    }
    catch (ProtocolException localProtocolException)
    {
      LogUtil.e(this.TAG, localProtocolException.getMessage(), localProtocolException);
      return;
    }
    catch (SSLException localSSLException)
    {
      LogUtil.e(this.TAG, "SSLException making delete personalization request: " + localSSLException.getMessage(), localSSLException);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(this.TAG, "IO Exception trying to DELETE " + str + ": " + localIOException.getMessage(), localIOException);
      return;
    }
    finally
    {
      evaluateDeleteResponse(-1);
    }
  }
  
  protected void evaluateDeleteResponse(int paramInt)
    throws TaskFailException
  {
    switch (paramInt)
    {
    default: 
      throw new TaskFailException("Did not receive a valid response to delete request", PersonalizationFailedReason.OTHER);
    case 202: 
      notifyListener(true, null);
      return;
    }
    LogUtil.w(this.TAG, "Delete request forbidden");
    notifyListener(false, PersonalizationFailedReason.OTHER);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.DeleteRemoteTask
 * JD-Core Version:    0.7.0.1
 */