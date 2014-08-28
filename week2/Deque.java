import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * Created by tux on 7/21/14.
 */
public class Deque<Item> implements Iterable<Item> {
    private int N; // size of the deque
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public Deque() {
        // construct an empty deque
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return first == null;
    }

    public int size() {
        // return the number of items on the
        return N;
    }

    // deque
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Cannot add/remove null.");
        // insert the item at the front
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (last == null) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        N++;
        assert check();
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Cannot add/remove null.");
        // insert the item at the end
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        N++;
        assert check();
    }

    public Item removeFirst() {
        // delete and return the item at the front
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
//        first.prev = null;
        if (isEmpty()) {
            last = null;  // to avoid loitering
        } else {
            first.prev = null;
        }
        N--;
        assert check();
        return item;
    }

    public Item removeLast() {
        // delete and return the item at the end
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        N--;
        assert check();
        return item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
            if (last != null) return false;
        } else if (N == 1) {
            if (first == null || last == null) return false;
            if (first != last) return false;
            if (first.next != null) return false;
        } else {
            if (first == last) return false;
            if (first.next == null) return false;
            if (last.next != null) return false;

            // check internal consistency of instance variable N
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != N) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        // unit testing
        Deque<Integer> dq = new Deque<Integer>();
        assert dq.isEmpty();
        dq.addFirst(1);
        dq.addLast(2);
        int len = dq.size();
        for (int i: dq) {
            StdOut.print(i);
        }
        for (int i = 0; i < len; i++) {
            StdOut.println(dq.removeFirst());
//            StdOut.println(dq.removeLast());
        }
    }
}
