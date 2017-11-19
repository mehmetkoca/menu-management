package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class Food {
    private SimpleStringProperty firstItem = new SimpleStringProperty("");
    private SimpleStringProperty secondItem = new SimpleStringProperty("");
    private SimpleStringProperty thirdItem = new SimpleStringProperty("");
    private SimpleStringProperty fourthItem = new SimpleStringProperty("");

    public Food() {
    }

    public Food(String firstItem, String secondItem, String thirdItem, String fourthItem){
        this.firstItem.set(firstItem);
        this.secondItem.set(secondItem);
        this.thirdItem.set(thirdItem);
        this.fourthItem.set(fourthItem);
    }

    public String getFirstItem() {
        return firstItem.get();
    }

    public SimpleStringProperty firstItemProperty() {
        return firstItem;
    }

    public void setFirstItem(String firstItem) {
        this.firstItem.set(firstItem);
    }

    public String getSecondItem() {
        return secondItem.get();
    }

    public SimpleStringProperty secondItemProperty() {
        return secondItem;
    }

    public void setSecondItem(String secondItem) {
        this.secondItem.set(secondItem);
    }

    public String getThirdItem() {
        return thirdItem.get();
    }

    public SimpleStringProperty thirdItemProperty() {
        return thirdItem;
    }

    public void setThirdItem(String thirdItem) {
        this.thirdItem.set(thirdItem);
    }

    public String getFourthItem() {
        return fourthItem.get();
    }

    public SimpleStringProperty fourthItemProperty() {
        return fourthItem;
    }

    public void setFourthItem(String fourthItem) {
        this.fourthItem.set(fourthItem);
    }

    @Override
    public String toString() {
        return "Food{" +
                "firstItem=" + firstItem +
                ", secondItem=" + secondItem +
                ", thirdItem=" + thirdItem +
                ", fourthItem=" + fourthItem +
                '}';
    }
}
