package linked_list_set;

import kotlinx.atomicfu.AtomicRef;

public class SetImpl implements Set {
    private static class Node {
        public AtomicRef<ReferenceNode> next;
        public int x;

        public Node(int x, Node next) {
            this.next = new AtomicRef<>(new ReferenceNode(next));
            this.x = x;
        }
    }

    private static class ReferenceNode {
        private final Node next;
        private final boolean removed;

        private ReferenceNode(Node next, boolean removed) {
            this.next = next;
            this.removed = removed;
        }

        public ReferenceNode(Node next) {
            this.next = next;
            removed = false;
        }

        public Node getNext() {
            return next;
        }

        public boolean isRemoved() {
            return removed;
        }
    }

    private static class Window {
        protected Node cur, next;

        public Window(Node cur, Node next) {
            this.cur = cur;
            this.next = next;
        }
    }

    private final Node head = new Node(Integer.MIN_VALUE, new Node(Integer.MAX_VALUE, null));

    /**
     * Returns the {@link Window}, where cur.x < x <= next.x
     */
    private Window findWindow(int x) {
        while (true) {
            boolean isFailed = false;
            Node left = head;
            Node right = left.next.getValue().getNext();
            while (right.x < x) {
                ReferenceNode leftReference = left.next.getValue();
                ReferenceNode rightReference = right.next.getValue();
                if (leftReference.isRemoved() || leftReference.getNext() != right) {
                    isFailed = true;
                    break;
                }
                if (rightReference.isRemoved()) {
                    ReferenceNode newLeftReference = new ReferenceNode(rightReference.getNext());
                    if (!left.next.compareAndSet(leftReference, newLeftReference)) {
                        isFailed = true;
                        break;
                    }
                    right = newLeftReference.getNext();
                } else {
                    left = right;
                    right = left.next.getValue().getNext();
                }
            }
            if (!isFailed) {
                ReferenceNode leftReference = left.next.getValue();
                ReferenceNode rightReference = right.next.getValue();
                if (leftReference.next != right || leftReference.isRemoved()) {
                    continue;
                }
                if (rightReference.isRemoved()) {
                    ReferenceNode newLeftReference = new ReferenceNode(rightReference.getNext());
                    left.next.compareAndSet(leftReference, newLeftReference);
                    continue;
                }
                return new Window(left, right);
            }
        }
    }

    @Override
    public boolean add(int x) {
        while (true) {
            Window w = findWindow(x);
            boolean res;
            if (w.next.x == x) {
                res = false;
            } else {
                ReferenceNode left = w.cur.next.getValue();
                if (left.getNext() != w.next || left.isRemoved()) {
                    continue;
                }
                Node node = new Node(x, w.next);
                ReferenceNode newLeft = new ReferenceNode(node);
                if (w.cur.next.compareAndSet(left, newLeft)) {
                    res = true;
                } else {
                    continue;
                }
            }
            return res;
        }
    }

    @Override
    public boolean remove(int x) {
        while (true) {
            Window w = findWindow(x);
            boolean res;
            if (w.next.x != x) {
                res = false;
            } else {
                ReferenceNode right = w.next.next.getValue();
                if (right.isRemoved()) {
                    continue;
                }
                ReferenceNode newRight = new ReferenceNode(right.next, true);
                if (w.next.next.compareAndSet(right, newRight)) {
                    res = true;
                } else {
                    continue;
                }
            }
            return res;
        }
    }

    @Override
    public boolean contains(int x) {
        Window w = findWindow(x);
        return w.next.x == x;
    }
}