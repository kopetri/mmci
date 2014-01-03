package com.facebook;

public enum SessionState
{
  private final Category category;
  
  static
  {
    OPENED = new SessionState("OPENED", 3, Category.OPENED_CATEGORY);
    OPENED_TOKEN_UPDATED = new SessionState("OPENED_TOKEN_UPDATED", 4, Category.OPENED_CATEGORY);
    CLOSED_LOGIN_FAILED = new SessionState("CLOSED_LOGIN_FAILED", 5, Category.CLOSED_CATEGORY);
    CLOSED = new SessionState("CLOSED", 6, Category.CLOSED_CATEGORY);
    SessionState[] arrayOfSessionState = new SessionState[7];
    arrayOfSessionState[0] = CREATED;
    arrayOfSessionState[1] = CREATED_TOKEN_LOADED;
    arrayOfSessionState[2] = OPENING;
    arrayOfSessionState[3] = OPENED;
    arrayOfSessionState[4] = OPENED_TOKEN_UPDATED;
    arrayOfSessionState[5] = CLOSED_LOGIN_FAILED;
    arrayOfSessionState[6] = CLOSED;
    $VALUES = arrayOfSessionState;
  }
  
  private SessionState(Category paramCategory)
  {
    this.category = paramCategory;
  }
  
  public boolean isClosed()
  {
    return this.category == Category.CLOSED_CATEGORY;
  }
  
  public boolean isOpened()
  {
    return this.category == Category.OPENED_CATEGORY;
  }
  
  private static enum Category
  {
    static
    {
      CLOSED_CATEGORY = new Category("CLOSED_CATEGORY", 2);
      Category[] arrayOfCategory = new Category[3];
      arrayOfCategory[0] = CREATED_CATEGORY;
      arrayOfCategory[1] = OPENED_CATEGORY;
      arrayOfCategory[2] = CLOSED_CATEGORY;
      $VALUES = arrayOfCategory;
    }
    
    private Category() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.SessionState
 * JD-Core Version:    0.7.0.1
 */