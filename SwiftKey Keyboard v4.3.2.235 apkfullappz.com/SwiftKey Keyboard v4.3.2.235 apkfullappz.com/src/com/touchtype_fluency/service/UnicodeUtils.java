package com.touchtype_fluency.service;

public class UnicodeUtils
{
  public static int lengthOfCodePointBeforeIndex(CharSequence paramCharSequence, int paramInt)
  {
    int i = 1;
    if ((paramInt <= 0) || (paramInt > paramCharSequence.length())) {
      i = 0;
    }
    char c1;
    char c2;
    do
    {
      do
      {
        return i;
      } while (paramInt == i);
      c1 = paramCharSequence.charAt(paramInt - 2);
      c2 = paramCharSequence.charAt(paramInt - 1);
    } while ((!Character.isHighSurrogate(c1)) || (!Character.isLowSurrogate(c2)));
    return 2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.UnicodeUtils
 * JD-Core Version:    0.7.0.1
 */