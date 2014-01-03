package org.spongycastle.cert.crmf;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.cmp.PBMParameter;
import org.spongycastle.asn1.crmf.PKMACValue;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.util.Arrays;

class PKMACValueVerifier
{
  private final PKMACBuilder builder;
  
  public PKMACValueVerifier(PKMACBuilder paramPKMACBuilder)
  {
    this.builder = paramPKMACBuilder;
  }
  
  public boolean isValid(PKMACValue paramPKMACValue, char[] paramArrayOfChar, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws CRMFException
  {
    this.builder.setParameters(PBMParameter.getInstance(paramPKMACValue.getAlgId().getParameters()));
    MacCalculator localMacCalculator = this.builder.build(paramArrayOfChar);
    OutputStream localOutputStream = localMacCalculator.getOutputStream();
    try
    {
      localOutputStream.write(paramSubjectPublicKeyInfo.getEncoded("DER"));
      localOutputStream.close();
      return Arrays.areEqual(localMacCalculator.getMac(), paramPKMACValue.getValue().getBytes());
    }
    catch (IOException localIOException)
    {
      throw new CRMFException("exception encoding mac input: " + localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.PKMACValueVerifier
 * JD-Core Version:    0.7.0.1
 */