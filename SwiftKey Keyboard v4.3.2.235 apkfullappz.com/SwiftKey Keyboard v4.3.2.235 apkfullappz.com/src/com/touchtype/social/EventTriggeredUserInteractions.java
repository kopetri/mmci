package com.touchtype.social;

import android.content.Context;
import android.os.Handler;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;

public class EventTriggeredUserInteractions
{
  private static final String TAG = EventTriggeredUserInteractions.class.getSimpleName();
  public static String USER_EVENT_KEY = "user_event_key";
  protected boolean mAllowInteraction;
  protected Context mContext;
  private final FluencyServiceProxy mFluencyServiceProxy = new FluencyServiceProxy()
  {
    protected void onServiceConnected()
    {
      EventTriggeredUserInteractions.this.mUserNotificationManager = EventTriggeredUserInteractions.this.mFluencyServiceProxy.getUserNotificationManager();
    }
  };
  protected Handler mHandler;
  protected TouchTypePreferences mTouchTypePreferences;
  protected UserNotificationManager mUserNotificationManager;
  
  public EventTriggeredUserInteractions(Context paramContext)
  {
    this.mContext = paramContext;
    this.mHandler = new Handler();
    this.mTouchTypePreferences = TouchTypePreferences.getInstance(this.mContext);
    boolean bool1 = this.mTouchTypePreferences.getBoolean("do_not_bother_me", false);
    boolean bool2 = false;
    if (!bool1) {
      bool2 = true;
    }
    this.mAllowInteraction = bool2;
    this.mFluencyServiceProxy.onCreate(this.mContext);
  }
  
  public void createRunner(UserEventNotificationCreator paramUserEventNotificationCreator)
  {
    if ((this.mTouchTypePreferences.getEventTriggered(paramUserEventNotificationCreator.getEventTriggeredName()) == -1) && (this.mAllowInteraction))
    {
      EventRunner localEventRunner = new EventRunner(paramUserEventNotificationCreator);
      this.mHandler.post(localEventRunner);
    }
  }
  
  private final class EventRunner
    implements Runnable
  {
    private int mRetryCount = 0;
    private UserEventNotificationCreator notifClass;
    
    public EventRunner(UserEventNotificationCreator paramUserEventNotificationCreator)
    {
      this.notifClass = paramUserEventNotificationCreator;
    }
    
    public void run()
    {
      if (EventTriggeredUserInteractions.this.mUserNotificationManager == null) {
        if (this.mRetryCount < 5)
        {
          this.mRetryCount = (1 + this.mRetryCount);
          EventTriggeredUserInteractions.this.mHandler.postDelayed(this, 1000L);
        }
      }
      do
      {
        return;
        LogUtil.w(EventTriggeredUserInteractions.TAG, "Couldn't get notification manager, can't do notification");
        return;
        EventTriggeredUserInteractions.this.mTouchTypePreferences.setEventTriggered(this.notifClass.getEventTriggeredName(), 0);
      } while ((!EventTriggeredUserInteractions.this.mAllowInteraction) || (EventTriggeredUserInteractions.this.mUserNotificationManager == null));
      EventTriggeredUserInteractions.this.mTouchTypePreferences.setEventTriggered(this.notifClass.getEventTriggeredName(), 1);
      EventTriggeredUserInteractions.this.mUserNotificationManager.displayUserEventNotification(this.notifClass);
    }
  }
  
  public static enum UserEvent
  {
    static
    {
      UserEvent[] arrayOfUserEvent = new UserEvent[1];
      arrayOfUserEvent[0] = KEYSTROKE_SAVING_EVENT;
      $VALUES = arrayOfUserEvent;
    }
    
    private UserEvent() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.EventTriggeredUserInteractions
 * JD-Core Version:    0.7.0.1
 */