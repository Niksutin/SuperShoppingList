package utils;

/**
 * Resizable-array implementation of the MyList interface. Implements all crucial list operations.
 * 
 * @author      Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version     26.17.2017
 * @since       26.17.2017
 */
class MyArrayList<T> implements MyList<T> {

    final int THRESHOLD = 5;
    int size;
    T[] list;

    public MyArrayList() {
        size = 0;
        list = (T[]) new Object[THRESHOLD];
    }

        
    /**
     * @see MyList#add(T e) add
     */
    public void add(T e) {
        if (list.length > size) {
            list[size] = e; 
        } else {
            T[] newList = (T[]) new Object[list.length + THRESHOLD];
            for (int i = 0; i < list.length; i++) {
                newList[i] = list[i];
            }
            list = newList;
            list[size] = e;
        }
        size++;
    }   

    /**
     * @see MyList#clear() clear
     */
    public void clear() {
        size = 0;
        list = (T[]) new Object[THRESHOLD];
        System.out.println("Array cleared succesfully!");
    }

    /**
     * @see MyList#get(int index) get
     */
    public T get(int index) {
        return list[index];
    }

    /**
     * @see MyList#isEmpty() isEmpty
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @see MyList#remove(T o) remove
     */
    public T remove(T o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (list[i] == null) {
                    fastRemove(i);
                    return o;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(list[i])) {
                    fastRemove(i);
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * @see MyList @remove(int index) remove
     */
    public boolean remove(int index) {
        T o = get(index);
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (list[i] == null) {
                    fastRemove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(list[i])) {
                    fastRemove(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Deletes wanted object from the list and creates new list according to the absence
     * of the object.
     * 
     * @param int Points to the object that needs to be deleted.
     */
    private void fastRemove(int i) {
        int numMoved = size - i - 1;
        if (numMoved > 0) {
            System.arraycopy(list, i+1, list, i,
                                numMoved);
        }

        list[--size] = null;
    }

    /**
     * @see MyList#size() size
     */
    public int size() {
        return size;
    }
}