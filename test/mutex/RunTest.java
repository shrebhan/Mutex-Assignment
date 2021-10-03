/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mutex;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 *
 * @author admin
 */
public class RunTest {
    public static void main(String args[]){
        
        Result resultBakery = JUnitCore.runClasses(BakeryTest.class);
        RunListener createListener = resultBakery.createListener();
        for (Failure failure : resultBakery.getFailures())
        {
                System.out.println(failure.toString());
        }
        
        Result resultFilter = JUnitCore.runClasses(FilterTest.class);
        for (Failure failure : resultFilter.getFailures())
        {
                System.out.println(failure.toString());
        }
        
        Result resultLock = JUnitCore.runClasses(LockTest.class);
        for (Failure failure : resultLock.getFailures())
        {
                System.out.println(failure.toString());
        }
    }
 }

