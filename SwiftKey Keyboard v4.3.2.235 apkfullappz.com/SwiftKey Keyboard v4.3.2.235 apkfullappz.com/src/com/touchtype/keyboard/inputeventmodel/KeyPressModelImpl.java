package com.touchtype.keyboard.inputeventmodel;

import android.util.Log;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.FileNotWritableException;
import com.touchtype_fluency.KeyShape;
import com.touchtype_fluency.service.FluencyServiceProxyI;
import com.touchtype_fluency.service.Predictor;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class KeyPressModelImpl
  implements KeyPressModel
{
  private static final String TAG = KeyPressModelImpl.class.getSimpleName();
  private final FluencyServiceProxyI mFluencyProxy;
  private KeyPressModelSettings mSettings;
  
  public KeyPressModelImpl(FluencyServiceProxyI paramFluencyServiceProxyI)
  {
    this.mFluencyProxy = paramFluencyServiceProxyI;
  }
  
  public static boolean canUseTouchLocations(char paramChar)
  {
    return Character.isLetter(paramChar);
  }
  
  private void changeIntentionalEventFilter(final Set<String> paramSet)
  {
    if (this.mFluencyProxy != null) {
      this.mFluencyProxy.runWhenConnected(new Runnable()
      {
        public void run()
        {
          Predictor localPredictor = KeyPressModelImpl.this.mFluencyProxy.getPredictor();
          if (localPredictor == null)
          {
            LogUtil.e(KeyPressModelImpl.TAG, "Predictor is null");
            return;
          }
          try
          {
            localPredictor.setIntentionalEvents(paramSet);
            return;
          }
          catch (IllegalStateException localIllegalStateException)
          {
            LogUtil.w(KeyPressModelImpl.TAG, "Unable to change IntentionalEventFilter: " + localIllegalStateException.getMessage());
          }
        }
      });
    }
  }
  
  private void changeKeyPressModel(KeyPressModelLayout paramKeyPressModelLayout)
  {
    final KeyPressModelSettings localKeyPressModelSettings = new KeyPressModelSettings(paramKeyPressModelLayout);
    if ((this.mSettings == null) || (!this.mSettings.equals(localKeyPressModelSettings)))
    {
      if (this.mFluencyProxy == null) {
        Log.e(TAG, "FluencyServiceProxy is null");
      }
    }
    else {
      return;
    }
    this.mFluencyProxy.runWhenConnected(new Runnable()
    {
      public void run()
      {
        Predictor localPredictor = KeyPressModelImpl.this.mFluencyProxy.getPredictor();
        if (localPredictor == null)
        {
          Log.e(KeyPressModelImpl.TAG, "Predictor is null");
          return;
        }
        com.touchtype_fluency.KeyPressModel localKeyPressModel = localPredictor.getKeyPressModel();
        if (localKeyPressModel == null)
        {
          Log.w(KeyPressModelImpl.TAG, "Failed to obtain a KeyPressModel, not loading a new one");
          return;
        }
        KeyPressModelImpl.this.saveModel();
        KeyPressModelImpl.access$302(KeyPressModelImpl.this, localKeyPressModelSettings);
        try
        {
          localKeyPressModel.loadFile(KeyPressModelImpl.this.mSettings.getFileName());
          return;
        }
        catch (IOException localIOException)
        {
          Log.w(KeyPressModelImpl.TAG, "Model couldn't be loaded, creating a new one: " + localIOException.getMessage());
          localKeyPressModel.set(KeyPressModelImpl.this.mSettings.getRawLayout());
          KeyPressModelImpl.this.saveModel();
        }
      }
    });
  }
  
  private void changePredictorFilter(final Set<String> paramSet)
  {
    if (this.mFluencyProxy != null) {
      this.mFluencyProxy.runWhenConnected(new Runnable()
      {
        public void run()
        {
          Predictor localPredictor = KeyPressModelImpl.this.mFluencyProxy.getPredictor();
          if (localPredictor == null)
          {
            LogUtil.e(KeyPressModelImpl.TAG, "Predictor is null");
            return;
          }
          try
          {
            localPredictor.setLayoutKeys(paramSet);
            return;
          }
          catch (IllegalStateException localIllegalStateException)
          {
            LogUtil.w(KeyPressModelImpl.TAG, "Unable to change PredictorFilter: " + localIllegalStateException.getMessage());
          }
        }
      });
    }
  }
  
  private void saveModel()
  {
    Predictor localPredictor = this.mFluencyProxy.getPredictor();
    if (localPredictor == null)
    {
      LogUtil.e(TAG, "Predictor is null");
      return;
    }
    com.touchtype_fluency.KeyPressModel localKeyPressModel = localPredictor.getKeyPressModel();
    if (localKeyPressModel == null)
    {
      LogUtil.w(TAG, "Save impossible - could not obtain a KeyPressModel");
      return;
    }
    if (this.mSettings == null)
    {
      LogUtil.w(TAG, "Save attempted before settings initialized.");
      return;
    }
    try
    {
      localKeyPressModel.addTag("keyboard_width", this.mSettings.getKeyboardWidth());
      localKeyPressModel.addTag("keyboard_height", this.mSettings.getKeyboardHeigh());
      localKeyPressModel.saveFile(this.mSettings.getFileName());
      return;
    }
    catch (FileNotWritableException localFileNotWritableException)
    {
      LogUtil.e(TAG, "Couldn't write new model: " + localFileNotWritableException.toString());
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      LogUtil.w(TAG, "Attempted to save before layout configured.");
    }
  }
  
  public Integer getModelId()
  {
    if (this.mSettings != null) {}
    for (int i = this.mSettings.getId();; i = -1) {
      return Integer.valueOf(i);
    }
  }
  
  public void onKeyboardHidden()
  {
    Predictor localPredictor;
    if ((this.mFluencyProxy != null) && (this.mFluencyProxy.isReady()))
    {
      saveModel();
      localPredictor = this.mFluencyProxy.getPredictor();
      if (localPredictor == null) {
        LogUtil.e(TAG, "Predictor is null");
      }
    }
    else
    {
      return;
    }
    try
    {
      localPredictor.clearLayoutKeys();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      LogUtil.w(TAG, "Unable to clear PredictorFilter: " + localIllegalStateException.getMessage());
    }
  }
  
  public void onKeyboardVisible(Set<String> paramSet1, Set<String> paramSet2)
  {
    changePredictorFilter(paramSet1);
    changeIntentionalEventFilter(paramSet2);
  }
  
  public void updateKeyPressModel(KeyPressModelLayout paramKeyPressModelLayout)
  {
    changeKeyPressModel(paramKeyPressModelLayout);
  }
  
  private final class KeyPressModelSettings
  {
    private final String mFileName;
    private final int mId;
    private final KeyPressModelLayout mLayout;
    
    public KeyPressModelSettings(KeyPressModelLayout paramKeyPressModelLayout)
    {
      this.mLayout = paramKeyPressModelLayout;
      this.mId = generateHash();
      this.mFileName = generateName(this.mId);
    }
    
    private int generateHash()
    {
      int i = 0;
      Iterator localIterator = this.mLayout.getRawLayout().entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        i = 271 * (271 * (271 * (i * 271 + ((KeyShape)localEntry.getKey()).hashCode()) + Arrays.hashCode((Object[])localEntry.getValue())) + Float.floatToIntBits(((KeyShape)localEntry.getKey()).getInitialScaleMultiplier())) + Float.floatToIntBits(((KeyShape)localEntry.getKey()).getFeatureThresholdMultiplier());
      }
      return i;
    }
    
    private String generateName(int paramInt)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      return String.format("model-%x.im", arrayOfObject);
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof KeyPressModelSettings;
      boolean bool2 = false;
      if (bool1)
      {
        KeyPressModelSettings localKeyPressModelSettings = (KeyPressModelSettings)paramObject;
        int i = getId();
        int j = localKeyPressModelSettings.getId();
        bool2 = false;
        if (i == j) {
          bool2 = true;
        }
      }
      return bool2;
    }
    
    public String getFileName()
    {
      return new File(KeyPressModelImpl.this.mFluencyProxy.getFolder(), this.mFileName).getAbsolutePath();
    }
    
    public int getId()
    {
      return this.mId;
    }
    
    public int getKeyboardHeigh()
    {
      return this.mLayout.getHeight();
    }
    
    public int getKeyboardWidth()
    {
      return this.mLayout.getWidth();
    }
    
    public Map<KeyShape, Character[]> getRawLayout()
    {
      return this.mLayout.getRawLayout();
    }
    
    public String toString()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.mFileName;
      arrayOfObject[1] = Integer.valueOf(this.mLayout.size());
      return String.format("KeyPressModelSettings: %s (%d keys).", arrayOfObject);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.KeyPressModelImpl
 * JD-Core Version:    0.7.0.1
 */