package com.touchtype.keyboard.key;

import java.util.HashSet;
import java.util.Set;

public final class IntentionalEventFilter
{
  private Set<String> mIntentionalEventFilterSet = new HashSet();
  
  public void addEvents(PopupContent paramPopupContent)
  {
    HashSet localHashSet = new HashSet();
    if ((paramPopupContent != null) && (paramPopupContent.getInputStrings().size() > 0)) {
      localHashSet.addAll(paramPopupContent.getInputStrings());
    }
    for (;;)
    {
      this.mIntentionalEventFilterSet.addAll(localHashSet);
      return;
      if ((paramPopupContent instanceof PopupContent.PreviewContent)) {
        localHashSet.add(((PopupContent.PreviewContent)paramPopupContent).mLabel);
      }
    }
  }
  
  public Set<String> getIntentionalEventFilterSet()
  {
    return this.mIntentionalEventFilterSet;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.IntentionalEventFilter
 * JD-Core Version:    0.7.0.1
 */