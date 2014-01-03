package com.touchtype_fluency;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Predictions
  extends AbstractList<Prediction>
{
  private List<Prediction> predictions;
  
  static
  {
    if (!Predictions.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  protected Predictions(List<Prediction> paramList)
  {
    this.predictions = paramList;
    assert (isSorted());
  }
  
  protected Predictions(Prediction[] paramArrayOfPrediction)
  {
    this.predictions = Arrays.asList(paramArrayOfPrediction);
    assert (isSorted());
  }
  
  private boolean isSorted()
  {
    int i = 1;
    for (int j = i;; j++) {
      if (j < this.predictions.size())
      {
        if (((Prediction)this.predictions.get(j - 1)).getProbability() < ((Prediction)this.predictions.get(j)).getProbability()) {
          i = 0;
        }
      }
      else {
        return i;
      }
    }
  }
  
  public Predictions best(int paramInt)
  {
    return new Predictions(this.predictions.subList(0, Math.min(paramInt, size())));
  }
  
  public Prediction get(int paramInt)
  {
    return (Prediction)this.predictions.get(paramInt);
  }
  
  public Predictions prefixMatches()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.predictions.iterator();
    while (localIterator.hasNext())
    {
      Prediction localPrediction = (Prediction)localIterator.next();
      if (localPrediction.getTags().contains("prefix")) {
        localArrayList.add(localPrediction);
      }
    }
    return new Predictions(localArrayList);
  }
  
  public Predictions sameLengthMatches()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.predictions.iterator();
    while (localIterator.hasNext())
    {
      Prediction localPrediction = (Prediction)localIterator.next();
      if (!localPrediction.getTags().contains("prefix")) {
        localArrayList.add(localPrediction);
      }
    }
    return new Predictions(localArrayList);
  }
  
  public int size()
  {
    return this.predictions.size();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.predictions.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(((Prediction)localIterator.next()).toString());
      localStringBuilder.append(" > ");
    }
    return localStringBuilder.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Predictions
 * JD-Core Version:    0.7.0.1
 */