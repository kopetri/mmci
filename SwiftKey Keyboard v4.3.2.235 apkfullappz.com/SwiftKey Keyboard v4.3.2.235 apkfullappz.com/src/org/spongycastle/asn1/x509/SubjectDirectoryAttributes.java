package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class SubjectDirectoryAttributes
  extends ASN1Object
{
  private Vector attributes = new Vector();
  
  public SubjectDirectoryAttributes(Vector paramVector)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements()) {
      this.attributes.addElement(localEnumeration.nextElement());
    }
  }
  
  private SubjectDirectoryAttributes(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localEnumeration.nextElement());
      this.attributes.addElement(Attribute.getInstance(localASN1Sequence));
    }
  }
  
  public static SubjectDirectoryAttributes getInstance(Object paramObject)
  {
    if ((paramObject instanceof SubjectDirectoryAttributes)) {
      return (SubjectDirectoryAttributes)paramObject;
    }
    if (paramObject != null) {
      return new SubjectDirectoryAttributes(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public Vector getAttributes()
  {
    return this.attributes;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Enumeration localEnumeration = this.attributes.elements();
    while (localEnumeration.hasMoreElements()) {
      localASN1EncodableVector.add((Attribute)localEnumeration.nextElement());
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.SubjectDirectoryAttributes
 * JD-Core Version:    0.7.0.1
 */