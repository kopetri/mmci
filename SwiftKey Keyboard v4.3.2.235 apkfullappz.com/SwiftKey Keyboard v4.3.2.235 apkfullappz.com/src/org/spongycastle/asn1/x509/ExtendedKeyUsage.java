package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class ExtendedKeyUsage
  extends ASN1Object
{
  ASN1Sequence seq;
  Hashtable usageTable = new Hashtable();
  
  public ExtendedKeyUsage(Vector paramVector)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      ASN1Primitive localASN1Primitive = (ASN1Primitive)localEnumeration.nextElement();
      localASN1EncodableVector.add(localASN1Primitive);
      this.usageTable.put(localASN1Primitive, localASN1Primitive);
    }
    this.seq = new DERSequence(localASN1EncodableVector);
  }
  
  public ExtendedKeyUsage(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if (!(localObject instanceof ASN1ObjectIdentifier)) {
        throw new IllegalArgumentException("Only ASN1ObjectIdentifiers allowed in ExtendedKeyUsage.");
      }
      this.usageTable.put(localObject, localObject);
    }
  }
  
  public ExtendedKeyUsage(KeyPurposeId paramKeyPurposeId)
  {
    this.seq = new DERSequence(paramKeyPurposeId);
    this.usageTable.put(paramKeyPurposeId, paramKeyPurposeId);
  }
  
  public static ExtendedKeyUsage getInstance(Object paramObject)
  {
    if ((paramObject instanceof ExtendedKeyUsage)) {
      return (ExtendedKeyUsage)paramObject;
    }
    if (paramObject != null) {
      return new ExtendedKeyUsage(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static ExtendedKeyUsage getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public Vector getUsages()
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.usageTable.elements();
    while (localEnumeration.hasMoreElements()) {
      localVector.addElement(localEnumeration.nextElement());
    }
    return localVector;
  }
  
  public boolean hasKeyPurposeId(KeyPurposeId paramKeyPurposeId)
  {
    return this.usageTable.get(paramKeyPurposeId) != null;
  }
  
  public int size()
  {
    return this.usageTable.size();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.ExtendedKeyUsage
 * JD-Core Version:    0.7.0.1
 */