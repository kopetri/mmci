package com.facebook.widget;

import android.content.Context;
import android.net.Uri.Builder;
import com.facebook.internal.Validate;
import java.net.MalformedURLException;
import java.net.URL;

class ImageRequest
{
  private static final String HEIGHT_PARAM = "height";
  private static final String MIGRATION_PARAM = "migration_overrides";
  private static final String MIGRATION_VALUE = "{october_2012:true}";
  private static final String PROFILEPIC_URL_FORMAT = "https://graph.facebook.com/%s/picture";
  static final int UNSPECIFIED_DIMENSION = 0;
  private static final String WIDTH_PARAM = "width";
  private boolean allowCachedRedirects;
  private Callback callback;
  private Object callerTag;
  private Context context;
  private URL imageUrl;
  
  private ImageRequest(Builder paramBuilder)
  {
    this.context = paramBuilder.context;
    this.imageUrl = paramBuilder.imageUrl;
    this.callback = paramBuilder.callback;
    this.allowCachedRedirects = paramBuilder.allowCachedRedirects;
    if (paramBuilder.callerTag == null) {}
    for (Object localObject = new Object();; localObject = paramBuilder.callerTag)
    {
      this.callerTag = localObject;
      return;
    }
  }
  
  static URL getProfilePictureUrl(String paramString, int paramInt1, int paramInt2)
    throws MalformedURLException
  {
    Validate.notNullOrEmpty(paramString, "userId");
    int i = Math.max(paramInt1, 0);
    int j = Math.max(paramInt2, 0);
    if ((i == 0) && (j == 0)) {
      throw new IllegalArgumentException("Either width or height must be greater than 0");
    }
    Uri.Builder localBuilder = new Uri.Builder().encodedPath(String.format("https://graph.facebook.com/%s/picture", new Object[] { paramString }));
    if (j != 0) {
      localBuilder.appendQueryParameter("height", String.valueOf(j));
    }
    if (i != 0) {
      localBuilder.appendQueryParameter("width", String.valueOf(i));
    }
    localBuilder.appendQueryParameter("migration_overrides", "{october_2012:true}");
    return new URL(localBuilder.toString());
  }
  
  Callback getCallback()
  {
    return this.callback;
  }
  
  Object getCallerTag()
  {
    return this.callerTag;
  }
  
  Context getContext()
  {
    return this.context;
  }
  
  URL getImageUrl()
  {
    return this.imageUrl;
  }
  
  boolean isCachedRedirectAllowed()
  {
    return this.allowCachedRedirects;
  }
  
  static class Builder
  {
    private boolean allowCachedRedirects;
    private ImageRequest.Callback callback;
    private Object callerTag;
    private Context context;
    private URL imageUrl;
    
    Builder(Context paramContext, URL paramURL)
    {
      Validate.notNull(paramURL, "imageUrl");
      this.context = paramContext;
      this.imageUrl = paramURL;
    }
    
    ImageRequest build()
    {
      return new ImageRequest(this, null);
    }
    
    Builder setAllowCachedRedirects(boolean paramBoolean)
    {
      this.allowCachedRedirects = paramBoolean;
      return this;
    }
    
    Builder setCallback(ImageRequest.Callback paramCallback)
    {
      this.callback = paramCallback;
      return this;
    }
    
    Builder setCallerTag(Object paramObject)
    {
      this.callerTag = paramObject;
      return this;
    }
  }
  
  static abstract interface Callback
  {
    public abstract void onCompleted(ImageResponse paramImageResponse);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.ImageRequest
 * JD-Core Version:    0.7.0.1
 */