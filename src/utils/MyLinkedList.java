package utils;

/**
 * Singly-linked implementation of the MyList interface. Implements all crucial list operations.
 * 
 * @author      Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version     26.17.2017
 * @since       26.17.2017
 */
public class MyLinkedList<T> implements MyList<T> {

    MyElement<T> first = null;
    int size = 0;

    public MyLinkedList() {
        
    }

    /**
     * @see MyList#add(T o) add
     */
    public void add(T o) {

        if (first == null) {
            first = new MyElement();
            first.entry = o;
        } else {
            MyElement<T> temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new MyElement();
            temp.next.entry = o;
        }
        size++;
    }

    /**
     * @see MyList#get(int index) get
     */
    public T get(int index) {
        MyElement<T> temp = first;

        if (index < 0 || index >= size) {
            return null;
        } else {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        }
        return temp.entry;
    }

    /**
     * @see MyList#size() size
     */
    public int size() {
        return size;
    }

    /**
     * @see MyList#isEmpty() isEmpty
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * @see MyList#clear() clear
     */
    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * @see MyList#remove(T o) remove
     */
    public T remove(T o) {
        MyElement<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (first.entry.equals(o)) {
                first = temp.next;
                size--;
                return o;
            } else if (temp.next.entry.equals(o)) {
                temp.next = temp.next.next;
                size--;
                return o;
            }
            temp = temp.next;
        }
        return o;
    }

    /**
     * @see MyList#remove(int index) remove
     */
    public boolean remove(int index) {
        MyElement<T> temp = first;
        T o = get(index);
        for (int i = 0; i < size; i++) {
            if (first.entry.equals(o)) {
                first = temp.next;
                size--;
                return true;
            } else if (temp.next.entry.equals(o)) {
                temp.next = temp.next.next;
                size--;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
}