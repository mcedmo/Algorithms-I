import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a
    // queue that supports adding and removing items from either the front or the back of the data structure.
    // implements Iterable<Item>
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue


    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = first;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) first = last;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit tests
    private class DequeTest {
        Deque<Integer> deque = new Deque<Integer>();

        private void DequeTest() {
            int[] testElements = { 1, 2, 3, 4, 5, 6 };
            for (int i = 0; i < testElements.length; i++) {
                deque.addFirst(testElements[i]);
            }
        }

        private void testIterator() {
            int x = 0;
            for (Integer i : deque) {
                if (x != i) StdOut.print("iteration error");
                x++;
            }

        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque.DequeTest.testIterator();
    }
}

