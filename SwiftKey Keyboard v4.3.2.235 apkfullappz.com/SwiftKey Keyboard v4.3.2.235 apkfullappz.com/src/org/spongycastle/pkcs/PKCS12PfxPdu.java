package org.spongycastle.pkcs;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.pkcs.Pfx;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.util.Arrays;

public class PKCS12PfxPdu
{
  private Pfx pfx;
  
  public PKCS12PfxPdu(Pfx paramPfx)
  {
    this.pfx = paramPfx;
  }
  
  public ContentInfo[] getContentInfos()
  {
    ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(ASN1OctetString.getInstance(this.pfx.getAuthSafe().getContent()).getOctets());
    ContentInfo[] arrayOfContentInfo = new ContentInfo[localASN1Sequence.size()];
    for (int i = 0; i != localASN1Sequence.size(); i++) {
      arrayOfContentInfo[i] = ContentInfo.getInstance(localASN1Sequence.getObjectAt(i));
    }
    return arrayOfContentInfo;
  }
  
  public boolean hasMac()
  {
    return this.pfx.getMacData() != null;
  }
  
  public boolean isMacValid(PKCS12MacCalculatorBuilderProvider paramPKCS12MacCalculatorBuilderProvider, char[] paramArrayOfChar)
    throws PKCSException
  {
    if (hasMac())
    {
      MacData localMacData = this.pfx.getMacData();
      MacDataGenerator localMacDataGenerator = new MacDataGenerator(paramPKCS12MacCalculatorBuilderProvider.get(new AlgorithmIdentifier(localMacData.getMac().getAlgorithmId().getAlgorithm(), new PKCS12PBEParams(localMacData.getSalt(), localMacData.getIterationCount().intValue()))));
      try
      {
        boolean bool = Arrays.constantTimeAreEqual(localMacDataGenerator.build(paramArrayOfChar, ASN1OctetString.getInstance(this.pfx.getAuthSafe().getContent()).getOctets()).getEncoded(), this.pfx.getMacData().getEncoded());
        return bool;
      }
      catch (IOException localIOException)
      {
        throw new PKCSException("unable to process AuthSafe: " + localIOException.getMessage());
      }
    }
    throw new IllegalStateException("no MAC present on PFX");
  }
  
  public Pfx toASN1Structure()
  {
    return this.pfx;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS12PfxPdu
 * JD-Core Version:    0.7.0.1
 */