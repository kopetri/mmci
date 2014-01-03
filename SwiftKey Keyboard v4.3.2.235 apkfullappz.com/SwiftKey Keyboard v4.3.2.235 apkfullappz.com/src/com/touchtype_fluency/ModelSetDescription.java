package com.touchtype_fluency;

import java.io.IOException;

public class ModelSetDescription
{
  private long peer;
  
  static {}
  
  private ModelSetDescription(long paramLong)
  {
    this.peer = paramLong;
  }
  
  private native void destroyPeer();
  
  public static native ModelSetDescription dynamicTemporary(int paramInt, String[] paramArrayOfString);
  
  public static native ModelSetDescription dynamicWithFile(String paramString, int paramInt, String[] paramArrayOfString, Type paramType);
  
  public static native ModelSetDescription fromFile(String paramString)
    throws IOException;
  
  static native void initIDs();
  
  private native boolean isEqualTo(ModelSetDescription paramModelSetDescription);
  
  public static ModelSetDescription merge(ModelSetDescription paramModelSetDescription1, ModelSetDescription paramModelSetDescription2, String paramString)
    throws IOException, IllegalStateException
  {
    return merge(paramModelSetDescription1, paramModelSetDescription2, paramString, Trainer.ModelFileVersion.Latest_Version);
  }
  
  public static native ModelSetDescription merge(ModelSetDescription paramModelSetDescription1, ModelSetDescription paramModelSetDescription2, String paramString, Trainer.ModelFileVersion paramModelFileVersion)
    throws IOException, IllegalStateException;
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ModelSetDescription)) {
      return isEqualTo((ModelSetDescription)paramObject);
    }
    return false;
  }
  
  protected void finalize()
  {
    destroyPeer();
  }
  
  public native String[] getUserTags();
  
  public native int hashCode();
  
  public native String toString();
  
  public static enum Type
  {
    static
    {
      OTHER_DYNAMIC_MODEL = new Type("OTHER_DYNAMIC_MODEL", 1);
      Type[] arrayOfType = new Type[2];
      arrayOfType[0] = PRIMARY_DYNAMIC_MODEL;
      arrayOfType[1] = OTHER_DYNAMIC_MODEL;
      $VALUES = arrayOfType;
    }
    
    private Type() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.ModelSetDescription
 * JD-Core Version:    0.7.0.1
 */