package com.facebook.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.android.R.dimen;
import com.facebook.android.R.drawable;
import com.facebook.android.R.styleable;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.net.MalformedURLException;

public class ProfilePictureView
  extends FrameLayout
{
  private static final String BITMAP_HEIGHT_KEY = "ProfilePictureView_height";
  private static final String BITMAP_KEY = "ProfilePictureView_bitmap";
  private static final String BITMAP_WIDTH_KEY = "ProfilePictureView_width";
  public static final int CUSTOM = -1;
  private static final boolean IS_CROPPED_DEFAULT_VALUE = true;
  private static final String IS_CROPPED_KEY = "ProfilePictureView_isCropped";
  public static final int LARGE = -4;
  private static final int MIN_SIZE = 1;
  public static final int NORMAL = -3;
  private static final String PENDING_REFRESH_KEY = "ProfilePictureView_refresh";
  private static final String PRESET_SIZE_KEY = "ProfilePictureView_presetSize";
  private static final String PROFILE_ID_KEY = "ProfilePictureView_profileId";
  public static final int SMALL = -2;
  private static final String SUPER_STATE_KEY = "ProfilePictureView_superState";
  public static final String TAG = ProfilePictureView.class.getSimpleName();
  private ImageView image;
  private Bitmap imageContents;
  private boolean isCropped = true;
  private ImageRequest lastRequest;
  private OnErrorListener onErrorListener;
  private int presetSizeType = -1;
  private String profileId;
  private int queryHeight = 0;
  private int queryWidth = 0;
  
  public ProfilePictureView(Context paramContext)
  {
    super(paramContext);
    initialize(paramContext);
  }
  
  public ProfilePictureView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize(paramContext);
    parseAttributes(paramAttributeSet);
  }
  
  public ProfilePictureView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize(paramContext);
    parseAttributes(paramAttributeSet);
  }
  
  private int getPresetSizeInPixels(boolean paramBoolean)
  {
    int i;
    switch (this.presetSizeType)
    {
    default: 
      return 0;
    case -2: 
      i = R.dimen.com_facebook_profilepictureview_preset_size_small;
    }
    for (;;)
    {
      return getResources().getDimensionPixelSize(i);
      i = R.dimen.com_facebook_profilepictureview_preset_size_normal;
      continue;
      i = R.dimen.com_facebook_profilepictureview_preset_size_large;
      continue;
      if (!paramBoolean) {
        break;
      }
      i = R.dimen.com_facebook_profilepictureview_preset_size_normal;
    }
  }
  
  private void initialize(Context paramContext)
  {
    removeAllViews();
    this.image = new ImageView(paramContext);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    this.image.setLayoutParams(localLayoutParams);
    this.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    addView(this.image);
  }
  
  private void parseAttributes(AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.com_facebook_profile_picture_view);
    setPresetSize(localTypedArray.getInt(0, -1));
    this.isCropped = localTypedArray.getBoolean(1, true);
    localTypedArray.recycle();
  }
  
  private void processResponse(ImageResponse paramImageResponse)
  {
    Bitmap localBitmap;
    Exception localException;
    if (paramImageResponse.getRequest() == this.lastRequest)
    {
      this.lastRequest = null;
      localBitmap = paramImageResponse.getBitmap();
      localException = paramImageResponse.getError();
      if (localException == null) {
        break label92;
      }
      OnErrorListener localOnErrorListener = this.onErrorListener;
      if (localOnErrorListener == null) {
        break label76;
      }
      localOnErrorListener.onError(new FacebookException("Error in downloading profile picture for profileId: " + getProfileId(), localException));
    }
    label76:
    label92:
    do
    {
      do
      {
        return;
        Logger.log(LoggingBehavior.REQUESTS, 6, TAG, localException.toString());
        return;
      } while (localBitmap == null);
      setImageBitmap(localBitmap);
    } while (!paramImageResponse.isCachedRedirect());
    sendImageRequest(false);
  }
  
  private void refreshImage(boolean paramBoolean)
  {
    boolean bool = updateImageQueryParameters();
    if ((this.profileId == null) || (this.profileId.length() == 0) || ((this.queryWidth == 0) && (this.queryHeight == 0))) {
      setBlankProfilePicture();
    }
    while ((!bool) && (!paramBoolean)) {
      return;
    }
    sendImageRequest(true);
  }
  
  private void sendImageRequest(boolean paramBoolean)
  {
    try
    {
      ImageRequest localImageRequest = new ImageRequest.Builder(getContext(), ImageRequest.getProfilePictureUrl(this.profileId, this.queryWidth, this.queryHeight)).setAllowCachedRedirects(paramBoolean).setCallerTag(this).setCallback(new ImageRequest.Callback()
      {
        public void onCompleted(ImageResponse paramAnonymousImageResponse)
        {
          ProfilePictureView.this.processResponse(paramAnonymousImageResponse);
        }
      }).build();
      if (this.lastRequest != null) {
        ImageDownloader.cancelRequest(this.lastRequest);
      }
      this.lastRequest = localImageRequest;
      ImageDownloader.downloadAsync(localImageRequest);
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      Logger.log(LoggingBehavior.REQUESTS, 6, TAG, localMalformedURLException.toString());
    }
  }
  
  private void setBlankProfilePicture()
  {
    if (isCropped()) {}
    for (int i = R.drawable.com_facebook_profile_picture_blank_square;; i = R.drawable.com_facebook_profile_picture_blank_portrait)
    {
      setImageBitmap(BitmapFactory.decodeResource(getResources(), i));
      return;
    }
  }
  
  private void setImageBitmap(Bitmap paramBitmap)
  {
    if ((this.image != null) && (paramBitmap != null))
    {
      this.imageContents = paramBitmap;
      this.image.setImageBitmap(paramBitmap);
    }
  }
  
  private boolean updateImageQueryParameters()
  {
    int i = getHeight();
    int j = getWidth();
    if ((j <= 0) || (i <= 0)) {
      return false;
    }
    int k = getPresetSizeInPixels(false);
    if (k != 0)
    {
      j = k;
      i = k;
    }
    if (j <= i) {
      if (isCropped())
      {
        i = j;
        if ((j == this.queryWidth) && (i == this.queryHeight)) {
          break label102;
        }
      }
    }
    label102:
    for (boolean bool = true;; bool = false)
    {
      this.queryWidth = j;
      this.queryHeight = i;
      return bool;
      i = 0;
      break;
      if (isCropped()) {}
      for (j = i;; j = 0) {
        break;
      }
    }
  }
  
  public final OnErrorListener getOnErrorListener()
  {
    return this.onErrorListener;
  }
  
  public final int getPresetSize()
  {
    return this.presetSizeType;
  }
  
  public final String getProfileId()
  {
    return this.profileId;
  }
  
  public final boolean isCropped()
  {
    return this.isCropped;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.lastRequest = null;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    refreshImage(false);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    int i = View.MeasureSpec.getSize(paramInt2);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = 0;
    if (k != 1073741824)
    {
      int n = localLayoutParams.height;
      m = 0;
      if (n == -2)
      {
        i = getPresetSizeInPixels(true);
        paramInt2 = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        m = 1;
      }
    }
    if ((View.MeasureSpec.getMode(paramInt1) != 1073741824) && (localLayoutParams.width == -2))
    {
      j = getPresetSizeInPixels(true);
      paramInt1 = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
      m = 1;
    }
    if (m != 0)
    {
      setMeasuredDimension(j, i);
      measureChildren(paramInt1, paramInt2);
      return;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (paramParcelable.getClass() != Bundle.class) {
      super.onRestoreInstanceState(paramParcelable);
    }
    Bundle localBundle;
    do
    {
      return;
      localBundle = (Bundle)paramParcelable;
      super.onRestoreInstanceState(localBundle.getParcelable("ProfilePictureView_superState"));
      this.profileId = localBundle.getString("ProfilePictureView_profileId");
      this.presetSizeType = localBundle.getInt("ProfilePictureView_presetSize");
      this.isCropped = localBundle.getBoolean("ProfilePictureView_isCropped");
      this.queryWidth = localBundle.getInt("ProfilePictureView_width");
      this.queryHeight = localBundle.getInt("ProfilePictureView_height");
      setImageBitmap((Bitmap)localBundle.getParcelable("ProfilePictureView_bitmap"));
    } while (!localBundle.getBoolean("ProfilePictureView_refresh"));
    refreshImage(true);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Parcelable localParcelable = super.onSaveInstanceState();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("ProfilePictureView_superState", localParcelable);
    localBundle.putString("ProfilePictureView_profileId", this.profileId);
    localBundle.putInt("ProfilePictureView_presetSize", this.presetSizeType);
    localBundle.putBoolean("ProfilePictureView_isCropped", this.isCropped);
    localBundle.putParcelable("ProfilePictureView_bitmap", this.imageContents);
    localBundle.putInt("ProfilePictureView_width", this.queryWidth);
    localBundle.putInt("ProfilePictureView_height", this.queryHeight);
    if (this.lastRequest != null) {}
    for (boolean bool = true;; bool = false)
    {
      localBundle.putBoolean("ProfilePictureView_refresh", bool);
      return localBundle;
    }
  }
  
  public final void setCropped(boolean paramBoolean)
  {
    this.isCropped = paramBoolean;
    refreshImage(false);
  }
  
  public final void setOnErrorListener(OnErrorListener paramOnErrorListener)
  {
    this.onErrorListener = paramOnErrorListener;
  }
  
  public final void setPresetSize(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Must use a predefined preset size");
    }
    this.presetSizeType = paramInt;
    requestLayout();
  }
  
  public final void setProfileId(String paramString)
  {
    boolean bool1;
    if (!Utility.isNullOrEmpty(this.profileId))
    {
      boolean bool2 = this.profileId.equalsIgnoreCase(paramString);
      bool1 = false;
      if (bool2) {}
    }
    else
    {
      setBlankProfilePicture();
      bool1 = true;
    }
    this.profileId = paramString;
    refreshImage(bool1);
  }
  
  public static abstract interface OnErrorListener
  {
    public abstract void onError(FacebookException paramFacebookException);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.ProfilePictureView
 * JD-Core Version:    0.7.0.1
 */