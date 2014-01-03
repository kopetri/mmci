package com.google.android.gms.common;

import android.app.PendingIntent;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.w.a;

public final class ConnectionResult
{
  public static final ConnectionResult B = new ConnectionResult(0, null);
  private final PendingIntent mPendingIntent;
  private final int p;
  
  public ConnectionResult(int paramInt, PendingIntent paramPendingIntent)
  {
    this.p = paramInt;
    this.mPendingIntent = paramPendingIntent;
  }
  
  private String e()
  {
    switch (this.p)
    {
    default: 
      return "unknown status code " + this.p;
    case 0: 
      return "SUCCESS";
    case 1: 
      return "SERVICE_MISSING";
    case 2: 
      return "SERVICE_VERSION_UPDATE_REQUIRED";
    case 3: 
      return "SERVICE_DISABLED";
    case 4: 
      return "SIGN_IN_REQUIRED";
    case 5: 
      return "INVALID_ACCOUNT";
    case 6: 
      return "RESOLUTION_REQUIRED";
    case 7: 
      return "NETWORK_ERROR";
    case 8: 
      return "INTERNAL_ERROR";
    case 9: 
      return "SERVICE_INVALID";
    case 10: 
      return "DEVELOPER_ERROR";
    }
    return "LICENSE_CHECK_FAILED";
  }
  
  public String toString()
  {
    return w.c(this).a("statusCode", e()).a("resolution", this.mPendingIntent).toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.ConnectionResult
 * JD-Core Version:    0.7.0.1
 */