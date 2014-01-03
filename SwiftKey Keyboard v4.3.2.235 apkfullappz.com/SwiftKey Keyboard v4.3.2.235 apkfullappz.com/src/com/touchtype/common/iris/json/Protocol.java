package com.touchtype.common.iris.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;

final class Protocol
{
  @SerializedName("format")
  private String mFormat;
  
  public static Protocol newInstance(Context paramContext)
  {
    Protocol localProtocol = new Protocol();
    localProtocol.mFormat = paramContext.getString(2131296341);
    return localProtocol;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.json.Protocol
 * JD-Core Version:    0.7.0.1
 */