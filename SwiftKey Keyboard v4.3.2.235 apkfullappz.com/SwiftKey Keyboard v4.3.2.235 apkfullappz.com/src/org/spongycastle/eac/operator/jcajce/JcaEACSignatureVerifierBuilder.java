package org.spongycastle.eac.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.eac.EACObjectIdentifiers;
import org.spongycastle.eac.operator.EACSignatureVerifier;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.OperatorStreamException;
import org.spongycastle.operator.RuntimeOperatorException;

public class JcaEACSignatureVerifierBuilder
{
  private EACHelper helper = new DefaultEACHelper();
  
  private static byte[] derEncode(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = paramArrayOfByte.length / 2;
    byte[] arrayOfByte1 = new byte[i];
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, i);
    System.arraycopy(paramArrayOfByte, i, arrayOfByte2, 0, i);
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new DERInteger(new BigInteger(1, arrayOfByte1)));
    localASN1EncodableVector.add(new DERInteger(new BigInteger(1, arrayOfByte2)));
    return new DERSequence(localASN1EncodableVector).getEncoded();
  }
  
  public EACSignatureVerifier build(final ASN1ObjectIdentifier paramASN1ObjectIdentifier, PublicKey paramPublicKey)
    throws OperatorCreationException
  {
    try
    {
      Signature localSignature = this.helper.getSignature(paramASN1ObjectIdentifier);
      localSignature.initVerify(paramPublicKey);
      new EACSignatureVerifier()
      {
        public OutputStream getOutputStream()
        {
          return this.val$sigStream;
        }
        
        public ASN1ObjectIdentifier getUsageIdentifier()
        {
          return paramASN1ObjectIdentifier;
        }
        
        public boolean verify(byte[] paramAnonymousArrayOfByte)
        {
          try
          {
            boolean bool1 = paramASN1ObjectIdentifier.on(EACObjectIdentifiers.id_TA_ECDSA);
            if (bool1) {
              try
              {
                byte[] arrayOfByte = JcaEACSignatureVerifierBuilder.derEncode(paramAnonymousArrayOfByte);
                boolean bool3 = this.val$sigStream.verify(arrayOfByte);
                return bool3;
              }
              catch (Exception localException)
              {
                return false;
              }
            }
            boolean bool2 = this.val$sigStream.verify(paramAnonymousArrayOfByte);
            return bool2;
          }
          catch (SignatureException localSignatureException)
          {
            throw new RuntimeOperatorException("exception obtaining signature: " + localSignatureException.getMessage(), localSignatureException);
          }
        }
      };
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new OperatorCreationException("unable to find algorithm: " + localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new OperatorCreationException("unable to find provider: " + localNoSuchProviderException.getMessage(), localNoSuchProviderException);
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new OperatorCreationException("invalid key: " + localInvalidKeyException.getMessage(), localInvalidKeyException);
    }
  }
  
  public JcaEACSignatureVerifierBuilder setProvider(String paramString)
  {
    this.helper = new NamedEACHelper(paramString);
    return this;
  }
  
  public JcaEACSignatureVerifierBuilder setProvider(Provider paramProvider)
  {
    this.helper = new ProviderEACHelper(paramProvider);
    return this;
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
 * Qualified Name:     org.spongycastle.eac.operator.jcajce.JcaEACSignatureVerifierBuilder
 * JD-Core Version:    0.7.0.1
 */