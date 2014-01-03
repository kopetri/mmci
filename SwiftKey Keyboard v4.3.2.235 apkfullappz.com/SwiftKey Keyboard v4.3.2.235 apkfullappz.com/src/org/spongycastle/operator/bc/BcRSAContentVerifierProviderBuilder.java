package org.spongycastle.operator.bc;

import java.io.IOException;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.signers.RSADigestSigner;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.operator.DigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.OperatorCreationException;

public class BcRSAContentVerifierProviderBuilder
  extends BcContentVerifierProviderBuilder
{
  private DigestAlgorithmIdentifierFinder digestAlgorithmFinder;
  
  public BcRSAContentVerifierProviderBuilder(DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder)
  {
    this.digestAlgorithmFinder = paramDigestAlgorithmIdentifierFinder;
  }
  
  protected Signer createSigner(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws OperatorCreationException
  {
    return new RSADigestSigner(BcUtil.createDigest(this.digestAlgorithmFinder.find(paramAlgorithmIdentifier)));
  }
  
  protected AsymmetricKeyParameter extractKeyParameters(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    return PublicKeyFactory.createKey(paramSubjectPublicKeyInfo);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcRSAContentVerifierProviderBuilder
 * JD-Core Version:    0.7.0.1
 */