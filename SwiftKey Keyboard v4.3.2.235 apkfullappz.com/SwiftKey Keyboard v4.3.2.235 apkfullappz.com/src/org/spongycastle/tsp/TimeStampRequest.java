package org.spongycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.tsp.MessageImprint;
import org.spongycastle.asn1.tsp.TimeStampReq;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;

public class TimeStampRequest
{
  private static Set EMPTY_SET = Collections.unmodifiableSet(new HashSet());
  private Extensions extensions;
  private TimeStampReq req;
  
  public TimeStampRequest(InputStream paramInputStream)
    throws IOException
  {
    try
    {
      this.req = TimeStampReq.getInstance(new ASN1InputStream(paramInputStream).readObject());
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IOException("malformed request: " + localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new IOException("malformed request: " + localIllegalArgumentException);
    }
  }
  
  public TimeStampRequest(TimeStampReq paramTimeStampReq)
  {
    this.req = paramTimeStampReq;
    this.extensions = paramTimeStampReq.getExtensions();
  }
  
  public TimeStampRequest(byte[] paramArrayOfByte)
    throws IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  private Set convert(Set paramSet)
  {
    if (paramSet == null) {
      return paramSet;
    }
    HashSet localHashSet = new HashSet(paramSet.size());
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof String)) {
        localHashSet.add(new ASN1ObjectIdentifier((String)localObject));
      } else {
        localHashSet.add(localObject);
      }
    }
    return localHashSet;
  }
  
  public boolean getCertReq()
  {
    if (this.req.getCertReq() != null) {
      return this.req.getCertReq().isTrue();
    }
    return false;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    if (this.extensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(Arrays.asList(this.extensions.getCriticalExtensionOIDs())));
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.req.getEncoded();
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    if (this.extensions != null) {
      return this.extensions.getExtension(paramASN1ObjectIdentifier);
    }
    return null;
  }
  
  public List getExtensionOIDs()
  {
    return TSPUtil.getExtensionOIDs(this.extensions);
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    Extensions localExtensions = this.req.getExtensions();
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
          throw new RuntimeException("error encoding " + localException.toString());
        }
      }
    }
    return null;
  }
  
  Extensions getExtensions()
  {
    return this.extensions;
  }
  
  public ASN1ObjectIdentifier getMessageImprintAlgOID()
  {
    return this.req.getMessageImprint().getHashAlgorithm().getAlgorithm();
  }
  
  public byte[] getMessageImprintDigest()
  {
    return this.req.getMessageImprint().getHashedMessage();
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    if (this.extensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(Arrays.asList(this.extensions.getNonCriticalExtensionOIDs())));
  }
  
  public BigInteger getNonce()
  {
    if (this.req.getNonce() != null) {
      return this.req.getNonce().getValue();
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getReqPolicy()
  {
    if (this.req.getReqPolicy() != null) {
      return this.req.getReqPolicy();
    }
    return null;
  }
  
  public int getVersion()
  {
    return this.req.getVersion().getValue().intValue();
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
  
  public void validate(Set paramSet1, Set paramSet2, Set paramSet3)
    throws TSPException
  {
    Set localSet1 = convert(paramSet1);
    Set localSet2 = convert(paramSet2);
    Set localSet3 = convert(paramSet3);
    if (!localSet1.contains(getMessageImprintAlgOID())) {
      throw new TSPValidationException("request contains unknown algorithm.", 128);
    }
    if ((localSet2 != null) && (getReqPolicy() != null) && (!localSet2.contains(getReqPolicy()))) {
      throw new TSPValidationException("request contains unknown policy.", 256);
    }
    if ((getExtensions() != null) && (localSet3 != null))
    {
      Enumeration localEnumeration = getExtensions().oids();
      while (localEnumeration.hasMoreElements()) {
        if (!localSet3.contains(((DERObjectIdentifier)localEnumeration.nextElement()).getId())) {
          throw new TSPValidationException("request contains unknown extension.", 8388608);
        }
      }
    }
    if (TSPUtil.getDigestLength(getMessageImprintAlgOID().getId()) != getMessageImprintDigest().length) {
      throw new TSPValidationException("imprint digest the wrong length.", 4);
    }
  }
  
  public void validate(Set paramSet1, Set paramSet2, Set paramSet3, String paramString)
    throws TSPException, NoSuchProviderException
  {
    validate(paramSet1, paramSet2, paramSet3);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampRequest
 * JD-Core Version:    0.7.0.1
 */