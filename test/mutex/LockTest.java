/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mutex;

import java.util.concurrent.locks.ReentrantLock;
import junit.framework.Test;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import junit.framework.TestSuite;

/**
 *
 * @author admin
 */
public class LockTest extends TestCase {
    private final static int THREADS = 8;
  private final static int COUNT = 1024;
  private final static int PER_THREAD = COUNT / THREADS;
  Thread[] thread = new Thread[THREADS];
  long[] timeOfExecution = new long[THREADS];
  int counter = 0;
  ReentrantLock instance = new ReentrantLock();
  public LockTest(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(FilterTest.class);
    return suite;
  }
  
  public void testParallel() throws Exception {
    ThreadID.reset();
    
    for (int i = 0; i < THREADS; i++) {
      thread[i] = new MyThread();
    }
    
    for (int i = 0; i < THREADS; i++) {
      timeOfExecution[i] = System.nanoTime();
      thread[i].start();
    }
    for (int i = 0; i < THREADS; i++) {
      thread[i].join();
      timeOfExecution[i] = System.nanoTime() - timeOfExecution[i];
    }
    System.out.println(meanTimeOfExecution(timeOfExecution, THREADS));
    assertEquals(counter, COUNT);
  }
  
  public long meanTimeOfExecution(long[] timeOfExecution, int THREADS){
      long sum = 0;
      for(int i = 0; i< THREADS; i++)
          sum += timeOfExecution[i];
      return (long)sum/THREADS;
  }
  class MyThread extends Thread {
    public void run() {
      for (int i = 0; i < PER_THREAD; i++) {
        instance.lock();
        try {
          counter = counter + 1;
        } finally {
          instance.unlock();
        }
      }
    }
  }
    
}
