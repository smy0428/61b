public class AList<T> {
    T[] items;
    int size;

    public AList() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[size + 1];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = x;
        size += 1;
    }

    public T getLast() {
        return items[size];
    }

    public T get(int i) {
        return items[i];
    }

    public int size() {
        return size;
    }

    public T removeLast() {
        size -= 1;
        T x = items[size];
        items[size] = null;
        double usage_ratio = size/items.length;
        if (size >= 16 && usage_ratio < 0.25) {
            size = size / 2;
        }
        return x;
    }

    public static void main(String[] args) {
        AList<Integer> L = new AList<Integer>();
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(10);
        L.addLast(100);
        // L.printDeque();
        System.out.println(L.items.length);
        //* System.out.println(L.getRecursive(2));
    }
}
