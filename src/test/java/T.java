import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.assertj.core.util.Lists;
/**
 * Created by wenchao.huang on 2017/7/17.
 */
public class T {




    public static void main(String[] args) throws InterruptedException {
//        final Lock lock=new ReentrantLock();
//        Thread t1=new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                    lock.lockInterruptibly();
//                } catch (InterruptedException e) {
//                    System.out.println(Thread.currentThread().getName()+" interrupted.");
//                }
//            }
//        });
//        t1.start();
//        t1.interrupt();
//        Thread.sleep(10000);

        ArrayDeque<String> deque = new ArrayDeque();
        deque.add("aaaa1");
        deque.addFirst("bbbb2");
        deque.addLast("cccc");

        System.out.println(deque);


        List<String> list = Lists.newArrayList("a","b","c");
        for(int i = 0; i < list.size(); i++) {

            String temp = list.get(i);
            if("b".equals(temp)){
                list.remove(i);
            }
            System.out.println(temp);

        }

    }


}
