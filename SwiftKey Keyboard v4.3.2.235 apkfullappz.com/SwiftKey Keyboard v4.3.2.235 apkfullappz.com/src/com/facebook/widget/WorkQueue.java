package com.facebook.widget;

import com.facebook.Settings;
import java.util.concurrent.Executor;

class WorkQueue
{
  public static final int DEFAULT_MAX_CONCURRENT = 8;
  private final Executor executor;
  private final int maxConcurrent;
  private WorkNode pendingJobs;
  private int runningCount = 0;
  private WorkNode runningJobs = null;
  private final Object workLock = new Object();
  
  static
  {
    if (!WorkQueue.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  WorkQueue()
  {
    this(8);
  }
  
  WorkQueue(int paramInt)
  {
    this(paramInt, Settings.getExecutor());
  }
  
  WorkQueue(int paramInt, Executor paramExecutor)
  {
    this.maxConcurrent = paramInt;
    this.executor = paramExecutor;
  }
  
  private void execute(final WorkNode paramWorkNode)
  {
    this.executor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          paramWorkNode.getCallback().run();
          return;
        }
        finally
        {
          WorkQueue.this.finishItemAndStartNew(paramWorkNode);
        }
      }
    });
  }
  
  private void finishItemAndStartNew(WorkNode paramWorkNode)
  {
    Object localObject1 = this.workLock;
    if (paramWorkNode != null) {}
    try
    {
      this.runningJobs = paramWorkNode.removeFromList(this.runningJobs);
      this.runningCount = (-1 + this.runningCount);
      int i = this.runningCount;
      int j = this.maxConcurrent;
      WorkNode localWorkNode = null;
      if (i < j)
      {
        localWorkNode = this.pendingJobs;
        if (localWorkNode != null)
        {
          this.pendingJobs = localWorkNode.removeFromList(this.pendingJobs);
          this.runningJobs = localWorkNode.addToList(this.runningJobs, false);
          this.runningCount = (1 + this.runningCount);
          localWorkNode.setIsRunning(true);
        }
      }
      if (localWorkNode != null) {
        execute(localWorkNode);
      }
      return;
    }
    finally {}
  }
  
  private void startItem()
  {
    finishItemAndStartNew(null);
  }
  
  WorkItem addActiveWorkItem(Runnable paramRunnable)
  {
    return addActiveWorkItem(paramRunnable, true);
  }
  
  WorkItem addActiveWorkItem(Runnable paramRunnable, boolean paramBoolean)
  {
    WorkNode localWorkNode = new WorkNode(paramRunnable);
    synchronized (this.workLock)
    {
      this.pendingJobs = localWorkNode.addToList(this.pendingJobs, paramBoolean);
      startItem();
      return localWorkNode;
    }
  }
  
  void validate()
  {
    synchronized (this.workLock)
    {
      WorkNode localWorkNode1 = this.runningJobs;
      int i = 0;
      if (localWorkNode1 != null)
      {
        WorkNode localWorkNode2 = this.runningJobs;
        do
        {
          localWorkNode2.verify(true);
          i++;
          localWorkNode2 = localWorkNode2.getNext();
        } while (localWorkNode2 != this.runningJobs);
      }
      if ((!$assertionsDisabled) && (this.runningCount != i)) {
        throw new AssertionError();
      }
    }
  }
  
  static abstract interface WorkItem
  {
    public abstract boolean cancel();
    
    public abstract boolean isRunning();
    
    public abstract void moveToFront();
  }
  
  private class WorkNode
    implements WorkQueue.WorkItem
  {
    private final Runnable callback;
    private boolean isRunning;
    private WorkNode next;
    private WorkNode prev;
    
    static
    {
      if (!WorkQueue.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    WorkNode(Runnable paramRunnable)
    {
      this.callback = paramRunnable;
    }
    
    WorkNode addToList(WorkNode paramWorkNode, boolean paramBoolean)
    {
      assert (this.next == null);
      assert (this.prev == null);
      if (paramWorkNode == null)
      {
        this.prev = this;
        this.next = this;
        paramWorkNode = this;
      }
      while (paramBoolean)
      {
        return this;
        this.next = paramWorkNode;
        this.prev = paramWorkNode.prev;
        WorkNode localWorkNode = this.next;
        this.prev.next = this;
        localWorkNode.prev = this;
      }
      return paramWorkNode;
    }
    
    public boolean cancel()
    {
      synchronized (WorkQueue.this.workLock)
      {
        if (!isRunning())
        {
          WorkQueue.access$202(WorkQueue.this, removeFromList(WorkQueue.this.pendingJobs));
          return true;
        }
        return false;
      }
    }
    
    Runnable getCallback()
    {
      return this.callback;
    }
    
    WorkNode getNext()
    {
      return this.next;
    }
    
    public boolean isRunning()
    {
      return this.isRunning;
    }
    
    public void moveToFront()
    {
      synchronized (WorkQueue.this.workLock)
      {
        if (!isRunning())
        {
          WorkQueue.access$202(WorkQueue.this, removeFromList(WorkQueue.this.pendingJobs));
          WorkQueue.access$202(WorkQueue.this, addToList(WorkQueue.this.pendingJobs, true));
        }
        return;
      }
    }
    
    WorkNode removeFromList(WorkNode paramWorkNode)
    {
      assert (this.next != null);
      assert (this.prev != null);
      if (paramWorkNode == this) {
        if (this.next != this) {
          break label91;
        }
      }
      label91:
      for (paramWorkNode = null;; paramWorkNode = this.next)
      {
        this.next.prev = this.prev;
        this.prev.next = this.next;
        this.prev = null;
        this.next = null;
        return paramWorkNode;
      }
    }
    
    void setIsRunning(boolean paramBoolean)
    {
      this.isRunning = paramBoolean;
    }
    
    void verify(boolean paramBoolean)
    {
      assert (this.prev.next == this);
      assert (this.next.prev == this);
      assert (isRunning() == paramBoolean);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.WorkQueue
 * JD-Core Version:    0.7.0.1
 */