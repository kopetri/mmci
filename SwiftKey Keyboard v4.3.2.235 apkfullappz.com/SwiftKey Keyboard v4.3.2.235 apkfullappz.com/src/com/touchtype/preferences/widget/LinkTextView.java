package com.touchtype.preferences.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.touchtype.R.styleable;
import java.util.List;

public class LinkTextView
  extends TextView
{
  private final String url;
  
  public LinkTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public LinkTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.url = getUrl(paramContext, paramAttributeSet, paramInt);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.url;
    arrayOfObject[1] = getText();
    setText(Html.fromHtml(String.format("<a href=\"%s\">%s</a>", arrayOfObject)));
    if (canNavigateLink())
    {
      setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(LinkTextView.this.url));
          LinkTextView.this.getContext().startActivity(localIntent);
        }
      });
      return;
    }
    setAppearanceEnabled(false);
  }
  
  private boolean canNavigateLink()
  {
    if (this.url == null) {}
    while (!getContext().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(this.url)), 65536).isEmpty()) {
      return true;
    }
    return false;
  }
  
  private String getUrl(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    return paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.LinkTextView, paramInt, 0).getString(0);
  }
  
  public void setAppearanceEnabled(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setLinkTextColor(getResources().getColor(2131165358));
      return;
    }
    setLinkTextColor(getTextColors().getColorForState(View.EMPTY_STATE_SET, 2131165358));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.widget.LinkTextView
 * JD-Core Version:    0.7.0.1
 */