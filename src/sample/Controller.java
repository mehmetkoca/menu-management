package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.model.Food;
import sample.model.FoodData;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private TableView<Food> foodsTable;

    private FoodData data;

    public void initialize() {
        data = new FoodData();
        data.loadFoods();
        foodsTable.setItems(data.getFoods());
    }

    @FXML
    public void showAddFood(){
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Food");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addFood.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Error load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        //OK butonuna basıldığında observable list'e ekle
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FoodController foodController = fxmlLoader.getController();
            Food newFood = foodController.getNewFood();
            data.addFood(newFood);
            data.saveFoods();
        }
    }

    @FXML
    public void showEditFood(){
        Food selectedFood = foodsTable.getSelectionModel().getSelectedItem();
        if (selectedFood == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Food Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select food you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit Food");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addFood.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Error load dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        FoodController foodController = fxmlLoader.getController();
        foodController.editFood(selectedFood);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            foodController.updateFood(selectedFood);
            data.saveFoods();
        }

    }

    public void deleteFood(){
        Food selectedFood = foodsTable.getSelectionModel().getSelectedItem();
        if (selectedFood == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Food selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the food you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Food");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected food: \n" +
                                selectedFood.getFirstItem() + "\n" +
                                selectedFood.getSecondItem() + "\n" +
                                selectedFood.getThirdItem() + "\n" +
                                selectedFood.getFourthItem());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.deleteFood(selectedFood);
            data.saveFoods();
        }
    }
}
