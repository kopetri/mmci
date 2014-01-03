package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.Freezable;

public abstract interface Person
  extends Freezable
{
  public static abstract interface AgeRange
    extends Freezable
  {}
  
  public static abstract interface Cover
    extends Freezable
  {
    public static abstract interface CoverInfo
      extends Freezable
    {}
    
    public static abstract interface CoverPhoto
      extends Freezable
    {}
  }
  
  public static abstract interface Emails
    extends Freezable
  {}
  
  public static abstract interface Image
    extends Freezable
  {}
  
  public static abstract interface Name
    extends Freezable
  {}
  
  public static abstract interface Organizations
    extends Freezable
  {}
  
  public static abstract interface PlacesLived
    extends Freezable
  {}
  
  public static abstract interface Urls
    extends Freezable
  {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.plus.model.people.Person
 * JD-Core Version:    0.7.0.1
 */