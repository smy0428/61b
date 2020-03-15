public class SLList {
    public class IntNode {
        public int item;
        public IntNode next;
        public IntNode previous;
        public IntNode(int i, IntNode n, IntNode m) {
            item = i;
            next = n;
            previous = m;
        }

        //* to get the index.th item in IntNode.

        public int helper(int index) {
            if (index == 0) {
                return item;
            }
            return next.helper(index - 1);
        }
    }

    public IntNode sentinel;
    public int size;

    public SLList() {
        size = 0;
        sentinel = new IntNode(28, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
    }
    public SLList(int x) {
        size = 1;
        sentinel = new IntNode(28, null, null);
        sentinel.next = new IntNode(x, null, null);
        sentinel.previous = sentinel.next;
        sentinel.previous.previous = sentinel;
        sentinel.next.next = sentinel;
    }

    public int size() {
        return size;
    }

    public void addFirst(int x) {
        size += 1;
        IntNode first = new IntNode(x, null, null);
        first.next = sentinel.next;
        first.previous = sentinel;
        sentinel.next.previous = first;
        sentinel.next = first;
    }

    public int removeFirst() {
        if (size == 0) {
            return 0;
        }
        size -= 1;
        int result = sentinel.next.item;
        sentinel.next.next.previous = sentinel;
        sentinel.next = sentinel.next.next;
        return result;
    }



    public void addLast(int x) {
        size += 1;
        IntNode last = new IntNode(x, null, null);
        last.next = sentinel;
        last.previous = sentinel.previous;
        sentinel.previous.next = last;
        sentinel.previous = last;
    }

    public int removeLast() {
        if (size == 0) {
            return 0;
        }
        size -= 1;
        int result = sentinel.previous.item;
        sentinel.previous.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;
        return result;
    }

    public void printDeque() {
        if (sentinel.next == sentinel) {
            return;
        }
        IntNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    public boolean isEmpty() {
        return (sentinel == sentinel.next);
    }

    public int get(int index) {
        if (index > size()) {
            return 0;
        }
        IntNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }



    public int getRecursive(int index) {
        if (index > size()) {
            return 0;
        }
        return sentinel.next.helper(index);
    }



    public static void main(String[] args) {
        SLList L = new SLList();
        L.addFirst(20);
        L.addFirst(10);
        L.addLast(40);
        L.printDeque();
        System.out.println(L.get(2));
        System.out.println(L.getRecursive(2));
    }
}