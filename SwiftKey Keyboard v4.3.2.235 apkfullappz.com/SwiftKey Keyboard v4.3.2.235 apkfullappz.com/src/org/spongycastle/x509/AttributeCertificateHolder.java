package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.Holder;
import org.spongycastle.asn1.x509.IssuerSerial;
import org.spongycastle.asn1.x509.ObjectDigestInfo;
import org.spongycastle.jce.PrincipalUtil;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;

public class AttributeCertificateHolder
  implements CertSelector, Selector
{
  final Holder holder;
  
  public AttributeCertificateHolder(int paramInt, String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    this.holder = new Holder(new ObjectDigestInfo(paramInt, new ASN1ObjectIdentifier(paramString2), new AlgorithmIdentifier(paramString1), Arrays.clone(paramArrayOfByte)));
  }
  
  public AttributeCertificateHolder(X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    try
    {
      X509Principal localX509Principal = PrincipalUtil.getIssuerX509Principal(paramX509Certificate);
      this.holder = new Holder(new IssuerSerial(generateGeneralNames(localX509Principal), new ASN1Integer(paramX509Certificate.getSerialNumber())));
      return;
    }
    catch (Exception localException)
    {
      throw new CertificateParsingException(localException.getMessage());
    }
  }
  
  public AttributeCertificateHolder(X500Principal paramX500Principal)
  {
    this(X509Util.convertPrincipal(paramX500Principal));
  }
  
  public AttributeCertificateHolder(X500Principal paramX500Principal, BigInteger paramBigInteger)
  {
    this(X509Util.convertPrincipal(paramX500Principal), paramBigInteger);
  }
  
  AttributeCertificateHolder(ASN1Sequence paramASN1Sequence)
  {
    this.holder = Holder.getInstance(paramASN1Sequence);
  }
  
  public AttributeCertificateHolder(X509Principal paramX509Principal)
  {
    this.holder = new Holder(generateGeneralNames(paramX509Principal));
  }
  
  public AttributeCertificateHolder(X509Principal paramX509Principal, BigInteger paramBigInteger)
  {
    this.holder = new Holder(new IssuerSerial(GeneralNames.getInstance(new DERSequence(new GeneralName(paramX509Principal))), new ASN1Integer(paramBigInteger)));
  }
  
  private GeneralNames generateGeneralNames(X509Principal paramX509Principal)
  {
    return GeneralNames.getInstance(new DERSequence(new GeneralName(paramX509Principal)));
  }
  
  private Object[] getNames(GeneralName[] paramArrayOfGeneralName)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfGeneralName.length);
    int i = 0;
    while (i != paramArrayOfGeneralName.length)
    {
      if (paramArrayOfGeneralName[i].getTagNo() == 4) {}
      try
      {
        localArrayList.add(new X500Principal(paramArrayOfGeneralName[i].getName().toASN1Primitive().getEncoded()));
        i++;
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException("badly formed Name object");
      }
    }
    return localArrayList.toArray(new Object[localArrayList.size()]);
  }
  
  private Principal[] getPrincipals(GeneralNames paramGeneralNames)
  {
    Object[] arrayOfObject = getNames(paramGeneralNames.getNames());
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i != arrayOfObject.length; i++) {
      if ((arrayOfObject[i] instanceof Principal)) {
        localArrayList.add(arrayOfObject[i]);
      }
    }
    return (Principal[])localArrayList.toArray(new Principal[localArrayList.size()]);
  }
  
  private boolean matchesDN(X509Principal paramX509Principal, GeneralNames paramGeneralNames)
  {
    GeneralName[] arrayOfGeneralName = paramGeneralNames.getNames();
    for (int i = 0; i != arrayOfGeneralName.length; i++)
    {
      GeneralName localGeneralName = arrayOfGeneralName[i];
      if (localGeneralName.getTagNo() == 4) {
        try
        {
          boolean bool = new X509Principal(localGeneralName.getName().toASN1Primitive().getEncoded()).equals(paramX509Principal);
          if (bool) {
            return true;
          }
        }
        catch (IOException localIOException) {}
      }
    }
    return false;
  }
  
  public Object clone()
  {
    return new AttributeCertificateHolder((ASN1Sequence)this.holder.toASN1Object());
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AttributeCertificateHolder)) {
      return false;
    }
    AttributeCertificateHolder localAttributeCertificateHolder = (AttributeCertificateHolder)paramObject;
    return this.holder.equals(localAttributeCertificateHolder.holder);
  }
  
  public String getDigestAlgorithm()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      return this.holder.getObjectDigestInfo().getDigestAlgorithm().getObjectId().getId();
    }
    return null;
  }
  
  public int getDigestedObjectType()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      return this.holder.getObjectDigestInfo().getDigestedObjectType().getValue().intValue();
    }
    return -1;
  }
  
  public Principal[] getEntityNames()
  {
    if (this.holder.getEntityName() != null) {
      return getPrincipals(this.holder.getEntityName());
    }
    return null;
  }
  
  public Principal[] getIssuer()
  {
    if (this.holder.getBaseCertificateID() != null) {
      return getPrincipals(this.holder.getBaseCertificateID().getIssuer());
    }
    return null;
  }
  
  public byte[] getObjectDigest()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      return this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
    }
    return null;
  }
  
  public String getOtherObjectTypeID()
  {
    if (this.holder.getObjectDigestInfo() != null) {
      this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId();
    }
    return null;
  }
  
  public BigInteger getSerialNumber()
  {
    if (this.holder.getBaseCertificateID() != null) {
      return this.holder.getBaseCertificateID().getSerial().getValue();
    }
    return null;
  }
  
  public int hashCode()
  {
    return this.holder.hashCode();
  }
  
  public boolean match(Object paramObject)
  {
    if (!(paramObject instanceof X509Certificate)) {
      return false;
    }
    return match((Certificate)paramObject);
  }
  
  /* Error */
  public boolean match(Certificate paramCertificate)
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 62
    //   4: ifne +5 -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: aload_1
    //   10: checkcast 62	java/security/cert/X509Certificate
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 41	org/spongycastle/x509/AttributeCertificateHolder:holder	Lorg/spongycastle/asn1/x509/Holder;
    //   18: invokevirtual 240	org/spongycastle/asn1/x509/Holder:getBaseCertificateID	()Lorg/spongycastle/asn1/x509/IssuerSerial;
    //   21: ifnull +49 -> 70
    //   24: aload_0
    //   25: getfield 41	org/spongycastle/x509/AttributeCertificateHolder:holder	Lorg/spongycastle/asn1/x509/Holder;
    //   28: invokevirtual 240	org/spongycastle/asn1/x509/Holder:getBaseCertificateID	()Lorg/spongycastle/asn1/x509/IssuerSerial;
    //   31: invokevirtual 258	org/spongycastle/asn1/x509/IssuerSerial:getSerial	()Lorg/spongycastle/asn1/ASN1Integer;
    //   34: invokevirtual 259	org/spongycastle/asn1/ASN1Integer:getValue	()Ljava/math/BigInteger;
    //   37: aload_2
    //   38: invokevirtual 66	java/security/cert/X509Certificate:getSerialNumber	()Ljava/math/BigInteger;
    //   41: invokevirtual 271	java/math/BigInteger:equals	(Ljava/lang/Object;)Z
    //   44: ifeq -37 -> 7
    //   47: aload_0
    //   48: aload_2
    //   49: invokestatic 52	org/spongycastle/jce/PrincipalUtil:getIssuerX509Principal	(Ljava/security/cert/X509Certificate;)Lorg/spongycastle/jce/X509Principal;
    //   52: aload_0
    //   53: getfield 41	org/spongycastle/x509/AttributeCertificateHolder:holder	Lorg/spongycastle/asn1/x509/Holder;
    //   56: invokevirtual 240	org/spongycastle/asn1/x509/Holder:getBaseCertificateID	()Lorg/spongycastle/asn1/x509/IssuerSerial;
    //   59: invokevirtual 242	org/spongycastle/asn1/x509/IssuerSerial:getIssuer	()Lorg/spongycastle/asn1/x509/GeneralNames;
    //   62: invokespecial 273	org/spongycastle/x509/AttributeCertificateHolder:matchesDN	(Lorg/spongycastle/jce/X509Principal;Lorg/spongycastle/asn1/x509/GeneralNames;)Z
    //   65: ifeq -58 -> 7
    //   68: iconst_1
    //   69: ireturn
    //   70: aload_0
    //   71: getfield 41	org/spongycastle/x509/AttributeCertificateHolder:holder	Lorg/spongycastle/asn1/x509/Holder;
    //   74: invokevirtual 233	org/spongycastle/asn1/x509/Holder:getEntityName	()Lorg/spongycastle/asn1/x509/GeneralNames;
    //   77: ifnull +23 -> 100
    //   80: aload_0
    //   81: aload_2
    //   82: invokestatic 276	org/spongycastle/jce/PrincipalUtil:getSubjectX509Principal	(Ljava/security/cert/X509Certificate;)Lorg/spongycastle/jce/X509Principal;
    //   85: aload_0
    //   86: getfield 41	org/spongycastle/x509/AttributeCertificateHolder:holder	Lorg/spongycastle/asn1/x509/Holder;
    //   89: invokevirtual 233	org/spongycastle/asn1/x509/Holder:getEntityName	()Lorg/spongycastle/asn1/x509/GeneralNames;
    //   92: invokespecial 273	org/spongycastle/x509/AttributeCertificateHolder:matchesDN	(Lorg/spongycastle/jce/X509Principal;Lorg/spongycastle/asn1/x509/GeneralNames;)Z
    //   95: ifeq +5 -> 100
    //   98: iconst_1
    //   99: ireturn
    //   100: aload_0
    //   101: getfield 41	org/spongycastle/x509/AttributeCertificateHolder:holder	Lorg/spongycastle/asn1/x509/Holder;
    //   104: invokevirtual 203	org/spongycastle/asn1/x509/Holder:getObjectDigestInfo	()Lorg/spongycastle/asn1/x509/ObjectDigestInfo;
    //   107: astore 4
    //   109: aload 4
    //   111: ifnull -104 -> 7
    //   114: aload_0
    //   115: invokevirtual 278	org/spongycastle/x509/AttributeCertificateHolder:getDigestAlgorithm	()Ljava/lang/String;
    //   118: ldc_w 280
    //   121: invokestatic 285	java/security/MessageDigest:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/MessageDigest;
    //   124: astore 6
    //   126: aload_0
    //   127: invokevirtual 287	org/spongycastle/x509/AttributeCertificateHolder:getDigestedObjectType	()I
    //   130: tableswitch	default:+22 -> 152, 0:+39->169, 1:+56->186
    //   153: iconst_3
    //   154: invokevirtual 290	java/security/MessageDigest:digest	()[B
    //   157: aload_0
    //   158: invokevirtual 292	org/spongycastle/x509/AttributeCertificateHolder:getObjectDigest	()[B
    //   161: invokestatic 296	org/spongycastle/util/Arrays:areEqual	([B[B)Z
    //   164: ifne -157 -> 7
    //   167: iconst_0
    //   168: ireturn
    //   169: aload 6
    //   171: aload_1
    //   172: invokevirtual 300	java/security/cert/Certificate:getPublicKey	()Ljava/security/PublicKey;
    //   175: invokeinterface 303 1 0
    //   180: invokevirtual 306	java/security/MessageDigest:update	([B)V
    //   183: goto -31 -> 152
    //   186: aload 6
    //   188: aload_1
    //   189: invokevirtual 307	java/security/cert/Certificate:getEncoded	()[B
    //   192: invokevirtual 306	java/security/MessageDigest:update	([B)V
    //   195: goto -43 -> 152
    //   198: astore 5
    //   200: iconst_0
    //   201: ireturn
    //   202: astore_3
    //   203: iconst_0
    //   204: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	205	0	this	AttributeCertificateHolder
    //   0	205	1	paramCertificate	Certificate
    //   13	69	2	localX509Certificate	X509Certificate
    //   202	1	3	localCertificateEncodingException	java.security.cert.CertificateEncodingException
    //   107	3	4	localObjectDigestInfo	ObjectDigestInfo
    //   198	1	5	localException	Exception
    //   124	63	6	localMessageDigest	java.security.MessageDigest
    // Exception table:
    //   from	to	target	type
    //   114	126	198	java/lang/Exception
    //   14	68	202	java/security/cert/CertificateEncodingException
    //   70	98	202	java/security/cert/CertificateEncodingException
    //   100	109	202	java/security/cert/CertificateEncodingException
    //   114	126	202	java/security/cert/CertificateEncodingException
    //   126	152	202	java/security/cert/CertificateEncodingException
    //   152	167	202	java/security/cert/CertificateEncodingException
    //   169	183	202	java/security/cert/CertificateEncodingException
    //   186	195	202	java/security/cert/CertificateEncodingException
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.AttributeCertificateHolder
 * JD-Core Version:    0.7.0.1
 */