package org.spongycastle.cert.crmf.jcajce;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Provider;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.crmf.CRMFException;
import org.spongycastle.cert.crmf.PKMACValuesCalculator;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;

public class JcePKMACValuesCalculator
  implements PKMACValuesCalculator
{
  private MessageDigest digest;
  private CRMFHelper helper = new CRMFHelper(new DefaultJcaJceHelper());
  private Mac mac;
  
  public byte[] calculateDigest(byte[] paramArrayOfByte)
  {
    return this.digest.digest(paramArrayOfByte);
  }
  
  public byte[] calculateMac(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws CRMFException
  {
    try
    {
      this.mac.init(new SecretKeySpec(paramArrayOfByte1, this.mac.getAlgorithm()));
      byte[] arrayOfByte = this.mac.doFinal(paramArrayOfByte2);
      return arrayOfByte;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CRMFException("failure in setup: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public JcePKMACValuesCalculator setProvider(String paramString)
  {
    this.helper = new CRMFHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JcePKMACValuesCalculator setProvider(Provider paramProvider)
  {
    this.helper = new CRMFHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  public void setup(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2)
    throws CRMFException
  {
    this.digest = this.helper.createDigest(paramAlgorithmIdentifier1.getAlgorithm());
    this.mac = this.helper.createMac(paramAlgorithmIdentifier2.getAlgorithm());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.JcePKMACValuesCalculator
 * JD-Core Version:    0.7.0.1
 */