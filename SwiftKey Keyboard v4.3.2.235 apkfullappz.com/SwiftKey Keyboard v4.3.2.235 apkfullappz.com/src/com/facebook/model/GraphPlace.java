package com.facebook.model;

public abstract interface GraphPlace
  extends GraphObject
{
  public abstract String getCategory();
  
  public abstract String getId();
  
  public abstract GraphLocation getLocation();
  
  public abstract String getName();
  
  public abstract void setCategory(String paramString);
  
  public abstract void setId(String paramString);
  
  public abstract void setLocation(GraphLocation paramGraphLocation);
  
  public abstract void setName(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.model.GraphPlace
 * JD-Core Version:    0.7.0.1
 */