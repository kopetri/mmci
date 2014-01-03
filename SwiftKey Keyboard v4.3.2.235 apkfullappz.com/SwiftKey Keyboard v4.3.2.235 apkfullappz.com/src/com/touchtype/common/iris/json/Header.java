package com.touchtype.common.iris.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;

public final class Header
{
  @SerializedName("instanceId")
  private InstanceId mInstanceId;
  @SerializedName("protocol")
  private Protocol mProtocol;
  
  public static Header newInstance(Context paramContext)
  {
    Header localHeader = new Header();
    localHeader.mInstanceId = InstanceId.newInstance(paramContext);
    localHeader.mProtocol = Protocol.newInstance(paramContext);
    return localHeader;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.json.Header
 * JD-Core Version:    0.7.0.1
 */