package com.touchtype.report.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.touchtype_fluency.service.FluencyMetrics;
import com.touchtype_fluency.service.FluencyMetrics.Metrics;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

final class Performance
{
  @SerializedName("getPrediction")
  private FluencyMetrics.Metrics mGetPrediction;
  @SerializedName("loadLanguages")
  private FluencyMetrics.Metrics mLoadLanguages;
  @SerializedName("loadSession")
  private FluencyMetrics.Metrics mLoadSession;
  @SerializedName("staticModels")
  private List<Model> mStaticModels;
  @SerializedName("unloadLanguages")
  private FluencyMetrics.Metrics mUnloadLanguages;
  @SerializedName("unloadSession")
  private FluencyMetrics.Metrics mUnloadSession;
  
  public static Performance newInstance(Context paramContext, FluencyMetrics paramFluencyMetrics, LanguagePackManager paramLanguagePackManager)
  {
    Performance localPerformance = new Performance();
    FluencyMetrics.Metrics localMetrics1 = paramFluencyMetrics.getLoadSession();
    FluencyMetrics.Metrics localMetrics2 = paramFluencyMetrics.getUnloadSession();
    FluencyMetrics.Metrics localMetrics3 = paramFluencyMetrics.getLoadLanguages();
    FluencyMetrics.Metrics localMetrics4 = paramFluencyMetrics.getUnloadLanguages();
    FluencyMetrics.Metrics localMetrics5 = paramFluencyMetrics.getGetPredictions();
    if (localMetrics1.isEmpty()) {
      localMetrics1 = null;
    }
    localPerformance.mLoadSession = localMetrics1;
    if (localMetrics2.isEmpty()) {
      localMetrics2 = null;
    }
    localPerformance.mUnloadSession = localMetrics2;
    if (localMetrics3.isEmpty()) {
      localMetrics3 = null;
    }
    localPerformance.mLoadLanguages = localMetrics3;
    if (localMetrics4.isEmpty()) {
      localMetrics4 = null;
    }
    localPerformance.mUnloadLanguages = localMetrics4;
    if (localMetrics5.isEmpty()) {
      localMetrics5 = null;
    }
    localPerformance.mGetPrediction = localMetrics5;
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator = paramLanguagePackManager.getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext()) {
      localArrayList1.add(Model.newInstance((LanguagePack)localIterator.next()));
    }
    boolean bool = localArrayList1.isEmpty();
    ArrayList localArrayList2 = null;
    if (bool) {}
    for (;;)
    {
      localPerformance.mStaticModels = localArrayList2;
      return localPerformance;
      localArrayList2 = localArrayList1;
    }
  }
  
  static final class Model
  {
    @SerializedName("id")
    private String mId;
    @SerializedName("version")
    private int mVersion;
    
    public static Model newInstance(LanguagePack paramLanguagePack)
    {
      Model localModel = new Model();
      localModel.mId = paramLanguagePack.getID();
      localModel.mVersion = paramLanguagePack.getVersion();
      return localModel;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Performance
 * JD-Core Version:    0.7.0.1
 */