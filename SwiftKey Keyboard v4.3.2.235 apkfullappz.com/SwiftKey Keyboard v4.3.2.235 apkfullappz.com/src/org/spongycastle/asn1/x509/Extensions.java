package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class Extensions
  extends ASN1Object
{
  private Hashtable extensions = new Hashtable();
  private Vector ordering = new Vector();
  
  private Extensions(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    if (localEnumeration.hasMoreElements())
    {
      ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localEnumeration.nextElement());
      if (localASN1Sequence.size() == 3) {
        this.extensions.put(localASN1Sequence.getObjectAt(0), new Extension(ASN1ObjectIdentifier.getInstance(localASN1Sequence.getObjectAt(0)), ASN1Boolean.getInstance(localASN1Sequence.getObjectAt(1)), ASN1OctetString.getInstance(localASN1Sequence.getObjectAt(2))));
      }
      for (;;)
      {
        this.ordering.addElement(localASN1Sequence.getObjectAt(0));
        break;
        if (localASN1Sequence.size() != 2) {
          break label165;
        }
        this.extensions.put(localASN1Sequence.getObjectAt(0), new Extension(ASN1ObjectIdentifier.getInstance(localASN1Sequence.getObjectAt(0)), false, ASN1OctetString.getInstance(localASN1Sequence.getObjectAt(1))));
      }
      label165:
      throw new IllegalArgumentException("Bad sequence size: " + localASN1Sequence.size());
    }
  }
  
  public Extensions(Extension[] paramArrayOfExtension)
  {
    for (int i = 0; i != paramArrayOfExtension.length; i++)
    {
      Extension localExtension = paramArrayOfExtension[i];
      this.ordering.addElement(localExtension.getExtnId());
      this.extensions.put(localExtension.getExtnId(), localExtension);
    }
  }
  
  private ASN1ObjectIdentifier[] getExtensionOIDs(boolean paramBoolean)
  {
    Vector localVector = new Vector();
    for (int i = 0; i != this.ordering.size(); i++)
    {
      Object localObject = this.ordering.elementAt(i);
      if (((Extension)this.extensions.get(localObject)).isCritical() == paramBoolean) {
        localVector.addElement(localObject);
      }
    }
    return toOidArray(localVector);
  }
  
  public static Extensions getInstance(Object paramObject)
  {
    if ((paramObject instanceof Extensions)) {
      return (Extensions)paramObject;
    }
    if (paramObject != null) {
      return new Extensions(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static Extensions getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  private ASN1ObjectIdentifier[] toOidArray(Vector paramVector)
  {
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[paramVector.size()];
    for (int i = 0; i != arrayOfASN1ObjectIdentifier.length; i++) {
      arrayOfASN1ObjectIdentifier[i] = ((ASN1ObjectIdentifier)paramVector.elementAt(i));
    }
    return arrayOfASN1ObjectIdentifier;
  }
  
  public boolean equivalent(Extensions paramExtensions)
  {
    if (this.extensions.size() != paramExtensions.extensions.size()) {
      return false;
    }
    Enumeration localEnumeration = this.extensions.keys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if (!this.extensions.get(localObject).equals(paramExtensions.extensions.get(localObject))) {
        return false;
      }
    }
    return true;
  }
  
  public ASN1ObjectIdentifier[] getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return (Extension)this.extensions.get(paramASN1ObjectIdentifier);
  }
  
  public ASN1ObjectIdentifier[] getExtensionOIDs()
  {
    return toOidArray(this.ordering);
  }
  
  public ASN1ObjectIdentifier[] getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  public Enumeration oids()
  {
    return this.ordering.elements();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    Enumeration localEnumeration = this.ordering.elements();
    while (localEnumeration.hasMoreElements())
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localEnumeration.nextElement();
      Extension localExtension = (Extension)this.extensions.get(localASN1ObjectIdentifier);
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      localASN1EncodableVector2.add(localASN1ObjectIdentifier);
      if (localExtension.isCritical()) {
        localASN1EncodableVector2.add(ASN1Boolean.getInstance(true));
      }
      localASN1EncodableVector2.add(localExtension.getExtnValue());
      localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
    }
    return new DERSequence(localASN1EncodableVector1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.Extensions
 * JD-Core Version:    0.7.0.1
 */