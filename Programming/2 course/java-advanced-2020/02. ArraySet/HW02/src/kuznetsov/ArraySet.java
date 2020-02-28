package kuznetsov;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements NavigableSet<E> {

    private final List<E> list;
    private final Comparator<? super E> comparator;

    public ArraySet() {
        this.list = new ArrayList<>();
        comparator = null;
    }

    // PRE:  comparator (maybe, null), a, b (maybe, !instanceof Comparable)
    // POST: return < 0, if uncomparated
    //       else return a compare b
    //       if !instanceof Comparable - return -1
    @SuppressWarnings("unchecked")
    private int compare(E a, E b, Comparator<? super E> comparator) {
        if (comparator == null && a instanceof Comparable) {
            return ((Comparable<E>) a).compareTo(b);
        }
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return -1;
    }

    // POST: return true, if collection[i] < collection[j] for all i < j
    //       else return false
    private boolean isSorted(Collection<? extends E> collection, Comparator<? super E> comparator) {
        Iterator<? extends E> it = collection.iterator();
        if (!it.hasNext()) return true;
        E last = it.next();
        E curr;
        while (it.hasNext()) {
            curr = it.next();
            if (compare(curr, last, comparator) <= 0) {
                return false;
            }
            last = curr;
        }
        return true;
    }

    public ArraySet(Collection<? extends E> collection) {
        comparator = null;
        if (isSorted(collection, null)) {
            list = List.copyOf(collection);
        } else {
            list = new ArrayList<>(new TreeSet<>(collection));
        }
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        this.comparator = comparator;
        if (isSorted(collection, comparator)) {
            this.list = List.copyOf(collection);
        } else {
            NavigableSet<E> real = new TreeSet<>(comparator);
            real.addAll(collection);
            this.list = new ArrayList<>(real);
        }
    }

    public ArraySet(Comparator<? super E> comparator) {
        list = new ArrayList<>();
        this.comparator = comparator;
    }

    // PRE: List is sorted
    private ArraySet(List<E> list, Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.list = list;
        if (list instanceof ReverseList) {
            ((ReverseList<E>) list).setReverse(!((ReverseList<E>) list).isReverse());
        }
    }

    // PRE: e type E. integer ifFound, ifNotFound
    private int search(E e, int ifFound, int ifNotFound) {
        Objects.requireNonNull(e);
        int index = Collections.binarySearch(list, e, comparator);
        if (index < 0) {
            // e not founded
            index = -index - 1 + ifNotFound;
            return index >= 0 && index < list.size() ? index : -1;
        }
        index += ifFound;
        return index >= 0 && index < list.size() ? index : -1;
    }

    private E searchE(E e, int ifFound, int ifNotFound) {
        int index = search(e, ifFound, ifNotFound);
        return (index >= 0 && index < list.size() ? list.get(index) : null);
    }

    @Override
    public E lower(E e) {
        return searchE(e, -1, -1);
    }

    @Override
    public E floor(E e) {
        return searchE(e, 0, -1);
    }

    @Override
    public E ceiling(E e) {
        return searchE(e, 0, 0);
    }

    @Override
    public E higher(E e) {
        return searchE(e, 1, 0);
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException("Unsupported pollFirst method");
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException("Unsupported pollLast method");
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<E> iterator() {
        return Collections.unmodifiableList(list).iterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ArraySet<>(new ReverseList<>(list), Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        int fromI = fromInclusive ? search(fromElement, 0, 0) : search(fromElement, 1, 0);
        int toI = toInclusive ? search(toElement, 0, -1) : search(toElement, -1, -1);
        if (fromI > toI || fromI == -1 || toI == -1) {
            return new ArraySet<>(comparator);
        }
        return new ArraySet<>(list.subList(fromI, toI + 1), comparator);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        if (list.isEmpty()) {
            return new ArraySet<>(comparator);
        }
        return subSet(first(), true, toElement, inclusive);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        if (list.isEmpty()) {
            return new ArraySet<>(comparator);
        }
        return subSet(fromElement, inclusive, last(), true);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        if (compare(fromElement, toElement, comparator) > 0) {
            throw new IllegalArgumentException(String.format("%s higher than %s", fromElement.toString(), toElement.toString()));
        }
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public E first() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.get(0);
    }

    @Override
    public E last() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.get(list.size() - 1);
    }

    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        Objects.requireNonNull(o);
        return Collections.binarySearch(list, (E) o, comparator) >= 0;
    }
}
