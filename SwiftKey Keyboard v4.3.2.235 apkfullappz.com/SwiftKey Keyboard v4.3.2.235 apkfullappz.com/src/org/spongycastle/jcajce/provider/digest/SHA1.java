package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jce.provider.JCEMac;

public class SHA1
{
  public static class Digest
    extends BCMessageDigest
    implements Cloneable
  {
    public Digest()
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      Digest localDigest = (Digest)super.clone();
      localDigest.digest = new SHA1Digest((SHA1Digest)this.digest);
      return localDigest;
    }
  }
  
  public static class HashMac
    extends JCEMac
  {
    public HashMac()
    {
      super();
    }
  }
  
  public static class KeyGenerator
    extends BaseKeyGenerator
  {
    public KeyGenerator()
    {
      super(160, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings
    extends DigestAlgorithmProvider
  {
    private static final String PREFIX = SHA1.class.getName();
    
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("MessageDigest.SHA-1", PREFIX + "$Digest");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA1", "SHA-1");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA", "SHA-1");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.MessageDigest." + OIWObjectIdentifiers.idSHA1, "SHA-1");
      addHMACAlgorithm(paramConfigurableProvider, "SHA1", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
      addHMACAlias(paramConfigurableProvider, "SHA1", PKCSObjectIdentifiers.id_hmacWithSHA1);
      addHMACAlias(paramConfigurableProvider, "SHA1", IANAObjectIdentifiers.hmacSHA1);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.digest.SHA1
 * JD-Core Version:    0.7.0.1
 */