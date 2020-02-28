package kuznetsov;

import java.util.AbstractList;
import java.util.List;

public class ReverseList<E> extends AbstractList<E> {

    private final List<E> list;
    private boolean isReverse;

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    public boolean isReverse() {
        return isReverse;
    }

    ReverseList(List<E> list) {
        this.list = list;
    }

    public E get(int index) {
        return !isReverse ? list.get(index) : list.get(size() - index - 1);
    }

    @Override
    public int size() {
        return list.size();
    }
}
