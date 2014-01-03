package com.touchtype.keyboard.key;

public enum KeyIcon
{
  private final int mAttributeValue;
  private final int mEnumValue;
  
  static
  {
    CommaKey = new KeyIcon("CommaKey", 1, 1, 2);
    Settings123Key = new KeyIcon("Settings123Key", 2, 2, 3);
    ShiftKey = new KeyIcon("ShiftKey", 3, 4, 4);
    IMEGoKeyTop = new KeyIcon("IMEGoKeyTop", 4, 5, 5);
    IMEGoKey = new KeyIcon("IMEGoKey", 5, 6, 6);
    leftArrow = new KeyIcon("leftArrow", 6, 7, 7);
    rightArrow = new KeyIcon("rightArrow", 7, 8, 8);
    upArrow = new KeyIcon("upArrow", 8, 9, 9);
    downArrow = new KeyIcon("downArrow", 9, 10, 10);
    TabKey = new KeyIcon("TabKey", 10, 11, 11);
    EnterKey = new KeyIcon("EnterKey", 11, 12, 12);
    SpaceKey = new KeyIcon("SpaceKey", 12, 13, 13);
    SpaceKey_OpenBox = new KeyIcon("SpaceKey_OpenBox", 13, 14, 14);
    SettingsKey = new KeyIcon("SettingsKey", 14, 3, 15);
    KeyIcon[] arrayOfKeyIcon = new KeyIcon[15];
    arrayOfKeyIcon[0] = DeleteKey;
    arrayOfKeyIcon[1] = CommaKey;
    arrayOfKeyIcon[2] = Settings123Key;
    arrayOfKeyIcon[3] = ShiftKey;
    arrayOfKeyIcon[4] = IMEGoKeyTop;
    arrayOfKeyIcon[5] = IMEGoKey;
    arrayOfKeyIcon[6] = leftArrow;
    arrayOfKeyIcon[7] = rightArrow;
    arrayOfKeyIcon[8] = upArrow;
    arrayOfKeyIcon[9] = downArrow;
    arrayOfKeyIcon[10] = TabKey;
    arrayOfKeyIcon[11] = EnterKey;
    arrayOfKeyIcon[12] = SpaceKey;
    arrayOfKeyIcon[13] = SpaceKey_OpenBox;
    arrayOfKeyIcon[14] = SettingsKey;
    $VALUES = arrayOfKeyIcon;
  }
  
  private KeyIcon(int paramInt1, int paramInt2)
  {
    this.mAttributeValue = paramInt1;
    this.mEnumValue = paramInt2;
  }
  
  public static KeyIcon getFromAttributeValue(int paramInt)
  {
    for (KeyIcon localKeyIcon : ) {
      if (localKeyIcon.getAttributeValue() == paramInt) {
        return localKeyIcon;
      }
    }
    return null;
  }
  
  public static KeyIcon getFromEnumValue(int paramInt)
  {
    for (KeyIcon localKeyIcon : ) {
      if (localKeyIcon.mEnumValue == paramInt) {
        return localKeyIcon;
      }
    }
    return null;
  }
  
  public int getAttributeValue()
  {
    return this.mAttributeValue;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeyIcon
 * JD-Core Version:    0.7.0.1
 */