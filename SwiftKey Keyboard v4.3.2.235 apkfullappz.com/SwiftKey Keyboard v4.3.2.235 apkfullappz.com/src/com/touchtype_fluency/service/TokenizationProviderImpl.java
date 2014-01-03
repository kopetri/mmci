package com.touchtype_fluency.service;

import com.touchtype.util.LogUtil;
import com.touchtype_fluency.ContextCurrentWord;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.internal.TokenizerImpl;

public class TokenizationProviderImpl
  implements TokenizationProvider
{
  private static final String TAG = TokenizationProviderImpl.class.getSimpleName();
  private final FluencyServiceProxyI mFluencyServiceProxy;
  private Predictor mPredictor;
  
  public TokenizationProviderImpl(FluencyServiceProxyI paramFluencyServiceProxyI)
  {
    this.mFluencyServiceProxy = paramFluencyServiceProxyI;
    this.mPredictor = getPredictor(this.mFluencyServiceProxy);
  }
  
  private Predictor getPredictor(FluencyServiceProxyI paramFluencyServiceProxyI)
  {
    if ((this.mFluencyServiceProxy != null) && (this.mFluencyServiceProxy.isReady())) {
      return this.mFluencyServiceProxy.getPredictor();
    }
    return null;
  }
  
  public TokenizationProvider.ContextCurrentWord getContextCurrentWord(String paramString)
  {
    if (this.mPredictor == null) {
      this.mPredictor = getPredictor(this.mFluencyServiceProxy);
    }
    if (this.mPredictor != null) {}
    for (ContextCurrentWord localContextCurrentWord = this.mPredictor.splitContextCurrentWord(paramString); localContextCurrentWord != null; localContextCurrentWord = TokenizerImpl.legacyGetContextCurrentWord(paramString, 6))
    {
      return new TokenizationProvider.ContextCurrentWord(localContextCurrentWord);
      LogUtil.w(TAG, "Predictor instance not available, falling back to legacy tokenizer");
    }
    return new TokenizationProvider.ContextCurrentWord("", new Sequence());
  }
  
  public Sequence getSequence(String paramString)
  {
    if (this.mPredictor == null) {
      this.mPredictor = getPredictor(this.mFluencyServiceProxy);
    }
    Sequence localSequence;
    if (this.mPredictor != null) {
      localSequence = this.mPredictor.split(paramString);
    }
    String str;
    do
    {
      return localSequence;
      LogUtil.w(TAG, "Predictor instance not available, falling back to legacy tokenizer");
      ContextCurrentWord localContextCurrentWord = TokenizerImpl.legacyGetContextCurrentWord(paramString, 100);
      localSequence = localContextCurrentWord.getContext();
      str = localContextCurrentWord.getCurrentWord();
    } while (str.length() <= 0);
    localSequence.append(str);
    return localSequence;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.TokenizationProviderImpl
 * JD-Core Version:    0.7.0.1
 */