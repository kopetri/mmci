package org.spongycastle.jcajce.provider.config;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public abstract interface ConfigurableProvider
{
  public static final String DH_DEFAULT_PARAMS = "DhDefaultParams";
  public static final String EC_IMPLICITLY_CA = "ecImplicitlyCa";
  public static final String THREAD_LOCAL_DH_DEFAULT_PARAMS = "threadLocalDhDefaultParams";
  public static final String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";
  
  public abstract void addAlgorithm(String paramString1, String paramString2);
  
  public abstract void addKeyInfoConverter(ASN1ObjectIdentifier paramASN1ObjectIdentifier, AsymmetricKeyInfoConverter paramAsymmetricKeyInfoConverter);
  
  public abstract AsymmetricKeyInfoConverter getConverter(ASN1ObjectIdentifier paramASN1ObjectIdentifier);
  
  public abstract boolean hasAlgorithm(String paramString1, String paramString2);
  
  public abstract void setParameter(String paramString, Object paramObject);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.config.ConfigurableProvider
 * JD-Core Version:    0.7.0.1
 */