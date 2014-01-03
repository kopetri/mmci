package com.touchtype.keyboard;

import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.touchtype.common.chinese.predictionfilters.PinyinSpellingHelper;
import com.touchtype.common.chinese.predictionfilters.SpellingHelper;
import com.touchtype.common.chinese.predictionfilters.ZhuyinsSpellingHelper;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.Candidate.Ranking;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.InputEventModelDecorator;
import com.touchtype.keyboard.inputeventmodel.ListenerManager;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.util.LogUtil;
import com.touchtype.util.WeakHashSet;
import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.Predictions;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.ResultsFilter.VerbatimMode;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.TouchHistory;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorNotReadyException;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AsianComposingBuffer
  extends InputEventModelDecorator
{
  static final Map<Character, Character> ENCODING_5STROKE = ImmutableMap.of(Character.valueOf('1'), Character.valueOf('一'), Character.valueOf('2'), Character.valueOf('丨'), Character.valueOf('3'), Character.valueOf('丿'), Character.valueOf('4'), Character.valueOf('丶'), Character.valueOf('5'), Character.valueOf('乛'));
  static final Map<Character, Character> ENCODING_CANGJIE = ImmutableMap.builder().put(Character.valueOf('A'), Character.valueOf('日')).put(Character.valueOf('B'), Character.valueOf('月')).put(Character.valueOf('C'), Character.valueOf(37329)).put(Character.valueOf('D'), Character.valueOf('木')).put(Character.valueOf('E'), Character.valueOf('水')).put(Character.valueOf('F'), Character.valueOf('火')).put(Character.valueOf('G'), Character.valueOf('土')).put(Character.valueOf('H'), Character.valueOf('竹')).put(Character.valueOf('I'), Character.valueOf('戈')).put(Character.valueOf('J'), Character.valueOf('十')).put(Character.valueOf('K'), Character.valueOf('大')).put(Character.valueOf('L'), Character.valueOf('中')).put(Character.valueOf('M'), Character.valueOf('一')).put(Character.valueOf('N'), Character.valueOf('弓')).put(Character.valueOf('O'), Character.valueOf('人')).put(Character.valueOf('P'), Character.valueOf('心')).put(Character.valueOf('Q'), Character.valueOf('手')).put(Character.valueOf('R'), Character.valueOf('口')).put(Character.valueOf('S'), Character.valueOf('尸')).put(Character.valueOf('T'), Character.valueOf('廿')).put(Character.valueOf('U'), Character.valueOf('山')).put(Character.valueOf('V'), Character.valueOf('女')).put(Character.valueOf('W'), Character.valueOf('田')).put(Character.valueOf('X'), Character.valueOf(38627)).put(Character.valueOf('Y'), Character.valueOf('卜')).build();
  private static final String TAG = AsianComposingBuffer.class.getSimpleName();
  private final WeakHashSet<UpdatedCandidatesListener> mCandidatesListeners;
  private final Map<Character, Character> mEncoding;
  private final FluencyServiceProxy mFluencyService;
  private boolean mHadInput;
  private String mInputBackup = null;
  private final StringBuilder mInputBuffer = new StringBuilder();
  private InputEventModel mInputEventModel;
  private List<String> mInputFilters = null;
  private final LayoutData.LayoutMap mLayout;
  private final Learner mLearner;
  private final ListenerManager mListenerManager;
  private String mReadableText = null;
  private final ResultsFilter.PredictionSearchType mSearchType;
  private final SpellingHelper mSpellingHelper;
  private Candidate mTopCandidate = null;
  
  public AsianComposingBuffer(FluencyServiceProxy paramFluencyServiceProxy, ListenerManager paramListenerManager, Learner paramLearner, KeyboardBehaviour paramKeyboardBehaviour)
  {
    this.mFluencyService = paramFluencyServiceProxy;
    this.mLayout = paramKeyboardBehaviour.getLayout();
    this.mEncoding = getEncoding(this.mLayout);
    this.mSearchType = paramKeyboardBehaviour.getSearchType();
    this.mSpellingHelper = getSpellingHelper(this.mLayout, paramFluencyServiceProxy);
    this.mListenerManager = paramListenerManager;
    this.mLearner = paramLearner;
    this.mCandidatesListeners = new WeakHashSet();
  }
  
  private void addSegment(StringBuilder paramStringBuilder, String paramString, int paramInt1, int paramInt2)
  {
    int i = paramStringBuilder.length();
    if ((i > 0) && (paramStringBuilder.charAt(i - 1) != '\'') && ((paramString.length() <= paramInt1) || (paramString.charAt(paramInt1) != '\''))) {
      paramStringBuilder.append(" ");
    }
    paramStringBuilder.append(paramString.substring(paramInt1, paramInt2));
  }
  
  private void commitText(String paramString)
  {
    this.mInputEventModel.onSoftKey(new KeyEvent(SystemClock.uptimeMillis(), paramString, 0, 2));
  }
  
  private int countAcceptedCharacters()
  {
    int i = this.mInputBuffer.length();
    for (int j = 0; j < i; j++) {
      if (isAsianInputCharacter(this.mInputBuffer.charAt(j))) {
        return j;
      }
    }
    return i;
  }
  
  private String createReadableText(String paramString1, String paramString2, Candidate paramCandidate)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[this.mLayout.ordinal()])
    {
    default: 
      if (paramCandidate != null) {
        break;
      }
    }
    StringBuilder localStringBuilder;
    int i;
    int j;
    for (Integer[] arrayOfInteger1 = null;; arrayOfInteger1 = paramCandidate.getPrediction().getTermBreaks())
    {
      localStringBuilder = new StringBuilder(paramString1);
      i = paramString2.length();
      j = 0;
      if (arrayOfInteger1 == null) {
        break label174;
      }
      Integer[] arrayOfInteger2 = arrayOfInteger1;
      int k = arrayOfInteger1.length;
      for (int m = 0; m < k; m++)
      {
        int n = arrayOfInteger2[m].intValue();
        if (n >= i) {
          break;
        }
        addSegment(localStringBuilder, paramString2, j, n);
        j = n;
      }
      paramString2 = getSpelling(paramCandidate, paramString2);
      break;
      paramString2 = translate(paramString2);
      break;
    }
    label174:
    if (j < i) {
      addSegment(localStringBuilder, paramString2, j, i);
    }
    return localStringBuilder.toString();
  }
  
  private static Map<Character, Character> getEncoding(LayoutData.LayoutMap paramLayoutMap)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return null;
    case 1: 
    case 2: 
    case 3: 
      return ENCODING_5STROKE;
    }
    return ENCODING_CANGJIE;
  }
  
  private List<String> getInputFilters(Predictions paramPredictions)
  {
    if ((this.mLayout == LayoutData.LayoutMap.PINYIN12) || (this.mLayout == LayoutData.LayoutMap.JYUTPING12) || (this.mLayout == LayoutData.LayoutMap.ZHUYIN12)) {
      return this.mSpellingHelper.getPredictionsFilters(paramPredictions, 0);
    }
    return null;
  }
  
  private static String getInputTag(Candidate paramCandidate)
  {
    Iterator localIterator = paramCandidate.getPrediction().getTags().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("input:")) {
        return str.substring(6);
      }
    }
    return "";
  }
  
  private static String getSpelling(Candidate paramCandidate, String paramString)
  {
    if (paramCandidate != null)
    {
      String str1 = getInputTag(paramCandidate);
      int i = str1.length();
      int j = paramString.length();
      StringBuilder localStringBuilder = new StringBuilder().append(str1);
      if (j > i) {}
      for (String str2 = paramString.substring(i);; str2 = "")
      {
        String str3 = str2;
        if (str3.length() > j) {
          str3 = str3.substring(0, j);
        }
        return str3;
      }
    }
    return paramString;
  }
  
  private static SpellingHelper getSpellingHelper(LayoutData.LayoutMap paramLayoutMap, FluencyServiceProxy paramFluencyServiceProxy)
  {
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[paramLayoutMap.ordinal()])
    {
    default: 
      return null;
    case 6: 
    case 7: 
      return PinyinSpellingHelper.create();
    }
    return ZhuyinsSpellingHelper.create(paramFluencyServiceProxy.getPredictor().getCharacterMap().getLayout());
  }
  
  private boolean isAsianInputCharacter(char paramChar)
  {
    boolean bool = true;
    switch (1.$SwitchMap$com$touchtype_fluency$service$languagepacks$layouts$LayoutData$LayoutMap[this.mLayout.ordinal()])
    {
    default: 
      bool = false;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    return bool;
                  } while ((('a' <= paramChar) && (paramChar <= 'z')) || (paramChar == '\''));
                  return false;
                } while ((('2' <= paramChar) && (paramChar <= '9')) || (('a' <= paramChar) && (paramChar <= 'z')) || (paramChar == '\''));
                return false;
              } while ((('ㄅ' <= paramChar) && (paramChar <= 'ㄭ')) || (('ㆠ' <= paramChar) && (paramChar <= 'ㆺ')) || (paramChar == 'ˊ') || (paramChar == 'ˋ') || (paramChar == 'ˇ') || (paramChar == '˙') || (paramChar == '\''));
              return false;
            } while ((('0' <= paramChar) && (paramChar <= '9')) || (('ㄅ' <= paramChar) && (paramChar <= 'ㄭ')) || (('ㆠ' <= paramChar) && (paramChar <= 'ㆺ')) || (paramChar == '#') || (paramChar == '\''));
            return false;
          } while ((('1' <= paramChar) && (paramChar <= '5')) || (paramChar == '*') || (paramChar == '\''));
          return false;
        } while ((('A' <= paramChar) && (paramChar <= 'Z')) || (paramChar == '\''));
        return false;
      } while ((('a' <= paramChar) && (paramChar <= 'z')) || (paramChar == '\''));
      return false;
    } while ((('a' <= paramChar) && (paramChar <= 'z')) || (paramChar == '-'));
    return false;
  }
  
  private void notifyCandidatesListeners(CandidateContainer paramCandidateContainer)
  {
    Iterator localIterator = this.mCandidatesListeners.iterator();
    while (localIterator.hasNext()) {
      ((UpdatedCandidatesListener)localIterator.next()).onCandidatesUpdated(paramCandidateContainer);
    }
  }
  
  private void replaceInputWithBackup()
  {
    if (this.mInputBackup != null)
    {
      int i = countAcceptedCharacters();
      this.mInputBuffer.replace(i, i + this.mInputBackup.length(), this.mInputBackup);
      this.mInputBackup = null;
    }
  }
  
  private boolean replaceInputWithCandidate(Candidate paramCandidate)
  {
    Integer[] arrayOfInteger = paramCandidate.getPrediction().getTermBreaks();
    int i = arrayOfInteger.length;
    if (i > 0) {}
    for (int j = arrayOfInteger[(i - 1)].intValue();; j = getInputTag(paramCandidate).length())
    {
      int k = countAcceptedCharacters();
      int m = j + k;
      int n = this.mInputBuffer.length();
      int i1 = Math.min(m, n);
      this.mInputBuffer.replace(k, i1, paramCandidate.toString());
      if (i1 != n) {
        break;
      }
      return true;
    }
    return false;
  }
  
  private String translate(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      Character localCharacter = (Character)this.mEncoding.get(Character.valueOf(Character.toUpperCase(arrayOfChar[i])));
      if (localCharacter != null) {
        arrayOfChar[i] = localCharacter.charValue();
      }
    }
    return new String(arrayOfChar);
  }
  
  public void addBufferedInputListener(BufferedInputListener paramBufferedInputListener)
  {
    super.addBufferedInputListener(paramBufferedInputListener);
    this.mListenerManager.addBufferedInputListener(paramBufferedInputListener);
  }
  
  public void addCandidatesListener(UpdatedCandidatesListener paramUpdatedCandidatesListener)
  {
    this.mCandidatesListeners.add(paramUpdatedCandidatesListener);
  }
  
  public void addFlowStateChangedListener(OnFlowStateChangedListener paramOnFlowStateChangedListener)
  {
    super.addFlowStateChangedListener(paramOnFlowStateChangedListener);
    this.mListenerManager.addFlowStateChangedListener(paramOnFlowStateChangedListener);
  }
  
  public void addInputFilterListener(InputFilterListener paramInputFilterListener, int paramInt)
  {
    super.addInputFilterListener(paramInputFilterListener, paramInt);
    this.mListenerManager.addInputFilterListener(paramInputFilterListener, paramInt);
  }
  
  public void addPredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    super.addPredictionsEnabledListener(paramPredictionsAvailabilityListener);
    this.mListenerManager.addPredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void addShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    super.addShiftStateChangedListener(paramOnShiftStateChangedListener);
    this.mListenerManager.addShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void commitBuffer()
  {
    if (this.mReadableText.length() > 0)
    {
      commitText(this.mReadableText.replaceAll(" ", ""));
      reset();
      refreshPredictions();
    }
  }
  
  public void deleteInput()
  {
    if (hasInput())
    {
      replaceInputWithBackup();
      this.mInputBuffer.setLength(-1 + this.mInputBuffer.length());
      refreshPredictions();
    }
  }
  
  protected InputEventModel getDecorated()
  {
    return this.mInputEventModel;
  }
  
  public char getLastInput()
  {
    if (hasInput()) {
      return this.mInputBuffer.charAt(-1 + this.mInputBuffer.length());
    }
    return '\000';
  }
  
  public void handleDeleteLastWord(int paramInt, EnumSet<ActionType> paramEnumSet)
  {
    if (paramInt == 0) {
      this.mHadInput = hasInput();
    }
    if (hasInput()) {
      deleteInput();
    }
    while (this.mHadInput) {
      return;
    }
    super.handleDeleteLastWord(paramInt, paramEnumSet);
  }
  
  public void handleInput(char paramChar1, char paramChar2)
  {
    if (paramChar1 == 0)
    {
      handleInput(paramChar2, false);
      return;
    }
    int i = -1 + this.mInputBuffer.length();
    if ((i >= 0) && (this.mInputBuffer.charAt(i) == paramChar1)) {
      this.mInputBuffer.setCharAt(i, paramChar2);
    }
    for (;;)
    {
      refreshPredictions();
      return;
      this.mInputBuffer.append(paramChar2);
    }
  }
  
  public void handleInput(char paramChar, boolean paramBoolean)
  {
    if ((!paramBoolean) && (isAsianInputCharacter(paramChar)))
    {
      replaceInputWithBackup();
      this.mInputBuffer.append(paramChar);
    }
    for (;;)
    {
      refreshPredictions();
      return;
      if (hasInput())
      {
        Candidate localCandidate = this.mTopCandidate;
        boolean bool = false;
        if (localCandidate != null)
        {
          bool = replaceInputWithCandidate(this.mTopCandidate);
          this.mLearner.learnPredictionMapping(this.mTopCandidate, this.mSearchType);
          this.mLearner.learnPredictionMappings();
        }
        if (!bool)
        {
          String str = this.mReadableText.replaceAll(" ", "");
          int i = str.length();
          for (int j = this.mInputBuffer.length(); (i > 0) && (j > 0) && (isAsianInputCharacter(this.mInputBuffer.charAt(j - 1))); j--)
          {
            this.mInputBuffer.setCharAt(j - 1, str.charAt(i - 1));
            i--;
          }
        }
        if (paramChar != ' ') {
          this.mInputBuffer.append(paramChar);
        }
        commitText(this.mInputBuffer.toString());
        reset();
      }
      else
      {
        commitText(Character.toString(paramChar));
      }
    }
  }
  
  public void handleInputFilterInput(int paramInt)
  {
    if (paramInt > this.mInputFilters.size())
    {
      Locale localLocale = Locale.ENGLISH;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = Integer.valueOf(this.mInputFilters.size());
      String str2 = String.format(localLocale, "Requested filter index %d, but have only %d", arrayOfObject);
      Log.w(TAG, str2);
      return;
    }
    replaceInputWithBackup();
    String str1 = (String)this.mInputFilters.get(paramInt);
    int i = str1.length();
    int j = countAcceptedCharacters();
    int k = Math.min(j + i, this.mInputBuffer.length());
    this.mInputBackup = this.mInputBuffer.substring(j, k);
    this.mInputBuffer.replace(j, k, str1);
    refreshPredictions();
  }
  
  public boolean hasInput()
  {
    return this.mInputBuffer.length() > 0;
  }
  
  public void onCycle(List<String> paramList)
  {
    int i = paramList.indexOf(Character.toString(getLastInput()));
    if (i == -1)
    {
      handleInput('\000', ((String)paramList.get(0)).charAt(0));
      return;
    }
    handleInput(((String)paramList.get(i)).charAt(0), ((String)paramList.get((i + 1) % paramList.size())).charAt(0));
  }
  
  public void onEnterKey()
  {
    if (hasInput())
    {
      commitBuffer();
      return;
    }
    this.mInputEventModel.onEnterKey();
  }
  
  public void onInputFilterInput(int paramInt, String paramString)
  {
    if (hasInput())
    {
      handleInputFilterInput(paramInt);
      return;
    }
    handleInput(paramString.charAt(0), false);
  }
  
  public void onPredictionSelected(Candidate paramCandidate)
  {
    int i = 0;
    if (this.mInputBuffer.length() == 0) {
      commitText(paramCandidate.toString());
    }
    for (;;)
    {
      this.mLearner.learnPredictionMapping(paramCandidate, this.mSearchType);
      if (i != 0) {
        this.mLearner.learnPredictionMappings();
      }
      refreshPredictions();
      return;
      boolean bool = replaceInputWithCandidate(paramCandidate);
      i = 0;
      if (bool)
      {
        commitText(this.mInputBuffer.toString());
        this.mInputBuffer.setLength(0);
        i = 1;
      }
      this.mInputBackup = null;
    }
  }
  
  public void onSoftKey(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 8)
    {
      if (hasInput())
      {
        deleteInput();
        return;
      }
      super.onSoftKey(paramKeyEvent);
      return;
    }
    if ((paramKeyEvent instanceof ExtendedKeyEvent))
    {
      ExtendedKeyEvent localExtendedKeyEvent = (ExtendedKeyEvent)paramKeyEvent;
      handleInput((char)localExtendedKeyEvent.getKeyCode(), localExtendedKeyEvent.shouldBypassBuffers());
      return;
    }
    handleInput((char)paramKeyEvent.getKeyCode(), false);
  }
  
  public void refreshPredictions()
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      Predictions localPredictions;
      String str;
      int j;
      int k;
      try
      {
        i = countAcceptedCharacters();
        Predictor localPredictor = this.mFluencyService.getPredictor();
        localPredictions = null;
        if (localPredictor == null) {
          break label479;
        }
        TouchTypeExtractedText localTouchTypeExtractedText = this.mInputEventModel.getTouchTypeExtractedText(false);
        if (localTouchTypeExtractedText == null) {
          break label490;
        }
        str = localTouchTypeExtractedText.getText();
        StringBuilder localStringBuilder = new StringBuilder(str);
        localStringBuilder.append(this.mInputBuffer, 0, i);
        TouchHistory localTouchHistory = new TouchHistory(this.mInputBuffer.substring(i));
        Sequence localSequence = localPredictor.split(localStringBuilder.toString());
        if (localTouchHistory.size() > 6) {
          break label498;
        }
        j = 90;
        ResultsFilter localResultsFilter = new ResultsFilter(j, ResultsFilter.CapitalizationHint.LOWER_CASE, ResultsFilter.VerbatimMode.DISABLED, this.mSearchType);
        localPredictions = localPredictor.getPredictions(localSequence, localTouchHistory, localResultsFilter);
      }
      catch (PredictorNotReadyException localPredictorNotReadyException)
      {
        int i;
        Candidate localCandidate;
        HashMap localHashMap3;
        LogUtil.w(TAG, "Predictor not ready yet");
        this.mTopCandidate = null;
        HashMap localHashMap2;
        return;
      }
      finally
      {
        HashMap localHashMap1 = new HashMap(1);
        localHashMap1.put("composing_popup_text", this.mReadableText);
        notifyCandidatesListeners(new CandidateContainer(localArrayList, false, CandidatesUpdateRequestType.ASIAN, null, localHashMap1));
        this.mListenerManager.notifyBufferedInputListeners(hasInput());
      }
      if (k < localPredictions.size())
      {
        localArrayList.add(Candidate.fromFluency(localPredictions.get(k), Candidate.Ranking.EMPTY, ""));
        k++;
      }
      else
      {
        if (localArrayList.size() > 0)
        {
          localCandidate = (Candidate)localArrayList.get(0);
          this.mTopCandidate = localCandidate;
          this.mReadableText = createReadableText(this.mInputBuffer.substring(0, i), this.mInputBuffer.substring(i), this.mTopCandidate);
          this.mInputFilters = getInputFilters(localPredictions);
          this.mListenerManager.notifyInputFilterListeners(this.mInputFilters);
          localHashMap3 = new HashMap(1);
          localHashMap3.put("composing_popup_text", this.mReadableText);
          notifyCandidatesListeners(new CandidateContainer(localArrayList, false, CandidatesUpdateRequestType.ASIAN, null, localHashMap3));
          this.mListenerManager.notifyBufferedInputListeners(hasInput());
          return;
        }
        localCandidate = null;
        continue;
        label479:
        if (localPredictions != null)
        {
          k = 0;
          continue;
          label490:
          str = "";
          continue;
          label498:
          j = 30;
        }
      }
    }
  }
  
  public void reset()
  {
    this.mInputBuffer.setLength(0);
    this.mInputBackup = null;
  }
  
  protected void setDecorated(InputEventModel paramInputEventModel)
  {
    this.mInputEventModel = paramInputEventModel;
    refreshPredictions();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.AsianComposingBuffer
 * JD-Core Version:    0.7.0.1
 */