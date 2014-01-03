package com.touchtype.keyboard;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class KeyboardBehaviour
{
  private static final String TAG = KeyboardBehaviour.class.getSimpleName();
  private final boolean mAlwaysComposeWordByWord;
  private final boolean mBlockNonAsianCandidates;
  private final String mCharacterMapLayout;
  private final boolean mDisableQuickPeriod;
  private final boolean mHasHardKeyboard;
  private final String mInputType;
  private final LayoutData.LayoutMap mLayout;
  private final LayoutType mLayoutType;
  private final boolean mNeverAutocomplete;
  private final List<QuickLayoutSwitch> mQuickSwitchLayouts;
  private final ResultsFilter.PredictionSearchType mSearchType;
  private final boolean mShiftStateInsensitive;
  private final boolean mShouldUseAsianCandidateBar;
  private final boolean mShouldUseAsianComposingBuffer;
  private final boolean mUseTelexConversionLayer;
  private final boolean mUseZeroWidthSpace;
  
  public KeyboardBehaviour(Context paramContext, KeyboardSwitcher paramKeyboardSwitcher, LayoutData.LayoutMap paramLayoutMap, LayoutType paramLayoutType, List<LayoutData.LayoutMap> paramList)
  {
    boolean bool2;
    boolean bool3;
    label42:
    boolean bool4;
    label59:
    boolean bool5;
    label76:
    boolean bool6;
    if ((paramLayoutMap == LayoutData.LayoutMap.KOREAN) || (paramLayoutMap == LayoutData.LayoutMap.QWERTY_VIETNAMESE))
    {
      bool2 = bool1;
      this.mAlwaysComposeWordByWord = bool2;
      if (paramLayoutMap != LayoutData.LayoutMap.KOREAN) {
        break label217;
      }
      bool3 = bool1;
      this.mNeverAutocomplete = bool3;
      if (paramLayoutMap != LayoutData.LayoutMap.THAI) {
        break label223;
      }
      bool4 = bool1;
      this.mDisableQuickPeriod = bool4;
      if (paramLayoutMap != LayoutData.LayoutMap.QWERTY_VIETNAMESE) {
        break label229;
      }
      bool5 = bool1;
      this.mUseTelexConversionLayer = bool5;
      if ((paramLayoutMap != LayoutData.LayoutMap.THAI) && (paramLayoutMap != LayoutData.LayoutMap.HIRAGANA) && (paramLayoutMap != LayoutData.LayoutMap.ROMAJI)) {
        break label235;
      }
      bool6 = bool1;
      label107:
      this.mUseZeroWidthSpace = bool6;
      if (paramLayoutMap != LayoutData.LayoutMap.HARDKEYBOARD) {
        break label241;
      }
    }
    for (;;)
    {
      this.mHasHardKeyboard = bool1;
      this.mSearchType = getSearchType(paramLayoutMap);
      this.mCharacterMapLayout = getCharacterMap(paramContext, paramLayoutMap);
      this.mInputType = getInputTag(paramLayoutMap);
      this.mLayout = paramLayoutMap;
      this.mLayoutType = paramLayoutType;
      this.mShouldUseAsianComposingBuffer = shouldUseAsianComposingBuffer(paramLayoutMap, paramLayoutType);
      this.mShouldUseAsianCandidateBar = shouldUseAsianCandidateBar(paramLayoutMap);
      this.mBlockNonAsianCandidates = shouldBlockNonAsianCandidates(paramLayoutMap);
      this.mShiftStateInsensitive = shiftStateInsensitive(paramLayoutMap);
      this.mQuickSwitchLayouts = getQuickSwitchLayouts(paramLayoutMap, paramList, paramKeyboardSwitcher);
      return;
      bool2 = false;
      break;
      label217:
      bool3 = false;
      break label42;
      label223:
      bool4 = false;
      break label59;
      label229:
      bool5 = false;
      break label76;
      label235:
      bool6 = false;
      break label107;
      label241:
      bool1 = false;
    }
  }
  
  private String getCharacterMap(Context paramContext, LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return "{\"charmap\": {}}";
    case 2: 
      return loadRawResource(paramContext, 2131099648);
    }
    return loadRawResource(paramContext, 2131099649);
  }
  
  private String getInputTag(LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    case 12: 
    case 13: 
    default: 
      return null;
    case 1: 
    case 2: 
      return "pinyin";
    case 3: 
    case 4: 
      return "jyutping";
    case 8: 
    case 9: 
      return "zhuyin";
    case 5: 
    case 6: 
    case 7: 
      return "stroke";
    case 10: 
      return "cangjie";
    case 11: 
      return "qcangjie";
    }
    return "english";
  }
  
  private List<QuickLayoutSwitch> getQuickSwitchLayouts(LayoutData.LayoutMap paramLayoutMap, List<LayoutData.LayoutMap> paramList, KeyboardSwitcher paramKeyboardSwitcher)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return new ArrayList();
    }
    return packageAlternativeLayouts(paramList, paramKeyboardSwitcher);
  }
  
  private static ResultsFilter.PredictionSearchType getSearchType(LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return ResultsFilter.PredictionSearchType.NORMAL;
    case 12: 
    case 13: 
      return ResultsFilter.PredictionSearchType.JAPANESE;
    case 1: 
    case 2: 
    case 3: 
    case 4: 
      return ResultsFilter.PredictionSearchType.PINYIN;
    case 8: 
    case 9: 
      return ResultsFilter.PredictionSearchType.ZHUYIN;
    case 5: 
    case 6: 
    case 7: 
      return ResultsFilter.PredictionSearchType.STROKE;
    }
    return ResultsFilter.PredictionSearchType.CANGJIE;
  }
  
  private static String loadRawResource(Context paramContext, int paramInt)
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramContext.getResources().openRawResource(paramInt);
      String str = IOUtils.toString(localInputStream, "UTF-8");
      return str;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "missing JSON character map");
      return null;
    }
    finally
    {
      IOUtils.closeQuietly(localInputStream);
    }
  }
  
  private List<QuickLayoutSwitch> packageAlternativeLayouts(List<LayoutData.LayoutMap> paramList, KeyboardSwitcher paramKeyboardSwitcher)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    if (localIterator.hasNext())
    {
      LayoutData.LayoutMap localLayoutMap = (LayoutData.LayoutMap)localIterator.next();
      if (localLayoutMap == this.mLayout) {}
      for (boolean bool = true;; bool = false)
      {
        localArrayList.add(new QuickLayoutSwitch(localLayoutMap, bool, paramKeyboardSwitcher));
        break;
      }
    }
    return localArrayList;
  }
  
  private static boolean shiftStateInsensitive(LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return false;
    }
    return true;
  }
  
  private static boolean shouldBlockNonAsianCandidates(LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return false;
    }
    return true;
  }
  
  private static boolean shouldUseAsianCandidateBar(LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return false;
    }
    return true;
  }
  
  private static boolean shouldUseAsianComposingBuffer(LayoutData.LayoutMap paramLayoutMap, LayoutType paramLayoutType)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$LayoutType[paramLayoutType.ordinal()])
    {
    default: 
      switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
      {
      }
      break;
    }
    return false;
    return true;
  }
  
  public boolean getAlwaysComposeWordByWord()
  {
    return this.mAlwaysComposeWordByWord;
  }
  
  public String getCharacterMapLayout()
  {
    return this.mCharacterMapLayout;
  }
  
  public boolean getDisableQuickPeriod()
  {
    return this.mDisableQuickPeriod;
  }
  
  public boolean getHasHardKeyboard()
  {
    return this.mHasHardKeyboard;
  }
  
  public String getInputType()
  {
    return this.mInputType;
  }
  
  public LayoutData.LayoutMap getLayout()
  {
    return this.mLayout;
  }
  
  public LayoutType getLayoutType()
  {
    return this.mLayoutType;
  }
  
  public boolean getNeverAutocomplete()
  {
    return this.mNeverAutocomplete;
  }
  
  public List<QuickLayoutSwitch> getQuickSwitchLayouts()
  {
    return this.mQuickSwitchLayouts;
  }
  
  public ResultsFilter.PredictionSearchType getSearchType()
  {
    return this.mSearchType;
  }
  
  public boolean getUseTelexConversionLayer()
  {
    return this.mUseTelexConversionLayer;
  }
  
  public boolean getUseZeroWidthSpace()
  {
    return this.mUseZeroWidthSpace;
  }
  
  public boolean shiftStateInsensitive()
  {
    return this.mShiftStateInsensitive;
  }
  
  public boolean shouldBlockNonAsianCandidates()
  {
    return this.mBlockNonAsianCandidates;
  }
  
  public boolean shouldUseAsianCandidateBar()
  {
    return this.mShouldUseAsianCandidateBar;
  }
  
  public boolean shouldUseAsianComposingBuffer()
  {
    return this.mShouldUseAsianComposingBuffer;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.KeyboardBehaviour
 * JD-Core Version:    0.7.0.1
 */