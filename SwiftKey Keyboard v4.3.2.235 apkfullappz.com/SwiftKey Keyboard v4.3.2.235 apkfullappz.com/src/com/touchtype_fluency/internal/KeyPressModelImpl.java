package com.touchtype_fluency.internal;

import com.touchtype_fluency.FileNotWritableException;
import com.touchtype_fluency.KeyPressModel;
import com.touchtype_fluency.KeyShape;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.TouchHistory;
import java.io.IOException;
import java.util.Map;

public class KeyPressModelImpl
  implements KeyPressModel, Disposable
{
  private long peer;
  
  private KeyPressModelImpl(long paramLong)
  {
    this.peer = paramLong;
  }
  
  public static native void initIDs();
  
  public native void addTag(String paramString1, String paramString2);
  
  public native String[] closestKey(Point paramPoint);
  
  public void dispose()
  {
    this.peer = 0L;
  }
  
  public native String getTag(String paramString);
  
  public native void learnFrom(TouchHistory paramTouchHistory, Prediction paramPrediction);
  
  public native void loadFile(String paramString)
    throws IOException;
  
  public native void removeAllTags();
  
  public native void removeTag(String paramString);
  
  public native void reset();
  
  public native void saveFile(String paramString)
    throws FileNotWritableException, IllegalStateException;
  
  public native void set();
  
  public native void set(Map<KeyShape, Character[]> paramMap);
  
  public native void setKeyShape(KeyShape paramKeyShape, Character[] paramArrayOfCharacter)
    throws IllegalArgumentException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.KeyPressModelImpl
 * JD-Core Version:    0.7.0.1
 */