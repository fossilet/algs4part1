import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * Created by tux on 8/20/14.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        // FIXME: maybe I should use resizing array...
        first = null;
        last = null;
        N = 0;
        assert check();
    }

    // is the queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;
        assert check();
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int num = StdRandom.uniform(size());  // 0 ~ N-1
        if (num == 0 || num == N - 1) {
            Item item = first.item;
            first = first.next;
            N--;
            if (isEmpty()) last = null;  // to avoid loitering
            assert check();
            return item;
        } else {
            Node before = first;
            for (int i = 0; i < num - 1; i++) {
                before = before.next;
            }
            // dequeue
            Node rm = before.next;
            Node after = rm.next;
            before.next = after;
            rm.next = null;
            N--;
            assert check();
            return rm.item;
        }
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int num = StdRandom.uniform(size());  // 0 ~ N-1
        Node ret = first;
        for (int i = 0; i < num; i++) {
            ret = ret.next;
        }
        return ret.item;
    }

    // over items in random order return an independent iterator
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        // FIXME: not random
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

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(0);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(7);
        rq.enqueue(5);
        rq.enqueue(6);
        int len = rq.size();
        for (int i: rq) {
            StdOut.println(i);
        }
        for (int i = 0; i < len; i++) {
            StdOut.println(rq.sample());
        }
        for (int i = 0; i < len; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
