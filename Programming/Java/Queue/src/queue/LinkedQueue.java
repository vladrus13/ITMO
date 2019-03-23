package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue implements Copiable {
    private Node head = null;
    private Node tail = head;

    // pre: Node
    // post: Node' = {element, Node}
    protected void push(Object element) {
        if (size == 0) {
            head = new Node(element, head);
            tail = head;
        } else {
            head = new Node(element, head);
        }
    }

    // pre: NOTHING
    // post: peek
    protected Object peek() {
        return tail.value();
    }

    // pre: Node = {element, Child}
    // post: Node' = Child
    protected void remove() {
        tail = tail.next();
    }

    // pre: head, size;
    // post: head = null head, size = 0
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    // pre: ArrayQueue s
    // post s' = s
    public LinkedQueue makeCopy() {
        final LinkedQueue copy = new LinkedQueue();
        copy.size = size;
        copy.head = head;
        copy.tail = tail;
        return copy;
    }


}
