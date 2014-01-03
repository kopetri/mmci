package com.touchtype;

public abstract class AbstractStatsScheduledJob
  extends AbstractScheduledJob
{
  private static final String TAG = AbstractStatsScheduledJob.class.getSimpleName();
  protected boolean mShouldBeRepeated = true;
  protected boolean mShouldGenerateStatsReport = true;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.AbstractStatsScheduledJob
 * JD-Core Version:    0.7.0.1
 */