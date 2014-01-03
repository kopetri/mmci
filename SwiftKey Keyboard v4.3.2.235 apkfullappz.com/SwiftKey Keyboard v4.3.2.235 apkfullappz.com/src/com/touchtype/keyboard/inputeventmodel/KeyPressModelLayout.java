package com.touchtype.keyboard.inputeventmodel;

import android.graphics.Matrix;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils.TransformableKeyShape;
import com.touchtype_fluency.KeyShape;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public final class KeyPressModelLayout
{
  private int mHeight;
  private final Map<LegacyTouchUtils.TransformableKeyShape, Character[]> mKeys;
  private int mWidth;
  
  public KeyPressModelLayout()
  {
    this.mKeys = new HashMap();
  }
  
  private KeyPressModelLayout(Map<LegacyTouchUtils.TransformableKeyShape, Character[]> paramMap, int paramInt1, int paramInt2)
  {
    this.mKeys = paramMap;
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
  }
  
  public int getHeight()
  {
    return this.mHeight;
  }
  
  public TreeMap<KeyShape, Character[]> getRawLayout()
  {
    TreeMap localTreeMap = new TreeMap();
    Iterator localIterator = this.mKeys.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localTreeMap.put(((LegacyTouchUtils.TransformableKeyShape)localEntry.getKey()).toKeyShape(new Matrix()), localEntry.getValue());
    }
    return localTreeMap;
  }
  
  public int getWidth()
  {
    return this.mWidth;
  }
  
  public KeyPressModelLayout mergedWith(KeyPressModelLayout paramKeyPressModelLayout, int paramInt1, int paramInt2)
  {
    KeyPressModelLayout localKeyPressModelLayout = new KeyPressModelLayout(this.mKeys, paramInt1, paramInt2);
    localKeyPressModelLayout.mKeys.putAll(paramKeyPressModelLayout.mKeys);
    return localKeyPressModelLayout;
  }
  
  public void put(LegacyTouchUtils.TransformableKeyShape paramTransformableKeyShape, Character[] paramArrayOfCharacter)
  {
    this.mKeys.put(paramTransformableKeyShape, paramArrayOfCharacter);
  }
  
  public int size()
  {
    return this.mKeys.size();
  }
  
  public KeyPressModelLayout transformed(Matrix paramMatrix, int paramInt1, int paramInt2)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.mKeys.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put(((LegacyTouchUtils.TransformableKeyShape)localEntry.getKey()).transformed(paramMatrix), localEntry.getValue());
    }
    return new KeyPressModelLayout(localHashMap, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.KeyPressModelLayout
 * JD-Core Version:    0.7.0.1
 */