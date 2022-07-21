import org.junit.Test;

import java.util.ArrayDeque;

public class TestDeque {
    @Test
    public void testDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        System.out.println(deque.size());
        deque.pollLast();
        System.out.println(deque.size());
        deque.pollLast();
        System.out.println(deque.size());
    }
}
