package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.util.Selector;

public class X509AttributeCertStoreSelector
  implements Selector
{
  private X509AttributeCertificate attributeCert;
  private Date attributeCertificateValid;
  private AttributeCertificateHolder holder;
  private AttributeCertificateIssuer issuer;
  private BigInteger serialNumber;
  private Collection targetGroups = new HashSet();
  private Collection targetNames = new HashSet();
  
  private Set extractGeneralNames(Collection paramCollection)
    throws IOException
  {
    HashSet localHashSet;
    if ((paramCollection == null) || (paramCollection.isEmpty())) {
      localHashSet = new HashSet();
    }
    for (;;)
    {
      return localHashSet;
      localHashSet = new HashSet();
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        if ((localObject instanceof GeneralName)) {
          localHashSet.add(localObject);
        } else {
          localHashSet.add(GeneralName.getInstance(ASN1Primitive.fromByteArray((byte[])localObject)));
        }
      }
    }
  }
  
  public void addTargetGroup(GeneralName paramGeneralName)
  {
    this.targetGroups.add(paramGeneralName);
  }
  
  public void addTargetGroup(byte[] paramArrayOfByte)
    throws IOException
  {
    addTargetGroup(GeneralName.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte)));
  }
  
  public void addTargetName(GeneralName paramGeneralName)
  {
    this.targetNames.add(paramGeneralName);
  }
  
  public void addTargetName(byte[] paramArrayOfByte)
    throws IOException
  {
    addTargetName(GeneralName.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte)));
  }
  
  public Object clone()
  {
    X509AttributeCertStoreSelector localX509AttributeCertStoreSelector = new X509AttributeCertStoreSelector();
    localX509AttributeCertStoreSelector.attributeCert = this.attributeCert;
    localX509AttributeCertStoreSelector.attributeCertificateValid = getAttributeCertificateValid();
    localX509AttributeCertStoreSelector.holder = this.holder;
    localX509AttributeCertStoreSelector.issuer = this.issuer;
    localX509AttributeCertStoreSelector.serialNumber = this.serialNumber;
    localX509AttributeCertStoreSelector.targetGroups = getTargetGroups();
    localX509AttributeCertStoreSelector.targetNames = getTargetNames();
    return localX509AttributeCertStoreSelector;
  }
  
  public X509AttributeCertificate getAttributeCert()
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
    return Collections.unmodifiableCollection(this.targetGroups);
  }
  
  public Collection getTargetNames()
  {
    return Collections.unmodifiableCollection(this.targetNames);
  }
  
  /* Error */
  public boolean match(Object paramObject)
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 137
    //   4: ifne +5 -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: aload_1
    //   10: checkcast 137	org/spongycastle/x509/X509AttributeCertificate
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 86	org/spongycastle/x509/X509AttributeCertStoreSelector:attributeCert	Lorg/spongycastle/x509/X509AttributeCertificate;
    //   18: ifnull +16 -> 34
    //   21: aload_0
    //   22: getfield 86	org/spongycastle/x509/X509AttributeCertStoreSelector:attributeCert	Lorg/spongycastle/x509/X509AttributeCertificate;
    //   25: aload_2
    //   26: invokevirtual 140	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   29: ifne +5 -> 34
    //   32: iconst_0
    //   33: ireturn
    //   34: aload_0
    //   35: getfield 98	org/spongycastle/x509/X509AttributeCertStoreSelector:serialNumber	Ljava/math/BigInteger;
    //   38: ifnull +21 -> 59
    //   41: aload_2
    //   42: invokeinterface 142 1 0
    //   47: aload_0
    //   48: getfield 98	org/spongycastle/x509/X509AttributeCertStoreSelector:serialNumber	Ljava/math/BigInteger;
    //   51: invokevirtual 145	java/math/BigInteger:equals	(Ljava/lang/Object;)Z
    //   54: ifne +5 -> 59
    //   57: iconst_0
    //   58: ireturn
    //   59: aload_0
    //   60: getfield 94	org/spongycastle/x509/X509AttributeCertStoreSelector:holder	Lorg/spongycastle/x509/AttributeCertificateHolder;
    //   63: ifnull +21 -> 84
    //   66: aload_2
    //   67: invokeinterface 147 1 0
    //   72: aload_0
    //   73: getfield 94	org/spongycastle/x509/X509AttributeCertStoreSelector:holder	Lorg/spongycastle/x509/AttributeCertificateHolder;
    //   76: invokevirtual 150	org/spongycastle/x509/AttributeCertificateHolder:equals	(Ljava/lang/Object;)Z
    //   79: ifne +5 -> 84
    //   82: iconst_0
    //   83: ireturn
    //   84: aload_0
    //   85: getfield 96	org/spongycastle/x509/X509AttributeCertStoreSelector:issuer	Lorg/spongycastle/x509/AttributeCertificateIssuer;
    //   88: ifnull +21 -> 109
    //   91: aload_2
    //   92: invokeinterface 152 1 0
    //   97: aload_0
    //   98: getfield 96	org/spongycastle/x509/X509AttributeCertStoreSelector:issuer	Lorg/spongycastle/x509/AttributeCertificateIssuer;
    //   101: invokevirtual 155	org/spongycastle/x509/AttributeCertificateIssuer:equals	(Ljava/lang/Object;)Z
    //   104: ifne +5 -> 109
    //   107: iconst_0
    //   108: ireturn
    //   109: aload_0
    //   110: getfield 92	org/spongycastle/x509/X509AttributeCertStoreSelector:attributeCertificateValid	Ljava/util/Date;
    //   113: ifnull +13 -> 126
    //   116: aload_2
    //   117: aload_0
    //   118: getfield 92	org/spongycastle/x509/X509AttributeCertStoreSelector:attributeCertificateValid	Ljava/util/Date;
    //   121: invokeinterface 159 2 0
    //   126: aload_0
    //   127: getfield 28	org/spongycastle/x509/X509AttributeCertStoreSelector:targetNames	Ljava/util/Collection;
    //   130: invokeinterface 40 1 0
    //   135: ifeq +15 -> 150
    //   138: aload_0
    //   139: getfield 30	org/spongycastle/x509/X509AttributeCertStoreSelector:targetGroups	Ljava/util/Collection;
    //   142: invokeinterface 40 1 0
    //   147: ifne +252 -> 399
    //   150: aload_2
    //   151: getstatic 165	org/spongycastle/asn1/x509/X509Extensions:TargetInformation	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   154: invokevirtual 171	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   157: invokeinterface 175 2 0
    //   162: astore_3
    //   163: aload_3
    //   164: ifnull +235 -> 399
    //   167: new 177	org/spongycastle/asn1/ASN1InputStream
    //   170: dup
    //   171: aload_3
    //   172: invokestatic 180	org/spongycastle/asn1/DEROctetString:fromByteArray	([B)Lorg/spongycastle/asn1/ASN1Primitive;
    //   175: checkcast 179	org/spongycastle/asn1/DEROctetString
    //   178: invokevirtual 184	org/spongycastle/asn1/DEROctetString:getOctets	()[B
    //   181: invokespecial 186	org/spongycastle/asn1/ASN1InputStream:<init>	([B)V
    //   184: invokevirtual 190	org/spongycastle/asn1/ASN1InputStream:readObject	()Lorg/spongycastle/asn1/ASN1Primitive;
    //   187: invokestatic 195	org/spongycastle/asn1/x509/TargetInformation:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/TargetInformation;
    //   190: astore 6
    //   192: aload 6
    //   194: invokevirtual 199	org/spongycastle/asn1/x509/TargetInformation:getTargetsObjects	()[Lorg/spongycastle/asn1/x509/Targets;
    //   197: astore 7
    //   199: aload_0
    //   200: getfield 28	org/spongycastle/x509/X509AttributeCertStoreSelector:targetNames	Ljava/util/Collection;
    //   203: invokeinterface 40 1 0
    //   208: ifne +99 -> 307
    //   211: iconst_0
    //   212: istore 12
    //   214: iconst_0
    //   215: istore 13
    //   217: iload 13
    //   219: aload 7
    //   221: arraylength
    //   222: if_icmpge +78 -> 300
    //   225: aload 7
    //   227: iload 13
    //   229: aaload
    //   230: invokevirtual 205	org/spongycastle/asn1/x509/Targets:getTargets	()[Lorg/spongycastle/asn1/x509/Target;
    //   233: astore 14
    //   235: iconst_0
    //   236: istore 15
    //   238: iload 15
    //   240: aload 14
    //   242: arraylength
    //   243: if_icmpge +29 -> 272
    //   246: aload_0
    //   247: getfield 28	org/spongycastle/x509/X509AttributeCertStoreSelector:targetNames	Ljava/util/Collection;
    //   250: aload 14
    //   252: iload 15
    //   254: aaload
    //   255: invokevirtual 211	org/spongycastle/asn1/x509/Target:getTargetName	()Lorg/spongycastle/asn1/x509/GeneralName;
    //   258: invokestatic 73	org/spongycastle/asn1/x509/GeneralName:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/GeneralName;
    //   261: invokeinterface 214 2 0
    //   266: ifeq +28 -> 294
    //   269: iconst_1
    //   270: istore 12
    //   272: iinc 13 1
    //   275: goto -58 -> 217
    //   278: astore 17
    //   280: iconst_0
    //   281: ireturn
    //   282: astore 16
    //   284: iconst_0
    //   285: ireturn
    //   286: astore 5
    //   288: iconst_0
    //   289: ireturn
    //   290: astore 4
    //   292: iconst_0
    //   293: ireturn
    //   294: iinc 15 1
    //   297: goto -59 -> 238
    //   300: iload 12
    //   302: ifne +5 -> 307
    //   305: iconst_0
    //   306: ireturn
    //   307: aload_0
    //   308: getfield 30	org/spongycastle/x509/X509AttributeCertStoreSelector:targetGroups	Ljava/util/Collection;
    //   311: invokeinterface 40 1 0
    //   316: ifne +83 -> 399
    //   319: iconst_0
    //   320: istore 8
    //   322: iconst_0
    //   323: istore 9
    //   325: iload 9
    //   327: aload 7
    //   329: arraylength
    //   330: if_icmpge +62 -> 392
    //   333: aload 7
    //   335: iload 9
    //   337: aaload
    //   338: invokevirtual 205	org/spongycastle/asn1/x509/Targets:getTargets	()[Lorg/spongycastle/asn1/x509/Target;
    //   341: astore 10
    //   343: iconst_0
    //   344: istore 11
    //   346: iload 11
    //   348: aload 10
    //   350: arraylength
    //   351: if_icmpge +29 -> 380
    //   354: aload_0
    //   355: getfield 30	org/spongycastle/x509/X509AttributeCertStoreSelector:targetGroups	Ljava/util/Collection;
    //   358: aload 10
    //   360: iload 11
    //   362: aaload
    //   363: invokevirtual 217	org/spongycastle/asn1/x509/Target:getTargetGroup	()Lorg/spongycastle/asn1/x509/GeneralName;
    //   366: invokestatic 73	org/spongycastle/asn1/x509/GeneralName:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/GeneralName;
    //   369: invokeinterface 214 2 0
    //   374: ifeq +12 -> 386
    //   377: iconst_1
    //   378: istore 8
    //   380: iinc 9 1
    //   383: goto -58 -> 325
    //   386: iinc 11 1
    //   389: goto -43 -> 346
    //   392: iload 8
    //   394: ifne +5 -> 399
    //   397: iconst_0
    //   398: ireturn
    //   399: iconst_1
    //   400: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	401	0	this	X509AttributeCertStoreSelector
    //   0	401	1	paramObject	Object
    //   13	138	2	localX509AttributeCertificate	X509AttributeCertificate
    //   162	10	3	arrayOfByte	byte[]
    //   290	1	4	localIllegalArgumentException	java.lang.IllegalArgumentException
    //   286	1	5	localIOException	IOException
    //   190	3	6	localTargetInformation	org.spongycastle.asn1.x509.TargetInformation
    //   197	137	7	arrayOfTargets	org.spongycastle.asn1.x509.Targets[]
    //   320	73	8	i	int
    //   323	58	9	j	int
    //   341	18	10	arrayOfTarget1	org.spongycastle.asn1.x509.Target[]
    //   344	43	11	k	int
    //   212	89	12	m	int
    //   215	58	13	n	int
    //   233	18	14	arrayOfTarget2	org.spongycastle.asn1.x509.Target[]
    //   236	59	15	i1	int
    //   282	1	16	localCertificateNotYetValidException	java.security.cert.CertificateNotYetValidException
    //   278	1	17	localCertificateExpiredException	java.security.cert.CertificateExpiredException
    // Exception table:
    //   from	to	target	type
    //   116	126	278	java/security/cert/CertificateExpiredException
    //   116	126	282	java/security/cert/CertificateNotYetValidException
    //   167	192	286	java/io/IOException
    //   167	192	290	java/lang/IllegalArgumentException
  }
  
  public void setAttributeCert(X509AttributeCertificate paramX509AttributeCertificate)
  {
    this.attributeCert = paramX509AttributeCertificate;
  }
  
  public void setAttributeCertificateValid(Date paramDate)
  {
    if (paramDate != null)
    {
      this.attributeCertificateValid = new Date(paramDate.getTime());
      return;
    }
    this.attributeCertificateValid = null;
  }
  
  public void setHolder(AttributeCertificateHolder paramAttributeCertificateHolder)
  {
    this.holder = paramAttributeCertificateHolder;
  }
  
  public void setIssuer(AttributeCertificateIssuer paramAttributeCertificateIssuer)
  {
    this.issuer = paramAttributeCertificateIssuer;
  }
  
  public void setSerialNumber(BigInteger paramBigInteger)
  {
    this.serialNumber = paramBigInteger;
  }
  
  public void setTargetGroups(Collection paramCollection)
    throws IOException
  {
    this.targetGroups = extractGeneralNames(paramCollection);
  }
  
  public void setTargetNames(Collection paramCollection)
    throws IOException
  {
    this.targetNames = extractGeneralNames(paramCollection);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509AttributeCertStoreSelector
 * JD-Core Version:    0.7.0.1
 */