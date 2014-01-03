package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class z
{
  private static Context bc;
  private static v bd;
  
  public static View d(Context paramContext, int paramInt1, int paramInt2)
    throws z.a
  {
    v localv = f(paramContext);
    try
    {
      bc localbc = localv.a(bd.f(paramContext), paramInt1, paramInt2);
      return (View)bd.a(localbc);
    }
    catch (Exception localException)
    {
      throw new a("Could not get button with size " + paramInt1 + " and color " + paramInt2);
    }
  }
  
  private static v f(Context paramContext)
    throws z.a
  {
    x.d(paramContext);
    ClassLoader localClassLoader;
    if (bd == null)
    {
      if (bc == null)
      {
        Context localContext = GooglePlayServicesUtil.getRemoteContext(paramContext);
        bc = localContext;
        if (localContext == null) {
          throw new a("Could not get remote context.");
        }
      }
      localClassLoader = bc.getClassLoader();
    }
    try
    {
      bd = v.a.i((IBinder)localClassLoader.loadClass("com.google.android.gms.common.ui.SignInButtonCreatorImpl").newInstance());
      return bd;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new a("Could not load creator class.");
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new a("Could not instantiate creator.");
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new a("Could not access creator.");
    }
  }
  
  public static final class a
    extends Exception
  {
    public a(String paramString)
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.z
 * JD-Core Version:    0.7.0.1
 */