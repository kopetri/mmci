package org.spongycastle.eac.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.eac.EACObjectIdentifiers;
import org.spongycastle.eac.operator.EACSigner;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.OperatorStreamException;
import org.spongycastle.operator.RuntimeOperatorException;

public class JcaEACSignerBuilder
{
  private static final Hashtable sigNames;
  private EACHelper helper = new DefaultEACHelper();
  
  static
  {
    Hashtable localHashtable = new Hashtable();
    sigNames = localHashtable;
    localHashtable.put("SHA1withRSA", EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1);
    sigNames.put("SHA256withRSA", EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256);
    sigNames.put("SHA1withRSAandMGF1", EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1);
    sigNames.put("SHA256withRSAandMGF1", EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256);
    sigNames.put("SHA512withRSA", EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_512);
    sigNames.put("SHA512withRSAandMGF1", EACObjectIdentifiers.id_TA_RSA_PSS_SHA_512);
    sigNames.put("SHA1withECDSA", EACObjectIdentifiers.id_TA_ECDSA_SHA_1);
    sigNames.put("SHA224withECDSA", EACObjectIdentifiers.id_TA_ECDSA_SHA_224);
    sigNames.put("SHA256withECDSA", EACObjectIdentifiers.id_TA_ECDSA_SHA_256);
    sigNames.put("SHA384withECDSA", EACObjectIdentifiers.id_TA_ECDSA_SHA_384);
    sigNames.put("SHA512withECDSA", EACObjectIdentifiers.id_TA_ECDSA_SHA_512);
  }
  
  private static void copyUnsignedInt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    int i = paramArrayOfByte1.length;
    int j = paramArrayOfByte1[0];
    int k = 0;
    if (j == 0)
    {
      i--;
      k = 1;
    }
    System.arraycopy(paramArrayOfByte1, k, paramArrayOfByte2, paramInt, i);
  }
  
  public static int max(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return paramInt1;
    }
    return paramInt2;
  }
  
  private static byte[] reencode(byte[] paramArrayOfByte)
  {
    ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(paramArrayOfByte);
    BigInteger localBigInteger1 = ASN1Integer.getInstance(localASN1Sequence.getObjectAt(0)).getValue();
    BigInteger localBigInteger2 = ASN1Integer.getInstance(localASN1Sequence.getObjectAt(1)).getValue();
    byte[] arrayOfByte1 = localBigInteger1.toByteArray();
    byte[] arrayOfByte2 = localBigInteger2.toByteArray();
    int i = unsignedIntLength(arrayOfByte1);
    int j = unsignedIntLength(arrayOfByte2);
    int k = max(i, j);
    byte[] arrayOfByte3 = new byte[k * 2];
    Arrays.fill(arrayOfByte3, (byte)0);
    copyUnsignedInt(arrayOfByte1, arrayOfByte3, k - i);
    copyUnsignedInt(arrayOfByte2, arrayOfByte3, k * 2 - j);
    return arrayOfByte3;
  }
  
  private static int unsignedIntLength(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    if (paramArrayOfByte[0] == 0) {
      i--;
    }
    return i;
  }
  
  public EACSigner build(String paramString, PrivateKey paramPrivateKey)
    throws OperatorCreationException
  {
    return build((ASN1ObjectIdentifier)sigNames.get(paramString), paramPrivateKey);
  }
  
  public EACSigner build(final ASN1ObjectIdentifier paramASN1ObjectIdentifier, PrivateKey paramPrivateKey)
    throws OperatorCreationException
  {
    try
    {
      Signature localSignature = this.helper.getSignature(paramASN1ObjectIdentifier);
      localSignature.initSign(paramPrivateKey);
      new EACSigner()
      {
        public OutputStream getOutputStream()
        {
          return this.val$sigStream;
        }
        
        public byte[] getSignature()
        {
          try
          {
            Object localObject = this.val$sigStream.getSignature();
            if (paramASN1ObjectIdentifier.on(EACObjectIdentifiers.id_TA_ECDSA))
            {
              byte[] arrayOfByte = JcaEACSignerBuilder.reencode((byte[])localObject);
              localObject = arrayOfByte;
            }
            return localObject;
          }
          catch (SignatureException localSignatureException)
          {
            throw new RuntimeOperatorException("exception obtaining signature: " + localSignatureException.getMessage(), localSignatureException);
          }
        }
        
        public ASN1ObjectIdentifier getUsageIdentifier()
        {
          return paramASN1ObjectIdentifier;
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
  
  public JcaEACSignerBuilder setProvider(String paramString)
  {
    this.helper = new NamedEACHelper(paramString);
    return this;
  }
  
  public JcaEACSignerBuilder setProvider(Provider paramProvider)
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
    
    byte[] getSignature()
      throws SignatureException
    {
      return this.sig.sign();
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
 * Qualified Name:     org.spongycastle.eac.operator.jcajce.JcaEACSignerBuilder
 * JD-Core Version:    0.7.0.1
 */