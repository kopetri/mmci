package com.touchtype.preferences.heatmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import org.json.JSONException;

public class HeatmapBuilder
{
  private static final String TAG = HeatmapBuilder.class.getSimpleName();
  private int[] mColourRow;
  private final int mHeight;
  private Bitmap mImage;
  private final KeyData mKeys;
  private double mMaxP;
  private final int mOrientation;
  private int mProgress;
  private long mTime;
  private final int mWidth;
  
  public HeatmapBuilder(ModelHandler paramModelHandler)
    throws IOException, JSONException
  {
    this.mKeys = new KeyData(paramModelHandler.getIMpath());
    this.mWidth = this.mKeys.getKeyboardWidth();
    this.mHeight = this.mKeys.getKeyboardHeight();
    this.mOrientation = paramModelHandler.getOrientation();
    if ((this.mWidth <= 0) || (this.mHeight <= 0)) {
      throw new IllegalArgumentException("Invalid Model - width and height must both be > 0");
    }
  }
  
  private int colorCalc(double paramDouble)
  {
    return Color.argb((int)(255.0D * paramDouble), 0, 0, 0);
  }
  
  protected void finalize()
  {
    this.mImage = null;
  }
  
  public Bitmap finishGen()
  {
    System.currentTimeMillis();
    return this.mImage;
  }
  
  public int runGen()
    throws IllegalStateException
  {
    if (this.mImage == null) {
      throw new IllegalStateException("Image bitmap is null");
    }
    if (this.mProgress < this.mHeight)
    {
      Vector2d localVector2d = new Vector2d();
      for (int i = 0; i < this.mWidth; i++)
      {
        localVector2d.set(i, this.mProgress);
        this.mColourRow[i] = colorCalc(this.mKeys.evaluate(localVector2d) / this.mMaxP);
      }
      this.mImage.setPixels(this.mColourRow, 0, this.mWidth, 0, this.mProgress, this.mWidth, 1);
      this.mProgress = (1 + this.mProgress);
    }
    for (;;)
    {
      return (int)(100.0F * (this.mProgress / this.mHeight));
      LogUtil.e(TAG, "Attempted to run completed builder");
    }
  }
  
  public int startGen()
  {
    this.mImage = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
    this.mMaxP = this.mKeys.maxProb();
    this.mColourRow = new int[this.mWidth];
    this.mTime = System.currentTimeMillis();
    this.mKeys.setOrientation(this.mOrientation);
    return this.mHeight;
  }
  
  static final class Vector2d
  {
    double mX;
    double mY;
    
    public Vector2d set(double paramDouble1, double paramDouble2)
    {
      this.mX = paramDouble1;
      this.mY = paramDouble2;
      return this;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.heatmap.HeatmapBuilder
 * JD-Core Version:    0.7.0.1
 */