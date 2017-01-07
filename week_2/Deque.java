import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Dima Pasieka
 */
public class Deque<Item> implements Iterable<Item>
{
    // Deque size
    private int size;

    // First element of the Deque
    private Node<Item> first;

    // Last element of the Deque
    private Node<Item> last;

    // Deque element/item
    private class Node<Item>
    {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }

    // Deque iterator
    private class DequeIterator<Item> implements Iterator<Item>
    {
        // Current iterable element
        private Node<Item> current;

        // Init with first element to start from
        private DequeIterator(Node<Item> item)
        {
            current = item;
        }

        public boolean hasNext()
        {
            return (current != null);
        }

        public Item next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // Construct an empty deque
    public Deque()
    {
        size = 0;
        first = null;
        last = null;
    }

    // If the deque empty
    public boolean isEmpty()
    {
        return (size == 0);
    }

    // Return the number of items on the deque
    public int size()
    {
        return size;
    }

    // Add the item to the front
    public void addFirst(Item item)
    {
        if (item == null) {
            throw new NullPointerException("Can't add empty element to deque");
        }

        Node<Item> newItem = new Node<>();
        newItem.item = item;
        newItem.next = first;
        newItem.previous = null;

        if (isEmpty()) {
            last = newItem;
        } else {
            first.previous = newItem;
        }

        first = newItem;
        size++;
    }

    // Add the item to the end
    public void addLast(Item item)
    {
        if (item == null) {
            throw new NullPointerException("Can't add empty element to deque");
        }

        Node<Item> newItem = new Node<>();
        newItem.item = item;
        newItem.next = null;
        newItem.previous = last;

        if (isEmpty()) {
            first = newItem;
        } else {
            last.next = newItem;
        }

        last = newItem;
        size++;
    }

    // Remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) {
            last = null;
        } else {
            first.previous = null;
        }

        return item;
    }

    // Remove and return the item from the end
    public Item removeLast()
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = last.item;
        last = last.previous;
        size--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }

        return item;
    }

    // Return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new DequeIterator<>(first);
    }

    // Unit testing
    public static void main(String[] args)
    {
        Deque<String> deque = new Deque<>();

        String text = "World";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = ", ";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "Hello";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "Meow, ";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "^^";
        deque.addLast(text);
        StdOut.println("addLast() with: '" + text + "'");

        deque.removeFirst();
        StdOut.println("removeFirst()");

        deque.removeLast();
        StdOut.println("removeLast()");

        text = "!";
        deque.addLast(text);
        StdOut.println("addLast() with: '" + text + "'");

        StdOut.println("Iterating deque...");
        for (String item: deque) {
            StdOut.println("Iterate element: " + item);
        }
    }
}