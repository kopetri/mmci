package com.touchtype_fluency;

import java.io.IOException;

public abstract interface Session
{
  public abstract void batchLoad(ModelSetDescription[] paramArrayOfModelSetDescription)
    throws IOException;
  
  public abstract void batchUnload(ModelSetDescription[] paramArrayOfModelSetDescription);
  
  public abstract void dispose();
  
  public abstract void enableModels(TagSelector paramTagSelector);
  
  public abstract ModelSetDescription[] getLoadedSets();
  
  public abstract ParameterSet getParameterSet();
  
  public abstract Predictor getPredictor();
  
  public abstract Punctuator getPunctuator();
  
  public abstract SentenceSegmenter getSentenceSegmenter();
  
  public abstract String[] getTags();
  
  public abstract String[] getTags(TagSelector paramTagSelector);
  
  public abstract Tokenizer getTokenizer();
  
  public abstract Trainer getTrainer();
  
  public abstract void load(ModelSetDescription paramModelSetDescription)
    throws IOException;
  
  public abstract void setParameterLearning(boolean paramBoolean);
  
  public abstract void unload(ModelSetDescription paramModelSetDescription);
  
  public abstract void verify(ModelSetDescription paramModelSetDescription)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Session
 * JD-Core Version:    0.7.0.1
 */