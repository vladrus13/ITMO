package msqueue;

import kotlinx.atomicfu.AtomicRef;

public class MSQueue implements Queue {
    private final AtomicRef<Node> head;
    private final AtomicRef<Node> tail;

    public MSQueue() {
        Node dummy = new Node(0);
        this.head = new AtomicRef<>(dummy);
        this.tail = new AtomicRef<>(dummy);
    }

    @Override
    public void enqueue(int x) {
        Node newTail = new Node(x);
        while (true) {
            Node tail = this.tail.getValue();
            if (tail.next.compareAndSet(null, newTail)) {
                this.tail.compareAndSet(tail, newTail);
                break;
            } else {
                this.tail.compareAndSet(tail, tail.next.getValue());
            }
        }
    }

    @Override
    public int dequeue() {
        int result;
        while (true) {
            Node current = head.getValue();
            Node next = current.next.getValue();
            if (next == null) {
                result = Integer.MIN_VALUE;
                break;
            }
            if (head.compareAndSet(current, next)) {
                result = next.x;
                break;
            }
        }
        return result;
    }

    @Override
    public int peek() {
        Node next = head.getValue().next.getValue();
        if (next == null) {
            return Integer.MIN_VALUE;
        }
        return next.x;
    }

    private static class Node {
        final int x;
        AtomicRef<Node> next;

        Node(int x) {
            this.x = x;
            next = new AtomicRef<>(null);
        }
    }
}