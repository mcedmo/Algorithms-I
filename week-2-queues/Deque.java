public class Deque<Item> implements Iterable<Item> {
    //Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a
    //queue that supports adding and removing items from either the front or the back of the data structure.

    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue


    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
        n = 1;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    //public int size()

    // add the item to the front
    public void addFirst(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        if (isEmpty()) first = last;
        else first.next = oldFirst;
        n++;
    }

    // add the item to the back
    //public void addLast(Item item)

    // remove and return the item from the front
    //public Item removeFirst()

    // remove and return the item from the back
    //public Item removeLast()

    // return an iterator over items in order from front to back
    //public Iterator<Item> iterator()

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst('hello');


    }

}
