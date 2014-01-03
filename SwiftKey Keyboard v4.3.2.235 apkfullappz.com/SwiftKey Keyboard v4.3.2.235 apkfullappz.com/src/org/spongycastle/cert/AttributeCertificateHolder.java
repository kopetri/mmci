package org.spongycastle.cert;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.Holder;
import org.spongycastle.asn1.x509.IssuerSerial;
import org.spongycastle.asn1.x509.ObjectDigestInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;

public class AttributeCertificateHolder
  implements Selector
{
  private static DigestCalculatorProvider digestCalculatorProvider;
  final Holder holder;
  
  public AttributeCertificateHolder(int paramInt, ASN1ObjectIdentifier paramASN1ObjectIdentifier1, ASN1ObjectIdentifier paramASN1ObjectIdentifier2, byte[] paramArrayOfByte)
  {
    this.holder = new Holder(new ObjectDigestInfo(paramInt, paramASN1ObjectIdentifier2, new AlgorithmIdentifier(paramASN1ObjectIdentifier1), Arrays.clone(paramArrayOfByte)));
  }
  
  AttributeCertificateHolder(ASN1Sequence paramASN1Sequence)
  {
    this.holder = Holder.getInstance(paramASN1Sequence);
  }
  
  public AttributeCertificateHolder(X500Name paramX500Name)
  {
    this.holder = new Holder(generateGeneralNames(paramX500Name));
  }
  
  public AttributeCertificateHolder(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    this.holder = new Holder(new IssuerSerial(new GeneralNames(new GeneralName(paramX500Name)), new ASN1Integer(paramBigInteger)));
  }
  
  public AttributeCertificateHolder(X509CertificateHolder paramX509CertificateHolder)
  {
    this.holder = new Holder(new IssuerSerial(generateGeneralNames(paramX509CertificateHolder.getIssuer()), new ASN1Integer(paramX509CertificateHolder.getSerialNumber())));
  }
  
  private GeneralNames generateGeneralNames(X500Name paramX500Name)
  {
    return new GeneralNames(new GeneralName(paramX500Name));
  }
  
  private X500Name[] getPrincipals(GeneralName[] paramArrayOfGeneralName)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfGeneralName.length);
    for (int i = 0; i != paramArrayOfGeneralName.length; i++) {
      if (paramArrayOfGeneralName[i].getTagNo() == 4) {
        localArrayList.add(X500Name.getInstance(paramArrayOfGeneralName[i].getName()));
      }
    }
    return (X500Name[])localArrayList.toArray(new X500Name[localArrayList.size()]);
  }
  
  private boolean matchesDN(X500Name paramX500Name, GeneralNames paramGeneralNames)
  {
    GeneralName[] arrayOfGeneralName = paramGeneralNames.getNames();
    for (int i = 0; i != arrayOfGeneralName.length; i++)
    {
      GeneralName localGeneralName = arrayOfGeneralName[i];
      if ((localGeneralName.getTagNo() == 4) && (X500Name.getInstance(localGeneralName.getName()).equals(paramX500Name))) {
        return true;
      }
    }
    return false;
  }
  
  public static void setDigestCalculatorProvider(DigestCalculatorProvider paramDigestCalculatorProvider)
  {
    digestCalculatorProvider = paramDigestCalculatorProvider;
  }
  
  public Object clone()
  {
    return new AttributeCertificateHolder((ASN1Sequence)this.holder.toASN1Object());
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AttributeCertificateHolder)) {
      return false;
    }
    AttributeCertificateHolder localAttributeCertificateHolder = (AttributeCertificateHolder)paramObject;
    return this.holder.equals(localAttributeCertificateHolder.holder);
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      return this.holder.getObjectDigestInfo().getDigestAlgorithm();
    }
    return null;
  }
  
  public int getDigestedObjectType()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      return this.holder.getObjectDigestInfo().getDigestedObjectType().getValue().intValue();
    }
    return -1;
  }
  
  public X500Name[] getEntityNames()
  {
    if (this.holder.getEntityName() != null) {
      return getPrincipals(this.holder.getEntityName().getNames());
    }
    return null;
  }
  
  public X500Name[] getIssuer()
  {
    if (this.holder.getBaseCertificateID() != null) {
      return getPrincipals(this.holder.getBaseCertificateID().getIssuer().getNames());
    }
    return null;
  }
  
  public byte[] getObjectDigest()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      return this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getOtherObjectTypeID()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      new ASN1ObjectIdentifier(this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId());
    }
    return null;
  }
  
  public BigInteger getSerialNumber()
  {
    if (this.holder.getBaseCertificateID() != null) {
      return this.holder.getBaseCertificateID().getSerial().getValue();
    }
    return null;
  }
  
  public int hashCode()
  {
    return this.holder.hashCode();
  }
  
  public boolean match(Object paramObject)
  {
    if (!(paramObject instanceof X509CertificateHolder)) {}
    for (;;)
    {
      return false;
      X509CertificateHolder localX509CertificateHolder = (X509CertificateHolder)paramObject;
      if (this.holder.getBaseCertificateID() != null)
      {
        if ((!this.holder.getBaseCertificateID().getSerial().getValue().equals(localX509CertificateHolder.getSerialNumber())) || (!matchesDN(localX509CertificateHolder.getIssuer(), this.holder.getBaseCertificateID().getIssuer()))) {
          continue;
        }
        return true;
      }
      if ((this.holder.getEntityName() != null) && (matchesDN(localX509CertificateHolder.getSubject(), this.holder.getEntityName()))) {
        return true;
      }
      if (this.holder.getObjectDigestInfo() == null) {
        continue;
      }
      try
      {
        DigestCalculator localDigestCalculator = digestCalculatorProvider.get(this.holder.getObjectDigestInfo().getDigestAlgorithm());
        OutputStream localOutputStream = localDigestCalculator.getOutputStream();
        switch (getDigestedObjectType())
        {
        }
        for (;;)
        {
          localOutputStream.close();
          if (Arrays.areEqual(localDigestCalculator.getDigest(), getObjectDigest())) {
            break;
          }
          return false;
          localOutputStream.write(localX509CertificateHolder.getSubjectPublicKeyInfo().getEncoded());
          continue;
          localOutputStream.write(localX509CertificateHolder.getEncoded());
        }
        return false;
      }
      catch (Exception localException) {}
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.AttributeCertificateHolder
 * JD-Core Version:    0.7.0.1
 */