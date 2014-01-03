package com.touchtype.sync.client;

public class Credential
{
  private String identifier;
  private CommonUtilities.CredentialType type;
  
  private Credential() {}
  
  Credential(String paramString, CommonUtilities.CredentialType paramCredentialType)
  {
    this.type = paramCredentialType;
    this.identifier = paramString;
  }
  
  public String getIdentifier()
  {
    return this.identifier;
  }
  
  public CommonUtilities.CredentialType getType()
  {
    return this.type;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.Credential
 * JD-Core Version:    0.7.0.1
 */