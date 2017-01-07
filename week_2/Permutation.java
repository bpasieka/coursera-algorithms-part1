import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Dima Pasieka
 */
public class Permutation
{
    public static void main(String[] args)
    {
        int count = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        for (int i = 0; i < count; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}