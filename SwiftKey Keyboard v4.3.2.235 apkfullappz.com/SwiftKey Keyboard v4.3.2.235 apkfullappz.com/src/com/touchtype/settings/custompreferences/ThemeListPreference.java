package com.touchtype.settings.custompreferences;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.touchtype.keyboard.theme.ThemeHeader;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype_fluency.service.receiver.SDCardListener;
import com.touchtype_fluency.service.receiver.SDCardReceiver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ThemeListPreference
  extends ListPreference
{
  private Map<String, ThemeHeader> mAvailableThemes;
  private String mCurrentThemeKey;
  private final String mDefaultTheme;
  private SDCardListener mSDCardListener;
  private final String mSummaryUnformatted;
  
  public ThemeListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mDefaultTheme = paramContext.getResources().getString(2131296791);
    this.mSummaryUnformatted = paramContext.getResources().getString(2131296980);
    setEntries(new CharSequence[] { "" });
    setEntryValues(new CharSequence[] { "" });
    updateSummary(getCurrentIndexValue());
  }
  
  private Map<String, ThemeHeader> getAvailableThemes()
  {
    if (this.mAvailableThemes == null) {
      this.mAvailableThemes = ThemeManager.getInstance(getContext()).getAvailableThemes();
    }
    return this.mAvailableThemes;
  }
  
  private Drawable[] getIcons(Map<String, ThemeHeader> paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramMap.values().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((ThemeHeader)localIterator.next()).getIcon());
    }
    return (Drawable[])localArrayList.toArray(new Drawable[localArrayList.size()]);
  }
  
  private CharSequence[] getIds(Map<String, ThemeHeader> paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramMap.values().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((ThemeHeader)localIterator.next()).getId());
    }
    return (CharSequence[])localArrayList.toArray(new CharSequence[localArrayList.size()]);
  }
  
  private CharSequence[] getNames(Map<String, ThemeHeader> paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramMap.values().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((ThemeHeader)localIterator.next()).getName());
    }
    return (CharSequence[])localArrayList.toArray(new CharSequence[localArrayList.size()]);
  }
  
  private void startListeningToSDCardEvents()
  {
    this.mSDCardListener = new SDCardListener()
    {
      public void onMediaMounted()
      {
        ThemeListPreference.this.update();
      }
      
      public void onMediaUnmounted()
      {
        ThemeListPreference.this.update();
      }
    };
    SDCardReceiver.addListener(this.mSDCardListener);
  }
  
  private void stopListeningToSDCardEvents()
  {
    SDCardReceiver.removeListener(this.mSDCardListener);
  }
  
  private void update()
  {
    this.mAvailableThemes = getAvailableThemes();
    setEntries(getNames(this.mAvailableThemes));
    setEntryValues(getIds(this.mAvailableThemes));
    updateSummary(getCurrentIndexValue());
  }
  
  private void updateSummary(String paramString)
  {
    ThemeHeader localThemeHeader = (ThemeHeader)ThemeManager.getInstance(getContext()).getAvailableThemes().get(paramString);
    if (localThemeHeader != null)
    {
      String str = this.mSummaryUnformatted;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localThemeHeader.getName();
      setSummary(String.format(str, arrayOfObject));
      return;
    }
    setSummary(String.format(this.mSummaryUnformatted, new Object[] { "Unknown" }));
  }
  
  public String getCurrentIndexValue()
  {
    if (this.mCurrentThemeKey == null) {
      this.mCurrentThemeKey = ThemeManager.getInstance(getContext()).getThemeId();
    }
    return this.mCurrentThemeKey;
  }
  
  public void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.mCurrentThemeKey = getCurrentIndexValue();
    this.mAvailableThemes = getAvailableThemes();
    Drawable localDrawable = ((ThemeHeader)this.mAvailableThemes.get(this.mCurrentThemeKey)).getIcon();
    ImageView localImageView = (ImageView)paramView.findViewById(2131230909);
    if ((localImageView != null) && (localDrawable != null)) {
      localImageView.setImageDrawable(localDrawable);
    }
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if (paramBoolean)
    {
      String str = getValue();
      if (!this.mCurrentThemeKey.equals(str))
      {
        this.mCurrentThemeKey = str;
        ThemeManager.getInstance(getContext()).setTheme(str);
      }
      if (shouldPersist()) {
        persistString(this.mCurrentThemeKey);
      }
      updateSummary(this.mCurrentThemeKey);
    }
    stopListeningToSDCardEvents();
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    startListeningToSDCardEvents();
    this.mCurrentThemeKey = getCurrentIndexValue();
    Context localContext = getContext();
    this.mAvailableThemes = getAvailableThemes();
    CharSequence[] arrayOfCharSequence1 = getNames(this.mAvailableThemes);
    CharSequence[] arrayOfCharSequence2 = getIds(this.mAvailableThemes);
    IconListItemAdapter localIconListItemAdapter = new IconListItemAdapter(localContext, this, 2130903079, arrayOfCharSequence1, arrayOfCharSequence2, null, getIcons(this.mAvailableThemes), null, true);
    setEntries(arrayOfCharSequence1);
    setEntryValues(arrayOfCharSequence2);
    setValue(this.mCurrentThemeKey);
    updateSummary(getCurrentIndexValue());
    paramBuilder.setAdapter(localIconListItemAdapter, this);
    super.onPrepareDialogBuilder(paramBuilder);
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    super.onSetInitialValue(paramBoolean, paramObject);
    if (paramBoolean) {}
    for (this.mCurrentThemeKey = getCurrentIndexValue();; this.mCurrentThemeKey = this.mDefaultTheme)
    {
      updateSummary(this.mCurrentThemeKey);
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.ThemeListPreference
 * JD-Core Version:    0.7.0.1
 */