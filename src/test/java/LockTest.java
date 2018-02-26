import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Created by wenchao.huang on 2017/7/17.
 */
public class LockTest {

    static Lock lock = new ReentrantLock();

    public void method1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" in method1 ");

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"  out method1 ");
        }
        finally {
            lock.unlock();
        }
    }


    public void method2() throws InterruptedException {
        if(lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + "in method2");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "out method2 ");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("线程 " + Thread.currentThread().getName()+"放弃了对锁的获取...");
        }

    }

    public static void main(String[] args) {

        LockTest lockTest = new LockTest();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    lockTest.method1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lockTest.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
