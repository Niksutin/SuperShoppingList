package main;

import javax.persistence.*;

/**
 * Item class to hold information about the item in question, including id,
 * name, number of items and list id of the list its supposed to be in.
 *
 * @author      Niko Mattila
 * @version     20.12.2017
 * @since       6.11.2017
 */
@Entity
@Table(name="ITEMS")
public class Item {

    @Id
    @Column(name="ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="LIST_ID")
    int listId;

    @Column(name="NAME")
    private String name;

    @Column(name="COUNT")
    private int numberOfItems;

    /**
     * Constructor for Item that needs crucial variables for construction.
     *
     * @param   name of the Item.
     * @param   numberOfItems
     */
    public Item (String name, int numberOfItems) {
        setName(name);
        this.numberOfItems = numberOfItems;
    }

    /**
     * Empty constructor for Item.
     */
    public Item() { }

    /**
     * Sets the id of the list the Item is in.
     *
     * @param i sets list id for Item.
     */
    public void setListId(int i) {
        listId = i;
    }

    /**
     * Gets the id of the list the Item is in.
     *
     * @return list id.
     */
    public int getListId() {
        return listId;
    }

    /**
     * Gets the name of the Item.
     *
     * @return  string name of the Item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Item.
     *
     * @param   name to be set on the Item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the number of items of the Item.
     *
     * @return  number of the items
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Sets the number of items of the Item.
     *
     * @param   i the number of items to ge set.
     */
    public void setNumberOfItems(int i) {
        numberOfItems = i;
    }
}
