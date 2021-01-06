package com.gas.web.util;

import javax.sound.sampled.FloatControl;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PauseUtil {
    public static Lock lock = new ReentrantLock();//锁对象
    public static Condition pause = lock.newCondition();
    public static boolean pauseFlag = false;

    public static void pauseThread() throws InterruptedException {
        lock.lock();
        while (pauseFlag) {
            pause.await();
        }
        lock.unlock();
    }

    public static void continueThread() {
        lock.lock();
        pause.signal();
        lock.unlock();
    }

    public static void setPauseFlag() {
        lock.lock();
        pauseFlag = true;
        lock.unlock();
    }

    public static void resetPauseFlag() {
        lock.lock();
        pauseFlag = false;
        lock.unlock();
    }

}
