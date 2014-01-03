package org.spongycastle.i18n.filter;

public class SQLFilter
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
      }
      for (;;)
      {
        i++;
        break;
        localStringBuffer.replace(i, i + 1, "\\'");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\\"");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\=");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\-");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\/");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\\\");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\;");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\r");
        i++;
        continue;
        localStringBuffer.replace(i, i + 1, "\\n");
        i++;
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
 * Qualified Name:     org.spongycastle.i18n.filter.SQLFilter
 * JD-Core Version:    0.7.0.1
 */