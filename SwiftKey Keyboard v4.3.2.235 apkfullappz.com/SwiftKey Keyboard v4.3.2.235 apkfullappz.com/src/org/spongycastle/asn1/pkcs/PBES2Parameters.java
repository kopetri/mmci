package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PBES2Parameters
  extends ASN1Object
  implements PKCSObjectIdentifiers
{
  private KeyDerivationFunc func;
  private EncryptionScheme scheme;
  
  public PBES2Parameters(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive());
    if (localASN1Sequence.getObjectAt(0).equals(id_PBKDF2)) {}
    for (this.func = new KeyDerivationFunc(id_PBKDF2, PBKDF2Params.getInstance(localASN1Sequence.getObjectAt(1)));; this.func = new KeyDerivationFunc(localASN1Sequence))
    {
      this.scheme = ((EncryptionScheme)EncryptionScheme.getInstance(localEnumeration.nextElement()));
      return;
    }
  }
  
  public static PBES2Parameters getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof PBES2Parameters))) {
      return (PBES2Parameters)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new PBES2Parameters((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public EncryptionScheme getEncryptionScheme()
  {
    return this.scheme;
  }
  
  public KeyDerivationFunc getKeyDerivationFunc()
  {
    return this.func;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.func);
    localASN1EncodableVector.add(this.scheme);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.PBES2Parameters
 * JD-Core Version:    0.7.0.1
 */