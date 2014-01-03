package com.touchtype.keyboard.theme.util;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.ColorFilter;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public abstract interface ColorFilterContainer
{
  public static final ColorFilterContainer NULL_FILTER_CONTAINER = new EmptyColorFilterContainer();
  
  public abstract ColorFilter getColorFilter(int[] paramArrayOfInt);
  
  public static final class BasicColorFilterContainer
    implements ColorFilterContainer
  {
    private final ColorFilter mColorFilter;
    
    public BasicColorFilterContainer(ColorFilter paramColorFilter)
    {
      this.mColorFilter = paramColorFilter;
    }
    
    public ColorFilter getColorFilter(int[] paramArrayOfInt)
    {
      return this.mColorFilter;
    }
  }
  
  public static final class EmptyColorFilterContainer
    implements ColorFilterContainer
  {
    public ColorFilter getColorFilter(int[] paramArrayOfInt)
    {
      return null;
    }
  }
  
  public static final class StateListColorFilter
    implements ColorFilterContainer
  {
    private final Map<int[], ColorFilter> mFilters;
    
    private StateListColorFilter(Map<int[], ColorFilter> paramMap)
    {
      this.mFilters = paramMap;
    }
    
    public static StateListColorFilter createStateListColorFilter(Resources paramResources, int paramInt)
    {
      XmlResourceParser localXmlResourceParser = paramResources.getXml(paramInt);
      HashMap localHashMap = new HashMap();
      for (;;)
      {
        int k;
        int i1;
        try
        {
          int i = localXmlResourceParser.next();
          if (i != 1)
          {
            String str1 = localXmlResourceParser.getName();
            switch (i)
            {
            case 2: 
              if (!"Filter".equals(str1)) {
                continue;
              }
              AttributeSet localAttributeSet = Xml.asAttributeSet(localXmlResourceParser);
              int j = localAttributeSet.getAttributeCount();
              arrayOfInt = new int[j];
              str2 = null;
              k = 0;
              m = 0;
              if (k < j)
              {
                n = localAttributeSet.getAttributeNameResource(k);
                if (localAttributeSet.getAttributeName(k).equals("filterString"))
                {
                  str2 = localAttributeSet.getAttributeValue(k);
                  i1 = m;
                }
                else if (n != 0)
                {
                  i1 = m + 1;
                  if (localAttributeSet.getAttributeBooleanValue(k, false)) {
                    arrayOfInt[m] = n;
                  }
                }
              }
              break;
            }
          }
        }
        catch (XmlPullParserException localXmlPullParserException)
        {
          int[] arrayOfInt;
          String str2;
          LogUtil.w("TEST", "XmlPullParserException");
          return new StateListColorFilter(localHashMap);
          int n = -n;
          continue;
          localHashMap.put(StateSet.trimStateSet(arrayOfInt, m), ColorFilterUtil.parseColorFilter(str2));
          continue;
        }
        catch (IOException localIOException)
        {
          LogUtil.w("TEST", "IOException");
          continue;
          i1 = m;
        }
        continue;
        k++;
        int m = i1;
      }
    }
    
    public ColorFilter getColorFilter(int[] paramArrayOfInt)
    {
      Iterator localIterator = this.mFilters.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (StateSet.stateSetMatches((int[])localEntry.getKey(), paramArrayOfInt)) {
          return (ColorFilter)localEntry.getValue();
        }
      }
      return null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.ColorFilterContainer
 * JD-Core Version:    0.7.0.1
 */