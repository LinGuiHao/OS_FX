package panel;

import device1.software.EquipmentOSImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import memory.hardware.PCB;
import panel.controller.ExeProcessResultController;
import panel.controller.ProcessController;

import java.io.IOException;
import java.net.URL;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL location = getClass().getResource("fxml//exepane.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = null;
        root = fxmlLoader.load();
        primaryStage.setTitle("任务管理器" );
        primaryStage.setScene(new Scene(root, 480, 540));
        primaryStage.setResizable(false);
        ExeProcessResultController controller = fxmlLoader.getController();
        PCB pcb = new PCB();
//        controller.init();
//        controller.setEquipmentOS();
        primaryStage.setOnCloseRequest(
                event -> {
                    try {
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
