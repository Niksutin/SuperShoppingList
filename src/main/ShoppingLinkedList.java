package main;

import utils.MyLinkedList;
import javax.persistence.*;

/**
 * Same as MyLinkedList class but has name attribute to keep track of its name.
 */
@Entity
@Table(name="LISTS")
public class ShoppingLinkedList<Item> extends MyLinkedList {

    @Transient
    ListWindow window;

    @Id
    @Column(name="LIST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="NAME")
    private String name;

    /**
     * Constructs object with same functionality that MyLinkedList object has
     * but adds in a name.
     *
     * @param   name of the list.
     */
    public ShoppingLinkedList(String name) {
        super();
        this.name = name;
    }

    /**
     * Empty constructor. Needed for Hibernate.
     */
    public ShoppingLinkedList() { }

    /**
     * Gets id of the list.
     *
     * @return  id of the list.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name of the list.
     *
     * @return  name of the list.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets ListWindow of the list.
     *
     * @return  ListWindow of the list.
     */
    public ListWindow getWindow() {
        return window;
    }

    /**
     * Sets a ListWindow for the list.
     *
     * @param   w is the ListWindow to be set.
     */
    public void setWindow(ListWindow w) {
        window = w;
    }

    /**
     * Sets the name of the list.
     *
     * @param   name of the list to be set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
