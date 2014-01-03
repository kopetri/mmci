package com.touchtype.keyboard.theme.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Xml;
import com.touchtype.keyboard.theme.KeyStyle;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ThemeLoader
{
  private static final String TAG = ThemeLoader.class.getSimpleName();
  
  public static Theme loadThemeHandler(Context paramContext, File paramFile1, File paramFile2)
    throws ThemeLoader.ThemeLoaderException
  {
    for (;;)
    {
      Resources localResources;
      String str1;
      String str2;
      XmlPullParser localXmlPullParser;
      int k;
      String str3;
      try
      {
        localFileInputStream = new FileInputStream(paramFile1);
        localResources = paramContext.getResources();
        str1 = null;
        str2 = null;
        localXmlPullParser = Xml.newPullParser();
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        FileInputStream localFileInputStream;
        int i;
        int j;
        int n;
        throw new ThemeLoaderException();
      }
      for (;;)
      {
        try
        {
          localXmlPullParser.setInput(localFileInputStream, null);
          i = localXmlPullParser.getEventType();
          j = i;
          k = 0;
          m = 0;
          localObject1 = null;
          localObject2 = null;
          localObject3 = null;
          localObject4 = null;
          localObject5 = null;
          if ((j == 1) || (k != 0)) {
            continue;
          }
        }
        catch (DrawableLoader.DrawableLoaderException localDrawableLoaderException2)
        {
          String str4;
          KeyStyle localKeyStyle;
          Map localMap;
          KeyStyle.StyleId localStyleId1;
          KeyStyle.StyleId localStyleId2;
          continue;
        }
        catch (XmlPullParserException localXmlPullParserException2)
        {
          continue;
        }
        catch (IOException localIOException2)
        {
          continue;
          localObject7 = localObject1;
          localObject8 = localObject2;
          localObject9 = localObject3;
          localObject10 = localObject4;
          localObject6 = localObject5;
          m = 0;
          break;
        }
        try
        {
          str3 = localXmlPullParser.getName();
          switch (j)
          {
          case 1: 
          default: 
            localObject7 = localObject1;
            localObject8 = localObject2;
            localObject9 = localObject3;
            localObject10 = localObject4;
            localObject6 = localObject5;
          }
        }
        catch (IOException localIOException1)
        {
          for (;;)
          {
            LogUtil.e(TAG, localIOException1.getMessage(), localIOException1);
            throw new ThemeLoaderException();
            try
            {
              localObject10 = new ThemeProperties(localResources, paramFile2, localXmlPullParser);
              localObject7 = localObject1;
              localObject8 = localObject2;
              localObject9 = localObject3;
              localObject6 = localObject5;
            }
            catch (DrawableLoader.DrawableLoaderException localDrawableLoaderException3)
            {
              LogUtil.e(TAG, localDrawableLoaderException3.getMessage(), localDrawableLoaderException3);
              localObject7 = localObject1;
              localObject8 = localObject2;
              localObject9 = localObject3;
              localObject10 = localObject4;
              localObject6 = localObject5;
            }
          }
          break;
          if (!str3.equalsIgnoreCase("STYLES")) {
            continue;
          }
          if ((localObject4 != null) && (localObject3 == null)) {
            continue;
          }
          throw new XmlPullParserException("Invalid Theme file");
        }
        catch (XmlPullParserException localXmlPullParserException1)
        {
          LogUtil.e(TAG, localXmlPullParserException1.getMessage(), localXmlPullParserException1);
          throw new ThemeLoaderException();
          localObject9 = new HashMap();
          m = 1;
          localObject7 = localObject1;
          localObject8 = localObject2;
          localObject10 = localObject4;
          localObject6 = localObject5;
          break;
          if (!str3.equalsIgnoreCase("STYLE")) {
            continue;
          }
          if ((m != 0) && (localObject3 != null) && (localObject2 == null)) {
            continue;
          }
          throw new XmlPullParserException("Invalid Theme file");
        }
        catch (DrawableLoader.DrawableLoaderException localDrawableLoaderException1) {}
      }
      n = localXmlPullParser.next();
      j = n;
      localObject1 = localObject7;
      localObject2 = localObject8;
      localObject3 = localObject9;
      localObject4 = localObject10;
      localObject5 = localObject6;
      continue;
      localObject7 = localObject1;
      localObject8 = localObject2;
      localObject9 = localObject3;
      localObject10 = localObject4;
      localObject6 = localObject5;
      continue;
      if (str3.equalsIgnoreCase("THEME")) {
        if (localObject4 != null) {
          throw new XmlPullParserException("Invalid Theme file");
        }
      }
      LogUtil.e(TAG, localDrawableLoaderException1.getMessage(), localDrawableLoaderException1);
      throw new ThemeLoaderException();
      str4 = localXmlPullParser.getAttributeValue(localXmlPullParser.getNamespace(), "parent");
      localKeyStyle = (KeyStyle)localObject3.get(str4);
      if (str4 != null) {}
      try
      {
        localObject8 = new KeyStyle(localResources, paramFile2, localXmlPullParser, localKeyStyle);
        localObject7 = localObject1;
        localObject9 = localObject3;
        localObject10 = localObject4;
        localObject6 = localObject5;
      }
      catch (DrawableLoader.DrawableLoaderException localDrawableLoaderException4)
      {
        LogUtil.e(TAG, localDrawableLoaderException4.getMessage(), localDrawableLoaderException4);
        localObject7 = localObject1;
        localObject8 = localObject2;
        localObject9 = localObject3;
        localObject10 = localObject4;
        localObject6 = localObject5;
      }
      localObject8 = new KeyStyle(localResources, paramFile2, localXmlPullParser);
      localObject7 = localObject1;
      localObject9 = localObject3;
      localObject10 = localObject4;
      localObject6 = localObject5;
      continue;
      continue;
      if (str3.equalsIgnoreCase("ICONS"))
      {
        localObject7 = new HashMap();
        localObject8 = localObject2;
        localObject9 = localObject3;
        localObject10 = localObject4;
        localObject6 = localObject5;
      }
      else if (str3.equalsIgnoreCase("ICON"))
      {
        if (localObject1 == null) {
          throw new XmlPullParserException("Invalid Theme file");
        }
        str1 = localXmlPullParser.getAttributeValue(localXmlPullParser.getNamespace(), "key");
        localObject7 = localObject1;
        localObject8 = localObject2;
        localObject9 = localObject3;
        localObject10 = localObject4;
        localObject6 = localObject5;
        continue;
        if (str1 != null)
        {
          str2 = localXmlPullParser.getText();
          localObject7 = localObject1;
          localObject8 = localObject2;
          localObject9 = localObject3;
          localObject10 = localObject4;
          localObject6 = localObject5;
          continue;
          if (str3.equalsIgnoreCase("THEME"))
          {
            if ((localObject4 == null) || (localObject3 == null) || (localObject1 == null)) {
              throw new XmlPullParserException("Invalid Theme file");
            }
            localMap = DrawableLoader.loadIcons(localResources, paramFile2, localObject1);
            localObject6 = new Theme(localObject4, localObject3, localMap, paramContext);
            k = 1;
            localObject7 = localObject1;
            localObject8 = localObject2;
            localObject9 = localObject3;
            localObject10 = localObject4;
          }
          else
          {
            if (str3.equalsIgnoreCase("STYLES"))
            {
              if ((localObject2 == null) && (verifyKeyStyles(localObject3))) {
                break label1121;
              }
              throw new XmlPullParserException("Invalid Theme file");
            }
            if (str3.equalsIgnoreCase("STYLE"))
            {
              if ((localObject2 != null) && (localObject3 != null))
              {
                localStyleId1 = localObject2.mId;
                if (!localObject3.containsKey(localStyleId1)) {}
              }
              else
              {
                throw new XmlPullParserException("Invalid Theme file");
              }
              localStyleId2 = localObject2.mId;
              localObject3.put(localStyleId2, localObject2);
              localObject7 = localObject1;
              localObject9 = localObject3;
              localObject10 = localObject4;
              localObject6 = localObject5;
              localObject8 = null;
            }
            else if ((!str3.equalsIgnoreCase("ICONS")) && (str3.equalsIgnoreCase("ICON")))
            {
              if ((str1 == null) || (str2 == null)) {
                throw new XmlPullParserException("Invalid Theme file");
              }
              localObject1.put(str1, str2);
              localObject7 = localObject1;
              localObject8 = localObject2;
              localObject9 = localObject3;
              localObject10 = localObject4;
              localObject6 = localObject5;
              str1 = null;
              str2 = null;
            }
          }
        }
      }
    }
    if (localObject5 == null) {
      throw new ThemeLoaderException();
    }
    return localObject5;
  }
  
  public static Map<String, String> parseIconSet(File paramFile)
    throws FileNotFoundException, XmlPullParserException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    Object localObject1 = null;
    XmlPullParser localXmlPullParser = Xml.newPullParser();
    for (;;)
    {
      try
      {
        localXmlPullParser.setInput(localFileInputStream, null);
        i = 0;
        int j = localXmlPullParser.getEventType();
        k = j;
        str1 = null;
        localObject2 = null;
        if ((i != 0) || (k == 1)) {
          continue;
        }
      }
      catch (IOException localIOException2)
      {
        int i;
        int k;
        String str1;
        Object localObject2;
        String str2;
        continue;
      }
      catch (XmlPullParserException localXmlPullParserException2)
      {
        continue;
      }
      try
      {
        str2 = localXmlPullParser.getName();
        switch (k)
        {
        }
      }
      catch (XmlPullParserException localXmlPullParserException1)
      {
        localObject1 = localObject2;
        LogUtil.e(TAG, localXmlPullParserException1.getMessage(), localXmlPullParserException1);
        if (verifyIconSet((Map)localObject1)) {
          continue;
        }
        throw new XmlPullParserException("IconSet is missing required icons");
        if (str1 == null) {
          continue;
        }
        localObject2.put(str1, localXmlPullParser.getText());
        localObject1 = localObject2;
        str1 = null;
        continue;
        if (!str2.equalsIgnoreCase("iconset")) {
          continue;
        }
        i = 1;
        localObject1 = localObject2;
        continue;
        if (str2.equalsIgnoreCase("icon")) {
          continue;
        }
        throw new XmlPullParserException("Invalid IconSet");
      }
      catch (IOException localIOException1)
      {
        localObject1 = localObject2;
      }
    }
    localObject1 = localObject2;
    for (;;)
    {
      int m = localXmlPullParser.next();
      k = m;
      localObject2 = localObject1;
      break;
      if (str2.equalsIgnoreCase("iconset"))
      {
        localObject1 = new HashMap();
      }
      else
      {
        if (!str2.equalsIgnoreCase("icon")) {
          break label166;
        }
        str1 = localXmlPullParser.getAttributeValue(localXmlPullParser.getNamespace(), "key");
        localObject1 = localObject2;
      }
    }
    label166:
    throw new XmlPullParserException("Invalid IconSet");
    for (;;)
    {
      LogUtil.e(TAG, localIOException1.getMessage(), localIOException1);
      continue;
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  private static boolean verifyIconSet(Map<String, String> paramMap)
  {
    return true;
  }
  
  private static boolean verifyKeyStyles(Map<KeyStyle.StyleId, KeyStyle> paramMap)
  {
    return true;
  }
  
  public static final class ThemeLoaderException
    extends Exception
  {
    public ThemeLoaderException() {}
    
    public ThemeLoaderException(String paramString)
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.ThemeLoader
 * JD-Core Version:    0.7.0.1
 */