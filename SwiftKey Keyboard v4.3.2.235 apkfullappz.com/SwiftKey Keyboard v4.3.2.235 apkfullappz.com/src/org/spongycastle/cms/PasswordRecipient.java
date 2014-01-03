package org.spongycastle.cms;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public abstract interface PasswordRecipient
  extends Recipient
{
  public static final int PKCS5_SCHEME2 = 0;
  public static final int PKCS5_SCHEME2_UTF8 = 1;
  
  public abstract char[] getPassword();
  
  public abstract int getPasswordConversionScheme();
  
  public abstract RecipientOperator getRecipientOperator(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws CMSException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.PasswordRecipient
 * JD-Core Version:    0.7.0.1
 */