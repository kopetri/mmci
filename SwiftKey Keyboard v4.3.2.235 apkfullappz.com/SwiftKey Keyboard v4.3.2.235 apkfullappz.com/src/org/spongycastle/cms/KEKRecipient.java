package org.spongycastle.cms;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public abstract interface KEKRecipient
  extends Recipient
{
  public abstract RecipientOperator getRecipientOperator(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte)
    throws CMSException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KEKRecipient
 * JD-Core Version:    0.7.0.1
 */