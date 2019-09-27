package queue;

import java.util.Arrays;

public class ArrayQueueADT {
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
    private static void resize_up(ArrayQueueADT queue) {
        if (queue.num < queue.size) return;
        Object[] newQueue = new Object[queue.size * 2];
        int it = 0;
        for (int i = queue.tail; i != queue.head || it == 0; i = (i + 1) % queue.size, it++) {
            newQueue[it] = queue.elements[i];
        }
        queue.elements = newQueue;
        queue.size *= 2;
        queue.head = it;
        queue.tail = 0;
    }

    // pre: size, num, tail, head in N
    // pre: element - Object
    // pre: elements - Object[]
    // post: elements - element + Object[], size = (size == num ? size * 2: size)
    public static void push(ArrayQueueADT queue, Object element) {
        if (element == null) {
            // Warnings.send(0001);
            assert false;
        }
        resize_up(queue);
        queue.tail = (queue.tail - 1 + queue.size) % queue.size;
        queue.elements[queue.tail] = element;
        queue.num++;
    }

    // pre: NOTHING
    // post: R = peek of elements
    public static Object peek(ArrayQueueADT queue) {
        return queue.elements[(queue.head - 1 + queue.size) % queue.size];
    }

    // pre: elements - Object[]
    // post: R = last in queue, delete R
    public static Object remove(ArrayQueueADT queue) {
        if (queue.num == 0) {
            // Warnings.send(0002);
            assert false;
        }
        Object answer = peek(queue);
        queue.head = (queue.head - 1 + queue.size) % queue.size;
        queue.num--;
        return answer;
    }

    // pre: size, num, tail, head in N
    // pre: element - Object
    // pre: elements - Object[]
    // post: elements - Object[] + element, size = (size == num ? size * 2: size
    public static void enqueue(ArrayQueueADT queue, Object element) {
        if (element == null) {
            // Warnings.send(0001);
            assert false;
        }
        resize_up(queue);
        queue.elements[head] = element;
        queue.num++;
        queue.head = (queue.head + 1) % queue.size;
    }

    // pre: NOTHING
    // post: R = element[tail]
    public static Object element(ArrayQueueADT queue) {
        return queue.elements[tail];
    }

    // pre: elements - Object[]
    // post: R = first in queue, delete R
    public static Object dequeue(ArrayQueueADT queue) {
        if (queue.num == 0) {
            // Warnings.send(0002);
            assert false;
        }
        Object answer = element(queue);
        queue.tail = (queue.tail + 1) % queue.size;
        queue.num--;
        return answer;
    }

    // pre: NOTHING
    // post: size of elements
    public static int size(ArrayQueueADT queue) {
        return queue.num;
    }

    // pre: NOTHING
    // post: boolean: (empty elements)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.num == 0;
    }

    // pre: ArrayQueue s
    // post: s = empty
    public static void clear(ArrayQueueADT queue) {
        queue.size = 2;
        queue.num = 0;
        queue.elements = new Object[2];
        queue.tail = 0;
        queue.head = 0;
    }

    // pre: ArrayQueue s
    // post s' = s
    public static ArrayQueueADT makeCopy(ArrayQueueADT queue) {
        final ArrayQueueADT copy = new ArrayQueueADT();
        copy.size = queue.size;
        copy.head = queue.head;
        copy.num = queue.num;
        copy.tail = queue.tail;
        copy.elements = Arrays.copyOf(queue.elements, queue.size);
        return copy;
    }

}
