package org.spongycastle.cert.selector;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.Target;
import org.spongycastle.asn1.x509.TargetInformation;
import org.spongycastle.asn1.x509.Targets;
import org.spongycastle.cert.AttributeCertificateHolder;
import org.spongycastle.cert.AttributeCertificateIssuer;
import org.spongycastle.cert.X509AttributeCertificateHolder;
import org.spongycastle.util.Selector;

public class X509AttributeCertificateHolderSelector
  implements Selector
{
  private final X509AttributeCertificateHolder attributeCert;
  private final Date attributeCertificateValid;
  private final AttributeCertificateHolder holder;
  private final AttributeCertificateIssuer issuer;
  private final BigInteger serialNumber;
  private final Collection targetGroups;
  private final Collection targetNames;
  
  X509AttributeCertificateHolderSelector(AttributeCertificateHolder paramAttributeCertificateHolder, AttributeCertificateIssuer paramAttributeCertificateIssuer, BigInteger paramBigInteger, Date paramDate, X509AttributeCertificateHolder paramX509AttributeCertificateHolder, Collection paramCollection1, Collection paramCollection2)
  {
    this.holder = paramAttributeCertificateHolder;
    this.issuer = paramAttributeCertificateIssuer;
    this.serialNumber = paramBigInteger;
    this.attributeCertificateValid = paramDate;
    this.attributeCert = paramX509AttributeCertificateHolder;
    this.targetNames = paramCollection1;
    this.targetGroups = paramCollection2;
  }
  
  public Object clone()
  {
    return new X509AttributeCertificateHolderSelector(this.holder, this.issuer, this.serialNumber, this.attributeCertificateValid, this.attributeCert, this.targetNames, this.targetGroups);
  }
  
  public X509AttributeCertificateHolder getAttributeCert()
  {
    return this.attributeCert;
  }
  
  public Date getAttributeCertificateValid()
  {
    if (this.attributeCertificateValid != null) {
      return new Date(this.attributeCertificateValid.getTime());
    }
    return null;
  }
  
  public AttributeCertificateHolder getHolder()
  {
    return this.holder;
  }
  
  public AttributeCertificateIssuer getIssuer()
  {
    return this.issuer;
  }
  
  public BigInteger getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public Collection getTargetGroups()
  {
    return this.targetGroups;
  }
  
  public Collection getTargetNames()
  {
    return this.targetNames;
  }
  
  public boolean match(Object paramObject)
  {
    if (!(paramObject instanceof X509AttributeCertificateHolder)) {}
    for (;;)
    {
      return false;
      X509AttributeCertificateHolder localX509AttributeCertificateHolder = (X509AttributeCertificateHolder)paramObject;
      if (((this.attributeCert == null) || (this.attributeCert.equals(localX509AttributeCertificateHolder))) && ((this.serialNumber == null) || (localX509AttributeCertificateHolder.getSerialNumber().equals(this.serialNumber))) && ((this.holder == null) || (localX509AttributeCertificateHolder.getHolder().equals(this.holder))) && ((this.issuer == null) || (localX509AttributeCertificateHolder.getIssuer().equals(this.issuer))) && ((this.attributeCertificateValid == null) || (localX509AttributeCertificateHolder.isValidOn(this.attributeCertificateValid))))
      {
        Extension localExtension;
        if ((!this.targetNames.isEmpty()) || (!this.targetGroups.isEmpty()))
        {
          localExtension = localX509AttributeCertificateHolder.getExtension(Extension.targetInformation);
          if (localExtension == null) {}
        }
        try
        {
          TargetInformation localTargetInformation = TargetInformation.getInstance(localExtension.getParsedValue());
          Targets[] arrayOfTargets = localTargetInformation.getTargetsObjects();
          if (!this.targetNames.isEmpty())
          {
            int m = 0;
            int n = 0;
            if (n < arrayOfTargets.length)
            {
              Target[] arrayOfTarget2 = arrayOfTargets[n].getTargets();
              for (int i1 = 0;; i1++) {
                if (i1 < arrayOfTarget2.length)
                {
                  if (this.targetNames.contains(GeneralName.getInstance(arrayOfTarget2[i1].getTargetName()))) {
                    m = 1;
                  }
                }
                else
                {
                  n++;
                  break;
                }
              }
            }
            if (m == 0) {}
          }
          else if (!this.targetGroups.isEmpty())
          {
            int i = 0;
            int j = 0;
            if (j < arrayOfTargets.length)
            {
              Target[] arrayOfTarget1 = arrayOfTargets[j].getTargets();
              for (int k = 0;; k++) {
                if (k < arrayOfTarget1.length)
                {
                  if (this.targetGroups.contains(GeneralName.getInstance(arrayOfTarget1[k].getTargetGroup()))) {
                    i = 1;
                  }
                }
                else
                {
                  j++;
                  break;
                }
              }
            }
            if (i == 0) {}
          }
          else
          {
            return true;
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException) {}
      }
    }
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.X509AttributeCertificateHolderSelector
 * JD-Core Version:    0.7.0.1
 */