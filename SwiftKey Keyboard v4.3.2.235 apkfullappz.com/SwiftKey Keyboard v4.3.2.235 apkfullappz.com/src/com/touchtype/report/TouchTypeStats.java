package com.touchtype.report;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touchtype.common.iris.IrisDataSenderService;
import com.touchtype.common.iris.SendInterval;
import com.touchtype.keyboard.candidates.Candidate.Ranking;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.report.json.KeyboardUseReport;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;

public class TouchTypeStats
{
  private static final String TAG = TouchTypeStats.class.getSimpleName();
  private boolean isKeyboardShown;
  private final StringToCountersMap mCandidateRankingMap;
  private float mDistanceFlowed = 0.0F;
  private final HashMap<String, Float> mFloatMap;
  private final HashMap<String, Integer> mIntegerMap;
  private final StringToCountersMap mKeyCountMap;
  private final Map<String, LanguageModelMetrics> mLMetrics;
  private final StringToStringMap mLanguageLayoutChangeMap;
  private Point mLastFlowedPoint = null;
  private final HashMap<String, Long> mLongMap;
  private final SwiftKeyPreferences mPreferences;
  private long mTimeEndedTyping;
  private long mTimeKeyboardClosed;
  private long mTimeKeyboardOpened;
  private long mTimeStartedTyping;
  private Integer[] mVoicePredictions;
  private final float xDPI;
  private final float yDPI;
  
  public TouchTypeStats(SwiftKeyPreferences paramSwiftKeyPreferences, Context paramContext)
  {
    this.mPreferences = paramSwiftKeyPreferences;
    this.mIntegerMap = new HashMap();
    this.mLongMap = new HashMap();
    this.mFloatMap = new HashMap();
    this.mLMetrics = new HashMap();
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    this.xDPI = localDisplayMetrics.xdpi;
    this.yDPI = localDisplayMetrics.ydpi;
    this.mKeyCountMap = new StringToCountersMap("stats_key_counts");
    this.mCandidateRankingMap = new StringToCountersMap("stats_candidate_rankings");
    this.mLanguageLayoutChangeMap = new StringToStringMap("stats_language_layout_changes");
  }
  
  private void calculateDistanceFlowed(Point paramPoint)
  {
    try
    {
      float f1 = (paramPoint.getX() - this.mLastFlowedPoint.getX()) / this.xDPI;
      float f2 = (paramPoint.getY() - this.mLastFlowedPoint.getY()) / this.yDPI;
      this.mDistanceFlowed = (FloatMath.sqrt(f1 * f1 + f2 * f2) + this.mDistanceFlowed);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private String createKeyForLanguageMetrics(String paramString, int paramInt)
  {
    return "stats_per_language_model_" + paramString + String.valueOf(paramInt);
  }
  
  private String extractLastTwoPathsFromSource(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    String[] arrayOfString;
    int i;
    do
    {
      return paramString;
      arrayOfString = paramString.split(File.separator);
      i = arrayOfString.length;
    } while (i <= 2);
    return arrayOfString[(i - 2)] + File.separator + arrayOfString[(i - 1)];
  }
  
  private boolean isDefaultLayout(LanguagePack paramLanguagePack, String paramString)
  {
    String str = paramLanguagePack.getDefaultLayoutName();
    return (paramString == null) || (paramString.equals(str));
  }
  
  private Integer[] parseVoicePredictionsPreferenceForReading(String paramString)
  {
    Integer[] arrayOfInteger = new Integer[3];
    String[] arrayOfString = paramString.split(",");
    int i = 0;
    for (;;)
    {
      if (i < arrayOfString.length) {
        try
        {
          arrayOfInteger[i] = Integer.valueOf(Integer.parseInt(arrayOfString[i]));
          i++;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          for (;;)
          {
            arrayOfInteger[i] = Integer.valueOf(0);
          }
        }
      }
    }
    return arrayOfInteger;
  }
  
  private String parseVoicePredictionsStatisticForSaving()
  {
    if (this.mVoicePredictions == null) {
      this.mVoicePredictions = parseVoicePredictionsPreferenceForReading(this.mPreferences.getString("stats_voice_recognition_predictions", "0,0,0"));
    }
    return Joiner.on(",").join(this.mVoicePredictions);
  }
  
  private void saveLanguageMetrics()
  {
    synchronized (this.mLMetrics)
    {
      Iterator localIterator = this.mLMetrics.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        LanguageModelMetrics localLanguageModelMetrics = (LanguageModelMetrics)localEntry.getValue();
        if (!Strings.isNullOrEmpty(localLanguageModelMetrics.getSource())) {
          this.mPreferences.putString((String)localEntry.getKey(), localLanguageModelMetrics.toJSON());
        }
      }
    }
  }
  
  private void sendKeyboardUseStatsReport(Context paramContext)
  {
    long l1 = this.mTimeKeyboardOpened / 1000L;
    long l2 = this.mTimeKeyboardClosed / 1000L;
    if (l2 - l1 < 1L) {
      return;
    }
    try
    {
      KeyboardUseReport localKeyboardUseReport = new KeyboardUseReport(l1, l2);
      String str = new Gson().toJson(localKeyboardUseReport, KeyboardUseReport.class);
      paramContext.startService(IrisDataSenderService.sendDataIntent(paramContext, paramContext.getString(2131296334), str, SendInterval.DAILY));
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      LogUtil.e(TAG, "Error creating KeyboardUseReport: " + localIllegalArgumentException.getMessage());
    }
  }
  
  public void addToCurrentFlowingWordHistory(Point paramPoint)
  {
    try
    {
      if (this.mLastFlowedPoint != null) {
        calculateDistanceFlowed(paramPoint);
      }
      this.mLastFlowedPoint = paramPoint;
      return;
    }
    finally {}
  }
  
  /* Error */
  public void decrementStatistic(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual 328	com/touchtype/report/TouchTypeStats:getStatisticInt	(Ljava/lang/String;)I
    //   7: istore_3
    //   8: aload_0
    //   9: getfield 63	com/touchtype/report/TouchTypeStats:mIntegerMap	Ljava/util/HashMap;
    //   12: astore 4
    //   14: iload_3
    //   15: ifle +27 -> 42
    //   18: iconst_m1
    //   19: aload_0
    //   20: aload_1
    //   21: invokevirtual 328	com/touchtype/report/TouchTypeStats:getStatisticInt	(Ljava/lang/String;)I
    //   24: iadd
    //   25: istore 5
    //   27: aload 4
    //   29: aload_1
    //   30: iload 5
    //   32: invokestatic 195	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   35: invokevirtual 332	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   38: pop
    //   39: aload_0
    //   40: monitorexit
    //   41: return
    //   42: iconst_0
    //   43: istore 5
    //   45: goto -18 -> 27
    //   48: astore_2
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_2
    //   52: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	this	TouchTypeStats
    //   0	53	1	paramString	String
    //   48	4	2	localObject	Object
    //   7	8	3	i	int
    //   12	16	4	localHashMap	HashMap
    //   25	19	5	j	int
    // Exception table:
    //   from	to	target	type
    //   2	14	48	finally
    //   18	27	48	finally
    //   27	39	48	finally
  }
  
  public List<LanguageModelMetrics> getAllLanguageModelMetrics()
  {
    saveLanguageMetrics();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mPreferences.getAll().keySet().iterator();
    for (;;)
    {
      if (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (!str.startsWith("stats_per_language_model_")) {
          continue;
        }
        try
        {
          localLanguageModelMetrics = (LanguageModelMetrics)new Gson().fromJson(this.mPreferences.getString(str, "{}"), LanguageModelMetrics.class);
          if (localLanguageModelMetrics != null) {
            localArrayList.add(localLanguageModelMetrics);
          }
        }
        catch (JsonSyntaxException localJsonSyntaxException)
        {
          for (;;)
          {
            LogUtil.e(TAG, localJsonSyntaxException.getMessage(), localJsonSyntaxException);
            LanguageModelMetrics localLanguageModelMetrics = null;
          }
        }
      }
    }
    return localArrayList;
  }
  
  public String getCandidateRankingsString()
  {
    try
    {
      String str = this.mCandidateRankingMap.getPreferenceString();
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public float getCurrentWordDistanceFlowed()
  {
    try
    {
      float f1 = this.mDistanceFlowed;
      float f2 = f1 * 0.0254F;
      return f2;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getKeyStrokesSaved()
  {
    try
    {
      int i = getStatisticInt("stats_entered_characters");
      int j = getStatisticInt("stats_key_strokes");
      int k = i - j;
      return k;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String getLanguageLayoutChangesString()
  {
    return this.mLanguageLayoutChangeMap.getPreferenceString();
  }
  
  public LanguageModelMetrics getLanguageMetricsPerSourceAndVersion(String paramString, int paramInt)
  {
    String str1 = extractLastTwoPathsFromSource(paramString);
    String str2 = createKeyForLanguageMetrics(str1, paramInt);
    synchronized (this.mLMetrics)
    {
      LanguageModelMetrics localLanguageModelMetrics2;
      if (!this.mLMetrics.containsKey(str2))
      {
        boolean bool = this.mPreferences.contains(str2);
        localLanguageModelMetrics2 = null;
        if (!bool) {}
      }
      try
      {
        localLanguageModelMetrics2 = (LanguageModelMetrics)new Gson().fromJson(this.mPreferences.getString(str2, "{}"), LanguageModelMetrics.class);
        Map localMap2 = this.mLMetrics;
        if (localLanguageModelMetrics2 == null) {
          localLanguageModelMetrics2 = new LanguageModelMetrics(str1, paramInt);
        }
        localMap2.put(str2, localLanguageModelMetrics2);
        LanguageModelMetrics localLanguageModelMetrics1 = (LanguageModelMetrics)this.mLMetrics.get(str2);
        return localLanguageModelMetrics1;
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        for (;;)
        {
          LogUtil.e(TAG, localJsonSyntaxException.getMessage(), localJsonSyntaxException);
          localLanguageModelMetrics2 = null;
        }
      }
    }
  }
  
  /* Error */
  public float getStatisticFloat(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 67	com/touchtype/report/TouchTypeStats:mFloatMap	Ljava/util/HashMap;
    //   6: aload_1
    //   7: invokevirtual 401	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
    //   10: ifne +35 -> 45
    //   13: aload_0
    //   14: getfield 58	com/touchtype/report/TouchTypeStats:mPreferences	Lcom/touchtype/preferences/SwiftKeyPreferences;
    //   17: aload_1
    //   18: fconst_0
    //   19: invokeinterface 405 3 0
    //   24: fstore 4
    //   26: aload_0
    //   27: getfield 67	com/touchtype/report/TouchTypeStats:mFloatMap	Ljava/util/HashMap;
    //   30: aload_1
    //   31: fload 4
    //   33: invokestatic 410	java/lang/Float:valueOf	(F)Ljava/lang/Float;
    //   36: invokevirtual 332	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   39: pop
    //   40: aload_0
    //   41: monitorexit
    //   42: fload 4
    //   44: freturn
    //   45: aload_0
    //   46: getfield 67	com/touchtype/report/TouchTypeStats:mFloatMap	Ljava/util/HashMap;
    //   49: aload_1
    //   50: invokevirtual 411	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   53: checkcast 407	java/lang/Float
    //   56: invokevirtual 414	java/lang/Float:floatValue	()F
    //   59: fstore_3
    //   60: fload_3
    //   61: fstore 4
    //   63: goto -23 -> 40
    //   66: astore_2
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_2
    //   70: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	71	0	this	TouchTypeStats
    //   0	71	1	paramString	String
    //   66	4	2	localObject	Object
    //   59	2	3	f1	float
    //   24	38	4	f2	float
    // Exception table:
    //   from	to	target	type
    //   2	40	66	finally
    //   45	60	66	finally
  }
  
  /* Error */
  public int getStatisticInt(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 63	com/touchtype/report/TouchTypeStats:mIntegerMap	Ljava/util/HashMap;
    //   6: aload_1
    //   7: invokevirtual 401	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
    //   10: ifne +35 -> 45
    //   13: aload_0
    //   14: getfield 58	com/touchtype/report/TouchTypeStats:mPreferences	Lcom/touchtype/preferences/SwiftKeyPreferences;
    //   17: aload_1
    //   18: iconst_0
    //   19: invokeinterface 418 3 0
    //   24: istore 4
    //   26: aload_0
    //   27: getfield 63	com/touchtype/report/TouchTypeStats:mIntegerMap	Ljava/util/HashMap;
    //   30: aload_1
    //   31: iload 4
    //   33: invokestatic 195	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   36: invokevirtual 332	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   39: pop
    //   40: aload_0
    //   41: monitorexit
    //   42: iload 4
    //   44: ireturn
    //   45: aload_0
    //   46: getfield 63	com/touchtype/report/TouchTypeStats:mIntegerMap	Ljava/util/HashMap;
    //   49: aload_1
    //   50: invokevirtual 411	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   53: checkcast 186	java/lang/Integer
    //   56: invokevirtual 421	java/lang/Integer:intValue	()I
    //   59: istore_3
    //   60: iload_3
    //   61: istore 4
    //   63: goto -23 -> 40
    //   66: astore_2
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_2
    //   70: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	71	0	this	TouchTypeStats
    //   0	71	1	paramString	String
    //   66	4	2	localObject	Object
    //   59	2	3	i	int
    //   24	38	4	j	int
    // Exception table:
    //   from	to	target	type
    //   2	40	66	finally
    //   45	60	66	finally
  }
  
  /* Error */
  public long getStatisticLong(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 65	com/touchtype/report/TouchTypeStats:mLongMap	Ljava/util/HashMap;
    //   6: aload_1
    //   7: invokevirtual 401	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
    //   10: ifne +35 -> 45
    //   13: aload_0
    //   14: getfield 58	com/touchtype/report/TouchTypeStats:mPreferences	Lcom/touchtype/preferences/SwiftKeyPreferences;
    //   17: aload_1
    //   18: lconst_0
    //   19: invokeinterface 427 4 0
    //   24: lstore 5
    //   26: aload_0
    //   27: getfield 65	com/touchtype/report/TouchTypeStats:mLongMap	Ljava/util/HashMap;
    //   30: aload_1
    //   31: lload 5
    //   33: invokestatic 432	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   36: invokevirtual 332	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   39: pop
    //   40: aload_0
    //   41: monitorexit
    //   42: lload 5
    //   44: lreturn
    //   45: aload_0
    //   46: getfield 65	com/touchtype/report/TouchTypeStats:mLongMap	Ljava/util/HashMap;
    //   49: aload_1
    //   50: invokevirtual 411	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   53: checkcast 429	java/lang/Long
    //   56: invokevirtual 436	java/lang/Long:longValue	()J
    //   59: lstore_3
    //   60: lload_3
    //   61: lstore 5
    //   63: goto -23 -> 40
    //   66: astore_2
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_2
    //   70: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	71	0	this	TouchTypeStats
    //   0	71	1	paramString	String
    //   66	4	2	localObject	Object
    //   59	2	3	l1	long
    //   24	38	5	l2	long
    // Exception table:
    //   from	to	target	type
    //   2	40	66	finally
    //   45	60	66	finally
  }
  
  public String getVoicePredictionsStatistic()
  {
    try
    {
      String str = parseVoicePredictionsStatisticForSaving();
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void incrementKeyCount(String paramString)
  {
    try
    {
      this.mKeyCountMap.increment(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void incrementStatistic(String paramString)
  {
    try
    {
      getStatisticInt(paramString);
      this.mIntegerMap.put(paramString, Integer.valueOf(1 + getStatisticInt(paramString)));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void incrementStatisticBy(String paramString, float paramFloat)
  {
    if (paramFloat <= 0.0F) {}
    for (;;)
    {
      return;
      try
      {
        getStatisticFloat(paramString);
        this.mFloatMap.put(paramString, Float.valueOf(paramFloat + getStatisticFloat(paramString)));
      }
      finally {}
    }
  }
  
  public void incrementStatisticBy(String paramString, int paramInt)
  {
    if (paramInt <= 0) {}
    for (;;)
    {
      return;
      try
      {
        getStatisticInt(paramString);
        this.mIntegerMap.put(paramString, Integer.valueOf(paramInt + getStatisticInt(paramString)));
      }
      finally {}
    }
  }
  
  public void incrementStatisticBy(String paramString, long paramLong)
  {
    if (paramLong <= 0L) {}
    for (;;)
    {
      return;
      try
      {
        getStatisticLong(paramString);
        this.mLongMap.put(paramString, Long.valueOf(paramLong + getStatisticLong(paramString)));
      }
      finally {}
    }
  }
  
  public void incrementVoicePredictions(int paramInt)
  {
    try
    {
      if (this.mVoicePredictions == null) {
        this.mVoicePredictions = parseVoicePredictionsPreferenceForReading(this.mPreferences.getString("stats_voice_recognition_predictions", "0,0,0"));
      }
      if ((paramInt < this.mVoicePredictions.length) && (paramInt >= 0))
      {
        Integer[] arrayOfInteger = this.mVoicePredictions;
        arrayOfInteger[paramInt] = Integer.valueOf(1 + arrayOfInteger[paramInt].intValue());
      }
      return;
    }
    finally {}
  }
  
  public void keyStroked()
  {
    try
    {
      incrementStatistic("stats_key_strokes");
      if (this.mTimeStartedTyping == 0L) {
        this.mTimeStartedTyping = System.currentTimeMillis();
      }
      long l = System.currentTimeMillis();
      if ((this.mTimeEndedTyping != 0L) && (l - this.mTimeEndedTyping > 5000L))
      {
        incrementStatisticBy("stats_time_spent_typing", this.mTimeEndedTyping - this.mTimeStartedTyping);
        this.mTimeStartedTyping = l;
      }
      this.mTimeEndedTyping = l;
      return;
    }
    finally {}
  }
  
  public void keyboardClosed(Context paramContext)
  {
    try
    {
      this.isKeyboardShown = false;
      long l = System.currentTimeMillis();
      if (l - this.mTimeKeyboardOpened < 86400000L)
      {
        this.mTimeKeyboardClosed = l;
        incrementStatisticBy("stats_time_keyboard_opened", this.mTimeKeyboardClosed - this.mTimeKeyboardOpened);
        sendKeyboardUseStatsReport(paramContext);
      }
      this.mTimeKeyboardOpened = 0L;
      this.mTimeKeyboardClosed = 0L;
      incrementStatisticBy("stats_time_spent_typing", this.mTimeEndedTyping - this.mTimeStartedTyping);
      this.mTimeStartedTyping = 0L;
      this.mTimeEndedTyping = 0L;
      return;
    }
    finally {}
  }
  
  public void keyboardOpened()
  {
    try
    {
      this.isKeyboardShown = true;
      if (this.mTimeKeyboardOpened == 0L) {
        this.mTimeKeyboardOpened = System.currentTimeMillis();
      }
      incrementStatistic("stats_keyboard_opens");
      return;
    }
    finally {}
  }
  
  public void predictionsOpened()
  {
    try
    {
      this.isKeyboardShown = true;
      if (this.mTimeKeyboardOpened == 0L) {
        this.mTimeKeyboardOpened = System.currentTimeMillis();
      }
      incrementStatistic("stats_predictions_opens");
      return;
    }
    finally {}
  }
  
  public void recordCandidateRanking(Candidate.Ranking paramRanking)
  {
    try
    {
      this.mCandidateRankingMap.increment(paramRanking.toString());
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public void recordLanguageLayoutChange(LanguagePack paramLanguagePack, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual 496	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:getID	()Ljava/lang/String;
    //   6: astore 4
    //   8: aload_0
    //   9: aload_1
    //   10: aload_2
    //   11: invokespecial 498	com/touchtype/report/TouchTypeStats:isDefaultLayout	(Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack;Ljava/lang/String;)Z
    //   14: ifeq +16 -> 30
    //   17: aload_0
    //   18: getfield 113	com/touchtype/report/TouchTypeStats:mLanguageLayoutChangeMap	Lcom/touchtype/report/TouchTypeStats$StringToStringMap;
    //   21: aload 4
    //   23: invokevirtual 501	com/touchtype/report/TouchTypeStats$StringToStringMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   26: pop
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: aload_0
    //   31: getfield 113	com/touchtype/report/TouchTypeStats:mLanguageLayoutChangeMap	Lcom/touchtype/report/TouchTypeStats$StringToStringMap;
    //   34: aload 4
    //   36: aload_2
    //   37: invokevirtual 504	com/touchtype/report/TouchTypeStats$StringToStringMap:put	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   40: goto -13 -> 27
    //   43: astore_3
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_3
    //   47: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	TouchTypeStats
    //   0	48	1	paramLanguagePack	LanguagePack
    //   0	48	2	paramString	String
    //   43	4	3	localObject	Object
    //   6	29	4	str	String
    // Exception table:
    //   from	to	target	type
    //   2	27	43	finally
    //   30	40	43	finally
  }
  
  public void resetCurrentFlowingWordHistory()
  {
    try
    {
      this.mLastFlowedPoint = null;
      this.mDistanceFlowed = 0.0F;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void resetLanguageModelMetrics()
  {
    Iterator localIterator = this.mPreferences.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("stats_per_language_model_")) {
        this.mPreferences.removeKey(str);
      }
    }
    synchronized (this.mLMetrics)
    {
      this.mLMetrics.clear();
      return;
    }
  }
  
  public void saveStatistics()
  {
    new SaveStatisticsTask(null).execute(new Void[0]);
  }
  
  Boolean saveStatisticsInternal()
  {
    boolean bool1 = true;
    for (;;)
    {
      SharedPreferences.Editor localEditor;
      try
      {
        if ((this.mIntegerMap == null) || (this.mLongMap == null) || (this.mFloatMap == null))
        {
          String str = TAG;
          Object[] arrayOfObject = new Object[3];
          if (this.mIntegerMap != null) {
            break label400;
          }
          bool2 = bool1;
          arrayOfObject[0] = Boolean.valueOf(bool2);
          if (this.mLongMap != null) {
            break label406;
          }
          bool3 = bool1;
          arrayOfObject[1] = Boolean.valueOf(bool3);
          if (this.mFloatMap != null) {
            break label412;
          }
          arrayOfObject[2] = Boolean.valueOf(bool1);
          LogUtil.e(str, String.format("Skipping statistics save: mIntegerMap is null (%1$s) and mLongMap is null (%2$s) and mFloatMap is null (%3$s)", arrayOfObject));
          Boolean localBoolean1 = Boolean.valueOf(false);
          return localBoolean1;
        }
        localEditor = this.mPreferences.edit();
        Iterator localIterator1 = this.mIntegerMap.entrySet().iterator();
        if (localIterator1.hasNext())
        {
          Map.Entry localEntry3 = (Map.Entry)localIterator1.next();
          localEditor.putInt((String)localEntry3.getKey(), ((Integer)localEntry3.getValue()).intValue());
          continue;
        }
        localIterator2 = this.mLongMap.entrySet().iterator();
      }
      finally {}
      Iterator localIterator2;
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
        localEditor.putLong((String)localEntry2.getKey(), ((Long)localEntry2.getValue()).longValue());
      }
      Iterator localIterator3 = this.mFloatMap.entrySet().iterator();
      while (localIterator3.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)localIterator3.next();
        localEditor.putFloat((String)localEntry1.getKey(), ((Float)localEntry1.getValue()).floatValue());
      }
      if (this.mVoicePredictions != null) {
        localEditor.putString("stats_voice_recognition_predictions", parseVoicePredictionsStatisticForSaving());
      }
      this.mKeyCountMap.save();
      this.mCandidateRankingMap.save();
      this.mLanguageLayoutChangeMap.save();
      saveLanguageMetrics();
      Boolean localBoolean2 = Boolean.valueOf(localEditor.commit());
      return localBoolean2;
      label400:
      boolean bool2 = false;
      continue;
      label406:
      boolean bool3 = false;
      continue;
      label412:
      bool1 = false;
    }
  }
  
  /* Error */
  public void updateOrientationStatistic(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 474	com/touchtype/report/TouchTypeStats:isKeyboardShown	Z
    //   6: ifeq +15 -> 21
    //   9: iload_1
    //   10: iconst_1
    //   11: if_icmpne +13 -> 24
    //   14: aload_0
    //   15: ldc_w 569
    //   18: invokevirtual 456	com/touchtype/report/TouchTypeStats:incrementStatistic	(Ljava/lang/String;)V
    //   21: aload_0
    //   22: monitorexit
    //   23: return
    //   24: iload_1
    //   25: iconst_2
    //   26: if_icmpne -5 -> 21
    //   29: aload_0
    //   30: ldc_w 571
    //   33: invokevirtual 456	com/touchtype/report/TouchTypeStats:incrementStatistic	(Ljava/lang/String;)V
    //   36: goto -15 -> 21
    //   39: astore_2
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_2
    //   43: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	this	TouchTypeStats
    //   0	44	1	paramInt	int
    //   39	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	9	39	finally
    //   14	21	39	finally
    //   29	36	39	finally
  }
  
  private abstract class AbstractStatsMap<K, V>
  {
    private Map<K, V> map;
    private final String preferenceKey;
    
    public AbstractStatsMap(String paramString)
    {
      this.preferenceKey = paramString;
    }
    
    private Map<K, V> fromJSONString()
    {
      localObject = new TreeMap();
      String str1 = getPreferenceString();
      try
      {
        JSONObject localJSONObject = new JSONObject(str1);
        Iterator localIterator = localJSONObject.keys();
        while (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          ((Map)localObject).put(keyFromString(str2), valueFromJSON(localJSONObject, str2));
        }
        return localObject;
      }
      catch (JSONException localJSONException)
      {
        LogUtil.e(TouchTypeStats.TAG, "Could not restore key counts from stats, starting afresh");
        localObject = new LinkedHashMap();
      }
    }
    
    private void lazyLoadMap()
    {
      if (this.map == null) {
        this.map = fromJSONString();
      }
    }
    
    public V get(K paramK)
    {
      try
      {
        lazyLoadMap();
        Object localObject2 = this.map.get(paramK);
        return localObject2;
      }
      finally
      {
        localObject1 = finally;
        throw localObject1;
      }
    }
    
    public String getPreferenceString()
    {
      try
      {
        String str = TouchTypeStats.this.mPreferences.getString(this.preferenceKey, "{}");
        return str;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    protected abstract K keyFromString(String paramString);
    
    public void put(K paramK, V paramV)
    {
      try
      {
        lazyLoadMap();
        this.map.put(paramK, paramV);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public V remove(K paramK)
    {
      try
      {
        lazyLoadMap();
        Object localObject2 = this.map.remove(paramK);
        return localObject2;
      }
      finally
      {
        localObject1 = finally;
        throw localObject1;
      }
    }
    
    public void save()
    {
      if (this.map != null) {
        TouchTypeStats.this.mPreferences.putString(this.preferenceKey, new JSONObject(this.map).toString());
      }
    }
    
    protected abstract V valueFromJSON(JSONObject paramJSONObject, String paramString)
      throws JSONException;
  }
  
  private class SaveStatisticsTask
    extends AsyncTask<Void, Integer, Boolean>
  {
    private final String TAG = SaveStatisticsTask.class.getSimpleName();
    
    private SaveStatisticsTask() {}
    
    protected Boolean doInBackground(Void... paramVarArgs)
    {
      return TouchTypeStats.this.saveStatisticsInternal();
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      if (!paramBoolean.booleanValue()) {
        LogUtil.w(this.TAG, "Failed to save statistics");
      }
    }
  }
  
  private final class StringToCountersMap
    extends TouchTypeStats.AbstractStatsMap<String, Integer>
  {
    public StringToCountersMap(String paramString)
    {
      super(paramString);
    }
    
    /* Error */
    public void increment(String paramString)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: aload_1
      //   4: invokevirtual 19	com/touchtype/report/TouchTypeStats$StringToCountersMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   7: checkcast 21	java/lang/Integer
      //   10: astore_3
      //   11: aload_3
      //   12: ifnull +24 -> 36
      //   15: aload_3
      //   16: invokevirtual 25	java/lang/Integer:intValue	()I
      //   19: istore 4
      //   21: aload_0
      //   22: aload_1
      //   23: iload 4
      //   25: iconst_1
      //   26: iadd
      //   27: invokestatic 29	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   30: invokevirtual 33	com/touchtype/report/TouchTypeStats$StringToCountersMap:put	(Ljava/lang/Object;Ljava/lang/Object;)V
      //   33: aload_0
      //   34: monitorexit
      //   35: return
      //   36: iconst_0
      //   37: istore 4
      //   39: goto -18 -> 21
      //   42: astore_2
      //   43: aload_0
      //   44: monitorexit
      //   45: aload_2
      //   46: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	47	0	this	StringToCountersMap
      //   0	47	1	paramString	String
      //   42	4	2	localObject	Object
      //   10	6	3	localInteger	Integer
      //   19	19	4	i	int
      // Exception table:
      //   from	to	target	type
      //   2	11	42	finally
      //   15	21	42	finally
      //   21	33	42	finally
    }
    
    protected String keyFromString(String paramString)
    {
      return paramString;
    }
    
    protected Integer valueFromJSON(JSONObject paramJSONObject, String paramString)
      throws JSONException
    {
      return Integer.valueOf(paramJSONObject.getInt(paramString));
    }
  }
  
  private final class StringToStringMap
    extends TouchTypeStats.AbstractStatsMap<String, String>
  {
    public StringToStringMap(String paramString)
    {
      super(paramString);
    }
    
    protected String keyFromString(String paramString)
    {
      return paramString;
    }
    
    protected String valueFromJSON(JSONObject paramJSONObject, String paramString)
      throws JSONException
    {
      return paramJSONObject.getString(paramString);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.TouchTypeStats
 * JD-Core Version:    0.7.0.1
 */