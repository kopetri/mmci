package com.touchtype.startup;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SplashScreenActivity
  extends Activity
{
  public void close(View paramView)
  {
    setResult(-1);
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903117);
    showSplashScreen();
  }
  
  public void onStop()
  {
    super.onStop();
    setResult(-1);
    finish();
  }
  
  public void showSplashScreen()
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)getApplicationContext().getSystemService("layout_inflater");
    TableLayout localTableLayout = (TableLayout)findViewById(2131230931);
    String[] arrayOfString = getResources().getStringArray(2131623959);
    if ((localTableLayout != null) && (arrayOfString != null) && (arrayOfString.length > 0))
    {
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++)
      {
        String str = arrayOfString[j];
        TableRow localTableRow = (TableRow)localLayoutInflater.inflate(2130903118, null);
        ((TextView)localTableRow.findViewById(2131230932)).setText(str);
        localTableLayout.addView(localTableRow);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.startup.SplashScreenActivity
 * JD-Core Version:    0.7.0.1
 */