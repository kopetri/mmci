package com.touchtype.keyboard.candidates;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public final class AsianPredictionsAdapter
  extends BaseAdapter
{
  private List<Candidate> mCandidates;
  private final Context mContext;
  
  public AsianPredictionsAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public int getCount()
  {
    if (this.mCandidates == null) {
      return 0;
    }
    return this.mCandidates.size();
  }
  
  public Object getItem(int paramInt)
  {
    if (this.mCandidates == null) {
      return null;
    }
    return (Candidate)this.mCandidates.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    TextView localTextView;
    if (paramView == null)
    {
      localTextView = new TextView(this.mContext);
      localTextView.setPadding(8, 8, 8, 8);
      localTextView.setTextSize(1, 24.0F);
    }
    for (;;)
    {
      localTextView.setText(((Candidate)this.mCandidates.get(paramInt)).toString());
      return localTextView;
      localTextView = (TextView)paramView;
    }
  }
  
  public void setCandidates(List<Candidate> paramList)
  {
    this.mCandidates = paramList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.AsianPredictionsAdapter
 * JD-Core Version:    0.7.0.1
 */