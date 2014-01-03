package com.touchtype.preferences.heatmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class KeyData
{
  private double mBlobScale;
  private int mIMHash;
  private final Map<String, Key> mKeyData;
  private int mKeyboardHeight;
  private int mKeyboardWidth;
  
  public KeyData(File paramFile)
    throws IOException, JSONException
  {
    String str1 = FileUtils.readFileToString(paramFile);
    this.mIMHash = str1.hashCode();
    this.mKeyData = new HashMap();
    JSONObject localJSONObject1 = (JSONObject)new JSONTokener(str1).nextValue();
    Iterator localIterator = localJSONObject1.keys();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      if ((!str2.equals(" ")) && (!str2.equals(" _2")) && (!str2.equals("tags")))
      {
        Key localKey = new Key(localJSONObject1.getJSONObject(str2));
        this.mKeyData.put(str2, localKey);
      }
      else if (str2.equals("tags"))
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("tags");
        this.mKeyboardWidth = localJSONObject2.getInt("keyboard_width");
        this.mKeyboardHeight = localJSONObject2.getInt("keyboard_height");
      }
    }
    this.mBlobScale = -0.5D;
  }
  
  private double max_probability(Key paramKey)
  {
    return Math.sqrt(paramKey.precisions[0] * paramKey.precisions[3] - paramKey.precisions[1] * paramKey.precisions[2]) / 6.283185307179586D;
  }
  
  public double evaluate(HeatmapBuilder.Vector2d paramVector2d)
  {
    double d1 = 0.0D;
    double d2 = this.mBlobScale;
    Iterator localIterator = this.mKeyData.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Key localKey = (Key)((Map.Entry)localIterator.next()).getValue();
      double d3 = paramVector2d.mX - localKey.means[0];
      double d4 = paramVector2d.mY - localKey.means[1];
      double d5 = d2 * (d3 * d3 * localKey.precisions[0] + d3 * d4 * (localKey.precisions[1] + localKey.precisions[2]) + d4 * d4 * localKey.precisions[3]);
      d1 = Math.max(d1, localKey.max_probability * Math.exp(d5));
    }
    return d1;
  }
  
  public Bitmap genOverlay(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt3, Bitmap.Config.ARGB_8888);
    Paint localPaint = new Paint();
    localPaint.setColor(paramInt4);
    localPaint.setTextAlign(Paint.Align.CENTER);
    localPaint.setTextSize(paramInt1);
    localPaint.setAntiAlias(true);
    Canvas localCanvas = new Canvas(localBitmap);
    Iterator localIterator = this.mKeyData.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Key localKey = (Key)localEntry.getValue();
      Rect localRect = new Rect();
      localPaint.getTextBounds(((String)localEntry.getKey()).toUpperCase(), 0, ((String)localEntry.getKey()).length(), localRect);
      float f1 = (float)localKey.original[0];
      float f2 = (float)localKey.original[1];
      localCanvas.drawText(((String)localEntry.getKey()).toUpperCase(), f1, f2, localPaint);
    }
    return localBitmap;
  }
  
  public int getIMhash()
  {
    return this.mIMHash;
  }
  
  public int getKeyboardHeight()
  {
    return this.mKeyboardHeight;
  }
  
  public int getKeyboardWidth()
  {
    return this.mKeyboardWidth;
  }
  
  public double maxProb()
  {
    double d = 0.0D;
    Iterator localIterator = this.mKeyData.entrySet().iterator();
    while (localIterator.hasNext()) {
      d = Math.max(d, max_probability((Key)((Map.Entry)localIterator.next()).getValue()));
    }
    return d;
  }
  
  public void setOrientation(int paramInt)
  {
    if (paramInt == 1) {}
    for (double d = -0.5D;; d = -0.6D)
    {
      this.mBlobScale = d;
      return;
    }
  }
  
  private final class Key
  {
    double max_probability;
    double[] means;
    double[] original;
    double[] precisions;
    
    public Key(JSONObject paramJSONObject)
      throws JSONException
    {
      JSONArray localJSONArray1 = paramJSONObject.getJSONObject("mean").getJSONArray("mode");
      JSONArray localJSONArray2 = paramJSONObject.getJSONObject("precision").getJSONArray("mode");
      JSONArray localJSONArray3 = paramJSONObject.getJSONArray("prior-mean");
      this.means = new double[localJSONArray1.length()];
      this.precisions = new double[localJSONArray2.length()];
      this.original = new double[localJSONArray3.length()];
      for (int i = 0; i < localJSONArray1.length(); i++) {
        this.means[i] = localJSONArray1.getDouble(i);
      }
      for (int j = 0; j < localJSONArray2.length(); j++) {
        this.precisions[j] = localJSONArray2.getDouble(j);
      }
      for (int k = 0; k < localJSONArray3.length(); k++) {
        this.original[k] = localJSONArray3.getDouble(k);
      }
      this.max_probability = KeyData.this.max_probability(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.heatmap.KeyData
 * JD-Core Version:    0.7.0.1
 */