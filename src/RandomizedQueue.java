import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * Created by tux on 8/20/14.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;  // array of items
    private int N;  // number of elements on stack

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Null not allowed");
        if (N == a.length)
            resize(2 * a.length);  // double size of array if necessary
        a[N++] = item;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underlow");
        int num = StdRandom.uniform(N);
        Item temp = a[num];
        a[num] = a[N - 1];
        a[N - 1] = null;
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return temp;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int num = StdRandom.uniform(size());  // 0 ~ N-1
        return a[num];
    }

    // over items in random order return an independent iterator
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int i;
        private int[] order = new int[N];

        public RandomIterator() {
            i = 0;
            for (int j = 0; j < N; j++) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[order[i++]];
        }
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
        for (int i : rq) {
            StdOut.println(i);
        }
        StdOut.println();
        for (int i = 0; i < len; i++) {
            StdOut.println(rq.sample());
        }
        StdOut.println();
        for (int i = 0; i < len; i++) {
            StdOut.println(rq.dequeue());
        }
        StdOut.println();
    }
}
