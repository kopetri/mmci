package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class az
{
  public static void a(StringBuilder paramStringBuilder, HashMap<String, String> paramHashMap)
  {
    paramStringBuilder.append("{");
    Iterator localIterator = paramHashMap.keySet().iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      if (i == 0) {
        paramStringBuilder.append(",");
      }
      String str2;
      for (int j = i;; j = 0)
      {
        str2 = (String)paramHashMap.get(str1);
        paramStringBuilder.append("\"").append(str1).append("\":");
        if (str2 != null) {
          break label109;
        }
        paramStringBuilder.append("null");
        i = j;
        break;
      }
      label109:
      paramStringBuilder.append("\"").append(str2).append("\"");
      i = j;
    }
    paramStringBuilder.append("}");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.az
 * JD-Core Version:    0.7.0.1
 */