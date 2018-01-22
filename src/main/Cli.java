package main;

import utils.MyLinkedList;
import java.io.*;
import java.util.Scanner;

/**
 * This class runs the shopping list application in the Command Line Interface.
 *
 * This class executes the shopping list application and its functions in the Command Line Interface.
 * Has functionality to take user input and print the shopping list according to the data.
 *
 * @author      Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version     8.11.2017
 * @since       6.11.2017
 */
public class Cli implements Runnable {

    MyLinkedList<Item> list;
    private boolean running;
    private Scanner scanner;
    private String input;

    /**
     * Constructor for Command Line Interface.
     *
     * Initiates all attributes for this class.
     */
    Cli() {

        this.list = new MyLinkedList<>();

        setRunning(true);
        scanner = new Scanner(System.in);
        run();
    }

    /**
     * Run method to run all functions that the app has implemented to be run in Cli.
     */
    @Override
    public void run() {

        System.out.println("SHOPPING LIST");
        System.out.println("Tampere University of Applied Sciences");

        while(isRunning()) {

            System.out.println("Give shopping list (example: 1 milk;2 tomato;3 carrot;)");
            input = scanner.nextLine();

            if (input.equals("exit")) {
                setRunning(false);
                break;
            }
            listHandler(input);
            printShoppingList();

        }
    }

    /**
     * Reads the content of a text file and creates new MyLinkedList according to the data
     * found inside.
     *
     * @return      Returns MyLinkedList created from the content of a save.txt file.
     */
    private MyLinkedList<Item> listRead() {

        MyLinkedList<Item> createdList = new MyLinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/save.txt"))) {
            String line = br.readLine();

            while(line != null) {
                String[] data = line.split(" ");
                createdList.add(new Item(data[1], Integer.parseInt(data[0])));
                line = br.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return createdList;
    }

    /**
     * Adds all new items to the list that are based on the input string
     * Deletes all duplicate items and only increases one items number of items.
     *
     * @param input     String input of the content that needs to be added to the list.
     */
    private void listHandler(String input) {

        String[] inputTemp = input.split(";");

        String[] itemName = new String[inputTemp.length];
        int[] numberOfItems = new int[inputTemp.length];

        for(int i = 0; i < inputTemp.length; i++) {
            String[] temp = inputTemp[i].split(" ");
            numberOfItems[i] = Integer.parseInt(temp[0]);
            itemName[i] = temp[1];
        }

        for(int i = 0; i < inputTemp.length; i++) {
            list.add(new Item(itemName[i], numberOfItems[i]));
        }

        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getName().equals(list.get(j).getName())) {
                    list.get(i).setNumberOfItems(list.get(i)
                            .getNumberOfItems()
                            + list.get(j).getNumberOfItems());
                    list.remove(j);
                }
            }
        }
    }

    /**
     * Prints the current content of the shopping list.
     */
    private void printShoppingList() {
        System.out.println("Your Shopping List now:");
        for(int i = 0; i < list.size(); i++) {
            System.out.print("  " + list.get(i).getNumberOfItems() + " ");
            System.out.print(list.get(i).getName());
            System.out.println();
        }
        System.out.println();
    }

    /**
     * This method checks the value of boolean running.
     *
     * @return  Returns value of boolean running.
     */
    private boolean isRunning() {
        return running;
    }

    /**
     * This method sets the value of running.
     *
     * @param   running is boolean that sets the program to run or to stop.
     */
    private void setRunning(boolean running) {
        this.running = running;
    }
}
