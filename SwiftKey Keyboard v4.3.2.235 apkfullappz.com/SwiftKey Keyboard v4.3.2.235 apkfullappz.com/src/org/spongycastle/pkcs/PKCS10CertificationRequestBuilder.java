package org.spongycastle.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.pkcs.Attribute;
import org.spongycastle.asn1.pkcs.CertificationRequest;
import org.spongycastle.asn1.pkcs.CertificationRequestInfo;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.ContentSigner;

public class PKCS10CertificationRequestBuilder
{
  private List attributes = new ArrayList();
  private SubjectPublicKeyInfo publicKeyInfo;
  private X500Name subject;
  
  public PKCS10CertificationRequestBuilder(X500Name paramX500Name, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.subject = paramX500Name;
    this.publicKeyInfo = paramSubjectPublicKeyInfo;
  }
  
  public PKCS10CertificationRequestBuilder addAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.attributes.add(new Attribute(paramASN1ObjectIdentifier, new DERSet(paramASN1Encodable)));
    return this;
  }
  
  public PKCS10CertificationRequestBuilder addAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable[] paramArrayOfASN1Encodable)
  {
    this.attributes.add(new Attribute(paramASN1ObjectIdentifier, new DERSet(paramArrayOfASN1Encodable)));
    return this;
  }
  
  public PKCS10CertificationRequest build(ContentSigner paramContentSigner)
  {
    if (this.attributes.isEmpty()) {}
    for (CertificationRequestInfo localCertificationRequestInfo = new CertificationRequestInfo(this.subject, this.publicKeyInfo, null);; localCertificationRequestInfo = new CertificationRequestInfo(this.subject, this.publicKeyInfo, new DERSet(localASN1EncodableVector)))
    {
      try
      {
        OutputStream localOutputStream = paramContentSigner.getOutputStream();
        localOutputStream.write(localCertificationRequestInfo.getEncoded("DER"));
        localOutputStream.close();
        PKCS10CertificationRequest localPKCS10CertificationRequest = new PKCS10CertificationRequest(new CertificationRequest(localCertificationRequestInfo, paramContentSigner.getAlgorithmIdentifier(), new DERBitString(paramContentSigner.getSignature())));
        return localPKCS10CertificationRequest;
      }
      catch (IOException localIOException)
      {
        ASN1EncodableVector localASN1EncodableVector;
        Iterator localIterator;
        throw new IllegalStateException("cannot produce certification request signature");
      }
      localASN1EncodableVector = new ASN1EncodableVector();
      localIterator = this.attributes.iterator();
      while (localIterator.hasNext()) {
        localASN1EncodableVector.add(Attribute.getInstance(localIterator.next()));
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS10CertificationRequestBuilder
 * JD-Core Version:    0.7.0.1
 */