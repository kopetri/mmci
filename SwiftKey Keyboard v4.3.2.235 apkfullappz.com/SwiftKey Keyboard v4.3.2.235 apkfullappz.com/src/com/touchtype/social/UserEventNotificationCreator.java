package com.touchtype.social;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;

public abstract interface UserEventNotificationCreator
{
  public abstract Notification createNotification(Context paramContext);
  
  public abstract String getEventTriggeredKey(Resources paramResources);
  
  public abstract String getEventTriggeredName();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.UserEventNotificationCreator
 * JD-Core Version:    0.7.0.1
 */