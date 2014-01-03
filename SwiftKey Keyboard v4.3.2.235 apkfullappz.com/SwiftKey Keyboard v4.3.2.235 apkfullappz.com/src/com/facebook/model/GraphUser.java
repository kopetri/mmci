package com.facebook.model;

public abstract interface GraphUser
  extends GraphObject
{
  public abstract String getBirthday();
  
  public abstract String getFirstName();
  
  public abstract String getId();
  
  public abstract String getLastName();
  
  public abstract String getLink();
  
  public abstract GraphLocation getLocation();
  
  public abstract String getMiddleName();
  
  public abstract String getName();
  
  public abstract String getUsername();
  
  public abstract void setBirthday(String paramString);
  
  public abstract void setFirstName(String paramString);
  
  public abstract void setId(String paramString);
  
  public abstract void setLastName(String paramString);
  
  public abstract void setLink(String paramString);
  
  public abstract void setLocation(GraphLocation paramGraphLocation);
  
  public abstract void setMiddleName(String paramString);
  
  public abstract void setName(String paramString);
  
  public abstract void setUsername(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.model.GraphUser
 * JD-Core Version:    0.7.0.1
 */