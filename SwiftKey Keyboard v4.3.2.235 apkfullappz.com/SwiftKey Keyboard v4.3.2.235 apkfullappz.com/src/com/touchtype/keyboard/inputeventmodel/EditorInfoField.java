package com.touchtype.keyboard.inputeventmodel;

public final class EditorInfoField
{
  private final int mFieldId;
  private final String mPackageName;
  
  public EditorInfoField(int paramInt, String paramString)
  {
    this.mFieldId = paramInt;
    this.mPackageName = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof EditorInfoField;
    boolean bool2 = false;
    if (bool1)
    {
      EditorInfoField localEditorInfoField = (EditorInfoField)paramObject;
      int i = this.mFieldId;
      int j = localEditorInfoField.mFieldId;
      bool2 = false;
      if (i == j)
      {
        boolean bool3 = this.mPackageName.equals(localEditorInfoField.mPackageName);
        bool2 = false;
        if (bool3) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public int hashCode()
  {
    return this.mPackageName.hashCode() + 29 * this.mFieldId;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.EditorInfoField
 * JD-Core Version:    0.7.0.1
 */