package queue;

import java.util.Arrays;

public class ArrayQueueModule {
    // size - the size of elements, num - number adding (what) elements
    private static int size = 2, num = 0;
    private static Object[] elements = new Object[2];
    private static int tail = 0, head = 0;

    // pre: size, num, tail, head in N
    // pre: elements - Objects[]
    // post: num = num'
    // if (num == size)
    //     post: size = 2 * size', elements = elements' + size * '0', head = num, tail = 0
    // else
    //     post: size = size' elements = elements', head = head', tail = tail'
    private static void resize_up() {
        if (num < size) return;
        Object[] newQueue = new Object[size * 2];
        int it = 0;
        for (int i = tail; i != head || it == 0; i = (i + 1) % size, it++) {
            newQueue[it] = elements[i];
        }
        elements = newQueue;
        size *= 2;
        head = it;
        tail = 0;
    }

    // pre: size, num, tail, head in N
    // pre: element - Object
    // pre: elements - Object[]
    // post: elements - Object[] + element, size = (size == num ? size * 2: size, head = head' + 1
    public static void enqueue(Object element) {
        if (element == null) {
            // Warnings.send(0001);
            assert false;
        }
        resize_up();
        elements[head] = element;
        num++;
        head = (head + 1) % size;
    }

    // pre: size, num, tail, head in N
    // pre: element - Object
    // pre: elements - Object[]
    // post: elements - element + Object[], size = (size == num ? size * 2: size, tail = tail' - 1
    public static void push(Object element) {
        if (element == null) {
            // Warnings.send(0001);
            assert false;
        }
        resize_up();
        tail = (tail - 1 + size) % size;
        elements[tail] = element;
        num++;
    }

    // pre: NOTHING
    // post: R = element[tail]
    public static Object element() {
        return elements[tail];
    }

    // pre: NOTHING
    // post: R = peek of elements
    public static Object peek() {
        return elements[(head - 1 + size) % size];
    }

    // pre: elements - Object[]
    // post: R = first in queue, delete R (tail = tail' + 1)
    public static Object dequeue() {
        if (num == 0) {
            // Warnings.send(0002);
            assert false;
        }
        Object answer = element();
        tail = (tail + 1) % size;
        num--;
        return answer;
    }

    // pre: elements - Object[]
    // post: R = last in queue, delete R (head = head' - 1)
    public static Object remove() {
        if (num == 0) {
            // Warnings.send(0002);
            assert false;
        }
        Object answer = peek();
        head = (head - 1 + size) % size;
        num--;
        return answer;
    }

    // pre: NOTHING
    // post: size of elements
    public static int size() {
        return num;
    }

    // pre: NOTHING
    // post: boolean: (empty elements)
    public static boolean isEmpty() {
        return num == 0;
    }

    // pre: ArrayQueue s
    // post: s = empty
    public static void clear() {
        size = 2;
        num = 0;
        elements = new Object[2];
        tail = 0;
        head = 0;
    }

    // pre: ArrayQueue s
    // post s' = s
    public static ArrayQueueModule makeCopy() {
        final ArrayQueueModule copy = new ArrayQueueModule();
        copy.size = size;
        copy.head = head;
        copy.num = num;
        copy.tail = tail;
        copy.elements = Arrays.copyOf(elements, size);
        return copy;
    }
}
