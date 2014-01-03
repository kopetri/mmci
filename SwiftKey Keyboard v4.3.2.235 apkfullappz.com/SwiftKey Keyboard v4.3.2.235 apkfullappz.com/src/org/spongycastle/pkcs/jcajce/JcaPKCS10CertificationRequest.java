package org.spongycastle.pkcs.jcajce;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.CertificationRequest;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.JcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.pkcs.PKCS10CertificationRequest;

public class JcaPKCS10CertificationRequest
  extends PKCS10CertificationRequest
{
  private static Hashtable keyAlgorithms;
  private JcaJceHelper helper = new DefaultJcaJceHelper();
  
  static
  {
    Hashtable localHashtable = new Hashtable();
    keyAlgorithms = localHashtable;
    localHashtable.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
    keyAlgorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");
  }
  
  public JcaPKCS10CertificationRequest(CertificationRequest paramCertificationRequest)
  {
    super(paramCertificationRequest);
  }
  
  public JcaPKCS10CertificationRequest(PKCS10CertificationRequest paramPKCS10CertificationRequest)
  {
    super(paramPKCS10CertificationRequest.toASN1Structure());
  }
  
  public JcaPKCS10CertificationRequest(byte[] paramArrayOfByte)
    throws IOException
  {
    super(paramArrayOfByte);
  }
  
  public PublicKey getPublicKey()
    throws InvalidKeyException, NoSuchAlgorithmException
  {
    try
    {
      localSubjectPublicKeyInfo = getSubjectPublicKeyInfo();
      X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(localSubjectPublicKeyInfo.getEncoded());
      try
      {
        KeyFactory localKeyFactory2 = this.helper.createKeyFactory(localSubjectPublicKeyInfo.getAlgorithmId().getAlgorithm().getId());
        localKeyFactory1 = localKeyFactory2;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        KeyFactory localKeyFactory1;
        while (keyAlgorithms.get(localSubjectPublicKeyInfo.getAlgorithmId().getAlgorithm()) != null)
        {
          String str = (String)keyAlgorithms.get(localSubjectPublicKeyInfo.getAlgorithmId().getAlgorithm());
          localKeyFactory1 = this.helper.createKeyFactory(str);
        }
        throw localNoSuchAlgorithmException;
      }
      return localKeyFactory1.generatePublic(localX509EncodedKeySpec);
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      SubjectPublicKeyInfo localSubjectPublicKeyInfo;
      throw new InvalidKeyException("error decoding public key");
    }
    catch (IOException localIOException)
    {
      throw new InvalidKeyException("error extracting key encoding");
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new NoSuchAlgorithmException("cannot find provider: " + localNoSuchProviderException.getMessage());
    }
  }
  
  public JcaPKCS10CertificationRequest setProvider(String paramString)
  {
    this.helper = new NamedJcaJceHelper(paramString);
    return this;
  }
  
  public JcaPKCS10CertificationRequest setProvider(Provider paramProvider)
  {
    this.helper = new ProviderJcaJceHelper(paramProvider);
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequest
 * JD-Core Version:    0.7.0.1
 */