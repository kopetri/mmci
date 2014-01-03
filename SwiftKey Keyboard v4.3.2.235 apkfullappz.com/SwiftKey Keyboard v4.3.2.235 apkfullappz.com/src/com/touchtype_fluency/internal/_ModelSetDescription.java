package com.touchtype_fluency.internal;

public class _ModelSetDescription
{
  static {}
  
  public static native void createStatic(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String[] paramArrayOfString, String paramString5, float paramFloat, Type paramType);
  
  private static native void initIDs();
  
  public static enum Type
  {
    static
    {
      CHINESE = new Type("CHINESE", 1);
      Type[] arrayOfType = new Type[2];
      arrayOfType[0] = NORMAL;
      arrayOfType[1] = CHINESE;
      $VALUES = arrayOfType;
    }
    
    private Type() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal._ModelSetDescription
 * JD-Core Version:    0.7.0.1
 */