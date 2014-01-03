package org.spongycastle.x509.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.jce.provider.X509AttrCertParser;
import org.spongycastle.jce.provider.X509CRLParser;
import org.spongycastle.jce.provider.X509CertPairParser;
import org.spongycastle.jce.provider.X509CertParser;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.AttributeCertificateHolder;
import org.spongycastle.x509.AttributeCertificateIssuer;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CRLStoreSelector;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;

public class LDAPStoreHelper
{
  private static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";
  private static String REFERRALS_IGNORE = "ignore";
  private static final String SEARCH_SECURITY_LEVEL = "none";
  private static final String URL_CONTEXT_PREFIX = "com.sun.jndi.url";
  private static int cacheSize = 32;
  private static long lifeTime = 60000L;
  private Map cacheMap = new HashMap(cacheSize);
  private X509LDAPCertStoreParameters params;
  
  public LDAPStoreHelper(X509LDAPCertStoreParameters paramX509LDAPCertStoreParameters)
  {
    this.params = paramX509LDAPCertStoreParameters;
  }
  
  /* Error */
  private void addToCache(String paramString, List paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 54	java/sql/Date
    //   5: dup
    //   6: invokestatic 60	java/lang/System:currentTimeMillis	()J
    //   9: invokespecial 63	java/sql/Date:<init>	(J)V
    //   12: astore_3
    //   13: new 65	java/util/ArrayList
    //   16: dup
    //   17: invokespecial 66	java/util/ArrayList:<init>	()V
    //   20: astore 4
    //   22: aload 4
    //   24: aload_3
    //   25: invokeinterface 72 2 0
    //   30: pop
    //   31: aload 4
    //   33: aload_2
    //   34: invokeinterface 72 2 0
    //   39: pop
    //   40: aload_0
    //   41: getfield 48	org/spongycastle/x509/util/LDAPStoreHelper:cacheMap	Ljava/util/Map;
    //   44: aload_1
    //   45: invokeinterface 77 2 0
    //   50: ifeq +19 -> 69
    //   53: aload_0
    //   54: getfield 48	org/spongycastle/x509/util/LDAPStoreHelper:cacheMap	Ljava/util/Map;
    //   57: aload_1
    //   58: aload 4
    //   60: invokeinterface 81 3 0
    //   65: pop
    //   66: aload_0
    //   67: monitorexit
    //   68: return
    //   69: aload_0
    //   70: getfield 48	org/spongycastle/x509/util/LDAPStoreHelper:cacheMap	Ljava/util/Map;
    //   73: invokeinterface 85 1 0
    //   78: getstatic 33	org/spongycastle/x509/util/LDAPStoreHelper:cacheSize	I
    //   81: if_icmplt +110 -> 191
    //   84: aload_0
    //   85: getfield 48	org/spongycastle/x509/util/LDAPStoreHelper:cacheMap	Ljava/util/Map;
    //   88: invokeinterface 89 1 0
    //   93: invokeinterface 95 1 0
    //   98: astore 9
    //   100: aload_3
    //   101: invokevirtual 98	java/sql/Date:getTime	()J
    //   104: lstore 10
    //   106: aconst_null
    //   107: astore 12
    //   109: aload 9
    //   111: invokeinterface 104 1 0
    //   116: ifeq +63 -> 179
    //   119: aload 9
    //   121: invokeinterface 108 1 0
    //   126: checkcast 110	java/util/Map$Entry
    //   129: astore 14
    //   131: aload 14
    //   133: invokeinterface 113 1 0
    //   138: checkcast 68	java/util/List
    //   141: iconst_0
    //   142: invokeinterface 117 2 0
    //   147: checkcast 54	java/sql/Date
    //   150: invokevirtual 98	java/sql/Date:getTime	()J
    //   153: lstore 15
    //   155: lload 15
    //   157: lload 10
    //   159: lcmp
    //   160: ifge -51 -> 109
    //   163: lload 15
    //   165: lstore 10
    //   167: aload 14
    //   169: invokeinterface 120 1 0
    //   174: astore 12
    //   176: goto -67 -> 109
    //   179: aload_0
    //   180: getfield 48	org/spongycastle/x509/util/LDAPStoreHelper:cacheMap	Ljava/util/Map;
    //   183: aload 12
    //   185: invokeinterface 124 2 0
    //   190: pop
    //   191: aload_0
    //   192: getfield 48	org/spongycastle/x509/util/LDAPStoreHelper:cacheMap	Ljava/util/Map;
    //   195: aload_1
    //   196: aload 4
    //   198: invokeinterface 81 3 0
    //   203: pop
    //   204: goto -138 -> 66
    //   207: astore 5
    //   209: aload_0
    //   210: monitorexit
    //   211: aload 5
    //   213: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	214	0	this	LDAPStoreHelper
    //   0	214	1	paramString	String
    //   0	214	2	paramList	List
    //   12	89	3	localDate	Date
    //   20	177	4	localArrayList	ArrayList
    //   207	5	5	localObject1	Object
    //   98	22	9	localIterator	Iterator
    //   104	62	10	l1	long
    //   107	77	12	localObject2	Object
    //   129	39	14	localEntry	java.util.Map.Entry
    //   153	11	15	l2	long
    // Exception table:
    //   from	to	target	type
    //   2	66	207	finally
    //   69	106	207	finally
    //   109	155	207	finally
    //   167	176	207	finally
    //   179	191	207	finally
    //   191	204	207	finally
  }
  
  private List attrCertSubjectSerialSearch(X509AttributeCertStoreSelector paramX509AttributeCertStoreSelector, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
    throws StoreException
  {
    ArrayList localArrayList = new ArrayList();
    HashSet localHashSet = new HashSet();
    AttributeCertificateHolder localAttributeCertificateHolder = paramX509AttributeCertStoreSelector.getHolder();
    Principal[] arrayOfPrincipal1 = null;
    if (localAttributeCertificateHolder != null)
    {
      if (paramX509AttributeCertStoreSelector.getHolder().getSerialNumber() != null) {
        localHashSet.add(paramX509AttributeCertStoreSelector.getHolder().getSerialNumber().toString());
      }
      Principal[] arrayOfPrincipal2 = paramX509AttributeCertStoreSelector.getHolder().getEntityNames();
      arrayOfPrincipal1 = null;
      if (arrayOfPrincipal2 != null) {
        arrayOfPrincipal1 = paramX509AttributeCertStoreSelector.getHolder().getEntityNames();
      }
    }
    if (paramX509AttributeCertStoreSelector.getAttributeCert() != null)
    {
      if (paramX509AttributeCertStoreSelector.getAttributeCert().getHolder().getEntityNames() != null) {
        arrayOfPrincipal1 = paramX509AttributeCertStoreSelector.getAttributeCert().getHolder().getEntityNames();
      }
      localHashSet.add(paramX509AttributeCertStoreSelector.getAttributeCert().getSerialNumber().toString());
    }
    String str1 = null;
    if (arrayOfPrincipal1 != null) {
      if (!(arrayOfPrincipal1[0] instanceof X500Principal)) {
        break label267;
      }
    }
    label267:
    for (str1 = ((X500Principal)arrayOfPrincipal1[0]).getName("RFC1779");; str1 = arrayOfPrincipal1[0].getName())
    {
      if (paramX509AttributeCertStoreSelector.getSerialNumber() != null) {
        localHashSet.add(paramX509AttributeCertStoreSelector.getSerialNumber().toString());
      }
      if (str1 == null) {
        break;
      }
      for (int i = 0; i < paramArrayOfString3.length; i++)
      {
        String str3 = parseDN(str1, paramArrayOfString3[i]);
        localArrayList.addAll(search(paramArrayOfString2, "*" + str3 + "*", paramArrayOfString1));
      }
    }
    if ((localHashSet.size() > 0) && (this.params.getSearchForSerialNumberIn() != null))
    {
      Iterator localIterator = localHashSet.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localArrayList.addAll(search(splitString(this.params.getSearchForSerialNumberIn()), str2, paramArrayOfString1));
      }
    }
    if ((localHashSet.size() == 0) && (str1 == null)) {
      localArrayList.addAll(search(paramArrayOfString2, "*", paramArrayOfString1));
    }
    return localArrayList;
  }
  
  private List cRLIssuerSearch(X509CRLStoreSelector paramX509CRLStoreSelector, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
    throws StoreException
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = null;
    HashSet localHashSet = new HashSet();
    if (paramX509CRLStoreSelector.getIssuers() != null) {
      localHashSet.addAll(paramX509CRLStoreSelector.getIssuers());
    }
    if (paramX509CRLStoreSelector.getCertificateChecking() != null) {
      localHashSet.add(getCertificateIssuer(paramX509CRLStoreSelector.getCertificateChecking()));
    }
    if (paramX509CRLStoreSelector.getAttrCertificateChecking() != null)
    {
      Principal[] arrayOfPrincipal = paramX509CRLStoreSelector.getAttrCertificateChecking().getIssuer().getPrincipals();
      for (int j = 0; j < arrayOfPrincipal.length; j++) {
        if ((arrayOfPrincipal[j] instanceof X500Principal)) {
          localHashSet.add(arrayOfPrincipal[j]);
        }
      }
    }
    Iterator localIterator = localHashSet.iterator();
    while (localIterator.hasNext())
    {
      str1 = ((X500Principal)localIterator.next()).getName("RFC1779");
      for (int i = 0; i < paramArrayOfString3.length; i++)
      {
        String str2 = parseDN(str1, paramArrayOfString3[i]);
        localArrayList.addAll(search(paramArrayOfString2, "*" + str2 + "*", paramArrayOfString1));
      }
    }
    if (str1 == null) {
      localArrayList.addAll(search(paramArrayOfString2, "*", paramArrayOfString1));
    }
    return localArrayList;
  }
  
  private List certSubjectSerialSearch(X509CertStoreSelector paramX509CertStoreSelector, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
    throws StoreException
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = getSubjectAsString(paramX509CertStoreSelector);
    BigInteger localBigInteger = paramX509CertStoreSelector.getSerialNumber();
    String str2 = null;
    if (localBigInteger != null) {
      str2 = paramX509CertStoreSelector.getSerialNumber().toString();
    }
    if (paramX509CertStoreSelector.getCertificate() != null)
    {
      str1 = paramX509CertStoreSelector.getCertificate().getSubjectX500Principal().getName("RFC1779");
      str2 = paramX509CertStoreSelector.getCertificate().getSerialNumber().toString();
    }
    if (str1 != null) {
      for (int i = 0; i < paramArrayOfString3.length; i++)
      {
        String str4 = parseDN(str1, paramArrayOfString3[i]);
        localArrayList.addAll(search(paramArrayOfString2, "*" + str4 + "*", paramArrayOfString1));
      }
    }
    if ((str2 != null) && (this.params.getSearchForSerialNumberIn() != null))
    {
      String str3 = str2;
      localArrayList.addAll(search(splitString(this.params.getSearchForSerialNumberIn()), str3, paramArrayOfString1));
    }
    if ((str2 == null) && (str1 == null)) {
      localArrayList.addAll(search(paramArrayOfString2, "*", paramArrayOfString1));
    }
    return localArrayList;
  }
  
  private DirContext connectLDAP()
    throws NamingException
  {
    Properties localProperties = new Properties();
    localProperties.setProperty("java.naming.factory.initial", LDAP_PROVIDER);
    localProperties.setProperty("java.naming.batchsize", "0");
    localProperties.setProperty("java.naming.provider.url", this.params.getLdapURL());
    localProperties.setProperty("java.naming.factory.url.pkgs", "com.sun.jndi.url");
    localProperties.setProperty("java.naming.referral", REFERRALS_IGNORE);
    localProperties.setProperty("java.naming.security.authentication", "none");
    return new InitialDirContext(localProperties);
  }
  
  private Set createAttributeCertificates(List paramList, X509AttributeCertStoreSelector paramX509AttributeCertStoreSelector)
    throws StoreException
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = paramList.iterator();
    X509AttrCertParser localX509AttrCertParser = new X509AttrCertParser();
    while (localIterator.hasNext()) {
      try
      {
        localX509AttrCertParser.engineInit(new ByteArrayInputStream((byte[])localIterator.next()));
        X509AttributeCertificate localX509AttributeCertificate = (X509AttributeCertificate)localX509AttrCertParser.engineRead();
        if (paramX509AttributeCertStoreSelector.match(localX509AttributeCertificate)) {
          localHashSet.add(localX509AttributeCertificate);
        }
      }
      catch (StreamParsingException localStreamParsingException) {}
    }
    return localHashSet;
  }
  
  private Set createCRLs(List paramList, X509CRLStoreSelector paramX509CRLStoreSelector)
    throws StoreException
  {
    HashSet localHashSet = new HashSet();
    X509CRLParser localX509CRLParser = new X509CRLParser();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      try
      {
        localX509CRLParser.engineInit(new ByteArrayInputStream((byte[])localIterator.next()));
        X509CRL localX509CRL = (X509CRL)localX509CRLParser.engineRead();
        if (paramX509CRLStoreSelector.match(localX509CRL)) {
          localHashSet.add(localX509CRL);
        }
      }
      catch (StreamParsingException localStreamParsingException) {}
    }
    return localHashSet;
  }
  
  private Set createCerts(List paramList, X509CertStoreSelector paramX509CertStoreSelector)
    throws StoreException
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = paramList.iterator();
    X509CertParser localX509CertParser = new X509CertParser();
    while (localIterator.hasNext()) {
      try
      {
        localX509CertParser.engineInit(new ByteArrayInputStream((byte[])localIterator.next()));
        X509Certificate localX509Certificate = (X509Certificate)localX509CertParser.engineRead();
        if (paramX509CertStoreSelector.match(localX509Certificate)) {
          localHashSet.add(localX509Certificate);
        }
      }
      catch (Exception localException) {}
    }
    return localHashSet;
  }
  
  private Set createCrossCertificatePairs(List paramList, X509CertPairStoreSelector paramX509CertPairStoreSelector)
    throws StoreException
  {
    HashSet localHashSet = new HashSet();
    for (int i = 0;; i++)
    {
      if (i < paramList.size()) {}
      try
      {
        X509CertPairParser localX509CertPairParser = new X509CertPairParser();
        localX509CertPairParser.engineInit(new ByteArrayInputStream((byte[])paramList.get(i)));
        localX509CertificatePair = (X509CertificatePair)localX509CertPairParser.engineRead();
        if (paramX509CertPairStoreSelector.match(localX509CertificatePair)) {
          localHashSet.add(localX509CertificatePair);
        }
      }
      catch (StreamParsingException localStreamParsingException)
      {
        for (;;)
        {
          byte[] arrayOfByte1 = (byte[])paramList.get(i);
          byte[] arrayOfByte2 = (byte[])paramList.get(i + 1);
          X509CertificatePair localX509CertificatePair = new X509CertificatePair(new CertificatePair(X509CertificateStructure.getInstance(new ASN1InputStream(arrayOfByte1).readObject()), X509CertificateStructure.getInstance(new ASN1InputStream(arrayOfByte2).readObject())));
          i++;
        }
        return localHashSet;
      }
      catch (IOException localIOException) {}catch (CertificateParsingException localCertificateParsingException) {}
    }
  }
  
  private List crossCertificatePairSubjectSearch(X509CertPairStoreSelector paramX509CertPairStoreSelector, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
    throws StoreException
  {
    ArrayList localArrayList = new ArrayList();
    X509CertStoreSelector localX509CertStoreSelector = paramX509CertPairStoreSelector.getForwardSelector();
    String str1 = null;
    if (localX509CertStoreSelector != null) {
      str1 = getSubjectAsString(paramX509CertPairStoreSelector.getForwardSelector());
    }
    if ((paramX509CertPairStoreSelector.getCertPair() != null) && (paramX509CertPairStoreSelector.getCertPair().getForward() != null)) {
      str1 = paramX509CertPairStoreSelector.getCertPair().getForward().getSubjectX500Principal().getName("RFC1779");
    }
    if (str1 != null) {
      for (int i = 0; i < paramArrayOfString3.length; i++)
      {
        String str2 = parseDN(str1, paramArrayOfString3[i]);
        localArrayList.addAll(search(paramArrayOfString2, "*" + str2 + "*", paramArrayOfString1));
      }
    }
    if (str1 == null) {
      localArrayList.addAll(search(paramArrayOfString2, "*", paramArrayOfString1));
    }
    return localArrayList;
  }
  
  private X500Principal getCertificateIssuer(X509Certificate paramX509Certificate)
  {
    return paramX509Certificate.getIssuerX500Principal();
  }
  
  private List getFromCache(String paramString)
  {
    List localList = (List)this.cacheMap.get(paramString);
    long l = System.currentTimeMillis();
    if (localList != null)
    {
      if (((Date)localList.get(0)).getTime() < l - lifeTime) {
        return null;
      }
      return (List)localList.get(1);
    }
    return null;
  }
  
  private String getSubjectAsString(X509CertStoreSelector paramX509CertStoreSelector)
  {
    try
    {
      byte[] arrayOfByte = paramX509CertStoreSelector.getSubjectAsBytes();
      if (arrayOfByte != null)
      {
        String str = new X500Principal(arrayOfByte).getName("RFC1779");
        return str;
      }
    }
    catch (IOException localIOException)
    {
      throw new StoreException("exception processing name: " + localIOException.getMessage(), localIOException);
    }
    return null;
  }
  
  private String parseDN(String paramString1, String paramString2)
  {
    int i = paramString1.toLowerCase().indexOf(paramString2.toLowerCase() + "=");
    if (i == -1) {
      return "";
    }
    String str1 = paramString1.substring(i + paramString2.length());
    int j = str1.indexOf(',');
    if (j == -1) {
      j = str1.length();
    }
    while (str1.charAt(j - 1) == '\\')
    {
      j = str1.indexOf(',', j + 1);
      if (j == -1) {
        j = str1.length();
      }
    }
    String str2 = str1.substring(0, j);
    String str3 = str2.substring(1 + str2.indexOf('='));
    if (str3.charAt(0) == ' ') {
      str3 = str3.substring(1);
    }
    if (str3.startsWith("\"")) {
      str3 = str3.substring(1);
    }
    if (str3.endsWith("\"")) {
      str3 = str3.substring(0, -1 + str3.length());
    }
    return str3;
  }
  
  /* Error */
  private List search(String[] paramArrayOfString1, String paramString, String[] paramArrayOfString2)
    throws StoreException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +63 -> 64
    //   4: aconst_null
    //   5: astore 6
    //   7: ldc_w 424
    //   10: astore 7
    //   12: iconst_0
    //   13: istore 8
    //   15: iload 8
    //   17: aload_3
    //   18: arraylength
    //   19: if_icmpge +154 -> 173
    //   22: new 179	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 416	java/lang/StringBuilder:<init>	()V
    //   29: aload 7
    //   31: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: ldc_w 455
    //   37: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: aload_3
    //   41: iload 8
    //   43: aaload
    //   44: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: ldc_w 457
    //   50: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: invokevirtual 189	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   56: astore 7
    //   58: iinc 8 1
    //   61: goto -46 -> 15
    //   64: ldc_w 424
    //   67: astore 4
    //   69: aload_2
    //   70: ldc_w 459
    //   73: invokevirtual 462	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   76: ifeq +6 -> 82
    //   79: ldc 181
    //   81: astore_2
    //   82: iconst_0
    //   83: istore 5
    //   85: iload 5
    //   87: aload_1
    //   88: arraylength
    //   89: if_icmpge +55 -> 144
    //   92: new 179	java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial 416	java/lang/StringBuilder:<init>	()V
    //   99: aload 4
    //   101: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: ldc_w 455
    //   107: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload_1
    //   111: iload 5
    //   113: aaload
    //   114: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: ldc_w 418
    //   120: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: aload_2
    //   124: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: ldc_w 464
    //   130: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: invokevirtual 189	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: astore 4
    //   138: iinc 5 1
    //   141: goto -56 -> 85
    //   144: new 179	java/lang/StringBuilder
    //   147: dup
    //   148: ldc_w 466
    //   151: invokespecial 184	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   154: aload 4
    //   156: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: ldc_w 464
    //   162: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: invokevirtual 189	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: astore 6
    //   170: goto -163 -> 7
    //   173: new 179	java/lang/StringBuilder
    //   176: dup
    //   177: ldc_w 466
    //   180: invokespecial 184	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   183: aload 7
    //   185: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: ldc_w 464
    //   191: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: invokevirtual 189	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   197: astore 9
    //   199: new 179	java/lang/StringBuilder
    //   202: dup
    //   203: ldc_w 468
    //   206: invokespecial 184	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   209: aload 6
    //   211: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: aload 9
    //   216: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: ldc_w 464
    //   222: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: invokevirtual 189	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   228: astore 10
    //   230: aload 6
    //   232: ifnonnull +7 -> 239
    //   235: aload 9
    //   237: astore 10
    //   239: aload_0
    //   240: aload 10
    //   242: invokespecial 470	org/spongycastle/x509/util/LDAPStoreHelper:getFromCache	(Ljava/lang/String;)Ljava/util/List;
    //   245: astore 11
    //   247: aload 11
    //   249: ifnull +6 -> 255
    //   252: aload 11
    //   254: areturn
    //   255: aconst_null
    //   256: astore 12
    //   258: new 65	java/util/ArrayList
    //   261: dup
    //   262: invokespecial 66	java/util/ArrayList:<init>	()V
    //   265: astore 13
    //   267: aload_0
    //   268: invokespecial 472	org/spongycastle/x509/util/LDAPStoreHelper:connectLDAP	()Ljavax/naming/directory/DirContext;
    //   271: astore 12
    //   273: new 474	javax/naming/directory/SearchControls
    //   276: dup
    //   277: invokespecial 475	javax/naming/directory/SearchControls:<init>	()V
    //   280: astore 18
    //   282: aload 18
    //   284: iconst_2
    //   285: invokevirtual 478	javax/naming/directory/SearchControls:setSearchScope	(I)V
    //   288: aload 18
    //   290: lconst_0
    //   291: invokevirtual 481	javax/naming/directory/SearchControls:setCountLimit	(J)V
    //   294: aload 18
    //   296: aload_3
    //   297: invokevirtual 485	javax/naming/directory/SearchControls:setReturningAttributes	([Ljava/lang/String;)V
    //   300: aload 12
    //   302: aload_0
    //   303: getfield 50	org/spongycastle/x509/util/LDAPStoreHelper:params	Lorg/spongycastle/jce/X509LDAPCertStoreParameters;
    //   306: invokevirtual 488	org/spongycastle/jce/X509LDAPCertStoreParameters:getBaseDN	()Ljava/lang/String;
    //   309: aload 10
    //   311: aload 18
    //   313: invokeinterface 493 4 0
    //   318: astore 19
    //   320: aload 19
    //   322: invokeinterface 498 1 0
    //   327: ifeq +81 -> 408
    //   330: aload 19
    //   332: invokeinterface 499 1 0
    //   337: checkcast 501	javax/naming/directory/SearchResult
    //   340: invokevirtual 505	javax/naming/directory/SearchResult:getAttributes	()Ljavax/naming/directory/Attributes;
    //   343: invokeinterface 511 1 0
    //   348: invokeinterface 499 1 0
    //   353: checkcast 513	javax/naming/directory/Attribute
    //   356: invokeinterface 514 1 0
    //   361: astore 21
    //   363: aload 21
    //   365: invokeinterface 517 1 0
    //   370: ifeq -50 -> 320
    //   373: aload 13
    //   375: aload 21
    //   377: invokeinterface 499 1 0
    //   382: invokeinterface 72 2 0
    //   387: pop
    //   388: goto -25 -> 363
    //   391: astore 16
    //   393: aload 12
    //   395: ifnull +10 -> 405
    //   398: aload 12
    //   400: invokeinterface 520 1 0
    //   405: aload 13
    //   407: areturn
    //   408: aload_0
    //   409: aload 10
    //   411: aload 13
    //   413: invokespecial 522	org/spongycastle/x509/util/LDAPStoreHelper:addToCache	(Ljava/lang/String;Ljava/util/List;)V
    //   416: aload 12
    //   418: ifnull -13 -> 405
    //   421: aload 12
    //   423: invokeinterface 520 1 0
    //   428: goto -23 -> 405
    //   431: astore 20
    //   433: goto -28 -> 405
    //   436: astore 14
    //   438: aload 12
    //   440: ifnull +10 -> 450
    //   443: aload 12
    //   445: invokeinterface 520 1 0
    //   450: aload 14
    //   452: athrow
    //   453: astore 17
    //   455: goto -50 -> 405
    //   458: astore 15
    //   460: goto -10 -> 450
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	463	0	this	LDAPStoreHelper
    //   0	463	1	paramArrayOfString1	String[]
    //   0	463	2	paramString	String
    //   0	463	3	paramArrayOfString2	String[]
    //   67	88	4	str1	String
    //   83	56	5	i	int
    //   5	226	6	str2	String
    //   10	174	7	str3	String
    //   13	46	8	j	int
    //   197	39	9	str4	String
    //   228	182	10	str5	String
    //   245	8	11	localList	List
    //   256	188	12	localDirContext	DirContext
    //   265	147	13	localArrayList	ArrayList
    //   436	15	14	localObject	Object
    //   458	1	15	localException1	Exception
    //   391	1	16	localNamingException	NamingException
    //   453	1	17	localException2	Exception
    //   280	32	18	localSearchControls	javax.naming.directory.SearchControls
    //   318	13	19	localNamingEnumeration1	javax.naming.NamingEnumeration
    //   431	1	20	localException3	Exception
    //   361	15	21	localNamingEnumeration2	javax.naming.NamingEnumeration
    // Exception table:
    //   from	to	target	type
    //   267	320	391	javax/naming/NamingException
    //   320	363	391	javax/naming/NamingException
    //   363	388	391	javax/naming/NamingException
    //   408	416	391	javax/naming/NamingException
    //   421	428	431	java/lang/Exception
    //   267	320	436	finally
    //   320	363	436	finally
    //   363	388	436	finally
    //   408	416	436	finally
    //   398	405	453	java/lang/Exception
    //   443	450	458	java/lang/Exception
  }
  
  private String[] splitString(String paramString)
  {
    return paramString.split("\\s+");
  }
  
  public Collection getAACertificates(X509AttributeCertStoreSelector paramX509AttributeCertStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getAACertificateAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapAACertificateAttributeName());
    String[] arrayOfString3 = splitString(this.params.getAACertificateSubjectAttributeName());
    Set localSet = createAttributeCertificates(attrCertSubjectSerialSearch(paramX509AttributeCertStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509AttributeCertStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createAttributeCertificates(attrCertSubjectSerialSearch(new X509AttributeCertStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509AttributeCertStoreSelector));
    }
    return localSet;
  }
  
  public Collection getAttributeAuthorityRevocationLists(X509CRLStoreSelector paramX509CRLStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getAttributeAuthorityRevocationListAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapAttributeAuthorityRevocationListAttributeName());
    String[] arrayOfString3 = splitString(this.params.getAttributeAuthorityRevocationListIssuerAttributeName());
    Set localSet = createCRLs(cRLIssuerSearch(paramX509CRLStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCRLs(cRLIssuerSearch(new X509CRLStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector));
    }
    return localSet;
  }
  
  public Collection getAttributeCertificateAttributes(X509AttributeCertStoreSelector paramX509AttributeCertStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getAttributeCertificateAttributeAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapAttributeCertificateAttributeAttributeName());
    String[] arrayOfString3 = splitString(this.params.getAttributeCertificateAttributeSubjectAttributeName());
    Set localSet = createAttributeCertificates(attrCertSubjectSerialSearch(paramX509AttributeCertStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509AttributeCertStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createAttributeCertificates(attrCertSubjectSerialSearch(new X509AttributeCertStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509AttributeCertStoreSelector));
    }
    return localSet;
  }
  
  public Collection getAttributeCertificateRevocationLists(X509CRLStoreSelector paramX509CRLStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getAttributeCertificateRevocationListAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapAttributeCertificateRevocationListAttributeName());
    String[] arrayOfString3 = splitString(this.params.getAttributeCertificateRevocationListIssuerAttributeName());
    Set localSet = createCRLs(cRLIssuerSearch(paramX509CRLStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCRLs(cRLIssuerSearch(new X509CRLStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector));
    }
    return localSet;
  }
  
  public Collection getAttributeDescriptorCertificates(X509AttributeCertStoreSelector paramX509AttributeCertStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getAttributeDescriptorCertificateAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapAttributeDescriptorCertificateAttributeName());
    String[] arrayOfString3 = splitString(this.params.getAttributeDescriptorCertificateSubjectAttributeName());
    Set localSet = createAttributeCertificates(attrCertSubjectSerialSearch(paramX509AttributeCertStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509AttributeCertStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createAttributeCertificates(attrCertSubjectSerialSearch(new X509AttributeCertStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509AttributeCertStoreSelector));
    }
    return localSet;
  }
  
  public Collection getAuthorityRevocationLists(X509CRLStoreSelector paramX509CRLStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getAuthorityRevocationListAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapAuthorityRevocationListAttributeName());
    String[] arrayOfString3 = splitString(this.params.getAuthorityRevocationListIssuerAttributeName());
    Set localSet = createCRLs(cRLIssuerSearch(paramX509CRLStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCRLs(cRLIssuerSearch(new X509CRLStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector));
    }
    return localSet;
  }
  
  public Collection getCACertificates(X509CertStoreSelector paramX509CertStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getCACertificateAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapCACertificateAttributeName());
    String[] arrayOfString3 = splitString(this.params.getCACertificateSubjectAttributeName());
    Set localSet = createCerts(certSubjectSerialSearch(paramX509CertStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CertStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCerts(certSubjectSerialSearch(new X509CertStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CertStoreSelector));
    }
    return localSet;
  }
  
  public Collection getCertificateRevocationLists(X509CRLStoreSelector paramX509CRLStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getCertificateRevocationListAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapCertificateRevocationListAttributeName());
    String[] arrayOfString3 = splitString(this.params.getCertificateRevocationListIssuerAttributeName());
    Set localSet = createCRLs(cRLIssuerSearch(paramX509CRLStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCRLs(cRLIssuerSearch(new X509CRLStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector));
    }
    return localSet;
  }
  
  public Collection getCrossCertificatePairs(X509CertPairStoreSelector paramX509CertPairStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getCrossCertificateAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapCrossCertificateAttributeName());
    String[] arrayOfString3 = splitString(this.params.getCrossCertificateSubjectAttributeName());
    Set localSet = createCrossCertificatePairs(crossCertificatePairSubjectSearch(paramX509CertPairStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CertPairStoreSelector);
    if (localSet.size() == 0)
    {
      X509CertStoreSelector localX509CertStoreSelector = new X509CertStoreSelector();
      X509CertPairStoreSelector localX509CertPairStoreSelector = new X509CertPairStoreSelector();
      localX509CertPairStoreSelector.setForwardSelector(localX509CertStoreSelector);
      localX509CertPairStoreSelector.setReverseSelector(localX509CertStoreSelector);
      localSet.addAll(createCrossCertificatePairs(crossCertificatePairSubjectSearch(localX509CertPairStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CertPairStoreSelector));
    }
    return localSet;
  }
  
  public Collection getDeltaCertificateRevocationLists(X509CRLStoreSelector paramX509CRLStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getDeltaRevocationListAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapDeltaRevocationListAttributeName());
    String[] arrayOfString3 = splitString(this.params.getDeltaRevocationListIssuerAttributeName());
    Set localSet = createCRLs(cRLIssuerSearch(paramX509CRLStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCRLs(cRLIssuerSearch(new X509CRLStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CRLStoreSelector));
    }
    return localSet;
  }
  
  public Collection getUserCertificates(X509CertStoreSelector paramX509CertStoreSelector)
    throws StoreException
  {
    String[] arrayOfString1 = splitString(this.params.getUserCertificateAttribute());
    String[] arrayOfString2 = splitString(this.params.getLdapUserCertificateAttributeName());
    String[] arrayOfString3 = splitString(this.params.getUserCertificateSubjectAttributeName());
    Set localSet = createCerts(certSubjectSerialSearch(paramX509CertStoreSelector, arrayOfString1, arrayOfString2, arrayOfString3), paramX509CertStoreSelector);
    if (localSet.size() == 0) {
      localSet.addAll(createCerts(certSubjectSerialSearch(new X509CertStoreSelector(), arrayOfString1, arrayOfString2, arrayOfString3), paramX509CertStoreSelector));
    }
    return localSet;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.util.LDAPStoreHelper
 * JD-Core Version:    0.7.0.1
 */