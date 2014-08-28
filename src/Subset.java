/**
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * Created by tux on 8/25/14.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // FIXME: does not meet bonus requirement:
        // For an extra challenge, use only one Deque or RandomizedQueue object of
        // maximum size at most k.
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        String[] strs = StdIn.readAllStrings();
        for (String s: strs) {
            rq.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }

    }
}
