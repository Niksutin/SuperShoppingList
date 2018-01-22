package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is used to create separate window that holds information and functionality regarding
 * the shopping list in question
 *
 * @author      Niko Mattila
 * @version     20.12.2017
 * @since       22.11.2017
 */
public class ListWindow {

    // List used for creation
    ShoppingLinkedList<Item> sl;

    // List used for necessities
    ObservableList<Item> items;

    // Main stage of the ListWindow
    Stage stage;

    ListView<Item> lw;
    boolean removeSelected;

    /**
     * Constructor for the ListWindow. Creates a list window with all its properties
     * including window sizes, buttons and user input fields.
     *
     * @param   shoppingLinkedList where the ListView is created from/for.
     */
    public ListWindow(ShoppingLinkedList<Item> shoppingLinkedList) {

        sl = shoppingLinkedList;
        items = FXCollections.observableArrayList();
        removeSelected = false;

        lw = new ListView<>();
        cellOrganizer();
        VBox bottomBox = generateAddRemove();

        BorderPane root = new BorderPane();
        root.setCenter(lw);
        root.setBottom(bottomBox);

        lw.setItems(items);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("main/styles");
        stage = new Stage();
        stage.setTitle(shoppingLinkedList.getName());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to organize the cells inside the ListView. Updates the list with newest
     * Items if there are any.
     */
    public void cellOrganizer() {

        for (int i = 0; i < sl.size(); i++) {
            Item temp = (Item) sl.get(i);
            items.add(temp);
        }

        lw.setCellFactory(param -> new ListCell<Item>() {

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " " + item.getNumberOfItems());
                }
            }
        });
    }

    /**
     * Generates Add and Remove buttons to the listWindow interface. Add button
     * is used to add more items into the list. Remove is used to remove specific
     * items from the list. Has a specific checker for user input to see that the
     * user inputted correct information needed to create new item. Has a checker
     * when remove is pressed that a item is selected also.
     *
     * @return  Returns VBox that holds buttons for adding and removing. Includes
     *          fields for user input regarding the item name and count.
     */
    public VBox generateAddRemove() {
        HBox hboxForms = new HBox();
        HBox hboxButtons = new HBox();
        VBox bottomBox = new VBox();

        hboxForms.setPadding(new Insets(15, 15, 15, 15));
        hboxForms.setSpacing(10);
        hboxForms.getStyleClass().add("hbox");

        hboxButtons.setPadding(new Insets(15, 15, 15, 15));
        hboxButtons.setSpacing(385);
        hboxButtons.getStyleClass().add("hboxAlt");

        // Item name input
        TextField nameField = new TextField();
        Label nameLabel = new Label("Item: ");
        nameLabel.setPadding(new Insets(5, 0, 0 ,0));
        nameLabel.setTextFill(Color.WHITE);
        nameField.setPromptText("Potato");

        // Number of items input
        TextField numberField = new TextField();
        Label numberLabel = new Label("Count: ");
        numberLabel.setPadding(new Insets(5, 0, 0 ,0));
        numberLabel.setTextFill(Color.WHITE);
        numberField.setPrefWidth(80);
        numberField.setPromptText("5");

        // Add button creation
        Button create = new Button("Add");
        create.setOnAction(e -> {
            Item item = new Item();

            if (nameField.getText() != null && numberField.getText() != null) {
                if (nameField.getText().equals("")) {
                    nameField.setStyle("-fx-background-color: red;");
                } else {
                    nameField.setStyle(null);
                }

                item.setName(nameField.getText());

                try {
                    int number = Integer.parseInt(numberField.getText());
                    item.setNumberOfItems(number);
                    for (int i = 0; i < sl.size(); i++) {
                        Item temp = (Item) sl.get(i);
                        if (temp.getName().equals(item.getName())) {
                            item.setNumberOfItems(item.getNumberOfItems()
                                    + temp.getNumberOfItems());
                            removeItem(temp);
                        }
                    }
                    items.add(item);
                    sl.add(item);
                    nameField.clear();
                    numberField.clear();
                    numberField.setStyle(null);
                } catch (NumberFormatException ex) {
                    numberField.setStyle("-fx-background-color: red;");
                }
            }
        });

        create.setPrefSize(100, 20);

        Button delete = new Button("Remove");
        delete.setOnAction((e) -> {
            try {
                Item selectedItem = lw.getSelectionModel().getSelectedItem();
                removeItem(selectedItem);
            } catch(NullPointerException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().getStylesheets().add("main/styles");
                alert.setTitle("Nothing to delete!");
                alert.setHeaderText("Warning!");
                alert.setContentText("Please choose something before deletion.");
                alert.showAndWait();
            }
        });

        delete.setPrefSize(100, 20);

        hboxForms.getChildren().addAll(nameLabel, nameField, numberLabel, numberField);
        hboxButtons.getChildren().addAll(create, delete);
        bottomBox.getChildren().addAll(hboxForms, hboxButtons);

        return bottomBox;
    }

    /**
     * Method to remove specific item from all the lists.
     *
     * @param item to be removed from all the lists.
     */
    public void removeItem(Item item) {
        lw.getItems().remove(item);
        items.remove(item);
        sl.remove(item);
    }
}
