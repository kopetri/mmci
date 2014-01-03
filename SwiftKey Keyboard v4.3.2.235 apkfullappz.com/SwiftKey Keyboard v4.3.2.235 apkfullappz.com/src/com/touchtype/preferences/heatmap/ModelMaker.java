package com.touchtype.preferences.heatmap;

public abstract interface ModelMaker
{
  public abstract void attach(ModelReceiver paramModelReceiver);
  
  public abstract void detach();
  
  public abstract int getProgress();
  
  public abstract boolean isDone();
  
  public abstract void stop(boolean paramBoolean);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.heatmap.ModelMaker
 * JD-Core Version:    0.7.0.1
 */