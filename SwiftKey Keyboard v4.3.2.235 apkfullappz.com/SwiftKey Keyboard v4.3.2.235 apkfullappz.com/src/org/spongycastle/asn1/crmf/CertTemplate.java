package org.spongycastle.asn1.crmf;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class CertTemplate
  extends ASN1Object
{
  private Extensions extensions;
  private X500Name issuer;
  private DERBitString issuerUID;
  private SubjectPublicKeyInfo publicKey;
  private ASN1Sequence seq;
  private ASN1Integer serialNumber;
  private AlgorithmIdentifier signingAlg;
  private X500Name subject;
  private DERBitString subjectUID;
  private OptionalValidity validity;
  private ASN1Integer version;
  
  private CertTemplate(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localEnumeration.nextElement();
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("unknown tag: " + localASN1TaggedObject.getTagNo());
      case 0: 
        this.version = ASN1Integer.getInstance(localASN1TaggedObject, false);
        break;
      case 1: 
        this.serialNumber = ASN1Integer.getInstance(localASN1TaggedObject, false);
        break;
      case 2: 
        this.signingAlg = AlgorithmIdentifier.getInstance(localASN1TaggedObject, false);
        break;
      case 3: 
        this.issuer = X500Name.getInstance(localASN1TaggedObject, true);
        break;
      case 4: 
        this.validity = OptionalValidity.getInstance(ASN1Sequence.getInstance(localASN1TaggedObject, false));
        break;
      case 5: 
        this.subject = X500Name.getInstance(localASN1TaggedObject, true);
        break;
      case 6: 
        this.publicKey = SubjectPublicKeyInfo.getInstance(localASN1TaggedObject, false);
        break;
      case 7: 
        this.issuerUID = DERBitString.getInstance(localASN1TaggedObject, false);
        break;
      case 8: 
        this.subjectUID = DERBitString.getInstance(localASN1TaggedObject, false);
        break;
      case 9: 
        this.extensions = Extensions.getInstance(localASN1TaggedObject, false);
      }
    }
  }
  
  public static CertTemplate getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertTemplate)) {
      return (CertTemplate)paramObject;
    }
    if (paramObject != null) {
      return new CertTemplate(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public Extensions getExtensions()
  {
    return this.extensions;
  }
  
  public X500Name getIssuer()
  {
    return this.issuer;
  }
  
  public DERBitString getIssuerUID()
  {
    return this.issuerUID;
  }
  
  public SubjectPublicKeyInfo getPublicKey()
  {
    return this.publicKey;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public AlgorithmIdentifier getSigningAlg()
  {
    return this.signingAlg;
  }
  
  public X500Name getSubject()
  {
    return this.subject;
  }
  
  public DERBitString getSubjectUID()
  {
    return this.subjectUID;
  }
  
  public OptionalValidity getValidity()
  {
    return this.validity;
  }
  
  public int getVersion()
  {
    return this.version.getValue().intValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.CertTemplate
 * JD-Core Version:    0.7.0.1
 */