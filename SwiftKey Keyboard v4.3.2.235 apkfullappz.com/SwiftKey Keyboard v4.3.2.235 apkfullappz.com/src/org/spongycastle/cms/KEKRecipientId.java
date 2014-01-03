package org.spongycastle.cms;

import org.spongycastle.util.Arrays;

public class KEKRecipientId
  extends RecipientId
{
  private byte[] keyIdentifier;
  
  public KEKRecipientId(byte[] paramArrayOfByte)
  {
    super(1);
    this.keyIdentifier = paramArrayOfByte;
  }
  
  public Object clone()
  {
    return new KEKRecipientId(this.keyIdentifier);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof KEKRecipientId)) {
      return false;
    }
    KEKRecipientId localKEKRecipientId = (KEKRecipientId)paramObject;
    return Arrays.areEqual(this.keyIdentifier, localKEKRecipientId.keyIdentifier);
  }
  
  public byte[] getKeyIdentifier()
  {
    return Arrays.clone(this.keyIdentifier);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.keyIdentifier);
  }
  
  public boolean match(Object paramObject)
  {
    if ((paramObject instanceof byte[])) {
      return Arrays.areEqual(this.keyIdentifier, (byte[])paramObject);
    }
    if ((paramObject instanceof KEKRecipientInformation)) {
      return ((KEKRecipientInformation)paramObject).getRID().equals(this);
    }
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KEKRecipientId
 * JD-Core Version:    0.7.0.1
 */