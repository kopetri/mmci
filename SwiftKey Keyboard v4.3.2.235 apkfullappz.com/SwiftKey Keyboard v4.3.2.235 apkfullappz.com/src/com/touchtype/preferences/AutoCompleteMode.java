package com.touchtype.preferences;

public enum AutoCompleteMode
{
  static
  {
    AutoCompleteMode[] arrayOfAutoCompleteMode = new AutoCompleteMode[3];
    arrayOfAutoCompleteMode[0] = AUTOCOMPLETEMODE_DISABLED;
    arrayOfAutoCompleteMode[1] = AUTOCOMPLETEMODE_ENABLED_WHEN_WORD_STARTED;
    arrayOfAutoCompleteMode[2] = AUTOCOMPLETEMODE_ENABLED_WITH_AUTOSELECT;
    $VALUES = arrayOfAutoCompleteMode;
  }
  
  private AutoCompleteMode() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.AutoCompleteMode
 * JD-Core Version:    0.7.0.1
 */