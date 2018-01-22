package utils;

import javafx.scene.control.Alert;

import main.Item;
import main.ShoppingLinkedList;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * This class is used to save application data into a text file and to load data from a text
 * file. Contains methods to load, save and transform files into MyLinkedLists or transform Lists
 * into files.
 *
 * @author  Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version 20.12.2017
 * @since   6.11.2017
 */
public class TextFileHandler {

    /**
     * Loads a user chosen (.txt) file from the computer.
     *
     * @return  File that the user has chosen.
     */
    public static File load() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load text file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        return fileChooser.showOpenDialog(new Stage());
    }

    /**
     * Saves a (.txt) file into the user chosen location.
     *
     * @return  File that the user saved.
     */
    public static File save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save text file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        return fileChooser.showSaveDialog(new Stage());
    }

    /**
     * Converts the parameter File into a List that contains all the saved lists
     * from a text file. The lists are used in SuperShoppingList GUI application.
     *
     * @param   file is the file where the MyLinkedList is trying to be loaded.
     * @return  Returns MyLinkedList if the loading was successful.
     */
    public static MyLinkedList toList(File file) {

        MyLinkedList<ShoppingLinkedList<Item>> lists = new MyLinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();
            ShoppingLinkedList loadedList = new ShoppingLinkedList("line");
            lists = new MyLinkedList<>();

            while (line != null) {

                if (line.charAt(0) == '@') {

                    String[] listName = line.split("@");
                    loadedList = new ShoppingLinkedList(listName[1]);
                    lists.add(loadedList);

                } else {

                    String[] itemInfo = line.split(" ");
                    int itemAmount = Integer.parseInt(itemInfo[0]);
                    Item itemToAdd = new Item(itemInfo[1], itemAmount);
                    loadedList.add(itemToAdd);
                }

                line = br.readLine();
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid file type");
            alert.setHeaderText("Warning!");
            alert.setContentText("You are trying to load invalid file.");
            alert.showAndWait();
        }
        return lists;
    }

    /**
     * Converts a MyLinkedList into a (.txt) file that can then be stored.
     *
     * @param   file is the file where to write data from the MyLinkedList.
     * @param   list is the MyLinkedList where the data is written from.
     */
    public static void listToFile(File file, MyLinkedList<ShoppingLinkedList<Item>> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (int i = 0; i < list.size(); i++) {

                bw.write("@" + list.get(i).getName());
                bw.write("\n");

                for(int j = 0; j < list.get(i).size(); j++) {

                    Item temp = (Item) list.get(i).get(j);
                    int number = temp.getNumberOfItems();
                    String name = temp.getName();

                    bw.write(number + " " + name);
                    bw.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
