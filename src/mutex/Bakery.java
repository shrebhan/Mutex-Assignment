/*
 * Bakery.java
 *
 * Created on January 21, 2006, 1:20 PM
 *
 * From "Multiprocessor Synchronization and Concurrent Data Structures",
 * by Maurice Herlihy and Nir Shavit.
 * Copyright 2006 Elsevier Inc. All rights reserved.
 *
 */

package mutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Lamport's Bakery lock (simplified)
 * @author Maurice Herlihy
 */
class Bakery implements Lock {
    private AtomicBoolean[] flag;
    private AtomicInteger[] label;
    private static int n;

  public Bakery(int threads) {
    this.n = threads;
    flag = new AtomicBoolean[threads];
    label = new AtomicInteger[threads];
    for (int i = 0; i < threads; i++) {
        flag[i] = new AtomicBoolean(); label[i] = new AtomicInteger();
      }
  }
  
  /**
   * Api for lock acquisition
   */
  public void lock() {
    int i = ThreadID.get();
    flag[i].set(true);
    
    int max = 0;
    for (AtomicInteger e : label){
        if(e.get() > max){
            max = e.get();
        }
    }
    label[i].set(max);
    for (int k = 0; k < n; k++) 
        while ((k != i) && flag[k].get() && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i)));
        
  }
  public void unlock() {
        flag[ThreadID.get()].set(false);
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
