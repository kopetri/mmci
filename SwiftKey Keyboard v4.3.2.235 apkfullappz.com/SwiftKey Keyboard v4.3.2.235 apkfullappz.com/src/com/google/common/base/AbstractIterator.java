package com.google.common.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class AbstractIterator<T>
  implements Iterator<T>
{
  private T next;
  private State state = State.NOT_READY;
  
  private boolean tryToComputeNext()
  {
    this.state = State.FAILED;
    this.next = computeNext();
    if (this.state != State.DONE)
    {
      this.state = State.READY;
      return true;
    }
    return false;
  }
  
  protected abstract T computeNext();
  
  protected final T endOfData()
  {
    this.state = State.DONE;
    return null;
  }
  
  public final boolean hasNext()
  {
    if (this.state != State.FAILED) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      Preconditions.checkState(bool1);
      int i = 1.$SwitchMap$com$google$common$base$AbstractIterator$State[this.state.ordinal()];
      boolean bool2 = false;
      switch (i)
      {
      default: 
        bool2 = tryToComputeNext();
      case 1: 
        return bool2;
      }
    }
    return true;
  }
  
  public final T next()
  {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    this.state = State.NOT_READY;
    return this.next;
  }
  
  public final void remove()
  {
    throw new UnsupportedOperationException();
  }
  
  private static enum State
  {
    static
    {
      NOT_READY = new State("NOT_READY", 1);
      DONE = new State("DONE", 2);
      FAILED = new State("FAILED", 3);
      State[] arrayOfState = new State[4];
      arrayOfState[0] = READY;
      arrayOfState[1] = NOT_READY;
      arrayOfState[2] = DONE;
      arrayOfState[3] = FAILED;
      $VALUES = arrayOfState;
    }
    
    private State() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.base.AbstractIterator
 * JD-Core Version:    0.7.0.1
 */