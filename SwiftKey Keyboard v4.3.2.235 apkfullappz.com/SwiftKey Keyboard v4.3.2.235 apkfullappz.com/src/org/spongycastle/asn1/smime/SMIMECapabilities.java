package org.spongycastle.asn1.smime;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapabilities
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier canNotDecryptAny;
  public static final ASN1ObjectIdentifier dES_CBC = new ASN1ObjectIdentifier("1.3.14.3.2.7");
  public static final ASN1ObjectIdentifier dES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
  public static final ASN1ObjectIdentifier preferSignedData = PKCSObjectIdentifiers.preferSignedData;
  public static final ASN1ObjectIdentifier rC2_CBC = PKCSObjectIdentifiers.RC2_CBC;
  public static final ASN1ObjectIdentifier sMIMECapabilitesVersions;
  private ASN1Sequence capabilities;
  
  static
  {
    canNotDecryptAny = PKCSObjectIdentifiers.canNotDecryptAny;
    sMIMECapabilitesVersions = PKCSObjectIdentifiers.sMIMECapabilitiesVersions;
  }
  
  public SMIMECapabilities(ASN1Sequence paramASN1Sequence)
  {
    this.capabilities = paramASN1Sequence;
  }
  
  public static SMIMECapabilities getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof SMIMECapabilities))) {
      return (SMIMECapabilities)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new SMIMECapabilities((ASN1Sequence)paramObject);
    }
    if ((paramObject instanceof Attribute)) {
      return new SMIMECapabilities((ASN1Sequence)((Attribute)paramObject).getAttrValues().getObjectAt(0));
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public Vector getCapabilities(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    Enumeration localEnumeration = this.capabilities.getObjects();
    Vector localVector = new Vector();
    if (paramASN1ObjectIdentifier == null) {
      while (localEnumeration.hasMoreElements()) {
        localVector.addElement(SMIMECapability.getInstance(localEnumeration.nextElement()));
      }
    }
    while (localEnumeration.hasMoreElements())
    {
      SMIMECapability localSMIMECapability = SMIMECapability.getInstance(localEnumeration.nextElement());
      if (paramASN1ObjectIdentifier.equals(localSMIMECapability.getCapabilityID())) {
        localVector.addElement(localSMIMECapability);
      }
    }
    return localVector;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.capabilities;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.smime.SMIMECapabilities
 * JD-Core Version:    0.7.0.1
 */