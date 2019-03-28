package ExpensesTracker.Controllers;

import ExpensesTracker.Models.Dashboard;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddexpenseController implements Initializable {
    private final Stage thisStage;
    private Boolean saveButtonPressed = new Boolean(false);
    private Dashboard dashboard;
    //FXML
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;
    @FXML private DatePicker date;
    @FXML private TextField price;
    @FXML private TextArea description;
    @FXML private ChoiceBox<String> categoryBox;

    public AddexpenseController(Dashboard dashboard) {
        this.dashboard = dashboard;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AddexpenseUI.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.resizableProperty().setValue(false);
            thisStage.setTitle("Add expense");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public Boolean getSaveButtonPressed() {
        return saveButtonPressed;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Sets that the amount property merely accepts numbers
        price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,4})?")) {
                    price.setText(oldValue);
                }
            }
        });
        //Load data to categoryBox and set a default value;
        categoryBox.getItems().addAll("Food", "Clothes", "Hobby", "Transport", "Health, hygiene and chemistry", "Other");
        categoryBox.setValue("Food");

        saveBtn.setOnAction(e -> {
            dashboard.getExpensesListObj().addToList(date.getValue().toString(), description.getText(), categoryBox.getValue(), price.getText());
            saveButtonPressed = true;
            thisStage.close();
        });
        cancelBtn.setOnAction(e -> {
            thisStage.close();
        });
    }
}
