package org.spongycastle.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.OperatorStreamException;
import org.spongycastle.operator.RawContentVerifier;
import org.spongycastle.operator.RuntimeOperatorException;

public class JcaContentVerifierProviderBuilder
{
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  
  private Signature createRawSig(AlgorithmIdentifier paramAlgorithmIdentifier, PublicKey paramPublicKey)
  {
    try
    {
      Signature localSignature = this.helper.createRawSignature(paramAlgorithmIdentifier);
      if (localSignature != null) {
        localSignature.initVerify(paramPublicKey);
      }
      return localSignature;
    }
    catch (Exception localException) {}
    return null;
  }
  
  private SignatureOutputStream createSignatureStream(AlgorithmIdentifier paramAlgorithmIdentifier, PublicKey paramPublicKey)
    throws OperatorCreationException
  {
    try
    {
      Signature localSignature = this.helper.createSignature(paramAlgorithmIdentifier);
      localSignature.initVerify(paramPublicKey);
      SignatureOutputStream localSignatureOutputStream = new SignatureOutputStream(localSignature);
      return localSignatureOutputStream;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new OperatorCreationException("exception on setup: " + localGeneralSecurityException, localGeneralSecurityException);
    }
  }
  
  public ContentVerifierProvider build(final PublicKey paramPublicKey)
    throws OperatorCreationException
  {
    new ContentVerifierProvider()
    {
      public ContentVerifier get(AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
        throws OperatorCreationException
      {
        JcaContentVerifierProviderBuilder.SignatureOutputStream localSignatureOutputStream = JcaContentVerifierProviderBuilder.this.createSignatureStream(paramAnonymousAlgorithmIdentifier, paramPublicKey);
        Signature localSignature = JcaContentVerifierProviderBuilder.this.createRawSig(paramAnonymousAlgorithmIdentifier, paramPublicKey);
        if (localSignature != null) {
          return new JcaContentVerifierProviderBuilder.RawSigVerifier(JcaContentVerifierProviderBuilder.this, paramAnonymousAlgorithmIdentifier, localSignatureOutputStream, localSignature);
        }
        return new JcaContentVerifierProviderBuilder.SigVerifier(JcaContentVerifierProviderBuilder.this, paramAnonymousAlgorithmIdentifier, localSignatureOutputStream);
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
  
  public ContentVerifierProvider build(final X509Certificate paramX509Certificate)
    throws OperatorCreationException
  {
    try
    {
      final JcaX509CertificateHolder localJcaX509CertificateHolder = new JcaX509CertificateHolder(paramX509Certificate);
      new ContentVerifierProvider()
      {
        private JcaContentVerifierProviderBuilder.SignatureOutputStream stream;
        
        public ContentVerifier get(AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
          throws OperatorCreationException
        {
          try
          {
            Signature localSignature1 = JcaContentVerifierProviderBuilder.this.helper.createSignature(paramAnonymousAlgorithmIdentifier);
            localSignature1.initVerify(paramX509Certificate.getPublicKey());
            this.stream = new JcaContentVerifierProviderBuilder.SignatureOutputStream(JcaContentVerifierProviderBuilder.this, localSignature1);
            Signature localSignature2 = JcaContentVerifierProviderBuilder.this.createRawSig(paramAnonymousAlgorithmIdentifier, paramX509Certificate.getPublicKey());
            if (localSignature2 != null) {
              return new JcaContentVerifierProviderBuilder.RawSigVerifier(JcaContentVerifierProviderBuilder.this, paramAnonymousAlgorithmIdentifier, this.stream, localSignature2);
            }
          }
          catch (GeneralSecurityException localGeneralSecurityException)
          {
            throw new OperatorCreationException("exception on setup: " + localGeneralSecurityException, localGeneralSecurityException);
          }
          return new JcaContentVerifierProviderBuilder.SigVerifier(JcaContentVerifierProviderBuilder.this, paramAnonymousAlgorithmIdentifier, this.stream);
        }
        
        public X509CertificateHolder getAssociatedCertificate()
        {
          return localJcaX509CertificateHolder;
        }
        
        public boolean hasAssociatedCertificate()
        {
          return true;
        }
      };
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      throw new OperatorCreationException("cannot process certificate: " + localCertificateEncodingException.getMessage(), localCertificateEncodingException);
    }
  }
  
  public ContentVerifierProvider build(X509CertificateHolder paramX509CertificateHolder)
    throws OperatorCreationException, CertificateException
  {
    return build(this.helper.convertCertificate(paramX509CertificateHolder));
  }
  
  public JcaContentVerifierProviderBuilder setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JcaContentVerifierProviderBuilder setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  private class RawSigVerifier
    extends JcaContentVerifierProviderBuilder.SigVerifier
    implements RawContentVerifier
  {
    private Signature rawSignature;
    
    RawSigVerifier(AlgorithmIdentifier paramAlgorithmIdentifier, JcaContentVerifierProviderBuilder.SignatureOutputStream paramSignatureOutputStream, Signature paramSignature)
    {
      super(paramAlgorithmIdentifier, paramSignatureOutputStream);
      this.rawSignature = paramSignature;
    }
    
    public boolean verify(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
      try
      {
        this.rawSignature.update(paramArrayOfByte1);
        boolean bool = this.rawSignature.verify(paramArrayOfByte2);
        return bool;
      }
      catch (SignatureException localSignatureException)
      {
        throw new RuntimeOperatorException("exception obtaining raw signature: " + localSignatureException.getMessage(), localSignatureException);
      }
    }
  }
  
  private class SigVerifier
    implements ContentVerifier
  {
    private AlgorithmIdentifier algorithm;
    private JcaContentVerifierProviderBuilder.SignatureOutputStream stream;
    
    SigVerifier(AlgorithmIdentifier paramAlgorithmIdentifier, JcaContentVerifierProviderBuilder.SignatureOutputStream paramSignatureOutputStream)
    {
      this.algorithm = paramAlgorithmIdentifier;
      this.stream = paramSignatureOutputStream;
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
      try
      {
        boolean bool = this.stream.verify(paramArrayOfByte);
        return bool;
      }
      catch (SignatureException localSignatureException)
      {
        throw new RuntimeOperatorException("exception obtaining signature: " + localSignatureException.getMessage(), localSignatureException);
      }
    }
  }
  
  private class SignatureOutputStream
    extends OutputStream
  {
    private Signature sig;
    
    SignatureOutputStream(Signature paramSignature)
    {
      this.sig = paramSignature;
    }
    
    boolean verify(byte[] paramArrayOfByte)
      throws SignatureException
    {
      return this.sig.verify(paramArrayOfByte);
    }
    
    public void write(int paramInt)
      throws IOException
    {
      try
      {
        this.sig.update((byte)paramInt);
        return;
      }
      catch (SignatureException localSignatureException)
      {
        throw new OperatorStreamException("exception in content signer: " + localSignatureException.getMessage(), localSignatureException);
      }
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        this.sig.update(paramArrayOfByte);
        return;
      }
      catch (SignatureException localSignatureException)
      {
        throw new OperatorStreamException("exception in content signer: " + localSignatureException.getMessage(), localSignatureException);
      }
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      try
      {
        this.sig.update(paramArrayOfByte, paramInt1, paramInt2);
        return;
      }
      catch (SignatureException localSignatureException)
      {
        throw new OperatorStreamException("exception in content signer: " + localSignatureException.getMessage(), localSignatureException);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.JcaContentVerifierProviderBuilder
 * JD-Core Version:    0.7.0.1
 */