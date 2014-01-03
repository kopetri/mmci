package com.touchtype.keyboard.theme.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class TextMetrics
{
  private ImmutableSet<String> linkSet = null;
  private ImmutableSet.Builder<String> linkSetBuilder = new ImmutableSet.Builder();
  
  public Set<String> getLinkSet()
  {
    if (this.linkSet == null) {
      this.linkSet = this.linkSetBuilder.build();
    }
    return this.linkSet;
  }
  
  public void update(String paramString)
  {
    if (this.linkSet != null)
    {
      if (this.linkSet.contains(paramString)) {
        return;
      }
      throw new IllegalStateException("Cannot update already used TextMetrics");
    }
    this.linkSetBuilder.add(paramString);
  }
  
  public static final class TextMetricsRegister
  {
    private final Map<String, TextMetrics> mRegister = new HashMap();
    
    public TextMetrics getTextMetrics(String paramString)
    {
      TextMetrics localTextMetrics = (TextMetrics)this.mRegister.get(paramString);
      if (localTextMetrics == null)
      {
        localTextMetrics = new TextMetrics();
        this.mRegister.put(paramString, localTextMetrics);
      }
      return localTextMetrics;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.TextMetrics
 * JD-Core Version:    0.7.0.1
 */