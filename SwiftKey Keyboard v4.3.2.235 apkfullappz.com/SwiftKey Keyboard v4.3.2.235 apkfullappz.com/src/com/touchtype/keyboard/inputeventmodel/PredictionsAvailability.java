package com.touchtype.keyboard.inputeventmodel;

public enum PredictionsAvailability
{
  static
  {
    DISABLED = new PredictionsAvailability("DISABLED", 1);
    UNAVAILABLE_NO_SD_CARD = new PredictionsAvailability("UNAVAILABLE_NO_SD_CARD", 2);
    PredictionsAvailability[] arrayOfPredictionsAvailability = new PredictionsAvailability[3];
    arrayOfPredictionsAvailability[0] = ENABLED;
    arrayOfPredictionsAvailability[1] = DISABLED;
    arrayOfPredictionsAvailability[2] = UNAVAILABLE_NO_SD_CARD;
    $VALUES = arrayOfPredictionsAvailability;
  }
  
  private PredictionsAvailability() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.PredictionsAvailability
 * JD-Core Version:    0.7.0.1
 */