package com.touchtype.media;

import android.content.Context;
import android.content.res.Resources;
import android.media.SoundPool;
import android.util.SparseIntArray;

public final class SoundPlayer
{
  private static SoundPlayer instance = null;
  private final Context mContext;
  private final int maxVolumeLevel;
  private final SoundPool soundPool;
  private final SparseIntArray soundPoolMap;
  
  private SoundPlayer(Context paramContext)
  {
    this.mContext = paramContext;
    this.soundPool = new SoundPool(3, 1, 100);
    this.soundPoolMap = new SparseIntArray();
    this.maxVolumeLevel = paramContext.getResources().getInteger(2131558420);
    initSounds();
  }
  
  public static SoundPlayer getInstance(Context paramContext)
  {
    try
    {
      if (instance == null) {
        instance = new SoundPlayer(paramContext);
      }
      SoundPlayer localSoundPlayer = instance;
      return localSoundPlayer;
    }
    finally {}
  }
  
  private void initSounds()
  {
    this.soundPoolMap.put(0, this.soundPool.load(this.mContext, 2131099653, 1));
    this.soundPoolMap.put(32, this.soundPool.load(this.mContext, 2131099652, 1));
    this.soundPoolMap.put(-5, this.soundPool.load(this.mContext, 2131099651, 1));
  }
  
  private float normaliseVolumeLevelToVolume(int paramInt)
  {
    return (float)Math.pow(0.8899999856948853D, 4 * (this.maxVolumeLevel - paramInt));
  }
  
  private void playSound(int paramInt, float paramFloat)
  {
    int i = this.soundPoolMap.get(paramInt, this.soundPoolMap.get(0, -1));
    if (i == -1) {
      return;
    }
    this.soundPool.play(i, paramFloat, paramFloat, 1, 0, 1.0F);
  }
  
  public void playSound(int paramInt1, int paramInt2)
  {
    playSound(paramInt1, normaliseVolumeLevelToVolume(paramInt2));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.media.SoundPlayer
 * JD-Core Version:    0.7.0.1
 */