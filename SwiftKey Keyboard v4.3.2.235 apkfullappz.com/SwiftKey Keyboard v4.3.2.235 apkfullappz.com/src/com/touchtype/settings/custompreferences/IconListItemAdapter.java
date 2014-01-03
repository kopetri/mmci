package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.ListPreference;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import com.touchtype.keyboard.view.IconWithShadowImageView;
import java.util.ArrayList;
import junit.framework.Assert;

public class IconListItemAdapter
  extends BaseAdapter
{
  private static final String TAG = IconListItemAdapter.class.getSimpleName();
  private CharSequence[] mEntries;
  private boolean mIconShadow;
  private ArrayList<DataHolder> mItems;
  private LayoutInflater mLayoutInflater;
  private ListPreference mParent;
  private int mResourceLayout;
  private CharSequence[] mValues;
  
  public IconListItemAdapter(Context paramContext, ListPreference paramListPreference, int paramInt, CharSequence[] paramArrayOfCharSequence1, CharSequence[] paramArrayOfCharSequence2, CharSequence[] paramArrayOfCharSequence3, Drawable[] paramArrayOfDrawable, int[] paramArrayOfInt, boolean paramBoolean)
  {
    this.mLayoutInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    this.mResourceLayout = paramInt;
    this.mParent = paramListPreference;
    this.mIconShadow = paramBoolean;
    initData(paramContext, paramArrayOfCharSequence1, paramArrayOfCharSequence2, paramArrayOfCharSequence3, paramArrayOfDrawable, paramArrayOfInt);
  }
  
  private void initData(Context paramContext, CharSequence[] paramArrayOfCharSequence1, CharSequence[] paramArrayOfCharSequence2, CharSequence[] paramArrayOfCharSequence3, Drawable[] paramArrayOfDrawable, int[] paramArrayOfInt)
  {
    boolean bool1;
    boolean bool2;
    label19:
    boolean bool3;
    if (paramArrayOfCharSequence1 != null)
    {
      bool1 = true;
      Assert.assertTrue(bool1);
      if (paramArrayOfCharSequence2 == null) {
        break label186;
      }
      bool2 = true;
      Assert.assertTrue(bool2);
      if (paramArrayOfDrawable == null) {
        break label192;
      }
      bool3 = true;
      label32:
      Assert.assertTrue(bool3);
      if (paramArrayOfCharSequence3 != null) {
        if (paramArrayOfCharSequence3.length != paramArrayOfCharSequence1.length) {
          break label198;
        }
      }
    }
    CharSequence[] arrayOfCharSequence1;
    CharSequence[] arrayOfCharSequence2;
    Drawable[] arrayOfDrawable;
    CharSequence[] arrayOfCharSequence3;
    label186:
    label192:
    label198:
    for (boolean bool4 = true;; bool4 = false)
    {
      Assert.assertTrue(bool4);
      if (paramArrayOfInt == null) {
        break label366;
      }
      arrayOfCharSequence1 = new CharSequence[paramArrayOfInt.length];
      arrayOfCharSequence2 = new CharSequence[paramArrayOfInt.length];
      arrayOfDrawable = new Drawable[paramArrayOfInt.length];
      arrayOfCharSequence3 = null;
      if (paramArrayOfCharSequence3 != null) {
        arrayOfCharSequence3 = new CharSequence[paramArrayOfInt.length];
      }
      int k = 0;
      int m = paramArrayOfInt.length;
      for (int n = 0; n < m; n++)
      {
        int i1 = paramArrayOfInt[n];
        arrayOfCharSequence1[k] = paramArrayOfCharSequence1[i1];
        arrayOfCharSequence2[k] = paramArrayOfCharSequence2[i1];
        arrayOfDrawable[k] = paramArrayOfDrawable[i1];
        if (arrayOfCharSequence3 != null) {
          arrayOfCharSequence3[k] = paramArrayOfCharSequence3[i1];
        }
        k++;
      }
      bool1 = false;
      break;
      bool2 = false;
      break label19;
      bool3 = false;
      break label32;
    }
    paramArrayOfCharSequence1 = arrayOfCharSequence1;
    paramArrayOfCharSequence3 = arrayOfCharSequence3;
    paramArrayOfCharSequence2 = arrayOfCharSequence2;
    int i = paramArrayOfCharSequence1.length;
    this.mItems = new ArrayList(i);
    int j = 0;
    label234:
    if (j < i)
    {
      DataHolder localDataHolder = new DataHolder(null);
      localDataHolder.icon = arrayOfDrawable[j];
      if (paramArrayOfCharSequence3 != null)
      {
        localDataHolder.title = new SpannableString(paramArrayOfCharSequence1[j].toString() + "\n" + paramArrayOfCharSequence3[j].toString());
        localDataHolder.title.setSpan(new TextAppearanceSpan(paramContext, 2131427463), paramArrayOfCharSequence1[j].length(), localDataHolder.title.length(), 0);
      }
      for (;;)
      {
        this.mItems.add(localDataHolder);
        j++;
        break label234;
        label366:
        arrayOfDrawable = paramArrayOfDrawable;
        break;
        localDataHolder.title = new SpannableString(paramArrayOfCharSequence1[j].toString());
      }
    }
    this.mEntries = paramArrayOfCharSequence1;
    this.mValues = paramArrayOfCharSequence2;
  }
  
  public int getCount()
  {
    return this.mItems.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.mItems.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    if (paramView == null)
    {
      localViewHolder = new ViewHolder(null);
      paramView = this.mLayoutInflater.inflate(this.mResourceLayout, null);
      localViewHolder.checkedTextView = ((CheckedTextView)paramView.findViewById(2131230851));
      localViewHolder.iconView = ((IconWithShadowImageView)paramView.findViewById(2131230850));
      localViewHolder.iconView.setShadow(this.mIconShadow);
      paramView.setTag(localViewHolder);
    }
    for (;;)
    {
      DataHolder localDataHolder = (DataHolder)getItem(paramInt);
      localViewHolder.checkedTextView.setText(localDataHolder.title);
      localViewHolder.iconView.setImageDrawable(localDataHolder.icon);
      if (this.mParent != null)
      {
        if (paramInt != this.mParent.findIndexOfValue(this.mParent.getValue())) {
          break;
        }
        localViewHolder.checkedTextView.setChecked(true);
      }
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
    }
    localViewHolder.checkedTextView.setChecked(false);
    return paramView;
  }
  
  public int getViewTypeCount()
  {
    return 1;
  }
  
  private static final class DataHolder
  {
    public Drawable icon;
    public SpannableString title;
  }
  
  private static final class ViewHolder
  {
    public CheckedTextView checkedTextView;
    public IconWithShadowImageView iconView;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.IconListItemAdapter
 * JD-Core Version:    0.7.0.1
 */