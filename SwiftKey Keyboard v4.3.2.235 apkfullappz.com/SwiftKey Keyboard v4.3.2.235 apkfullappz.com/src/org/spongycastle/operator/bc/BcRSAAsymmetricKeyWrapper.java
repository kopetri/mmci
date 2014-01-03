package org.spongycastle.operator.bc;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.util.PublicKeyFactory;

public class BcRSAAsymmetricKeyWrapper
  extends BcAsymmetricKeyWrapper
{
  public BcRSAAsymmetricKeyWrapper(AlgorithmIdentifier paramAlgorithmIdentifier, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    super(paramAlgorithmIdentifier, PublicKeyFactory.createKey(paramSubjectPublicKeyInfo));
  }
  
  public BcRSAAsymmetricKeyWrapper(AlgorithmIdentifier paramAlgorithmIdentifier, AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    super(paramAlgorithmIdentifier, paramAsymmetricKeyParameter);
  }
  
  protected AsymmetricBlockCipher createAsymmetricWrapper(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return new PKCS1Encoding(new RSAEngine());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcRSAAsymmetricKeyWrapper
 * JD-Core Version:    0.7.0.1
 */