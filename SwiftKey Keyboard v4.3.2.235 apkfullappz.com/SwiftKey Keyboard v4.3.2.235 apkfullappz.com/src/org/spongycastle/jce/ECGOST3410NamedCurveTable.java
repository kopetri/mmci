package org.spongycastle.jce;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;

public class ECGOST3410NamedCurveTable
{
  public static Enumeration getNames()
  {
    return ECGOST3410NamedCurves.getNames();
  }
  
  public static ECNamedCurveParameterSpec getParameterSpec(String paramString)
  {
    Object localObject = ECGOST3410NamedCurves.getByName(paramString);
    if (localObject == null) {}
    try
    {
      ECDomainParameters localECDomainParameters = ECGOST3410NamedCurves.getByOID(new ASN1ObjectIdentifier(paramString));
      localObject = localECDomainParameters;
      if (localObject == null) {
        return null;
      }
      return new ECNamedCurveParameterSpec(paramString, ((ECDomainParameters)localObject).getCurve(), ((ECDomainParameters)localObject).getG(), ((ECDomainParameters)localObject).getN(), ((ECDomainParameters)localObject).getH(), ((ECDomainParameters)localObject).getSeed());
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.ECGOST3410NamedCurveTable
 * JD-Core Version:    0.7.0.1
 */