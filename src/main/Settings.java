package main;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

/**
 * This class contains information that needs to be saved inside the user's preferences.
 * Also holds a graphical representation of the settings window and needed input fields
 * and buttons to change and save the said settings.
 *
 * @author      Niko Mattila
 * @version     20.12.2017
 * @since       19.12.2017
 */
public class Settings {

    // Window size
    public static final int WINDOW_HEIGHT = 160;
    public static final int WINDOW_WIDTH = 400;

    // Information to be stored in preferences
    private static String database;
    public static String username;
    public static String password;

    // Preferences
    public static Preferences preferences;

    // JavaFX shenanigans
    public static Button save;
    public static Button cancel;
    public static ComboBox options;
    public static TextField nameField;
    public static TextField passField;
    public static Stage stage;
    public static Scene scene;

    /**
     * Constructor for Settings. Creates the main window and all functionality for
     * the settings window. Initates the settings from preferences.
     */
    public Settings() {

        // Preferences
        preferences = Preferences.userNodeForPackage(Settings.class);
        database = preferences.get("database", "text");
        username = preferences.get("username", "username");
        password = preferences.get("password", "password");

        // JavaFX start -------------------------------------------------

        // Buttons
        save = saveSettingsButton();
        cancel = closeButton();

        // Dropdown
        options = new ComboBox();
        options.getItems().addAll("text", "mysql", "javadb");
        options.setValue(database);
        options.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> database = t1);

        // Textfields
        nameField = new TextField(username);
        nameField.setPromptText("username");
        passField = new TextField(password);
        passField.setPromptText("password");

        // Gridpane
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        // adding comboBox
        grid.add(new Label("Saving method: "), 0, 0);
        grid.add(options, 1, 0);

        // adding username + password
        grid.add(new Label("Username: "), 0, 2);
        grid.add(nameField, 1, 2);
        grid.add(new Label("Password: "), 0, 3);
        grid.add(passField, 1, 3);

        // adding save + cancel
        grid.add(save, 0, 4);
        grid.add(cancel, 1, 4);

        // Scene + Stage
        scene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add("main/styles");
        grid.getStyleClass().add("hboxAlt");
        stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(scene);

        // JavaFX end -------------------------------------------------
    }

    /**
     * Getter for database
     * @return      database that is stored in the settings
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Method to create save button. Holds function to save username,
     * password and database into preferences.
     *
     * @return      returns button to save preference and close the
     *              application
     */
    public Button saveSettingsButton() {
        Button button = new Button("Save settings");
        button.setPrefSize(200, 20);
        button.setOnAction((e) -> {
            username = nameField.getText();
            password = passField.getText();
            preferences.put("database", database);
            preferences.put("username", username);
            preferences.put("password", password);

            stage.close();
        });
        return button;
    }

    /**
     * Method to create close button for the settings window.
     *
     * @return      returns button to close the application
     */
    public Button closeButton() {
        Button button = new Button("Close");
        button.setPrefSize(100, 20);
        button.setOnAction((e) -> {
            stage.close();
        });
        return button;
    }


    /**
     * Prints preferences.
     */
    public void printPrefs() {
        System.out.println("username = " + username
                + "\npassword = " + password
                + "\ndatabase = " + database);
    }

    /**
     * Simple method to show settings window.
     */
    public void show() {
        stage.show();
    }
}
