package org.spongycastle.cms;

import java.math.BigInteger;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cert.selector.X509CertificateHolderSelector;

public class KeyTransRecipientId
  extends RecipientId
{
  private X509CertificateHolderSelector baseSelector;
  
  public KeyTransRecipientId(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    this(paramX500Name, paramBigInteger, null);
  }
  
  public KeyTransRecipientId(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    this(new X509CertificateHolderSelector(paramX500Name, paramBigInteger, paramArrayOfByte));
  }
  
  private KeyTransRecipientId(X509CertificateHolderSelector paramX509CertificateHolderSelector)
  {
    super(0);
    this.baseSelector = paramX509CertificateHolderSelector;
  }
  
  public KeyTransRecipientId(byte[] paramArrayOfByte)
  {
    this(null, null, paramArrayOfByte);
  }
  
  public Object clone()
  {
    return new KeyTransRecipientId(this.baseSelector);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof KeyTransRecipientId)) {
      return false;
    }
    KeyTransRecipientId localKeyTransRecipientId = (KeyTransRecipientId)paramObject;
    return this.baseSelector.equals(localKeyTransRecipientId.baseSelector);
  }
  
  public X500Name getIssuer()
  {
    return this.baseSelector.getIssuer();
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
    if ((paramObject instanceof KeyTransRecipientInformation)) {
      return ((KeyTransRecipientInformation)paramObject).getRID().equals(this);
    }
    return this.baseSelector.match(paramObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KeyTransRecipientId
 * JD-Core Version:    0.7.0.1
 */