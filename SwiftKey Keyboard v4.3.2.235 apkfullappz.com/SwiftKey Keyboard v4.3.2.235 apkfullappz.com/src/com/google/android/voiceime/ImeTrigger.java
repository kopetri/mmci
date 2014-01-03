package com.google.android.voiceime;

import android.app.Dialog;
import android.content.ComponentName;
import android.inputmethodservice.InputMethodService;
import android.os.Build.VERSION;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class ImeTrigger
  implements Trigger
{
  private final InputMethodService mInputMethodService;
  
  public ImeTrigger(InputMethodService paramInputMethodService)
  {
    this.mInputMethodService = paramInputMethodService;
  }
  
  private static InputMethodManager getInputMethodManager(InputMethodService paramInputMethodService)
  {
    return (InputMethodManager)paramInputMethodService.getSystemService("input_method");
  }
  
  private static InputMethodInfo getVoiceImeInputMethodInfo(InputMethodManager paramInputMethodManager)
    throws SecurityException, IllegalArgumentException
  {
    Iterator localIterator = paramInputMethodManager.getEnabledInputMethodList().iterator();
    label78:
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        localInputMethodInfo = null;
        return localInputMethodInfo;
      }
      InputMethodInfo localInputMethodInfo = (InputMethodInfo)localIterator.next();
      for (int i = 0;; i++)
      {
        if (i >= localInputMethodInfo.getSubtypeCount()) {
          break label78;
        }
        if (("voice".equals(localInputMethodInfo.getSubtypeAt(i).getMode())) && (localInputMethodInfo.getComponent().getPackageName().startsWith("com.google.android"))) {
          break;
        }
      }
    }
  }
  
  private InputMethodSubtype getVoiceImeSubtype(InputMethodManager paramInputMethodManager, InputMethodInfo paramInputMethodInfo)
    throws SecurityException, IllegalArgumentException
  {
    List localList = (List)paramInputMethodManager.getShortcutInputMethodsAndSubtypes().get(paramInputMethodInfo);
    if ((localList != null) && (localList.size() > 0)) {
      return (InputMethodSubtype)localList.get(0);
    }
    return null;
  }
  
  public static boolean isInstalled(InputMethodService paramInputMethodService)
  {
    if (Build.VERSION.SDK_INT < 14) {}
    InputMethodInfo localInputMethodInfo;
    do
    {
      return false;
      localInputMethodInfo = getVoiceImeInputMethodInfo(getInputMethodManager(paramInputMethodService));
    } while ((localInputMethodInfo == null) || (localInputMethodInfo.getSubtypeCount() <= 0));
    return true;
  }
  
  public void onStartInputView() {}
  
  public void startVoiceRecognition(String paramString)
  {
    InputMethodManager localInputMethodManager = getInputMethodManager(this.mInputMethodService);
    InputMethodInfo localInputMethodInfo = getVoiceImeInputMethodInfo(localInputMethodManager);
    if (localInputMethodInfo == null) {
      return;
    }
    localInputMethodManager.setInputMethodAndSubtype(this.mInputMethodService.getWindow().getWindow().getAttributes().token, localInputMethodInfo.getId(), getVoiceImeSubtype(localInputMethodManager, localInputMethodInfo));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.voiceime.ImeTrigger
 * JD-Core Version:    0.7.0.1
 */