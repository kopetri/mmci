package org.spongycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.jce.X509LDAPCertStoreParameters;

public class X509LDAPCertStoreSpi
  extends CertStoreSpi
{
  private static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";
  private static String REFERRALS_IGNORE = "ignore";
  private static final String SEARCH_SECURITY_LEVEL = "none";
  private static final String URL_CONTEXT_PREFIX = "com.sun.jndi.url";
  private X509LDAPCertStoreParameters params;
  
  public X509LDAPCertStoreSpi(CertStoreParameters paramCertStoreParameters)
    throws InvalidAlgorithmParameterException
  {
    super(paramCertStoreParameters);
    if (!(paramCertStoreParameters instanceof X509LDAPCertStoreParameters)) {
      throw new InvalidAlgorithmParameterException(X509LDAPCertStoreSpi.class.getName() + ": parameter must be a " + X509LDAPCertStoreParameters.class.getName() + " object\n" + paramCertStoreParameters.toString());
    }
    this.params = ((X509LDAPCertStoreParameters)paramCertStoreParameters);
  }
  
  private Set certSubjectSerialSearch(X509CertSelector paramX509CertSelector, String[] paramArrayOfString, String paramString1, String paramString2)
    throws CertStoreException
  {
    HashSet localHashSet = new HashSet();
    try
    {
      if ((paramX509CertSelector.getSubjectAsBytes() != null) || (paramX509CertSelector.getSubjectAsString() != null) || (paramX509CertSelector.getCertificate() != null))
      {
        String str1;
        String str2;
        if (paramX509CertSelector.getCertificate() != null)
        {
          str1 = paramX509CertSelector.getCertificate().getSubjectX500Principal().getName("RFC1779");
          str2 = paramX509CertSelector.getCertificate().getSerialNumber().toString();
        }
        for (;;)
        {
          String str3 = parseDN(str1, paramString2);
          localHashSet.addAll(search(paramString1, "*" + str3 + "*", paramArrayOfString));
          if ((str2 == null) || (this.params.getSearchForSerialNumberIn() == null)) {
            break;
          }
          String str4 = str2;
          localHashSet.addAll(search(this.params.getSearchForSerialNumberIn(), "*" + str4 + "*", paramArrayOfString));
          return localHashSet;
          if (paramX509CertSelector.getSubjectAsBytes() != null)
          {
            str1 = new X500Principal(paramX509CertSelector.getSubjectAsBytes()).getName("RFC1779");
            str2 = null;
          }
          else
          {
            str1 = paramX509CertSelector.getSubjectAsString();
            str2 = null;
          }
        }
      }
      localHashSet.addAll(search(paramString1, "*", paramArrayOfString));
      return localHashSet;
    }
    catch (IOException localIOException)
    {
      throw new CertStoreException("exception processing selector: " + localIOException);
    }
    return localHashSet;
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
  
  private Set getCACertificates(X509CertSelector paramX509CertSelector)
    throws CertStoreException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.params.getCACertificateAttribute();
    Set localSet = certSubjectSerialSearch(paramX509CertSelector, arrayOfString, this.params.getLdapCACertificateAttributeName(), this.params.getCACertificateSubjectAttributeName());
    if (localSet.isEmpty()) {
      localSet.addAll(search(null, "*", arrayOfString));
    }
    return localSet;
  }
  
  private Set getCrossCertificates(X509CertSelector paramX509CertSelector)
    throws CertStoreException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.params.getCrossCertificateAttribute();
    Set localSet = certSubjectSerialSearch(paramX509CertSelector, arrayOfString, this.params.getLdapCrossCertificateAttributeName(), this.params.getCrossCertificateSubjectAttributeName());
    if (localSet.isEmpty()) {
      localSet.addAll(search(null, "*", arrayOfString));
    }
    return localSet;
  }
  
  private Set getEndCertificates(X509CertSelector paramX509CertSelector)
    throws CertStoreException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.params.getUserCertificateAttribute();
    return certSubjectSerialSearch(paramX509CertSelector, arrayOfString, this.params.getLdapUserCertificateAttributeName(), this.params.getUserCertificateSubjectAttributeName());
  }
  
  private String parseDN(String paramString1, String paramString2)
  {
    String str1 = paramString1.substring(paramString1.toLowerCase().indexOf(paramString2.toLowerCase()) + paramString2.length());
    int i = str1.indexOf(',');
    if (i == -1) {
      i = str1.length();
    }
    while (str1.charAt(i - 1) == '\\')
    {
      i = str1.indexOf(',', i + 1);
      if (i == -1) {
        i = str1.length();
      }
    }
    String str2 = str1.substring(0, i);
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
  
  private Set search(String paramString1, String paramString2, String[] paramArrayOfString)
    throws CertStoreException
  {
    String str1 = paramString1 + "=" + paramString2;
    if (paramString1 == null) {
      str1 = null;
    }
    localDirContext = null;
    localHashSet = new HashSet();
    try
    {
      localDirContext = connectLDAP();
      SearchControls localSearchControls = new SearchControls();
      localSearchControls.setSearchScope(2);
      localSearchControls.setCountLimit(0L);
      int i = 0;
      for (;;)
      {
        if (i < paramArrayOfString.length)
        {
          String[] arrayOfString = new String[1];
          arrayOfString[0] = paramArrayOfString[i];
          localSearchControls.setReturningAttributes(arrayOfString);
          String str2 = "(&(" + str1 + ")(" + arrayOfString[0] + "=*))";
          if (str1 == null) {
            str2 = "(" + arrayOfString[0] + "=*)";
          }
          NamingEnumeration localNamingEnumeration1 = localDirContext.search(this.params.getBaseDN(), str2, localSearchControls);
          while (localNamingEnumeration1.hasMoreElements())
          {
            NamingEnumeration localNamingEnumeration2 = ((Attribute)((SearchResult)localNamingEnumeration1.next()).getAttributes().getAll().next()).getAll();
            while (localNamingEnumeration2.hasMore()) {
              localHashSet.add(localNamingEnumeration2.next());
            }
          }
        }
        try
        {
          localDirContext.close();
          throw localObject;
          i++;
          continue;
          if (localDirContext != null) {}
          try
          {
            localDirContext.close();
            return localHashSet;
          }
          catch (Exception localException3)
          {
            return localHashSet;
          }
        }
        catch (Exception localException1)
        {
          break label307;
        }
      }
    }
    catch (Exception localException2)
    {
      throw new CertStoreException("Error getting results from LDAP directory " + localException2);
    }
    finally
    {
      if (localDirContext == null) {}
    }
  }
  
  public Collection engineGetCRLs(CRLSelector paramCRLSelector)
    throws CertStoreException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.params.getCertificateRevocationListAttribute();
    if (!(paramCRLSelector instanceof X509CRLSelector)) {
      throw new CertStoreException("selector is not a X509CRLSelector");
    }
    X509CRLSelector localX509CRLSelector = (X509CRLSelector)paramCRLSelector;
    localHashSet1 = new HashSet();
    String str1 = this.params.getLdapCertificateRevocationListAttributeName();
    HashSet localHashSet2 = new HashSet();
    if (localX509CRLSelector.getIssuerNames() != null)
    {
      Iterator localIterator2 = localX509CRLSelector.getIssuerNames().iterator();
      if (localIterator2.hasNext())
      {
        Object localObject = localIterator2.next();
        String str4;
        if ((localObject instanceof String)) {
          str4 = this.params.getCertificateRevocationListIssuerAttributeName();
        }
        String str2;
        for (String str3 = parseDN((String)localObject, str4);; str3 = parseDN(new X500Principal((byte[])localObject).getName("RFC1779"), str2))
        {
          localHashSet2.addAll(search(str1, "*" + str3 + "*", arrayOfString));
          break;
          str2 = this.params.getCertificateRevocationListIssuerAttributeName();
        }
      }
    }
    else
    {
      localHashSet2.addAll(search(str1, "*", arrayOfString));
    }
    localHashSet2.addAll(search(null, "*", arrayOfString));
    Iterator localIterator1 = localHashSet2.iterator();
    try
    {
      CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
      while (localIterator1.hasNext())
      {
        CRL localCRL = localCertificateFactory.generateCRL(new ByteArrayInputStream((byte[])localIterator1.next()));
        if (localX509CRLSelector.match(localCRL)) {
          localHashSet1.add(localCRL);
        }
      }
      return localHashSet1;
    }
    catch (Exception localException)
    {
      throw new CertStoreException("CRL cannot be constructed from LDAP result " + localException);
    }
  }
  
  public Collection engineGetCertificates(CertSelector paramCertSelector)
    throws CertStoreException
  {
    if (!(paramCertSelector instanceof X509CertSelector)) {
      throw new CertStoreException("selector is not a X509CertSelector");
    }
    X509CertSelector localX509CertSelector = (X509CertSelector)paramCertSelector;
    HashSet localHashSet = new HashSet();
    Set localSet = getEndCertificates(localX509CertSelector);
    localSet.addAll(getCACertificates(localX509CertSelector));
    localSet.addAll(getCrossCertificates(localX509CertSelector));
    Iterator localIterator1 = localSet.iterator();
    for (;;)
    {
      try
      {
        localCertificateFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
        if (localIterator1.hasNext())
        {
          arrayOfByte = (byte[])localIterator1.next();
          if ((arrayOfByte == null) || (arrayOfByte.length == 0)) {
            continue;
          }
          localArrayList = new ArrayList();
          localArrayList.add(arrayOfByte);
        }
      }
      catch (Exception localException1)
      {
        CertificateFactory localCertificateFactory;
        byte[] arrayOfByte;
        ArrayList localArrayList;
        CertificatePair localCertificatePair;
        Iterator localIterator2;
        ByteArrayInputStream localByteArrayInputStream;
        throw new CertStoreException("certificate cannot be constructed from LDAP result: " + localException1);
      }
      try
      {
        localCertificatePair = CertificatePair.getInstance(new ASN1InputStream(arrayOfByte).readObject());
        localArrayList.clear();
        if (localCertificatePair.getForward() != null) {
          localArrayList.add(localCertificatePair.getForward().getEncoded());
        }
        if (localCertificatePair.getReverse() != null) {
          localArrayList.add(localCertificatePair.getReverse().getEncoded());
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        continue;
      }
      catch (IOException localIOException)
      {
        continue;
      }
      localIterator2 = localArrayList.iterator();
      if (localIterator2.hasNext())
      {
        localByteArrayInputStream = new ByteArrayInputStream((byte[])localIterator2.next());
        try
        {
          Certificate localCertificate = localCertificateFactory.generateCertificate(localByteArrayInputStream);
          if (!localX509CertSelector.match(localCertificate)) {
            continue;
          }
          localHashSet.add(localCertificate);
        }
        catch (Exception localException2) {}
      }
    }
    return localHashSet;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509LDAPCertStoreSpi
 * JD-Core Version:    0.7.0.1
 */