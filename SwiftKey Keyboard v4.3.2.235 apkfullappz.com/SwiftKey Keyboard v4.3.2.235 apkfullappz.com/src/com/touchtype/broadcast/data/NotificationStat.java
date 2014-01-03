package com.touchtype.broadcast.data;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class NotificationStat
{
  public final Action action;
  public final String dateHandled = currentDate();
  public final int messageId;
  
  public NotificationStat(int paramInt, Action paramAction)
  {
    this.action = paramAction;
    this.messageId = paramInt;
  }
  
  private String currentDate()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.ENGLISH);
    localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return localSimpleDateFormat.format(new Date());
  }
  
  public String toString()
  {
    return new Gson().toJson(this);
  }
  
  public static enum Action
  {
    static
    {
      FOLLOWED = new Action("FOLLOWED", 3);
      PUBLICITY_REFUSED = new Action("PUBLICITY_REFUSED", 4);
      DISPLAYED = new Action("DISPLAYED", 5);
      Action[] arrayOfAction = new Action[6];
      arrayOfAction[0] = DISMISSED;
      arrayOfAction[1] = EXPIRED;
      arrayOfAction[2] = SPAM;
      arrayOfAction[3] = FOLLOWED;
      arrayOfAction[4] = PUBLICITY_REFUSED;
      arrayOfAction[5] = DISPLAYED;
      $VALUES = arrayOfAction;
    }
    
    private Action() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.data.NotificationStat
 * JD-Core Version:    0.7.0.1
 */