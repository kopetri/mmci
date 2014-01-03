package org.spongycastle.asn1.x500.style;

import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;

public class BCStrictStyle
  extends BCStyle
{
  public boolean areEqual(X500Name paramX500Name1, X500Name paramX500Name2)
  {
    RDN[] arrayOfRDN1 = paramX500Name1.getRDNs();
    RDN[] arrayOfRDN2 = paramX500Name2.getRDNs();
    if (arrayOfRDN1.length != arrayOfRDN2.length) {
      return false;
    }
    for (int i = 0;; i++)
    {
      if (i == arrayOfRDN1.length) {
        break label53;
      }
      if (rdnAreEqual(arrayOfRDN1[i], arrayOfRDN2[i])) {
        break;
      }
    }
    label53:
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.style.BCStrictStyle
 * JD-Core Version:    0.7.0.1
 */