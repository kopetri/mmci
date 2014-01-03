package com.touchtype.keyboard.theme.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.util.Xml;
import com.google.common.io.Closeables;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableLoader
{
  private static Type getDrawableType(File paramFile)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    String str = getFirstTagName(new FileInputStream(paramFile));
    if (str.equals("selector")) {
      return Type.STATELIST;
    }
    if (str.equals("shape")) {
      return Type.GRADIENT;
    }
    return Type.INVALID;
  }
  
  private static Ext getExtension(String paramString)
  {
    String[] arrayOfString = paramString.split("\\.");
    if (arrayOfString.length < 2) {
      return Ext.INVALID;
    }
    String str = arrayOfString[(-1 + arrayOfString.length)];
    if (str.equals("xml")) {
      return Ext.XML;
    }
    if (str.equals("png"))
    {
      if (arrayOfString[(-2 + arrayOfString.length)].equals("9")) {
        return Ext.NINE;
      }
      return Ext.PNG;
    }
    return Ext.INVALID;
  }
  
  private static String getFirstTagName(InputStream paramInputStream)
    throws DrawableLoader.DrawableLoaderException
  {
    XmlPullParser localXmlPullParser = Xml.newPullParser();
    try
    {
      localXmlPullParser.setInput(paramInputStream, null);
      for (int i = localXmlPullParser.getEventType(); i != 1; i = localXmlPullParser.next()) {
        if (i == 2) {
          return localXmlPullParser.getName();
        }
      }
      throw new DrawableLoaderException();
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      throw new DrawableLoaderException(localXmlPullParserException);
    }
    catch (IOException localIOException)
    {
      throw new DrawableLoaderException(localIOException);
    }
  }
  
  private static Integer getGradientType(String paramString)
  {
    if (paramString == null) {
      return Integer.valueOf(0);
    }
    if (paramString.equals("radial")) {
      return Integer.valueOf(1);
    }
    if (paramString.equals("sweep")) {
      return Integer.valueOf(2);
    }
    return Integer.valueOf(0);
  }
  
  private static Integer getShape(String paramString)
  {
    if (paramString == null) {
      return Integer.valueOf(0);
    }
    if (paramString.equals("oval")) {
      return Integer.valueOf(1);
    }
    if (paramString.equals("line")) {
      return Integer.valueOf(2);
    }
    if (paramString.equals("ring")) {
      return Integer.valueOf(3);
    }
    return Integer.valueOf(0);
  }
  
  private static Integer getState(String paramString)
  {
    if (paramString.equals("drawable")) {
      return Integer.valueOf(16843161);
    }
    if (paramString.equals("state_activated")) {
      return Integer.valueOf(16843518);
    }
    if (paramString.equals("state_active")) {
      return Integer.valueOf(16842914);
    }
    if (paramString.equals("state_enabled")) {
      return Integer.valueOf(16842910);
    }
    if (paramString.equals("state_first")) {
      return Integer.valueOf(16842916);
    }
    if (paramString.equals("state_focused")) {
      return Integer.valueOf(16842908);
    }
    if (paramString.equals("state_last")) {
      return Integer.valueOf(16842918);
    }
    if (paramString.equals("state_middle")) {
      return Integer.valueOf(16842917);
    }
    if (paramString.equals("state_pressed")) {
      return Integer.valueOf(16842919);
    }
    if (paramString.equals("state_selected")) {
      return Integer.valueOf(16842913);
    }
    if (paramString.equals("state_single")) {
      return Integer.valueOf(16842915);
    }
    if (paramString.equals("state_window_focused")) {
      return Integer.valueOf(16842909);
    }
    LogUtil.w("DrawableLoader", "Unknown state value");
    return null;
  }
  
  private static List<StatePair> getStates(InputStream paramInputStream)
    throws DrawableLoader.DrawableLoaderException
  {
    XmlPullParser localXmlPullParser = Xml.newPullParser();
    for (;;)
    {
      try
      {
        localXmlPullParser.setInput(paramInputStream, null);
        i = localXmlPullParser.getEventType();
        localObject1 = null;
      }
      catch (IOException localIOException2)
      {
        int j;
        continue;
      }
      catch (XmlPullParserException localXmlPullParserException2)
      {
        int i;
        Object localObject1;
        int m;
        int i2;
        continue;
        if (i == 1) {
          continue;
        }
        switch (i)
        {
        }
        Object localObject2 = localObject1;
        continue;
        m++;
        int n = i2;
        continue;
      }
      j = localXmlPullParser.next();
      i = j;
      localObject1 = localObject2;
      continue;
      localObject2 = localObject1;
      continue;
      try
      {
        String str1 = localXmlPullParser.getName();
        if (str1.equalsIgnoreCase("SELECTOR"))
        {
          localObject2 = new ArrayList();
        }
        else
        {
          if ((localObject1 == null) || (!str1.equalsIgnoreCase("ITEM"))) {
            continue;
          }
          str2 = "";
          int k = localXmlPullParser.getAttributeCount();
          arrayOfInt = new int[k];
          m = 0;
          n = 0;
          if (m >= k) {
            continue;
          }
          i1 = getState(localXmlPullParser.getAttributeName(m)).intValue();
          if (i1 == 0) {
            continue;
          }
          if (i1 == 16843161)
          {
            str2 = localXmlPullParser.getAttributeValue(m);
            i2 = n;
            continue;
          }
          i2 = n + 1;
          if (!Boolean.parseBoolean(localXmlPullParser.getAttributeValue(m))) {
            continue;
          }
        }
      }
      catch (XmlPullParserException localXmlPullParserException1)
      {
        String str2;
        int[] arrayOfInt;
        throw new DrawableLoaderException(localXmlPullParserException1);
        int i1 = -i1;
        continue;
        localObject1.add(new StatePair(StateSet.trimStateSet(arrayOfInt, n), str2));
      }
      catch (IOException localIOException1) {}
    }
    arrayOfInt[n] = i1;
    break label327;
    throw new DrawableLoaderException(localIOException1);
    if (localObject1 == null) {
      throw new DrawableLoaderException();
    }
    return localObject1;
  }
  
  private static boolean isEmpty(String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramArrayOfString[i] != null) {
        return false;
      }
    }
    return true;
  }
  
  private static Drawable loadBitmapDrawable(Resources paramResources, InputStream paramInputStream)
  {
    Bitmap localBitmap = BitmapFactory.decodeStream(paramInputStream);
    localBitmap.setDensity(0);
    return DensityIndependentBitmapDrawableFactory.create(localBitmap);
  }
  
  private static void loadCorners(GradientBuilder paramGradientBuilder, XmlPullParser paramXmlPullParser)
  {
    String str1 = paramXmlPullParser.getNamespace();
    String str2 = paramXmlPullParser.getAttributeValue(str1, "radius");
    String[] arrayOfString = new String[4];
    arrayOfString[0] = paramXmlPullParser.getAttributeValue(str1, "topLeftRadius");
    arrayOfString[1] = paramXmlPullParser.getAttributeValue(str1, "topRightRadius");
    arrayOfString[2] = paramXmlPullParser.getAttributeValue(str1, "bottomLeftRadius");
    arrayOfString[3] = paramXmlPullParser.getAttributeValue(str1, "bottomRightRadius");
    if (isEmpty(arrayOfString)) {
      paramGradientBuilder.setCornerRadius(parseValue(str2, 0.0F));
    }
    for (;;)
    {
      paramGradientBuilder.setCorners(true);
      return;
      float[] arrayOfFloat = new float[8];
      for (int i = 0; i < arrayOfString.length; i++)
      {
        float f = parseValue(arrayOfString[i], Float.parseFloat(str2));
        arrayOfFloat[(i * 2)] = f;
        arrayOfFloat[(1 + i * 2)] = f;
      }
      paramGradientBuilder.setCornerRadii(arrayOfFloat);
    }
  }
  
  public static Drawable loadDrawable(Resources paramResources, File paramFile, String paramString)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    FileInputStream localFileInputStream = new FileInputStream(new File(paramFile, paramString));
    switch (1.$SwitchMap$com$touchtype$keyboard$theme$util$DrawableLoader$Ext[getExtension(paramString).ordinal()])
    {
    default: 
      Closeables.closeQuietly(localFileInputStream);
      throw new DrawableLoaderException();
    case 1: 
      return loadBitmapDrawable(paramResources, localFileInputStream);
    case 2: 
      return loadNinePatchDrawable(paramResources, localFileInputStream);
    }
    Closeables.closeQuietly(localFileInputStream);
    return loadFromXml(paramResources, paramFile, paramString);
  }
  
  /* Error */
  private static Drawable loadFromXml(Resources paramResources, File paramFile, String paramString)
    throws DrawableLoader.DrawableLoaderException, FileNotFoundException
  {
    // Byte code:
    //   0: new 288	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: aload_2
    //   6: invokespecial 291	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   9: astore_3
    //   10: new 12	java/io/FileInputStream
    //   13: dup
    //   14: aload_3
    //   15: invokespecial 16	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   18: astore 4
    //   20: getstatic 319	com/touchtype/keyboard/theme/util/DrawableLoader$1:$SwitchMap$com$touchtype$keyboard$theme$util$DrawableLoader$Type	[I
    //   23: aload_3
    //   24: invokestatic 321	com/touchtype/keyboard/theme/util/DrawableLoader:getDrawableType	(Ljava/io/File;)Lcom/touchtype/keyboard/theme/util/DrawableLoader$Type;
    //   27: invokevirtual 322	com/touchtype/keyboard/theme/util/DrawableLoader$Type:ordinal	()I
    //   30: iaload
    //   31: tableswitch	default:+21 -> 52, 1:+46->77, 2:+63->94
    //   53: nop
    //   54: lconst_1
    //   55: dup
    //   56: ldc_w 324
    //   59: invokespecial 327	com/touchtype/keyboard/theme/util/DrawableLoader$DrawableLoaderException:<init>	(Ljava/lang/String;)V
    //   62: athrow
    //   63: astore 5
    //   65: aload 4
    //   67: astore 6
    //   69: aload 6
    //   71: invokestatic 332	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   74: aload 5
    //   76: athrow
    //   77: aload_0
    //   78: aload_1
    //   79: aload 4
    //   81: invokestatic 336	com/touchtype/keyboard/theme/util/DrawableLoader:loadStateListDrawable	(Landroid/content/res/Resources;Ljava/io/File;Ljava/io/InputStream;)Landroid/graphics/drawable/Drawable;
    //   84: astore 8
    //   86: aload 4
    //   88: invokestatic 332	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   91: aload 8
    //   93: areturn
    //   94: aload_0
    //   95: aload 4
    //   97: invokestatic 339	com/touchtype/keyboard/theme/util/DrawableLoader:loadGradientDrawable	(Landroid/content/res/Resources;Ljava/io/InputStream;)Landroid/graphics/drawable/Drawable;
    //   100: astore 7
    //   102: aload 4
    //   104: invokestatic 332	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   107: aload 7
    //   109: areturn
    //   110: astore 5
    //   112: aconst_null
    //   113: astore 6
    //   115: goto -46 -> 69
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	paramResources	Resources
    //   0	118	1	paramFile	File
    //   0	118	2	paramString	String
    //   9	15	3	localFile	File
    //   18	85	4	localFileInputStream1	FileInputStream
    //   63	12	5	localObject1	Object
    //   110	1	5	localObject2	Object
    //   67	47	6	localFileInputStream2	FileInputStream
    //   100	8	7	localDrawable1	Drawable
    //   84	8	8	localDrawable2	Drawable
    // Exception table:
    //   from	to	target	type
    //   20	52	63	finally
    //   52	63	63	finally
    //   77	86	63	finally
    //   94	102	63	finally
    //   10	20	110	finally
  }
  
  private static void loadGradient(GradientBuilder paramGradientBuilder, XmlPullParser paramXmlPullParser)
    throws DrawableLoader.DrawableLoaderException
  {
    String str1 = paramXmlPullParser.getNamespace();
    String str2 = paramXmlPullParser.getAttributeValue(str1, "angle");
    paramGradientBuilder.setCenterX(parseValue(paramXmlPullParser.getAttributeValue(str1, "centerX"), 0.5F));
    paramGradientBuilder.setCenterY(parseValue(paramXmlPullParser.getAttributeValue(str1, "centerY"), 0.5F));
    String str3 = paramXmlPullParser.getAttributeValue(str1, "centerColor");
    String str4 = paramXmlPullParser.getAttributeValue(str1, "startColor");
    int i;
    label94:
    int j;
    label118:
    String str6;
    int k;
    String str7;
    if (str4 != null)
    {
      i = Color.parseColor(str4);
      String str5 = paramXmlPullParser.getAttributeValue(str1, "endColor");
      if (str5 == null) {
        break label289;
      }
      j = Color.parseColor(str5);
      str6 = paramXmlPullParser.getAttributeValue(str1, "gradientRadius");
      k = getGradientType(paramXmlPullParser.getAttributeValue(str1, "type")).intValue();
      paramGradientBuilder.setGradientType(k);
      str7 = paramXmlPullParser.getAttributeValue(str1, "useLevel");
      if (str7 == null) {
        break label295;
      }
    }
    label289:
    label295:
    for (boolean bool = Boolean.parseBoolean(str7);; bool = false)
    {
      paramGradientBuilder.setUseLevel(bool);
      if (k != 0) {
        break label427;
      }
      switch (parseValue(str2, 0) % 360)
      {
      default: 
        throw new DrawableLoaderException("Invalid GradientDrawable");
        i = 0;
        break label94;
        j = 0;
        break label118;
      }
    }
    paramGradientBuilder.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
    if (str3 != null)
    {
      int m = Color.parseColor(str3);
      paramGradientBuilder.setColors(new int[3]);
      paramGradientBuilder.getColors()[0] = i;
      paramGradientBuilder.getColors()[1] = m;
      paramGradientBuilder.getColors()[2] = j;
    }
    for (;;)
    {
      paramGradientBuilder.setGradient(true);
      return;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.BL_TR);
      break;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
      break;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.BR_TL);
      break;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
      break;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.TR_BL);
      break;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
      break;
      paramGradientBuilder.setOrientation(GradientDrawable.Orientation.TL_BR);
      break;
      label427:
      if (str6 != null)
      {
        paramGradientBuilder.setGradientRadius(0.0F);
        break;
      }
      if (k != 1) {
        break;
      }
      throw new DrawableLoaderException("<gradient> tag requires 'gradientRadius' attribute with radial type");
      paramGradientBuilder.setColors(new int[2]);
      paramGradientBuilder.getColors()[0] = i;
      paramGradientBuilder.getColors()[1] = j;
    }
  }
  
  private static Drawable loadGradientDrawable(Resources paramResources, InputStream paramInputStream)
    throws DrawableLoader.DrawableLoaderException
  {
    try
    {
      localXmlPullParser = Xml.newPullParser();
      localXmlPullParser.setInput(paramInputStream, null);
      int i = localXmlPullParser.next();
      j = i;
      localObject1 = null;
    }
    catch (XmlPullParserException localXmlPullParserException1)
    {
      try
      {
        XmlPullParser localXmlPullParser;
        int j;
        String str = localXmlPullParser.getName();
        Object localObject2;
        if (str.equals("shape"))
        {
          localObject2 = new GradientBuilder(null);
          ((GradientBuilder)localObject2).setShape(getShape(localXmlPullParser.getAttributeValue(localXmlPullParser.getNamespace(), "shape")).intValue());
        }
        for (;;)
        {
          int k = localXmlPullParser.next();
          j = k;
          localObject1 = localObject2;
          break;
          if (str.equals("corners"))
          {
            loadCorners(localObject1, localXmlPullParser);
            localObject2 = localObject1;
          }
          else if (str.equals("gradient"))
          {
            loadGradient(localObject1, localXmlPullParser);
            localObject2 = localObject1;
          }
          else if (str.equals("padding"))
          {
            loadPadding(localObject1, localXmlPullParser);
            localObject2 = localObject1;
          }
          else if (str.equals("size"))
          {
            loadSize(localObject1, localXmlPullParser);
            localObject2 = localObject1;
          }
          else if (str.equals("solid"))
          {
            loadSolid(localObject1, localXmlPullParser);
            localObject2 = localObject1;
          }
          else
          {
            if (str.equals("stroke")) {
              loadStroke(localObject1, localXmlPullParser);
            }
            localObject2 = localObject1;
          }
        }
        localXmlPullParserException1 = localXmlPullParserException1;
      }
      catch (IOException localIOException2)
      {
        break label279;
      }
      catch (XmlPullParserException localXmlPullParserException2)
      {
        Object localObject1;
        break label269;
      }
      throw new DrawableLoaderException(localXmlPullParserException1);
    }
    catch (IOException localIOException1)
    {
      label269:
      label279:
      throw new DrawableLoaderException(localIOException1);
    }
    if ((j == 1) || (j == 2)) {}
    return localObject1.build();
  }
  
  public static Map<KeyIcon, Drawable> loadIconSet(Resources paramResources, File paramFile, String paramString)
    throws DrawableLoader.DrawableLoaderException, FileNotFoundException
  {
    if (getExtension(paramString) != Ext.XML) {
      throw new DrawableLoaderException("Invalid Iconset");
    }
    localHashMap = new HashMap();
    File localFile = new File(paramFile, paramString);
    try
    {
      Map localMap = ThemeLoader.parseIconSet(localFile);
      Iterator localIterator = localMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localHashMap.put(KeyIcon.valueOf((String)localEntry.getKey()), loadDrawable(paramResources, paramFile, (String)localEntry.getValue()));
      }
      return localHashMap;
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      throw new DrawableLoaderException(localXmlPullParserException);
    }
  }
  
  public static Map<KeyIcon, Drawable> loadIcons(Resources paramResources, File paramFile, Map<String, String> paramMap)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    if (paramMap.containsKey("IconSet")) {}
    for (Object localObject = loadIconSet(paramResources, paramFile, (String)paramMap.remove("IconSet"));; localObject = new HashMap())
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        ((Map)localObject).put(KeyIcon.valueOf((String)localEntry.getKey()), loadDrawable(paramResources, paramFile, (String)localEntry.getValue()));
      }
    }
    return localObject;
  }
  
  public static Map<KeyIcon, Drawable> loadIconsFromResources(Context paramContext, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.Icons);
    for (int i = 0; i < localTypedArray.getIndexCount(); i++)
    {
      int j = localTypedArray.getIndex(i);
      localHashMap.put(KeyIcon.getFromAttributeValue(j), localTypedArray.getDrawable(j));
    }
    localTypedArray.recycle();
    return localHashMap;
  }
  
  private static Drawable loadNinePatchDrawable(Resources paramResources, InputStream paramInputStream)
    throws DrawableLoader.DrawableLoaderException
  {
    Rect localRect = new Rect();
    Bitmap localBitmap1 = BitmapFactory.decodeStream(paramInputStream, localRect, null);
    Bitmap localBitmap2 = localBitmap1.copy(Bitmap.Config.ARGB_8888, true);
    byte[] arrayOfByte = localBitmap1.getNinePatchChunk();
    if (!NinePatch.isNinePatchChunk(arrayOfByte)) {
      throw new DrawableLoaderException();
    }
    localBitmap1.recycle();
    return new NinePatchDrawable(paramResources, localBitmap2, arrayOfByte, localRect, null).mutate();
  }
  
  private static void loadPadding(GradientBuilder paramGradientBuilder, XmlPullParser paramXmlPullParser)
  {
    String str = paramXmlPullParser.getNamespace();
    paramGradientBuilder.setPadding(new Rect(parseValue(paramXmlPullParser.getAttributeValue(str, "left"), 0), parseValue(paramXmlPullParser.getAttributeValue(str, "top"), 0), parseValue(paramXmlPullParser.getAttributeValue(str, "right"), 0), parseValue(paramXmlPullParser.getAttributeValue(str, "bottom"), 0)));
  }
  
  private static void loadSize(GradientBuilder paramGradientBuilder, XmlPullParser paramXmlPullParser)
  {
    String str = paramXmlPullParser.getNamespace();
    paramGradientBuilder.setWidth(parseValue(paramXmlPullParser.getAttributeValue(str, "width"), 0));
    paramGradientBuilder.setHeight(parseValue(paramXmlPullParser.getAttributeValue(str, "height"), 0));
    paramGradientBuilder.setSize(true);
  }
  
  private static void loadSolid(GradientBuilder paramGradientBuilder, XmlPullParser paramXmlPullParser)
  {
    String str = paramXmlPullParser.getAttributeValue(paramXmlPullParser.getNamespace(), "color");
    if (str != null) {}
    for (int i = Color.parseColor(str);; i = 0)
    {
      paramGradientBuilder.setArgb(i);
      paramGradientBuilder.setSolid(true);
      return;
    }
  }
  
  private static Drawable loadStateListDrawable(Resources paramResources, File paramFile, InputStream paramInputStream)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    Iterator localIterator = getStates(paramInputStream).iterator();
    while (localIterator.hasNext())
    {
      StatePair localStatePair = (StatePair)localIterator.next();
      localStateListDrawable.addState(localStatePair.getStates(), loadDrawable(paramResources, paramFile, localStatePair.getPath()));
    }
    return localStateListDrawable;
  }
  
  private static void loadStroke(GradientBuilder paramGradientBuilder, XmlPullParser paramXmlPullParser)
  {
    String str1 = paramXmlPullParser.getNamespace();
    paramGradientBuilder.setStrokeWidth(parseValue(paramXmlPullParser.getAttributeValue(str1, "width"), 0));
    String str2 = paramXmlPullParser.getAttributeValue(str1, "color");
    int i = 0;
    if (str2 != null) {
      i = Color.parseColor(str2);
    }
    paramGradientBuilder.setStrokeColor(i);
    paramGradientBuilder.setDashWidth(parseValue(paramXmlPullParser.getAttributeValue(str1, "dashWidth"), 0.0F));
    paramGradientBuilder.setDashGap(parseValue(paramXmlPullParser.getAttributeValue(str1, "dashGap"), 0.0F));
    paramGradientBuilder.setStroke(true);
  }
  
  private static float parseValue(String paramString, float paramFloat)
  {
    if (paramString == null) {
      return paramFloat;
    }
    try
    {
      if (paramString.endsWith("dip")) {
        return Float.parseFloat(paramString.replaceAll("dip", ""));
      }
      float f = Float.parseFloat(paramString);
      return f;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return paramFloat;
  }
  
  private static int parseValue(String paramString, int paramInt)
  {
    if (paramString == null) {
      return paramInt;
    }
    try
    {
      if (paramString.endsWith("dip")) {
        return Integer.parseInt(paramString.replaceAll("dip", ""));
      }
      int i = Integer.parseInt(paramString);
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return paramInt;
  }
  
  public static final class DrawableLoaderException
    extends Exception
  {
    public DrawableLoaderException() {}
    
    public DrawableLoaderException(Exception paramException)
    {
      super();
    }
    
    public DrawableLoaderException(String paramString)
    {
      super();
    }
  }
  
  private static enum Ext
  {
    static
    {
      NINE = new Ext("NINE", 1);
      XML = new Ext("XML", 2);
      INVALID = new Ext("INVALID", 3);
      Ext[] arrayOfExt = new Ext[4];
      arrayOfExt[0] = PNG;
      arrayOfExt[1] = NINE;
      arrayOfExt[2] = XML;
      arrayOfExt[3] = INVALID;
      $VALUES = arrayOfExt;
    }
    
    private Ext() {}
  }
  
  private static final class GradientBuilder
  {
    private int argb;
    private float centerX;
    private float centerY;
    private int[] colors;
    private float[] cornerRadii = null;
    private float cornerRadius;
    private boolean corners = false;
    private float dashGap;
    private float dashWidth;
    private boolean gradient = false;
    private float gradientRadius;
    private int gradientType;
    private int height = -1;
    private GradientDrawable.Orientation orientation = null;
    private Rect padding = null;
    private int shape;
    private boolean size = false;
    private boolean solid = false;
    private boolean stroke = false;
    private int strokeColor;
    private int strokeWidth;
    private boolean useLevel;
    private int width = -1;
    
    public GradientDrawable build()
    {
      PaddableGradientDrawable localPaddableGradientDrawable;
      if (getOrientation() != null)
      {
        localPaddableGradientDrawable = new PaddableGradientDrawable(getOrientation(), getColors());
        localPaddableGradientDrawable.setShape(getShape());
        if (isCorners())
        {
          if (getCornerRadii() != null) {
            break label202;
          }
          localPaddableGradientDrawable.setCornerRadius(getCornerRadius());
        }
      }
      for (;;)
      {
        if (isGradient())
        {
          localPaddableGradientDrawable.setGradientCenter(getCenterX(), getCenterY());
          localPaddableGradientDrawable.setGradientType(getGradientType());
          if (getGradientType() == 1) {
            localPaddableGradientDrawable.setGradientRadius(getGradientRadius());
          }
          localPaddableGradientDrawable.setUseLevel(isUseLevel());
        }
        if (getPadding() != null) {
          localPaddableGradientDrawable.setPadding(getPadding());
        }
        if (isSize()) {
          localPaddableGradientDrawable.setSize(getWidth(), getHeight());
        }
        if (isSolid()) {
          localPaddableGradientDrawable.setColor(getArgb());
        }
        if (isStroke())
        {
          if (getDashWidth() == 0.0F) {
            break label213;
          }
          localPaddableGradientDrawable.setStroke(getStrokeWidth(), getStrokeColor(), getDashWidth(), getDashGap());
        }
        return localPaddableGradientDrawable;
        localPaddableGradientDrawable = new PaddableGradientDrawable();
        break;
        label202:
        localPaddableGradientDrawable.setCornerRadii(getCornerRadii());
      }
      label213:
      localPaddableGradientDrawable.setStroke(getStrokeWidth(), getStrokeColor());
      return localPaddableGradientDrawable;
    }
    
    public int getArgb()
    {
      return this.argb;
    }
    
    public float getCenterX()
    {
      return this.centerX;
    }
    
    public float getCenterY()
    {
      return this.centerY;
    }
    
    public int[] getColors()
    {
      return this.colors;
    }
    
    public float[] getCornerRadii()
    {
      return this.cornerRadii;
    }
    
    public float getCornerRadius()
    {
      return this.cornerRadius;
    }
    
    public float getDashGap()
    {
      return this.dashGap;
    }
    
    public float getDashWidth()
    {
      return this.dashWidth;
    }
    
    public float getGradientRadius()
    {
      return this.gradientRadius;
    }
    
    public int getGradientType()
    {
      return this.gradientType;
    }
    
    public int getHeight()
    {
      return this.height;
    }
    
    public GradientDrawable.Orientation getOrientation()
    {
      return this.orientation;
    }
    
    public Rect getPadding()
    {
      return this.padding;
    }
    
    public int getShape()
    {
      return this.shape;
    }
    
    public int getStrokeColor()
    {
      return this.strokeColor;
    }
    
    public int getStrokeWidth()
    {
      return this.strokeWidth;
    }
    
    public int getWidth()
    {
      return this.width;
    }
    
    public boolean isCorners()
    {
      return this.corners;
    }
    
    public boolean isGradient()
    {
      return this.gradient;
    }
    
    public boolean isSize()
    {
      return this.size;
    }
    
    public boolean isSolid()
    {
      return this.solid;
    }
    
    public boolean isStroke()
    {
      return this.stroke;
    }
    
    public boolean isUseLevel()
    {
      return this.useLevel;
    }
    
    public void setArgb(int paramInt)
    {
      this.argb = paramInt;
    }
    
    public void setCenterX(float paramFloat)
    {
      this.centerX = paramFloat;
    }
    
    public void setCenterY(float paramFloat)
    {
      this.centerY = paramFloat;
    }
    
    public void setColors(int[] paramArrayOfInt)
    {
      this.colors = paramArrayOfInt;
    }
    
    public void setCornerRadii(float[] paramArrayOfFloat)
    {
      this.cornerRadii = paramArrayOfFloat;
    }
    
    public void setCornerRadius(float paramFloat)
    {
      this.cornerRadius = paramFloat;
    }
    
    public void setCorners(boolean paramBoolean)
    {
      this.corners = paramBoolean;
    }
    
    public void setDashGap(float paramFloat)
    {
      this.dashGap = paramFloat;
    }
    
    public void setDashWidth(float paramFloat)
    {
      this.dashWidth = paramFloat;
    }
    
    public void setGradient(boolean paramBoolean)
    {
      this.gradient = paramBoolean;
    }
    
    public void setGradientRadius(float paramFloat)
    {
      this.gradientRadius = paramFloat;
    }
    
    public void setGradientType(int paramInt)
    {
      this.gradientType = paramInt;
    }
    
    public void setHeight(int paramInt)
    {
      this.height = paramInt;
    }
    
    public void setOrientation(GradientDrawable.Orientation paramOrientation)
    {
      this.orientation = paramOrientation;
    }
    
    public void setPadding(Rect paramRect)
    {
      this.padding = paramRect;
    }
    
    public void setShape(int paramInt)
    {
      this.shape = paramInt;
    }
    
    public void setSize(boolean paramBoolean)
    {
      this.size = paramBoolean;
    }
    
    public void setSolid(boolean paramBoolean)
    {
      this.solid = paramBoolean;
    }
    
    public void setStroke(boolean paramBoolean)
    {
      this.stroke = paramBoolean;
    }
    
    public void setStrokeColor(int paramInt)
    {
      this.strokeColor = paramInt;
    }
    
    public void setStrokeWidth(int paramInt)
    {
      this.strokeWidth = paramInt;
    }
    
    public void setUseLevel(boolean paramBoolean)
    {
      this.useLevel = paramBoolean;
    }
    
    public void setWidth(int paramInt)
    {
      this.width = paramInt;
    }
  }
  
  private static final class StatePair
  {
    private final String mPath;
    private final int[] mStates;
    
    public StatePair(int[] paramArrayOfInt, String paramString)
    {
      this.mStates = ((int[])paramArrayOfInt.clone());
      this.mPath = paramString;
    }
    
    public String getPath()
    {
      return this.mPath;
    }
    
    public int[] getStates()
    {
      return this.mStates;
    }
  }
  
  private static enum Type
  {
    static
    {
      GRADIENT = new Type("GRADIENT", 1);
      INVALID = new Type("INVALID", 2);
      Type[] arrayOfType = new Type[3];
      arrayOfType[0] = STATELIST;
      arrayOfType[1] = GRADIENT;
      arrayOfType[2] = INVALID;
      $VALUES = arrayOfType;
    }
    
    private Type() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.DrawableLoader
 * JD-Core Version:    0.7.0.1
 */