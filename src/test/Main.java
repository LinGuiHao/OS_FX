package test;

import device1.software.EquipmentOSImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import memory.software.MemoryOS;
import memory.software.impl.MemoryOSImpl;
import process.software.impl.ProcessOSImp;

import java.util.Scanner;

public class Main extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {
        MemoryOSImpl memoryOS = new MemoryOSImpl();
        ProcessOSImp processOS = new ProcessOSImp(memoryOS);
        EquipmentOSImpl equipmentOS = new EquipmentOSImpl(processOS);
        EquipmentPane equipmentPane = new EquipmentPane(equipmentOS);
        Scene scene = new Scene(equipmentPane.getEquipmentPane(),500,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
