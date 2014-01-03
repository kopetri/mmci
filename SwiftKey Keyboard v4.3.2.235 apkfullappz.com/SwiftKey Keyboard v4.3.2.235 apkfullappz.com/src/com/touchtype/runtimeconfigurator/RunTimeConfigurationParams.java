package com.touchtype.runtimeconfigurator;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class RunTimeConfigurationParams
{
  @SerializedName("days")
  public int days;
  @SerializedName("paddingNonce")
  public String paddindNonce;
  @SerializedName("referrer-id")
  public String referrerId;
  
  public RunTimeConfigurationParams(int paramInt)
  {
    this.days = paramInt;
    this.paddindNonce = UUID.randomUUID().toString();
  }
  
  public RunTimeConfigurationParams(String paramString)
  {
    this.referrerId = paramString;
    this.paddindNonce = UUID.randomUUID().toString();
  }
  
  public RunTimeConfigurationParams(String paramString, int paramInt)
  {
    this.referrerId = paramString;
    this.days = paramInt;
    this.paddindNonce = UUID.randomUUID().toString();
  }
  
  public String toJson()
  {
    return new Gson().toJson(this);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.RunTimeConfigurationParams
 * JD-Core Version:    0.7.0.1
 */