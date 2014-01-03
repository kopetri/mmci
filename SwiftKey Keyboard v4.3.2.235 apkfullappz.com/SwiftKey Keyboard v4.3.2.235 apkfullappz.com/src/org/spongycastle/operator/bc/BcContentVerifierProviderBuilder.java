package org.spongycastle.operator.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;
import org.spongycastle.operator.OperatorCreationException;

public abstract class BcContentVerifierProviderBuilder
{
  private BcSignerOutputStream createSignatureStream(AlgorithmIdentifier paramAlgorithmIdentifier, AsymmetricKeyParameter paramAsymmetricKeyParameter)
    throws OperatorCreationException
  {
    Signer localSigner = createSigner(paramAlgorithmIdentifier);
    localSigner.init(false, paramAsymmetricKeyParameter);
    return new BcSignerOutputStream(localSigner);
  }
  
  public ContentVerifierProvider build(final X509CertificateHolder paramX509CertificateHolder)
    throws OperatorCreationException
  {
    new ContentVerifierProvider()
    {
      public ContentVerifier get(AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
        throws OperatorCreationException
      {
        try
        {
          AsymmetricKeyParameter localAsymmetricKeyParameter = BcContentVerifierProviderBuilder.this.extractKeyParameters(paramX509CertificateHolder.getSubjectPublicKeyInfo());
          BcSignerOutputStream localBcSignerOutputStream = BcContentVerifierProviderBuilder.this.createSignatureStream(paramAnonymousAlgorithmIdentifier, localAsymmetricKeyParameter);
          BcContentVerifierProviderBuilder.SigVerifier localSigVerifier = new BcContentVerifierProviderBuilder.SigVerifier(BcContentVerifierProviderBuilder.this, paramAnonymousAlgorithmIdentifier, localBcSignerOutputStream);
          return localSigVerifier;
        }
        catch (IOException localIOException)
        {
          throw new OperatorCreationException("exception on setup: " + localIOException, localIOException);
        }
      }
      
      public X509CertificateHolder getAssociatedCertificate()
      {
        return paramX509CertificateHolder;
      }
      
      public boolean hasAssociatedCertificate()
      {
        return true;
      }
    };
  }
  
  public ContentVerifierProvider build(final AsymmetricKeyParameter paramAsymmetricKeyParameter)
    throws OperatorCreationException
  {
    new ContentVerifierProvider()
    {
      public ContentVerifier get(AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
        throws OperatorCreationException
      {
        BcSignerOutputStream localBcSignerOutputStream = BcContentVerifierProviderBuilder.this.createSignatureStream(paramAnonymousAlgorithmIdentifier, paramAsymmetricKeyParameter);
        return new BcContentVerifierProviderBuilder.SigVerifier(BcContentVerifierProviderBuilder.this, paramAnonymousAlgorithmIdentifier, localBcSignerOutputStream);
      }
      
      public X509CertificateHolder getAssociatedCertificate()
      {
        return null;
      }
      
      public boolean hasAssociatedCertificate()
      {
        return false;
      }
    };
  }
  
  protected abstract Signer createSigner(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws OperatorCreationException;
  
  protected abstract AsymmetricKeyParameter extractKeyParameters(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException;
  
  private class SigVerifier
    implements ContentVerifier
  {
    private AlgorithmIdentifier algorithm;
    private BcSignerOutputStream stream;
    
    SigVerifier(AlgorithmIdentifier paramAlgorithmIdentifier, BcSignerOutputStream paramBcSignerOutputStream)
    {
      this.algorithm = paramAlgorithmIdentifier;
      this.stream = paramBcSignerOutputStream;
    }
    
    public AlgorithmIdentifier getAlgorithmIdentifier()
    {
      return this.algorithm;
    }
    
    public OutputStream getOutputStream()
    {
      if (this.stream == null) {
        throw new IllegalStateException("verifier not initialised");
      }
      return this.stream;
    }
    
    public boolean verify(byte[] paramArrayOfByte)
    {
      return this.stream.verify(paramArrayOfByte);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcContentVerifierProviderBuilder
 * JD-Core Version:    0.7.0.1
 */