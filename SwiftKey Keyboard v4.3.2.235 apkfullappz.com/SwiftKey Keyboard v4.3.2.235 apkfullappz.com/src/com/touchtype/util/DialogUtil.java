package com.touchtype.util;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public final class DialogUtil
{
  public static void prepareIMEDialog(Dialog paramDialog, View paramView)
  {
    Window localWindow = paramDialog.getWindow();
    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
    localLayoutParams.token = paramView.getWindowToken();
    localLayoutParams.type = 1003;
    localWindow.setAttributes(localLayoutParams);
    localWindow.addFlags(131072);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.DialogUtil
 * JD-Core Version:    0.7.0.1
 */