import queue.ArrayQueue;
import queue.ArrayQueueADT;
import queue.ArrayQueueModule;

public class Tester {
    public static void run(ArrayQueueModule q) {
        ArrayQueueModule queue = q.makeCopy();
        queue.enqueue("Hate");
        queue.enqueue("I");
        queue.enqueue("World");
        queue.enqueue("?");
        queue.dequeue();
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.dequeue());
        }
        queue.enqueue("JAVA HAHA");
        queue.clear();
        queue.enqueue("JAVA     ");
        System.out.print(queue.size());
        System.out.print(queue.isEmpty());
    }

    public static void run(ArrayQueueADT q) {
        ArrayQueueADT queue = q.makeCopy(q);
        queue.enqueue(queue,"Hate");
        queue.enqueue(queue,"I");
        queue.enqueue(queue,"World");
        queue.enqueue(queue,"?");
        queue.dequeue(queue);
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.dequeue(queue));
        }
        queue.enqueue(queue,"JAVA HAHA");
        queue.clear(queue);
        queue.enqueue(queue,"JAVA     ");
        System.out.print(queue.size(queue));
        System.out.print(queue.isEmpty(queue));
    }

    public static void run(ArrayQueue q) {
        ArrayQueue queue = q.makeCopy();
        queue.enqueue("Hate");
        queue.enqueue("I");
        queue.enqueue("World");
        queue.enqueue("?");
        queue.dequeue();
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.dequeue());
        }
        queue.enqueue("JAVA HAHA");
        queue.clear();
        queue.enqueue("JAVA     ");
        System.out.print(queue.size());
        System.out.print(queue.isEmpty());
    }
}
