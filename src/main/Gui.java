package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utils.MyLinkedList;
import utils.TextFileHandler;
import java.io.*;
import java.util.List;
import java.util.Optional;

/**
 * Main class for the Graphical User Interface portion of the SuperShoppingList application.
 * All of the graphical elements of the application are created with JavaFX.Contains all the
 * functionality that is wanted from the program. Those features are:
 * - Create new lists.
 * - Delete lists.
 * - Inspect lists.
 * - Add items to lists.
 * - Remove items from lists.
 * - Ability to save lists into .text file
 * - Ability to save lists into database
 * - Settings to change username, password and database
 *
 * @author  Niko Mattila <niko.mattila@cs.tamk.fi>
 * @version 20.12.2017
 * @since   6.11.2017
 */
public class Gui extends Application {

    // List of all the shopping lists
    MyLinkedList<ShoppingLinkedList<Item>> lists;
    VBox vbox;
    BorderPane root;
    Stage primaryStage;

    boolean deleteSelected;

    // Size of the window
    final double WINDOWHEIGHT = 480;
    final double WINDOWWIDTH = 640;

    /**
     * Empty constructor for Gui.
     */
    public Gui() {}

    /**
     * Main method which launched the program.
     *
     * @param   args command line parameters.
     */
    public static void main(String [] args) { launch(); }

    /**
     * This method is called when the program is started. Creates the visual
     * representation of the Graphical User Interface window and all its elements.
     *
     * @param   primaryStage is self explanatory primary stage for all the GUI main features.
     * @throws  Exception is thrown if exception occurs.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        lists = new MyLinkedList<>();

        deleteSelected = false;

        // Things to add to the BorderPane
        HBox createDelete = addCreateDelete();
        vbox = addVBox();

        //Menu
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menu = settingsGenerator();
        menuBar.getMenus().addAll(menu);

        // All items in the top box of the BorderPane
        VBox topBox = new VBox(menuBar, createDelete);

        // All BorderPane stuff
        root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(vbox);


        // Creating scene and setting the stage
        Scene scene = new Scene(root, WINDOWWIDTH, WINDOWHEIGHT);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("main/styles");
        primaryStage.setTitle("Super Shopping List");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Adds the Create and Delete buttons to the interface. Enables creation
     * and deletion of lists and their buttons from the application.
     *
     * @return  Returns HBox element with Create and Delete buttons in it.
     */
    public HBox addCreateDelete() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 15, 15, 15));
        hbox.setSpacing(10);
        hbox.getStyleClass().add("hbox");

        Button create = new Button("Create");
        create.setOnAction((e) -> { createNewList(); });
        create.setPrefSize(100, 20);

        ToggleButton delete = new ToggleButton("Delete");
        delete.setOnAction((e) -> {
            if (delete.isSelected()) {
                deleteSelected = true;
            } else {
                deleteSelected = false;
            }
            System.out.println("DELETE"); });
        delete.setPrefSize(100, 20);

        hbox.getChildren().addAll(create, delete);

        return hbox;
    }

    /**
     * This method pops up a dialog to ask the user for the name of the new
     * list that is to be created. Asks the user for list name, adds the list
     * into the MyLinkedList of lists and creates a button for the newly created list.
     */
    private void createNewList() {

        // Text dialog code
        TextInputDialog dialog = new TextInputDialog("List");
        dialog.setTitle("");
        dialog.setHeaderText("Create new list");
        dialog.setContentText("Please enter name for list:");
        dialog.getDialogPane().getStylesheets().add("main/styles");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            ShoppingLinkedList<Item> tempList = new ShoppingLinkedList<>(name);
            lists.add(tempList);
        });

        // Adds button for the list
        VBox temp = addVBox();
        root.getChildren().remove(1,2);
        root.setCenter(temp);
    }

    /**
     * This method deletes a list and its button from the VBox of the
     * application window. Searches for the correct list with id.
     *
     * @param   s is id of the list that needs to be deleted.
     */
    public void deleteList(String s) {
        for (int i = 0; i < lists.size(); i++) {
            String id = "" + i;
            if (id.equals(s)) {
                lists.remove(i);
            }
        }
        VBox temp = addVBox();
        root.getChildren().remove(1,2);
        root.setCenter(temp);
    }

    /**
     * This method is used to create updated view of the buttons or lists of the
     * application. Can be used to create new buttons as well.
     *
     * @return  Returns VBox which holds all the buttons to indicate lists.
     */
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #EAB962;");


        for(int i = 0; i < lists.size(); i++) {
            String tempName = lists.get(i).getName();
            Button button = new Button(tempName);

            // Setting all buttons new id
            button.setId("" + i);
            button.setOnAction((e) -> {
                if (deleteSelected) {
                    deleteList(button.getId());
                } else {
                    openList(button.getId());
                }
            });
            vbox.getChildren().add(button);
        }

        return vbox;

    }

    /**
     * Opens a single list represented with its id. Opens a separate window,
     * which holds all the functionality needed to operate the list.
     *
     * @param   s is id of the list that needs to be opened.
     */
    private void openList(String s) {
        for (int i = 0; i < lists.size(); i++) {
            String id = "" + i;
            if (id.equals(s)) {
                if (lists.get(i).getWindow() == null) {
                    lists.get(i).setWindow(new ListWindow(lists.get(i)));
                } else {
                    lists.get(i).getWindow().stage.show();
                }
            }
        }
    }

    /**
     * Creates menu for the application that includes functional settings,
     * save, load and exit. Creates separate MenuItem for each functionality.
     * Stores the MenuItems in a dropdown menu.
     *
     * @return      Returns Menu object for dropdown menu function.
     */
    public Menu settingsGenerator() {

        Menu temp = new Menu("Menu");

        MenuItem settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setOnAction((e) -> {
            Settings settings = new Settings();
            settings.show();
        });

        MenuItem save = new MenuItem("Save");
        save.setOnAction((e) -> {
            Settings settings = new Settings();
            if (settings.getDatabase().equals("text")) {
                textSave();
            } else if (settings.getDatabase().equals("mysql")) {
                databaseSave("mysql");
            } else if (settings.getDatabase().equals("javadb")) {
                databaseSave("derby");
            }
        });

        MenuItem load = new MenuItem("Load");
        load.setOnAction((e) -> {
            Settings settings = new Settings();
            if (settings.getDatabase().equals("text")) {
                textLoad();
            } else if (settings.getDatabase().equals("mysql")) {
                databaseLoad("mysql");
            } else if (settings.getDatabase().equals("javadb")) {
                databaseLoad("derby");
            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((e) -> { System.exit(0); });

        temp.getItems().addAll(settingsMenuItem, save, load, new SeparatorMenuItem(), exit);

        return temp;
    }

    /**
     * This method loads information from the wanted database. Loads lists or items
     * from either MySQL or Derby database. Alerts the user if the credentials are wrong.
     *
     * @param   type is the type of the database that needs to be loaded.
     */
    public void databaseLoad(String type) {
        lists = new MyLinkedList<>();

        try {
            List<ShoppingLinkedList> tempList = Databaser.getLists(type);
            for (int i = 0; i < tempList.size(); i++) {
                lists.add(tempList.get(i));
                List<Item> tempItems = Databaser.getItems(type);
                Databaser.showDatabase(type);
                for (int j = 0; j < tempItems.size(); j++) {
                    if (tempItems.get(j).getListId() == lists.get(i).getId()) {
                        lists.get(i).add(tempItems.get(j));
                    }
                }
            }
        } catch (org.hibernate.service.spi.ServiceException e) {
            // Alert if the username or password is wrong
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().getStylesheets().add("main/styles");
            alert.setTitle("Incorrect credentials!");
            alert.setHeaderText("Warning!");
            alert.setContentText("Wrong username or password.");
            alert.showAndWait();
        }
        VBox loadedBox = addVBox();
        root.setCenter(loadedBox);
    }

    /**
     * Saves the lists and items into database. Can store data into either
     * MySQL or Derby database.
     *
     * @param   type is the type of the database that the lists and items are saved to.
     */
    public void databaseSave(String type) {
        try {
            for (int i = 0; i < lists.size(); i++) {
                Databaser.storeToDatabase(type, lists.get(i));
                for (int j = 0; j < lists.get(i).size(); j++) {
                    List<ShoppingLinkedList> tempList = Databaser.getLists(type);
                    Item temp = (Item) lists.get(i).get(j);
                    temp.setListId(tempList.get(i).getId());
                    Databaser.storeToDatabase(type, temp);
                }
            }
            Databaser.showDatabase(type);
        } catch (org.hibernate.service.spi.ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().getStylesheets().add("main/styles");
            alert.setTitle("Incorrect credentials!");
            alert.setHeaderText("Warning!");
            alert.setContentText("Wrong username or password.");
            alert.showAndWait();
        }
    }

    /**
     * Saves a (.txt) file to hold a save for the lists and items.
     * Asks the user for location and name of the file.
     */
    public void textSave() {
        File file = TextFileHandler.save();
        TextFileHandler.listToFile(file, lists);
    }

    /**
     * Loads a (.txt) file to be converted into a list.
     * List can then be used by the program.
     */
    public void textLoad() {
        File file = TextFileHandler.load();
        lists = TextFileHandler.toList(file);

        VBox loadedBox = addVBox();
        root.setCenter(loadedBox);
    }
}
