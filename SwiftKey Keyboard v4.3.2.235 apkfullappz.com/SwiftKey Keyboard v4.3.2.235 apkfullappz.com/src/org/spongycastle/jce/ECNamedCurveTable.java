package org.spongycastle.jce;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;

public class ECNamedCurveTable
{
  private static void addEnumeration(Vector paramVector, Enumeration paramEnumeration)
  {
    while (paramEnumeration.hasMoreElements()) {
      paramVector.addElement(paramEnumeration.nextElement());
    }
  }
  
  public static Enumeration getNames()
  {
    Vector localVector = new Vector();
    addEnumeration(localVector, X962NamedCurves.getNames());
    addEnumeration(localVector, SECNamedCurves.getNames());
    addEnumeration(localVector, NISTNamedCurves.getNames());
    addEnumeration(localVector, TeleTrusTNamedCurves.getNames());
    return localVector.elements();
  }
  
  public static ECNamedCurveParameterSpec getParameterSpec(String paramString)
  {
    Object localObject = X962NamedCurves.getByName(paramString);
    if (localObject == null) {}
    try
    {
      X9ECParameters localX9ECParameters3 = X962NamedCurves.getByOID(new ASN1ObjectIdentifier(paramString));
      localObject = localX9ECParameters3;
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      label25:
      break label25;
    }
    if (localObject == null)
    {
      localObject = SECNamedCurves.getByName(paramString);
      if (localObject != null) {}
    }
    try
    {
      X9ECParameters localX9ECParameters2 = SECNamedCurves.getByOID(new ASN1ObjectIdentifier(paramString));
      localObject = localX9ECParameters2;
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      label54:
      label81:
      break label54;
    }
    if (localObject == null)
    {
      localObject = TeleTrusTNamedCurves.getByName(paramString);
      if (localObject != null) {}
    }
    try
    {
      X9ECParameters localX9ECParameters1 = TeleTrusTNamedCurves.getByOID(new ASN1ObjectIdentifier(paramString));
      localObject = localX9ECParameters1;
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      break label81;
    }
    if (localObject == null) {
      localObject = NISTNamedCurves.getByName(paramString);
    }
    if (localObject == null) {
      return null;
    }
    return new ECNamedCurveParameterSpec(paramString, ((X9ECParameters)localObject).getCurve(), ((X9ECParameters)localObject).getG(), ((X9ECParameters)localObject).getN(), ((X9ECParameters)localObject).getH(), ((X9ECParameters)localObject).getSeed());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.ECNamedCurveTable
 * JD-Core Version:    0.7.0.1
 */