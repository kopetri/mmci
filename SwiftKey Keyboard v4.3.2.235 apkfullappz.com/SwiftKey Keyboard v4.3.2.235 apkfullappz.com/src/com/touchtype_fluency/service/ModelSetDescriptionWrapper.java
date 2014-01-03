package com.touchtype_fluency.service;

import com.touchtype_fluency.ModelSetDescription;
import com.touchtype_fluency.ModelSetDescription.Type;
import java.io.IOException;

class ModelSetDescriptionWrapper
{
  public ModelSetDescription dynamicTemporary(int paramInt, String[] paramArrayOfString)
  {
    return ModelSetDescription.dynamicTemporary(paramInt, paramArrayOfString);
  }
  
  public ModelSetDescription dynamicWithFile(String paramString, int paramInt, String[] paramArrayOfString, ModelSetDescription.Type paramType)
    throws IOException
  {
    return ModelSetDescription.dynamicWithFile(paramString, paramInt, paramArrayOfString, paramType);
  }
  
  public ModelSetDescription fromFile(String paramString)
    throws IOException
  {
    return ModelSetDescription.fromFile(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.ModelSetDescriptionWrapper
 * JD-Core Version:    0.7.0.1
 */