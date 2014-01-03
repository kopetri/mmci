package org.spongycastle.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.spongycastle.operator.OperatorStreamException;
import org.spongycastle.operator.RuntimeOperatorException;

public class JcaContentSignerBuilder
{
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  private SecureRandom random;
  private AlgorithmIdentifier sigAlgId;
  private String signatureAlgorithm;
  
  public JcaContentSignerBuilder(String paramString)
  {
    this.signatureAlgorithm = paramString;
    this.sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(paramString);
  }
  
  /* Error */
  public ContentSigner build(java.security.PrivateKey paramPrivateKey)
    throws org.spongycastle.operator.OperatorCreationException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 27	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:helper	Lorg/spongycastle/operator/jcajce/OperatorHelper;
    //   4: aload_0
    //   5: getfield 38	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:sigAlgId	Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   8: invokevirtual 50	org/spongycastle/operator/jcajce/OperatorHelper:createSignature	(Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;)Ljava/security/Signature;
    //   11: astore_3
    //   12: aload_0
    //   13: getfield 52	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:random	Ljava/security/SecureRandom;
    //   16: ifnull +22 -> 38
    //   19: aload_3
    //   20: aload_1
    //   21: aload_0
    //   22: getfield 52	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:random	Ljava/security/SecureRandom;
    //   25: invokevirtual 58	java/security/Signature:initSign	(Ljava/security/PrivateKey;Ljava/security/SecureRandom;)V
    //   28: new 60	org/spongycastle/operator/jcajce/JcaContentSignerBuilder$1
    //   31: dup
    //   32: aload_0
    //   33: aload_3
    //   34: invokespecial 63	org/spongycastle/operator/jcajce/JcaContentSignerBuilder$1:<init>	(Lorg/spongycastle/operator/jcajce/JcaContentSignerBuilder;Ljava/security/Signature;)V
    //   37: areturn
    //   38: aload_3
    //   39: aload_1
    //   40: invokevirtual 66	java/security/Signature:initSign	(Ljava/security/PrivateKey;)V
    //   43: goto -15 -> 28
    //   46: astore_2
    //   47: new 44	org/spongycastle/operator/OperatorCreationException
    //   50: dup
    //   51: new 68	java/lang/StringBuilder
    //   54: dup
    //   55: ldc 70
    //   57: invokespecial 72	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   60: aload_2
    //   61: invokevirtual 76	java/security/GeneralSecurityException:getMessage	()Ljava/lang/String;
    //   64: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: invokevirtual 83	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: aload_2
    //   71: invokespecial 86	org/spongycastle/operator/OperatorCreationException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   74: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	75	0	this	JcaContentSignerBuilder
    //   0	75	1	paramPrivateKey	java.security.PrivateKey
    //   46	25	2	localGeneralSecurityException	java.security.GeneralSecurityException
    //   11	28	3	localSignature	Signature
    // Exception table:
    //   from	to	target	type
    //   0	28	46	java/security/GeneralSecurityException
    //   28	38	46	java/security/GeneralSecurityException
    //   38	43	46	java/security/GeneralSecurityException
  }
  
  public JcaContentSignerBuilder setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JcaContentSignerBuilder setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  public JcaContentSignerBuilder setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
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
 * Qualified Name:     org.spongycastle.operator.jcajce.JcaContentSignerBuilder
 * JD-Core Version:    0.7.0.1
 */