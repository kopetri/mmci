package com.touchtype.common.iris.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.touchtype.util.LogUtil;

public final class IrisErrorResponse
{
  private static final String TAG = IrisErrorResponse.class.getSimpleName();
  @SerializedName("errors")
  private Errors mErrors;
  
  public static IrisErrorResponse createFromJson(String paramString)
  {
    try
    {
      localIrisErrorResponse = (IrisErrorResponse)new Gson().fromJson(paramString, IrisErrorResponse.class);
      if (localIrisErrorResponse == null) {
        localIrisErrorResponse = new IrisErrorResponse();
      }
      return localIrisErrorResponse;
    }
    catch (JsonSyntaxException localJsonSyntaxException)
    {
      for (;;)
      {
        LogUtil.e(TAG, localJsonSyntaxException.getMessage(), localJsonSyntaxException);
        IrisErrorResponse localIrisErrorResponse = null;
      }
    }
  }
  
  public long getSuggestedRetry()
  {
    if (this.mErrors == null) {
      return 3600000L;
    }
    return 1000L * this.mErrors.suggestedRetry;
  }
  
  private static final class Errors
  {
    @SerializedName("suggested_retry")
    private long suggestedRetry;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.json.IrisErrorResponse
 * JD-Core Version:    0.7.0.1
 */