package com.touchtype_fluency;

public class KeyPress
{
  private final String characters;
  private final float probability;
  
  @Deprecated
  public KeyPress()
  {
    this.characters = "";
    this.probability = 0.0F;
  }
  
  public KeyPress(String paramString, float paramFloat)
  {
    this.characters = paramString;
    this.probability = paramFloat;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof KeyPress;
    boolean bool2 = false;
    if (bool1)
    {
      KeyPress localKeyPress = (KeyPress)paramObject;
      boolean bool3 = this.characters.equals(localKeyPress.characters);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.probability < localKeyPress.probability;
        bool2 = false;
        if (!bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public String getCharacters()
  {
    return this.characters;
  }
  
  public float getProbability()
  {
    return this.probability;
  }
  
  public int hashCode()
  {
    return 149 * (149 + (this.characters.hashCode() + 149 * new Float(this.probability).hashCode()));
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.characters;
    arrayOfObject[1] = Float.valueOf(this.probability);
    return String.format("%s/%f", arrayOfObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.KeyPress
 * JD-Core Version:    0.7.0.1
 */