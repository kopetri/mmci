package com.touchtype.settings;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity.Header;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public final class PreferenceHeaderAdapter
  extends ArrayAdapter<PreferenceActivity.Header>
{
  private final LayoutInflater mInflater;
  
  public PreferenceHeaderAdapter(Context paramContext, List<PreferenceActivity.Header> paramList)
  {
    super(paramContext, 0, paramList);
    this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }
  
  public static boolean dontShowFragmentHeader(PreferenceActivity.Header paramHeader, Resources paramResources)
  {
    Bundle localBundle = paramHeader.fragmentArguments;
    return (localBundle != null) && (localBundle.getBoolean(paramResources.getString(2131296458)));
  }
  
  public static boolean isCategoryHeader(PreferenceActivity.Header paramHeader)
  {
    return (paramHeader.fragment == null) && (paramHeader.intent == null);
  }
  
  public boolean areAllItemsEnabled()
  {
    return false;
  }
  
  public int getItemViewType(int paramInt)
  {
    if (isCategoryHeader((PreferenceActivity.Header)getItem(paramInt))) {
      return 0;
    }
    return 1;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Resources localResources = getContext().getResources();
    PreferenceActivity.Header localHeader = (PreferenceActivity.Header)getItem(paramInt);
    switch (getItemViewType(paramInt))
    {
    default: 
      return null;
    case 0: 
      if (paramView == null)
      {
        HeaderViewHolder localHeaderViewHolder3 = new HeaderViewHolder(null);
        View localView2 = this.mInflater.inflate(17367042, paramViewGroup, false);
        localHeaderViewHolder3.title = ((TextView)localView2.findViewById(16908310));
        localHeaderViewHolder3.title.setText(localHeader.getTitle(localResources));
        localView2.setTag(localHeaderViewHolder3);
        return localView2;
      }
      ((HeaderViewHolder)paramView.getTag()).title.setText(localHeader.getTitle(localResources));
      return paramView;
    }
    if (paramView == null)
    {
      HeaderViewHolder localHeaderViewHolder1 = new HeaderViewHolder(null);
      View localView1 = this.mInflater.inflate(2130903099, paramViewGroup, false);
      ImageView localImageView = (ImageView)localView1.findViewById(16908294);
      localHeaderViewHolder1.icon = localImageView;
      localImageView.setImageResource(localHeader.iconRes);
      localHeaderViewHolder1.title = ((TextView)localView1.findViewById(16908310));
      localHeaderViewHolder1.title.setText(localHeader.getTitle(localResources));
      localHeaderViewHolder1.summary = ((TextView)localView1.findViewById(16908304));
      localHeaderViewHolder1.summary.setText(localHeader.getSummary(localResources));
      localView1.setTag(localHeaderViewHolder1);
      return localView1;
    }
    HeaderViewHolder localHeaderViewHolder2 = (HeaderViewHolder)paramView.getTag();
    if (localHeaderViewHolder2.icon != null) {
      localHeaderViewHolder2.icon.setImageResource(localHeader.iconRes);
    }
    localHeaderViewHolder2.title.setText(localHeader.getTitle(localResources));
    localHeaderViewHolder2.summary.setText(localHeader.getSummary(localResources));
    return paramView;
  }
  
  public int getViewTypeCount()
  {
    return 2;
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  public boolean isEnabled(int paramInt)
  {
    return getItemViewType(paramInt) != 0;
  }
  
  private static final class HeaderViewHolder
  {
    ImageView icon;
    TextView summary;
    TextView title;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.PreferenceHeaderAdapter
 * JD-Core Version:    0.7.0.1
 */