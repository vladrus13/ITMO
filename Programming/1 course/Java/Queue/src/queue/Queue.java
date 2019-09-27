package queue;

public interface Queue extends Copiable {
    /*public*/ void enqueue(Object element);

    /*public*/ Object dequeue();

    /*public*/ Object element();

    /*public*/ int size();

    /*public*/ boolean isEmpty();

    /*pulbic*/ void clear();

    Queue makeCopy();
}