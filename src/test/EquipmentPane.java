package test;

import device1.hardware.Equipment;
import device1.software.DMT;
import device1.software.EquipmentOS;
import device1.software.EquipmentOSImpl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import process.software.impl.ProcessOS;

import java.util.Timer;
import java.util.TimerTask;

public class EquipmentPane{
    //设备使用信息
    private EquipmentOSImpl equipmentOS;
    //显示设备是否使用
    private RadioButton A1;
    private RadioButton A2;
    private RadioButton A3;
    private RadioButton B1;
    private RadioButton B2;
    private RadioButton C1;

    //显示设备的使用状况
    private ProgressBar eA1;
    private ProgressBar eA2;
    private ProgressBar eA3;
    private ProgressBar eB1;
    private ProgressBar eB2;
    private ProgressBar eC1;


    public EquipmentPane(EquipmentOSImpl equipmentOS){
        this.equipmentOS = equipmentOS;
    }

    public VBox getEquipmentPane(){
        VBox vBox = new VBox(2);
        vBox.getChildren().add(createRadioButtonOfEquipment());
        vBox.getChildren().add(createEquipmentUseTabel());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkUsingCondition();
            }
        },3000,3000);
        return vBox;
    }

    public GridPane createEquipmentUseTabel(){
        GridPane gridPane = new GridPane();
        gridPane.setVgap(50);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(50,0,0,0));
        eA1 = new ProgressBar();
        eA2 = new ProgressBar();
        eA3 = new ProgressBar();
        eB1 = new ProgressBar();
        eB2 = new ProgressBar();
        eC1 = new ProgressBar();
        conditionOfcheckDeviceUsing(eA1,equipmentOS.getEquipmentA().getDeviceUseTable()[0]);
        conditionOfcheckDeviceUsing(eA2,equipmentOS.getEquipmentA().getDeviceUseTable()[1]);
        conditionOfcheckDeviceUsing(eA3,equipmentOS.getEquipmentA().getDeviceUseTable()[2]);
        conditionOfcheckDeviceUsing(eB1,equipmentOS.getEquipmentB().getDeviceUseTable()[0]);
        conditionOfcheckDeviceUsing(eB2,equipmentOS.getEquipmentB().getDeviceUseTable()[1]);
        conditionOfcheckDeviceUsing(eC1,equipmentOS.getEquipmentC().getDeviceUseTable()[0]);
        gridPane.add(eA1,1,0);
        gridPane.add(eA2,1,1);
        gridPane.add(eA3,1,2);
        gridPane.add(eB1,1,3);
        gridPane.add(eB2,1,4);
        gridPane.add(eC1,1,5);
        gridPane.add(new Label("A1:"),0,0);
        gridPane.add(new Label("A2:"),0,1);
        gridPane.add(new Label("A3:"),0,2);
        gridPane.add(new Label("B1:"),0,3);
        gridPane.add(new Label("B2:"),0,4);
        gridPane.add(new Label("C1:"),0,5);
        return gridPane;
    }

    //进度条显示设备使用状况
    public ProgressBar equipmentUseCondition(int usedTime,int useTime){
        ProgressBar pb = new ProgressBar(usedTime/useTime);
        pb.setPrefSize(200,25);
        return pb;
    }

    //判断设备是否使用及其使用的状况
    public void conditionOfcheckDeviceUsing(ProgressBar equipmentProgress,DMT deviceUsingCondition){
        equipmentProgress.setPrefSize(200,25);
        if(deviceUsingCondition==null){
            equipmentProgress.setProgress(0);
        }else{
            equipmentProgress.setProgress(deviceUsingCondition.getUseTime()/deviceUsingCondition.getTime());
        }
    }

    public GridPane createRadioButtonOfEquipment(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,0,0,0));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(25);
        gridPane.setVgap(25);


        A1 = createRadioButtonOfEquipment("A1",equipmentOS.getEquipmentA().getIsUsingDevice()[0]);
        A2 = createRadioButtonOfEquipment("A2",equipmentOS.getEquipmentA().getIsUsingDevice()[1]);
        A3 = createRadioButtonOfEquipment("A3",equipmentOS.getEquipmentA().getIsUsingDevice()[2]);
        B1 = createRadioButtonOfEquipment("B1",equipmentOS.getEquipmentB().getIsUsingDevice()[0]);
        B2 = createRadioButtonOfEquipment("B2",equipmentOS.getEquipmentB().getIsUsingDevice()[1]);
        C1 = createRadioButtonOfEquipment("C1",equipmentOS.getEquipmentC().getIsUsingDevice()[0]);
        gridPane.add(A1,0,0);
        gridPane.add(A2,1,0);
        gridPane.add(A3,2,0);
        gridPane.add(B1,0,1);
        gridPane.add(B2,1,1);
        gridPane.add(C1,2,1);


        return gridPane;
    }

    public RadioButton createRadioButtonOfEquipment(String text, boolean isUsing){
        RadioButton radioButton = new RadioButton(text);
        radioButton.setDisable(true);
        radioButton.setSelected(isUsing);
        radioButton.setStyle("-fx-background-color:" +
                " #CCFF99;-fx-text-fill: blue;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;");
        return radioButton;
    }

    public void checkUsingCondition(){
        A1.setSelected(equipmentOS.getEquipmentA().getIsUsingDevice()[0]);
        A2.setSelected(equipmentOS.getEquipmentA().getIsUsingDevice()[1]);
        A3.setSelected(equipmentOS.getEquipmentA().getIsUsingDevice()[2]);
        B1.setSelected(equipmentOS.getEquipmentB().getIsUsingDevice()[0]);
        B2.setSelected(equipmentOS.getEquipmentB().getIsUsingDevice()[1]);
        C1.setSelected(equipmentOS.getEquipmentC().getIsUsingDevice()[0]);
        conditionOfcheckDeviceUsing(eA1,equipmentOS.getEquipmentA().getDeviceUseTable()[0]);
        conditionOfcheckDeviceUsing(eA2,equipmentOS.getEquipmentA().getDeviceUseTable()[1]);
        conditionOfcheckDeviceUsing(eA3,equipmentOS.getEquipmentA().getDeviceUseTable()[2]);
        conditionOfcheckDeviceUsing(eB1,equipmentOS.getEquipmentB().getDeviceUseTable()[0]);
        conditionOfcheckDeviceUsing(eB2,equipmentOS.getEquipmentB().getDeviceUseTable()[1]);
        conditionOfcheckDeviceUsing(eC1,equipmentOS.getEquipmentC().getDeviceUseTable()[0]);
    }

}
