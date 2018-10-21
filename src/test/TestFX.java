package test;

import device1.software.EquipmentOSImpl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.Timer;
import java.util.TimerTask;

public class TestFX extends Application {



    @Override
    public void start(Stage primaryStage) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(200,50);
        progressBar.setProgress(0.5);
        Pane pane = new Pane();
        pane.getChildren().add(progressBar);
        Scene scene = new Scene(pane,200,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args){
        Application.launch(args);
    }
}
