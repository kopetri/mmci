package com.touchtype.keyboard.key;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.KeyboardLoader;
import com.touchtype.keyboard.KeyboardLoader.KeyboardAttributes;
import com.touchtype.keyboard.KeyboardLoader.Row;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.KeyboardSwitcher.DynamicSwitch;
import com.touchtype.keyboard.LanguageSwitchData;
import com.touchtype.keyboard.LanguageSwitchData.LanguageSwitchEntry;
import com.touchtype.keyboard.MiniKeyboard;
import com.touchtype.keyboard.MiniKeyboard.Orientation;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.KeyPressModelLayout;
import com.touchtype.keyboard.key.actions.Action;
import com.touchtype.keyboard.key.actions.ActionParams;
import com.touchtype.keyboard.key.actions.ActionParams.ActionParamsBuilder;
import com.touchtype.keyboard.key.actions.ActionParams.RepeatBehaviourPresets;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.key.actions.BloopAction;
import com.touchtype.keyboard.key.actions.CasedAction;
import com.touchtype.keyboard.key.actions.CloseKeyboardAction;
import com.touchtype.keyboard.key.actions.CycleAction;
import com.touchtype.keyboard.key.actions.CycleCandidatesAction;
import com.touchtype.keyboard.key.actions.DeleteWordAction;
import com.touchtype.keyboard.key.actions.DownUpAction;
import com.touchtype.keyboard.key.actions.FlowAction;
import com.touchtype.keyboard.key.actions.InputFilterAction;
import com.touchtype.keyboard.key.actions.InsertDefaultCandidateAction;
import com.touchtype.keyboard.key.actions.InterimMenuAction;
import com.touchtype.keyboard.key.actions.OptionsKeyAction;
import com.touchtype.keyboard.key.actions.PopupAction;
import com.touchtype.keyboard.key.actions.SettingsLaunchAction;
import com.touchtype.keyboard.key.actions.ShiftAction;
import com.touchtype.keyboard.key.actions.SlidingPopupAction;
import com.touchtype.keyboard.key.actions.StartlessFlowAction;
import com.touchtype.keyboard.key.actions.StatsLoggerAction;
import com.touchtype.keyboard.key.actions.SwitchLayoutAction;
import com.touchtype.keyboard.key.actions.TextAction;
import com.touchtype.keyboard.key.actions.VoiceAction;
import com.touchtype.keyboard.key.callbacks.DragEvent.Direction;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.key.callbacks.DragFilterFactory;
import com.touchtype.keyboard.key.contents.DualKeyContent;
import com.touchtype.keyboard.key.contents.EmptyContent;
import com.touchtype.keyboard.key.contents.IconContent;
import com.touchtype.keyboard.key.contents.InputFilterContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.contents.LSSBContent;
import com.touchtype.keyboard.key.contents.ResizeDualKeyContent;
import com.touchtype.keyboard.key.contents.ScaleLinkedTextContent;
import com.touchtype.keyboard.key.contents.ShiftIconContent;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.key.delegates.CasedDrawDelegate;
import com.touchtype.keyboard.key.delegates.EmptyDelegate.FlowCompleteDelegate;
import com.touchtype.keyboard.key.delegates.KeyDrawDelegate;
import com.touchtype.keyboard.key.delegates.KeyTouchHandler;
import com.touchtype.keyboard.key.delegates.SimpleDrawDelegate;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.util.TextMetrics.TextMetricsRegister;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public final class KeyFactory
{
  private int mChineseInputFilterKeyCounter = 0;
  private final Context mContext;
  private final DefaultPredictionProvider mDefaultPredictionProvider;
  private final boolean mDisablePopups;
  private final FlowOrSwipe mFlowOrSwipe;
  private final FluencyServiceProxy mFluencyProxy;
  private final InputEventModel mInputEventModel;
  private final IntentionalEventFilter mIntentionalEventFilter = new IntentionalEventFilter();
  private final boolean mIsMicrophoneKeyEnabled;
  private final KeyboardBehaviour mKeyboardBehaviour;
  private final KeyboardSwitcher mKeyboardSwitcher;
  private final LanguageSwitchData mLanguageSwitchData;
  private final KeyPressModelLayout mLayout;
  public final Locale mLocale;
  private final Set<String> mLowerCasePrimaryLetters;
  private final int mNextLayoutId;
  private final PredictionFilter mPredictionFilter = new PredictionFilter();
  private final TouchTypePreferences mPreferences;
  private final TextMetrics.TextMetricsRegister mRegister;
  private final boolean mRightToLeft;
  private final boolean mShiftStateInsensitive;
  private final Set<String> mUpperCasePrimaryLetters;
  
  public KeyFactory(Context paramContext, FluencyServiceProxy paramFluencyServiceProxy, InputEventModel paramInputEventModel, KeyboardSwitcher paramKeyboardSwitcher, LanguageSwitchData paramLanguageSwitchData, KeyboardBehaviour paramKeyboardBehaviour, Locale paramLocale, FlowOrSwipe paramFlowOrSwipe, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, TextMetrics.TextMetricsRegister paramTextMetricsRegister, int paramInt, DefaultPredictionProvider paramDefaultPredictionProvider, Set<String> paramSet)
  {
    this.mContext = paramContext;
    this.mFluencyProxy = paramFluencyServiceProxy;
    this.mInputEventModel = paramInputEventModel;
    this.mLayout = new KeyPressModelLayout();
    this.mKeyboardSwitcher = paramKeyboardSwitcher;
    this.mLanguageSwitchData = paramLanguageSwitchData;
    this.mPreferences = TouchTypePreferences.getInstance(paramContext);
    this.mLocale = paramLocale;
    this.mFlowOrSwipe = paramFlowOrSwipe;
    this.mRightToLeft = paramBoolean1;
    this.mIsMicrophoneKeyEnabled = paramBoolean2;
    this.mDisablePopups = paramBoolean3;
    this.mRegister = paramTextMetricsRegister;
    this.mNextLayoutId = paramInt;
    this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
    this.mKeyboardBehaviour = paramKeyboardBehaviour;
    this.mShiftStateInsensitive = paramKeyboardBehaviour.shiftStateInsensitive();
    this.mLowerCasePrimaryLetters = new HashSet();
    this.mUpperCasePrimaryLetters = new HashSet();
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      this.mLowerCasePrimaryLetters.add(str.toLowerCase(this.mLocale));
      this.mUpperCasePrimaryLetters.add(str.toUpperCase(this.mLocale));
    }
  }
  
  private Action addFlicks(List<String> paramList, float paramFloat, KeyState paramKeyState, Action paramAction)
  {
    float f = 180.0F;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      DragFilter localDragFilter = DragFilterFactory.makeFilter((int)f);
      f += 360.0F / paramList.size();
      ActionParams localActionParams = new ActionParams.ActionParamsBuilder().dragBehaviour(localDragFilter, paramFloat, paramFloat).build();
      PopupContent.PreviewContent localPreviewContent = new PopupContent.PreviewContent(str);
      TextAction localTextAction = new TextAction(EnumSet.of(ActionType.DRAG_CLICK), this.mInputEventModel, str, false, false, localActionParams, new PopupAction(EnumSet.of(ActionType.DRAG), EnumSet.of(ActionType.UP, ActionType.CANCEL), paramKeyState, localPreviewContent, localActionParams, paramAction));
      paramAction = localTextAction;
    }
    return paramAction;
  }
  
  private void addLineKeyToLayout(String paramString, RectF paramRectF)
  {
    float f = paramRectF.centerY();
    this.mLayout.put(LegacyTouchUtils.lineKey(new PointF(paramRectF.left, f), new PointF(paramRectF.right, f), 3.0F, 0.1F), toCharacterArray(paramString));
  }
  
  private Action addLongPress(KeyState paramKeyState, PopupContent paramPopupContent, Action paramAction)
  {
    int i = this.mPreferences.getLongPressTimeOut();
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).build();
    if ((paramPopupContent instanceof PopupContent.CasedPopupContent)) {
      return new CasedAction(this.mInputEventModel, addLongPress(paramKeyState, paramPopupContent.applyShiftState(TouchTypeSoftKeyboard.ShiftState.UNSHIFTED), paramAction), addLongPress(paramKeyState, paramPopupContent.applyShiftState(TouchTypeSoftKeyboard.ShiftState.SHIFTED), paramAction));
    }
    if ((paramPopupContent instanceof PopupContent.EmptyPopupContent)) {
      return paramAction;
    }
    BloopAction localBloopAction = new BloopAction(EnumSet.of(ActionType.LONGPRESS), this.mContext, ActionParams.EMPTY_PARAMS, paramAction);
    PopupAction localPopupAction = new PopupAction(EnumSet.of(ActionType.LONGPRESS), EnumSet.of(ActionType.UP, ActionType.SLIDE_OUT, ActionType.CANCEL), paramKeyState, paramPopupContent, localActionParams, localBloopAction);
    if ((paramPopupContent instanceof PopupContent.PreviewContent)) {
      return createCaseSensitiveTextAction(EnumSet.of(ActionType.LONGCLICK), ((PopupContent.PreviewContent)paramPopupContent).mLabel, false, true, localActionParams, localPopupAction);
    }
    return localPopupAction;
  }
  
  private Action addMainPopup(KeyState paramKeyState, String paramString, Action paramAction)
  {
    if ((!this.mContext.getResources().getBoolean(2131492903)) || (this.mDisablePopups)) {
      return paramAction;
    }
    PopupContent.PreviewContent localPreviewContent = new PopupContent.PreviewContent(paramString);
    return new PopupAction(EnumSet.of(ActionType.DOWN, ActionType.SLIDE_IN), EnumSet.of(ActionType.UP, ActionType.SLIDE_OUT, ActionType.CANCEL), paramKeyState, localPreviewContent, ActionParams.EMPTY_PARAMS, paramAction);
  }
  
  private Action addMicroActions(Action paramAction, ActionParams paramActionParams)
  {
    return addMicroInsertCandidateAction(addMicroCycleCandidatesAction(new TextAction(EnumSet.of(ActionType.SWIPE_DOWN), this.mInputEventModel, " ", paramAction), paramActionParams), paramActionParams);
  }
  
  private Action addMicroCycleCandidatesAction(Action paramAction, ActionParams paramActionParams)
  {
    return new CycleCandidatesAction(EnumSet.of(ActionType.SWIPE_UP), paramActionParams, paramAction, this.mInputEventModel);
  }
  
  private Action addMicroInsertCandidateAction(Action paramAction, ActionParams paramActionParams)
  {
    if (this.mRightToLeft) {}
    for (ActionType localActionType = ActionType.SWIPE_LEFT;; localActionType = ActionType.SWIPE_RIGHT) {
      return new InsertDefaultCandidateAction(this.mInputEventModel, this.mDefaultPredictionProvider, EnumSet.of(localActionType), paramActionParams, paramAction);
    }
  }
  
  private void addPointKeyToLayout(String paramString, RectF paramRectF)
  {
    this.mLayout.put(LegacyTouchUtils.pointKey(new PointF(paramRectF.centerX(), paramRectF.centerY())), toCharacterArray(paramString));
  }
  
  private Action addSwipeActions(KeyArea paramKeyArea, Action paramAction)
  {
    ActionType localActionType;
    ActionParams localActionParams;
    Object localObject;
    if (this.mRightToLeft)
    {
      localActionType = ActionType.SWIPE_RIGHT;
      localActionParams = new ActionParams.ActionParamsBuilder().swipeXActivationThreshold(2.0F * paramKeyArea.getDrawBounds().width()).swipeYActivationThreshold(paramKeyArea.getDrawBounds().height()).swipeMinXVelocity(2.0F * paramKeyArea.getDrawBounds().width()).swipeMinYVelocity(2.0F * paramKeyArea.getDrawBounds().height()).build();
      localObject = new BloopAction(EnumSet.of(localActionType), this.mContext, localActionParams, new DeleteWordAction(this.mInputEventModel, EnumSet.of(localActionType), localActionParams, paramAction));
      if (!ProductConfiguration.isWatchBuild(this.mContext)) {
        break label136;
      }
      localObject = addMicroActions((Action)localObject, localActionParams);
    }
    label136:
    do
    {
      return localObject;
      localActionType = ActionType.SWIPE_LEFT;
      break;
      if (this.mPreferences.isSwipeDownEnabled()) {
        localObject = new CloseKeyboardAction(EnumSet.of(ActionType.SWIPE_DOWN), localActionParams, (Action)localObject);
      }
    } while (!this.mPreferences.isSwipeUpEnabled());
    return new ShiftAction(this.mInputEventModel, EnumSet.of(ActionType.SWIPE_UP), localActionParams, (Action)localObject);
  }
  
  private Action createArrowKeyAction(KeyState paramKeyState, int paramInt)
  {
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().repeatBehaviour(ActionParams.RepeatBehaviourPresets.getDefaultRepeatBehaviour()).build();
    String str = new String(Character.toChars(paramInt));
    return new BloopAction(EnumSet.of(ActionType.DOWN, ActionType.REPEAT), this.mContext, localActionParams, new TextAction(EnumSet.of(ActionType.CLICK, ActionType.REPEAT), this.mInputEventModel, str, false, false, localActionParams, new StatsLoggerAction(this.mPreferences)));
  }
  
  private KeyDrawDelegate createCaseSensitiveDrawDelegate(KeyStyle.StyleId paramStyleId, KeyArea paramKeyArea, KeyContent paramKeyContent, KeyState paramKeyState)
  {
    if (this.mShiftStateInsensitive) {
      return new SimpleDrawDelegate(paramStyleId, paramKeyArea, paramKeyContent, paramKeyState);
    }
    return new CasedDrawDelegate(paramStyleId, paramKeyArea, paramKeyContent, paramKeyState, this.mInputEventModel);
  }
  
  private Action createCaseSensitiveTextAction(EnumSet<ActionType> paramEnumSet, String paramString, boolean paramBoolean1, boolean paramBoolean2, ActionParams paramActionParams, Action paramAction)
  {
    if (this.mShiftStateInsensitive) {
      return new TextAction(paramEnumSet, this.mInputEventModel, paramString, paramBoolean1, paramBoolean2, paramActionParams, paramAction);
    }
    return new CasedAction(this.mInputEventModel, new TextAction(paramEnumSet, this.mInputEventModel, paramString.toLowerCase(this.mLocale), paramBoolean1, paramBoolean2, paramActionParams, paramAction), new TextAction(paramEnumSet, this.mInputEventModel, paramString.toUpperCase(this.mLocale), paramBoolean1, paramBoolean2, paramActionParams, paramAction));
  }
  
  private PopupContent createCasedPopupContent(TypedArray paramTypedArray, KeyArea paramKeyArea)
  {
    CharSequence localCharSequence = paramTypedArray.getText(4);
    if (localCharSequence != null) {}
    ArrayList localArrayList;
    for (String str1 = localCharSequence.toString();; str1 = "")
    {
      localArrayList = new ArrayList(Arrays.asList(str1.split(" ")));
      Object localObject1 = new ArrayList();
      Object localObject2 = new ArrayList();
      while (localArrayList.remove("")) {}
      String str2 = paramTypedArray.getString(11);
      if (str2 != null)
      {
        localObject1 = getPopupCharacters(localArrayList, str2, false, false);
        localObject2 = getPopupCharacters(localArrayList, str2, true, false);
      }
      if ((((List)localObject1).size() <= 1) && (((List)localObject2).size() <= 1)) {
        break;
      }
      return new PopupContent.CasedPopupContent(getPopupFromChars((List)localObject1, paramKeyArea), getPopupFromChars((List)localObject2, paramKeyArea));
    }
    if (localArrayList.size() == 1) {
      return getPopupFromChars(localArrayList, paramKeyArea);
    }
    return PopupContent.EMPTY_CONTENT;
  }
  
  private Action createChineseInputFilterKeyAction(KeyState paramKeyState, KeyContent paramKeyContent)
  {
    InputFilterAction localInputFilterAction = new InputFilterAction(EnumSet.of(ActionType.CLICK), ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences), ((InputFilterContent)paramKeyContent).getText(), this.mInputEventModel, this.mChineseInputFilterKeyCounter);
    return new BloopAction(EnumSet.of(ActionType.CLICK), this.mContext, ActionParams.EMPTY_PARAMS, localInputFilterAction);
  }
  
  private KeyContent createChineseInputFilterKeyContent(KeyFields paramKeyFields)
  {
    return InputFilterContent.getDefaultInputFilterContent(paramKeyFields.getBottomLabel(), paramKeyFields.getBottomText(), this.mLocale, Typeface.DEFAULT, this.mChineseInputFilterKeyCounter);
  }
  
  private Action createCommaKeyAction(KeyState paramKeyState, String paramString)
  {
    TextAction localTextAction = new TextAction(this.mInputEventModel, paramString, new StatsLoggerAction(this.mPreferences));
    if ((this.mPreferences.isVoiceEnabled()) && (this.mIsMicrophoneKeyEnabled))
    {
      int i = this.mPreferences.getLongPressTimeOut();
      ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).build();
      return new BloopAction(EnumSet.of(ActionType.DOWN, ActionType.LONGPRESS), this.mContext, ActionParams.EMPTY_PARAMS, new VoiceAction(EnumSet.of(ActionType.LONGPRESS), localActionParams, localTextAction));
    }
    return new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, localTextAction);
  }
  
  private PopupContent createCurrencyPopupContent(TypedArray paramTypedArray, KeyArea paramKeyArea)
  {
    CharSequence localCharSequence = paramTypedArray.getText(4);
    if (localCharSequence != null) {}
    for (String str1 = localCharSequence.toString();; str1 = "")
    {
      ArrayList localArrayList = new ArrayList(Arrays.asList(str1.split(" ")));
      Object localObject = new ArrayList();
      while (localArrayList.remove("")) {}
      String str2 = paramTypedArray.getString(11);
      if (str2 != null) {
        localObject = getPopupCharacters(localArrayList, str2, false, false);
      }
      String str3 = getLocalCurrencyGlyph();
      if (str3 != null)
      {
        if (paramTypedArray.getBoolean(12, false))
        {
          if (((List)localObject).contains(str3)) {
            ((List)localObject).remove(str3);
          }
          ((List)localObject).add(0, str3);
        }
        if ((paramTypedArray.getBoolean(13, false)) && (!str2.equals(str3)))
        {
          if (!((List)localObject).contains(str2)) {
            ((List)localObject).add(0, str2.toString());
          }
          ((List)localObject).remove(str3);
        }
      }
      if (((List)localObject).size() <= 0) {
        break;
      }
      return getPopupFromChars((List)localObject, paramKeyArea);
    }
    return PopupContent.EMPTY_CONTENT;
  }
  
  private Action createDeleteKeyAction()
  {
    BloopAction localBloopAction = new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, -5, ActionParams.EMPTY_PARAMS, new TextAction(this.mInputEventModel, KeyCodeString.DELETE, new StatsLoggerAction(this.mPreferences)));
    int i = this.mPreferences.getLongPressTimeOut();
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).repeatBehaviour(ActionParams.RepeatBehaviourPresets.getAcceleratingDeleteRepeatBehaviour(i)).build();
    return new BloopAction(EnumSet.of(ActionType.LONGPRESS, ActionType.REPEAT), this.mContext, -5, localActionParams, new DeleteWordAction(this.mInputEventModel, EnumSet.of(ActionType.LONGPRESS, ActionType.REPEAT), localActionParams, localBloopAction));
  }
  
  public static Key createEmptyKey(InputEventModel paramInputEventModel)
  {
    return new SimpleKey(new KeyArea(new RectF(), 0), new KeyStateImpl.EmptyState(), null, new EmptyDelegate.FlowCompleteDelegate(paramInputEventModel));
  }
  
  private Action createFlickAction(KeyArea paramKeyArea, KeyContent paramKeyContent, KeyState paramKeyState, List<String> paramList)
  {
    if ((paramKeyContent instanceof DualKeyContent)) {}
    for (String str = ((TextContent)((DualKeyContent)paramKeyContent).mBottomContent).getText();; str = ((TextContent)paramKeyContent).getText())
    {
      PopupContent.PreviewContent localPreviewContent = new PopupContent.PreviewContent(str);
      ActionParams localActionParams = new ActionParams.ActionParamsBuilder().dragBehaviour(DragFilterFactory.makeNonDragFilter(), 0.0F, 0.0F).build();
      float f = 0.6F * paramKeyArea.getDrawBounds().width();
      PopupAction localPopupAction = new PopupAction(EnumSet.of(ActionType.DOWN, ActionType.DRAG), EnumSet.of(ActionType.UP, ActionType.CANCEL), paramKeyState, localPreviewContent, localActionParams, new TextAction(EnumSet.of(ActionType.CLICK, ActionType.DRAG_CLICK), this.mInputEventModel, str, false, false, localActionParams, new StatsLoggerAction(this.mPreferences)));
      return addFlicks(paramList, f, paramKeyState, localPopupAction);
    }
  }
  
  private Action createIMEGoKeyAction(KeyState paramKeyState, int paramInt)
  {
    int i = this.mPreferences.getLongPressTimeOut();
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).build();
    return new OptionsKeyAction(this.mInputEventModel, this.mKeyboardSwitcher, paramInt, paramKeyState, localActionParams, this.mContext, new StatsLoggerAction(this.mPreferences));
  }
  
  private KeyContent createKeyContent(KeyFields paramKeyFields, Float paramFloat1, Float paramFloat2)
    throws KeyFactory.KeyLoaderException
  {
    int i = 1;
    Object localObject1;
    label32:
    Object localObject2;
    if (paramKeyFields.getTopIcon() != null)
    {
      localObject1 = IconContent.getDefaultTopIconContent(paramKeyFields.getTopIcon(), paramFloat1);
      if (paramKeyFields.getBottomIcon() == null) {
        break label176;
      }
      if (localObject1 != null) {
        break label158;
      }
      if (i == 0) {
        break label164;
      }
      localObject2 = IconContent.getDefaultMainIconContent(paramKeyFields.getBottomIcon());
    }
    for (;;)
    {
      if (localObject2 == null) {
        break label366;
      }
      if (localObject1 != null) {
        localObject2 = DualKeyContent.createDefaultDualContent((KeyContent)localObject1, (KeyContent)localObject2);
      }
      return localObject2;
      if (paramKeyFields.getTopLabel() != null)
      {
        if ((paramKeyFields.getOverrideMetricsTag() != null) && (paramFloat1 == null))
        {
          localObject1 = ScaleLinkedTextContent.getDefaultTopTextContent(paramKeyFields.getTopLabel(), this.mLocale, this.mRegister.getTextMetrics(paramKeyFields.getOverrideMetricsTag() + "_TOP"));
          break;
        }
        localObject1 = TextContent.getDefaultTopTextContent(paramKeyFields.getTopLabel(), this.mLocale, paramFloat1.floatValue());
        break;
      }
      localObject1 = null;
      break;
      label158:
      i = 0;
      break label32;
      label164:
      localObject2 = IconContent.getDefaultBottomIconContent(paramKeyFields.getBottomIcon());
      continue;
      label176:
      if (paramKeyFields.getBottomLabel() != null)
      {
        if (localObject1 == null) {}
        for (;;)
        {
          if (i != 0)
          {
            if (paramKeyFields.getOverrideMetricsTag() != null)
            {
              localObject2 = ScaleLinkedTextContent.getDefaultMainTextContent(paramKeyFields.getBottomLabel(), paramKeyFields.getBottomText(), this.mLocale, TextContent.getLetterKeyMainTextHeight(this.mContext), this.mRegister.getTextMetrics(paramKeyFields.getOverrideMetricsTag()));
              break;
              i = 0;
              continue;
            }
            localObject2 = TextContent.getDefaultMainTextContent(paramKeyFields.getBottomLabel(), paramKeyFields.getBottomText(), this.mLocale, TextContent.getLetterKeyMainTextHeight(this.mContext));
            break;
          }
        }
        if ((paramKeyFields.getOverrideMetricsTag() != null) && (paramFloat2 == null)) {
          localObject2 = ScaleLinkedTextContent.getDefaultBottomTextContent(this.mContext, paramKeyFields.getBottomLabel(), paramKeyFields.getBottomText(), this.mLocale, this.mRegister.getTextMetrics(paramKeyFields.getOverrideMetricsTag() + "_BOTTOM"));
        } else {
          localObject2 = TextContent.getDefaultBottomTextContent(paramKeyFields.getBottomLabel(), paramKeyFields.getBottomText(), this.mLocale, paramFloat2.floatValue());
        }
      }
      else
      {
        localObject2 = null;
      }
    }
    label366:
    return new EmptyContent();
  }
  
  private Action createLSSpaceKeyAction(KeyArea paramKeyArea, KeyState paramKeyState, PopupContent paramPopupContent)
  {
    ActionParams localActionParams1 = new ActionParams.ActionParamsBuilder().dragBehaviour(DragFilterFactory.makeFilter(DragEvent.Direction.RIGHT), -1.0F, 0.4F * paramKeyArea.getBounds().width()).build();
    ActionParams localActionParams2 = new ActionParams.ActionParamsBuilder().dragBehaviour(DragFilterFactory.makeFilter(DragEvent.Direction.LEFT), -1.0F, 0.4F * paramKeyArea.getBounds().width()).build();
    Context localContext1 = this.mContext;
    TouchTypePreferences localTouchTypePreferences1 = this.mPreferences;
    KeyboardSwitcher localKeyboardSwitcher1 = this.mKeyboardSwitcher;
    int i = KeyboardSwitcher.DynamicSwitch.LANGUAGE_NEXT.getValue();
    EnumSet localEnumSet1 = EnumSet.of(ActionType.DRAG_CLICK);
    Context localContext2 = this.mContext;
    TouchTypePreferences localTouchTypePreferences2 = this.mPreferences;
    KeyboardSwitcher localKeyboardSwitcher2 = this.mKeyboardSwitcher;
    int j = KeyboardSwitcher.DynamicSwitch.LANGUAGE_PREVIOUS.getValue();
    EnumSet localEnumSet2 = EnumSet.of(ActionType.DRAG_CLICK);
    SlidingPopupAction localSlidingPopupAction = new SlidingPopupAction(paramKeyState, paramPopupContent, 150, true, 0.1F * paramKeyArea.getBounds().width(), new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, 32, ActionParams.EMPTY_PARAMS, new TextAction(EnumSet.of(ActionType.CLICK, ActionType.LONGCLICK), this.mInputEventModel, " ", new StatsLoggerAction(this.mPreferences))));
    SwitchLayoutAction localSwitchLayoutAction1 = new SwitchLayoutAction(localContext2, localTouchTypePreferences2, localKeyboardSwitcher2, j, localEnumSet2, localActionParams2, localSlidingPopupAction);
    SwitchLayoutAction localSwitchLayoutAction2 = new SwitchLayoutAction(localContext1, localTouchTypePreferences1, localKeyboardSwitcher1, i, localEnumSet1, localActionParams1, localSwitchLayoutAction1);
    return localSwitchLayoutAction2;
  }
  
  private Action createLetterKeyAction(KeyArea paramKeyArea, KeyContent paramKeyContent, PopupContent paramPopupContent, KeyState paramKeyState)
    throws KeyFactory.KeyLoaderException
  {
    if ((paramKeyContent instanceof DualKeyContent))
    {
      DualKeyContent localDualKeyContent = (DualKeyContent)paramKeyContent;
      if (((localDualKeyContent.mBottomContent instanceof TextContent)) && ((localDualKeyContent.mTopContent instanceof TextContent)))
      {
        String str1 = ((TextContent)localDualKeyContent.mBottomContent).getLabel();
        String str2 = ((TextContent)localDualKeyContent.mBottomContent).getText();
        return addLongPress(paramKeyState, paramPopupContent, addMainPopup(paramKeyState, str1, new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, createCaseSensitiveTextAction(EnumSet.of(ActionType.CLICK), str2, true, false, ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences)))));
      }
    }
    else if ((paramKeyContent instanceof TextContent))
    {
      return addLongPress(paramKeyState, paramPopupContent, addMainPopup(paramKeyState, ((TextContent)paramKeyContent).getLabel(), new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, createCaseSensitiveTextAction(EnumSet.of(ActionType.CLICK), ((TextContent)paramKeyContent).getText(), true, false, ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences)))));
    }
    throw new KeyLoaderException("Invalid LetterKey Content: " + paramKeyContent.toString());
  }
  
  private Action createMiniKeyboardKeyAction(KeyArea paramKeyArea, TextContent paramTextContent, KeyState paramKeyState)
  {
    return new TextAction(EnumSet.of(ActionType.UP), this.mInputEventModel, paramTextContent.getText(), new StatsLoggerAction(this.mPreferences));
  }
  
  private Action createPeriodKeyAction(KeyState paramKeyState, KeyFields paramKeyFields)
  {
    int i;
    Object localObject;
    if ((paramKeyFields.hasDualContents()) && (!paramKeyFields.getTopLabel().equals("")))
    {
      i = 1;
      localObject = new TextAction(EnumSet.of(ActionType.CLICK), this.mInputEventModel, paramKeyFields.getBottomText(), new StatsLoggerAction(this.mPreferences));
      if (i != 0) {
        localObject = addLongPress(paramKeyState, new PopupContent.PreviewContent(paramKeyFields.getTopLabel()), (Action)localObject);
      }
      if (i == 0) {
        break label119;
      }
    }
    label119:
    for (EnumSet localEnumSet = EnumSet.of(ActionType.DOWN, ActionType.LONGPRESS);; localEnumSet = EnumSet.of(ActionType.DOWN))
    {
      return new BloopAction(localEnumSet, this.mContext, ActionParams.EMPTY_PARAMS, (Action)localObject);
      i = 0;
      break;
    }
  }
  
  private PopupContent createPopupContent(TypedArray paramTypedArray, KeyArea paramKeyArea)
  {
    CharSequence localCharSequence = paramTypedArray.getText(4);
    if (localCharSequence != null) {}
    ArrayList localArrayList;
    String str2;
    for (String str1 = localCharSequence.toString();; str1 = "")
    {
      localArrayList = new ArrayList(Arrays.asList(str1.split(" ")));
      while (localArrayList.remove("")) {}
      str2 = paramTypedArray.getString(11);
      if ((str2 == null) || (localArrayList.size() <= 0)) {
        break label193;
      }
      if (!localArrayList.contains(str2)) {
        localArrayList.add(0, str2.toString());
      }
      List localList = getPopupCharacters(new ArrayList(), str2, false, true);
      for (int i = 0; i < localList.size(); i++) {
        localArrayList.add(localList.get(i));
      }
    }
    return new PopupContent.MiniKeyboardContent(MiniKeyboard.createMiniKeyboard(this.mContext, this, localArrayList, str2.toString(), paramKeyArea, MiniKeyboard.Orientation.HORIZONTAL, true, this.mIsMicrophoneKeyEnabled), paramKeyArea);
    label193:
    return PopupContent.EMPTY_CONTENT;
  }
  
  private Action createPuncKeyAction(KeyArea paramKeyArea, KeyState paramKeyState, String paramString, PopupContent paramPopupContent)
  {
    return new SlidingPopupAction(paramKeyState, paramPopupContent, 150, false, 0.4F * paramKeyArea.getBounds().width(), new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new TextAction(this.mInputEventModel, paramString, new StatsLoggerAction(this.mPreferences))));
  }
  
  private Action createSettings123Action(KeyState paramKeyState, int paramInt)
  {
    int i = this.mPreferences.getLongPressTimeOut();
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).build();
    SwitchLayoutAction localSwitchLayoutAction = new SwitchLayoutAction(this.mContext, this.mPreferences, this.mKeyboardSwitcher, paramInt, EnumSet.of(ActionType.CLICK), ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences));
    if (this.mContext.getResources().getBoolean(2131492925)) {}
    for (Object localObject = new InterimMenuAction(KeyState.InterimMenu.SETTINGS, paramKeyState, EnumSet.of(ActionType.LONGPRESS), localActionParams, localSwitchLayoutAction);; localObject = new SettingsLaunchAction(this.mContext, EnumSet.of(ActionType.LONGPRESS), localActionParams, localSwitchLayoutAction)) {
      return new BloopAction(EnumSet.of(ActionType.DOWN, ActionType.LONGPRESS), this.mContext, ActionParams.EMPTY_PARAMS, (Action)localObject);
    }
  }
  
  private Action createShiftKeyAction()
  {
    return new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new DownUpAction(this.mInputEventModel, -1, new StatsLoggerAction(this.mPreferences)));
  }
  
  private Action createSmileyKeyAction(KeyContent paramKeyContent, KeyState paramKeyState, int paramInt)
  {
    EnumSet localEnumSet = EnumSet.of(ActionType.CLICK);
    String str = ((TextContent)paramKeyContent).getText();
    return addMainPopup(paramKeyState, str, new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new SwitchLayoutAction(this.mContext, this.mPreferences, this.mKeyboardSwitcher, paramInt, EnumSet.of(ActionType.CLICK), ActionParams.EMPTY_PARAMS, new TextAction(localEnumSet, this.mInputEventModel, str, new StatsLoggerAction(this.mPreferences)))));
  }
  
  private Action createSmileySwitchAction(KeyState paramKeyState, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    int i = this.mPreferences.getLongPressTimeOut();
    String str = ((TextContent)((DualKeyContent)createKeyContent(paramKeyFields, null, null)).mBottomContent).getText();
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).build();
    SwitchLayoutAction localSwitchLayoutAction = new SwitchLayoutAction(this.mContext, this.mPreferences, this.mKeyboardSwitcher, paramInt, EnumSet.of(ActionType.LONGPRESS), localActionParams, new TextAction(this.mInputEventModel, str, new StatsLoggerAction(this.mPreferences)));
    return new BloopAction(EnumSet.of(ActionType.CLICK, ActionType.LONGPRESS), this.mContext, ActionParams.EMPTY_PARAMS, localSwitchLayoutAction);
  }
  
  private Action createSpaceKeyAction(KeyFields paramKeyFields, KeyState paramKeyState)
  {
    int i;
    Object localObject;
    if ((paramKeyFields.hasDualContents()) && (!paramKeyFields.getTopLabel().equals("")))
    {
      i = 1;
      localObject = new TextAction(EnumSet.of(ActionType.CLICK), this.mInputEventModel, " ", new StatsLoggerAction(this.mPreferences));
      if (i != 0) {
        localObject = addLongPress(paramKeyState, new PopupContent.PreviewContent(paramKeyFields.getTopLabel()), (Action)localObject);
      }
      if (i == 0) {
        break label120;
      }
    }
    label120:
    for (EnumSet localEnumSet = EnumSet.of(ActionType.DOWN, ActionType.LONGPRESS);; localEnumSet = EnumSet.of(ActionType.DOWN))
    {
      return new BloopAction(localEnumSet, this.mContext, 32, ActionParams.EMPTY_PARAMS, (Action)localObject);
      i = 0;
      break;
    }
  }
  
  private Action createSwitchKeyAction(int paramInt)
  {
    return new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new SwitchLayoutAction(this.mContext, this.mPreferences, this.mKeyboardSwitcher, paramInt, EnumSet.of(ActionType.CLICK), ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences)));
  }
  
  private Action createSymbolKeyAction(KeyArea paramKeyArea, KeyContent paramKeyContent, PopupContent paramPopupContent, KeyState paramKeyState)
    throws KeyFactory.KeyLoaderException
  {
    if ((paramKeyContent instanceof DualKeyContent))
    {
      DualKeyContent localDualKeyContent = (DualKeyContent)paramKeyContent;
      if (((localDualKeyContent.mBottomContent instanceof TextContent)) && ((localDualKeyContent.mTopContent instanceof TextContent)))
      {
        String str = ((TextContent)localDualKeyContent.mBottomContent).getText();
        return addLongPress(paramKeyState, paramPopupContent, addMainPopup(paramKeyState, str, new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new TextAction(EnumSet.of(ActionType.CLICK), this.mInputEventModel, str, new StatsLoggerAction(this.mPreferences)))));
      }
    }
    else if ((paramKeyContent instanceof TextContent))
    {
      return addLongPress(paramKeyState, paramPopupContent, addMainPopup(paramKeyState, ((TextContent)paramKeyContent).getText(), new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new TextAction(EnumSet.of(ActionType.CLICK), this.mInputEventModel, ((TextContent)paramKeyContent).getText(), new StatsLoggerAction(this.mPreferences)))));
    }
    throw new KeyLoaderException("Invalid SymbolKey Content: " + paramKeyContent.toString());
  }
  
  private PopupContent createSymbolKeyPopupContent(TypedArray paramTypedArray, KeyArea paramKeyArea)
  {
    CharSequence localCharSequence = paramTypedArray.getText(4);
    if (localCharSequence != null) {}
    ArrayList localArrayList;
    for (String str = localCharSequence.toString();; str = "")
    {
      localArrayList = new ArrayList(Arrays.asList(str.split(" ")));
      while (localArrayList.remove("")) {}
      if (localArrayList.size() != 1) {
        break;
      }
      return getPopupFromChars(localArrayList, paramKeyArea);
    }
    if (localArrayList.size() > 1) {
      return new PopupContent.MiniKeyboardContent(MiniKeyboard.createMiniKeyboard(this.mContext, this, localArrayList, paramKeyArea, MiniKeyboard.Orientation.HORIZONTAL, this.mIsMicrophoneKeyEnabled), paramKeyArea);
    }
    return PopupContent.EMPTY_CONTENT;
  }
  
  private PopupContent createTextKeyPopupContent(TypedArray paramTypedArray, KeyArea paramKeyArea)
  {
    CharSequence localCharSequence = paramTypedArray.getText(4);
    if (localCharSequence != null) {}
    for (String str = localCharSequence.toString();; str = "")
    {
      List localList = Arrays.asList(str.split(" "));
      if (localList.size() <= 0) {
        break;
      }
      return new PopupContent.MiniKeyboardContent(MiniKeyboard.createMiniKeyboard(this.mContext, this, localList, paramKeyArea, MiniKeyboard.Orientation.VERTICAL, this.mIsMicrophoneKeyEnabled), paramKeyArea);
    }
    return PopupContent.EMPTY_CONTENT;
  }
  
  private Action createZWNJKeyAction(KeyState paramKeyState, PopupContent paramPopupContent)
  {
    int i = this.mPreferences.getLongPressTimeOut();
    ActionParams localActionParams = new ActionParams.ActionParamsBuilder().longPressTimeout(i).build();
    return new PopupAction(EnumSet.of(ActionType.LONGPRESS), EnumSet.of(ActionType.UP, ActionType.SLIDE_OUT, ActionType.CANCEL), paramKeyState, paramPopupContent, localActionParams, new BloopAction(EnumSet.of(ActionType.CLICK), this.mContext, ActionParams.EMPTY_PARAMS, new TextAction(this.mInputEventModel, KeyCodeString.ZWNJ, new StatsLoggerAction(this.mPreferences))));
  }
  
  private Action createZhuyinToneKeyAction(KeyState paramKeyState, KeyContent paramKeyContent)
  {
    String str = ((TextContent)paramKeyContent).getText();
    ArrayList localArrayList = new ArrayList(str.length());
    char[] arrayOfChar = str.toCharArray();
    int i = arrayOfChar.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(Character.toString(arrayOfChar[j]));
    }
    return new BloopAction(EnumSet.of(ActionType.CLICK), this.mContext, ActionParams.EMPTY_PARAMS, new CycleAction(this.mInputEventModel, localArrayList, EnumSet.of(ActionType.CLICK), ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences)));
  }
  
  private int getLayoutId(TypedArray paramTypedArray)
    throws KeyFactory.KeyLoaderException
  {
    TypedValue localTypedValue = paramTypedArray.peekValue(17);
    if (localTypedValue == null) {
      throw new KeyLoaderException("No layoutId defined");
    }
    if (localTypedValue.resourceId != 0) {
      return localTypedValue.resourceId;
    }
    return localTypedValue.data;
  }
  
  protected static String getLocalCurrencyGlyph()
  {
    String str;
    try
    {
      Locale localLocale = Locale.getDefault();
      str = Currency.getInstance(localLocale).getSymbol(localLocale);
      if (str.length() != 1) {
        return null;
      }
      if (65509 == str.charAt(0)) {
        return "��";
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      str = null;
    }
    return str;
  }
  
  private List<String> getPopupCharacters(List<String> paramList, CharSequence paramCharSequence, boolean paramBoolean1, boolean paramBoolean2)
  {
    Predictor localPredictor = this.mFluencyProxy.getPredictor();
    if (localPredictor == null)
    {
      LogUtil.w("KeyFactory", "Could not load accented characters: servicePredictor not ready");
      return paramList;
    }
    CharacterMap localCharacterMap = localPredictor.getCharacterMap();
    if (localCharacterMap == null)
    {
      LogUtil.e("KeyFactory", "Could not load CharacterMap");
      return paramList;
    }
    HashSet localHashSet = new HashSet(12, 0.9F);
    int i = 0;
    if (i < paramList.size())
    {
      if (paramBoolean1) {}
      for (char c = ((String)paramList.get(i)).toUpperCase(this.mLocale).charAt(0);; c = ((String)paramList.get(i)).toLowerCase(this.mLocale).charAt(0))
      {
        localHashSet.add(Character.valueOf(c));
        i++;
        break;
      }
    }
    String str1;
    if (paramBoolean1)
    {
      str1 = paramCharSequence.toString().toUpperCase(this.mLocale);
      if (!paramBoolean1) {
        break label282;
      }
    }
    ArrayList localArrayList1;
    label282:
    for (String str2 = localCharacterMap.getAccentedVariantsOf(str1, this.mUpperCasePrimaryLetters).toUpperCase(this.mLocale);; str2 = localCharacterMap.getAccentedVariantsOf(str1, this.mLowerCasePrimaryLetters).toLowerCase(this.mLocale))
    {
      localArrayList1 = new ArrayList();
      for (int j = 0; j < str2.length(); j++) {
        if (!localHashSet.contains(Character.valueOf(str2.charAt(j))))
        {
          int m = j + 1;
          localArrayList1.add(str2.substring(j, m));
        }
      }
      str1 = paramCharSequence.toString().toLowerCase(this.mLocale);
      break;
    }
    if ((paramBoolean2) && (this.mKeyboardBehaviour.getLayout().providesLatin()))
    {
      LanguagePackManager localLanguagePackManager = this.mFluencyProxy.getLanguagePackManager();
      if (localLanguagePackManager != null)
      {
        List localList = localLanguagePackManager.getExtraPunctuationCharsFromEnabledLPs();
        for (int k = 0; k < localList.size(); k++)
        {
          String str5 = (String)localList.get(k);
          if (!paramList.contains(str5)) {
            paramList.add(str5);
          }
        }
      }
    }
    ArrayList localArrayList2 = new ArrayList(paramList);
    ArrayList localArrayList3 = new ArrayList(paramList.size());
    Iterator localIterator = localArrayList2.iterator();
    if (localIterator.hasNext())
    {
      String str3 = (String)localIterator.next();
      if (paramBoolean1) {}
      for (String str4 = str3.toUpperCase(this.mLocale);; str4 = str3.toLowerCase(this.mLocale))
      {
        localArrayList3.add(str4);
        break;
      }
    }
    localArrayList3.addAll(localArrayList1);
    return localArrayList3;
  }
  
  private PopupContent getPopupFromChars(List<String> paramList, KeyArea paramKeyArea)
  {
    if (paramList.size() == 1) {
      return new PopupContent.PreviewContent((String)paramList.get(0));
    }
    if (paramList.size() > 1) {
      return new PopupContent.MiniKeyboardContent(MiniKeyboard.createMiniKeyboard(this.mContext, this, paramList, paramKeyArea, MiniKeyboard.Orientation.HORIZONTAL, this.mIsMicrophoneKeyEnabled), paramKeyArea);
    }
    return PopupContent.EMPTY_CONTENT;
  }
  
  private String getPunctuationString(KeyContent paramKeyContent)
  {
    if ((paramKeyContent instanceof DualKeyContent)) {
      paramKeyContent = ((DualKeyContent)paramKeyContent).mBottomContent;
    }
    if (!(paramKeyContent instanceof TextContent)) {
      throw new IllegalArgumentException("Cannot load PuncKey/CommaKey - must have TextContent");
    }
    return ((TextContent)paramKeyContent).getText();
  }
  
  private int parseArrowKeyCode(XmlResourceParser paramXmlResourceParser)
    throws XmlPullParserException
  {
    AttributeSet localAttributeSet = Xml.asAttributeSet(paramXmlResourceParser);
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(localAttributeSet, R.styleable.LatinKey, 2130772124, 0);
    String str = localTypedArray.getString(16);
    localTypedArray.recycle();
    if (str == null) {
      throw new XmlPullParserException("ArrowKey must have attribute: direction");
    }
    if (str.equalsIgnoreCase("left")) {
      return 21;
    }
    if (str.equalsIgnoreCase("right")) {
      return 22;
    }
    if (str.equalsIgnoreCase("up")) {
      return 19;
    }
    if (str.equalsIgnoreCase("down")) {
      return 20;
    }
    throw new XmlPullParserException("Attribute: direction must be set to left, right, up or down");
  }
  
  private List<String> stringToList(String paramString)
  {
    ArrayList localArrayList = new ArrayList(Arrays.asList(paramString.split(" ")));
    while (localArrayList.remove("")) {}
    return localArrayList;
  }
  
  private Character[] toCharacterArray(String paramString)
  {
    Character[] arrayOfCharacter;
    if (paramString == null) {
      arrayOfCharacter = new Character[0];
    }
    for (;;)
    {
      return arrayOfCharacter;
      arrayOfCharacter = new Character[paramString.length()];
      for (int i = 0; i < paramString.length(); i++) {
        arrayOfCharacter[i] = Character.valueOf(paramString.charAt(i));
      }
    }
  }
  
  public Key createArrowKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createArrowKeyAction(localKeyStateImpl, paramInt)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createChineseInputFilterKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    this.mInputEventModel.addInputFilterListener(localKeyStateImpl, this.mChineseInputFilterKeyCounter);
    KeyContent localKeyContent = createChineseInputFilterKeyContent(paramKeyFields);
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createChineseInputFilterKeyAction(localKeyStateImpl, localKeyContent));
    SimpleDrawDelegate localSimpleDrawDelegate = new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, localKeyContent, localKeyStateImpl);
    KeyTouchHandler localKeyTouchHandler = new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F);
    this.mChineseInputFilterKeyCounter = (1 + this.mChineseInputFilterKeyCounter);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, localSimpleDrawDelegate, localKeyTouchHandler);
  }
  
  public Key createCommaKey(KeyArea paramKeyArea, KeyFields paramKeyFields, Typeface paramTypeface)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent;
    if ((this.mPreferences.isVoiceEnabled()) && (this.mIsMicrophoneKeyEnabled))
    {
      localKeyContent = createKeyContent(paramKeyFields, Float.valueOf(1.0F), Float.valueOf(1.0F));
      if (paramTypeface != null)
      {
        if (!(localKeyContent instanceof DualKeyContent)) {
          break label154;
        }
        ((TextContent)((DualKeyContent)localKeyContent).mBottomContent).setTypeface(paramTypeface);
      }
    }
    for (;;)
    {
      return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createCommaKeyAction(localKeyStateImpl, getPunctuationString(localKeyContent))), paramKeyArea.getDrawBounds().width() / 2.0F));
      localKeyContent = TextContent.getDefaultBottomTextContent(paramKeyFields.getBottomLabel(), paramKeyFields.getBottomText(), this.mLocale, 1.0F);
      break;
      label154:
      if ((localKeyContent instanceof TextContent)) {
        ((TextContent)localKeyContent).setTypeface(paramTypeface);
      }
    }
  }
  
  public Key createCurrencyKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createLetterKeyAction(paramKeyArea, localKeyContent, paramPopupContent, localKeyStateImpl));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, createCaseSensitiveDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createDeleteKey(KeyArea paramKeyArea)
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, IconContent.getDefaultMainIconContent(KeyIcon.DeleteKey), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createDeleteKeyAction()), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createEmptyKey()
  {
    return createEmptyKey(this.mInputEventModel);
  }
  
  public Key createEnterKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new TextAction(this.mInputEventModel, "\n", new StatsLoggerAction(this.mPreferences))), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createIMEGoKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
  {
    int i = 1;
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    this.mInputEventModel.addBufferedInputListener(localKeyStateImpl);
    this.mKeyboardSwitcher.addOptionsListener(localKeyStateImpl);
    if ((this.mPreferences.getKeyboardLayoutStyle(this.mContext) == i) && (this.mPreferences.getUsePCLayoutStyle(false))) {
      if ((!this.mContext.getResources().getBoolean(2131492936)) || (i != 0)) {
        break label174;
      }
    }
    label174:
    for (final ResizeDualKeyContent localResizeDualKeyContent = ResizeDualKeyContent.createResizeDualKeyContent(paramKeyFields.getTopIcon(), paramKeyFields.getBottomIcon(), KeyState.StateType.OPTIONS);; localResizeDualKeyContent = ResizeDualKeyContent.createResizeDualKeyContent(EnumSet.noneOf(KeyState.OptionState.class), paramKeyFields.getTopIcon(), paramKeyFields.getBottomIcon(), KeyState.StateType.OPTIONS))
    {
      EmptyContent local1 = new EmptyContent()
      {
        private final IconContent commitBufferIcon = new IconContent(KeyIcon.EnterKey, TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.8F, false, true);
        
        public KeyContent applyKeyState(KeyState paramAnonymousKeyState)
        {
          if (paramAnonymousKeyState.hasBufferedInput()) {
            return this.commitBufferIcon.applyKeyState(paramAnonymousKeyState);
          }
          return localResizeDualKeyContent.applyKeyState(paramAnonymousKeyState);
        }
      };
      return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, local1, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createIMEGoKeyAction(localKeyStateImpl, paramInt)), paramKeyArea.getDrawBounds().width() / 2.0F));
      i = 0;
      break;
    }
  }
  
  public Key createJapaneseKey(KeyArea paramKeyArea, KeyFields paramKeyFields, List<String> paramList)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = createFlickAction(paramKeyArea, localKeyContent, localKeyStateImpl, paramList);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createKeyFromXml(Context paramContext, KeyboardLoader.Row paramRow, String paramString, float paramFloat1, float paramFloat2, XmlResourceParser paramXmlResourceParser)
    throws KeyFactory.KeyLoaderException, XmlPullParserException
  {
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.TouchTypeKeyboard);
    float f1 = KeyboardLoader.getDimensionOrFraction(localTypedArray1, 0, paramRow.parent.mDisplayRect.width(), paramRow.mDefaultKeyWidth);
    float f2 = KeyboardLoader.getDimensionOrFraction(localTypedArray1, 1, paramRow.parent.mDisplayRect.width(), paramRow.mDefaultPadRect.left);
    float f3 = KeyboardLoader.getDimensionOrFraction(localTypedArray1, 2, paramRow.parent.mDisplayRect.width(), paramRow.mDefaultPadRect.right);
    float f4 = KeyboardLoader.getDimensionOrFraction(localTypedArray1, 3, paramRow.parent.mDisplayRect.height(), paramRow.mDefaultPadRect.top);
    float f5 = KeyboardLoader.getDimensionOrFraction(localTypedArray1, 4, paramRow.parent.mDisplayRect.height(), paramRow.mDefaultPadRect.bottom);
    localTypedArray1.recycle();
    TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.LatinKey, 2130772124, 0);
    float f6 = KeyboardLoader.getDimensionOrFraction(localTypedArray2, 1, paramRow.parent.mDisplayRect.height(), paramRow.mDefaultKeyHeight);
    RectF localRectF1 = new RectF(paramFloat1, paramFloat2, f3 + (f2 + (paramFloat1 + f1)), paramFloat2 + f6);
    int i = localTypedArray2.getInt(5, 0) | paramRow.mEdgeFlags;
    RectF localRectF2 = new RectF(f2, f4, f3, f5);
    KeyArea localKeyArea = new KeyArea(localRectF1, localRectF2, i);
    KeyFields localKeyFields = new KeyFields(localTypedArray2, paramString);
    String str1 = localKeyFields.getBottomText();
    PopupContent localPopupContent;
    Key localKey;
    if ("LetterKey".equals(paramString))
    {
      if ((str1 != null) && (str1.length() > 0)) {
        addPointKeyToLayout(str1, localKeyArea.getDrawBounds());
      }
      localPopupContent = createCasedPopupContent(localTypedArray2, localKeyArea);
      localKey = createLetterKey(localKeyArea, localKeyFields, localPopupContent);
    }
    for (;;)
    {
      localTypedArray2.recycle();
      this.mIntentionalEventFilter.addEvents(localPopupContent);
      this.mPredictionFilter.addKey(localKey.getContent(), localPopupContent);
      return localKey;
      if ("SymbolKey".equals(paramString))
      {
        localPopupContent = createSymbolKeyPopupContent(localTypedArray2, localKeyArea);
        localKey = createSymbolKey(localKeyArea, localKeyFields, localPopupContent);
      }
      else if ("TextKey".equals(paramString))
      {
        localPopupContent = createTextKeyPopupContent(localTypedArray2, localKeyArea);
        localKey = createTextKey(localKeyArea, localKeyFields, localPopupContent);
      }
      else if ("CurrencyKey".equals(paramString))
      {
        if ((str1 != null) && (str1.length() > 0)) {
          addPointKeyToLayout(str1, localKeyArea.getDrawBounds());
        }
        localPopupContent = createCurrencyPopupContent(localTypedArray2, localKeyArea);
        localKey = createCurrencyKey(localKeyArea, localKeyFields, localPopupContent);
      }
      else if ("IMEGoKey".equals(paramString))
      {
        localKey = createIMEGoKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("ArrowKey".equals(paramString))
      {
        localKey = createArrowKey(localKeyArea, localKeyFields, parseArrowKeyCode(paramXmlResourceParser));
        localPopupContent = null;
      }
      else if ("PuncKey".equals(paramString))
      {
        localPopupContent = createPopupContent(localTypedArray2, localKeyArea);
        localKey = createPuncKey(localKeyArea, localKeyFields, localPopupContent);
      }
      else if ("ShiftKey".equals(paramString))
      {
        localKey = createShiftKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("SpaceKey".equals(paramString))
      {
        addLineKeyToLayout(" ", localKeyArea.getDrawBounds());
        localKey = createSpaceKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("LanguageSwitchingSpaceKey".equals(paramString))
      {
        addLineKeyToLayout(" ", localKeyArea.getDrawBounds());
        localKey = createLSSpaceKey(localKeyArea);
        localPopupContent = null;
      }
      else if ("DeleteKey".equals(paramString))
      {
        localKey = createDeleteKey(localKeyArea);
        localPopupContent = null;
      }
      else if ("CommaKey".equals(paramString))
      {
        localKey = createCommaKey(localKeyArea, localKeyFields, null);
        localPopupContent = null;
      }
      else if ("AsianCommaKey".equals(paramString))
      {
        localKey = createCommaKey(localKeyArea, localKeyFields, Typeface.SERIF);
        localPopupContent = null;
      }
      else if ("PeriodKey".equals(paramString))
      {
        localKey = createPeriodKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("Settings123Key".equals(paramString))
      {
        localKey = createSettingsKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("SwitchLayoutKey".equals(paramString))
      {
        localKey = createSwitchKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("SmileyKey".equals(paramString))
      {
        localKey = createSmileyKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("SmileySwitchKey".equals(paramString))
      {
        localKey = createSmileySwitchKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("LayoutMenuKey".equals(paramString))
      {
        localKey = createLayoutMenuKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("TabKey".equals(paramString))
      {
        localKey = createTabKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("EnterKey".equals(paramString))
      {
        localKey = createEnterKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("ZeroWidthNonJoinKey".equals(paramString))
      {
        addPointKeyToLayout("‌", localKeyArea.getDrawBounds());
        localPopupContent = createSymbolKeyPopupContent(localTypedArray2, localKeyArea);
        localKey = createZWNJKey(localKeyArea, localKeyFields, localPopupContent);
      }
      else if ("ShiftLayoutKey".equals(paramString))
      {
        localKey = createShiftLayoutKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("ReturnLetterKey".equals(paramString))
      {
        localKey = createReturnLetterKey(localKeyArea, localKeyFields, getLayoutId(localTypedArray2));
        localPopupContent = null;
      }
      else if ("ChineseInputFilterKey".equals(paramString))
      {
        localKey = createChineseInputFilterKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else if ("ZhuyinToneKey".equals(paramString))
      {
        localKey = createZhuyinToneKey(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
      else
      {
        if ("JapaneseKey".equals(paramString))
        {
          CharSequence localCharSequence = localTypedArray2.getText(4);
          if (localCharSequence != null) {}
          for (String str2 = localCharSequence.toString();; str2 = "")
          {
            localKey = createJapaneseKey(localKeyArea, localKeyFields, stringToList(str2));
            localPopupContent = null;
            break;
          }
        }
        localKey = createPlaceholder(localKeyArea, localKeyFields);
        localPopupContent = null;
      }
    }
  }
  
  public Key createLSSpaceKey(KeyArea paramKeyArea)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    if (this.mLanguageSwitchData != null)
    {
      LanguageSwitchData.LanguageSwitchEntry localLanguageSwitchEntry = this.mLanguageSwitchData.getCurrentLanguageSwitchEntry();
      PopupContent.LSSBPopupContent localLSSBPopupContent = new PopupContent.LSSBPopupContent(this.mContext, this.mLanguageSwitchData);
      return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.LSSB, paramKeyArea, new LSSBContent(this.mContext, localLanguageSwitchEntry.getShortLabel(), localLanguageSwitchEntry.getFullLabel()), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithStartlessFlowAction(paramKeyArea, createLSSpaceKeyAction(paramKeyArea, localKeyStateImpl, localLSSBPopupContent)), paramKeyArea.getDrawBounds().width() / 2.0F));
    }
    return createSpaceKey(paramKeyArea, KeyFields.getDefaultSpaceKeyFields());
  }
  
  public Key createLayoutMenuKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, new InterimMenuAction(KeyState.InterimMenu.LAYOUT, localKeyStateImpl, EnumSet.of(ActionType.CLICK), ActionParams.EMPTY_PARAMS, new StatsLoggerAction(this.mPreferences)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createLetterKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent)
    throws KeyFactory.KeyLoaderException
  {
    return createSimpleKey(paramKeyArea, paramKeyFields, paramPopupContent, KeyStyle.StyleId.BASE);
  }
  
  public Key createMiniKeyboardKey(KeyArea paramKeyArea, TextContent paramTextContent)
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.MINIKB, paramKeyArea, paramTextContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, createMiniKeyboardKeyAction(paramKeyArea, paramTextContent, localKeyStateImpl), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createPeriodKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, Float.valueOf(1.0F), Float.valueOf(1.0F)), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createPeriodKeyAction(localKeyStateImpl, paramKeyFields)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createPlaceholder(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new EmptyDelegate.FlowCompleteDelegate(this.mInputEventModel));
  }
  
  public Key createPuncKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, Float.valueOf(1.0F), Float.valueOf(1.0F));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createPuncKeyAction(paramKeyArea, localKeyStateImpl, getPunctuationString(localKeyContent), paramPopupContent)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createReturnLetterKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    SwitchLayoutAction localSwitchLayoutAction = new SwitchLayoutAction(this.mContext, this.mPreferences, this.mKeyboardSwitcher, paramInt, EnumSet.of(ActionType.UP), ActionParams.EMPTY_PARAMS, createLetterKeyAction(paramKeyArea, localKeyContent, PopupContent.EMPTY_CONTENT, localKeyStateImpl));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, createCaseSensitiveDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localSwitchLayoutAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createSettingsKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, Float.valueOf(1.0F), Float.valueOf(1.0F)), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createSettings123Action(localKeyStateImpl, paramInt)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createShiftKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, createCaseSensitiveDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, new ShiftIconContent(paramKeyFields.getBottomIcon()), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithStartlessFlowAction(paramKeyArea, createShiftKeyAction()), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createShiftLayoutKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, createSwitchKeyAction(paramInt), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createSimpleKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent, KeyStyle.StyleId paramStyleId)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createLetterKeyAction(paramKeyArea, localKeyContent, paramPopupContent, localKeyStateImpl));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, createCaseSensitiveDrawDelegate(paramStyleId, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createSmileyKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = createSmileyKeyAction(localKeyContent, localKeyStateImpl, paramInt);
    if (ProductConfiguration.isWatchBuild(this.mContext)) {
      localAction = wrapWithFlowOrSwipeAction(paramKeyArea, localAction);
    }
    KeyTouchHandler localKeyTouchHandler = new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), localKeyTouchHandler);
  }
  
  public Key createSmileySwitchKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createSmileySwitchAction(localKeyStateImpl, paramKeyFields, paramInt)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createSpaceKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createSpaceKeyAction(paramKeyFields, localKeyStateImpl)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createSwitchKey(KeyArea paramKeyArea, KeyFields paramKeyFields, int paramInt)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, Float.valueOf(0.95F), Float.valueOf(1.0F)), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, wrapWithFlowAction(paramKeyArea, createSwitchKeyAction(paramInt)), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createSymbolKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createSymbolKeyAction(paramKeyArea, localKeyContent, paramPopupContent, localKeyStateImpl));
    int i = paramKeyFields.getKeyStyle();
    if (i > 0) {}
    for (KeyStyle.StyleId localStyleId = KeySpecialStyle.getIDFromValue(i);; localStyleId = KeyStyle.StyleId.BASE) {
      return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(localStyleId, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
    }
  }
  
  public Key createTabKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.FUNCTION, paramKeyArea, createKeyContent(paramKeyFields, null, null), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, new BloopAction(EnumSet.of(ActionType.DOWN), this.mContext, ActionParams.EMPTY_PARAMS, new TextAction(this.mInputEventModel, "\t", new StatsLoggerAction(this.mPreferences))), paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createTextKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, Float.valueOf(1.0F), Float.valueOf(1.0F));
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createSymbolKeyAction(paramKeyArea, localKeyContent, paramPopupContent, localKeyStateImpl));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createZWNJKey(KeyArea paramKeyArea, KeyFields paramKeyFields, PopupContent paramPopupContent)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createZWNJKeyAction(localKeyStateImpl, paramPopupContent));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public Key createZhuyinToneKey(KeyArea paramKeyArea, KeyFields paramKeyFields)
    throws KeyFactory.KeyLoaderException
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(this.mInputEventModel);
    KeyContent localKeyContent = createKeyContent(paramKeyFields, null, null);
    Action localAction = wrapWithFlowOrSwipeAction(paramKeyArea, createZhuyinToneKeyAction(localKeyStateImpl, localKeyContent));
    return new SimpleKey(paramKeyArea, localKeyStateImpl, new SimpleDrawDelegate(KeyStyle.StyleId.BASE, paramKeyArea, localKeyContent, localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localAction, paramKeyArea.getDrawBounds().width() / 2.0F));
  }
  
  public FlowOrSwipe getFlowOrSwipe()
  {
    return this.mFlowOrSwipe;
  }
  
  public Set<String> getIntentionalEventFilter()
  {
    return this.mIntentionalEventFilter.getIntentionalEventFilterSet();
  }
  
  public KeyPressModelLayout getLayout()
  {
    return this.mLayout;
  }
  
  public Set<String> getPredictionFilter()
  {
    return this.mPredictionFilter.getPredictionFilterSet();
  }
  
  public Action wrapWithFlowAction(KeyArea paramKeyArea, Action paramAction)
  {
    switch (2.$SwitchMap$com$touchtype$keyboard$key$KeyFactory$FlowOrSwipe[this.mFlowOrSwipe.ordinal()])
    {
    default: 
      return paramAction;
    }
    return new FlowAction(this.mInputEventModel, new ActionParams.ActionParamsBuilder().flowXActivationThreshold(paramKeyArea.getDrawBounds().width()).flowYActivationThreshold(paramKeyArea.getDrawBounds().height()).build(), paramAction);
  }
  
  public Action wrapWithFlowOrSwipeAction(KeyArea paramKeyArea, Action paramAction)
  {
    switch (2.$SwitchMap$com$touchtype$keyboard$key$KeyFactory$FlowOrSwipe[this.mFlowOrSwipe.ordinal()])
    {
    default: 
      return paramAction;
    case 1: 
      return new FlowAction(this.mInputEventModel, new ActionParams.ActionParamsBuilder().flowXActivationThreshold(paramKeyArea.getDrawBounds().width()).flowYActivationThreshold(paramKeyArea.getDrawBounds().height()).build(), paramAction);
    }
    return addSwipeActions(paramKeyArea, paramAction);
  }
  
  public Action wrapWithStartlessFlowAction(KeyArea paramKeyArea, Action paramAction)
  {
    switch (2.$SwitchMap$com$touchtype$keyboard$key$KeyFactory$FlowOrSwipe[this.mFlowOrSwipe.ordinal()])
    {
    default: 
      return paramAction;
    }
    return new StartlessFlowAction(this.mInputEventModel, paramAction);
  }
  
  public static enum FlowOrSwipe
  {
    static
    {
      NEITHER = new FlowOrSwipe("NEITHER", 2);
      FlowOrSwipe[] arrayOfFlowOrSwipe = new FlowOrSwipe[3];
      arrayOfFlowOrSwipe[0] = FLOW;
      arrayOfFlowOrSwipe[1] = SWIPE;
      arrayOfFlowOrSwipe[2] = NEITHER;
      $VALUES = arrayOfFlowOrSwipe;
    }
    
    private FlowOrSwipe() {}
    
    public static FlowOrSwipe getDisabledValue(FlowOrSwipe paramFlowOrSwipe)
    {
      switch (KeyFactory.2.$SwitchMap$com$touchtype$keyboard$key$KeyFactory$FlowOrSwipe[paramFlowOrSwipe.ordinal()])
      {
      default: 
        return paramFlowOrSwipe;
      }
      return NEITHER;
    }
  }
  
  public static final class KeyCodeString
  {
    public static final String DELETE = new String(Character.toChars(8));
    public static final String ZWNJ = new String(Character.toChars(8204));
  }
  
  public static final class KeyLoaderException
    extends Exception
  {
    public KeyLoaderException() {}
    
    public KeyLoaderException(Exception paramException)
    {
      super();
    }
    
    public KeyLoaderException(String paramString)
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeyFactory
 * JD-Core Version:    0.7.0.1
 */