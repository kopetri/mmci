package com.touchtype.keyboard.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;
import com.touchtype.keyboard.BaseKeyboard;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.InterimMenu;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.key.KeyStateImpl;
import com.touchtype.keyboard.key.KeyStateImpl.EmptyState;
import com.touchtype.keyboard.key.KeyStateListener;
import com.touchtype.keyboard.key.SimpleKey;
import com.touchtype.keyboard.key.actions.Action;
import com.touchtype.keyboard.key.actions.ActionDecorator;
import com.touchtype.keyboard.key.actions.ActionParams;
import com.touchtype.keyboard.key.actions.ActionParams.ActionParamsBuilder;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.key.actions.BloopAction;
import com.touchtype.keyboard.key.actions.StatsLoggerAction;
import com.touchtype.keyboard.key.actions.TextAction;
import com.touchtype.keyboard.key.contents.CandidateContent;
import com.touchtype.keyboard.key.delegates.EmptyDelegate;
import com.touchtype.keyboard.key.delegates.KeyDrawDelegate;
import com.touchtype.keyboard.key.delegates.KeyTouchDelegate;
import com.touchtype.keyboard.key.delegates.KeyTouchHandler;
import com.touchtype.keyboard.key.delegates.SimpleDrawDelegate;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import com.touchtype.preferences.HapticsUtil;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.DialogUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class CandidateKeyboardView
  extends BaseKeyboardView<BaseKeyboard<CandidateKey>>
  implements OnThemeChangedListener, CandidateViewInterface
{
  private final CandidateOrderingProvider mCandidateIteratorProvider;
  private final float mHPadding;
  private final InputEventModel mInputEventModel;
  private final Set<InterimMenuCallback> mInterimMenuCallbacks = new HashSet();
  private final Learner mLearner;
  private final float mVPadding;
  
  public CandidateKeyboardView(Context paramContext, Learner paramLearner, InputEventModel paramInputEventModel, int paramInt, CandidateOrderingProvider paramCandidateOrderingProvider, float paramFloat1, float paramFloat2)
  {
    super(paramContext, createCandidateKeyboard(paramContext, paramInputEventModel, paramInt, paramCandidateOrderingProvider, paramFloat1, paramFloat2));
    this.mLearner = paramLearner;
    this.mInputEventModel = paramInputEventModel;
    this.mCandidateIteratorProvider = paramCandidateOrderingProvider;
    this.mHPadding = paramFloat1;
    this.mVPadding = paramFloat2;
  }
  
  private static CandidateKey createCandidateKey(Context paramContext, InputEventModel paramInputEventModel, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
  {
    KeyStateImpl localKeyStateImpl = new KeyStateImpl(paramInputEventModel);
    if (ProductConfiguration.isWatchBuild(paramContext)) {}
    for (CandidateAction localCandidateAction = new CandidateAction(paramContext, localKeyStateImpl, paramInputEventModel, new ActionParams.ActionParamsBuilder().swipeXActivationThreshold(2.0F * paramKeyArea.getDrawBounds().width()).swipeYActivationThreshold(0.3F * paramKeyArea.getDrawBounds().height()).swipeMinXVelocity(2.0F * paramKeyArea.getDrawBounds().width()).swipeMinYVelocity(0.6F * paramKeyArea.getDrawBounds().height()).longPressTimeout(TouchTypePreferences.getInstance(paramContext).getLongPressTimeOut()).build(), new BloopAction(paramContext, new TextAction(EnumSet.of(ActionType.SWIPE_DOWN), paramInputEventModel, " ", new StatsLoggerAction(TouchTypePreferences.getInstance(paramContext)))));; localCandidateAction = new CandidateAction(paramContext, localKeyStateImpl, paramInputEventModel)) {
      return new CandidateKeyImpl(paramKeyArea, localKeyStateImpl, new CandidateDrawDelegate(paramStyleId, paramKeyArea, new CandidateContent(), localKeyStateImpl), new KeyTouchHandler(localKeyStateImpl, localCandidateAction, paramKeyArea.getBounds().width() / 2.0F), localCandidateAction, null);
    }
  }
  
  private static BaseKeyboard<CandidateKey> createCandidateKeyboard(Context paramContext, InputEventModel paramInputEventModel, int paramInt, CandidateOrderingProvider paramCandidateOrderingProvider, float paramFloat1, float paramFloat2)
  {
    ArrayList localArrayList = new ArrayList(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localArrayList.add(createCandidateKey(paramContext, paramInputEventModel, new KeyArea(CandidateKeyboardViewUtils.getBounds(i, paramInt, paramFloat1, paramFloat2), 0), getCandidateStyle(i, paramInt, paramCandidateOrderingProvider)));
    }
    return new BaseKeyboard(localArrayList, createEmptyKey(paramInputEventModel), 0.8F);
  }
  
  private static CandidateKey createEmptyKey(InputEventModel paramInputEventModel)
  {
    return new EmptyCandidateKey(new KeyArea(new RectF(), 0), new KeyStateImpl.EmptyState(), null, new EmptyDelegate());
  }
  
  private InterimMenuCallback createInterimCallback(final KeyState paramKeyState)
  {
    InterimMenuCallback local2 = new InterimMenuCallback()
    {
      public void onInterimClosed()
      {
        paramKeyState.setInterimState(KeyState.InterimMenu.NONE);
      }
    };
    this.mInterimMenuCallbacks.add(local2);
    return local2;
  }
  
  private KeyStateListener createInterimListener(final Key paramKey)
  {
    KeyStateListener local1 = new KeyStateListener()
    {
      public void onKeyStateChanged(KeyState paramAnonymousKeyState)
      {
        if (!CandidateKeyboardView.this.isShown()) {
          return;
        }
        switch (CandidateKeyboardView.5.$SwitchMap$com$touchtype$keyboard$key$KeyState$InterimMenu[paramAnonymousKeyState.getInterimState().ordinal()])
        {
        default: 
          return;
        }
        CandidateKeyboardView.this.showCandidatePopup((CandidateContent)paramKey.getContent(), CandidateKeyboardView.access$100(CandidateKeyboardView.this, paramAnonymousKeyState));
        paramAnonymousKeyState.setPressed(false);
        paramAnonymousKeyState.setInterimState(KeyState.InterimMenu.NONE);
      }
    };
    this.mListeners.add(local1);
    return local1;
  }
  
  private DialogInterface.OnClickListener createOnAcceptListener(final String paramString)
  {
    new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        boolean bool = CandidateKeyboardView.this.mLearner.forgetWord(paramString);
        if (bool) {}
        for (String str = CandidateKeyboardView.this.getContext().getString(2131297051);; str = CandidateKeyboardView.this.getContext().getString(2131297052))
        {
          if (bool)
          {
            HapticsUtil.hapticClick(CandidateKeyboardView.this.getContext());
            CandidateKeyboardView.this.mInputEventModel.refreshPredictions(true);
          }
          Context localContext = CandidateKeyboardView.this.getContext();
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramString;
          Toast.makeText(localContext, String.format(str, arrayOfObject), 0).show();
          return;
        }
      }
    };
  }
  
  private DialogInterface.OnClickListener createOnCancelListener()
  {
    new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        Iterator localIterator = CandidateKeyboardView.this.mInterimMenuCallbacks.iterator();
        while (localIterator.hasNext()) {
          ((InterimMenuCallback)localIterator.next()).onInterimClosed();
        }
        CandidateKeyboardView.this.mInterimMenuCallbacks.clear();
      }
    };
  }
  
  private static KeyStyle.StyleId getCandidateStyle(int paramInt1, int paramInt2, CandidateOrderingProvider paramCandidateOrderingProvider)
  {
    if (paramInt1 == paramCandidateOrderingProvider.getTopCandidateKeyIndex(paramInt2)) {
      return KeyStyle.StyleId.TOPCANDIDATE;
    }
    return KeyStyle.StyleId.CANDIDATE;
  }
  
  private void showCandidatePopup(CandidateContent paramCandidateContent, InterimMenuCallback paramInterimMenuCallback)
  {
    if (paramInterimMenuCallback != null) {
      this.mInterimMenuCallbacks.add(paramInterimMenuCallback);
    }
    Resources localResources = getContext().getResources();
    String str1 = localResources.getString(2131297291);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramCandidateContent.getLabel();
    String str2 = String.format(str1, arrayOfObject);
    AlertDialog localAlertDialog = new AlertDialog.Builder(getContext()).setMessage(str2).setTitle(localResources.getString(2131297292)).setPositiveButton(localResources.getString(2131297275), createOnAcceptListener(paramCandidateContent.getLabel())).setNegativeButton(localResources.getString(2131297276), createOnCancelListener()).create();
    DialogUtil.prepareIMEDialog(localAlertDialog, this);
    localAlertDialog.show();
  }
  
  private void updateBackground()
  {
    setBackgroundDrawable(ThemeManager.getInstance(getContext()).getThemeHandler().getProperties().getCandidateBackground());
  }
  
  public View getView()
  {
    return this;
  }
  
  public boolean hasDifferentPadding(float paramFloat1, float paramFloat2)
  {
    return (this.mHPadding != paramFloat1) || (this.mVPadding != paramFloat2);
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ThemeManager.getInstance(getContext()).addListener(this);
    updateBackground();
    Iterator localIterator = this.mKeyboard.getKeys().iterator();
    while (localIterator.hasNext())
    {
      CandidateKey localCandidateKey = (CandidateKey)localIterator.next();
      localCandidateKey.getState().addListener(KeyState.StateType.INTERIM, createInterimListener(localCandidateKey));
    }
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ThemeManager.getInstance(getContext()).removeListener(this);
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    float f = 0.5F * (2.0F - (float)Math.exp(-Math.abs(0.17D * (paramInt1 / paramInt2 - this.mKeyboard.getKeys().size()))));
    Iterator localIterator = this.mKeyboard.getKeys().iterator();
    while (localIterator.hasNext()) {
      ((CandidateKey)localIterator.next()).setContentLimit(f);
    }
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onThemeChanged()
  {
    updateBackground();
    invalidateAllKeys();
  }
  
  public void updateCandidateKeys(List<Candidate> paramList, boolean paramBoolean)
  {
    int i = 0;
    int j = -1;
    int k = this.mKeyboard.getKeys().size();
    Iterator localIterator = this.mCandidateIteratorProvider.getIterator(this.mKeyboard.getKeys().size(), paramList.size());
    if ((localIterator.hasNext()) && (i < this.mKeyboard.getKeys().size()))
    {
      Integer localInteger = (Integer)localIterator.next();
      int i2;
      if (localInteger != null)
      {
        i2 = i;
        label94:
        j = Math.max(j, i2);
        if (localInteger == null) {
          break label168;
        }
      }
      label168:
      for (int i3 = i;; i3 = k)
      {
        k = Math.min(k, i3);
        if (localInteger != null) {
          ((CandidateKey)this.mKeyboard.getKey(i)).updateCandidate((Candidate)paramList.get(localInteger.intValue()));
        }
        i++;
        break;
        i2 = j;
        break label94;
      }
    }
    for (int m = 0; m < k; m++) {
      ((CandidateKey)this.mKeyboard.getKey(m)).updateCandidate(Candidate.empty());
    }
    for (int n = j + 1; n < this.mKeyboard.getKeys().size(); n++) {
      ((CandidateKey)this.mKeyboard.getKey(n)).updateCandidate(Candidate.empty());
    }
    CandidateKey localCandidateKey;
    if (this.mKeyboard.getKeys().size() > 0)
    {
      int i1 = this.mCandidateIteratorProvider.getTopCandidateKeyIndex(this.mKeyboard.getKeys().size());
      if (i1 != -1)
      {
        localCandidateKey = (CandidateKey)this.mKeyboard.getKey(i1);
        if (!paramBoolean) {
          break label341;
        }
      }
    }
    label341:
    for (KeyStyle.StyleId localStyleId = KeyStyle.StyleId.CANDIDATE;; localStyleId = KeyStyle.StyleId.TOPCANDIDATE)
    {
      localCandidateKey.setStyle(localStyleId);
      invalidateAllKeys();
      return;
    }
  }
  
  private static final class CandidateAction
    extends ActionDecorator
  {
    private BloopAction mBloopAction;
    private Candidate mCandidate;
    private final Context mContext;
    private final InputEventModel mInputEventModel;
    private final KeyState mState;
    
    public CandidateAction(Context paramContext, KeyState paramKeyState, InputEventModel paramInputEventModel)
    {
      this(paramContext, paramKeyState, paramInputEventModel, createParams(paramContext), getRootAction(paramContext));
    }
    
    public CandidateAction(Context paramContext, KeyState paramKeyState, InputEventModel paramInputEventModel, ActionParams paramActionParams, Action paramAction)
    {
      super(paramAction);
      this.mContext = paramContext;
      this.mState = paramKeyState;
      this.mInputEventModel = paramInputEventModel;
      this.mBloopAction = new BloopAction(EnumSet.of(ActionType.CLICK), paramContext, paramActionParams, paramAction);
    }
    
    private static ActionParams createParams(Context paramContext)
    {
      return new ActionParams.ActionParamsBuilder().longPressTimeout(TouchTypePreferences.getInstance(paramContext).getLongPressTimeOut()).build();
    }
    
    private static Action getRootAction(Context paramContext)
    {
      return new StatsLoggerAction(TouchTypePreferences.getInstance(paramContext));
    }
    
    public EnumSet<ActionType> getUsedActions()
    {
      return EnumSet.of(ActionType.CLICK, ActionType.LONGPRESS);
    }
    
    public void onClick(TouchEvent.Touch paramTouch)
    {
      if ((this.mCandidate != null) && (this.mCandidate.toString().length() > 0))
      {
        this.mBloopAction.click(paramTouch);
        CandidateKeyboardViewUtils.recordCandidateRanking(this.mContext, this.mCandidate);
        this.mInputEventModel.onPredictionSelected(this.mCandidate);
      }
    }
    
    public void onLongPress()
    {
      if ((this.mCandidate != null) && (this.mCandidate.toString().length() > 0))
      {
        this.mBloopAction.click(null);
        this.mState.setInterimState(KeyState.InterimMenu.REMOVE_CANDIDATE);
      }
    }
    
    public void setCandidate(Candidate paramCandidate)
    {
      this.mCandidate = paramCandidate;
    }
  }
  
  private static final class CandidateDrawDelegate
    extends SimpleDrawDelegate
  {
    private final KeyArea mArea;
    private final KeyState mState;
    private KeyStyle.StyleId mStyleId;
    
    public CandidateDrawDelegate(KeyStyle.StyleId paramStyleId, KeyArea paramKeyArea, CandidateContent paramCandidateContent, KeyState paramKeyState)
    {
      super(paramKeyArea, paramCandidateContent, paramKeyState);
      this.mState = paramKeyState;
      this.mArea = paramKeyArea;
      this.mStyleId = paramStyleId;
    }
    
    public Drawable getKeyDrawable(Theme paramTheme)
    {
      return paramTheme.getRenderer().getKeyDrawable(getContent(), this.mState, this.mArea, this.mStyleId);
    }
    
    public void setStyle(KeyStyle.StyleId paramStyleId)
    {
      this.mStyleId = paramStyleId;
    }
  }
  
  public static abstract interface CandidateKey
    extends Key
  {
    public abstract void setContentLimit(float paramFloat);
    
    public abstract void setStyle(KeyStyle.StyleId paramStyleId);
    
    public abstract void updateCandidate(Candidate paramCandidate);
  }
  
  private static final class CandidateKeyImpl
    extends SimpleKey
    implements CandidateKeyboardView.CandidateKey
  {
    private final CandidateKeyboardView.CandidateAction mCandidateAction;
    private final CandidateKeyboardView.CandidateDrawDelegate mDrawDelegate;
    
    private CandidateKeyImpl(KeyArea paramKeyArea, KeyState paramKeyState, CandidateKeyboardView.CandidateDrawDelegate paramCandidateDrawDelegate, KeyTouchHandler paramKeyTouchHandler, CandidateKeyboardView.CandidateAction paramCandidateAction)
    {
      super(paramKeyState, paramCandidateDrawDelegate, paramKeyTouchHandler);
      this.mCandidateAction = paramCandidateAction;
      this.mDrawDelegate = paramCandidateDrawDelegate;
    }
    
    public void setContentLimit(float paramFloat)
    {
      ((CandidateContent)getContent()).setHeightLimit(paramFloat);
    }
    
    public void setStyle(KeyStyle.StyleId paramStyleId)
    {
      this.mDrawDelegate.setStyle(paramStyleId);
    }
    
    public void updateCandidate(Candidate paramCandidate)
    {
      this.mCandidateAction.setCandidate(paramCandidate);
      ((CandidateContent)getContent()).updateCandidate(paramCandidate);
    }
  }
  
  private static final class EmptyCandidateKey
    extends SimpleKey
    implements CandidateKeyboardView.CandidateKey
  {
    public EmptyCandidateKey(KeyArea paramKeyArea, KeyState paramKeyState, KeyDrawDelegate paramKeyDrawDelegate, KeyTouchDelegate paramKeyTouchDelegate)
    {
      super(paramKeyState, paramKeyDrawDelegate, paramKeyTouchDelegate);
    }
    
    public void setContentLimit(float paramFloat) {}
    
    public void setStyle(KeyStyle.StyleId paramStyleId) {}
    
    public void updateCandidate(Candidate paramCandidate) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.CandidateKeyboardView
 * JD-Core Version:    0.7.0.1
 */