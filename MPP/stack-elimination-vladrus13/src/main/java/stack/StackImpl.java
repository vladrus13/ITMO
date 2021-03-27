package stack;

import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicRef;

import java.util.ArrayList;

public class StackImpl implements Stack {
    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x, Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }
    }

    private final AtomicRef<Node> head = new AtomicRef<>(null);
    private final int STACK_SIZE = 64;
    private final int TIME_TO_ELIMINATE = 300;
    private final AtomicArray<Integer> stack = new AtomicArray<>(STACK_SIZE);

    @Override
    public void push(int x) {
        for (int i = 0; i < STACK_SIZE; i++) {
            Integer insertValue = x;
            if (stack.get(i).compareAndSet(null, insertValue)) {
                long time = System.currentTimeMillis();
                for (int j = 0; j < TIME_TO_ELIMINATE; j++) {
                    Integer currentValue = stack.get(i).getValue();
                    if (currentValue == null || currentValue != x) {
                        return;
                    }
                }
                if (stack.get(i).compareAndSet(insertValue, null)) {
                    break;
                }
                return;
            }
        }
        while (true) {
            Node h = head.getValue();
            Node newHead = new Node(x, h);
            if (head.compareAndSet(h, newHead)) {
                return;
            }
        }
    }

    @Override
    public int pop() {
        for (int i = 0; i < STACK_SIZE; i++) {
            Integer value = stack.get(i).getValue();
            if (value != null && stack.get(i).compareAndSet(value, null)) {
                return value;
            }
        }
        while (true) {
            Node h = head.getValue();
            if (h == null) {
                return Integer.MIN_VALUE;
            }
            if (head.compareAndSet(h, h.next.getValue())) {
                return h.x;
            }
        }
    }
}
