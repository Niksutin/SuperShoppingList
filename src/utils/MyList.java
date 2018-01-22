package utils;

/**
 * This interface holds all the crucial methods that are needed for proper
 * use of lists.
 * 
 * @author      Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version     26.17.2017
 * @since       26.17.2017
 */
interface MyList<T> {

    /** Appends the specified element to the end of this list */
    void add(T e);

    /** Removes all of the elements from this list */
    void clear();

    /** Returns the element at the specified position in this list */
    T get(int i);

    /** Returns true if this list contains no elements */
    boolean isEmpty();

    /** Removes the first occurrence of the specified elemnt from this
     * list if it is present (return true)
     */
    T remove(T o);

    /** Removes the element at the specified position in this list. Returns the removed element. */
    boolean remove(int index);

    /** Returns the number of elements in this list */
    int size();
}