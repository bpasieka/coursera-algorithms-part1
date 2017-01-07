import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Dima Pasieka
 */
public class RandomizedQueue<Item> implements Iterable<Item>
{
    // Items in queue
    private Item[] items;

    // RandomizedQueue size
    private int size;

    // RandomizedQueue Iterator
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        // Items in queue during iteration
        private Item[] itemsCopy;

        // RandomizedQueue size during iteration
        private int sizeCopy;

        // Init with coping items and size
        private RandomizedQueueIterator()
        {
            sizeCopy = size;
            itemsCopy = (Item[]) new Object[sizeCopy];

            for (int i = 0; i < sizeCopy; i++) {
                itemsCopy[i] = items[i];
            }
        }

        public boolean hasNext()
        {
            return (sizeCopy > 0);
        }

        public Item next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }

            int index = StdRandom.uniform(sizeCopy);
            Item item = itemsCopy[index];

            // replace taken element with last one
            itemsCopy[index] = itemsCopy[sizeCopy - 1];
            itemsCopy[sizeCopy - 1] = null;
            sizeCopy--;

            return item;
        }
    }

    // Construct an empty randomized queue
    public RandomizedQueue()
    {
        int defaultItemsSize = 2;

        items = (Item[]) new Object[defaultItemsSize];
        size = 0;
    }

    // If the queue empty
    public boolean isEmpty()
    {
        return (size == 0);
    }

    // Return the number of items on the queue
    public int size()
    {
        return size;
    }

    // Add the item
    public void enqueue(Item item)
    {
        if (item == null) {
            throw new NullPointerException("Can't add empty element to queue");
        }

        if (size == items.length) {
            resizeItems(items.length * 2);
        }

        items[size] = item;
        size++;
    }

    // Remove and return a random item
    public Item dequeue()
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int index = StdRandom.uniform(size);
        Item item = items[index];

        // replace taken element with last one
        items[index] = items[size - 1];
        items[size - 1] = null;
        size--;

        if (size > 0 && (size == items.length / 4)) {
            resizeItems(items.length / 2);
        }

        return item;
    }

    // Return (but do not remove) a random item
    public Item sample()
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int index = StdRandom.uniform(size);

        return items[index];
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    // Resize items array
    private void resizeItems(int length) {
        Item[] newItems = (Item[]) new Object[length];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    // Unit testing
    public static void main(String[] args)
    {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        String text = "A";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "B";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "C";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "D";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "E";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        queue.dequeue();
        StdOut.println("dequeue()");

        queue.sample();
        StdOut.println("sample()");

        text = "F";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        StdOut.println("Iterating queue...");
        for (String item: queue) {
            StdOut.println("Iterate element: " + item);
        }
    }
}