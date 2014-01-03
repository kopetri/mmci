package com.touchtype.keyboard.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.Candidate.Ranking;
import com.touchtype.keyboard.candidates.view.CandidateButton;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.preferences.HapticsUtil;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.DialogUtil;
import java.util.Locale;

public class CandidateKeyboardViewUtils
{
  protected static final String TAG = CandidateKeyboardViewUtils.class.getSimpleName();
  
  public static RectF getBounds(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    if (paramInt1 == paramInt2 / 2)
    {
      float f3 = paramInt1 * getSideWidth(paramInt2);
      return new RectF(f3 + paramFloat1, 0.0F + 2.0F * paramFloat2, f3 + getCentralWidth(paramInt2) - paramFloat1, 1.0F - paramFloat2);
    }
    if (paramInt1 < paramInt2 / 2)
    {
      float f2 = paramInt1 * getSideWidth(paramInt2);
      return new RectF(f2 + paramFloat1, 0.0F + 2.0F * paramFloat2, f2 + getSideWidth(paramInt2) - paramFloat1, 1.0F - paramFloat2);
    }
    float f1 = (paramInt1 - 1) * getSideWidth(paramInt2) + getCentralWidth(paramInt2);
    return new RectF(f1 + paramFloat1, 0.0F + 2.0F * paramFloat2, f1 + getSideWidth(paramInt2) - paramFloat1, 1.0F - paramFloat2);
  }
  
  public static View.OnClickListener getCandidateButtonClickListener(Context paramContext, final InputEventModel paramInputEventModel)
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((paramAnonymousView instanceof CandidateButton))
        {
          Candidate localCandidate = (Candidate)((CandidateButton)paramAnonymousView).getTag();
          if ((localCandidate != null) && (localCandidate.toString().length() > 0))
          {
            HapticsUtil.hapticClick(this.val$context);
            CandidateKeyboardViewUtils.recordCandidateRanking(this.val$context, localCandidate);
            paramInputEventModel.onPredictionSelected(localCandidate);
          }
          return;
        }
        String str = CandidateKeyboardViewUtils.TAG;
        Locale localLocale = Locale.ENGLISH;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramAnonymousView.getId());
        arrayOfObject[1] = paramAnonymousView.getClass().getSimpleName();
        Log.w(str, String.format(localLocale, "Unexpected view with ID %d and class %s", arrayOfObject));
      }
    };
  }
  
  public static View.OnLongClickListener getCandidateButtonLongClickListener(Context paramContext, final Learner paramLearner, final InputEventModel paramInputEventModel, final View paramView)
  {
    new View.OnLongClickListener()
    {
      private DialogInterface.OnClickListener createOnAcceptListener(final String paramAnonymousString)
      {
        new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.dismiss();
            boolean bool = CandidateKeyboardViewUtils.2.this.val$learner.forgetWord(paramAnonymousString);
            if (bool) {}
            for (String str = CandidateKeyboardViewUtils.2.this.val$context.getString(2131297051);; str = CandidateKeyboardViewUtils.2.this.val$context.getString(2131297052))
            {
              if (bool)
              {
                HapticsUtil.hapticClick(CandidateKeyboardViewUtils.2.this.val$context);
                CandidateKeyboardViewUtils.2.this.val$iem.refreshPredictions(true);
              }
              Context localContext = CandidateKeyboardViewUtils.2.this.val$context;
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = paramAnonymousString;
              Toast.makeText(localContext, String.format(str, arrayOfObject), 0).show();
              return;
            }
          }
        };
      }
      
      private void showCandidatePopup(String paramAnonymousString)
      {
        Resources localResources = this.val$context.getResources();
        String str = String.format(localResources.getString(2131297291), new Object[] { paramAnonymousString });
        AlertDialog localAlertDialog = new AlertDialog.Builder(this.val$context).setMessage(str).setTitle(localResources.getString(2131297292)).setPositiveButton(localResources.getString(2131297275), createOnAcceptListener(paramAnonymousString)).setNegativeButton(localResources.getString(2131297276), null).create();
        DialogUtil.prepareIMEDialog(localAlertDialog, paramView);
        localAlertDialog.show();
      }
      
      public boolean onLongClick(View paramAnonymousView)
      {
        if ((paramAnonymousView instanceof CandidateButton))
        {
          Candidate localCandidate = (Candidate)((CandidateButton)paramAnonymousView).getTag();
          if ((localCandidate != null) && (localCandidate.toString().length() > 0))
          {
            HapticsUtil.hapticClick(this.val$context);
            showCandidatePopup(localCandidate.toString());
          }
          return true;
        }
        String str = CandidateKeyboardViewUtils.TAG;
        Locale localLocale = Locale.ENGLISH;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramAnonymousView.getId());
        arrayOfObject[1] = paramAnonymousView.getClass().getSimpleName();
        Log.w(str, String.format(localLocale, "Unexpected view with ID %d and class %s", arrayOfObject));
        return true;
      }
    };
  }
  
  public static float getCentralWidth(int paramInt)
  {
    if (paramInt % 2 == 0) {}
    for (float f = 1.0F;; f = (paramInt + 1) / paramInt) {
      return f * getSideWidth(paramInt);
    }
  }
  
  public static float getSideWidth(int paramInt)
  {
    if (paramInt % 2 == 0) {
      return 1.0F / paramInt;
    }
    return 1.0F / (paramInt + 1.0F / paramInt);
  }
  
  public static void recordCandidateRanking(Context paramContext, Candidate paramCandidate)
  {
    Candidate.Ranking localRanking = paramCandidate.getRanking();
    TouchTypePreferences.getInstance(paramContext).getTouchTypeStats().recordCandidateRanking(localRanking);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.CandidateKeyboardViewUtils
 * JD-Core Version:    0.7.0.1
 */