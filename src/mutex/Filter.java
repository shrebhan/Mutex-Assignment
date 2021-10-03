/*
 * Filter.java
 *
 * Created on January 21, 2006, 9:35 AM
 *
 * From "Multiprocessor Synchronization and Concurrent Data Structures",
 * by Maurice Herlihy and Nir Shavit.
 * Copyright 2006 Elsevier Inc. All rights reserved.
 */

package mutex;

/**
 * Peterson lock for multiple threads
 * @author Maurice Herlihy
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Filter implements Lock {
    
    private AtomicInteger[] level;
    private AtomicInteger[] victim;
    private int threads;
    
    /**
     * Constructor for Filter Class
     * @param threads 
     */
  public Filter(int threads) {
      this.threads = threads;
      level  = new AtomicInteger[threads]; // n-1 waiting rooms
      victim = new AtomicInteger[threads]; 
      for(int i = 0; i < threads; i++){
          level[i] = new AtomicInteger();
          victim[i] = new AtomicInteger();
      }
  }
  
  /**
   * API for lock acquisition
   */
  public void lock() {
      int me = ThreadID.get();
      
      //attempt at level i
      for(int i = 0; i < threads; i++){
          level[me].set(i);
          victim[i].set(me);
          
          for (int j = 0; j < threads; j++){             
              // If more than one thread is trying to enter level l,
              // then at least one is blocked (i.e., continues to wait at that level).
              while ((j != me) && ((level[i].get() >= i) && (victim[i].get() == me))) {
                    //wait
                }
          }
          
      }
  }
  // Is there another thread at the same or higher level?
  
  /**
   * API for lock release
   */
  public void unlock() {
      int me = ThreadID.get();
      level[me].set(0);
  }
  
  // Any class implementing Lock must provide these methods
  public Condition newCondition() {
    throw new java.lang.UnsupportedOperationException();
  }
  public boolean tryLock(long time,
      TimeUnit unit)
      throws InterruptedException {
    throw new java.lang.UnsupportedOperationException();
  }
  public boolean tryLock() {
    throw new java.lang.UnsupportedOperationException();
  }
  public void lockInterruptibly() throws InterruptedException {
    throw new java.lang.UnsupportedOperationException();
  }
  
}


