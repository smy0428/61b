public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;


    //* helper method to increase index circularly */
    private int loopAdd(int a) {
        if (a + 1 == items.length) {
            return 0;
        } else {
            return a + 1;
        }
    }

    //* helper method to decrease index circularly */
    private int loopSub(int a) {
        if (a == 0) {
            return items.length - 1;
        } else {
            return a - 1;
        }
    }


    //* helper method to get the actual index */
    private int actualIndex(int a) {
        int actual = nextFirst + a + 1;
        if (actual >= items.length) {
            actual -= items.length;
        }
        return actual;
    }


    //* helper method to check whether the usage ratio is lower as 0.25 */
    private boolean usageLow() {
        int usageRatio = 100 * size / items.length;
        return (items.length >= 16 && usageRatio < 25);
    }


    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int firstIndex = loopAdd(nextFirst);
        int lastIndex = loopSub(nextLast);

        if (firstIndex < lastIndex) {
            System.arraycopy(items, firstIndex, a, 0, size);
        } else {
            int firstLen = items.length - firstIndex;
            System.arraycopy(items, firstIndex, a, 0, firstLen);

            int secondLen = nextLast;
            System.arraycopy(items, 0, a, firstLen, secondLen);
        }

        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }


    @Override
    public void addFirst(T x) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = loopSub(nextFirst);
    }


    @Override
    public void addLast(T x) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        size += 1;
        nextLast = loopAdd(nextLast);
    }


    @Override
    public boolean isEmpty() {
        return (size == 0);
    }


    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        } else {
            int i = actualIndex(index);
            return items[i];
        }
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            int indexFirst = loopAdd(nextFirst);
            nextFirst = indexFirst;
            T x = items[indexFirst];
            items[indexFirst] = null;
            if (usageLow()) {
                resize(items.length / 2);
            }
            return x;
        }
    }


    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            int indexLast = loopSub(nextLast);
            nextLast = indexLast;
            T x = items[indexLast];
            items[indexLast] = null;
            if (usageLow()) {
                resize(items.length / 2);
            }
            return x;
        }
    }


    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        for (int i = loopAdd(nextFirst); i != nextLast; i = loopAdd(i)) {
            System.out.print(items[i] + " ");
        }
    }
}
