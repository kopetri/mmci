package com.touchtype.keyboard;

import java.util.List;

public final class LanguageSwitchData
{
  private final List<LanguageSwitchEntry> mEntries;
  
  public LanguageSwitchData(List<LanguageSwitchEntry> paramList)
  {
    this.mEntries = paramList;
  }
  
  private int wrapIndex(int paramInt)
  {
    if (paramInt < 0) {
      paramInt += this.mEntries.size();
    }
    return paramInt % this.mEntries.size();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LanguageSwitchData)) {
      return false;
    }
    LanguageSwitchData localLanguageSwitchData = (LanguageSwitchData)paramObject;
    if (this.mEntries.size() != localLanguageSwitchData.mEntries.size()) {
      return false;
    }
    for (int i = 0; i < this.mEntries.size(); i++) {
      if (!((LanguageSwitchEntry)this.mEntries.get(i)).equals(localLanguageSwitchData.mEntries.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  public LanguageSwitchEntry getCurrentLanguageSwitchEntry()
  {
    return (LanguageSwitchEntry)this.mEntries.get(0);
  }
  
  public LanguageSwitchEntry getNextLanguageSwitchEntry()
  {
    return (LanguageSwitchEntry)this.mEntries.get(wrapIndex(1));
  }
  
  public LanguageSwitchEntry getPrevLanguageSwitchEntry()
  {
    return (LanguageSwitchEntry)this.mEntries.get(wrapIndex(-1));
  }
  
  public static final class LanguageSwitchEntry
  {
    private final String mFullLabel;
    private final String mShortLabel;
    
    public LanguageSwitchEntry(String paramString1, String paramString2)
    {
      this.mFullLabel = paramString1;
      this.mShortLabel = paramString2;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof LanguageSwitchEntry)) {}
      LanguageSwitchEntry localLanguageSwitchEntry;
      do
      {
        return false;
        localLanguageSwitchEntry = (LanguageSwitchEntry)paramObject;
      } while ((!this.mFullLabel.equals(localLanguageSwitchEntry.mFullLabel)) || (!this.mShortLabel.equals(localLanguageSwitchEntry.mShortLabel)));
      return true;
    }
    
    public String getFullLabel()
    {
      return this.mFullLabel;
    }
    
    public String getShortLabel()
    {
      return this.mShortLabel;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.LanguageSwitchData
 * JD-Core Version:    0.7.0.1
 */