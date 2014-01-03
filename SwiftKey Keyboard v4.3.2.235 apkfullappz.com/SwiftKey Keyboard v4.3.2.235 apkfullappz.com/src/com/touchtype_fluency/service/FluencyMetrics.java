package com.touchtype_fluency.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class FluencyMetrics
{
  @SerializedName("get_predictions")
  private Metrics getPredictions = new Metrics();
  @SerializedName("load_languages")
  private Metrics loadLanguages = new Metrics();
  @SerializedName("load_session")
  private Metrics loadSession = new Metrics();
  @SerializedName("unload_languages")
  private Metrics unloadLanguages = new Metrics();
  @SerializedName("unload_session")
  private Metrics unloadSession = new Metrics();
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    FluencyMetrics localFluencyMetrics;
    do
    {
      return true;
      if (!(paramObject instanceof FluencyMetrics)) {
        return false;
      }
      localFluencyMetrics = (FluencyMetrics)paramObject;
    } while ((this.loadSession.equals(localFluencyMetrics.loadSession)) && (this.unloadSession.equals(localFluencyMetrics.unloadSession)) && (this.loadLanguages.equals(localFluencyMetrics.loadLanguages)) && (this.unloadLanguages.equals(localFluencyMetrics.unloadLanguages)) && (this.getPredictions.equals(localFluencyMetrics.getPredictions)));
    return false;
  }
  
  public Metrics getGetPredictions()
  {
    return this.getPredictions;
  }
  
  public Metrics getLoadLanguages()
  {
    return this.loadLanguages;
  }
  
  public Metrics getLoadSession()
  {
    return this.loadSession;
  }
  
  public Metrics getUnloadLanguages()
  {
    return this.unloadLanguages;
  }
  
  public Metrics getUnloadSession()
  {
    return this.unloadSession;
  }
  
  public int hashCode()
  {
    int i = 13 + (143 + this.loadSession.hashCode());
    int j = i + (i * 11 + this.unloadSession.hashCode());
    int k = j + (j * 11 + this.loadLanguages.hashCode());
    int m = k + (k * 11 + this.unloadLanguages.hashCode());
    return m + (m * 11 + this.getPredictions.hashCode());
  }
  
  public void reset()
  {
    this.loadSession = new Metrics();
    this.unloadSession = new Metrics();
    this.loadLanguages = new Metrics();
    this.unloadLanguages = new Metrics();
    this.getPredictions = new Metrics();
  }
  
  public String toJSON()
  {
    return new Gson().toJson(this, FluencyMetrics.class);
  }
  
  public static class Metrics
  {
    private static final int NOT_SET = -1;
    @SerializedName("average")
    private double average = 0.0D;
    @SerializedName("max")
    private long max = 0L;
    @SerializedName("min")
    private long min = 9223372036854775807L;
    @SerializedName("n")
    private int population = 0;
    private transient long startTime = -1L;
    
    protected void addSample(long paramLong)
    {
      double d = this.average * this.population + paramLong;
      int i = 1 + this.population;
      this.population = i;
      this.average = (d / i);
      this.min = Math.min(paramLong, this.min);
      this.max = Math.max(paramLong, this.max);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      Metrics localMetrics;
      do
      {
        return true;
        if (!(paramObject instanceof Metrics)) {
          return false;
        }
        localMetrics = (Metrics)paramObject;
      } while (((int)this.average == (int)localMetrics.average) && (this.min == localMetrics.min) && (this.max == localMetrics.max) && (this.population == localMetrics.population));
      return false;
    }
    
    public double getAverage()
    {
      return this.average;
    }
    
    public long getMax()
    {
      return this.max;
    }
    
    public long getMin()
    {
      return this.min;
    }
    
    public int getPopulation()
    {
      return this.population;
    }
    
    public int hashCode()
    {
      int i = (int)(11L + (143L + this.min));
      int j = (int)(i + (i * 13 + this.max));
      int k = j + (j * 13 + this.population);
      return k + (k * 13 + (int)this.average);
    }
    
    public boolean isEmpty()
    {
      return this.population == 0;
    }
    
    public void start()
    {
      this.startTime = System.currentTimeMillis();
    }
    
    public void stop()
    {
      if (this.startTime == -1L) {
        return;
      }
      addSample(System.currentTimeMillis() - this.startTime);
      this.startTime = -1L;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyMetrics
 * JD-Core Version:    0.7.0.1
 */