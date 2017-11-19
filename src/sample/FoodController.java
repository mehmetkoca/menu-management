package sample;

import javafx.fxml.FXML;
import sample.model.Food;
import javafx.scene.control.TextField;


public class FoodController {

    @FXML
    private TextField firstItemField;

    @FXML
    private TextField secondItemField;

    @FXML
    private TextField thirdItemField;

    @FXML
    private TextField fourthItemField;


    public Food getNewFood(){
        String firstItem,secondItem,thirdItem,fourthItem;

        firstItem = firstItemField.getText();
        secondItem = secondItemField.getText();
        thirdItem = thirdItemField.getText();
        fourthItem = fourthItemField.getText();


        Food newFood = new Food(firstItem,secondItem,thirdItem,fourthItem);
        return newFood;
    }

    public void editFood(Food food) {
        firstItemField.setText(food.getFirstItem());
        secondItemField.setText(food.getSecondItem());
        thirdItemField.setText(food.getThirdItem());
        fourthItemField.setText(food.getFourthItem());
    }

    public void updateFood(Food food) {
        food.setFirstItem(firstItemField.getText());
        food.setSecondItem(secondItemField.getText());
        food.setThirdItem(thirdItemField.getText());
        food.setFourthItem(fourthItemField.getText());
    }
}
