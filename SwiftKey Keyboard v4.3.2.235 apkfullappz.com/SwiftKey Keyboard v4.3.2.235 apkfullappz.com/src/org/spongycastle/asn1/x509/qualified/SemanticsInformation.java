package org.spongycastle.asn1.x509.qualified;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class SemanticsInformation
  extends ASN1Object
{
  private GeneralName[] nameRegistrationAuthorities;
  private ASN1ObjectIdentifier semanticsIdentifier;
  
  public SemanticsInformation(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.semanticsIdentifier = paramASN1ObjectIdentifier;
    this.nameRegistrationAuthorities = null;
  }
  
  public SemanticsInformation(ASN1ObjectIdentifier paramASN1ObjectIdentifier, GeneralName[] paramArrayOfGeneralName)
  {
    this.semanticsIdentifier = paramASN1ObjectIdentifier;
    this.nameRegistrationAuthorities = paramArrayOfGeneralName;
  }
  
  private SemanticsInformation(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    if (paramASN1Sequence.size() <= 0) {
      throw new IllegalArgumentException("no objects in SemanticsInformation");
    }
    Object localObject = localEnumeration.nextElement();
    if ((localObject instanceof ASN1ObjectIdentifier))
    {
      this.semanticsIdentifier = ASN1ObjectIdentifier.getInstance(localObject);
      if (!localEnumeration.hasMoreElements()) {
        break label122;
      }
    }
    label122:
    for (localObject = localEnumeration.nextElement(); localObject != null; localObject = null)
    {
      ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localObject);
      this.nameRegistrationAuthorities = new GeneralName[localASN1Sequence.size()];
      for (int i = 0; i < localASN1Sequence.size(); i++) {
        this.nameRegistrationAuthorities[i] = GeneralName.getInstance(localASN1Sequence.getObjectAt(i));
      }
    }
  }
  
  public SemanticsInformation(GeneralName[] paramArrayOfGeneralName)
  {
    this.semanticsIdentifier = null;
    this.nameRegistrationAuthorities = paramArrayOfGeneralName;
  }
  
  public static SemanticsInformation getInstance(Object paramObject)
  {
    if ((paramObject instanceof SemanticsInformation)) {
      return (SemanticsInformation)paramObject;
    }
    if (paramObject != null) {
      return new SemanticsInformation(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public GeneralName[] getNameRegistrationAuthorities()
  {
    return this.nameRegistrationAuthorities;
  }
  
  public ASN1ObjectIdentifier getSemanticsIdentifier()
  {
    return this.semanticsIdentifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    if (this.semanticsIdentifier != null) {
      localASN1EncodableVector1.add(this.semanticsIdentifier);
    }
    if (this.nameRegistrationAuthorities != null)
    {
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      for (int i = 0; i < this.nameRegistrationAuthorities.length; i++) {
        localASN1EncodableVector2.add(this.nameRegistrationAuthorities[i]);
      }
      localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
    }
    return new DERSequence(localASN1EncodableVector1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.SemanticsInformation
 * JD-Core Version:    0.7.0.1
 */