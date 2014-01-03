package org.spongycastle.cms;

import org.spongycastle.asn1.DERObjectIdentifier;

public class CMSConfig
{
  public static void setSigningDigestAlgorithmMapping(String paramString1, String paramString2)
  {
    DERObjectIdentifier localDERObjectIdentifier = new DERObjectIdentifier(paramString1);
    CMSSignedHelper.INSTANCE.setSigningDigestAlgorithmMapping(localDERObjectIdentifier, paramString2);
  }
  
  public static void setSigningEncryptionAlgorithmMapping(String paramString1, String paramString2)
  {
    DERObjectIdentifier localDERObjectIdentifier = new DERObjectIdentifier(paramString1);
    CMSSignedHelper.INSTANCE.setSigningEncryptionAlgorithmMapping(localDERObjectIdentifier, paramString2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSConfig
 * JD-Core Version:    0.7.0.1
 */