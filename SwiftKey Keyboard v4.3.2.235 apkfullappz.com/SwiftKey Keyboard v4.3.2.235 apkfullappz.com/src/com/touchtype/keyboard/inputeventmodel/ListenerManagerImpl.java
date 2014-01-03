package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.BufferedInputListener;
import com.touchtype.keyboard.InputFilterListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnCandidatesUpdateRequestListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.util.WeakHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public final class ListenerManagerImpl
  implements ListenerManager
{
  private final Set<BufferedInputListener> mBufferedInputListeners = new WeakHashSet();
  private final Set<OnCandidatesUpdateRequestListener> mCandidateListeners = new WeakHashSet();
  private final Set<OnFlowStateChangedListener> mFlowListeners = new WeakHashSet();
  private final Map<InputFilterListener, Integer> mInputFilterListeners = new WeakHashMap();
  private final Set<PredictionsAvailabilityListener> mPredictionsEnabledListeners = new WeakHashSet();
  private final Set<OnShiftStateChangedListener> mShiftListeners = new WeakHashSet();
  
  public void addBufferedInputListener(BufferedInputListener paramBufferedInputListener)
  {
    this.mBufferedInputListeners.add(paramBufferedInputListener);
  }
  
  public void addCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    this.mCandidateListeners.add(paramOnCandidatesUpdateRequestListener);
  }
  
  public void addFlowStateChangedListener(OnFlowStateChangedListener paramOnFlowStateChangedListener)
  {
    this.mFlowListeners.add(paramOnFlowStateChangedListener);
  }
  
  public void addInputFilterListener(InputFilterListener paramInputFilterListener, int paramInt)
  {
    this.mInputFilterListeners.put(paramInputFilterListener, Integer.valueOf(paramInt));
  }
  
  public void addPredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    this.mPredictionsEnabledListeners.add(paramPredictionsAvailabilityListener);
  }
  
  public void addShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    this.mShiftListeners.add(paramOnShiftStateChangedListener);
  }
  
  public void notifyBufferedInputListeners(boolean paramBoolean)
  {
    Iterator localIterator = this.mBufferedInputListeners.iterator();
    while (localIterator.hasNext()) {
      ((BufferedInputListener)localIterator.next()).onBufferedInputStateChanged(paramBoolean);
    }
  }
  
  public void notifyCandidateUpdateListeners()
  {
    Iterator localIterator = this.mCandidateListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnCandidatesUpdateRequestListener)localIterator.next()).handleCandidatesUpdateRequest();
    }
  }
  
  public void notifyFlowStateChangedListeners(boolean paramBoolean)
  {
    Iterator localIterator = this.mFlowListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnFlowStateChangedListener)localIterator.next()).handleFlowStateChanged(paramBoolean);
    }
  }
  
  public void notifyInputFilterListeners(List<String> paramList)
  {
    Iterator localIterator = this.mInputFilterListeners.entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      int i = ((Integer)localEntry.getValue()).intValue();
      if ((paramList != null) && (i < paramList.size())) {}
      for (String str = (String)paramList.get(i);; str = null)
      {
        ((InputFilterListener)localEntry.getKey()).onInputChanged(str);
        break;
      }
    }
  }
  
  public void notifyPredictionsEnabledListener(PredictionsAvailability paramPredictionsAvailability)
  {
    Iterator localIterator = this.mPredictionsEnabledListeners.iterator();
    while (localIterator.hasNext()) {
      ((PredictionsAvailabilityListener)localIterator.next()).onPredictionsAvailabilityUpdate(paramPredictionsAvailability);
    }
  }
  
  public void notifyShiftStateChangedListeners(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    Iterator localIterator = this.mShiftListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnShiftStateChangedListener)localIterator.next()).handleShiftStateChanged(paramShiftState);
    }
  }
  
  public void removeCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    this.mCandidateListeners.remove(paramOnCandidatesUpdateRequestListener);
  }
  
  public void removePredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    this.mPredictionsEnabledListeners.remove(paramPredictionsAvailabilityListener);
  }
  
  public void removeShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    this.mShiftListeners.remove(paramOnShiftStateChangedListener);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.ListenerManagerImpl
 * JD-Core Version:    0.7.0.1
 */