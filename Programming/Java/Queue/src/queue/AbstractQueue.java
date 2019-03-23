package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue, Copiable {
    protected int size = 0;

    // pre: head, size
    // post: head = head' + {element}, size = size' + 1
    public void enqueue(Object element) {
        assert element != null;
        push(element);
        size++;
    }

    protected abstract void push(Object element);

    public abstract AbstractQueue makeCopy();

    // pre: NOTHING
    // post: R = peek of elements
    protected abstract Object peek();

    // pre: head, size
    // post: head value
    public Object element() {
        assert size > 0;
        return peek();
    }

    // pre: head, size
    // post: head = head' \ {last}, size = size' - 1
    public Object dequeue() {
        assert size > 0;
        Object result = element();
        remove();
        size--;
        return result;
    }

    protected abstract void remove();

    // pre: head, size
    // post: Z = size
    public int size() {
        return size;
    }

    // pre: head, size
    // post: boolean = size == 0
    public boolean isEmpty() {
        return size == 0;
    }

    public AbstractQueue filter(Predicate f) {
        AbstractQueue copy = makeCopy();
        AbstractQueue ready = makeCopy();
        ready.clear();
        Object temp;
        while (!copy.isEmpty()) {
            temp = copy.dequeue();
            if (f.test(temp)) {
                ready.enqueue(temp);
            }
        }
        return ready;
    }

    public AbstractQueue map(Function f) {
        AbstractQueue copy = makeCopy();
        AbstractQueue ready = makeCopy();
        ready.clear();
        Object temp;
        while (!copy.isEmpty()) {
            temp = copy.dequeue();
            ready.enqueue(f.apply(temp));
        }
        return ready;
    }
}
