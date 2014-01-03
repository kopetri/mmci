package org.spongycastle.cert.selector;

import java.io.IOException;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;

class MSOutlookKeyIdCalculator
{
  static byte[] calculateKeyId(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    SHA1Digest localSHA1Digest = new SHA1Digest();
    byte[] arrayOfByte1 = new byte[localSHA1Digest.getDigestSize()];
    try
    {
      byte[] arrayOfByte2 = paramSubjectPublicKeyInfo.getEncoded("DER");
      localSHA1Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
      localSHA1Digest.doFinal(arrayOfByte1, 0);
      return arrayOfByte1;
    }
    catch (IOException localIOException) {}
    return new byte[0];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.MSOutlookKeyIdCalculator
 * JD-Core Version:    0.7.0.1
 */