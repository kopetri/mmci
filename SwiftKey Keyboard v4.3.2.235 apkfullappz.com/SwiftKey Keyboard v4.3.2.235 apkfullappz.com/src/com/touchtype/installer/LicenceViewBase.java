package com.touchtype.installer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LicenceViewBase
  extends Activity
{
  protected String mProductName;
  protected ScrollerAwareWebView mWebview;
  
  protected static String escapeWebViewContent(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString, "utf-8").replaceAll("\\+", "%20");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return paramString;
  }
  
  public void exitFail(View paramView)
  {
    setResult(0);
    finish();
  }
  
  public void exitSuccess(View paramView)
  {
    setResult(-1);
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903072);
    this.mWebview = ((ScrollerAwareWebView)findViewById(2131230823));
    this.mProductName = getString(2131296303);
    setWebviewData();
    String str = getIntent().getAction();
    if ((str != null) && (str.contentEquals("android.intent.action.VIEW")))
    {
      Button localButton1 = (Button)findViewById(2131230825);
      Button localButton2 = (Button)findViewById(2131230824);
      localButton1.setVisibility(8);
      localButton2.setText(2131296392);
    }
  }
  
  protected void setWebviewData()
  {
    this.mWebview.loadData(escapeWebViewContent("<html><head></head><body><h1>test</h1></body></html>"), "text/html", "utf-8");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.LicenceViewBase
 * JD-Core Version:    0.7.0.1
 */