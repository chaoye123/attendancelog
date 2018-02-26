import java.util.concurrent.TimeUnit;
/**
 * 类描述
 *
 * @author huang.wenchao
 */
public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 定义线程1
        Thread thread1 = new Thread() {
            public void run() {
                System.out.println("thread1...");
            }
        };

        // 定义线程2
        Thread thread2 = new Thread() {
            public void run() {
                System.out.println("thread2...");
            }
        };

        // 定义关闭线程
        Thread shutdownThread = new Thread() {
            public void run() {
                System.out.println("shutdownThread...");
            }
        };

        // jvm关闭的时候先执行该线程钩子
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        thread1.start();
        thread2.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
