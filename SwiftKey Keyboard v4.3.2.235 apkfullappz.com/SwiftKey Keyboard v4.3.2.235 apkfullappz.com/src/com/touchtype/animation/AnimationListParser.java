package com.touchtype.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Pair;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public final class AnimationListParser
{
  private int animationListResId;
  private Context context;
  
  public AnimationListParser(Context paramContext, int paramInt)
  {
    this.context = paramContext;
    this.animationListResId = paramInt;
  }
  
  public ParsedAnimationList parse()
  {
    localParsedAnimationList = new ParsedAnimationList();
    ArrayList localArrayList = new ArrayList();
    localParsedAnimationList.frames = localArrayList;
    XmlResourceParser localXmlResourceParser = this.context.getResources().getXml(this.animationListResId);
    try
    {
      int i = localXmlResourceParser.next();
      if (i != 1)
      {
        String str;
        if (i == 2)
        {
          str = localXmlResourceParser.getName();
          if (!str.equals("animation-list")) {
            break label100;
          }
          localParsedAnimationList.oneShot = localXmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "oneshot", false);
        }
        for (;;)
        {
          i = localXmlResourceParser.next();
          break;
          label100:
          if (str.equals("item"))
          {
            int j = localXmlResourceParser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawable", 0);
            int k = localXmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "duration", 0);
            localArrayList.add(new Pair(Integer.valueOf(j), Integer.valueOf(k)));
            localParsedAnimationList.duration = (k + localParsedAnimationList.duration);
          }
        }
      }
      return localParsedAnimationList;
    }
    catch (IOException localIOException)
    {
      LogUtil.e("AnimationListParser", "Failed to parsed animation list with resource ID [" + this.animationListResId + "] due to an IOException [" + localIOException.getMessage() + "]");
      return localParsedAnimationList;
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      LogUtil.e("AnimationListParser", "Failed to parsed animation list with resource ID [" + this.animationListResId + "] due to an XMLPullParserException [" + localXmlPullParserException.getMessage() + "]");
    }
  }
  
  static final class ParsedAnimationList
  {
    int duration = 0;
    List<Pair<Integer, Integer>> frames;
    boolean oneShot = false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.animation.AnimationListParser
 * JD-Core Version:    0.7.0.1
 */