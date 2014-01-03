package com.touchtype;

public abstract interface AsyncTaskListener<T>
{
  public abstract void onCancelledTask(String paramString, T paramT);
  
  public abstract void onPostExecuteTask(String paramString, T paramT);
  
  public abstract void onPreExecuteTask(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.AsyncTaskListener
 * JD-Core Version:    0.7.0.1
 */