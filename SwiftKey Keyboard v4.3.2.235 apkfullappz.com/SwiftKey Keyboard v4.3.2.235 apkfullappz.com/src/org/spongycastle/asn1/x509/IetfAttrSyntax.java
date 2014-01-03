package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.DERUTF8String;

public class IetfAttrSyntax
  extends ASN1Object
{
  public static final int VALUE_OCTETS = 1;
  public static final int VALUE_OID = 2;
  public static final int VALUE_UTF8 = 3;
  GeneralNames policyAuthority = null;
  int valueChoice = -1;
  Vector values = new Vector();
  
  private IetfAttrSyntax(ASN1Sequence paramASN1Sequence)
  {
    int j;
    if ((paramASN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject))
    {
      this.policyAuthority = GeneralNames.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), false);
      j = 0 + 1;
    }
    while (!(paramASN1Sequence.getObjectAt(j) instanceof ASN1Sequence))
    {
      throw new IllegalArgumentException("Non-IetfAttrSyntax encoding");
      int i = paramASN1Sequence.size();
      j = 0;
      if (i == 2)
      {
        this.policyAuthority = GeneralNames.getInstance(paramASN1Sequence.getObjectAt(0));
        j = 0 + 1;
      }
    }
    Enumeration localEnumeration = ((ASN1Sequence)paramASN1Sequence.getObjectAt(j)).getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1Primitive localASN1Primitive = (ASN1Primitive)localEnumeration.nextElement();
      int k;
      if ((localASN1Primitive instanceof ASN1ObjectIdentifier)) {
        k = 2;
      }
      for (;;)
      {
        if (this.valueChoice < 0) {
          this.valueChoice = k;
        }
        if (k == this.valueChoice) {
          break label224;
        }
        throw new IllegalArgumentException("Mix of value types in IetfAttrSyntax");
        if ((localASN1Primitive instanceof DERUTF8String))
        {
          k = 3;
        }
        else
        {
          if (!(localASN1Primitive instanceof DEROctetString)) {
            break;
          }
          k = 1;
        }
      }
      throw new IllegalArgumentException("Bad value type encoding IetfAttrSyntax");
      label224:
      this.values.addElement(localASN1Primitive);
    }
  }
  
  public static IetfAttrSyntax getInstance(Object paramObject)
  {
    if ((paramObject instanceof IetfAttrSyntax)) {
      return (IetfAttrSyntax)paramObject;
    }
    if (paramObject != null) {
      return new IetfAttrSyntax(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public GeneralNames getPolicyAuthority()
  {
    return this.policyAuthority;
  }
  
  public int getValueType()
  {
    return this.valueChoice;
  }
  
  public Object[] getValues()
  {
    if (getValueType() == 1)
    {
      localObject = new ASN1OctetString[this.values.size()];
      for (int k = 0; k != localObject.length; k++) {
        localObject[k] = ((ASN1OctetString)this.values.elementAt(k));
      }
    }
    if (getValueType() == 2)
    {
      localObject = new ASN1ObjectIdentifier[this.values.size()];
      for (int j = 0; j != localObject.length; j++) {
        localObject[j] = ((ASN1ObjectIdentifier)this.values.elementAt(j));
      }
    }
    Object localObject = new DERUTF8String[this.values.size()];
    for (int i = 0; i != localObject.length; i++) {
      localObject[i] = ((DERUTF8String)this.values.elementAt(i));
    }
    return localObject;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    if (this.policyAuthority != null) {
      localASN1EncodableVector1.add(new DERTaggedObject(0, this.policyAuthority));
    }
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    Enumeration localEnumeration = this.values.elements();
    while (localEnumeration.hasMoreElements()) {
      localASN1EncodableVector2.add((ASN1Encodable)localEnumeration.nextElement());
    }
    localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
    return new DERSequence(localASN1EncodableVector1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.IetfAttrSyntax
 * JD-Core Version:    0.7.0.1
 */