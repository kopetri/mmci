package com.touchtype.installer;

import android.content.res.AssetManager;
import android.content.res.Resources;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class EulaLicenceView
  extends LicenceViewBase
{
  private static final String TAG = EulaLicenceView.class.getSimpleName();
  
  protected void setWebviewData()
  {
    AssetManager localAssetManager = getResources().getAssets();
    try
    {
      String str3 = IOUtils.toString(localAssetManager.open("eula.txt"));
      str1 = str3;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        Object[] arrayOfObject;
        String str2;
        LogUtil.e(TAG, "Could not load EULA!", localIOException);
        String str1 = null;
      }
    }
    if (str1 != null)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mProductName;
      str2 = String.format(str1, arrayOfObject);
      this.mWebview.loadData(escapeWebViewContent(str2), "text/html", "utf-8");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.EulaLicenceView
 * JD-Core Version:    0.7.0.1
 */