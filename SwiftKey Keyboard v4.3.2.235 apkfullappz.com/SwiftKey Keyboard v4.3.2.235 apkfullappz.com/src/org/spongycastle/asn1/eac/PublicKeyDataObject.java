package org.spongycastle.asn1.eac;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;

public abstract class PublicKeyDataObject
  extends ASN1Object
{
  public static PublicKeyDataObject getInstance(Object paramObject)
  {
    if ((paramObject instanceof PublicKeyDataObject)) {
      return (PublicKeyDataObject)paramObject;
    }
    if (paramObject != null)
    {
      ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(paramObject);
      if (ASN1ObjectIdentifier.getInstance(localASN1Sequence.getObjectAt(0)).on(EACObjectIdentifiers.id_TA_ECDSA)) {
        return new ECDSAPublicKey(localASN1Sequence);
      }
      return new RSAPublicKey(localASN1Sequence);
    }
    return null;
  }
  
  public abstract ASN1ObjectIdentifier getUsage();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.PublicKeyDataObject
 * JD-Core Version:    0.7.0.1
 */