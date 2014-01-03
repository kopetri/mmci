package org.spongycastle.cms;

import java.math.BigInteger;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cert.selector.X509CertificateHolderSelector;

public class KeyAgreeRecipientId
  extends RecipientId
{
  private X509CertificateHolderSelector baseSelector;
  
  public KeyAgreeRecipientId(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    this(paramX500Name, paramBigInteger, null);
  }
  
  public KeyAgreeRecipientId(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    this(new X509CertificateHolderSelector(paramX500Name, paramBigInteger, paramArrayOfByte));
  }
  
  private KeyAgreeRecipientId(X509CertificateHolderSelector paramX509CertificateHolderSelector)
  {
    super(2);
    this.baseSelector = paramX509CertificateHolderSelector;
  }
  
  public KeyAgreeRecipientId(byte[] paramArrayOfByte)
  {
    this(null, null, paramArrayOfByte);
  }
  
  public Object clone()
  {
    return new KeyAgreeRecipientId(this.baseSelector);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof KeyAgreeRecipientId)) {
      return false;
    }
    KeyAgreeRecipientId localKeyAgreeRecipientId = (KeyAgreeRecipientId)paramObject;
    return this.baseSelector.equals(localKeyAgreeRecipientId.baseSelector);
  }
  
  public BigInteger getSerialNumber()
  {
    return this.baseSelector.getSerialNumber();
  }
  
  public byte[] getSubjectKeyIdentifier()
  {
    return this.baseSelector.getSubjectKeyIdentifier();
  }
  
  public int hashCode()
  {
    return this.baseSelector.hashCode();
  }
  
  public boolean match(Object paramObject)
  {
    if ((paramObject instanceof KeyAgreeRecipientInformation)) {
      return ((KeyAgreeRecipientInformation)paramObject).getRID().equals(this);
    }
    return this.baseSelector.match(paramObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KeyAgreeRecipientId
 * JD-Core Version:    0.7.0.1
 */