package utils;

import java.io.*;

public class ListLoader {

    MyLinkedList list;

    public ListLoader() {
        list = new MyLinkedList<>();
    }

    /**
     * Reads all lines separately from a text file and prints shopping list actions to
     * command line interface.
     *
    private void fileRead() {

        try (BufferedReader br = new BufferedReader(new FileReader("src/input.txt"))) {
            String line = br.readLine();

            while (line != null) {
                System.out.println("Give shopping list (example: 1 milk;2 tomato;3 carrot;)");
                if (line.equals("exit")) {
                    break;
                }
                //listHandler(line);
                //printShoppingList();
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     */

    private MyLinkedList fileRead() {

        try (BufferedReader br = new BufferedReader(new FileReader("src/saveGui.txt"))) {
            String line = br.readLine();

            while (line != null) {

                //listHandler(line);

                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
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
    */

    /**
     * Writes save file to save items to text file
     *
     * Writes and therefore saves information from the shopping list
     * First write: number and name of the item
     * Second write: Line break
     */
    private void fileWrite() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/save.txt"))) {

            /*
            for (int i = 0; i < list.size(); i++) {
                int number = list.get(i).getNumberOfItems();
                String name = list.get(i).getName();

                bw.write(number + " " + name);
                bw.write("\n");
            }
            */

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}