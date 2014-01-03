package com.touchtype.installer;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.webkit.WebSettings;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class OssLicencesView
  extends LicenceViewBase
{
  private static final String TAG = OssLicencesView.class.getSimpleName();
  
  protected void setWebviewData()
  {
    super.setWebviewData();
    AssetManager localAssetManager = getResources().getAssets();
    try
    {
      String str2 = IOUtils.toString(localAssetManager.open("oss_licences.html"));
      str1 = str2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Could not load OSS Licences!", localIOException);
        String str1 = null;
      }
    }
    if (str1 != null)
    {
      this.mWebview.getSettings().setUseWideViewPort(true);
      this.mWebview.getSettings().setBuiltInZoomControls(true);
      this.mWebview.loadData(escapeWebViewContent(str1), "text/html", "utf-8");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.OssLicencesView
 * JD-Core Version:    0.7.0.1
 */