package com.touchtype.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.inputmethod.EditorInfo;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.InputEventModelTransformingWrapper;
import com.touchtype.keyboard.key.KeyFactory.FlowOrSwipe;
import com.touchtype.keyboard.key.KeyState.OptionState;
import com.touchtype.keyboard.theme.util.TextMetrics.TextMetricsRegister;
import com.touchtype.keyboard.view.BaseKeyboardView;
import com.touchtype.keyboard.view.KeyboardChoreographer;
import com.touchtype.keyboard.view.KeyboardState;
import com.touchtype.keyboard.view.MainKeyboardView;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.LogUtil;
import com.touchtype.util.WeakHashSet;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.PredictorListener;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutChangeListener;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutManager;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class KeyboardSwitcher
  implements SharedPreferences.OnSharedPreferenceChangeListener, PredictorListener, LayoutChangeListener
{
  private static final String TAG = KeyboardSwitcher.class.getSimpleName();
  private final Context mContext;
  private EditorInfoExtract mCurrentEditorInfoExtract;
  private MainKeyboard mCurrentKeyboard1;
  private MainKeyboard mCurrentKeyboard2;
  private int mCurrentKeyboardLayoutId;
  private int mCurrentKeyboardLayoutId2;
  private int mCurrentLayoutIndex;
  private DefaultPredictionProvider mDefaultPredictionProvider;
  private final FluencyServiceProxy mFluencyProxy;
  private final Handler mHandler;
  private int mImeOptions;
  private final InputEventModelTransformingWrapper mInputEventModelSingular;
  private boolean mIsMicrophoneKeyEnabled;
  private final Map<String, MainKeyboard> mKeyboardCache = new KeyboardCacheMap();
  private final Set<KeyboardChangeListener> mKeyboardChangeListeners = new WeakHashSet();
  private KeyboardChoreographer mKeyboardContainer;
  private KeyboardMode mKeyboardMode;
  private final Vector<LayoutLanguagePack> mLayouts = new Vector();
  private final Set<OptionStateListener> mOptionStateListeners = new WeakHashSet();
  private final TouchTypePreferences mPreferences;
  private int mPreviousKeyboardLayoutId;
  private InputEventModelTransformingWrapper mWrappedInputEventModelLeft;
  private InputEventModelTransformingWrapper mWrappedInputEventModelRight;
  
  public KeyboardSwitcher(Context paramContext, TouchTypePreferences paramTouchTypePreferences, InputEventModel paramInputEventModel, FluencyServiceProxy paramFluencyServiceProxy, DefaultPredictionProvider paramDefaultPredictionProvider)
  {
    this.mContext = paramContext;
    this.mPreferences = paramTouchTypePreferences;
    this.mInputEventModelSingular = new InputEventModelTransformingWrapper(paramInputEventModel);
    this.mWrappedInputEventModelLeft = new InputEventModelTransformingWrapper(paramInputEventModel);
    this.mWrappedInputEventModelRight = new InputEventModelTransformingWrapper(paramInputEventModel);
    this.mFluencyProxy = paramFluencyServiceProxy;
    this.mHandler = new Handler();
    this.mKeyboardMode = KeyboardMode.TEXT;
    TouchTypePreferences localTouchTypePreferences = this.mPreferences;
    Context localContext = this.mContext;
    if (this.mKeyboardContainer != null) {}
    for (boolean bool = this.mKeyboardContainer.isDocked();; bool = true)
    {
      this.mCurrentKeyboardLayoutId = localTouchTypePreferences.getKeyboardLayoutResourceId(localContext, bool);
      this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
      this.mFluencyProxy.runWhenConnected(new Runnable()
      {
        public void run()
        {
          KeyboardSwitcher.this.updateLayouts(KeyboardSwitcher.this.mFluencyProxy.getLayoutManager().getLayoutMap());
        }
      });
      return;
    }
  }
  
  private void addLatinLayout()
  {
    this.mLayouts.add(new LayoutLanguagePack(LayoutData.LayoutMap.QWERTY, new Vector()));
  }
  
  private boolean checkNoMicrophoneKeyOption(EditorInfoExtract paramEditorInfoExtract)
  {
    int i;
    if (Build.VERSION.SDK_INT > 15)
    {
      i = 1;
      if ((paramEditorInfoExtract.privateImeOptions == null) || ((!paramEditorInfoExtract.privateImeOptions.startsWith("nm")) && (!paramEditorInfoExtract.privateImeOptions.endsWith("nm")) && (!paramEditorInfoExtract.privateImeOptions.contains(paramEditorInfoExtract.packageName + "noMicrophoneKey")))) {
        break label97;
      }
    }
    label97:
    for (int j = 1;; j = 0)
    {
      boolean bool;
      if (i != 0)
      {
        bool = false;
        if (j != 0) {}
      }
      else
      {
        bool = true;
      }
      return bool;
      i = 0;
      break;
    }
  }
  
  private void clearKeyboards()
  {
    this.mCurrentKeyboard1 = null;
    this.mCurrentKeyboardLayoutId = 0;
    this.mKeyboardCache.clear();
  }
  
  private KeyboardSwitch createKeyboardSwitch(int paramInt)
  {
    LayoutData.LayoutMap localLayoutMap = getAsianLayoutMapFromId(paramInt);
    if ((localLayoutMap == null) || (this.mPreferences.getKeyboardLayout() == localLayoutMap)) {
      return new SimpleKeyboardSwitch(paramInt);
    }
    return new UpdateLayoutKeyboardSwitch(paramInt, localLayoutMap, this.mCurrentLayoutIndex);
  }
  
  private LanguageSwitchData createLanguageSwitchData(int paramInt)
  {
    if (this.mLayouts.size() < 2) {
      return null;
    }
    ArrayList localArrayList = new ArrayList(this.mLayouts.size());
    for (int i = 0; i < this.mLayouts.size(); i++)
    {
      LayoutLanguagePack localLayoutLanguagePack = (LayoutLanguagePack)this.mLayouts.get(wrapIndex(i + paramInt));
      localArrayList.add(new LanguageSwitchData.LanguageSwitchEntry(localLayoutLanguagePack.toString(this.mContext), localLayoutLanguagePack.toShortString(this.mContext)));
    }
    return new LanguageSwitchData(localArrayList);
  }
  
  private KeyFactory.FlowOrSwipe flowEnabledForInput(boolean paramBoolean)
  {
    if ((this.mPreferences.isFlowEnabled()) && (!paramBoolean))
    {
      KeyFactory.FlowOrSwipe localFlowOrSwipe = KeyFactory.FlowOrSwipe.FLOW;
      if (this.mInputEventModelSingular.isPredictionEnabled()) {
        return localFlowOrSwipe;
      }
      return KeyFactory.FlowOrSwipe.getDisabledValue(localFlowOrSwipe);
    }
    return KeyFactory.FlowOrSwipe.SWIPE;
  }
  
  private LayoutData.LayoutMap getAsianLayoutMapFromId(int paramInt)
  {
    if (LayoutData.LayoutMap.FIVESTROKE_CN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.FIVESTROKE_CN;
    }
    if (LayoutData.LayoutMap.FIVESTROKE_HK.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.FIVESTROKE_HK;
    }
    if (LayoutData.LayoutMap.FIVESTROKE_TW.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.FIVESTROKE_TW;
    }
    if (LayoutData.LayoutMap.CANGJIE.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.CANGJIE;
    }
    if (LayoutData.LayoutMap.QCANGJIE.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.QCANGJIE;
    }
    if (LayoutData.LayoutMap.PINYIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.PINYIN;
    }
    if (LayoutData.LayoutMap.PINYIN12.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.PINYIN12;
    }
    if (LayoutData.LayoutMap.ZHUYIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.ZHUYIN;
    }
    if (LayoutData.LayoutMap.ZHUYIN12.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.ZHUYIN12;
    }
    if (LayoutData.LayoutMap.FIVESTROKE_CN_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.FIVESTROKE_CN_LATIN;
    }
    if (LayoutData.LayoutMap.FIVESTROKE_HK_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.FIVESTROKE_HK_LATIN;
    }
    if (LayoutData.LayoutMap.FIVESTROKE_TW_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.FIVESTROKE_TW_LATIN;
    }
    if (LayoutData.LayoutMap.CANGJIE_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.CANGJIE_LATIN;
    }
    if (LayoutData.LayoutMap.QCANGJIE_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.QCANGJIE_LATIN;
    }
    if (LayoutData.LayoutMap.PINYIN_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.PINYIN_LATIN;
    }
    if (LayoutData.LayoutMap.PINYIN12_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.PINYIN12_LATIN;
    }
    if (LayoutData.LayoutMap.ZHUYIN_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.ZHUYIN_LATIN;
    }
    if (LayoutData.LayoutMap.ZHUYIN12_LATIN.definesLayout(paramInt)) {
      return LayoutData.LayoutMap.ZHUYIN12_LATIN;
    }
    return null;
  }
  
  private String getString(int paramInt)
  {
    return this.mContext.getResources().getString(paramInt);
  }
  
  private MainKeyboard loadKeyboard(KeyboardSwitch paramKeyboardSwitch, InputEventModel paramInputEventModel, TextMetrics.TextMetricsRegister paramTextMetricsRegister, boolean paramBoolean)
  {
    KeyboardLoader.KeyboardAttributes localKeyboardAttributes = KeyboardLoader.loadKeyboardAttributes(this.mContext, paramKeyboardSwitch.getLayoutId(), this.mKeyboardMode.getValue());
    KeyFactory.FlowOrSwipe localFlowOrSwipe1 = flowEnabledForInput(paramBoolean);
    if (!localKeyboardAttributes.mDisableFlow) {}
    for (KeyFactory.FlowOrSwipe localFlowOrSwipe2 = localFlowOrSwipe1;; localFlowOrSwipe2 = KeyFactory.FlowOrSwipe.getDisabledValue(localFlowOrSwipe1))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(this.mKeyboardMode.value).append("_");
      localStringBuffer.append(paramKeyboardSwitch.getLayoutId()).append("_");
      localStringBuffer.append(localFlowOrSwipe2.ordinal());
      String str = localStringBuffer.toString();
      MainKeyboard localMainKeyboard = (MainKeyboard)this.mKeyboardCache.get(str);
      if (localMainKeyboard == null)
      {
        localMainKeyboard = KeyboardLoader.loadKeyboard(this.mContext, this.mFluencyProxy, paramInputEventModel, this, paramTextMetricsRegister, paramKeyboardSwitch.getLanguageSwitchData(), paramKeyboardSwitch.getLayoutId(), this.mKeyboardMode.getValue(), localFlowOrSwipe1, this.mIsMicrophoneKeyEnabled, this.mDefaultPredictionProvider, new KeyboardBehaviour(this.mContext, this, paramKeyboardSwitch.getLayout(), localKeyboardAttributes.mLayoutType, paramKeyboardSwitch.getAlternativeLayouts()));
        this.mKeyboardCache.put(str, localMainKeyboard);
      }
      return localMainKeyboard;
    }
  }
  
  private void notifyKeyboardChangeListeners(KeyboardBehaviour paramKeyboardBehaviour)
  {
    Iterator localIterator = this.mKeyboardChangeListeners.iterator();
    while (localIterator.hasNext()) {
      ((KeyboardChangeListener)localIterator.next()).onKeyboardChanged(paramKeyboardBehaviour);
    }
  }
  
  private void notifyOptionsListeners(KeyState.OptionState paramOptionState)
  {
    Iterator localIterator = this.mOptionStateListeners.iterator();
    while (localIterator.hasNext()) {
      ((OptionStateListener)localIterator.next()).onOptionStateChanged(paramOptionState);
    }
  }
  
  private void selectKeyboard(KeyboardSwitch paramKeyboardSwitch1, KeyboardSwitch paramKeyboardSwitch2, boolean paramBoolean)
  {
    int i;
    TextMetrics.TextMetricsRegister localTextMetricsRegister;
    label115:
    KeyboardBehaviour localKeyboardBehaviour;
    boolean bool2;
    if ((paramBoolean) || (paramKeyboardSwitch1.shouldChangeLayout()))
    {
      i = 1;
      localTextMetricsRegister = new TextMetrics.TextMetricsRegister();
      this.mPreviousKeyboardLayoutId = this.mCurrentKeyboardLayoutId;
      this.mCurrentKeyboardLayoutId = paramKeyboardSwitch1.getLayoutId();
      this.mCurrentKeyboardLayoutId2 = paramKeyboardSwitch2.getLayoutId();
      if ((this.mCurrentKeyboard1 == null) || (i != 0))
      {
        if (this.mCurrentKeyboardLayoutId2 <= 0) {
          break label411;
        }
        this.mCurrentKeyboard1 = loadKeyboard(paramKeyboardSwitch1, this.mWrappedInputEventModelLeft, localTextMetricsRegister, true);
        this.mCurrentKeyboard2 = loadKeyboard(paramKeyboardSwitch2, this.mWrappedInputEventModelRight, localTextMetricsRegister, true);
        this.mCurrentKeyboard1.mergeFilters(this.mCurrentKeyboard2);
      }
      localKeyboardBehaviour = new KeyboardBehaviour(this.mContext, this, paramKeyboardSwitch1.getLayout(), this.mCurrentKeyboard1.getLayoutType(), paramKeyboardSwitch1.getAlternativeLayouts());
      if (this.mKeyboardContainer == null) {
        break label546;
      }
      if (this.mCurrentKeyboardLayoutId2 <= 0) {
        break label436;
      }
      MainKeyboardView localMainKeyboardView1 = new MainKeyboardView(this.mKeyboardContainer.getContext(), this.mCurrentKeyboard1, this.mWrappedInputEventModelLeft, this.mKeyboardContainer.getPopupParent(), this.mKeyboardContainer, localKeyboardBehaviour);
      MainKeyboardView localMainKeyboardView2 = new MainKeyboardView(this.mKeyboardContainer.getContext(), this.mCurrentKeyboard2, this.mWrappedInputEventModelRight, this.mKeyboardContainer.getPopupParent(), this.mKeyboardContainer, localKeyboardBehaviour);
      DualKeyPressModelUpdater localDualKeyPressModelUpdater = new DualKeyPressModelUpdater(this.mCurrentKeyboard1.getLayout(), this.mCurrentKeyboard2.getLayout(), this.mInputEventModelSingular.getKeyPressModel());
      KeyboardChoreographer localKeyboardChoreographer1 = this.mKeyboardContainer;
      InputEventModelTransformingWrapper localInputEventModelTransformingWrapper1 = this.mWrappedInputEventModelLeft;
      InputEventModelTransformingWrapper localInputEventModelTransformingWrapper2 = this.mWrappedInputEventModelRight;
      if (this.mCurrentKeyboard1.getFlowOrSwipe() != KeyFactory.FlowOrSwipe.FLOW) {
        break label430;
      }
      bool2 = true;
      label300:
      localKeyboardChoreographer1.setKeyboardView(localMainKeyboardView1, localMainKeyboardView2, localInputEventModelTransformingWrapper1, localInputEventModelTransformingWrapper2, localDualKeyPressModelUpdater, bool2);
      label317:
      if (this.mKeyboardMode != KeyboardMode.IM) {
        break label558;
      }
    }
    label411:
    label546:
    label558:
    for (boolean bool1 = true;; bool1 = false)
    {
      notifyOptionsListeners(toOptionState(this.mImeOptions, bool1));
      if (i != 0)
      {
        paramKeyboardSwitch1.apply();
        if (this.mCurrentKeyboardLayoutId2 > 0) {
          paramKeyboardSwitch2.apply();
        }
        this.mInputEventModelSingular.updateKeyPressModel(this.mCurrentKeyboard1.getPredictionFilter(), this.mCurrentKeyboard1.getIntentionalEventFilter(), this.mFluencyProxy, localKeyboardBehaviour.getHasHardKeyboard());
        notifyKeyboardChangeListeners(localKeyboardBehaviour);
      }
      return;
      i = 0;
      break;
      this.mCurrentKeyboard1 = loadKeyboard(paramKeyboardSwitch1, this.mInputEventModelSingular, localTextMetricsRegister, false);
      break label115;
      label430:
      bool2 = false;
      break label300;
      label436:
      MainKeyboardView localMainKeyboardView3 = new MainKeyboardView(this.mKeyboardContainer.getContext(), this.mCurrentKeyboard1, this.mInputEventModelSingular, this.mKeyboardContainer.getPopupParent(), this.mKeyboardContainer, localKeyboardBehaviour);
      SingularKeyPressModelUpdater localSingularKeyPressModelUpdater = new SingularKeyPressModelUpdater(this.mCurrentKeyboard1.getLayout(), this.mInputEventModelSingular.getKeyPressModel());
      KeyboardChoreographer localKeyboardChoreographer2 = this.mKeyboardContainer;
      InputEventModelTransformingWrapper localInputEventModelTransformingWrapper3 = this.mInputEventModelSingular;
      if (this.mCurrentKeyboard1.getFlowOrSwipe() == KeyFactory.FlowOrSwipe.FLOW) {}
      for (boolean bool3 = true;; bool3 = false)
      {
        localKeyboardChoreographer2.setKeyboardView(localMainKeyboardView3, localInputEventModelTransformingWrapper3, localSingularKeyPressModelUpdater, bool3);
        break;
      }
      LogUtil.w(TAG, "KeyboardViewContainer hasn't been set before selecting a keyboard");
      break label317;
    }
  }
  
  private void selectKeyboard(KeyboardSwitch paramKeyboardSwitch, boolean paramBoolean)
  {
    selectKeyboard(paramKeyboardSwitch, new SimpleKeyboardSwitch(-1), paramBoolean);
  }
  
  private void startInputView(EditorInfoExtract paramEditorInfoExtract)
  {
    Object localObject = KeyboardMode.TEXT;
    LayoutData.LayoutMap localLayoutMap = this.mPreferences.getKeyboardLayout();
    int i = this.mPreferences.getKeyboardLayoutStyle(this.mContext);
    boolean bool1 = this.mPreferences.getUsePCLayoutStyle(this.mContext.getResources().getBoolean(2131492874));
    boolean bool2 = this.mPreferences.getShowSplitNumpad(this.mContext, this.mContext.getResources().getBoolean(2131492877));
    int j = -1;
    int k = -1;
    int m = -1;
    int n = -1;
    int i1;
    int i2;
    int i3;
    int i4;
    KeyboardMode localKeyboardMode1;
    label155:
    KeyboardMode localKeyboardMode2;
    label170:
    int i6;
    if (i == 2) {
      if (this.mKeyboardContainer.isDocked())
      {
        i1 = localLayoutMap.getSplitLayoutResId(bool2);
        i2 = localLayoutMap.getSymbolsLayout().getSplitLayoutResId(bool2);
        i3 = LayoutData.LayoutMap.PHONE.getSplitLayoutResId(bool1);
        i4 = LayoutData.LayoutMap.PIN.getSplitLayoutResId(bool1);
        if (!this.mPreferences.useSpecializedEmailKeyboard()) {
          break label509;
        }
        localKeyboardMode1 = KeyboardMode.EMAIL;
        if (!this.mPreferences.useSpecializedURLKeyboard()) {
          break label517;
        }
        localKeyboardMode2 = KeyboardMode.URL;
        int i5 = paramEditorInfoExtract.inputType;
        i6 = i5 & 0xFF0;
        switch (i5 & 0xF)
        {
        }
      }
    }
    for (;;)
    {
      this.mKeyboardMode = ((KeyboardMode)localObject);
      this.mImeOptions = paramEditorInfoExtract.imeOptions;
      boolean bool3 = checkNoMicrophoneKeyOption(paramEditorInfoExtract);
      if (this.mIsMicrophoneKeyEnabled != bool3)
      {
        clearKeyboards();
        this.mIsMicrophoneKeyEnabled = bool3;
      }
      boolean bool4 = this.mFluencyProxy.isReady();
      SimpleKeyboardSwitch localSimpleKeyboardSwitch1 = new SimpleKeyboardSwitch(i1);
      SimpleKeyboardSwitch localSimpleKeyboardSwitch2 = new SimpleKeyboardSwitch(j);
      selectKeyboard(localSimpleKeyboardSwitch1, localSimpleKeyboardSwitch2, true);
      if (!bool4)
      {
        final SimpleKeyboardSwitch localSimpleKeyboardSwitch3 = new SimpleKeyboardSwitch(i1);
        final SimpleKeyboardSwitch localSimpleKeyboardSwitch4 = new SimpleKeyboardSwitch(j);
        FluencyServiceProxy localFluencyServiceProxy = this.mFluencyProxy;
        Runnable local2 = new Runnable()
        {
          public void run()
          {
            KeyboardSwitcher.this.clearKeyboards();
            KeyboardSwitcher.this.selectKeyboard(localSimpleKeyboardSwitch3, localSimpleKeyboardSwitch4, false);
          }
        };
        localFluencyServiceProxy.runWhenConnected(local2);
      }
      return;
      i1 = localLayoutMap.getSplitLeftLayoutResId();
      j = localLayoutMap.getSplitRightLayoutResId();
      i2 = localLayoutMap.getSymbolsLayout().getSplitLeftLayoutResId();
      k = localLayoutMap.getSymbolsLayout().getSplitRightLayoutResId();
      i3 = LayoutData.LayoutMap.PHONE.getSplitLeftLayoutResId();
      m = LayoutData.LayoutMap.PHONE.getSplitRightLayoutResId();
      i4 = LayoutData.LayoutMap.PIN.getSplitLeftLayoutResId();
      n = LayoutData.LayoutMap.PIN.getSplitRightLayoutResId();
      break;
      if (i == 3)
      {
        i1 = localLayoutMap.getCompactLayoutResId();
        i2 = localLayoutMap.getSymbolsLayout().getCompactLayoutResId();
        i3 = LayoutData.LayoutMap.PHONE.getCompactLayoutResId();
        i4 = LayoutData.LayoutMap.PIN.getCompactLayoutResId();
        break;
      }
      i1 = localLayoutMap.getLayoutResId(bool1);
      i2 = localLayoutMap.getSymbolsLayout().getLayoutResId(bool1);
      i3 = LayoutData.LayoutMap.PHONE.getLayoutResId(bool1);
      i4 = LayoutData.LayoutMap.PIN.getLayoutResId(bool1);
      break;
      label509:
      localKeyboardMode1 = KeyboardMode.TEXT;
      break label155;
      label517:
      localKeyboardMode2 = KeyboardMode.TEXT;
      break label170;
      if (i6 == 16)
      {
        localObject = KeyboardMode.PIN;
        i1 = i4;
        j = n;
      }
      else
      {
        localObject = KeyboardMode.SYMBOLS;
        i1 = i2;
        j = k;
        continue;
        localObject = KeyboardMode.SYMBOLS;
        i1 = i2;
        j = k;
        continue;
        localObject = KeyboardMode.PHONE;
        i1 = i3;
        j = m;
        continue;
        if ((i6 == 32) || (i6 == 208))
        {
          localObject = localKeyboardMode1;
        }
        else if (i6 == 16)
        {
          localObject = localKeyboardMode2;
        }
        else if (i6 == 64)
        {
          localObject = KeyboardMode.IM;
        }
        else if (i6 == 160)
        {
          String str = paramEditorInfoExtract.fieldName;
          if ((str != null) && (str.contains("url\\")))
          {
            localObject = localKeyboardMode2;
          }
          else if ((str != null) && (str.contains("email\\")))
          {
            localObject = localKeyboardMode1;
            continue;
            if ((i6 == 32) || (i6 == 208)) {
              localObject = localKeyboardMode1;
            } else if (i6 == 16) {
              localObject = localKeyboardMode2;
            }
          }
        }
      }
    }
  }
  
  private KeyState.OptionState toOptionState(int paramInt, boolean paramBoolean)
  {
    int i = 1;
    switch (0x400000FF & paramInt)
    {
    default: 
      if ((!this.mPreferences.getUsePCLayoutStyle(false)) || (this.mPreferences.getKeyboardLayoutStyle(this.mContext) != i)) {
        break;
      }
    }
    while ((paramBoolean) && (!ProductConfiguration.isWatchBuild(this.mContext)) && (i == 0))
    {
      return KeyState.OptionState.SMILEY;
      return KeyState.OptionState.GO;
      return KeyState.OptionState.NEXT;
      return KeyState.OptionState.SEARCH;
      return KeyState.OptionState.SEND;
      i = 0;
    }
    return KeyState.OptionState.DONE;
  }
  
  private KeyboardSwitch[] translateId(int paramInt)
  {
    boolean bool1 = this.mPreferences.getKeyboardDockedState(this.mContext);
    int i = this.mPreferences.getKeyboardLayoutStyle(this.mContext);
    boolean bool2 = this.mPreferences.getUsePCLayoutStyle(this.mContext.getResources().getBoolean(2131492874));
    boolean bool3 = this.mPreferences.getShowSplitNumpad(this.mContext, this.mContext.getResources().getBoolean(2131492877));
    DynamicSwitch localDynamicSwitch = DynamicSwitch.forValue(paramInt);
    if (localDynamicSwitch == null)
    {
      LayoutData.LayoutMap localLayoutMap2 = LayoutData.getLayoutWhichContainsResource(paramInt);
      boolean bool5 = false;
      if (localLayoutMap2 == null)
      {
        localLayoutMap2 = LayoutData.getLayoutWhichContainsSecondaryResource(paramInt);
        bool5 = true;
      }
      if (localLayoutMap2 != null)
      {
        int[] arrayOfInt = localLayoutMap2.getLayoutForStyle(bool1, i, bool5, bool2, bool3);
        if ((arrayOfInt.length > 1) && (arrayOfInt[1] >= 0))
        {
          KeyboardSwitch[] arrayOfKeyboardSwitch3 = new KeyboardSwitch[2];
          arrayOfKeyboardSwitch3[0] = createKeyboardSwitch(arrayOfInt[0]);
          arrayOfKeyboardSwitch3[1] = createKeyboardSwitch(arrayOfInt[1]);
          return arrayOfKeyboardSwitch3;
        }
        KeyboardSwitch[] arrayOfKeyboardSwitch2 = new KeyboardSwitch[1];
        arrayOfKeyboardSwitch2[0] = createKeyboardSwitch(arrayOfInt[0]);
        return arrayOfKeyboardSwitch2;
      }
      return wrapSimpleSwitches(this.mPreferences.getKeyboardLayout().getLayoutForStyle(bool1, i, false, bool2, bool3));
    }
    switch (3.$SwitchMap$com$touchtype$keyboard$KeyboardSwitcher$DynamicSwitch[localDynamicSwitch.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("Unhandled DynamicSwitch: " + localDynamicSwitch.toString());
    case 1: 
      KeyboardSwitch[] arrayOfKeyboardSwitch1 = new KeyboardSwitch[1];
      arrayOfKeyboardSwitch1[0] = new SimpleKeyboardSwitch(DynamicSwitch.NONE.getValue());
      return arrayOfKeyboardSwitch1;
    case 2: 
      if (this.mPreviousKeyboardLayoutId != 0)
      {
        LayoutData.LayoutMap localLayoutMap1 = LayoutData.getLayoutWhichContainsResource(this.mPreviousKeyboardLayoutId);
        boolean bool4 = false;
        if (localLayoutMap1 == null)
        {
          localLayoutMap1 = LayoutData.getLayoutWhichContainsResource(this.mPreviousKeyboardLayoutId);
          bool4 = true;
        }
        if (localLayoutMap1 != null) {
          return wrapSimpleSwitches(localLayoutMap1.getLayoutForStyle(bool1, i, bool4, bool2, bool3));
        }
      }
    case 3: 
      return wrapSimpleSwitches(this.mPreferences.getKeyboardLayout().getLayoutForStyle(bool1, i, false, bool2, bool3));
    case 4: 
      int k = wrapIndex(1 + this.mCurrentLayoutIndex);
      return wrapKeyboardSwitches(((LayoutLanguagePack)this.mLayouts.get(k)).layout, bool1, i, bool2, bool3, k);
    }
    int j = wrapIndex(-1 + this.mCurrentLayoutIndex);
    return wrapKeyboardSwitches(((LayoutLanguagePack)this.mLayouts.get(j)).layout, bool1, i, bool2, bool3, j);
  }
  
  private void updateCurrentLayout(LayoutData.LayoutMap paramLayoutMap)
  {
    for (this.mCurrentLayoutIndex = 0; this.mCurrentLayoutIndex < this.mLayouts.size(); this.mCurrentLayoutIndex = (1 + this.mCurrentLayoutIndex))
    {
      LayoutLanguagePack localLayoutLanguagePack = (LayoutLanguagePack)this.mLayouts.get(this.mCurrentLayoutIndex);
      if ((localLayoutLanguagePack.layout == paramLayoutMap) && (!localLayoutLanguagePack.languagePacks.isEmpty())) {
        return;
      }
    }
    this.mCurrentLayoutIndex = 0;
    this.mPreferences.setKeyboardLayout(((LayoutLanguagePack)this.mLayouts.get(this.mCurrentLayoutIndex)).layout);
  }
  
  private void updateLayouts(Map<LayoutData.LayoutMap, Set<LanguagePack>> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    this.mLayouts.clear();
    boolean bool = false;
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      LayoutData.LayoutMap localLayoutMap2 = (LayoutData.LayoutMap)localEntry.getKey();
      LayoutLanguagePack localLayoutLanguagePack = new LayoutLanguagePack(localLayoutMap2, new Vector((Set)localEntry.getValue()));
      if (localLayoutMap2.providesLatin()) {
        bool = true;
      }
      this.mLayouts.add(localLayoutLanguagePack);
    }
    LayoutData.LayoutMap localLayoutMap1 = this.mPreferences.getKeyboardLayout();
    if (this.mLayouts.size() == 0)
    {
      this.mLayouts.add(new LayoutLanguagePack(localLayoutMap1, new Vector()));
      bool = localLayoutMap1.providesLatin();
    }
    if (!bool) {
      addLatinLayout();
    }
    updateCurrentLayout(localLayoutMap1);
  }
  
  private void updateStatistics(int paramInt)
  {
    TouchTypeStats localTouchTypeStats = this.mPreferences.getTouchTypeStats();
    switch (paramInt)
    {
    default: 
      return;
    case 2131034654: 
    case 2131034716: 
    case 2131034744: 
      localTouchTypeStats.incrementStatistic("stats_symbols_primary_opens");
      return;
    }
    localTouchTypeStats.incrementStatistic("stats_symbols_secondary_opens");
  }
  
  private int wrapIndex(int paramInt)
  {
    if (paramInt < 0) {
      paramInt += this.mLayouts.size();
    }
    return paramInt % this.mLayouts.size();
  }
  
  private KeyboardSwitch[] wrapKeyboardSwitches(LayoutData.LayoutMap paramLayoutMap, boolean paramBoolean1, int paramInt1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2)
  {
    int[] arrayOfInt = paramLayoutMap.getLayoutForStyle(paramBoolean1, paramInt1, false, paramBoolean2, paramBoolean3);
    if ((arrayOfInt.length > 1) && (arrayOfInt[1] >= 0))
    {
      KeyboardSwitch[] arrayOfKeyboardSwitch2 = new KeyboardSwitch[2];
      arrayOfKeyboardSwitch2[0] = new UpdateLayoutKeyboardSwitch(arrayOfInt[0], paramLayoutMap, paramInt2);
      arrayOfKeyboardSwitch2[1] = new UpdateLayoutKeyboardSwitch(arrayOfInt[1], paramLayoutMap, paramInt2);
      return arrayOfKeyboardSwitch2;
    }
    KeyboardSwitch[] arrayOfKeyboardSwitch1 = new KeyboardSwitch[1];
    arrayOfKeyboardSwitch1[0] = new UpdateLayoutKeyboardSwitch(arrayOfInt[0], paramLayoutMap, paramInt2);
    return arrayOfKeyboardSwitch1;
  }
  
  private KeyboardSwitch[] wrapSimpleSwitches(int[] paramArrayOfInt)
  {
    if ((paramArrayOfInt.length > 1) && (paramArrayOfInt[1] >= 0))
    {
      KeyboardSwitch[] arrayOfKeyboardSwitch2 = new KeyboardSwitch[2];
      arrayOfKeyboardSwitch2[0] = new SimpleKeyboardSwitch(paramArrayOfInt[0]);
      arrayOfKeyboardSwitch2[1] = new SimpleKeyboardSwitch(paramArrayOfInt[1]);
      return arrayOfKeyboardSwitch2;
    }
    KeyboardSwitch[] arrayOfKeyboardSwitch1 = new KeyboardSwitch[1];
    arrayOfKeyboardSwitch1[0] = new SimpleKeyboardSwitch(paramArrayOfInt[0]);
    return arrayOfKeyboardSwitch1;
  }
  
  public void addKeyboardChangeListener(KeyboardChangeListener paramKeyboardChangeListener)
  {
    this.mKeyboardChangeListeners.add(paramKeyboardChangeListener);
  }
  
  public void addOptionsListener(OptionStateListener paramOptionStateListener)
  {
    this.mOptionStateListeners.add(paramOptionStateListener);
  }
  
  public int getCurrentKeyboardLayoutId()
  {
    return this.mCurrentKeyboardLayoutId;
  }
  
  public void onLayoutChanged(Map<LayoutData.LayoutMap, Set<LanguagePack>> paramMap)
  {
    updateLayouts(paramMap);
    this.mHandler.post(new ReloadKeyboardIfNecessary(null));
  }
  
  public void onPredictorError() {}
  
  public void onPredictorReady()
  {
    this.mHandler.post(new ReloadKeyboardIfNecessary(null));
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if ((paramString.equals(getString(2131296319))) || (paramString.equals(getString(2131296476))) || (paramString.equals(getString(2131296478))) || (paramString.equals(getString(2131296498))) || (paramString.equals(getString(2131296489))) || (paramString.equals(getString(2131296777))) || (paramString.equals(getString(2131296310))) || (paramString.equals(KeyboardState.FULL_FLOATING.getName())) || (paramString.equals("pref_keyboard_layout_docked_state"))) {
      clearKeyboards();
    }
  }
  
  public void reloadKeyboard()
  {
    if (this.mCurrentKeyboard1 != null) {
      selectKeyboard(this.mCurrentKeyboardLayoutId, true);
    }
  }
  
  public void removeKeyboardChangeListener(KeyboardChangeListener paramKeyboardChangeListener)
  {
    this.mKeyboardChangeListeners.remove(paramKeyboardChangeListener);
  }
  
  public void resetKeyboardToLastKnownState()
  {
    clearKeyboards();
    if ((this.mKeyboardContainer == null) || (!this.mKeyboardContainer.isShown())) {}
    while (this.mCurrentEditorInfoExtract == null) {
      return;
    }
    startInputView(this.mCurrentEditorInfoExtract);
  }
  
  public void selectKeyboard(int paramInt, boolean paramBoolean)
  {
    if ((paramBoolean) && (this.mPreferences.isArrowsEnabled())) {
      clearKeyboards();
    }
    KeyboardSwitch[] arrayOfKeyboardSwitch;
    try
    {
      arrayOfKeyboardSwitch = translateId(paramInt);
      if ((arrayOfKeyboardSwitch.length != 0) && (arrayOfKeyboardSwitch[0].getLayoutId() != DynamicSwitch.NONE.getValue()))
      {
        if (arrayOfKeyboardSwitch.length > 1)
        {
          updateStatistics(arrayOfKeyboardSwitch[0].getLayoutId());
          selectKeyboard(arrayOfKeyboardSwitch[0], arrayOfKeyboardSwitch[1], paramBoolean);
        }
      }
      else {
        return;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      LogUtil.e(TAG, localIllegalArgumentException.getMessage());
      return;
    }
    updateStatistics(arrayOfKeyboardSwitch[0].getLayoutId());
    selectKeyboard(arrayOfKeyboardSwitch[0], paramBoolean);
  }
  
  public void selectKeyboard(LayoutData.LayoutMap paramLayoutMap)
  {
    this.mPreferences.setKeyboardLayout(paramLayoutMap);
    LayoutLanguagePack localLayoutLanguagePack = (LayoutLanguagePack)this.mLayouts.get(this.mCurrentLayoutIndex);
    Iterator localIterator = localLayoutLanguagePack.languagePacks.iterator();
    while (localIterator.hasNext())
    {
      LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
      if (localLanguagePack.getAvailableLayouts().contains(paramLayoutMap)) {
        this.mFluencyProxy.getLanguagePackManager().setCurrentLayout(localLanguagePack, paramLayoutMap.getLayoutName(), false);
      }
    }
    this.mLayouts.set(this.mCurrentLayoutIndex, new LayoutLanguagePack(paramLayoutMap, localLayoutLanguagePack.languagePacks));
    this.mPreferences.getKeyboardLayoutStyle(this.mContext);
    this.mPreferences.getKeyboardLayoutStyle(this.mContext);
    boolean bool1 = this.mPreferences.getKeyboardDockedState(this.mContext);
    boolean bool2 = this.mPreferences.getUsePCLayoutStyle(this.mContext.getResources().getBoolean(2131492874));
    boolean bool3 = this.mPreferences.getShowSplitNumpad(this.mContext, this.mContext.getResources().getBoolean(2131492877));
    int[] arrayOfInt = paramLayoutMap.getLayoutForStyle(bool1, this.mPreferences.getKeyboardLayoutStyle(this.mContext), false, bool2, bool3);
    if ((arrayOfInt.length > 1) && (arrayOfInt[1] >= 0))
    {
      selectKeyboard(new SimpleKeyboardSwitch(arrayOfInt[0]), new SimpleKeyboardSwitch(arrayOfInt[1]), false);
      return;
    }
    selectKeyboard(new SimpleKeyboardSwitch(arrayOfInt[0]), false);
  }
  
  public void setKeyboardContainer(KeyboardChoreographer paramKeyboardChoreographer)
  {
    this.mKeyboardContainer = paramKeyboardChoreographer;
  }
  
  public void startInputView(EditorInfo paramEditorInfo)
  {
    this.mCurrentEditorInfoExtract = new EditorInfoExtract(paramEditorInfo.inputType, paramEditorInfo.fieldName, paramEditorInfo.imeOptions, paramEditorInfo.privateImeOptions, paramEditorInfo.packageName);
    startInputView(this.mCurrentEditorInfoExtract);
  }
  
  public static enum DynamicSwitch
  {
    private final int value;
    
    static
    {
      ABC = new DynamicSwitch("ABC", 2, -2);
      LANGUAGE_NEXT = new DynamicSwitch("LANGUAGE_NEXT", 3, -3);
      LANGUAGE_PREVIOUS = new DynamicSwitch("LANGUAGE_PREVIOUS", 4, -4);
      DynamicSwitch[] arrayOfDynamicSwitch = new DynamicSwitch[5];
      arrayOfDynamicSwitch[0] = NONE;
      arrayOfDynamicSwitch[1] = PREVIOUS;
      arrayOfDynamicSwitch[2] = ABC;
      arrayOfDynamicSwitch[3] = LANGUAGE_NEXT;
      arrayOfDynamicSwitch[4] = LANGUAGE_PREVIOUS;
      $VALUES = arrayOfDynamicSwitch;
    }
    
    private DynamicSwitch(int paramInt)
    {
      this.value = paramInt;
    }
    
    public static DynamicSwitch forValue(int paramInt)
    {
      for (DynamicSwitch localDynamicSwitch : ) {
        if (localDynamicSwitch.getValue() == paramInt) {
          return localDynamicSwitch;
        }
      }
      return null;
    }
    
    public int getValue()
    {
      return this.value;
    }
  }
  
  private final class EditorInfoExtract
  {
    final String fieldName;
    final int imeOptions;
    final int inputType;
    final String packageName;
    final String privateImeOptions;
    
    public EditorInfoExtract(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3)
    {
      this.inputType = paramInt1;
      this.fieldName = paramString1;
      this.imeOptions = paramInt2;
      this.privateImeOptions = paramString2;
      this.packageName = paramString3;
    }
  }
  
  private static final class KeyboardCacheMap
    extends LinkedHashMap<String, MainKeyboard>
  {
    public KeyboardCacheMap()
    {
      super(0.75F, true);
    }
    
    protected boolean removeEldestEntry(Map.Entry<String, MainKeyboard> paramEntry)
    {
      return size() > 3;
    }
  }
  
  protected static enum KeyboardMode
  {
    private final int value;
    
    static
    {
      IM = new KeyboardMode("IM", 1, 2131230979);
      EMAIL = new KeyboardMode("EMAIL", 2, 2131230980);
      URL = new KeyboardMode("URL", 3, 2131230979);
      SYMBOLS = new KeyboardMode("SYMBOLS", 4, 2131230979);
      SYMBOLS_ALT = new KeyboardMode("SYMBOLS_ALT", 5, 2131230979);
      PHONE = new KeyboardMode("PHONE", 6, 2131230979);
      PIN = new KeyboardMode("PIN", 7, 2131230979);
      KeyboardMode[] arrayOfKeyboardMode = new KeyboardMode[8];
      arrayOfKeyboardMode[0] = TEXT;
      arrayOfKeyboardMode[1] = IM;
      arrayOfKeyboardMode[2] = EMAIL;
      arrayOfKeyboardMode[3] = URL;
      arrayOfKeyboardMode[4] = SYMBOLS;
      arrayOfKeyboardMode[5] = SYMBOLS_ALT;
      arrayOfKeyboardMode[6] = PHONE;
      arrayOfKeyboardMode[7] = PIN;
      $VALUES = arrayOfKeyboardMode;
    }
    
    private KeyboardMode(int paramInt)
    {
      this.value = paramInt;
    }
    
    public int getValue()
    {
      return this.value;
    }
  }
  
  private static abstract interface KeyboardSwitch
  {
    public abstract void apply();
    
    public abstract List<LayoutData.LayoutMap> getAlternativeLayouts();
    
    public abstract LanguageSwitchData getLanguageSwitchData();
    
    public abstract LayoutData.LayoutMap getLayout();
    
    public abstract int getLayoutId();
    
    public abstract boolean shouldChangeLayout();
  }
  
  public static final class LayoutLanguagePack
  {
    public final Vector<LanguagePack> languagePacks;
    public final LayoutData.LayoutMap layout;
    
    public LayoutLanguagePack(LayoutData.LayoutMap paramLayoutMap, Vector<LanguagePack> paramVector)
    {
      this.layout = paramLayoutMap;
      this.languagePacks = paramVector;
    }
    
    private String correct(String paramString, LanguagePack paramLanguagePack)
    {
      if ((paramString != null) && (Build.VERSION.SDK_INT < 11) && (Bidi.requiresBidi(paramString.toCharArray(), 0, paramString.length())))
      {
        String str = paramLanguagePack.getDefaultLayoutName();
        paramString = str.substring(0, 1).toUpperCase(Locale.US) + str.substring(1);
      }
      return paramString;
    }
    
    public String toShortString(Context paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      if (this.languagePacks.isEmpty()) {
        return this.layout.toString(paramContext);
      }
      Iterator localIterator = this.languagePacks.iterator();
      while (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        localStringBuilder.append(localLanguagePack.getLanguage() + "/");
      }
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
      return localStringBuilder.toString();
    }
    
    public String toString(Context paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      if (this.languagePacks.isEmpty()) {
        return this.layout.toString(paramContext);
      }
      Iterator localIterator = this.languagePacks.iterator();
      while (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        localStringBuilder.append(correct(localLanguagePack.getName(), localLanguagePack) + "/");
      }
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
      return localStringBuilder.toString();
    }
  }
  
  private final class ReloadKeyboardIfNecessary
    implements Runnable
  {
    private ReloadKeyboardIfNecessary() {}
    
    public void run()
    {
      if ((KeyboardSwitcher.this.mCurrentKeyboard1 != null) && (KeyboardSwitcher.this.mKeyboardContainer.isShown()) && (KeyboardSwitcher.this.mCurrentKeyboard1 == KeyboardSwitcher.this.mKeyboardContainer.getKeyboardView().getKeyboard())) {}
      KeyboardSwitcher.KeyboardSwitch[] arrayOfKeyboardSwitch;
      for (int i = 1;; i = 0)
      {
        arrayOfKeyboardSwitch = KeyboardSwitcher.this.translateId(KeyboardSwitcher.this.mCurrentKeyboardLayoutId);
        KeyboardSwitcher.access$802(KeyboardSwitcher.this, arrayOfKeyboardSwitch[0].getLayoutId());
        KeyboardSwitcher.this.clearKeyboards();
        if (i != 0)
        {
          if ((arrayOfKeyboardSwitch.length <= 1) || (arrayOfKeyboardSwitch[1].getLayoutId() <= 0)) {
            break;
          }
          KeyboardSwitcher.access$1402(KeyboardSwitcher.this, arrayOfKeyboardSwitch[1].getLayoutId());
          KeyboardSwitcher.this.selectKeyboard(arrayOfKeyboardSwitch[0], arrayOfKeyboardSwitch[1], false);
        }
        return;
      }
      KeyboardSwitcher.this.selectKeyboard(arrayOfKeyboardSwitch[0], false);
    }
  }
  
  private class SimpleKeyboardSwitch
    implements KeyboardSwitcher.KeyboardSwitch
  {
    private final List<LayoutData.LayoutMap> alternateLayouts;
    private final LanguageSwitchData languageData;
    private final LayoutData.LayoutMap layout;
    private final int layoutId;
    
    public SimpleKeyboardSwitch(int paramInt)
    {
      this(paramInt, KeyboardSwitcher.this.createLanguageSwitchData(KeyboardSwitcher.this.mCurrentLayoutIndex), KeyboardSwitcher.this.mCurrentLayoutIndex);
    }
    
    protected SimpleKeyboardSwitch(int paramInt1, LanguageSwitchData paramLanguageSwitchData, int paramInt2)
    {
      this.layoutId = paramInt1;
      this.layout = KeyboardSwitcher.this.mPreferences.getKeyboardLayout();
      this.languageData = paramLanguageSwitchData;
      this.alternateLayouts = getAlternativeLayoutsForIndex(paramInt2);
    }
    
    private List<LayoutData.LayoutMap> getAlternativeLayoutsForIndex(int paramInt)
    {
      ArrayList localArrayList = new ArrayList();
      if (KeyboardSwitcher.this.mLayouts.size() == 0) {}
      for (;;)
      {
        return localArrayList;
        Iterator localIterator = ((KeyboardSwitcher.LayoutLanguagePack)KeyboardSwitcher.this.mLayouts.get(paramInt)).languagePacks.iterator();
        while (localIterator.hasNext()) {
          localArrayList.addAll(((LanguagePack)localIterator.next()).getAvailableLayouts());
        }
      }
    }
    
    public void apply()
    {
      KeyboardSwitcher.access$802(KeyboardSwitcher.this, getLayoutId());
    }
    
    public List<LayoutData.LayoutMap> getAlternativeLayouts()
    {
      return this.alternateLayouts;
    }
    
    public LanguageSwitchData getLanguageSwitchData()
    {
      return this.languageData;
    }
    
    public LayoutData.LayoutMap getLayout()
    {
      return this.layout;
    }
    
    public int getLayoutId()
    {
      return this.layoutId;
    }
    
    public boolean shouldChangeLayout()
    {
      return KeyboardSwitcher.this.mCurrentKeyboardLayoutId != getLayoutId();
    }
  }
  
  private final class UpdateLayoutKeyboardSwitch
    extends KeyboardSwitcher.SimpleKeyboardSwitch
  {
    private final LayoutData.LayoutMap newLayout;
    private final int newLayoutIndex;
    
    public UpdateLayoutKeyboardSwitch(int paramInt1, LayoutData.LayoutMap paramLayoutMap, int paramInt2)
    {
      super(paramInt1, KeyboardSwitcher.this.createLanguageSwitchData(paramInt2), paramInt2);
      this.newLayout = paramLayoutMap;
      this.newLayoutIndex = paramInt2;
    }
    
    public void apply()
    {
      super.apply();
      KeyboardSwitcher.access$502(KeyboardSwitcher.this, this.newLayoutIndex);
      KeyboardSwitcher.this.mPreferences.setKeyboardLayout(this.newLayout);
    }
    
    public LayoutData.LayoutMap getLayout()
    {
      return this.newLayout;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.KeyboardSwitcher
 * JD-Core Version:    0.7.0.1
 */