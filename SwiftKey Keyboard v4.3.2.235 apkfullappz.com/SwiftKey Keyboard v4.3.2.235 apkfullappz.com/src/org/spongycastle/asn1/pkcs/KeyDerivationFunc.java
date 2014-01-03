package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KeyDerivationFunc
  extends AlgorithmIdentifier
{
  public KeyDerivationFunc(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    super(paramASN1ObjectIdentifier, paramASN1Encodable);
  }
  
  KeyDerivationFunc(ASN1Sequence paramASN1Sequence)
  {
    super(paramASN1Sequence);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.KeyDerivationFunc
 * JD-Core Version:    0.7.0.1
 */