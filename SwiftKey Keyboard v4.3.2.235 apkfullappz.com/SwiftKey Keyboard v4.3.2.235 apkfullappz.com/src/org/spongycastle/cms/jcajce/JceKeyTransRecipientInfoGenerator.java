package org.spongycastle.cms.jcajce;

import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
import org.spongycastle.cms.KeyTransRecipientInfoGenerator;
import org.spongycastle.operator.jcajce.JceAsymmetricKeyWrapper;

public class JceKeyTransRecipientInfoGenerator
  extends KeyTransRecipientInfoGenerator
{
  public JceKeyTransRecipientInfoGenerator(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    super(new IssuerAndSerialNumber(new JcaX509CertificateHolder(paramX509Certificate).toASN1Structure()), new JceAsymmetricKeyWrapper(paramX509Certificate.getPublicKey()));
  }
  
  public JceKeyTransRecipientInfoGenerator(byte[] paramArrayOfByte, PublicKey paramPublicKey)
  {
    super(paramArrayOfByte, new JceAsymmetricKeyWrapper(paramPublicKey));
  }
  
  public JceKeyTransRecipientInfoGenerator setAlgorithmMapping(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    ((JceAsymmetricKeyWrapper)this.wrapper).setAlgorithmMapping(paramASN1ObjectIdentifier, paramString);
    return this;
  }
  
  public JceKeyTransRecipientInfoGenerator setProvider(String paramString)
  {
    ((JceAsymmetricKeyWrapper)this.wrapper).setProvider(paramString);
    return this;
  }
  
  public JceKeyTransRecipientInfoGenerator setProvider(Provider paramProvider)
  {
    ((JceAsymmetricKeyWrapper)this.wrapper).setProvider(paramProvider);
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */