package com.facebook;

public enum SessionDefaultAudience
{
  private final String nativeProtocolAudience;
  
  static
  {
    FRIENDS = new SessionDefaultAudience("FRIENDS", 2, "ALL_FRIENDS");
    EVERYONE = new SessionDefaultAudience("EVERYONE", 3, "EVERYONE");
    SessionDefaultAudience[] arrayOfSessionDefaultAudience = new SessionDefaultAudience[4];
    arrayOfSessionDefaultAudience[0] = NONE;
    arrayOfSessionDefaultAudience[1] = ONLY_ME;
    arrayOfSessionDefaultAudience[2] = FRIENDS;
    arrayOfSessionDefaultAudience[3] = EVERYONE;
    $VALUES = arrayOfSessionDefaultAudience;
  }
  
  private SessionDefaultAudience(String paramString)
  {
    this.nativeProtocolAudience = paramString;
  }
  
  String getNativeProtocolAudience()
  {
    return this.nativeProtocolAudience;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.SessionDefaultAudience
 * JD-Core Version:    0.7.0.1
 */