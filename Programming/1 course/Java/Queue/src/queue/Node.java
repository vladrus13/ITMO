package queue;

public class Node {
    private Object value;
    private Node next;

    public Node(Object value, Node next) {
        this.value = value;
        if (next != null) {
            next.next = this;
        }
    }

    public Object value() {
        return value;
    }

    public Node next() {
        return next;
    }
}
