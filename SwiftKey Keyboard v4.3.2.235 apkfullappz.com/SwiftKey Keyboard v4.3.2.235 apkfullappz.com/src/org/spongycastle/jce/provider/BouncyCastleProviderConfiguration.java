package org.spongycastle.jce.provider;

import java.security.Permission;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jcajce.provider.asymmetric.ec.EC5Util;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.config.ProviderConfigurationPermission;

class BouncyCastleProviderConfiguration
  implements ProviderConfiguration
{
  private static Permission BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "threadLocalDhDefaultParams");
  private static Permission BC_DH_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "DhDefaultParams");
  private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "threadLocalEcImplicitlyCa");
  private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "ecImplicitlyCa");
  private volatile DHParameterSpec dhDefaultParams;
  private ThreadLocal dhThreadSpec = new ThreadLocal();
  private volatile org.spongycastle.jce.spec.ECParameterSpec ecImplicitCaParams;
  private ThreadLocal ecThreadSpec = new ThreadLocal();
  
  public DHParameterSpec getDHDefaultParameters()
  {
    DHParameterSpec localDHParameterSpec = (DHParameterSpec)this.dhThreadSpec.get();
    if (localDHParameterSpec != null) {
      return localDHParameterSpec;
    }
    return this.dhDefaultParams;
  }
  
  public org.spongycastle.jce.spec.ECParameterSpec getEcImplicitlyCa()
  {
    org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec = (org.spongycastle.jce.spec.ECParameterSpec)this.ecThreadSpec.get();
    if (localECParameterSpec != null) {
      return localECParameterSpec;
    }
    return this.ecImplicitCaParams;
  }
  
  void setParameter(String paramString, Object paramObject)
  {
    SecurityManager localSecurityManager = System.getSecurityManager();
    org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec;
    if (paramString.equals("threadLocalEcImplicitlyCa"))
    {
      if (localSecurityManager != null) {
        localSecurityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
      }
      if (((paramObject instanceof org.spongycastle.jce.spec.ECParameterSpec)) || (paramObject == null))
      {
        localECParameterSpec = (org.spongycastle.jce.spec.ECParameterSpec)paramObject;
        if (localECParameterSpec != null) {
          break label67;
        }
        this.ecThreadSpec.remove();
      }
    }
    label67:
    do
    {
      return;
      localECParameterSpec = EC5Util.convertSpec((java.security.spec.ECParameterSpec)paramObject, false);
      break;
      this.ecThreadSpec.set(localECParameterSpec);
      return;
      if (paramString.equals("ecImplicitlyCa"))
      {
        if (localSecurityManager != null) {
          localSecurityManager.checkPermission(BC_EC_PERMISSION);
        }
        if (((paramObject instanceof org.spongycastle.jce.spec.ECParameterSpec)) || (paramObject == null))
        {
          this.ecImplicitCaParams = ((org.spongycastle.jce.spec.ECParameterSpec)paramObject);
          return;
        }
        this.ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec)paramObject, false);
        return;
      }
      if (paramString.equals("threadLocalDhDefaultParams"))
      {
        if (localSecurityManager != null) {
          localSecurityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
        }
        DHParameterSpec localDHParameterSpec;
        if (((paramObject instanceof DHParameterSpec)) || (paramObject == null))
        {
          localDHParameterSpec = (DHParameterSpec)paramObject;
          if (localDHParameterSpec == null) {
            this.dhThreadSpec.remove();
          }
        }
        else
        {
          throw new IllegalArgumentException("not a valid DHParameterSpec");
        }
        this.dhThreadSpec.set(localDHParameterSpec);
        return;
      }
    } while (!paramString.equals("DhDefaultParams"));
    if (localSecurityManager != null) {
      localSecurityManager.checkPermission(BC_DH_PERMISSION);
    }
    if (((paramObject instanceof DHParameterSpec)) || (paramObject == null))
    {
      this.dhDefaultParams = ((DHParameterSpec)paramObject);
      return;
    }
    throw new IllegalArgumentException("not a valid DHParameterSpec");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.BouncyCastleProviderConfiguration
 * JD-Core Version:    0.7.0.1
 */