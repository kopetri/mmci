package org.spongycastle.jcajce.provider.config;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import org.spongycastle.util.Strings;

public class ProviderConfigurationPermission
  extends BasicPermission
{
  private static final int ALL = 15;
  private static final String ALL_STR = "all";
  private static final int DH_DEFAULT_PARAMS = 8;
  private static final String DH_DEFAULT_PARAMS_STR = "dhdefaultparams";
  private static final int EC_IMPLICITLY_CA = 2;
  private static final String EC_IMPLICITLY_CA_STR = "ecimplicitlyca";
  private static final int THREAD_LOCAL_DH_DEFAULT_PARAMS = 4;
  private static final String THREAD_LOCAL_DH_DEFAULT_PARAMS_STR = "threadlocaldhdefaultparams";
  private static final int THREAD_LOCAL_EC_IMPLICITLY_CA = 1;
  private static final String THREAD_LOCAL_EC_IMPLICITLY_CA_STR = "threadlocalecimplicitlyca";
  private final String actions;
  private final int permissionMask;
  
  public ProviderConfigurationPermission(String paramString)
  {
    super(paramString);
    this.actions = "all";
    this.permissionMask = 15;
  }
  
  public ProviderConfigurationPermission(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
    this.actions = paramString2;
    this.permissionMask = calculateMask(paramString2);
  }
  
  private int calculateMask(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(Strings.toLowerCase(paramString), " ,");
    int i = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (str.equals("threadlocalecimplicitlyca")) {
        i |= 0x1;
      } else if (str.equals("ecimplicitlyca")) {
        i |= 0x2;
      } else if (str.equals("threadlocaldhdefaultparams")) {
        i |= 0x4;
      } else if (str.equals("dhdefaultparams")) {
        i |= 0x8;
      } else if (str.equals("all")) {
        i |= 0xF;
      }
    }
    if (i == 0) {
      throw new IllegalArgumentException("unknown permissions passed to mask");
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    ProviderConfigurationPermission localProviderConfigurationPermission;
    do
    {
      return true;
      if (!(paramObject instanceof ProviderConfigurationPermission)) {
        break;
      }
      localProviderConfigurationPermission = (ProviderConfigurationPermission)paramObject;
    } while ((this.permissionMask == localProviderConfigurationPermission.permissionMask) && (getName().equals(localProviderConfigurationPermission.getName())));
    return false;
    return false;
  }
  
  public String getActions()
  {
    return this.actions;
  }
  
  public int hashCode()
  {
    return getName().hashCode() + this.permissionMask;
  }
  
  public boolean implies(Permission paramPermission)
  {
    if (!(paramPermission instanceof ProviderConfigurationPermission)) {}
    ProviderConfigurationPermission localProviderConfigurationPermission;
    do
    {
      do
      {
        return false;
      } while (!getName().equals(paramPermission.getName()));
      localProviderConfigurationPermission = (ProviderConfigurationPermission)paramPermission;
    } while ((this.permissionMask & localProviderConfigurationPermission.permissionMask) != localProviderConfigurationPermission.permissionMask);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.config.ProviderConfigurationPermission
 * JD-Core Version:    0.7.0.1
 */