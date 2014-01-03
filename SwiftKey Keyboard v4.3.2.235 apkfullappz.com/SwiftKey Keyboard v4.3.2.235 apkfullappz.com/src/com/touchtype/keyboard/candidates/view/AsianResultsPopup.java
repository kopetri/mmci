package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.PopupWindow;
import com.touchtype.keyboard.candidates.AsianPredictionsAdapter;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import com.touchtype.keyboard.view.BaseKeyboardView;
import java.util.List;

public final class AsianResultsPopup
  extends PopupWindow
{
  private AsianPredictionsAdapter mAdapter;
  private List<Candidate> mCandidates;
  private GridView mGridView;
  private final InputEventModel mInputEventModel;
  private final AsianCandidateLayout mParent;
  
  public AsianResultsPopup(AsianCandidateLayout paramAsianCandidateLayout, InputEventModel paramInputEventModel)
  {
    super(paramAsianCandidateLayout.getContext());
    this.mParent = paramAsianCandidateLayout;
    this.mInputEventModel = paramInputEventModel;
    Context localContext = paramAsianCandidateLayout.getContext();
    setFocusable(true);
    setBackgroundDrawable(localContext.getResources().getDrawable(2130838211));
    setInputMethodMode(2);
    setWindowLayoutMode(0, 0);
    FrameLayout localFrameLayout = (FrameLayout)((LayoutInflater)localContext.getSystemService("layout_inflater")).inflate(2130903045, null);
    setContentView(localFrameLayout);
    this.mAdapter = new AsianPredictionsAdapter(localContext);
    this.mGridView = ((GridView)localFrameLayout.findViewById(2131230771));
    this.mGridView.setAdapter(this.mAdapter);
    this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        AsianResultsPopup.this.mInputEventModel.onPredictionSelected((Candidate)AsianResultsPopup.this.mCandidates.get(paramAnonymousInt));
        AsianResultsPopup.this.dismiss();
      }
    });
  }
  
  public void show(List<Candidate> paramList)
  {
    this.mCandidates = paramList;
    if (!isShowing())
    {
      if (this.mAdapter != null) {
        this.mAdapter.setCandidates(paramList);
      }
      int i = this.mParent.getHeight();
      BaseKeyboardView localBaseKeyboardView = TouchTypeSoftKeyboard.getInstance().getKeyboardView();
      if ((localBaseKeyboardView != null) && (localBaseKeyboardView.getVisibility() == 0)) {
        i += localBaseKeyboardView.getHeight();
      }
      setHeight(i);
      setWidth(this.mParent.getWidth());
      showAtLocation(this.mParent, 80, 0, 0);
      this.mGridView.setSelection(0);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianResultsPopup
 * JD-Core Version:    0.7.0.1
 */