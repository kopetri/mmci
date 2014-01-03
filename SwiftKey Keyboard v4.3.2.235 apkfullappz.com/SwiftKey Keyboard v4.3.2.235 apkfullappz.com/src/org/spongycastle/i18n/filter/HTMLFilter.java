package org.spongycastle.i18n.filter;

public class HTMLFilter
  implements Filter
{
  public String doFilter(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    int i = 0;
    if (i < localStringBuffer.length())
    {
      switch (localStringBuffer.charAt(i))
      {
      case '$': 
      case '*': 
      case ',': 
      case '.': 
      case '/': 
      case '0': 
      case '1': 
      case '2': 
      case '3': 
      case '4': 
      case '5': 
      case '6': 
      case '7': 
      case '8': 
      case '9': 
      case ':': 
      case '=': 
      default: 
        i -= 3;
      }
      for (;;)
      {
        i += 4;
        break;
        localStringBuffer.replace(i, i + 1, "&#60");
        continue;
        localStringBuffer.replace(i, i + 1, "&#62");
        continue;
        localStringBuffer.replace(i, i + 1, "&#40");
        continue;
        localStringBuffer.replace(i, i + 1, "&#41");
        continue;
        localStringBuffer.replace(i, i + 1, "&#35");
        continue;
        localStringBuffer.replace(i, i + 1, "&#38");
        continue;
        localStringBuffer.replace(i, i + 1, "&#34");
        continue;
        localStringBuffer.replace(i, i + 1, "&#39");
        continue;
        localStringBuffer.replace(i, i + 1, "&#37");
        continue;
        localStringBuffer.replace(i, i + 1, "&#59");
        continue;
        localStringBuffer.replace(i, i + 1, "&#43");
        continue;
        localStringBuffer.replace(i, i + 1, "&#45");
      }
    }
    return localStringBuffer.toString();
  }
  
  public String doFilterUrl(String paramString)
  {
    return doFilter(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.i18n.filter.HTMLFilter
 * JD-Core Version:    0.7.0.1
 */