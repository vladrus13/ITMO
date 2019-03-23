import queue.LinkedQueue;

public class Basic {
    public static void main(String[] argm) {
        LinkedQueue q = new LinkedQueue();
        q.enqueue(1);
        q.enqueue(2);
        System.out.print(q.element());
        System.out.print(q.dequeue());
        System.out.print(q.element());
    }
}
