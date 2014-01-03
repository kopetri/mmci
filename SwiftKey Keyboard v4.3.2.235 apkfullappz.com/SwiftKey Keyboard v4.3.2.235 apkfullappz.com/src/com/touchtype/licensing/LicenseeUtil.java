package com.touchtype.licensing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.touchtype.common.crypto.AESNoSaltObfuscator;
import com.touchtype.crypto.AssetsObfuscator;
import com.touchtype.errorhandling.UnrecoverableError;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public final class LicenseeUtil
{
  public static String getCustomer(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("Licensee", 0);
    str1 = paramContext.getString(2131296338);
    localObject1 = localSharedPreferences.getString("customer", "");
    if ((((String)localObject1).equals("")) && (!str1.equals(""))) {
      localInputStream = null;
    }
    try
    {
      localInputStream = paramContext.getAssets().open(str1);
      String str2 = new AssetsObfuscator().unobfuscate(new String(ByteStreams.toByteArray(localInputStream), "UTF-8"));
      localObject1 = str2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localObject1 = str1;
        Closeables.closeQuietly(localInputStream);
      }
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new UnrecoverableError("Customer tag compromised.", localGeneralSecurityException);
    }
    finally
    {
      Closeables.closeQuietly(localInputStream);
    }
    localSharedPreferences.edit().putString("customer", (String)localObject1).commit();
    return localObject1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.licensing.LicenseeUtil
 * JD-Core Version:    0.7.0.1
 */