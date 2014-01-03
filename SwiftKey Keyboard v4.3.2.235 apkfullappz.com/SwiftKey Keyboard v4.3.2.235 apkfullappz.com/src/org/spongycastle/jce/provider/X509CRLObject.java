package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.CRLNumber;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.IssuingDistributionPoint;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.asn1.x509.TBSCertList.CRLEntry;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.util.encoders.Hex;
import org.spongycastle.x509.extension.X509ExtensionUtil;

public class X509CRLObject
  extends X509CRL
{
  private CertificateList c;
  private boolean isIndirect;
  private String sigAlgName;
  private byte[] sigAlgParams;
  
  /* Error */
  public X509CRLObject(CertificateList paramCertificateList)
    throws CRLException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 21	java/security/cert/X509CRL:<init>	()V
    //   4: aload_0
    //   5: aload_1
    //   6: putfield 23	org/spongycastle/jce/provider/X509CRLObject:c	Lorg/spongycastle/asn1/x509/CertificateList;
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual 29	org/spongycastle/asn1/x509/CertificateList:getSignatureAlgorithm	()Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   14: invokestatic 35	org/spongycastle/jce/provider/X509SignatureUtil:getSignatureName	(Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;)Ljava/lang/String;
    //   17: putfield 37	org/spongycastle/jce/provider/X509CRLObject:sigAlgName	Ljava/lang/String;
    //   20: aload_1
    //   21: invokevirtual 29	org/spongycastle/asn1/x509/CertificateList:getSignatureAlgorithm	()Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   24: invokevirtual 43	org/spongycastle/asn1/x509/AlgorithmIdentifier:getParameters	()Lorg/spongycastle/asn1/ASN1Encodable;
    //   27: ifnull +33 -> 60
    //   30: aload_0
    //   31: aload_1
    //   32: invokevirtual 29	org/spongycastle/asn1/x509/CertificateList:getSignatureAlgorithm	()Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   35: invokevirtual 43	org/spongycastle/asn1/x509/AlgorithmIdentifier:getParameters	()Lorg/spongycastle/asn1/ASN1Encodable;
    //   38: invokeinterface 49 1 0
    //   43: ldc 51
    //   45: invokevirtual 57	org/spongycastle/asn1/ASN1Primitive:getEncoded	(Ljava/lang/String;)[B
    //   48: putfield 59	org/spongycastle/jce/provider/X509CRLObject:sigAlgParams	[B
    //   51: aload_0
    //   52: aload_0
    //   53: invokestatic 63	org/spongycastle/jce/provider/X509CRLObject:isIndirectCRL	(Ljava/security/cert/X509CRL;)Z
    //   56: putfield 65	org/spongycastle/jce/provider/X509CRLObject:isIndirect	Z
    //   59: return
    //   60: aload_0
    //   61: aconst_null
    //   62: putfield 59	org/spongycastle/jce/provider/X509CRLObject:sigAlgParams	[B
    //   65: goto -14 -> 51
    //   68: astore_2
    //   69: new 16	java/security/cert/CRLException
    //   72: dup
    //   73: new 67	java/lang/StringBuilder
    //   76: dup
    //   77: ldc 69
    //   79: invokespecial 72	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   82: aload_2
    //   83: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   86: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: invokespecial 81	java/security/cert/CRLException:<init>	(Ljava/lang/String;)V
    //   92: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	93	0	this	X509CRLObject
    //   0	93	1	paramCertificateList	CertificateList
    //   68	15	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   9	51	68	java/lang/Exception
    //   51	59	68	java/lang/Exception
    //   60	65	68	java/lang/Exception
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    if (getVersion() == 2)
    {
      Extensions localExtensions = this.c.getTBSCertList().getExtensions();
      if (localExtensions != null)
      {
        localHashSet = new HashSet();
        Enumeration localEnumeration = localExtensions.oids();
        while (localEnumeration.hasMoreElements())
        {
          ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localEnumeration.nextElement();
          if (paramBoolean == localExtensions.getExtension(localASN1ObjectIdentifier).isCritical()) {
            localHashSet.add(localASN1ObjectIdentifier.getId());
          }
        }
      }
    }
    HashSet localHashSet = null;
    return localHashSet;
  }
  
  static boolean isIndirectCRL(X509CRL paramX509CRL)
    throws CRLException
  {
    try
    {
      byte[] arrayOfByte = paramX509CRL.getExtensionValue(Extension.issuingDistributionPoint.getId());
      if (arrayOfByte != null)
      {
        boolean bool = IssuingDistributionPoint.getInstance(X509ExtensionUtil.fromExtensionValue(arrayOfByte)).isIndirectCRL();
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (Exception localException)
    {
      throw new ExtCRLException("Exception reading IssuingDistributionPoint", localException);
    }
  }
  
  private Set loadCRLEntries()
  {
    HashSet localHashSet = new HashSet();
    Enumeration localEnumeration = this.c.getRevokedCertificateEnumeration();
    X500Name localX500Name = null;
    while (localEnumeration.hasMoreElements())
    {
      TBSCertList.CRLEntry localCRLEntry = (TBSCertList.CRLEntry)localEnumeration.nextElement();
      localHashSet.add(new X509CRLEntryObject(localCRLEntry, this.isIndirect, localX500Name));
      if ((this.isIndirect) && (localCRLEntry.hasExtensions()))
      {
        Extension localExtension = localCRLEntry.getExtensions().getExtension(Extension.certificateIssuer);
        if (localExtension != null) {
          localX500Name = X500Name.getInstance(org.spongycastle.asn1.x509.GeneralNames.getInstance(localExtension.getParsedValue()).getNames()[0].getName());
        }
      }
    }
    return localHashSet;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public byte[] getEncoded()
    throws CRLException
  {
    try
    {
      byte[] arrayOfByte = this.c.getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CRLException(localIOException.toString());
    }
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    Extensions localExtensions = this.c.getTBSCertList().getExtensions();
    if (localExtensions != null)
    {
      Extension localExtension = localExtensions.getExtension(new ASN1ObjectIdentifier(paramString));
      if (localExtension != null) {
        try
        {
          byte[] arrayOfByte = localExtension.getExtnValue().getEncoded();
          return arrayOfByte;
        }
        catch (Exception localException)
        {
          throw new IllegalStateException("error parsing " + localException.toString());
        }
      }
    }
    return null;
  }
  
  public Principal getIssuerDN()
  {
    return new X509Principal(X500Name.getInstance(this.c.getIssuer().toASN1Primitive()));
  }
  
  public X500Principal getIssuerX500Principal()
  {
    try
    {
      X500Principal localX500Principal = new X500Principal(this.c.getIssuer().getEncoded());
      return localX500Principal;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("can't encode issuer DN");
    }
  }
  
  public Date getNextUpdate()
  {
    if (this.c.getNextUpdate() != null) {
      return this.c.getNextUpdate().getDate();
    }
    return null;
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  public X509CRLEntry getRevokedCertificate(BigInteger paramBigInteger)
  {
    Enumeration localEnumeration = this.c.getRevokedCertificateEnumeration();
    X500Name localX500Name = null;
    while (localEnumeration.hasMoreElements())
    {
      TBSCertList.CRLEntry localCRLEntry = (TBSCertList.CRLEntry)localEnumeration.nextElement();
      if (paramBigInteger.equals(localCRLEntry.getUserCertificate().getValue())) {
        return new X509CRLEntryObject(localCRLEntry, this.isIndirect, localX500Name);
      }
      if ((this.isIndirect) && (localCRLEntry.hasExtensions()))
      {
        Extension localExtension = localCRLEntry.getExtensions().getExtension(Extension.certificateIssuer);
        if (localExtension != null) {
          localX500Name = X500Name.getInstance(org.spongycastle.asn1.x509.GeneralNames.getInstance(localExtension.getParsedValue()).getNames()[0].getName());
        }
      }
    }
    return null;
  }
  
  public Set getRevokedCertificates()
  {
    Set localSet = loadCRLEntries();
    if (!localSet.isEmpty()) {
      return Collections.unmodifiableSet(localSet);
    }
    return null;
  }
  
  public String getSigAlgName()
  {
    return this.sigAlgName;
  }
  
  public String getSigAlgOID()
  {
    return this.c.getSignatureAlgorithm().getAlgorithm().getId();
  }
  
  public byte[] getSigAlgParams()
  {
    if (this.sigAlgParams != null)
    {
      byte[] arrayOfByte = new byte[this.sigAlgParams.length];
      System.arraycopy(this.sigAlgParams, 0, arrayOfByte, 0, arrayOfByte.length);
      return arrayOfByte;
    }
    return null;
  }
  
  public byte[] getSignature()
  {
    return this.c.getSignature().getBytes();
  }
  
  public byte[] getTBSCertList()
    throws CRLException
  {
    try
    {
      byte[] arrayOfByte = this.c.getTBSCertList().getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CRLException(localIOException.toString());
    }
  }
  
  public Date getThisUpdate()
  {
    return this.c.getThisUpdate().getDate();
  }
  
  public int getVersion()
  {
    return this.c.getVersionNumber();
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    if (localSet == null) {}
    do
    {
      return false;
      localSet.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
      localSet.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    } while (localSet.isEmpty());
    return true;
  }
  
  public boolean isRevoked(java.security.cert.Certificate paramCertificate)
  {
    if (!paramCertificate.getType().equals("X.509")) {
      throw new RuntimeException("X.509 CRL used with non X.509 Cert");
    }
    TBSCertList.CRLEntry[] arrayOfCRLEntry = this.c.getRevokedCertificates();
    X500Name localX500Name1 = this.c.getIssuer();
    if (arrayOfCRLEntry != null)
    {
      BigInteger localBigInteger = ((X509Certificate)paramCertificate).getSerialNumber();
      for (int i = 0; i < arrayOfCRLEntry.length; i++)
      {
        if ((this.isIndirect) && (arrayOfCRLEntry[i].hasExtensions()))
        {
          Extension localExtension = arrayOfCRLEntry[i].getExtensions().getExtension(Extension.certificateIssuer);
          if (localExtension != null) {
            localX500Name1 = X500Name.getInstance(org.spongycastle.asn1.x509.GeneralNames.getInstance(localExtension.getParsedValue()).getNames()[0].getName());
          }
        }
        if (arrayOfCRLEntry[i].getUserCertificate().getValue().equals(localBigInteger))
        {
          Object localObject;
          if ((paramCertificate instanceof X509Certificate)) {
            localObject = X500Name.getInstance(((X509Certificate)paramCertificate).getIssuerX500Principal().getEncoded());
          }
          while (!localX500Name1.equals(localObject))
          {
            return false;
            try
            {
              X500Name localX500Name2 = org.spongycastle.asn1.x509.Certificate.getInstance(paramCertificate.getEncoded()).getIssuer();
              localObject = localX500Name2;
            }
            catch (CertificateEncodingException localCertificateEncodingException)
            {
              throw new RuntimeException("Cannot process certificate");
            }
          }
          return true;
        }
      }
    }
    return false;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("              Version: ").append(getVersion()).append(str);
    localStringBuffer.append("             IssuerDN: ").append(getIssuerDN()).append(str);
    localStringBuffer.append("          This update: ").append(getThisUpdate()).append(str);
    localStringBuffer.append("          Next update: ").append(getNextUpdate()).append(str);
    localStringBuffer.append("  Signature Algorithm: ").append(getSigAlgName()).append(str);
    byte[] arrayOfByte = getSignature();
    localStringBuffer.append("            Signature: ").append(new String(Hex.encode(arrayOfByte, 0, 20))).append(str);
    int i = 20;
    if (i < arrayOfByte.length)
    {
      if (i < -20 + arrayOfByte.length) {
        localStringBuffer.append("                       ").append(new String(Hex.encode(arrayOfByte, i, 20))).append(str);
      }
      for (;;)
      {
        i += 20;
        break;
        localStringBuffer.append("                       ").append(new String(Hex.encode(arrayOfByte, i, arrayOfByte.length - i))).append(str);
      }
    }
    Extensions localExtensions = this.c.getTBSCertList().getExtensions();
    if (localExtensions != null)
    {
      Enumeration localEnumeration = localExtensions.oids();
      if (localEnumeration.hasMoreElements()) {
        localStringBuffer.append("           Extensions: ").append(str);
      }
      while (localEnumeration.hasMoreElements())
      {
        ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localEnumeration.nextElement();
        Extension localExtension = localExtensions.getExtension(localASN1ObjectIdentifier);
        if (localExtension.getExtnValue() != null)
        {
          ASN1InputStream localASN1InputStream = new ASN1InputStream(localExtension.getExtnValue().getOctets());
          localStringBuffer.append("                       critical(").append(localExtension.isCritical()).append(") ");
          try
          {
            if (!localASN1ObjectIdentifier.equals(Extension.cRLNumber)) {
              break label436;
            }
            localStringBuffer.append(new CRLNumber(DERInteger.getInstance(localASN1InputStream.readObject()).getPositiveValue())).append(str);
          }
          catch (Exception localException)
          {
            localStringBuffer.append(localASN1ObjectIdentifier.getId());
            localStringBuffer.append(" value = *****").append(str);
          }
          continue;
          label436:
          if (localASN1ObjectIdentifier.equals(Extension.deltaCRLIndicator))
          {
            localStringBuffer.append("Base CRL: " + new CRLNumber(DERInteger.getInstance(localASN1InputStream.readObject()).getPositiveValue())).append(str);
          }
          else if (localASN1ObjectIdentifier.equals(Extension.issuingDistributionPoint))
          {
            localStringBuffer.append(IssuingDistributionPoint.getInstance(localASN1InputStream.readObject())).append(str);
          }
          else if (localASN1ObjectIdentifier.equals(Extension.cRLDistributionPoints))
          {
            localStringBuffer.append(CRLDistPoint.getInstance(localASN1InputStream.readObject())).append(str);
          }
          else if (localASN1ObjectIdentifier.equals(Extension.freshestCRL))
          {
            localStringBuffer.append(CRLDistPoint.getInstance(localASN1InputStream.readObject())).append(str);
          }
          else
          {
            localStringBuffer.append(localASN1ObjectIdentifier.getId());
            localStringBuffer.append(" value = ").append(ASN1Dump.dumpAsString(localASN1InputStream.readObject())).append(str);
          }
        }
        else
        {
          localStringBuffer.append(str);
        }
      }
    }
    Set localSet = getRevokedCertificates();
    if (localSet != null)
    {
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        localStringBuffer.append(localIterator.next());
        localStringBuffer.append(str);
      }
    }
    return localStringBuffer.toString();
  }
  
  public void verify(PublicKey paramPublicKey)
    throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
  {
    verify(paramPublicKey, BouncyCastleProvider.PROVIDER_NAME);
  }
  
  public void verify(PublicKey paramPublicKey, String paramString)
    throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
  {
    if (!this.c.getSignatureAlgorithm().equals(this.c.getTBSCertList().getSignature())) {
      throw new CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
    }
    if (paramString != null) {}
    for (Signature localSignature = Signature.getInstance(getSigAlgName(), paramString);; localSignature = Signature.getInstance(getSigAlgName()))
    {
      localSignature.initVerify(paramPublicKey);
      localSignature.update(getTBSCertList());
      if (localSignature.verify(getSignature())) {
        break;
      }
      throw new SignatureException("CRL does not verify with supplied public key.");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509CRLObject
 * JD-Core Version:    0.7.0.1
 */