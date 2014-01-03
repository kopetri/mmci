package org.spongycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.misc.MiscObjectIdentifiers;
import org.spongycastle.asn1.misc.NetscapeCertType;
import org.spongycastle.asn1.misc.NetscapeRevocationURL;
import org.spongycastle.asn1.misc.VerisignCzagExtension;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.asn1.x509.TBSCertificateStructure;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;

public class X509CertificateObject
  extends X509Certificate
  implements PKCS12BagAttributeCarrier
{
  private PKCS12BagAttributeCarrier attrCarrier;
  private BasicConstraints basicConstraints;
  private X509CertificateStructure c;
  private int hashValue;
  private boolean hashValueSet;
  private boolean[] keyUsage;
  
  /* Error */
  public X509CertificateObject(X509CertificateStructure paramX509CertificateStructure)
    throws CertificateParsingException
  {
    // Byte code:
    //   0: bipush 9
    //   2: istore_2
    //   3: aload_0
    //   4: invokespecial 27	java/security/cert/X509Certificate:<init>	()V
    //   7: aload_0
    //   8: new 29	org/spongycastle/jcajce/provider/asymmetric/util/PKCS12BagAttributeCarrierImpl
    //   11: dup
    //   12: invokespecial 30	org/spongycastle/jcajce/provider/asymmetric/util/PKCS12BagAttributeCarrierImpl:<init>	()V
    //   15: putfield 32	org/spongycastle/jce/provider/X509CertificateObject:attrCarrier	Lorg/spongycastle/jce/interfaces/PKCS12BagAttributeCarrier;
    //   18: aload_0
    //   19: aload_1
    //   20: putfield 34	org/spongycastle/jce/provider/X509CertificateObject:c	Lorg/spongycastle/asn1/x509/X509CertificateStructure;
    //   23: aload_0
    //   24: ldc 36
    //   26: invokespecial 40	org/spongycastle/jce/provider/X509CertificateObject:getExtensionBytes	(Ljava/lang/String;)[B
    //   29: astore 4
    //   31: aload 4
    //   33: ifnull +15 -> 48
    //   36: aload_0
    //   37: aload 4
    //   39: invokestatic 46	org/spongycastle/asn1/ASN1Primitive:fromByteArray	([B)Lorg/spongycastle/asn1/ASN1Primitive;
    //   42: invokestatic 52	org/spongycastle/asn1/x509/BasicConstraints:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/BasicConstraints;
    //   45: putfield 54	org/spongycastle/jce/provider/X509CertificateObject:basicConstraints	Lorg/spongycastle/asn1/x509/BasicConstraints;
    //   48: aload_0
    //   49: ldc 56
    //   51: invokespecial 40	org/spongycastle/jce/provider/X509CertificateObject:getExtensionBytes	(Ljava/lang/String;)[B
    //   54: astore 6
    //   56: aload 6
    //   58: ifnull +137 -> 195
    //   61: aload 6
    //   63: invokestatic 46	org/spongycastle/asn1/ASN1Primitive:fromByteArray	([B)Lorg/spongycastle/asn1/ASN1Primitive;
    //   66: invokestatic 61	org/spongycastle/asn1/DERBitString:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/DERBitString;
    //   69: astore 7
    //   71: aload 7
    //   73: invokevirtual 65	org/spongycastle/asn1/DERBitString:getBytes	()[B
    //   76: astore 8
    //   78: bipush 8
    //   80: aload 8
    //   82: arraylength
    //   83: imul
    //   84: aload 7
    //   86: invokevirtual 69	org/spongycastle/asn1/DERBitString:getPadBits	()I
    //   89: isub
    //   90: istore 9
    //   92: iload 9
    //   94: iload_2
    //   95: if_icmpge +88 -> 183
    //   98: aload_0
    //   99: iload_2
    //   100: newarray boolean
    //   102: putfield 71	org/spongycastle/jce/provider/X509CertificateObject:keyUsage	[Z
    //   105: iconst_0
    //   106: istore 10
    //   108: iload 10
    //   110: iload 9
    //   112: if_icmpeq +88 -> 200
    //   115: aload_0
    //   116: getfield 71	org/spongycastle/jce/provider/X509CertificateObject:keyUsage	[Z
    //   119: astore 11
    //   121: aload 8
    //   123: iload 10
    //   125: bipush 8
    //   127: idiv
    //   128: baload
    //   129: sipush 128
    //   132: iload 10
    //   134: bipush 8
    //   136: irem
    //   137: iushr
    //   138: iand
    //   139: ifeq +50 -> 189
    //   142: iconst_1
    //   143: istore 12
    //   145: aload 11
    //   147: iload 10
    //   149: iload 12
    //   151: bastore
    //   152: iinc 10 1
    //   155: goto -47 -> 108
    //   158: astore_3
    //   159: new 22	java/security/cert/CertificateParsingException
    //   162: dup
    //   163: new 73	java/lang/StringBuilder
    //   166: dup
    //   167: ldc 75
    //   169: invokespecial 78	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   172: aload_3
    //   173: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   176: invokevirtual 86	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   179: invokespecial 87	java/security/cert/CertificateParsingException:<init>	(Ljava/lang/String;)V
    //   182: athrow
    //   183: iload 9
    //   185: istore_2
    //   186: goto -88 -> 98
    //   189: iconst_0
    //   190: istore 12
    //   192: goto -47 -> 145
    //   195: aload_0
    //   196: aconst_null
    //   197: putfield 71	org/spongycastle/jce/provider/X509CertificateObject:keyUsage	[Z
    //   200: return
    //   201: astore 5
    //   203: new 22	java/security/cert/CertificateParsingException
    //   206: dup
    //   207: new 73	java/lang/StringBuilder
    //   210: dup
    //   211: ldc 89
    //   213: invokespecial 78	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   216: aload 5
    //   218: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   221: invokevirtual 86	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   224: invokespecial 87	java/security/cert/CertificateParsingException:<init>	(Ljava/lang/String;)V
    //   227: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	228	0	this	X509CertificateObject
    //   0	228	1	paramX509CertificateStructure	X509CertificateStructure
    //   2	184	2	i	int
    //   158	15	3	localException1	Exception
    //   29	9	4	arrayOfByte1	byte[]
    //   201	16	5	localException2	Exception
    //   54	8	6	arrayOfByte2	byte[]
    //   69	16	7	localDERBitString	DERBitString
    //   76	46	8	arrayOfByte3	byte[]
    //   90	94	9	j	int
    //   106	47	10	k	int
    //   119	27	11	arrayOfBoolean	boolean[]
    //   143	48	12	m	int
    // Exception table:
    //   from	to	target	type
    //   23	31	158	java/lang/Exception
    //   36	48	158	java/lang/Exception
    //   48	56	201	java/lang/Exception
    //   61	92	201	java/lang/Exception
    //   98	105	201	java/lang/Exception
    //   115	142	201	java/lang/Exception
    //   145	152	201	java/lang/Exception
    //   195	200	201	java/lang/Exception
  }
  
  private int calculateHashCode()
  {
    int i = 0;
    try
    {
      byte[] arrayOfByte = getEncoded();
      for (int j = 1; j < arrayOfByte.length; j++)
      {
        int k = arrayOfByte[j];
        i += k * j;
      }
      return i;
    }
    catch (CertificateEncodingException localCertificateEncodingException) {}
    return 0;
  }
  
  private void checkSignature(PublicKey paramPublicKey, Signature paramSignature)
    throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    if (!isAlgIdEqual(this.c.getSignatureAlgorithm(), this.c.getTBSCertificate().getSignature())) {
      throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
    }
    X509SignatureUtil.setSignatureParameters(paramSignature, this.c.getSignatureAlgorithm().getParameters());
    paramSignature.initVerify(paramPublicKey);
    paramSignature.update(getTBSCertificate());
    if (!paramSignature.verify(getSignature())) {
      throw new SignatureException("certificate does not verify with supplied key");
    }
  }
  
  private byte[] getExtensionBytes(String paramString)
  {
    X509Extensions localX509Extensions = this.c.getTBSCertificate().getExtensions();
    if (localX509Extensions != null)
    {
      X509Extension localX509Extension = localX509Extensions.getExtension(new DERObjectIdentifier(paramString));
      if (localX509Extension != null) {
        return localX509Extension.getValue().getOctets();
      }
    }
    return null;
  }
  
  private boolean isAlgIdEqual(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2)
  {
    if (!paramAlgorithmIdentifier1.getObjectId().equals(paramAlgorithmIdentifier2.getObjectId())) {}
    do
    {
      do
      {
        return false;
        if (paramAlgorithmIdentifier1.getParameters() != null) {
          break;
        }
      } while ((paramAlgorithmIdentifier2.getParameters() != null) && (!paramAlgorithmIdentifier2.getParameters().equals(DERNull.INSTANCE)));
      return true;
      if (paramAlgorithmIdentifier2.getParameters() != null) {
        break;
      }
    } while ((paramAlgorithmIdentifier1.getParameters() != null) && (!paramAlgorithmIdentifier1.getParameters().equals(DERNull.INSTANCE)));
    return true;
    return paramAlgorithmIdentifier1.getParameters().equals(paramAlgorithmIdentifier2.getParameters());
  }
  
  public void checkValidity()
    throws CertificateExpiredException, CertificateNotYetValidException
  {
    checkValidity(new Date());
  }
  
  public void checkValidity(Date paramDate)
    throws CertificateExpiredException, CertificateNotYetValidException
  {
    if (paramDate.getTime() > getNotAfter().getTime()) {
      throw new CertificateExpiredException("certificate expired on " + this.c.getEndDate().getTime());
    }
    if (paramDate.getTime() < getNotBefore().getTime()) {
      throw new CertificateNotYetValidException("certificate not valid till " + this.c.getStartDate().getTime());
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2;
    if (paramObject == this) {
      bool2 = true;
    }
    boolean bool1;
    do
    {
      return bool2;
      bool1 = paramObject instanceof Certificate;
      bool2 = false;
    } while (!bool1);
    Certificate localCertificate = (Certificate)paramObject;
    try
    {
      boolean bool3 = Arrays.areEqual(getEncoded(), localCertificate.getEncoded());
      return bool3;
    }
    catch (CertificateEncodingException localCertificateEncodingException) {}
    return false;
  }
  
  public ASN1Encodable getBagAttribute(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return this.attrCarrier.getBagAttribute(paramDERObjectIdentifier);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return this.attrCarrier.getBagAttributeKeys();
  }
  
  public int getBasicConstraints()
  {
    int i = -1;
    if ((this.basicConstraints != null) && (this.basicConstraints.isCA()))
    {
      if (this.basicConstraints.getPathLenConstraint() == null) {
        i = 2147483647;
      }
    }
    else {
      return i;
    }
    return this.basicConstraints.getPathLenConstraint().intValue();
  }
  
  public Set getCriticalExtensionOIDs()
  {
    if (getVersion() == 3)
    {
      localHashSet = new HashSet();
      X509Extensions localX509Extensions = this.c.getTBSCertificate().getExtensions();
      if (localX509Extensions != null)
      {
        Enumeration localEnumeration = localX509Extensions.oids();
        while (localEnumeration.hasMoreElements())
        {
          DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
          if (localX509Extensions.getExtension(localDERObjectIdentifier).isCritical()) {
            localHashSet.add(localDERObjectIdentifier.getId());
          }
        }
      }
    }
    HashSet localHashSet = null;
    return localHashSet;
  }
  
  public byte[] getEncoded()
    throws CertificateEncodingException
  {
    try
    {
      byte[] arrayOfByte = this.c.getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CertificateEncodingException(localIOException.toString());
    }
  }
  
  public List getExtendedKeyUsage()
    throws CertificateParsingException
  {
    byte[] arrayOfByte = getExtensionBytes("2.5.29.37");
    if (arrayOfByte != null) {
      try
      {
        ASN1Sequence localASN1Sequence = (ASN1Sequence)new ASN1InputStream(arrayOfByte).readObject();
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i != localASN1Sequence.size(); i++) {
          localArrayList.add(((DERObjectIdentifier)localASN1Sequence.getObjectAt(i)).getId());
        }
        List localList = Collections.unmodifiableList(localArrayList);
        return localList;
      }
      catch (Exception localException)
      {
        throw new CertificateParsingException("error processing extended key usage extension");
      }
    }
    return null;
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    X509Extensions localX509Extensions = this.c.getTBSCertificate().getExtensions();
    if (localX509Extensions != null)
    {
      X509Extension localX509Extension = localX509Extensions.getExtension(new DERObjectIdentifier(paramString));
      if (localX509Extension != null) {
        try
        {
          byte[] arrayOfByte = localX509Extension.getValue().getEncoded();
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
    try
    {
      X509Principal localX509Principal = new X509Principal(X500Name.getInstance(this.c.getIssuer().getEncoded()));
      return localX509Principal;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public boolean[] getIssuerUniqueID()
  {
    DERBitString localDERBitString = this.c.getTBSCertificate().getIssuerUniqueId();
    boolean[] arrayOfBoolean;
    if (localDERBitString != null)
    {
      byte[] arrayOfByte = localDERBitString.getBytes();
      arrayOfBoolean = new boolean[8 * arrayOfByte.length - localDERBitString.getPadBits()];
      int i = 0;
      if (i != arrayOfBoolean.length)
      {
        if ((arrayOfByte[(i / 8)] & 128 >>> i % 8) != 0) {}
        for (int j = 1;; j = 0)
        {
          arrayOfBoolean[i] = j;
          i++;
          break;
        }
      }
    }
    else
    {
      arrayOfBoolean = null;
    }
    return arrayOfBoolean;
  }
  
  public X500Principal getIssuerX500Principal()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      new ASN1OutputStream(localByteArrayOutputStream).writeObject(this.c.getIssuer());
      X500Principal localX500Principal = new X500Principal(localByteArrayOutputStream.toByteArray());
      return localX500Principal;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("can't encode issuer DN");
    }
  }
  
  public boolean[] getKeyUsage()
  {
    return this.keyUsage;
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    if (getVersion() == 3)
    {
      localHashSet = new HashSet();
      X509Extensions localX509Extensions = this.c.getTBSCertificate().getExtensions();
      if (localX509Extensions != null)
      {
        Enumeration localEnumeration = localX509Extensions.oids();
        while (localEnumeration.hasMoreElements())
        {
          DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
          if (!localX509Extensions.getExtension(localDERObjectIdentifier).isCritical()) {
            localHashSet.add(localDERObjectIdentifier.getId());
          }
        }
      }
    }
    HashSet localHashSet = null;
    return localHashSet;
  }
  
  public Date getNotAfter()
  {
    return this.c.getEndDate().getDate();
  }
  
  public Date getNotBefore()
  {
    return this.c.getStartDate().getDate();
  }
  
  public PublicKey getPublicKey()
  {
    try
    {
      PublicKey localPublicKey = BouncyCastleProvider.getPublicKey(this.c.getSubjectPublicKeyInfo());
      return localPublicKey;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public BigInteger getSerialNumber()
  {
    return this.c.getSerialNumber().getValue();
  }
  
  public String getSigAlgName()
  {
    Provider localProvider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
    String str;
    if (localProvider != null)
    {
      str = localProvider.getProperty("Alg.Alias.Signature." + getSigAlgOID());
      if (str != null) {
        return str;
      }
    }
    Provider[] arrayOfProvider = Security.getProviders();
    for (int i = 0;; i++)
    {
      if (i == arrayOfProvider.length) {
        break label96;
      }
      str = arrayOfProvider[i].getProperty("Alg.Alias.Signature." + getSigAlgOID());
      if (str != null) {
        break;
      }
    }
    label96:
    return getSigAlgOID();
  }
  
  public String getSigAlgOID()
  {
    return this.c.getSignatureAlgorithm().getObjectId().getId();
  }
  
  public byte[] getSigAlgParams()
  {
    ASN1Encodable localASN1Encodable = this.c.getSignatureAlgorithm().getParameters();
    Object localObject = null;
    if (localASN1Encodable != null) {}
    try
    {
      byte[] arrayOfByte = this.c.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded("DER");
      localObject = arrayOfByte;
      return localObject;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public byte[] getSignature()
  {
    return this.c.getSignature().getBytes();
  }
  
  public Principal getSubjectDN()
  {
    return new X509Principal(X500Name.getInstance(this.c.getSubject().toASN1Primitive()));
  }
  
  public boolean[] getSubjectUniqueID()
  {
    DERBitString localDERBitString = this.c.getTBSCertificate().getSubjectUniqueId();
    boolean[] arrayOfBoolean;
    if (localDERBitString != null)
    {
      byte[] arrayOfByte = localDERBitString.getBytes();
      arrayOfBoolean = new boolean[8 * arrayOfByte.length - localDERBitString.getPadBits()];
      int i = 0;
      if (i != arrayOfBoolean.length)
      {
        if ((arrayOfByte[(i / 8)] & 128 >>> i % 8) != 0) {}
        for (int j = 1;; j = 0)
        {
          arrayOfBoolean[i] = j;
          i++;
          break;
        }
      }
    }
    else
    {
      arrayOfBoolean = null;
    }
    return arrayOfBoolean;
  }
  
  public X500Principal getSubjectX500Principal()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      new ASN1OutputStream(localByteArrayOutputStream).writeObject(this.c.getSubject());
      X500Principal localX500Principal = new X500Principal(localByteArrayOutputStream.toByteArray());
      return localX500Principal;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("can't encode issuer DN");
    }
  }
  
  public byte[] getTBSCertificate()
    throws CertificateEncodingException
  {
    try
    {
      byte[] arrayOfByte = this.c.getTBSCertificate().getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CertificateEncodingException(localIOException.toString());
    }
  }
  
  public int getVersion()
  {
    return this.c.getVersion();
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    if (getVersion() == 3)
    {
      X509Extensions localX509Extensions = this.c.getTBSCertificate().getExtensions();
      if (localX509Extensions != null)
      {
        Enumeration localEnumeration = localX509Extensions.oids();
        while (localEnumeration.hasMoreElements())
        {
          DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
          String str = localDERObjectIdentifier.getId();
          if ((!str.equals(RFC3280CertPathUtilities.KEY_USAGE)) && (!str.equals(RFC3280CertPathUtilities.CERTIFICATE_POLICIES)) && (!str.equals(RFC3280CertPathUtilities.POLICY_MAPPINGS)) && (!str.equals(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY)) && (!str.equals(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS)) && (!str.equals(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT)) && (!str.equals(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR)) && (!str.equals(RFC3280CertPathUtilities.POLICY_CONSTRAINTS)) && (!str.equals(RFC3280CertPathUtilities.BASIC_CONSTRAINTS)) && (!str.equals(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME)) && (!str.equals(RFC3280CertPathUtilities.NAME_CONSTRAINTS)) && (localX509Extensions.getExtension(localDERObjectIdentifier).isCritical())) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    try
    {
      if (!this.hashValueSet)
      {
        this.hashValue = calculateHashCode();
        this.hashValueSet = true;
      }
      int i = this.hashValue;
      return i;
    }
    finally {}
  }
  
  public void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.attrCarrier.setBagAttribute(paramASN1ObjectIdentifier, paramASN1Encodable);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("  [0]         Version: ").append(getVersion()).append(str);
    localStringBuffer.append("         SerialNumber: ").append(getSerialNumber()).append(str);
    localStringBuffer.append("             IssuerDN: ").append(getIssuerDN()).append(str);
    localStringBuffer.append("           Start Date: ").append(getNotBefore()).append(str);
    localStringBuffer.append("           Final Date: ").append(getNotAfter()).append(str);
    localStringBuffer.append("            SubjectDN: ").append(getSubjectDN()).append(str);
    localStringBuffer.append("           Public Key: ").append(getPublicKey()).append(str);
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
    X509Extensions localX509Extensions = this.c.getTBSCertificate().getExtensions();
    if (localX509Extensions != null)
    {
      Enumeration localEnumeration = localX509Extensions.oids();
      if (localEnumeration.hasMoreElements()) {
        localStringBuffer.append("       Extensions: \n");
      }
      while (localEnumeration.hasMoreElements())
      {
        DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
        X509Extension localX509Extension = localX509Extensions.getExtension(localDERObjectIdentifier);
        if (localX509Extension.getValue() != null)
        {
          ASN1InputStream localASN1InputStream = new ASN1InputStream(localX509Extension.getValue().getOctets());
          localStringBuffer.append("                       critical(").append(localX509Extension.isCritical()).append(") ");
          try
          {
            if (!localDERObjectIdentifier.equals(X509Extension.basicConstraints)) {
              break label479;
            }
            localStringBuffer.append(BasicConstraints.getInstance(localASN1InputStream.readObject())).append(str);
          }
          catch (Exception localException)
          {
            localStringBuffer.append(localDERObjectIdentifier.getId());
            localStringBuffer.append(" value = *****").append(str);
          }
          continue;
          label479:
          if (localDERObjectIdentifier.equals(X509Extension.keyUsage))
          {
            localStringBuffer.append(new KeyUsage((DERBitString)localASN1InputStream.readObject())).append(str);
          }
          else if (localDERObjectIdentifier.equals(MiscObjectIdentifiers.netscapeCertType))
          {
            localStringBuffer.append(new NetscapeCertType((DERBitString)localASN1InputStream.readObject())).append(str);
          }
          else if (localDERObjectIdentifier.equals(MiscObjectIdentifiers.netscapeRevocationURL))
          {
            localStringBuffer.append(new NetscapeRevocationURL((DERIA5String)localASN1InputStream.readObject())).append(str);
          }
          else if (localDERObjectIdentifier.equals(MiscObjectIdentifiers.verisignCzagExtension))
          {
            localStringBuffer.append(new VerisignCzagExtension((DERIA5String)localASN1InputStream.readObject())).append(str);
          }
          else
          {
            localStringBuffer.append(localDERObjectIdentifier.getId());
            localStringBuffer.append(" value = ").append(ASN1Dump.dumpAsString(localASN1InputStream.readObject())).append(str);
          }
        }
        else
        {
          localStringBuffer.append(str);
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public final void verify(PublicKey paramPublicKey)
    throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
  {
    str = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
    try
    {
      Signature localSignature2 = Signature.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
      localSignature1 = localSignature2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Signature localSignature1 = Signature.getInstance(str);
      }
    }
    checkSignature(paramPublicKey, localSignature1);
  }
  
  public final void verify(PublicKey paramPublicKey, String paramString)
    throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException
  {
    checkSignature(paramPublicKey, Signature.getInstance(X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm()), paramString));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509CertificateObject
 * JD-Core Version:    0.7.0.1
 */