package org.spongycastle.util.test;

public final class NumberParsing
{
  public static int decodeIntFromHex(String paramString)
  {
    if ((paramString.charAt(1) == 'x') || (paramString.charAt(1) == 'X')) {
      return Integer.parseInt(paramString.substring(2), 16);
    }
    return Integer.parseInt(paramString, 16);
  }
  
  public static long decodeLongFromHex(String paramString)
  {
    if ((paramString.charAt(1) == 'x') || (paramString.charAt(1) == 'X')) {
      return Long.parseLong(paramString.substring(2), 16);
    }
    return Long.parseLong(paramString, 16);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.test.NumberParsing
 * JD-Core Version:    0.7.0.1
 */