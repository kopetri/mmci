package com.touchtype.referral;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public final class GoogleReferrerInfoInserter
  implements ReferrerInfoInserter
{
  private ReferrerInfo info;
  private final Context mContext;
  
  public GoogleReferrerInfoInserter(Context paramContext, ReferrerInfo paramReferrerInfo)
  {
    this.info = paramReferrerInfo;
    this.mContext = paramContext;
  }
  
  public Uri insertInfoIntoUri(Uri paramUri, ReferralSource paramReferralSource)
  {
    return paramUri.buildUpon().appendQueryParameter("referrer", referrerParamValue(paramReferralSource)).build();
  }
  
  public String referrerParamValue(ReferralSource paramReferralSource)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("utm_source", this.info.source(this.mContext, paramReferralSource)));
    localArrayList.add(new BasicNameValuePair("utm_medium", this.info.medium()));
    localArrayList.add(new BasicNameValuePair("utm_campaign", this.info.campaign()));
    return URLEncodedUtils.format(localArrayList, "utf-8");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.GoogleReferrerInfoInserter
 * JD-Core Version:    0.7.0.1
 */