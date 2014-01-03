package com.touchtype.animation;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Pair;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import java.util.List;

public final class FrameSequenceAnimator
{
  private AnimationListParser.ParsedAnimationList animation;
  private boolean animationHasFrames;
  private int currentFrame = 0;
  private int currentFrameDuration = 0;
  private final Runnable firstFrameRunnable = new Runnable()
  {
    public void run()
    {
      if (!FrameSequenceAnimator.this.showFirstFrame()) {
        FrameSequenceAnimator.access$102(FrameSequenceAnimator.this, false);
      }
    }
  };
  private Handler handler;
  private WeakReference<ImageView> imageViewRef;
  private long lastFrameStartTime;
  private final Runnable nextFrameRunnable = new Runnable()
  {
    public void run()
    {
      if (FrameSequenceAnimator.this.running)
      {
        ImageView localImageView = (ImageView)FrameSequenceAnimator.this.imageViewRef.get();
        long l2;
        if ((localImageView != null) && (localImageView.isShown()))
        {
          long l1 = SystemClock.uptimeMillis();
          if (l1 - FrameSequenceAnimator.this.lastFrameStartTime < Math.max(-20 + FrameSequenceAnimator.this.currentFrameDuration, 0)) {
            break label312;
          }
          FrameSequenceAnimator.access$302(FrameSequenceAnimator.this, FrameSequenceAnimator.this.lastFrameStartTime + ((Integer)((Pair)FrameSequenceAnimator.this.animation.frames.get(FrameSequenceAnimator.this.currentFrame)).second).intValue());
          l2 = FrameSequenceAnimator.this.lastFrameStartTime - l1;
          if (FrameSequenceAnimator.this.currentFrame >= -1 + FrameSequenceAnimator.this.animation.frames.size())
          {
            FrameSequenceAnimator.access$502(FrameSequenceAnimator.this, 0);
            if (!FrameSequenceAnimator.this.animation.oneShot) {
              break label196;
            }
            FrameSequenceAnimator.access$102(FrameSequenceAnimator.this, false);
          }
        }
        else
        {
          return;
        }
        FrameSequenceAnimator.access$508(FrameSequenceAnimator.this);
        label196:
        FrameSequenceAnimator.access$402(FrameSequenceAnimator.this, ((Integer)((Pair)FrameSequenceAnimator.this.animation.frames.get(FrameSequenceAnimator.this.currentFrame)).second).intValue());
        long l3 = Math.max(l2 + FrameSequenceAnimator.this.currentFrameDuration - 20L, 0L);
        FrameSequenceAnimator.this.handler.postDelayed(this, l3);
        localImageView.setBackgroundResource(((Integer)((Pair)FrameSequenceAnimator.this.animation.frames.get(FrameSequenceAnimator.this.currentFrame)).first).intValue());
        return;
        label312:
        FrameSequenceAnimator.this.handler.postDelayed(this, 20L);
        return;
      }
      FrameSequenceAnimator.access$102(FrameSequenceAnimator.this, false);
    }
  };
  private volatile boolean running = false;
  
  public FrameSequenceAnimator(Context paramContext, ImageView paramImageView, int paramInt, boolean paramBoolean)
  {
    this.imageViewRef = new WeakReference(paramImageView);
    this.handler = new Handler(Looper.getMainLooper());
    loadAnimationFrames(paramContext, paramInt);
    if (paramBoolean) {
      showFirstFrame();
    }
  }
  
  private void loadAnimationFrames(Context paramContext, int paramInt)
  {
    this.animation = new AnimationListParser(paramContext, paramInt).parse();
    if (this.animation.frames.size() > 0) {}
    for (boolean bool = true;; bool = false)
    {
      this.animationHasFrames = bool;
      return;
    }
  }
  
  private boolean showFirstFrame()
  {
    boolean bool1 = this.animationHasFrames;
    boolean bool2 = false;
    if (bool1)
    {
      ImageView localImageView = (ImageView)this.imageViewRef.get();
      bool2 = false;
      if (localImageView != null)
      {
        localImageView.setBackgroundResource(((Integer)((Pair)this.animation.frames.get(0)).first).intValue());
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public long getDuration()
  {
    return this.animation.duration;
  }
  
  public void start()
  {
    if ((!this.running) && (this.animationHasFrames))
    {
      this.running = true;
      this.lastFrameStartTime = SystemClock.uptimeMillis();
      this.currentFrameDuration = ((Integer)((Pair)this.animation.frames.get(0)).second).intValue();
      this.handler.post(this.firstFrameRunnable);
      this.handler.postDelayed(this.nextFrameRunnable, Math.max(-20 + this.currentFrameDuration, 0));
    }
  }
  
  public void stop()
  {
    this.running = false;
    this.currentFrame = 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.animation.FrameSequenceAnimator
 * JD-Core Version:    0.7.0.1
 */