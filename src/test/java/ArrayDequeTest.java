import java.util.ArrayDeque;
import java.util.Deque;
/**
 * Created by wenchao.huang on 2017/9/26.
 */
public class ArrayDequeTest {

    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();

        deque.add("1111");

        String first = deque.getFirst();
        System.out.println(first);

        deque.addFirst("2222");
        deque.addLast("3333");
        System.out.println(deque);

        deque.offer("444");
        System.out.println(deque);

        deque.offerFirst("555");
        System.out.println(deque);

        deque.offerLast("666");
        System.out.println(deque);

    }
}
