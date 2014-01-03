package org.spongycastle.cms;

public class PasswordRecipientId
  extends RecipientId
{
  public PasswordRecipientId()
  {
    super(3);
  }
  
  public Object clone()
  {
    return new PasswordRecipientId();
  }
  
  public boolean equals(Object paramObject)
  {
    return (paramObject instanceof PasswordRecipientId);
  }
  
  public int hashCode()
  {
    return 3;
  }
  
  public boolean match(Object paramObject)
  {
    return (paramObject instanceof PasswordRecipientInformation);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.PasswordRecipientId
 * JD-Core Version:    0.7.0.1
 */