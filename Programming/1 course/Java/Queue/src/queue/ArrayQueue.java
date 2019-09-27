package queue;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue implements Copiable {
    private int num = 2;
    private Object[] elements = new Object[2];
    private int tail = 0, head = 0;

    // pre: size, num, tail, head in N
    // pre: elements - Objects[]
    // post: num = num'
    // if (num == size)
    //     post: size = 2 * size', elements = elements' + size * '0', head = num, tail = 0
    // else
    //     post: size = size' elements = elements', head = head', tail = tail'
    private void resize_up() {
        if (size < num) return;
        Object[] newQueue = new Object[num * 2];
        int it = 0;
        for (int i = tail; i != head || it == 0; i = (i + 1) % num, it++) {
            newQueue[it] = elements[i];
        }
        elements = newQueue;
        num *= 2;
        head = it;
        tail = 0;
    }

    // pre: size, num, tail, head in N
    // pre: element - Object
    // pre: elements - Object[]
    // post: elements - element + Object[], size = (size == num ? size * 2: size
    protected void push(Object element) {
        resize_up();
        tail = (tail - 1 + num) % num;
        elements[tail] = element;
    }

    protected Object peek() {
        return elements[(head - 1 + num) % num];
    }

    // pre: elements - Object[]
    // post: R = last in queue, delete R
    protected void remove() {
        head = (head - 1 + num) % num;
    }

    // pre: ArrayQueue s
    // post: s = empty
    public void clear() {
        num = 2;
        size = 0;
        elements = new Object[2];
        tail = 0;
        head = 0;
    }

    // pre: ArrayQueue s
    // post s' = s
    public ArrayQueue makeCopy() {
        final ArrayQueue copy = new ArrayQueue();
        copy.size = size;
        copy.head = head;
        copy.num = num;
        copy.tail = tail;
        copy.elements = Arrays.copyOf(elements, size);
        return copy;
    }
}
