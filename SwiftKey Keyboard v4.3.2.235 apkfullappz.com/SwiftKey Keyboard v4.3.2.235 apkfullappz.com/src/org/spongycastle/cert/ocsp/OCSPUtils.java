package org.spongycastle.cert.ocsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.cert.X509CertificateHolder;

class OCSPUtils
{
  static final X509CertificateHolder[] EMPTY_CERTS = new X509CertificateHolder[0];
  static List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());
  static Set EMPTY_SET = Collections.unmodifiableSet(new HashSet());
  
  static Date extractDate(DERGeneralizedTime paramDERGeneralizedTime)
  {
    try
    {
      Date localDate = paramDERGeneralizedTime.getDate();
      return localDate;
    }
    catch (Exception localException)
    {
      throw new IllegalStateException("exception processing GeneralizedTime: " + localException.getMessage());
    }
  }
  
  static Set getCriticalExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(Arrays.asList(paramExtensions.getCriticalExtensionOIDs())));
  }
  
  static List getExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_LIST;
    }
    return Collections.unmodifiableList(Arrays.asList(paramExtensions.getExtensionOIDs()));
  }
  
  static Set getNonCriticalExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(Arrays.asList(paramExtensions.getNonCriticalExtensionOIDs())));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.OCSPUtils
 * JD-Core Version:    0.7.0.1
 */