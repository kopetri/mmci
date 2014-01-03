package com.facebook;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InsightsLogger
{
  private static final String EVENT_NAME_LOG_CONVERSION_PIXEL = "fb_log_offsite_pixel";
  private static final String EVENT_NAME_LOG_MOBILE_PURCHASE = "fb_mobile_purchase";
  private static final String EVENT_PARAMETER_CURRENCY = "fb_currency";
  private static final String EVENT_PARAMETER_PIXEL_ID = "fb_offsite_pixel_id";
  private static final String EVENT_PARAMETER_PIXEL_VALUE = "fb_offsite_pixel_value";
  private static Session appAuthSession = null;
  private final String applicationId;
  private final String clientToken;
  private final Context context;
  private final Session specifiedSession;
  
  private InsightsLogger(Context paramContext, String paramString1, String paramString2, Session paramSession)
  {
    Validate.notNull(paramContext, "context");
    Validate.notNullOrEmpty(paramString1, "clientToken");
    if (paramString2 == null) {
      paramString2 = Utility.getMetadataApplicationId(paramContext);
    }
    this.context = paramContext;
    this.clientToken = paramString1;
    this.applicationId = paramString2;
    this.specifiedSession = paramSession;
  }
  
  private static String buildJSONForEvent(String paramString, double paramDouble, Bundle paramBundle)
  {
    JSONObject localJSONObject;
    try
    {
      localJSONObject = new JSONObject();
      localJSONObject.put("_eventName", paramString);
      if (paramDouble != 1.0D) {
        localJSONObject.put("_valueToSum", paramDouble);
      }
      if (paramBundle != null)
      {
        Iterator localIterator = paramBundle.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          Object localObject = paramBundle.get(str2);
          if ((!(localObject instanceof String)) && (!(localObject instanceof Number))) {
            notifyDeveloperError(String.format("Parameter '%s' must be a string or a numeric type.", new Object[] { str2 }));
          }
          localJSONObject.put(str2, localObject);
        }
      }
      localJSONArray = new JSONArray();
    }
    catch (JSONException localJSONException)
    {
      notifyDeveloperError(localJSONException.toString());
      return null;
    }
    JSONArray localJSONArray;
    localJSONArray.put(localJSONObject);
    String str1 = localJSONArray.toString();
    return str1;
  }
  
  private void logEventNow(final String paramString, final double paramDouble, Bundle paramBundle)
  {
    Settings.getExecutor().execute(new Runnable()
    {
      public void run()
      {
        String str1 = InsightsLogger.buildJSONForEvent(paramString, paramDouble, this.val$parameters);
        if (str1 == null) {}
        for (;;)
        {
          return;
          GraphObject localGraphObject = GraphObject.Factory.create();
          localGraphObject.setProperty("event", "CUSTOM_APP_EVENTS");
          localGraphObject.setProperty("custom_events", str1);
          if (Utility.queryAppAttributionSupportAndWait(InsightsLogger.this.applicationId))
          {
            String str3 = Settings.getAttributionId(InsightsLogger.this.context.getContentResolver());
            if (str3 != null) {
              localGraphObject.setProperty("attribution", str3);
            }
          }
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = InsightsLogger.this.applicationId;
          String str2 = String.format("%s/activities", arrayOfObject1);
          try
          {
            Response localResponse = Request.newPostRequest(InsightsLogger.this.sessionToLogTo(), str2, localGraphObject, null).executeAndWait();
            if ((localResponse.getError() != null) && (localResponse.getError().getErrorCode() != -1))
            {
              Object[] arrayOfObject2 = new Object[3];
              arrayOfObject2[0] = str1;
              arrayOfObject2[1] = localResponse.toString();
              arrayOfObject2[2] = localResponse.getError().toString();
              InsightsLogger.notifyDeveloperError(String.format("Error publishing Insights event '%s'\n  Response: %s\n  Error: %s", arrayOfObject2));
              return;
            }
          }
          catch (Exception localException)
          {
            Utility.logd("Insights-exception: ", localException);
          }
        }
      }
    });
  }
  
  public static InsightsLogger newLogger(Context paramContext, String paramString)
  {
    return new InsightsLogger(paramContext, paramString, null, null);
  }
  
  public static InsightsLogger newLogger(Context paramContext, String paramString1, String paramString2)
  {
    return new InsightsLogger(paramContext, paramString1, paramString2, null);
  }
  
  public static InsightsLogger newLogger(Context paramContext, String paramString1, String paramString2, Session paramSession)
  {
    return new InsightsLogger(paramContext, paramString1, paramString2, paramSession);
  }
  
  private static void notifyDeveloperError(String paramString)
  {
    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "Insights", paramString);
  }
  
  private Session sessionToLogTo()
  {
    try
    {
      Session localSession1 = this.specifiedSession;
      if ((localSession1 == null) || (!localSession1.isOpened())) {
        localSession1 = Session.getActiveSession();
      }
      if ((localSession1 == null) || (!localSession1.isOpened()) || (localSession1.getAccessToken() == null))
      {
        if (appAuthSession == null)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = this.applicationId;
          arrayOfObject[1] = this.clientToken;
          AccessToken localAccessToken = AccessToken.createFromString(String.format("%s|%s", arrayOfObject), null, AccessTokenSource.CLIENT_TOKEN);
          Session localSession2 = new Session(null, this.applicationId, new NonCachingTokenCachingStrategy(), false);
          appAuthSession = localSession2;
          localSession2.open(localAccessToken, null);
        }
        localSession1 = appAuthSession;
      }
      return localSession1;
    }
    finally {}
  }
  
  public void logConversionPixel(String paramString, double paramDouble)
  {
    if (paramString == null)
    {
      notifyDeveloperError("pixelID cannot be null");
      return;
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("fb_offsite_pixel_id", paramString);
    localBundle.putDouble("fb_offsite_pixel_value", paramDouble);
    logEventNow("fb_log_offsite_pixel", paramDouble, localBundle);
  }
  
  public void logPurchase(BigDecimal paramBigDecimal, Currency paramCurrency)
  {
    logPurchase(paramBigDecimal, paramCurrency, null);
  }
  
  public void logPurchase(BigDecimal paramBigDecimal, Currency paramCurrency, Bundle paramBundle)
  {
    if (paramBigDecimal == null)
    {
      notifyDeveloperError("purchaseAmount cannot be null");
      return;
    }
    if (paramCurrency == null)
    {
      notifyDeveloperError("currency cannot be null");
      return;
    }
    if (paramBundle == null) {
      paramBundle = new Bundle();
    }
    paramBundle.putString("fb_currency", paramCurrency.getCurrencyCode());
    logEventNow("fb_mobile_purchase", paramBigDecimal.doubleValue(), paramBundle);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.InsightsLogger
 * JD-Core Version:    0.7.0.1
 */