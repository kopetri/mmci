package org.spongycastle.cert.selector;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;

public class X509CertificateHolderSelector
  implements Selector
{
  private X500Name issuer;
  private BigInteger serialNumber;
  private byte[] subjectKeyId;
  
  public X509CertificateHolderSelector(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    this(paramX500Name, paramBigInteger, null);
  }
  
  public X509CertificateHolderSelector(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    this.issuer = paramX500Name;
    this.serialNumber = paramBigInteger;
    this.subjectKeyId = paramArrayOfByte;
  }
  
  public X509CertificateHolderSelector(byte[] paramArrayOfByte)
  {
    this(null, null, paramArrayOfByte);
  }
  
  private boolean equalsObj(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 != null) {
      return paramObject1.equals(paramObject2);
    }
    return paramObject2 == null;
  }
  
  public Object clone()
  {
    return new X509CertificateHolderSelector(this.issuer, this.serialNumber, this.subjectKeyId);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof X509CertificateHolderSelector)) {}
    X509CertificateHolderSelector localX509CertificateHolderSelector;
    do
    {
      return false;
      localX509CertificateHolderSelector = (X509CertificateHolderSelector)paramObject;
    } while ((!Arrays.areEqual(this.subjectKeyId, localX509CertificateHolderSelector.subjectKeyId)) || (!equalsObj(this.serialNumber, localX509CertificateHolderSelector.serialNumber)) || (!equalsObj(this.issuer, localX509CertificateHolderSelector.issuer)));
    return true;
  }
  
  public X500Name getIssuer()
  {
    return this.issuer;
  }
  
  public BigInteger getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public byte[] getSubjectKeyIdentifier()
  {
    return Arrays.clone(this.subjectKeyId);
  }
  
  public int hashCode()
  {
    int i = Arrays.hashCode(this.subjectKeyId);
    if (this.serialNumber != null) {
      i ^= this.serialNumber.hashCode();
    }
    if (this.issuer != null) {
      i ^= this.issuer.hashCode();
    }
    return i;
  }
  
  public boolean match(Object paramObject)
  {
    X509CertificateHolder localX509CertificateHolder;
    boolean bool2;
    if ((paramObject instanceof X509CertificateHolder))
    {
      localX509CertificateHolder = (X509CertificateHolder)paramObject;
      if (getSerialNumber() != null)
      {
        IssuerAndSerialNumber localIssuerAndSerialNumber = new IssuerAndSerialNumber(localX509CertificateHolder.toASN1Structure());
        boolean bool3 = localIssuerAndSerialNumber.getName().equals(this.issuer);
        bool2 = false;
        if (bool3)
        {
          boolean bool4 = localIssuerAndSerialNumber.getSerialNumber().getValue().equals(this.serialNumber);
          bool2 = false;
          if (bool4) {
            bool2 = true;
          }
        }
      }
    }
    boolean bool1;
    do
    {
      byte[] arrayOfByte1;
      do
      {
        return bool2;
        arrayOfByte1 = this.subjectKeyId;
        bool2 = false;
      } while (arrayOfByte1 == null);
      Extension localExtension = localX509CertificateHolder.getExtension(Extension.subjectKeyIdentifier);
      if (localExtension == null) {
        return Arrays.areEqual(this.subjectKeyId, MSOutlookKeyIdCalculator.calculateKeyId(localX509CertificateHolder.getSubjectPublicKeyInfo()));
      }
      byte[] arrayOfByte2 = ASN1OctetString.getInstance(localExtension.getParsedValue()).getOctets();
      return Arrays.areEqual(this.subjectKeyId, arrayOfByte2);
      bool1 = paramObject instanceof byte[];
      bool2 = false;
    } while (!bool1);
    return Arrays.areEqual(this.subjectKeyId, (byte[])paramObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.X509CertificateHolderSelector
 * JD-Core Version:    0.7.0.1
 */