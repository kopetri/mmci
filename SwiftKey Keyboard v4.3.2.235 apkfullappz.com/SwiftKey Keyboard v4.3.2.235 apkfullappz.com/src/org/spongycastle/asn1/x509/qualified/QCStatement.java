package org.spongycastle.asn1.x509.qualified;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class QCStatement
  extends ASN1Object
  implements ETSIQCObjectIdentifiers, RFC3739QCObjectIdentifiers
{
  ASN1ObjectIdentifier qcStatementId;
  ASN1Encodable qcStatementInfo;
  
  public QCStatement(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.qcStatementId = paramASN1ObjectIdentifier;
    this.qcStatementInfo = null;
  }
  
  public QCStatement(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.qcStatementId = paramASN1ObjectIdentifier;
    this.qcStatementInfo = paramASN1Encodable;
  }
  
  private QCStatement(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.qcStatementId = ASN1ObjectIdentifier.getInstance(localEnumeration.nextElement());
    if (localEnumeration.hasMoreElements()) {
      this.qcStatementInfo = ((ASN1Encodable)localEnumeration.nextElement());
    }
  }
  
  public static QCStatement getInstance(Object paramObject)
  {
    if ((paramObject instanceof QCStatement)) {
      return (QCStatement)paramObject;
    }
    if (paramObject != null) {
      return new QCStatement(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getStatementId()
  {
    return this.qcStatementId;
  }
  
  public ASN1Encodable getStatementInfo()
  {
    return this.qcStatementInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.qcStatementId);
    if (this.qcStatementInfo != null) {
      localASN1EncodableVector.add(this.qcStatementInfo);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.QCStatement
 * JD-Core Version:    0.7.0.1
 */