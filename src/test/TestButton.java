package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class TestButton extends Application {

    private boolean isUsing = false;

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button();
        TestButton testButton = new TestButton();
        RadioButton radioButton = new RadioButton();
        radioButton.setSelected(testButton.isUsing);
        radioButton.setDisable(true);
        button.setOnAction(e->{
            radioButton.setSelected(testButton.isUsing);
            testButton.setUsing(!testButton.isUsing);
        });
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(button);
        flowPane.getChildren().add(radioButton);
        Scene scene = new Scene(flowPane,200,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args){
        Application.launch(args);
    }

    public boolean isUsing() {
        return isUsing;
    }

    public void setUsing(boolean using) {
        isUsing = using;
    }
}
