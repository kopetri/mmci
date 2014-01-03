package org.spongycastle.cms;

import java.math.BigInteger;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cert.selector.X509CertificateHolderSelector;
import org.spongycastle.util.Selector;

public class SignerId
  implements Selector
{
  private X509CertificateHolderSelector baseSelector;
  
  public SignerId(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    this(paramX500Name, paramBigInteger, null);
  }
  
  public SignerId(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    this(new X509CertificateHolderSelector(paramX500Name, paramBigInteger, paramArrayOfByte));
  }
  
  private SignerId(X509CertificateHolderSelector paramX509CertificateHolderSelector)
  {
    this.baseSelector = paramX509CertificateHolderSelector;
  }
  
  public SignerId(byte[] paramArrayOfByte)
  {
    this(null, null, paramArrayOfByte);
  }
  
  public Object clone()
  {
    return new SignerId(this.baseSelector);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof SignerId)) {
      return false;
    }
    SignerId localSignerId = (SignerId)paramObject;
    return this.baseSelector.equals(localSignerId.baseSelector);
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
    if ((paramObject instanceof SignerInformation)) {
      return ((SignerInformation)paramObject).getSID().equals(this);
    }
    return this.baseSelector.match(paramObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SignerId
 * JD-Core Version:    0.7.0.1
 */