package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Properties;

/**
 * This class is used to interact between the applications GUI and database.
 * Is used to create session with either MySQL or Derby database. Contains methods
 * for getting items and lists and turning them into Lists. Can store objects into
 * database and can show contents of databases.
 *
 * @author  Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version 20.12.2017
 * @since   6.11.2017
 */
class Databaser {

    // Factory to be used for each session
    public static SessionFactory factory;

    /**
     * Creates a session with wanted database type. Fetches Item objects from the
     * database and creates a List of them. Returns the List.
     *
     * @param   type of the database that is needed.
     * @return  List of Items from database.
     */
    public static List getItems(String type) {
        if (type.equals("derby")) {
            factory = myDerbySessionFactory();
        } else if (type.equals("mysql")) {
            factory = mySQLSessionFactory();
        } else {
            return null;
        }
        Session session = factory.openSession();
        List<Item> items = session.createQuery("from Item").list();
        factory.close();
        return items;
    }

    /**
     * Creates a session with wanted database type. Fetches ShoppingLinkedList objects
     * from the database and creates a List of them. Returns the List.
     *
     * @param   type of the database that is needed.
     * @return  List of ShoppingLinkedLists from database.
     */
    public static List getLists(String type) {
        if (type.equals("derby")) {
            factory = myDerbySessionFactory();
        } else if (type.equals("mysql")) {
            factory = mySQLSessionFactory();
        } else {
            return null;
        }
        Session session = factory.openSession();
        List<ShoppingLinkedList> lists = session.createQuery("from ShoppingLinkedList").list();
        factory.close();
        return lists;
    }

    /**
     * Creates a session with wanted database type. Prints some contents from the
     * database tables: LISTS and ITEMS.
     *
     * @param   type of the database that is needed.
     */
    public static void showDatabase(String type) {
        if (type.equals("derby")) {
            factory = myDerbySessionFactory();
        } else if (type.equals("mysql")) {
            factory = mySQLSessionFactory();
        } else {
            return;
        }
        Session session = factory.openSession();
        List<ShoppingLinkedList> lists = session.createQuery("from ShoppingLinkedList").list();
        for(ShoppingLinkedList i : lists) {
            System.out.println("Listname=" + i.getName() + " ListID=" +  i.getId());
        }
        List<Item> list = session.createQuery("from Item").list();
        for(Item i : list) {
            System.out.println("Itemname=" + i.getName()
                    + " Numberofitems=" + i.getNumberOfItems()
                    + " ListID=" + i.getListId());
        }
        factory.close();
    }

    /**
     * Creates a session with wanted database type. Stores a annotated Object into
     * the database.
     *
     * @param   type of the database that is needed.
     * @param   i is the Object that is added into the database.
     */
    public static void storeToDatabase(String type, Object i) {
        if (type.equals("derby")) {
            factory = myDerbySessionFactory();
        } else if (type.equals("mysql")) {
            factory = mySQLSessionFactory();
        } else {
            return;
        }
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(i);
        tx.commit();
        factory.close();
    }

    /**
     * SessionFactory builder method that, instead of using .XML file to insert
     * properties in the SessionFactory, we do it with just Java! Contains information
     * about the MySQL connection:
     *      - Driver
     *      - Dialect
     *      - Url
     *      - Username
     *      - Password
     *
     * @return  SessionFactory of the connection into the database.
     */
    public static SessionFactory mySQLSessionFactory() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.put("hibernate.connection.url", "jdbc:mysql://mydb.tamk.fi/dbc5nimatt1");
        properties.put("hibernate.connection.username", Settings.username);
        properties.put("hibernate.connection.password", Settings.password);

        return new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(ShoppingLinkedList.class)
                .buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .applySettings(properties)
                                .build()
                );
    }

    /**
     * SessionFactory builder method that, instead of using .XML file to insert
     * properties in the SessionFactory, we do it with just Java! Contains information
     * about the Derby connection:
     *      - Driver
     *      - Dialect
     *      - Url
     *
     * @return  SessionFactory of the connection into the database.
     */
    public static SessionFactory myDerbySessionFactory() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        // Driver to use
        properties.put("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
        // Url to database
        properties.put("hibernate.connection.url", "jdbc:derby:itemdb;create=true");

        return new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(ShoppingLinkedList.class)
                .buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .applySettings(properties)
                                .build()
                );
    }
}