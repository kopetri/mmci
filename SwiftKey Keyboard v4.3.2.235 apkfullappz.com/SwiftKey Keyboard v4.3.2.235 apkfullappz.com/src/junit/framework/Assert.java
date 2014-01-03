package junit.framework;

public final class Assert
{
  public static void assertEquals(int paramInt1, int paramInt2)
  {
    assertEquals(null, paramInt1, paramInt2);
  }
  
  public static void assertEquals(Object paramObject1, Object paramObject2)
  {
    assertEquals(null, paramObject1, paramObject2);
  }
  
  public static void assertEquals(String paramString, int paramInt1, int paramInt2)
  {
    assertEquals(paramString, new Integer(paramInt1), new Integer(paramInt2));
  }
  
  public static void assertEquals(String paramString, Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) && (paramObject2 == null)) {}
    while ((paramObject1 != null) && (paramObject1.equals(paramObject2))) {
      return;
    }
    failNotEquals(paramString, paramObject1, paramObject2);
  }
  
  public static void assertFalse(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      assertTrue(paramString, bool);
      return;
    }
  }
  
  public static void assertFalse(boolean paramBoolean)
  {
    assertFalse(null, paramBoolean);
  }
  
  public static void assertNotNull(Object paramObject)
  {
    assertNotNull(null, paramObject);
  }
  
  public static void assertNotNull(String paramString, Object paramObject)
  {
    if (paramObject != null) {}
    for (boolean bool = true;; bool = false)
    {
      assertTrue(paramString, bool);
      return;
    }
  }
  
  public static void assertNull(Object paramObject)
  {
    assertNull("Expected: <null> but was: " + String.valueOf(paramObject), paramObject);
  }
  
  public static void assertNull(String paramString, Object paramObject)
  {
    if (paramObject == null) {}
    for (boolean bool = true;; bool = false)
    {
      assertTrue(paramString, bool);
      return;
    }
  }
  
  public static void assertTrue(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {
      fail(paramString);
    }
  }
  
  public static void assertTrue(boolean paramBoolean)
  {
    assertTrue(null, paramBoolean);
  }
  
  public static void fail()
  {
    fail(null);
  }
  
  public static void fail(String paramString)
  {
    if (paramString == null) {
      throw new AssertionFailedError();
    }
    throw new AssertionFailedError(paramString);
  }
  
  public static void failNotEquals(String paramString, Object paramObject1, Object paramObject2)
  {
    fail(format(paramString, paramObject1, paramObject2));
  }
  
  public static String format(String paramString, Object paramObject1, Object paramObject2)
  {
    String str = "";
    if ((paramString != null) && (paramString.length() > 0)) {
      str = paramString + " ";
    }
    return str + "expected:<" + paramObject1 + "> but was:<" + paramObject2 + ">";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     junit.framework.Assert
 * JD-Core Version:    0.7.0.1
 */