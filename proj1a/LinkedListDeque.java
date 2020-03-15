public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode next;
        private StuffNode previous;
        private StuffNode(T i, StuffNode n, StuffNode m) {
            item = i;
            next = n;
            previous = m;
        }

        //* to get the index.th item in IntNode.

        public T helper(int index) {
            if (index == 0) {
                return item;
            }
            return next.helper(index - 1);
        }
    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new StuffNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
    }


    public int size() {
        return size;
    }

    public void addFirst(T x) {
        size += 1;
        StuffNode first = new StuffNode(x, null, null);
        first.next = sentinel.next;
        first.previous = sentinel;
        sentinel.next.previous = first;
        sentinel.next = first;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T result = sentinel.next.item;
        sentinel.next.next.previous = sentinel;
        sentinel.next = sentinel.next.next;
        return result;
    }



    public void addLast(T x) {
        size += 1;
        StuffNode last = new StuffNode(x, null, null);
        last.next = sentinel;
        last.previous = sentinel.previous;
        sentinel.previous.next = last;
        sentinel.previous = last;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T result = sentinel.previous.item;
        sentinel.previous.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;
        return result;
    }

    public void printDeque() {
        if (sentinel.next == sentinel) {
            return;
        }
        StuffNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    public boolean isEmpty() {
        return (sentinel == sentinel.next);
    }

    public T get(int index) {
        if (index > size()) {
            return null;
        }
        StuffNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }



    public T getRecursive(int index) {
        if (index > size()) {
            return null;
        }
        return sentinel.next.helper(index);
    }



    private static void main(String[] args) {
        LinkedListDeque<Integer> L = new LinkedListDeque<Integer>();
        L.addFirst(10);
        L.addLast(20);
        L.printDeque();
        System.out.println(L.get(0));
        System.out.println(L.getRecursive(1));
    }
}
