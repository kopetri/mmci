package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class cw
{
  private static Context bc;
  private static cl fL;
  
  private static <T> T a(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      Object localObject = b(((ClassLoader)x.d(paramClassLoader)).loadClass(paramString));
      return localObject;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new IllegalStateException("Unable to find dynamic class " + paramString);
    }
  }
  
  public static boolean aU()
  {
    return aV() != null;
  }
  
  private static Class<?> aV()
  {
    try
    {
      Class localClass = Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    return null;
  }
  
  private static <T> T b(Class<?> paramClass)
  {
    try
    {
      Object localObject = paramClass.newInstance();
      return localObject;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new IllegalStateException("Unable to instantiate the dynamic class " + paramClass.getName());
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException("Unable to call the default constructor of " + paramClass.getName());
    }
  }
  
  public static cl g(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    x.d(paramContext);
    i(paramContext);
    if (fL == null) {
      j(paramContext);
    }
    if (fL != null) {
      return fL;
    }
    fL = cl.a.t((IBinder)a(getRemoteContext(paramContext).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
    h(paramContext);
    return fL;
  }
  
  private static Context getRemoteContext(Context paramContext)
  {
    if (bc == null) {
      if (aV() == null) {
        break label20;
      }
    }
    label20:
    for (bc = paramContext;; bc = GooglePlayServicesUtil.getRemoteContext(paramContext)) {
      return bc;
    }
  }
  
  private static void h(Context paramContext)
  {
    try
    {
      fL.a(bd.f(getRemoteContext(paramContext).getResources()), 3159100);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
  
  public static void i(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    if (!aU())
    {
      int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
      if (i != 0) {
        throw new GooglePlayServicesNotAvailableException(i);
      }
    }
  }
  
  private static void j(Context paramContext)
  {
    Class localClass = aV();
    if (localClass != null)
    {
      cw.class.getSimpleName();
      fL = (cl)b(localClass);
      h(paramContext);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.cw
 * JD-Core Version:    0.7.0.1
 */